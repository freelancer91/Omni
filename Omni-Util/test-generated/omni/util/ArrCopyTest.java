package omni.util;
import org.junit.jupiter.api.Test;
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
        boolean[] src;
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Boolean[] dst=new Boolean[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Boolean[] dst=new Boolean[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Byte[] dst=new Byte[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Byte[] dst=new Byte[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Character[] dst=new Character[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Character[] dst=new Character[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Short[] dst=new Short[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Short[] dst=new Short[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Integer[] dst=new Integer[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Integer[] dst=new Integer[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Long[] dst=new Long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Long[] dst=new Long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.floatArrayBuilder.Randomized.buildUnchecked(src=new float[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Float[] dst=new Float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.floatArrayBuilder.Randomized.buildUnchecked(src=new float[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Float[] dst=new Float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.doubleArrayBuilder.Randomized.buildUnchecked(src=new double[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Double[] dst=new Double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.doubleArrayBuilder.Randomized.buildUnchecked(src=new double[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Double[] dst=new Double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.floatArrayBuilder.Randomized.buildUnchecked(src=new float[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.floatArrayBuilder.Randomized.buildUnchecked(src=new float[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.doubleArrayBuilder.Randomized.buildUnchecked(src=new double[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.doubleArrayBuilder.Randomized.buildUnchecked(src=new double[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        Object[] dst=new Object[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new Boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        boolean[] dst=new boolean[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new Boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        boolean[] dst=new boolean[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new Byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        byte[] dst=new byte[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new Byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        byte[] dst=new byte[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new Character[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        char[] dst=new char[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new Character[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        char[] dst=new char[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new Short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        short[] dst=new short[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new Short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        short[] dst=new short[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new Integer[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new Integer[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new Long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new Long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.floatArrayBuilder.Randomized.buildUnchecked(src=new Float[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.floatArrayBuilder.Randomized.buildUnchecked(src=new Float[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.doubleArrayBuilder.Randomized.buildUnchecked(src=new Double[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.doubleArrayBuilder.Randomized.buildUnchecked(src=new Double[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        boolean[] dst=new boolean[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        boolean[] dst=new boolean[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        byte[] dst=new byte[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        byte[] dst=new byte[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        char[] dst=new char[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        char[] dst=new char[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        short[] dst=new short[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        short[] dst=new short[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.booleanArrayBuilder.Randomized.buildUnchecked(src=new boolean[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        byte[] dst=new byte[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        byte[] dst=new byte[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        short[] dst=new short[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        short[] dst=new short[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.byteArrayBuilder.Randomized.buildUnchecked(src=new byte[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        char[] dst=new char[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        char[] dst=new char[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.charArrayBuilder.Randomized.buildUnchecked(src=new char[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        short[] dst=new short[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        short[] dst=new short[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.shortArrayBuilder.Randomized.buildUnchecked(src=new short[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        int[] dst=new int[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.intArrayBuilder.Randomized.buildUnchecked(src=new int[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        long[] dst=new long[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.longArrayBuilder.Randomized.buildUnchecked(src=new long[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.floatArrayBuilder.Randomized.buildUnchecked(src=new float[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.floatArrayBuilder.Randomized.buildUnchecked(src=new float[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        float[] dst=new float[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.floatArrayBuilder.Randomized.buildUnchecked(src=new float[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.floatArrayBuilder.Randomized.buildUnchecked(src=new float[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.doubleArrayBuilder.Randomized.buildUnchecked(src=new double[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.doubleArrayBuilder.Randomized.buildUnchecked(src=new double[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        double[] dst=new double[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.StringArrayBuilder.Randomized.buildUnchecked(src=new String[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        String[] dst=new String[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
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
        JunitUtil.StringArrayBuilder.Randomized.buildUnchecked(src=new String[srcLength],0,srcLength,rand,0);
        int dstLength=srcLength+rand.nextInt(1000);
        String[] dst=new String[dstLength];
        int copyLength=rand.nextInt(srcLength+1);
        int srcOffset=rand.nextInt(srcLength-copyLength+1);
        int dstOffset=rand.nextInt(dstLength-copyLength+1);
        ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
        JunitUtil.uncheckedparallelassertreversearraysAreEqual(src,srcOffset,dst,dstOffset,copyLength);
      }
    }
}
