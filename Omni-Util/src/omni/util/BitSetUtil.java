package omni.util;

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
      return (convertToPackedIndex(nonPackedCapacity-1))+1;
    }
    
    public static boolean getFromPackedArr(long[] words,int nonPackedIndex) {
      return ((words[convertToPackedIndex(nonPackedIndex)]>>>nonPackedIndex)&1)!=0;
    }
    public static void arraycopyaligned(long[] src,int srcOffset,long[] dst,int dstOffset,int length) {
      //TODO bugtest
      ArrCopy.uncheckedCopy(src,srcOffset>>=6,dst,dstOffset>>=6,length>>=6);
      if((length&63)!=0) {
        //length is not aligned
        (dst)[dstOffset+=length] = (dst[dstOffset]&(-1L<<length)) | ((src)[srcOffset+length]&(-1L>>>length));
      }
    }
    public static void arraycopysrcaligned(long[] src,int srcOffset,long[] dst,int dstOffset,int length) {
      //TODO bugtest

      int srcWordOffset,dstWordOffset;
      long srcWord;
      (dst)[dstWordOffset=dstOffset>>6]=(dst[dstOffset]&(-1L>>>-dstOffset))|((srcWord=(src)[srcWordOffset=srcOffset>>6])<<dstOffset);
      int dstBound;
      for(final var dstWordBound=(dstBound=dstOffset+length)>>6;++dstWordOffset<dstWordBound;) {
        dst[dstWordOffset]=(srcWord>>>-dstOffset)|((srcWord=src[++srcWordOffset])<<dstOffset);
      }
      dst[dstWordOffset]=(srcWord>>>-dstOffset)|(dst[dstWordOffset]&(-1L<<(dstBound-1)));
    }
    public static void arraycopydstaligned(long[] src,int srcOffset,long[] dst,int dstOffset,int length) {
      //TODO bugtest

      int srcWordOffset,dstWordOffset;
      long srcWord;
      (dst)[dstWordOffset=dstOffset>>6]=(((src)[srcWordOffset=srcOffset>>6])>>>srcOffset)|((srcWord=src[++srcWordOffset])<<-srcOffset);
      for(final var dstWordBound=(dstOffset+length)>>6;++dstWordOffset<=dstWordBound;) {
        dst[dstWordOffset]=(srcWord>>>srcOffset)|((srcWord=src[++srcWordOffset])<<-srcOffset);
      }
    }
    public static void arraycopyunaligned(long[] src,int srcOffset,long[] dst,int dstOffset,int length) {
      //TODO bugtest
      int srcWordOffset=srcOffset>>6;
      int dstWordOffset=dstOffset>>6;
      int dstBound;
      final int shift,dstWordBound=(dstBound=dstOffset+length)>>6;
      long srcWord;
      if((dstOffset&=63)<(srcOffset&=63)) {
        dst[dstWordOffset]=dst[dstWordOffset]&(-1L<<dstOffset)|((srcWord=src[srcWordOffset]))>>>(shift=srcOffset-dstOffset);
        while(++dstWordOffset<dstWordBound) {
          dst[dstWordOffset]=(srcWord<<-shift) | ((srcWord=src[++srcWordOffset])>>>shift);
        }
        dst[dstWordOffset]=(srcWord<<-shift) | (dst[dstWordOffset]&(-1L<<(dstBound-1)));
      }else {
        dst[dstWordOffset]=dst[dstWordOffset]&(-1L<<dstOffset)|((srcWord=src[srcWordOffset]))<<(shift=dstOffset-srcOffset);
        while(++dstWordOffset<dstWordBound) {
          dst[dstWordOffset]=(srcWord>>>-shift) | ((srcWord=src[++srcWordOffset])<<shift);
        }
        dst[dstWordOffset]=(srcWord>>>-shift) | (dst[dstWordOffset]&(-1L<<(dstBound-1)));
      }
    }
    public static void arraycopy(long[] src,int srcOffset,long[] dst,int dstOffset,int length) {
      //TODO bugtest

      if((srcOffset&63)==0) {
        if((dstOffset&63)==0) {
          arraycopyaligned(src,srcOffset,dst,dstOffset,length);
        }else {
          arraycopysrcaligned(src,srcOffset,dst,dstOffset,length);
        }
      }else {
        if((dstOffset&63)==0) {
          arraycopydstaligned(src,srcOffset,dst,dstOffset,length);
        }else {
          arraycopyunaligned(src,srcOffset,dst,dstOffset,length);
        }
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
      return ((word>>nonPackedIndex)&1)!=0;
    }
    
    
    public static void storeInPackedArr(long[] words,int nonPackedIndex,boolean val) {
      final var packedIndex=convertToPackedIndex(nonPackedIndex);
      final var mask=1L<<nonPackedIndex;
      if(val) {
        words[packedIndex]|=(mask);
      }else {
        words[packedIndex]&=~(mask);
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
    
    public static String prettyPrintWord(long word) {
        var byteArr=new byte[64];
        for(int i=0;;) {
            byteArr[i]=(byte)(48+(word>>>i&1));
            if(++i==64) {
                return new String(byteArr,ToStringUtil.IOS8859CharSet);
            }
        }
    }
  
   
    
    public static void srcUnallignedPullDown(long[] words,int dstWordOffset,int srcOffset,int srcWordBound) {
        int srcWordOffset;
        for(var word=words[srcWordOffset=srcOffset>>6];;++dstWordOffset) {
            if(srcWordOffset==srcWordBound) {
                words[dstWordOffset]=word>>>srcOffset;
                return;
            }
            words[dstWordOffset]=word>>>srcOffset|(word=words[++srcWordOffset])<<-srcOffset;
        }
    }
    public static void dstUnallignedPullDown(long[] words,int dstOffset,int srcOffset,int srcWordBound) {
        int dstWordOffset;
        var word=words[dstWordOffset=dstOffset>>6]&(1L<<dstOffset)-1;
        if((srcOffset&63)!=0) {
            dstOffset-=srcOffset;
        }
        for(int srcWordOffset=srcOffset>>6;srcWordOffset!=srcWordBound;words[dstWordOffset]=word|(word=words[++srcWordOffset])<<dstOffset) {}
        words[dstWordOffset]=word|words[srcWordBound]<<dstOffset;
        return;
    }
    
//    public static void uncheckedSelfCopy(long[] src,int dstOffset,int srcOffset,int length) {
//        if((dstOffset&63)==0) {
//            if((srcOffset&63)==0) {
//                allignedSelfCopy(src,dstOffset,srcOffset,length);
//            }else {
//                dstAllignedSelfCopy(src,dstOffset,srcOffset,length);
//            }
//        }else {
//            if((srcOffset&63)==0) {
//                srcAllignedSelfCopy(src,dstOffset,srcOffset,length);
//            }else {
//                notAllignedSelfCopy(src,dstOffset,srcOffset,length);
//            }
//        }
//    }
//    public static void dstAllignedSelfCopy(long[] src,int dstOffset,int srcOffset,int length) {
//        //TODO
//        int srcWordOffset=srcOffset>>6;
//        int srcBound=srcOffset+length-1;
//        int srcWordBound=srcBound>>6;
//        if(srcWordOffset==srcWordBound) {
//            
//        }else {
//            
//        }
//    }
//    public static void srcAllignedSelfCopy(long[] src,int dstOffset,int srcOffset,int length) {
//        //TODO
//    }
//    public static void notAllignedSelfCopy(long[] src,int dstOffset,int srcOffset,int length) {
//        //TODO
//    }
//    public static void allignedSelfCopy(long[] src,int dstOffset,int srcOffset,int length){
//        if(length<64) {
//            allignedPartialWordSelfCopy(src,dstOffset,srcOffset,length);
//        }else {
//            final int wordLength;
//            ArrCopy.uncheckedSelfCopy(src,dstOffset>>=6,srcOffset>>=6,wordLength=length>>6);
//            if((length&63)!=0) {
//                allignedPartialWordSelfCopy(src,dstOffset+wordLength,srcOffset+wordLength,length);
//            }
//        }
//    }
//
//    public static void allignedPartialWordSelfCopy(long[] src,int dstOffset,int srcOffset,int length){
//        final long mask;
//        src[dstOffset]=(src[srcOffset]&(mask=(1L<<length)-1))|(src[dstOffset]&~mask);
//    }
//    
//    
    
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
    
   
    
    
    
    
//    
//    
//    
//    public static boolean containsfragmented(long[] arr,int begin,int end,boolean val){
//        int wordBegin,wordEnd;
//        if((wordBegin=begin << 6) == (wordEnd=end << 6)){
//            long word=arr[wordBegin];
//            if(val){
//                return word >>> begin != 0L || word << end + 1 != 0L || wholeWordsContainTrue(arr,0,wordEnd - 1)
//                        || wholeWordsContainTrue(arr,wordBegin + 1,arr.length - 1);
//            }else{
//                return (word=~word) >>> begin != 0L || word << end + 1 != 0L
//                        || wholeWordsContainFalse(arr,0,wordEnd - 1)
//                        || wholeWordsContainFalse(arr,wordBegin + 1,wordEnd - 1);
//            }
//        }else{
//            if(val){
//                return arr[wordBegin] >>> begin != 0L || arr[wordEnd] << end + 1 != 0L
//                        || wholeWordsContainTrue(arr,0,wordEnd - 1)
//                        || wholeWordsContainTrue(arr,wordBegin + 1,wordEnd - 1);
//            }else{
//                return ~arr[wordBegin] >>> begin != 0L || ~arr[wordEnd] << end + 1 != 0L
//                        || wholeWordsContainFalse(arr,0,wordEnd - 1)
//                        || wholeWordsContainFalse(arr,wordBegin + 1,wordEnd - 1);
//            }
//        }
//    }
//    public static boolean contains(long[] arr,int bound,boolean val){
//        int wordEnd;
//        if(0 == (wordEnd=bound - 1 << 6)){
//            long word=arr[0];
//            if(!val){
//                word=~word;
//            }
//            return word << -bound != 0L;
//        }else{
//            if(val){
//                return arr[wordEnd] << bound != 0L || wholeWordsContainTrue(arr,0,wordEnd - 1);
//            }else{
//                return ~arr[wordEnd] << bound != 0L || wholeWordsContainFalse(arr,0,wordEnd - 1);
//            }
//        }
//    }
//    public static boolean contains(long[] arr,int begin,int bound,boolean val){
//        int wordBegin;
//        int wordEnd;
//        if((wordBegin=begin << 6) == (wordEnd=bound - 1 << 6)){
//            long word=arr[wordBegin];
//            if(!val){
//                word=~word;
//            }
//            return word >>> begin << begin - bound != 0L;
//        }else{
//            if(val){
//                return arr[wordBegin] >>> begin != 0L || arr[wordEnd] << bound != 0L
//                        || wholeWordsContainTrue(arr,wordBegin + 1,wordEnd - 1);
//            }else{
//                return ~arr[wordBegin] >>> begin != 0L || ~arr[wordEnd] << bound != 0L
//                        || wholeWordsContainFalse(arr,wordBegin + 1,wordEnd - 1);
//            }
//        }
//    }
//    private static boolean wholeWordsContainTrue(long[] arr,int wordBegin,int wordEnd){
//        if(wordBegin <= wordEnd){
//            while(arr[wordBegin] == 0L){
//                if(wordBegin == wordEnd){
//                    return false;
//                }
//                ++wordBegin;
//            }
//            return true;
//        }
//        return false;
//    }
//    private static boolean wholeWordsContainFalse(long[] arr,int wordBegin,int wordEnd){
//        if(wordBegin <= wordEnd){
//            while(arr[wordBegin] == -1L){
//                if(wordBegin == wordEnd){
//                    return false;
//                }
//                ++wordBegin;
//            }
//            return true;
//        }
//        return false;
//    }
}
