package omni.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.function.LongUnaryOperator;

/**
 * TODO use this in a bit-sequence class and a bit-deq class
 *
 * @author lyonste
 *
 */
public interface BitSetUtil{

    public static int convertToPackedIndex(int nonPackedIndex) {
      return nonPackedIndex>>6;
    }
    public static int convertToNonPackedIndex(int packedIndex) {
      return packedIndex<<6;
    }
    public static int getPackedCapacity(int nonPackedCapacity) {
      return convertToPackedIndex(nonPackedCapacity-1)+1;
    }
    
    public static boolean getFromPackedArr(long[] words,int nonPackedIndex) {
      return (words[convertToPackedIndex(nonPackedIndex)]>>>nonPackedIndex&1)!=0;
    }

    
    
    public static void uncheckedAlignedSelfCopy(long[] src,int dstOffset,int srcOffset,int length) {
      //TODO
    }
    public static void uncheckedSrcAlignedSelfCopy(long[] src,int dstOffset,int srcOffset,int length) {
      //TODO
    }
    public static void uncheckedDstAlignedSelfCopy(long[] src,int dstOffset,int srcOffset,int length) {
      //TODO
    }
    public static void uncheckedSelfCopy(long[] src,int dstOffset,int srcOffset,int length) {
      int srcWordOffset;
      int srcWordBound;
      int dstWordOffset=dstOffset>>6;
      int dstBound;
      int dstWordBound=(dstBound=dstOffset+length)-1>>6;
      long mask;
      int shift;
      if((srcWordOffset=srcOffset>>6)==(srcWordBound=srcOffset+length-1>>6)) {
          long srcWord=src[srcWordOffset];
          long dstWord;
          if(dstWordOffset==dstWordBound) {
              dstWord=src[dstWordOffset]&~(mask=-1L>>>-length<<dstOffset);
              if((shift=(dstOffset&63)-(srcOffset&63))<0) {
                src[dstWordOffset]=srcWord>>>-shift&mask | dstWord;
              }else {
                src[dstWordOffset]=srcWord<<shift&mask | dstWord;
              }
          }else {
            src[dstWordOffset]=srcWord<<(shift=dstOffset-srcOffset)&(mask=-1L<<dstOffset) | src[dstWordOffset]&~mask;
            src[++dstWordOffset]=srcWord>>>-shift&~(mask=-1L<<dstBound)|src[dstWordOffset]&mask;
          }
      }else {
          if(dstWordOffset==dstWordBound) {
            src[dstWordOffset]=src[dstWordOffset]&~(mask=-1L>>>-length<<dstOffset) | (src[srcWordOffset]>>>(shift=srcOffset-dstOffset)|src[srcWordOffset+1]<<-shift)&mask;
          }else {
              long srcWord=src[srcWordOffset];
              switch(Integer.signum(shift=(dstOffset&63)-(srcOffset&63))) {
              case 0:{
                src[dstWordOffset]=src[dstWordOffset]&~(mask=-1L<<dstOffset)|srcWord&mask;
                  ArrCopy.semicheckedSelfCopy(src,++dstWordOffset,srcWordOffset+1,dstWordBound-dstWordOffset);
                  src[dstWordBound]=src[dstWordBound]&~(mask=-1L>>>-dstBound) | src[srcWordBound]&mask;
                  break;
              }
              case 1:{
                src[dstWordOffset]=src[dstWordOffset]&~(mask=-1L<<dstOffset) | srcWord<<shift&mask;
                  while(++dstWordOffset<dstWordBound) {
                    src[dstWordOffset]=srcWord>>>-shift | (srcWord=src[++srcWordOffset])<<shift;
                  }
                  src[dstWordBound]=src[dstWordBound]&~(mask=-1L>>>-dstBound) |(srcWord>>>-shift|src[srcWordBound]<<shift)&mask;
                  break;
              }
              default:{
                src[dstWordOffset]=src[dstWordOffset]&~(mask=-1L<<dstOffset) | (srcWord>>>-shift|(srcWord=src[++srcWordOffset])<<shift)&mask ;
                while(++dstWordOffset<dstWordBound) {
                  src[dstWordOffset]=srcWord>>>-shift | (srcWord=src[++srcWordOffset])<<shift;
                }
                if(srcWordOffset<srcWordBound) {
                  src[dstWordBound]=src[dstWordBound]&~(mask=-1L>>>-dstBound) |(srcWord>>>-shift|src[srcWordBound]<<shift)&mask;
                }else {
                  src[dstWordBound]=src[dstWordBound]&~(mask=-1L>>>-dstBound) |srcWord>>>-shift&mask;
                }
              }
              }
          } 
      }
  }
    public static void semicheckedSelfCopy(long[] src,int dstOffset,int srcOffset,int length) {
      if(length!=0) {
        uncheckedSelfCopy(src,dstOffset,srcOffset,length);
      }
    }
    public static void uncheckedAlignedCopy(long[] src,int srcOffset,long[] dst,int dstOffset,int length) {
      ArrCopy.semicheckedCopy(src,srcOffset>>6,dst,dstOffset>>6,length>>6);
      if((length&63)!=0) {
        dst[dstOffset=dstOffset+length>>6]=dst[dstOffset]&-1L<<length | src[srcOffset+length>>6]&-1L>>>-length;
      }
    }
    public static void uncheckedSrcAlignedCopy(long[] src,int srcOffset,long[] dst,int dstOffset,int length) {
      if((dstOffset&63)==0) {
          uncheckedAlignedCopy(src,srcOffset,dst,dstOffset,length);
          return;
      }
      int srcWordOffset,dstWordOffset;
      long srcWord;
      int dstBound;
      int dstWordBound;
      final long srcEndMask=-1L>>>-length;
      if((dstWordOffset=dstOffset>>6)==(dstWordBound=(dstBound=dstOffset+length-1)>>6)) {
          dst[dstWordOffset]=dst[dstWordOffset]&~(srcEndMask<<dstOffset) | (src[srcOffset>>6]&srcEndMask)<<dstOffset;
      }else {
          dst[dstWordOffset]=dst[dstWordOffset]&-1L>>>-dstOffset|(srcWord=src[srcWordOffset=srcOffset>>6])<<dstOffset;
          while(++dstWordOffset<dstWordBound) {
            dst[dstWordOffset]=srcWord>>>-dstOffset|(srcWord=src[++srcWordOffset])<<dstOffset;
          }
          final long dstEndMask=-1L<<dstBound-1;
        //TODO this is potentially bugged
          if(++srcWordOffset==srcOffset+length-1>>6) {
              
              dst[dstWordOffset]=srcWord>>>-dstOffset|dst[dstWordOffset]&dstEndMask| (src[srcWordOffset]&srcEndMask)<<dstOffset;

          }else {
              dst[dstWordOffset]=(srcWord&srcEndMask)>>>-dstOffset|dst[dstWordOffset]&dstEndMask;
          }
      }
    }
    public static void uncheckedDstAlignedCopy(long[] src,int srcOffset,long[] dst,int dstOffset,int length) {
      if((srcOffset&63)==0) {
        uncheckedAlignedCopy(src,srcOffset,dst,dstOffset,length);
        return;
      }
      int srcWordOffset=srcOffset>>6,dstWordOffset=dstOffset>>6;
      long srcWord;
      if(length<=64) {
          srcWord=src[srcWordOffset]>>>srcOffset;
          if(srcWordOffset!=srcOffset+length-1>>6) {
              srcWord|=src[srcWordOffset+1]<<-srcOffset;
          }
          final long endMask;
          dst[dstWordOffset]=dst[dstWordOffset]&~(endMask=-1L>>>-length)|srcWord&endMask;
      }else {
          dst[dstWordOffset]=src[srcWordOffset]>>>srcOffset | (srcWord=src[++srcWordOffset])<<-srcOffset;
          final int dstWordBound=dstOffset+length-1>>6;
          while(++dstWordOffset<dstWordBound) {
              dst[dstWordOffset]=srcWord>>>srcOffset|(srcWord=src[++srcWordOffset])<<-srcOffset;
          }
          srcWord>>>=srcOffset;
          if(srcWordOffset!=srcOffset+length-1>>6) {
              srcWord|=src[srcWordOffset+1]<<-srcOffset;
          }
          final long endMask;
          dst[dstWordOffset]=dst[dstWordOffset]&~(endMask=-1L>>>-length)|srcWord&endMask;
      }
    }

    
    public static void uncheckedCopy(long[] src,int srcOffset,long[] dst,int dstOffset,int length) {
        int srcWordOffset;
        int srcWordBound;
        int dstWordOffset=dstOffset>>6;
        int dstBound;
        int dstWordBound=(dstBound=dstOffset+length)-1>>6;
        long mask;
        int shift;
        if((srcWordOffset=srcOffset>>6)==(srcWordBound=srcOffset+length-1>>6)) {
            long srcWord=src[srcWordOffset];
            long dstWord;
            if(dstWordOffset==dstWordBound) {
                dstWord=dst[dstWordOffset]&~(mask=-1L>>>-length<<dstOffset);
                if((shift=(dstOffset&63)-(srcOffset&63))<0) {
                    dst[dstWordOffset]=srcWord>>>-shift&mask | dstWord;
                }else {
                    dst[dstWordOffset]=srcWord<<shift&mask | dstWord;
                }
            }else {
                dst[dstWordOffset]=srcWord<<(shift=dstOffset-srcOffset)&(mask=-1L<<dstOffset) | dst[dstWordOffset]&~mask;
                dst[++dstWordOffset]=srcWord>>>-shift&~(mask=-1L<<dstBound)|dst[dstWordOffset]&mask;
            }
        }else {
            if(dstWordOffset==dstWordBound) {
                dst[dstWordOffset]=dst[dstWordOffset]&~(mask=-1L>>>-length<<dstOffset) | (src[srcWordOffset]>>>(shift=srcOffset-dstOffset)|src[srcWordOffset+1]<<-shift)&mask;
            }else {
                long srcWord=src[srcWordOffset];
                switch(Integer.signum(shift=(dstOffset&63)-(srcOffset&63))) {
                case 0:{
                    dst[dstWordOffset]=dst[dstWordOffset]&~(mask=-1L<<dstOffset)|srcWord&mask;
                    ArrCopy.semicheckedCopy(src,srcWordOffset+1,dst,++dstWordOffset,dstWordBound-dstWordOffset);
                    dst[dstWordBound]=dst[dstWordBound]&~(mask=-1L>>>-dstBound) | src[srcWordBound]&mask;
                    break;
                }
                case 1:{
                    dst[dstWordOffset]=dst[dstWordOffset]&~(mask=-1L<<dstOffset) | srcWord<<shift&mask;
                    while(++dstWordOffset<dstWordBound) {
                        dst[dstWordOffset]=srcWord>>>-shift | (srcWord=src[++srcWordOffset])<<shift;
                    }
                    dst[dstWordBound]=dst[dstWordBound]&~(mask=-1L>>>-dstBound) |(srcWord>>>-shift|src[srcWordBound]<<shift)&mask;
                    break;
                }
                default:{
                    dst[dstWordOffset]=dst[dstWordOffset]&~(mask=-1L<<dstOffset) | (srcWord>>>-shift|(srcWord=src[++srcWordOffset])<<shift)&mask ;
                  while(++dstWordOffset<dstWordBound) {
                      dst[dstWordOffset]=srcWord>>>-shift | (srcWord=src[++srcWordOffset])<<shift;
                  }
                  if(srcWordOffset<srcWordBound) {
                      dst[dstWordBound]=dst[dstWordBound]&~(mask=-1L>>>-dstBound) |(srcWord>>>-shift|src[srcWordBound]<<shift)&mask;
                  }else {
                      dst[dstWordBound]=dst[dstWordBound]&~(mask=-1L>>>-dstBound) |srcWord>>>-shift&mask;
                  }
                }
                }
            } 
        }
    }
    public static void semicheckedCopy(long[] src,int srcOffset,long[] dst,int dstOffset,int length) {
        if(length!=0) {
            uncheckedCopy(src,srcOffset,dst,dstOffset,length);
        }
    }
    public static void writeWordsSrcAligned(long[] words,int begin,int end,DataOutput dataOutput) throws IOException {
      for(int wordOffset=begin>>6,wordBound=end>>6;;++wordOffset) {
        if(wordOffset==wordBound) {
          writeFinalWord(words[wordOffset],end,dataOutput);
          return;
        }
        dataOutput.writeLong(words[wordOffset]);
      }
    }
    @Deprecated
    public static void writeWordsFragmentedAligned(long[] words,int head,int tail,DataOutput dataOutput) throws IOException{
      OmniArray.OfLong.writeArray(words,head>>6,words.length-1>>6,dataOutput);
      writeWordsSrcAligned(words,0,tail,dataOutput);
    }
    public static void writeWordsFragmentedUnaligned(long[] words,int head,int tail,DataOutput dataOutput) throws IOException{
      int wordOffset;
      var word=words[wordOffset=head>>6];
      for(int wordBound=words.length-1>>6;wordOffset!=wordBound;dataOutput.writeLong(word>>>head|(word=words[++wordOffset])<<-head)) {}
      wordOffset=0;
      for(int wordBound=tail>>6;wordOffset!=wordBound;dataOutput.writeLong(word>>>head|(word=words[wordOffset++])<<-head)) {}
      writeFinalWord(word>>>head,tail-(head&63),dataOutput);
    }
    
    
    public static void writeWordsSrcUnaligned(long[] words,int head,int tail,DataOutput dataOutput) throws IOException {
      int wordOffset;
      var word=words[wordOffset=head>>6];
      for(int wordBound=tail>>6;wordOffset!=wordBound;) {
          dataOutput.writeLong(word>>>head|(word=words[++wordOffset])<<-head);
      }
      writeFinalWord(word>>>head,tail-(head&63),dataOutput);
    }
    
    public static void readWords(long[] words,int end,DataInput dataInput) throws IOException{
      for(int wordOffset=0,wordBound=end>>6;;++wordOffset) {
        if(wordOffset==wordBound) {
          words[wordOffset]=readFinalWord(end,dataInput);
          return;
        }
        words[wordOffset]=dataInput.readLong();
      }
    }
    
    
    public static long shiftUpLeadingBits(long word,int head)
    {
        final long mask;
        return word<<1&(mask=-1L<<head)|word&~mask;
    }
    public static long shiftUpTrailingBits(long word,int cursor) {
        final long mask;
        return word<<1&(mask=-1L>>>-cursor-1) | word&~mask;
    }
    public static long shiftDownTrailingBits(long word,int tail) {
        final long mask;
        return word&(mask=-1L<<tail) | word>>>1&~mask;
    }
    public static long shiftDownLeadingBits(long word,int cursor) {
        final long mask;
        return word>>>1&(mask=-1L<<cursor)|word&~mask;
    }
    public static long shiftDownMiddleBits(long word,int cursor,int tail) {
        final long mask;
        return word>>>1&(mask=-1L<<cursor&-1L>>>-tail) | word&~mask;
    }
    public static long shiftDownEdgeBits(long word,int cursor,int tail) {
        final long mask;
        return word&(mask=-1L<<tail & -1L>>>-cursor) | word>>>1&~mask;
    }
    public static long shiftUpMiddleBits(long word,int head,int cursor)
    {
        final long mask;
        return word<<1&(mask=-1L<<head&-1L>>>-cursor-1) | word&~mask;
    }
    public static long shiftUpEdgeBits(long word,int cursor,int head) {
        final long mask;
        return word&(mask=-1L<<cursor+1&-1L>>>-head) | word<<1&~mask;
    }
    
    public static long combineWordWithTrailingBitOfNext(long currWord,long nextWord) {
        return currWord | nextWord<<-1;
    }
    public static long combineWordWithLeadingBitOfPrev(long currWord,long prevWord) {
        return currWord | prevWord>>>-1;
    }
    

    public static long pullDownLoop(long[] words,long word,int begin,int end){
        while(begin!=end){
            words[begin]=combineWordWithTrailingBitOfNext(word>>>1,word=words[++begin]);
        }
        return word;
    }
    public static long pullUpLoop(long[] words,long word,int begin,int end){
        while(end!=begin){
            words[end]=combineWordWithLeadingBitOfPrev(word<<1,word=words[--end]);
        }
        return word;
    }
    
    
    public static long readFinalWord(int end,DataInput dataInput) throws IOException{
      switch((end&63)>>3) {
      case 7:
        return dataInput.readLong();
      case 6:
        return (long)dataInput.readUnsignedByte()<<48|(long)dataInput.readUnsignedShort()<<32|dataInput.readInt();
      case 5:
        return (long)dataInput.readUnsignedShort()<<32|dataInput.readInt();
      case 4:
        return dataInput.readInt() | (long)dataInput.readUnsignedByte()<<32;
      case 3:
        return dataInput.readInt();
      case 2:
        return (long)dataInput.readUnsignedByte()<<16 | dataInput.readUnsignedShort();
      case 1:
        return dataInput.readUnsignedShort();
      default:
        return dataInput.readUnsignedByte();
      }
    }
    public static void writeFinalWord(long word,int end,DataOutput dataOutput) throws IOException {
      switch((end&63)>>3) {
      case 7:
        dataOutput.writeLong(word);
        return;
      case 6:
        dataOutput.writeByte((int)(word>>>48));
      case 5:
        dataOutput.writeShort((int)(word>>>32));
        dataOutput.writeInt((int)word);
        return;
      case 4:
        dataOutput.writeInt((int)word);
        dataOutput.writeByte((int)(word>>>32));
        return;
      case 3:
        dataOutput.writeInt((int)word);
        return;
      case 2:
        dataOutput.writeByte((int)(word>>>16));
      case 1:
        dataOutput.writeShort((int)word);
        return;
      default:
        dataOutput.writeByte((int)word);
      }
    }
    
    
    
    
    public static boolean getAndSwap(long[] words,int nonPackedIndex,boolean val) {
      final int packedIndex;
      final var mask=1L<<nonPackedIndex;
      final var word=words[packedIndex=convertToPackedIndex(nonPackedIndex)];
      if(val) {
        words[packedIndex]=word|mask;
      }else {
        words[packedIndex]=word&~mask;
      }
      return (word>>nonPackedIndex&1)!=0;
    }
    
    
    public static void storeInPackedArr(long[] words,int nonPackedIndex,boolean val) {
      final var packedIndex=convertToPackedIndex(nonPackedIndex);
      final var mask=1L<<nonPackedIndex;
      if(val) {
        words[packedIndex]|=mask;
      }else {
        words[packedIndex]&=~mask;
      }
    }
  
    public static long flip(long word) {
        return ~word;
    }
    
    public static long partialWordShiftDown(long word,int shift) {
        final long mask;
        return word & (mask=(1L << shift) - 1) | word >>> 1 & ~mask;
    }
    
    public static LongUnaryOperator getWordFlipper(boolean val) {
        if(val) {
            return LongUnaryOperator.identity();
        }else {
            return BitSetUtil::flip;
        }
    }
    
    /**
     * left = high bits
     * @param word
     * @return
     */
    public static String prettyPrintWord(long word) {
        var byteArr=new byte[64];
        for(int i=0;;) {
            byteArr[63-i]=(byte)(48+(word>>>i&1));
            if(++i==64) {
                return new String(byteArr,ToStringUtil.IOS8859CharSet);
            }
        }
    }

    
    public static void srcUnalignedPullDown(long[] words,int dstWordOffset,int srcOffset,int srcWordBound) {
        int srcWordOffset;
        for(var word=words[srcWordOffset=srcOffset>>6];;++dstWordOffset) {
            if(srcWordOffset==srcWordBound) {
                words[dstWordOffset]=word>>>srcOffset;
                return;
            }
            words[dstWordOffset]=word>>>srcOffset|(word=words[++srcWordOffset])<<-srcOffset;
        }
    }
    public static void dstUnalignedPullDown(long[] words,int dstOffset,int srcOffset,int srcWordBound) {
        int dstWordOffset=dstOffset>>6;
        int srcWordOffset=srcOffset>>6;
        int shift;
        long mask=(1L<<dstOffset)-1;
        switch(Integer.signum(shift=(dstOffset&63)-(srcOffset&63))) {
        case 0:{
            words[dstWordOffset]=words[dstWordOffset]&mask|words[srcWordOffset]&~mask;
            ArrCopy.semicheckedSelfCopy(words,dstWordOffset+1,srcWordOffset+1,srcWordBound-srcWordOffset);
            break;
        }
        case 1:{
            long srcWord;
            words[dstWordOffset]=words[dstWordOffset]&mask|(srcWord=words[srcWordOffset])<<shift&~mask;
            while(++srcWordOffset<=srcWordBound) {
                words[++dstWordOffset]=srcWord>>>-shift| (srcWord=words[srcWordOffset])<<shift;
            }
            words[dstWordOffset+1]=srcWord>>>-shift;
            break;
        }
        default:{
            long srcWord;
            if(srcWordOffset==srcWordBound) {
                words[dstWordOffset]=words[dstWordOffset]&mask|words[srcWordOffset]>>>-shift&~mask;
            }else {
                words[dstWordOffset]=words[dstWordOffset]&mask|(words[srcWordOffset]>>>-shift|(srcWord=words[++srcWordOffset])<<shift)&~mask;
                while(srcWordOffset<srcWordBound) {
                    words[++dstWordOffset]=srcWord>>>-shift | (srcWord=words[++srcWordOffset])<<shift;   
                }
            }
            break;
        }
        }
    }
    

    
    public static boolean uncheckedcontains(long[] words,int bound,LongUnaryOperator wordFlipper) {
        int wordBound=bound>>6;
        if(wordFlipper.applyAsLong(words[wordBound])<<-bound-1==0) {
          do {
              if(wordBound==0) {
                  return false;
              }
          }while(wordFlipper.applyAsLong(words[--wordBound])==0);
        }
        return true;
    }
    public static boolean uncheckedcontains(long[] words,int offset,int size,LongUnaryOperator wordFlipper) {
        int bound;
        int wordBound=(bound=offset+size-1)>>6;
        int wordOffset=offset>>6;
        if((offset&63)!=0) {
            if(wordOffset==wordBound) {
                return (wordFlipper.applyAsLong(words[wordBound])&(1L<<bound-offset+1)-1<<offset)!=0;
            }
            if(wordFlipper.applyAsLong(words[wordOffset++])>>>offset!=0) {
                return true;
            }
        }
        for(;wordOffset<wordBound;++wordOffset) {
            if(wordFlipper.applyAsLong(words[wordOffset])!=0) {
                return true;
            }
        }
        return wordFlipper.applyAsLong(words[wordBound])<< -bound - 1 != 0;
    }
    
   
    
    
    
    

}
