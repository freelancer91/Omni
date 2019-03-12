package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Random;
public class BitSetUtilTest{
    @Test
    public void tesGetBitSet(){
        for(int count=0;count<=100;++count){
            Assertions.assertArrayEquals(new long[(count-1>>6)+1],BitSetUtil.getBitSet(count));
        }
    }
    @Test
    public void testContainsWord(){
        long word=0xAAAAAAAAAAAAAAAAL;
        for(int i=Byte.MIN_VALUE;i<=Byte.MAX_VALUE;i+=2){
            Assertions.assertFalse(BitSetUtil.containsword(word,i));
            Assertions.assertTrue(BitSetUtil.containsword(word,i+1));
        }
    }
    @Test
    public void testMarkSurvivorsBitSetbyte()
    {
      Random rand=new Random(0);
      var filter=bytePredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        byte[] arr=byteArrayBuilder.buildRandomArray(5000,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long[] bitSet=BitSetUtil.getBitSet(arr.length);
        int numExpectedSurvivors=BitSetUtil.markSurvivors(arr,0,arr.length,filter,bitSet);
        byte[] copy=new byte[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,bitSet);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          long word=bitSet[j>>>6];
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsRetWordbyte()
    {
      Random rand=new Random(0);
      var filter=bytePredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        byte[] arr=byteArrayBuilder.buildRandomArray(64,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long word=BitSetUtil.markSurvivors(arr,0,arr.length,filter);
        int numExpectedSurvivors=Long.bitCount(word);
        byte[] copy=new byte[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,word);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsBitSetchar()
    {
      Random rand=new Random(0);
      var filter=charPredicates.MarkGreaterThan.getPred(rand,128);
      for(int i=0;i<100;++i)
      {
        char[] arr=charArrayBuilder.buildRandomArray(5000,0,256,rand);
        long[] bitSet=BitSetUtil.getBitSet(arr.length);
        int numExpectedSurvivors=BitSetUtil.markSurvivors(arr,0,arr.length,filter,bitSet);
        char[] copy=new char[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,bitSet);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          long word=bitSet[j>>>6];
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsRetWordchar()
    {
      Random rand=new Random(0);
      var filter=charPredicates.MarkGreaterThan.getPred(rand,128);
      for(int i=0;i<100;++i)
      {
        char[] arr=charArrayBuilder.buildRandomArray(64,0,256,rand);
        long word=BitSetUtil.markSurvivors(arr,0,arr.length,filter);
        int numExpectedSurvivors=Long.bitCount(word);
        char[] copy=new char[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,word);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsBitSetshort()
    {
      Random rand=new Random(0);
      var filter=shortPredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        short[] arr=shortArrayBuilder.buildRandomArray(5000,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long[] bitSet=BitSetUtil.getBitSet(arr.length);
        int numExpectedSurvivors=BitSetUtil.markSurvivors(arr,0,arr.length,filter,bitSet);
        short[] copy=new short[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,bitSet);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          long word=bitSet[j>>>6];
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsRetWordshort()
    {
      Random rand=new Random(0);
      var filter=shortPredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        short[] arr=shortArrayBuilder.buildRandomArray(64,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long word=BitSetUtil.markSurvivors(arr,0,arr.length,filter);
        int numExpectedSurvivors=Long.bitCount(word);
        short[] copy=new short[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,word);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsBitSetint()
    {
      Random rand=new Random(0);
      var filter=intPredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        int[] arr=intArrayBuilder.buildRandomArray(5000,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long[] bitSet=BitSetUtil.getBitSet(arr.length);
        int numExpectedSurvivors=BitSetUtil.markSurvivors(arr,0,arr.length,filter,bitSet);
        int[] copy=new int[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,bitSet);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          long word=bitSet[j>>>6];
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsRetWordint()
    {
      Random rand=new Random(0);
      var filter=intPredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        int[] arr=intArrayBuilder.buildRandomArray(64,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long word=BitSetUtil.markSurvivors(arr,0,arr.length,filter);
        int numExpectedSurvivors=Long.bitCount(word);
        int[] copy=new int[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,word);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsBitSetlong()
    {
      Random rand=new Random(0);
      var filter=longPredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        long[] arr=longArrayBuilder.buildRandomArray(5000,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long[] bitSet=BitSetUtil.getBitSet(arr.length);
        int numExpectedSurvivors=BitSetUtil.markSurvivors(arr,0,arr.length,filter,bitSet);
        long[] copy=new long[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,bitSet);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          long word=bitSet[j>>>6];
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsRetWordlong()
    {
      Random rand=new Random(0);
      var filter=longPredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        long[] arr=longArrayBuilder.buildRandomArray(64,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long word=BitSetUtil.markSurvivors(arr,0,arr.length,filter);
        int numExpectedSurvivors=Long.bitCount(word);
        long[] copy=new long[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,word);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsBitSetfloat()
    {
      Random rand=new Random(0);
      var filter=floatPredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        float[] arr=floatArrayBuilder.buildRandomArray(5000,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long[] bitSet=BitSetUtil.getBitSet(arr.length);
        int numExpectedSurvivors=BitSetUtil.markSurvivors(arr,0,arr.length,filter,bitSet);
        float[] copy=new float[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,bitSet);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          long word=bitSet[j>>>6];
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsRetWordfloat()
    {
      Random rand=new Random(0);
      var filter=floatPredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        float[] arr=floatArrayBuilder.buildRandomArray(64,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long word=BitSetUtil.markSurvivors(arr,0,arr.length,filter);
        int numExpectedSurvivors=Long.bitCount(word);
        float[] copy=new float[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,word);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsBitSetdouble()
    {
      Random rand=new Random(0);
      var filter=doublePredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        double[] arr=doubleArrayBuilder.buildRandomArray(5000,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long[] bitSet=BitSetUtil.getBitSet(arr.length);
        int numExpectedSurvivors=BitSetUtil.markSurvivors(arr,0,arr.length,filter,bitSet);
        double[] copy=new double[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,bitSet);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          long word=bitSet[j>>>6];
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsRetWorddouble()
    {
      Random rand=new Random(0);
      var filter=doublePredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        double[] arr=doubleArrayBuilder.buildRandomArray(64,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long word=BitSetUtil.markSurvivors(arr,0,arr.length,filter);
        int numExpectedSurvivors=Long.bitCount(word);
        double[] copy=new double[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,word);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsBitSetInteger()
    {
      Random rand=new Random(0);
      var filter=IntegerPredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        Integer[] arr=IntegerArrayBuilder.buildRandomArray(5000,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long[] bitSet=BitSetUtil.getBitSet(arr.length);
        int numExpectedSurvivors=BitSetUtil.markSurvivors(arr,0,arr.length,filter,bitSet);
        Integer[] copy=new Integer[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,bitSet);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          long word=bitSet[j>>>6];
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
    @Test
    public void testMarkSurvivorsRetWordInteger()
    {
      Random rand=new Random(0);
      var filter=IntegerPredicates.MarkGreaterThan.getPred(rand,0);
      for(int i=0;i<100;++i)
      {
        Integer[] arr=IntegerArrayBuilder.buildRandomArray(64,Byte.MIN_VALUE,Byte.MAX_VALUE,rand);
        long word=BitSetUtil.markSurvivors(arr,0,arr.length,filter);
        int numExpectedSurvivors=Long.bitCount(word);
        Integer[] copy=new Integer[arr.length];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
        BitSetUtil.pullSurvivorsDown(copy,0,0,numExpectedSurvivors,word);
        int numActualSurvivors=0;
        for(int j=0;j<arr.length;++j)
        {
          var currVal=arr[j];
          boolean v=filter.test(currVal);
          if(!v)
          {
            Assertions.assertTrue(EqualityUtil.isEqual(currVal,copy[numActualSurvivors]));
            ++numActualSurvivors;
          }
          Assertions.assertEquals(v,(word&1L<<j)==0);
        }
        Assertions.assertEquals(numActualSurvivors,numExpectedSurvivors);
      }
    }
}
