package omni.util;
import org.junit.jupiter.api.Test;
import java.util.Random;
public class ArrCopyTest
{
    @Test
    public void testSemiCheckedCopybooleanBoolean()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Boolean[] dst=new Boolean[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybooleanBoolean()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Boolean[] dst=new Boolean[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybyteByte()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Byte[] dst=new Byte[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybyteByte()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Byte[] dst=new Byte[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopycharCharacter()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Character[] dst=new Character[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopycharCharacter()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Character[] dst=new Character[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyshortShort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Short[] dst=new Short[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyshortShort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Short[] dst=new Short[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyintInteger()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Integer[] dst=new Integer[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyintInteger()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Integer[] dst=new Integer[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopylongLong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Long[] dst=new Long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopylongLong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Long[] dst=new Long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyfloatFloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        float[] src=new float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Float[] dst=new Float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyfloatFloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        float[] src=new float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Float[] dst=new Float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopydoubleDouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        double[] src=new double[srcLength];
        if(srcLength!=0)
        {
          doubleArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Double[] dst=new Double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopydoubleDouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        double[] src=new double[srcLength];
        if(srcLength!=0)
        {
          doubleArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Double[] dst=new Double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybooleanObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybooleanObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybyteObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybyteObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopycharObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopycharObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyshortObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyshortObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyintObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyintObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopylongObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopylongObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyfloatObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        float[] src=new float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyfloatObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        float[] src=new float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopydoubleObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        double[] src=new double[srcLength];
        if(srcLength!=0)
        {
          doubleArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopydoubleObject()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        double[] src=new double[srcLength];
        if(srcLength!=0)
        {
          doubleArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        Object[] dst=new Object[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyBooleanboolean()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Boolean[] src=new Boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        boolean[] dst=new boolean[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyBooleanboolean()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Boolean[] src=new Boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        boolean[] dst=new boolean[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyBytebyte()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Byte[] src=new Byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        byte[] dst=new byte[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyBytebyte()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Byte[] src=new Byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        byte[] dst=new byte[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyCharacterchar()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Character[] src=new Character[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        char[] dst=new char[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyCharacterchar()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Character[] src=new Character[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        char[] dst=new char[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyShortshort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Short[] src=new Short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        short[] dst=new short[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyShortshort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Short[] src=new Short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        short[] dst=new short[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyIntegerint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Integer[] src=new Integer[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyIntegerint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Integer[] src=new Integer[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyLonglong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Long[] src=new Long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyLonglong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Long[] src=new Long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyFloatfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Float[] src=new Float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyFloatfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Float[] src=new Float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyDoubledouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Double[] src=new Double[srcLength];
        if(srcLength!=0)
        {
          doubleArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyDoubledouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        Double[] src=new Double[srcLength];
        if(srcLength!=0)
        {
          doubleArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybooleanboolean()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        boolean[] dst=new boolean[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybooleanboolean()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        boolean[] dst=new boolean[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybooleanbyte()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        byte[] dst=new byte[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybooleanbyte()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        byte[] dst=new byte[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybooleanchar()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        char[] dst=new char[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybooleanchar()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        char[] dst=new char[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybooleanshort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        short[] dst=new short[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybooleanshort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        short[] dst=new short[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybooleanint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybooleanint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybooleanlong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybooleanlong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybooleanfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybooleanfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybooleandouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybooleandouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybytebyte()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        byte[] dst=new byte[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybytebyte()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        byte[] dst=new byte[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybyteshort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        short[] dst=new short[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybyteshort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        short[] dst=new short[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybyteint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybyteint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybytelong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybytelong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybytefloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybytefloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopybytedouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopybytedouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopycharchar()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        char[] dst=new char[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopycharchar()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        char[] dst=new char[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopycharint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopycharint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopycharlong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopycharlong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopycharfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopycharfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopychardouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopychardouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyshortshort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        short[] dst=new short[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyshortshort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        short[] dst=new short[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyshortint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyshortint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyshortlong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyshortlong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyshortfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyshortfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyshortdouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyshortdouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyintint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyintint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        int[] dst=new int[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyintlong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyintlong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyintfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyintfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyintdouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyintdouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopylonglong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopylonglong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        long[] dst=new long[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopylongfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopylongfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopylongdouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopylongdouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyfloatfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        float[] src=new float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyfloatfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        float[] src=new float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        float[] dst=new float[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyfloatdouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        float[] src=new float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyfloatdouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        float[] src=new float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopydoubledouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        double[] src=new double[srcLength];
        if(srcLength!=0)
        {
          doubleArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopydoubledouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        double[] src=new double[srcLength];
        if(srcLength!=0)
        {
          doubleArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        double[] dst=new double[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedCopyStringString()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        String[] src=new String[srcLength];
        if(srcLength!=0)
        {
          StringArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        String[] dst=new String[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testSemiCheckedReverseCopyStringString()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        String[] src=new String[srcLength];
        if(srcLength!=0)
        {
          StringArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int dstLength=RandomUtil.randomIntBetween(srcLength,srcLength+1000,rand);
        String[] dst=new String[dstLength];
        int copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
        int srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
        int dstOffset=RandomUtil.randomIntBetween(0,dstLength-copyLength,rand);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
        }
      }
    }
    @Test
    public void testsemicheckedSelfCopyboolean()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        boolean[] src=new boolean[srcLength];
        if(srcLength!=0)
        {
          booleanArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int copyLength,srcOffset,dstOffset;
        if(srcLength>5)
        {
          switch(rand.nextInt(3))
          {
          case 0:
            copyLength=RandomUtil.randomIntBetween(6,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            break;
          case 1:
            copyLength=RandomUtil.randomIntBetween(0,5,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
            break;
          default:
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
            break;
          }
        }
        else
        {
          if(srcLength<2||rand.nextBoolean())
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
          }
          else
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
          }
        }
        boolean[] copy=new boolean[copyLength];
        ArrCopy.semicheckedCopy(src,srcOffset,copy,0,copyLength);
        ArrCopy.semicheckedSelfCopy(src,srcOffset,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,dstOffset,copy,0,copyLength);
        }
      }
    }
    @Test
    public void testsemicheckedSelfCopybyte()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        byte[] src=new byte[srcLength];
        if(srcLength!=0)
        {
          byteArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int copyLength,srcOffset,dstOffset;
        if(srcLength>5)
        {
          switch(rand.nextInt(3))
          {
          case 0:
            copyLength=RandomUtil.randomIntBetween(6,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            break;
          case 1:
            copyLength=RandomUtil.randomIntBetween(0,5,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
            break;
          default:
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
            break;
          }
        }
        else
        {
          if(srcLength<2||rand.nextBoolean())
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
          }
          else
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
          }
        }
        byte[] copy=new byte[copyLength];
        ArrCopy.semicheckedCopy(src,srcOffset,copy,0,copyLength);
        ArrCopy.semicheckedSelfCopy(src,srcOffset,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,dstOffset,copy,0,copyLength);
        }
      }
    }
    @Test
    public void testsemicheckedSelfCopychar()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        char[] src=new char[srcLength];
        if(srcLength!=0)
        {
          charArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int copyLength,srcOffset,dstOffset;
        if(srcLength>5)
        {
          switch(rand.nextInt(3))
          {
          case 0:
            copyLength=RandomUtil.randomIntBetween(6,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            break;
          case 1:
            copyLength=RandomUtil.randomIntBetween(0,5,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
            break;
          default:
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
            break;
          }
        }
        else
        {
          if(srcLength<2||rand.nextBoolean())
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
          }
          else
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
          }
        }
        char[] copy=new char[copyLength];
        ArrCopy.semicheckedCopy(src,srcOffset,copy,0,copyLength);
        ArrCopy.semicheckedSelfCopy(src,srcOffset,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,dstOffset,copy,0,copyLength);
        }
      }
    }
    @Test
    public void testsemicheckedSelfCopyshort()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        short[] src=new short[srcLength];
        if(srcLength!=0)
        {
          shortArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int copyLength,srcOffset,dstOffset;
        if(srcLength>5)
        {
          switch(rand.nextInt(3))
          {
          case 0:
            copyLength=RandomUtil.randomIntBetween(6,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            break;
          case 1:
            copyLength=RandomUtil.randomIntBetween(0,5,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
            break;
          default:
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
            break;
          }
        }
        else
        {
          if(srcLength<2||rand.nextBoolean())
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
          }
          else
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
          }
        }
        short[] copy=new short[copyLength];
        ArrCopy.semicheckedCopy(src,srcOffset,copy,0,copyLength);
        ArrCopy.semicheckedSelfCopy(src,srcOffset,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,dstOffset,copy,0,copyLength);
        }
      }
    }
    @Test
    public void testsemicheckedSelfCopyint()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        int[] src=new int[srcLength];
        if(srcLength!=0)
        {
          intArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int copyLength,srcOffset,dstOffset;
        if(srcLength>5)
        {
          switch(rand.nextInt(3))
          {
          case 0:
            copyLength=RandomUtil.randomIntBetween(6,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            break;
          case 1:
            copyLength=RandomUtil.randomIntBetween(0,5,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
            break;
          default:
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
            break;
          }
        }
        else
        {
          if(srcLength<2||rand.nextBoolean())
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
          }
          else
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
          }
        }
        int[] copy=new int[copyLength];
        ArrCopy.semicheckedCopy(src,srcOffset,copy,0,copyLength);
        ArrCopy.semicheckedSelfCopy(src,srcOffset,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,dstOffset,copy,0,copyLength);
        }
      }
    }
    @Test
    public void testsemicheckedSelfCopylong()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        long[] src=new long[srcLength];
        if(srcLength!=0)
        {
          longArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int copyLength,srcOffset,dstOffset;
        if(srcLength>5)
        {
          switch(rand.nextInt(3))
          {
          case 0:
            copyLength=RandomUtil.randomIntBetween(6,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            break;
          case 1:
            copyLength=RandomUtil.randomIntBetween(0,5,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
            break;
          default:
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
            break;
          }
        }
        else
        {
          if(srcLength<2||rand.nextBoolean())
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
          }
          else
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
          }
        }
        long[] copy=new long[copyLength];
        ArrCopy.semicheckedCopy(src,srcOffset,copy,0,copyLength);
        ArrCopy.semicheckedSelfCopy(src,srcOffset,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,dstOffset,copy,0,copyLength);
        }
      }
    }
    @Test
    public void testsemicheckedSelfCopyfloat()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        float[] src=new float[srcLength];
        if(srcLength!=0)
        {
          floatArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int copyLength,srcOffset,dstOffset;
        if(srcLength>5)
        {
          switch(rand.nextInt(3))
          {
          case 0:
            copyLength=RandomUtil.randomIntBetween(6,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            break;
          case 1:
            copyLength=RandomUtil.randomIntBetween(0,5,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
            break;
          default:
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
            break;
          }
        }
        else
        {
          if(srcLength<2||rand.nextBoolean())
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
          }
          else
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
          }
        }
        float[] copy=new float[copyLength];
        ArrCopy.semicheckedCopy(src,srcOffset,copy,0,copyLength);
        ArrCopy.semicheckedSelfCopy(src,srcOffset,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,dstOffset,copy,0,copyLength);
        }
      }
    }
    @Test
    public void testsemicheckedSelfCopydouble()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        double[] src=new double[srcLength];
        if(srcLength!=0)
        {
          doubleArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int copyLength,srcOffset,dstOffset;
        if(srcLength>5)
        {
          switch(rand.nextInt(3))
          {
          case 0:
            copyLength=RandomUtil.randomIntBetween(6,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            break;
          case 1:
            copyLength=RandomUtil.randomIntBetween(0,5,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
            break;
          default:
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
            break;
          }
        }
        else
        {
          if(srcLength<2||rand.nextBoolean())
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
          }
          else
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
          }
        }
        double[] copy=new double[copyLength];
        ArrCopy.semicheckedCopy(src,srcOffset,copy,0,copyLength);
        ArrCopy.semicheckedSelfCopy(src,srcOffset,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,dstOffset,copy,0,copyLength);
        }
      }
    }
    @Test
    public void testsemicheckedSelfCopyString()
    {
      Random rand=new Random(0);
      for(int i=0;i<1000;++i)
      {
        int srcLength=RandomUtil.randomIntBetween(0,1000,rand);
        String[] src=new String[srcLength];
        if(srcLength!=0)
        {
          StringArrayBuilder.Randomized.buildUnchecked(src,0,srcLength,rand,0);
        }
        int copyLength,srcOffset,dstOffset;
        if(srcLength>5)
        {
          switch(rand.nextInt(3))
          {
          case 0:
            copyLength=RandomUtil.randomIntBetween(6,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            break;
          case 1:
            copyLength=RandomUtil.randomIntBetween(0,5,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
            break;
          default:
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
            break;
          }
        }
        else
        {
          if(srcLength<2||rand.nextBoolean())
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,srcLength-copyLength,rand);
            dstOffset=RandomUtil.randomIntBetween(0,srcOffset,rand);
          }
          else
          {
            copyLength=RandomUtil.randomIntBetween(0,srcLength/2,rand);
            dstOffset=RandomUtil.randomIntBetween(copyLength,srcLength-copyLength,rand);
            srcOffset=RandomUtil.randomIntBetween(0,dstOffset-copyLength,rand);
          }
        }
        String[] copy=new String[copyLength];
        ArrCopy.semicheckedCopy(src,srcOffset,copy,0,copyLength);
        ArrCopy.semicheckedSelfCopy(src,srcOffset,dstOffset,copyLength);
        if(copyLength!=0)
        {
          EqualityUtil.uncheckedassertarraysAreEqual(src,dstOffset,copy,0,copyLength);
        }
      }
    }
}
