package omni.util;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BitSetUtilTest{
    
    
    
    @Test
    public void testconvertToPackedIndex_int() {
        for(int nonPackedIndex=0;nonPackedIndex<=129;++nonPackedIndex) {
            Assertions.assertEquals(nonPackedIndex/64,BitSetUtil.convertToPackedIndex(nonPackedIndex));
        }
    }
    @Test
    public void testconvertToNonPackedIndex_int() {
        for(int packedIndex=0;packedIndex<=10;++packedIndex) {
            Assertions.assertEquals(packedIndex*64,BitSetUtil.convertToNonPackedIndex(packedIndex));
        }
    }
    @Test
    public void testgetPackedCapacity_int() {
        for(int nonPackedCapacity=0;nonPackedCapacity<=129;++nonPackedCapacity) {
            Assertions.assertEquals((nonPackedCapacity-1)/64+1,BitSetUtil.getPackedCapacity(nonPackedCapacity));
        }
    }
    private static final int[] FIB_SEQ=new int[] {0,1,2,3,5,8,13,21,34,55,89,144,233,377,610};
    private static final long[] FIB_WORDS=new long[] {
            1L<<0 | 1L<<1 | 1L<<2 | 1L<<3 | 1L<<5 | 1L<<8 | 1L<<13 | 1L<<21 | 1L<<34 | 1L<<55,
            1L<<89,
            1L<<144,
            1L<<233,
            0L,
            1L<<377,
            0L,
            0L,
            0L,
            1L<<610};
    
    
    @Test
    public void testgetFromPackedArr_longArrayint() {
        int nonPackedIndex,currFibIndex;
        for(nonPackedIndex=0,currFibIndex=0;nonPackedIndex<640;++nonPackedIndex) {
            final var val=BitSetUtil.getFromPackedArr(FIB_WORDS,nonPackedIndex);
            if(nonPackedIndex==FIB_SEQ[currFibIndex]) {
              Assertions.assertTrue(val);
              if(++currFibIndex==FIB_SEQ.length) {
                  break;
              }
            } else {
              Assertions.assertFalse(val);
            }
        }
        while(++nonPackedIndex<640) {
            Assertions.assertFalse(BitSetUtil.getFromPackedArr(FIB_WORDS,nonPackedIndex));
        }
    }
    @Test
    public void testgetAndSwap_longArrayintboolean() {
        final var words=Arrays.copyOf(FIB_WORDS,FIB_WORDS.length);
        int nonPackedIndex;
        int currFibIndex;
        for(nonPackedIndex=0,currFibIndex=0;nonPackedIndex<640;++nonPackedIndex) {
            if(nonPackedIndex==FIB_SEQ[currFibIndex]) {
                Assertions.assertTrue(BitSetUtil.getAndSwap(words,nonPackedIndex,false));
                Assertions.assertFalse(BitSetUtil.getAndSwap(words,nonPackedIndex,true));
                Assertions.assertTrue(BitSetUtil.getFromPackedArr(words,nonPackedIndex));
                if(++currFibIndex==FIB_SEQ.length) {
                    break;
                }
            }else {
                Assertions.assertFalse(BitSetUtil.getAndSwap(words,nonPackedIndex,true));
                Assertions.assertTrue(BitSetUtil.getAndSwap(words,nonPackedIndex,false));
                Assertions.assertFalse(BitSetUtil.getFromPackedArr(words,nonPackedIndex));
            }
        }
        while(++nonPackedIndex<640) {
            Assertions.assertFalse(BitSetUtil.getAndSwap(words,nonPackedIndex,true));
            Assertions.assertTrue(BitSetUtil.getAndSwap(words,nonPackedIndex,false));
            Assertions.assertFalse(BitSetUtil.getFromPackedArr(words,nonPackedIndex));
        }
    }
    @Test
    public void teststoreInPackedArr_longArrayintboolean() {
        final var words=new long[FIB_WORDS.length];
        for(int nonPackedIndex=0;nonPackedIndex<640;++nonPackedIndex) {
            BitSetUtil.storeInPackedArr(words,nonPackedIndex,true);
            Assertions.assertTrue(BitSetUtil.getFromPackedArr(words,nonPackedIndex));
            BitSetUtil.storeInPackedArr(words,nonPackedIndex,false);
            Assertions.assertFalse(BitSetUtil.getFromPackedArr(words,nonPackedIndex));
        }
    }
    private static long[] generateTestArr(int length,Random rand) {
        final var arr=new long[(length-1>>6)+1];
        for(var i=arr.length;--i>=0;) {
            arr[i]=rand.nextLong();
        }
        return arr;
    }
    
    @Test
    public void testarraycopy_longArrayintlongArrayintint() {
        final var srcArr=generateTestArr(640,new Random(0));
        for(int tmpSrcOffset=3;tmpSrcOffset<640;++tmpSrcOffset) {
            final int srcOffset=tmpSrcOffset;
            
            for(int tmpDstOffset=0;tmpDstOffset<640;++tmpDstOffset) {
                final int dstOffset=tmpDstOffset;
                System.out.printf("srcOffset=%3d; dstOffset=%3d%n",srcOffset,dstOffset);
                final int lengthBound=640-Math.max(srcOffset,dstOffset);
                for(int tmpLength=0;tmpLength<lengthBound;++tmpLength) {
                    final int length=tmpLength;
                    //TestExecutorService.submitTest(()->{
                        if(dstOffset==1 && srcOffset==3 && length==190) {
                            TestExecutorService.suspend();
                        }
                    
                        final long[] dstArr;
                        BitSetUtil.arraycopy(srcArr,srcOffset,dstArr=new long[10],dstOffset,length);
                        for(int s=srcOffset,d=dstOffset,sb=srcOffset+length;s<sb;++s,++d) {
                            Assertions.assertEquals(BitSetUtil.getFromPackedArr(srcArr,s),BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                        for(int d=0;d<dstOffset;++d) {
                            Assertions.assertFalse(BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                        for(int d=dstOffset+length;d<640;++d) {
                            Assertions.assertFalse(BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                    //});
                }
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testarraycopy_longArrayintlongArrayintint");
    }
    
    
    
}
