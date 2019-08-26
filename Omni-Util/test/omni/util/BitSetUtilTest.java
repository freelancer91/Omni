package omni.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private static final long[] BIT_PATTERN_WORDS=new long[] {
        0x0000000000000000L
       ,0x1111111111111111L
       ,0x2222222222222222L
       ,0x3333333333333333L
       ,0x4444444444444444L
       ,0x5555555555555555L
       ,0x6666666666666666L
       ,0x7777777777777777L
       ,0x8888888888888888L
       ,0x9999999999999999L
       ,0xaaaaaaaaaaaaaaaaL
       ,0xbbbbbbbbbbbbbbbbL
       ,0xccccccccccccccccL
       ,0xddddddddddddddddL
       ,0xeeeeeeeeeeeeeeeeL
       ,0xffffffffffffffffL
     };

    
    @Test
    public void testReadAndWrite() {
      int numBatches=TestExecutorService.getNumWorkers();
      int batchSize=Math.max(1,6400/numBatches);
      for(int batchCount=0;batchCount<numBatches;) {
        final int batchOffset=batchCount*batchSize;
        final int batchBound;
        if(++batchCount==numBatches) {
          batchBound=6400;
        }else {
          batchBound=batchOffset+batchSize;
        }
        TestExecutorService.submitTest(()->{
          long[] srcArr=generateTestArr(6400,new Random(0));
          long[] dstArr=new long[100];
          for(int begin=batchOffset;begin<batchBound;++begin) {
            for(int end=begin;end<6400;++end) {
              final int length=end-begin;
              byte[] buffer=null;
              final var baos=new ByteArrayOutputStream(((length)>>3)+1);
              try(
                 
                  final var oos=new ObjectOutputStream(baos);
              ){
                
                if((begin&63)==0) {
                  BitSetUtil.writeWordsSrcAligned(srcArr,begin,end,oos);
                }else {
                  BitSetUtil.writeWordsSrcUnaligned(srcArr,begin,end,oos);
                }
               
               
              }catch(IOException e){
                Assertions.fail(e);
              }
              buffer=baos.toByteArray();
              try(
                  final var bais=new ByteArrayInputStream(buffer);
                  final var ois=new ObjectInputStream(bais);      
              ){
                BitSetUtil.readWords(dstArr,length,ois);
              }catch(IOException e){
                Assertions.fail(e);
              }
              for(int i=begin,dstOffset=0;i<=end;++i,++dstOffset) {
                Assertions.assertEquals(getFromPackedArr(srcArr,i),getFromPackedArr(dstArr,dstOffset));
              }
            }
          }
        });
      }
      TestExecutorService.completeAllTests("BitSetUtilTest.testReadAndWrite");
      
      
    }

   
    
    @Order(640)
    @Test
    public void testgetFromPackedArr_longArrayint() {
        int nonPackedIndex,currFibIndex;
        for(nonPackedIndex=0,currFibIndex=0;nonPackedIndex<640;++nonPackedIndex) {
            final var val=getFromPackedArr(FIB_WORDS,nonPackedIndex);
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
            Assertions.assertFalse(getFromPackedArr(FIB_WORDS,nonPackedIndex));
        }
    }
   

    
    private static void storeInPackedArr(long[] words,int nonPackedIndex,boolean val) {
      final var packedIndex=(nonPackedIndex)>>6;
      final var mask=1L<<nonPackedIndex;
      if(val) {
        words[packedIndex]|=mask;
      }else {
        words[packedIndex]&=~mask;
      }
    }
    @Order(-1)
    @Test
    public void testshiftDownLeadingBits_longint() {
      for(final var testWord:BIT_PATTERN_WORDS) {
        for(int i=0;i<128;++i) {
          final int finalI=i;
          long result=BitSetUtil.shiftDownLeadingBits(testWord,i);
          int bound=i&63;
          for(int j=0;j<bound;++j) {
            final int finalIndex=j;
            Assertions.assertEquals((testWord>>>j)&1,(result>>>j)&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
          }
          for(int j=bound;j<63;++j) {
            final int finalIndex=j;
            Assertions.assertEquals((testWord>>>(j+1))&1,(result>>>(j))&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
          }
          Assertions.assertEquals(0,(result>>>63)&1,()->"Failed on final index i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
        }
      }
    }
    @Order(-1)
    @Test
    public void testshiftUpLeadingBits_longint() {
      for(final var testWord:BIT_PATTERN_WORDS) {
        for(int i=0;i<128;++i) {
          final int finalI=i;
          long result=BitSetUtil.shiftUpLeadingBits(testWord,i);
          int bound=i&63;
          for(int j=0;j<bound;++j) {
            final int finalIndex=j;
            Assertions.assertEquals((testWord>>>j)&1,(result>>>j)&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
          }
          for(int j=bound+1;j<64;++j) {
            final int finalIndex=j;
            Assertions.assertEquals((testWord>>>(j-1))&1,(result>>>j)&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
          }
        }
      }
    }
    @Order(-1)
    @Test
    public void testshiftDownTrailingBits_longint() {
      for(final var testWord:BIT_PATTERN_WORDS) {
        for(int i=0;i<128;++i) {
          final int finalI=i;
          long result=BitSetUtil.shiftDownTrailingBits(testWord,i);
          int bound=i&63;
          for(int j=0;j<bound;++j) {
            final int finalIndex=j;
            Assertions.assertEquals((testWord>>>(j+1))&1,(result>>>(j))&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
          }
          for(int j=bound+1;j<64;++j) {
            final int finalIndex=j;
            Assertions.assertEquals((testWord>>>(j))&1,(result>>>(j))&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
          }
        }
      }
    }
    @Order(-1)
    @Test
    public void testshiftUpTrailingBits_longint() {
      for(final var testWord:BIT_PATTERN_WORDS) {
        for(int i=0;i<128;++i) {
          final int finalI=i;
          long result=BitSetUtil.shiftUpTrailingBits(testWord,i);
          int bound=i&63;
          Assertions.assertEquals(0,(result)&1,()->"Failed on firstIndex index i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));

          for(int j=1;j<=bound;++j) {
            final int finalIndex=j;
            Assertions.assertEquals((testWord>>>(j-1))&1,(result>>>(j))&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
          }
          for(int j=bound+1;j<64;++j) {
            final int finalIndex=j;
            Assertions.assertEquals((testWord>>>(j))&1,(result>>>(j))&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
          }
        }
      }
    }
    @Order(-1)
    @Test
    public void testshiftDownMiddleBits_longintint() {
      for(final var testWord:BIT_PATTERN_WORDS) {
        for(int i=1;i<63;++i) {
          final int finalI=i;
          for(int j=0;j<=i;++j) {
            final int finalJ=j;
            final long result=BitSetUtil.shiftDownMiddleBits(testWord,j,i);
            for(int k=0;k<i;++k) {
              final int finalIndex=k;
              Assertions.assertEquals((testWord>>>k)&1,(result>>>k)&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; j="+finalJ+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
            }
            for(int k=i;k<j;++k) {
              final int finalIndex=k;
              Assertions.assertEquals((testWord>>>(k+1))&1,(result>>>k)&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; j="+finalJ+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
              //TODO
            }
          
           

            //Assertions.assertEquals(0,(result>>>j)&1);
//            for(int k=j+1;k<64;++k) {
//              final int finalIndex=k;
//              Assertions.assertEquals((testWord>>>k)&1,(result>>>k)&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; j="+finalJ+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
//
//              //TODO
//            }
            
          }
          
//          long result=BitSetUtil.shiftUpTrailingBits(testWord,i);
//          int bound=i&63;
//          Assertions.assertEquals(0,(result)&1,()->"Failed on firstIndex index i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
//
//          for(int j=1;j<=bound;++j) {
//            final int finalIndex=j;
//            Assertions.assertEquals((testWord>>>(j-1))&1,(result>>>(j))&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
//          }
//          for(int j=bound+1;j<64;++j) {
//            final int finalIndex=j;
//            Assertions.assertEquals((testWord>>>(j))&1,(result>>>(j))&1,()->"Failed at index "+finalIndex+"; i="+finalI+"; testWord="+BitSetUtil.prettyPrintWord(testWord)+"; result="+BitSetUtil.prettyPrintWord(result));
//          }
        }
      }
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
    public void testprettyPrintword_int() {
        Random rand=new Random(0);
        for(int i=0;i<100;++i) {
            int word=rand.nextInt();
            TestExecutorService.submitTest(()->{
              String result=BitSetUtil.prettyPrintWord(word);
              int bufferOffset=35;
              for(int j=0;;) {
                Assertions.assertEquals((char)(48+(word>>>j&1)),result.charAt(--bufferOffset));
                if(++j==32) {
                    break;
                }
                if((j&7)==0) {
                  Assertions.assertEquals('_',result.charAt(--bufferOffset));
                }
              }             
            });
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testprettyPrintword_int");
    }
    @Order(100)
    @Test
    public void testprettyPrintword_short() {
        Random rand=new Random(0);
        for(int i=0;i<100;++i) {
            short word=(short)rand.nextInt();
            TestExecutorService.submitTest(()->{
              String result=BitSetUtil.prettyPrintWord(word);
              int bufferOffset=17;
              for(int j=0;;) {
                Assertions.assertEquals((char)(48+(word>>>j&1)),result.charAt(--bufferOffset));
                if(++j==16) {
                    break;
                }
                if((j&7)==0) {
                  Assertions.assertEquals('_',result.charAt(--bufferOffset));
                }
              }             
            });
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testprettyPrintword_short");
    }
    @Order(100)
    @Test
    public void testprettyPrintword_char() {
        Random rand=new Random(0);
        for(int i=0;i<100;++i) {
          char word=(char)rand.nextInt();
            TestExecutorService.submitTest(()->{
              String result=BitSetUtil.prettyPrintWord(word);
              int bufferOffset=17;
              for(int j=0;;) {
                Assertions.assertEquals((char)(48+(word>>>j&1)),result.charAt(--bufferOffset));
                if(++j==16) {
                    break;
                }
                if((j&7)==0) {
                  Assertions.assertEquals('_',result.charAt(--bufferOffset));
                }
              }             
            });
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testprettyPrintword_char");
    }
    @Order(100)
    @Test
    public void testprettyPrintword_byte() {
        Random rand=new Random(0);
        for(int i=0;i<100;++i) {
          byte word=(byte)rand.nextInt();
            TestExecutorService.submitTest(()->{
              String result=BitSetUtil.prettyPrintWord(word);
              for(int j=0;;) {
                Assertions.assertEquals((char)(48+(word>>>j&1)),result.charAt(7-j));
                if(++j==8) {
                    break;
                }
              }             
            });
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testprettyPrintword_byte");
    }
    @Order(100)
    @Test
    public void testprettyPrintword_long() {
        Random rand=new Random(0);
        for(int i=0;i<100;++i) {
            long word=rand.nextLong();
            TestExecutorService.submitTest(()->{
              String result=BitSetUtil.prettyPrintWord(word);
              int bufferOffset=71;
              for(int j=0;;) {
                Assertions.assertEquals((char)(48+(word>>>j&1)),result.charAt(--bufferOffset));
                if(++j==64) {
                    break;
                }
                if((j&7)==0) {
                  Assertions.assertEquals('_',result.charAt(--bufferOffset));
                }
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
                          Assertions.assertEquals(getFromPackedArr(testArr,s),getFromPackedArr(arrCopy,d));
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
                            Assertions.assertEquals(getFromPackedArr(testArr,i),getFromPackedArr(arrCopy,i));
                        }
                        int sb;
                        if((srcOffset&63)==0) {
                            sb=(srcWordBound+1<<6)-(dstOffset&63);
                        }else {
                            sb=(srcWordBound+1<<6)-(dstOffset-srcOffset&63);
                        }
                        for(int s=srcOffset,d=dstOffset;s<sb;++s,++d) {
                          Assertions.assertEquals(getFromPackedArr(testArr,s),getFromPackedArr(arrCopy,d));

                        }
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testdstUnalignedPullDown_longArrayintintint");
    }
    private static boolean getFromPackedArr(long[] words,int nonPackedIndex) {
      return (words[(nonPackedIndex)>>6]>>>nonPackedIndex&1)!=0;
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
                    long[] arr=new long[(((bound)-1)>>6)+1];
                    if(!val) {
                        for(int i=arr.length;--i>=0;) {
                            arr[i]=-1L;
                        }
                    }
                    Assertions.assertFalse(BitSetUtil.uncheckedcontains(arr,bound-1,wordFlipper));
                    for(int index=0;index<bound;++index) {
                        storeInPackedArr(arr,index,val);
                        Assertions.assertTrue(BitSetUtil.uncheckedcontains(arr,bound-1,wordFlipper));
                        storeInPackedArr(arr,index,!val);
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
                        long[] arr=new long[(((size+offset)-1)>>6)+1];
                        if(!val) {
                            for(int i=arr.length;--i>=0;) {
                                arr[i]=-1L;
                            }
                        }
                        Assertions.assertFalse(BitSetUtil.uncheckedcontains(arr,offset,size,wordFlipper));
                        for(int index=offset,bound=offset+size;index<bound;++index) {
                            storeInPackedArr(arr,index,val);
                            Assertions.assertTrue(BitSetUtil.uncheckedcontains(arr,offset,size,wordFlipper));
                            storeInPackedArr(arr,index,!val);
                        }
                    });
                }
               
            }
        }
        TestExecutorService.completeAllTests("BitSetUtilTest.testuncheckedcontains_longArrayintintLongUnaryOperator");

    }
    
}
