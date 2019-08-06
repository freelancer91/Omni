package omni.util;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
@TestMethodOrder(OrderAnnotation.class)
public class BitSetUtilTest{
    
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
    private static long[] generateTestArr(int length,Random rand) {
        final var arr=new long[(length-1>>6)+1];
        for(var i=arr.length;--i>=0;) {
            arr[i]=rand.nextLong();
        }
        return arr;
    }
    
    @Order(130)
    @Test
    public void testconvertToPackedIndex_int() {
        for(int tmpNonPackedIndex=0;tmpNonPackedIndex<=129;++tmpNonPackedIndex) {
            final int nonPackedIndex=tmpNonPackedIndex;
            TestExecutorService.submitTest(()->{
            Assertions.assertEquals(nonPackedIndex/64,BitSetUtil.convertToPackedIndex(nonPackedIndex));
            });
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testconvertToPackedIndex_int");

    }
    @Order(11)
    @Test
    public void testconvertToNonPackedIndex_int() {
        for(int tmpPackedIndex=0;tmpPackedIndex<=10;++tmpPackedIndex) {
            final int packedIndex=tmpPackedIndex;
            TestExecutorService.submitTest(()->{
                Assertions.assertEquals(packedIndex*64,BitSetUtil.convertToNonPackedIndex(packedIndex));

            });
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testconvertToNonPackedIndex_int");

    }
    @Order(130)
    @Test
    public void testgetPackedCapacity_int() {
        for(int tmpNonPackedCapacity=0;tmpNonPackedCapacity<=129;++tmpNonPackedCapacity) {
            final int nonPackedCapacity=tmpNonPackedCapacity;
            TestExecutorService.submitTest(()->{
                Assertions.assertEquals((nonPackedCapacity-1>>6)+1,BitSetUtil.getPackedCapacity(nonPackedCapacity));
            });
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testgetPackedCapacity_int");

    }
   
    
    @Order(640)
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
    @Order(640)
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
    @Order(640)
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
   
    
    @Order(1459680)
    @Test
    public void testuncheckedDstAlignedCopy_longArrayintlongArrayintint() {
        final var srcArr=generateTestArr(640,new Random(0));
        for(int tmpSrcOffset=0;tmpSrcOffset<640;++tmpSrcOffset) {
            final int srcOffset=tmpSrcOffset;
            for(int tmpDstOffset=0;tmpDstOffset<640;tmpDstOffset+=64) {
                final int dstOffset=tmpDstOffset;
                final int lengthBound=640-Math.max(srcOffset,dstOffset);
                for(int tmpLength=1;tmpLength<lengthBound;++tmpLength) {
                    final int length=tmpLength;
                    TestExecutorService.submitTest(()->{
                        final long[] dstArr;
                        BitSetUtil.uncheckedDstAlignedCopy(srcArr,srcOffset,dstArr=new long[10],dstOffset,length);
                        for(int s=srcOffset,d=dstOffset,sb=srcOffset+length;s<sb;++s,++d) {
                            Assertions.assertEquals(BitSetUtil.getFromPackedArr(srcArr,s),BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                        for(int d=0;d<dstOffset;++d) {
                            Assertions.assertFalse(BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                        for(int d=dstOffset+length;d<640;++d) {
                            Assertions.assertFalse(BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testuncheckedDstAlignedCopy_longArrayintlongArrayintint");
    }
    @Order(1459680)
    @Test
    public void testuncheckedSrcAlignedCopy_longArrayintlongArrayintint() {
        final var srcArr=generateTestArr(640,new Random(0));
        for(int tmpDstOffset=0;tmpDstOffset<640;++tmpDstOffset) {
            final int dstOffset=tmpDstOffset;
            for(int tmpSrcOffset=0;tmpSrcOffset<640;tmpSrcOffset+=64) {
                final int srcOffset=tmpSrcOffset;
                final int lengthBound=640-Math.max(srcOffset,dstOffset);
                for(int tmpLength=1;tmpLength<lengthBound;++tmpLength) {
                    final int length=tmpLength;
                    TestExecutorService.submitTest(()->{
                        final long[] dstArr;
                        BitSetUtil.uncheckedSrcAlignedCopy(srcArr,srcOffset,dstArr=new long[10],dstOffset,length);
                        for(int s=srcOffset,d=dstOffset,sb=srcOffset+length;s<sb;++s,++d) {
                            Assertions.assertEquals(BitSetUtil.getFromPackedArr(srcArr,s),BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                        for(int d=0;d<dstOffset;++d) {
                            Assertions.assertFalse(BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                        for(int d=dstOffset+length;d<640;++d) {
                            Assertions.assertFalse(BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testuncheckedSrcAlignedCopy_longArrayintlongArrayintint");
    }
    @Order(24540)
    @Test
    public void testuncheckedAlignedCopy_longArrayintlongArrayintint() {
        final var srcArr=generateTestArr(640,new Random(0));
        for(int tmpSrcOffset=0;tmpSrcOffset<640;tmpSrcOffset+=64) {
            final int srcOffset=tmpSrcOffset;
            for(int tmpDstOffset=0;tmpDstOffset<640;tmpDstOffset+=64) {
                final int dstOffset=tmpDstOffset;
                final int lengthBound=640-Math.max(srcOffset,dstOffset);
                for(int tmpLength=1;tmpLength<lengthBound;++tmpLength) {
                    final int length=tmpLength;
                    TestExecutorService.submitTest(()->{
                        final long[] dstArr;
                        BitSetUtil.uncheckedAlignedCopy(srcArr,srcOffset,dstArr=new long[10],dstOffset,length);
                        for(int s=srcOffset,d=dstOffset,sb=srcOffset+length;s<sb;++s,++d) {
                            Assertions.assertEquals(BitSetUtil.getFromPackedArr(srcArr,s),BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                        for(int d=0;d<dstOffset;++d) {
                            Assertions.assertFalse(BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                        for(int d=dstOffset+length;d<640;++d) {
                            Assertions.assertFalse(BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testuncheckedAlignedCopy_longArrayintlongArrayintint");
    }
    @Order(87586240)
    @Test
    public void testsemicheckedCopy_longArrayintlongArrayintint() {
        final var srcArr=generateTestArr(640,new Random(0));
        for(int tmpSrcOffset=0;tmpSrcOffset<640;++tmpSrcOffset) {
            final int srcOffset=tmpSrcOffset;
            for(int tmpDstOffset=0;tmpDstOffset<640;++tmpDstOffset) {
                final int dstOffset=tmpDstOffset;
                final int lengthBound=640-Math.max(srcOffset,dstOffset);
                for(int tmpLength=0;tmpLength<lengthBound;++tmpLength) {
                    final int length=tmpLength;
                    TestExecutorService.submitTest(()->{
                        final long[] dstArr;
                        BitSetUtil.semicheckedCopy(srcArr,srcOffset,dstArr=new long[10],dstOffset,length);
                        for(int s=srcOffset,d=dstOffset,sb=srcOffset+length;s<sb;++s,++d) {
                            Assertions.assertEquals(BitSetUtil.getFromPackedArr(srcArr,s),BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                        for(int d=0;d<dstOffset;++d) {
                            Assertions.assertFalse(BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                        for(int d=dstOffset+length;d<640;++d) {
                            Assertions.assertFalse(BitSetUtil.getFromPackedArr(dstArr,d));
                        }
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testsemicheckedCopy_longArrayintlongArrayintint");
    }
    @Order(100)
    @Test
    public void testflip_long() {
        Random rand=new Random(0);
        for(int i=0;i<100;++i) {
            long origWord=rand.nextLong();
            TestExecutorService.submitTest(()->{
                Assertions.assertEquals(~origWord,BitSetUtil.flip(origWord));
            });
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testflip_long");
    }
    @Order(13000)
    @Test
    public void testpartialWordShiftDown_longint() {
        Random rand=new Random(0);
        for(int i=0;i<100;++i) {
            long word=rand.nextLong();
            for(int tmpShift=0;tmpShift<=129;++tmpShift) {
                final int shift=tmpShift;
                TestExecutorService.submitTest(()->{
                    final long wordCopy=word;
                    long result=BitSetUtil.partialWordShiftDown(wordCopy,shift);
                    int adjustedShift=shift&63;
                    for(int j=0;j<adjustedShift;++j) {
                        Assertions.assertEquals(wordCopy>>>j&1,result>>>j&1);
                    }
                    for(int j=adjustedShift;j<63;++j) {
                        Assertions.assertEquals(wordCopy>>>j+1&1,result>>>j&1);
                    }
                    Assertions.assertEquals(0,result>>>63); 
                });
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testpartialWordShiftDown_longint");
    }
    @Order(100)
    @Test
    public void testgetWordFlipper_boolean() {
        Random rand=new Random(0);
        final var falseFlipper=BitSetUtil.getWordFlipper(false);
        final var trueFlipper=BitSetUtil.getWordFlipper(true);
        for(int i=0;i<100;++i) {
            long word=rand.nextLong();
            TestExecutorService.submitTest(()->{
              Assertions.assertEquals(~word,falseFlipper.applyAsLong(word));
              Assertions.assertEquals(word,trueFlipper.applyAsLong(word));
            });
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testgetWordFlipper_boolean");
    }
    @Order(100)
    @Test
    public void testprettyPrintword_long() {
        Random rand=new Random(0);
        for(int i=0;i<100;++i) {
            long word=rand.nextLong();
            TestExecutorService.submitTest(()->{
              String result=BitSetUtil.prettyPrintWord(word);
              for(int j=0;j<64;++j) {
                  Assertions.assertEquals((char)((word>>>j&1)+48),result.charAt(j));
              }              
            });
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testprettyPrintword_long");
    }
    @Test
    @Order(13860)
    public void testsrcUnalignedPullDown_longArrayintintint() {
        long[] testArr=generateTestArr(640,new Random(0));
        for(int tmpDstWordOffset=0;tmpDstWordOffset<10;++tmpDstWordOffset) {
            final int dstWordOffset=tmpDstWordOffset;
            for(int tmpSrcOffset=(dstWordOffset<<6)+1;tmpSrcOffset<640;++tmpSrcOffset) {
                if((tmpSrcOffset&63)==0) {
                    continue;
                }
                final int srcOffset=tmpSrcOffset;
                for(int tmpSrcWordBound=srcOffset>>6;tmpSrcWordBound<10;++tmpSrcWordBound) {
                    final int srcWordBound=tmpSrcWordBound;
                    TestExecutorService.submitTest(()->{
                        long[] arrCopy=Arrays.copyOf(testArr,testArr.length);
                        BitSetUtil.srcUnalignedPullDown(arrCopy,dstWordOffset,srcOffset,srcWordBound);
                        for(int i=0;i<dstWordOffset;++i) {
                            Assertions.assertEquals(testArr[i],arrCopy[i]);
                        }
                        for(int d=dstWordOffset<<6,s=srcOffset,sb=(srcWordBound+1<<6)-(s&63);s<sb;++s,++d) {
                          Assertions.assertEquals(BitSetUtil.getFromPackedArr(testArr,s),BitSetUtil.getFromPackedArr(arrCopy,d));
                        }
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testsrcUnalignedPullDown_longArrayintintint");
    }
    @Test
    @Order(772695)
    public void testdstUnalignedPullDown_longArrayintintint() {
        long[] testArr=generateTestArr(640,new Random(0));
        for(int tmpDstOffset=1;tmpDstOffset<640;++tmpDstOffset) {
            if((tmpDstOffset&63)==0) {
                continue;
            }
            final int dstOffset=tmpDstOffset;
            for(int tmpSrcOffset=dstOffset+1;tmpSrcOffset<640;++tmpSrcOffset) {
                final int srcOffset=tmpSrcOffset;
                for(int tmpSrcWordBound=srcOffset>>6;tmpSrcWordBound<10;++tmpSrcWordBound) {
                    final int srcWordBound=tmpSrcWordBound;
                    TestExecutorService.submitTest(()->{
                        long[] arrCopy=Arrays.copyOf(testArr,testArr.length);
                        BitSetUtil.dstUnalignedPullDown(arrCopy,dstOffset,srcOffset,srcWordBound);
                        for(int i=0;i<dstOffset;++i) {
                            Assertions.assertEquals(BitSetUtil.getFromPackedArr(testArr,i),BitSetUtil.getFromPackedArr(arrCopy,i));
                        }
                        int sb;
                        if((srcOffset&63)==0) {
                            sb=(srcWordBound+1<<6)-(dstOffset&63);
                        }else {
                            sb=(srcWordBound+1<<6)-(dstOffset-srcOffset&63);
                        }
                        for(int s=srcOffset,d=dstOffset;s<sb;++s,++d) {
                          Assertions.assertEquals(BitSetUtil.getFromPackedArr(testArr,s),BitSetUtil.getFromPackedArr(arrCopy,d));

                        }
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testdstUnalignedPullDown_longArrayintintint");
    }
    
    @Test
    @Order(1280)
    public void testuncheckedcontains_longArrayintLongUnaryOperator() {
        for(int v=0;v<=1;++v) {
            final boolean val=v!=0;
            final var wordFlipper=BitSetUtil.getWordFlipper(val);
            for(int tmpBound=1;tmpBound<=640;++tmpBound) {
                final int bound=tmpBound;
                TestExecutorService.submitTest(()->{
                    long[] arr=new long[BitSetUtil.getPackedCapacity(bound)];
                    if(!val) {
                        for(int i=arr.length;--i>=0;) {
                            arr[i]=-1L;
                        }
                    }
                    Assertions.assertFalse(BitSetUtil.uncheckedcontains(arr,bound-1,wordFlipper));
                    for(int index=0;index<bound;++index) {
                        BitSetUtil.storeInPackedArr(arr,index,val);
                        Assertions.assertTrue(BitSetUtil.uncheckedcontains(arr,bound-1,wordFlipper));
                        BitSetUtil.storeInPackedArr(arr,index,!val);
                    }
                });
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testuncheckedcontains_longArrayintLongUnaryOperator");

    }
    @Test
    @Order(85760)
    public void testuncheckedcontains_longArrayintintLongUnaryOperator() {
        for(int v=0;v<=1;++v) {
            final boolean val=v!=0;
            final var wordFlipper=BitSetUtil.getWordFlipper(val);
            for(int tmpSize=1;tmpSize<=640;++tmpSize) {
                final int size=tmpSize;
                for(int tmpOffset=0;tmpOffset<=66;++tmpOffset) {
                    final int offset=tmpOffset;
                    TestExecutorService.submitTest(()->{
                        long[] arr=new long[BitSetUtil.getPackedCapacity(size+offset)];
                        if(!val) {
                            for(int i=arr.length;--i>=0;) {
                                arr[i]=-1L;
                            }
                        }
                        Assertions.assertFalse(BitSetUtil.uncheckedcontains(arr,offset,size,wordFlipper));
                        for(int index=offset,bound=offset+size;index<bound;++index) {
                            BitSetUtil.storeInPackedArr(arr,index,val);
                            Assertions.assertTrue(BitSetUtil.uncheckedcontains(arr,offset,size,wordFlipper));
                            BitSetUtil.storeInPackedArr(arr,index,!val);
                        }
                    });
                }
               
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testuncheckedcontains_longArrayintintLongUnaryOperator");

    }
    
}
