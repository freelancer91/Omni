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


    
    

    



    public static void writeWordsSrcAligned(long[] words,int begin,int end,DataOutput dataOutput) throws IOException {
      for(int wordOffset=begin>>6,wordBound=end>>6;;++wordOffset) {
        if(wordOffset==wordBound) {
          writeFinalWord(words[wordOffset],end,dataOutput);
          return;
        }
        dataOutput.writeLong(words[wordOffset]);
      }
    }
    

    public static void writeWordsSrcUnaligned(long[] words,int head,int tail,DataOutput dataOutput) throws IOException {
      int headOffset;
      var word=words[headOffset=head >> 6];
      int size;
      for(size=tail-head;size >= 64;size-=64){
        dataOutput.writeLong(word >>> head | (word=words[++headOffset]) << -head);
      }
      if(headOffset == tail >> 6){
          BitSetUtil.writeFinalWord(word >>> head,size,dataOutput);
      }else{
          BitSetUtil.writeFinalWord(word >>> head | words[headOffset + 1] << -head,size,dataOutput);
      }
    }
    
    public static void readWords(long[] words,int end,DataInput dataInput) throws IOException{
      for(int wordOffset=0,wordBound=end>>6;;++wordOffset) {
        if(wordOffset==wordBound) {
          var tmpWord=readFinalWord(end,dataInput);
          words[wordOffset]=tmpWord;
          return;
        }
        var tmpWord=dataInput.readLong();
        words[wordOffset]=tmpWord;
      }
    }
    
    
    public static long shiftUpLeadingBits(long word,int shiftInclusiveLo)
    {
        final long mask;
        return word<<1&(mask=-1L<<shiftInclusiveLo)|word&~mask;
    }
    public static long shiftUpTrailingBits(long word,int shiftInclusiveHi) {
        final long mask;
        return word<<1&(mask=-1L>>>-shiftInclusiveHi-1) | word&~mask;
    }
    public static long shiftDownTrailingBits(long word,int shiftExclusiveHi) {
        final long mask;
        return word&(mask=-1L<<shiftExclusiveHi) | word>>>1&~mask;
    }
    public static long shiftDownLeadingBits(long word,int shiftInclusiveLo) {
        final long mask;
        return word>>>1&(mask=-1L<<shiftInclusiveLo)|word&~mask;
    }
    public static long shiftDownMiddleBits(long word,int shiftInclusiveLo,int shiftInclusiveHi) {
        final long mask;
        return word>>>1&(mask=-1L<<shiftInclusiveLo&-1L>>>-shiftInclusiveHi) | word&~mask;
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
    
    
    private static long readFinalWord(int end,DataInput dataInput) throws IOException{
      switch((end&63)>>3) {
      case 7:
        return dataInput.readLong();
      case 6:
        return ((long)dataInput.readUnsignedByte()<<48)|(((long)dataInput.readUnsignedShort()<<32))|(((long)dataInput.readInt())&0xffffffffL);
      case 5:
        return ((long)dataInput.readUnsignedShort()<<32)|(((long)dataInput.readInt())&0xffffffffL);
      case 4:
        return (((long)dataInput.readInt())&0xffffffffL) | ((long)dataInput.readUnsignedByte()<<32);
      case 3:
        return dataInput.readInt();
      case 2:
        return ((long)dataInput.readUnsignedByte()<<16) | (((long)dataInput.readUnsignedShort()));
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
    
    

    public static long flip(long word) {
      return ~word;
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
        var byteArr=new byte[71];
        int bufferOffset=71;
        for(int i=0;;) {
            byteArr[--bufferOffset]=(byte)(48+(word>>>i&1));
            if(++i==64) {
                return new String(byteArr,ToStringUtil.IOS8859CharSet);
            }
            if((i&7)==0) {
              byteArr[--bufferOffset]='_';
            }
        }
    }
    
    public static String prettyPrintWord(int word) {
      var byteArr=new byte[35];
      int bufferOffset=35;
      for(int i=0;;) {
          byteArr[--bufferOffset]=(byte)(48+(word>>>i&1));
          if(++i==32) {
              return new String(byteArr,ToStringUtil.IOS8859CharSet);
          }
          if((i&7)==0) {
            byteArr[--bufferOffset]='_';
          }
      }
    }
    public static String prettyPrintWord(short word) {
      var byteArr=new byte[17];
      int bufferOffset=17;
      for(int i=0;;) {
          byteArr[--bufferOffset]=(byte)(48+(word>>>i&1));
          if(++i==16) {
              return new String(byteArr,ToStringUtil.IOS8859CharSet);
          }
          if((i&7)==0) {
            byteArr[--bufferOffset]='_';
          }
      }
    }
    public static String prettyPrintWord(char word) {
      var byteArr=new byte[17];
      int bufferOffset=17;
      for(int i=0;;) {
          byteArr[--bufferOffset]=(byte)(48+(word>>>i&1));
          if(++i==16) {
              return new String(byteArr,ToStringUtil.IOS8859CharSet);
          }
          if((i&7)==0) {
            byteArr[--bufferOffset]='_';
          }
      }
    }
    public static String prettyPrintWord(byte word) {
      var byteArr=new byte[8];
      for(int i=0;;) {
          byteArr[7-i]=(byte)(48+(word>>>i&1));
          if(++i==8) {
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
