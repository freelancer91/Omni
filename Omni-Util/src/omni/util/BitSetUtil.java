package omni.util;

public interface BitSetUtil{

    public static boolean containsfragmented(long[] arr,int begin,int end,boolean val){
        int wordBegin,wordEnd;
        if((wordBegin=begin << 6) == (wordEnd=end << 6)){
            long word=arr[wordBegin];
            if(val){
                return word >>> begin != 0L || word << end + 1 != 0L || wholeWordsContainTrue(arr,0,wordEnd - 1)
                        || wholeWordsContainTrue(arr,wordBegin + 1,arr.length - 1);
            }else{
                return (word=~word) >>> begin != 0L || word << end + 1 != 0L
                        || wholeWordsContainFalse(arr,0,wordEnd - 1)
                        || wholeWordsContainFalse(arr,wordBegin + 1,wordEnd - 1);
            }
        }else{
            if(val){
                return arr[wordBegin] >>> begin != 0L || arr[wordEnd] << end + 1 != 0L
                        || wholeWordsContainTrue(arr,0,wordEnd - 1)
                        || wholeWordsContainTrue(arr,wordBegin + 1,wordEnd - 1);
            }else{
                return ~arr[wordBegin] >>> begin != 0L || ~arr[wordEnd] << end + 1 != 0L
                        || wholeWordsContainFalse(arr,0,wordEnd - 1)
                        || wholeWordsContainFalse(arr,wordBegin + 1,wordEnd - 1);
            }
        }
    }
    public static boolean contains(long[] arr,int bound,boolean val){
        int wordEnd;
        if(0 == (wordEnd=bound - 1 << 6)){
            long word=arr[0];
            if(!val){
                word=~word;
            }
            return word << -bound != 0L;
        }else{
            if(val){
                return arr[wordEnd] << bound != 0L || wholeWordsContainTrue(arr,0,wordEnd - 1);
            }else{
                return ~arr[wordEnd] << bound != 0L || wholeWordsContainFalse(arr,0,wordEnd - 1);
            }
        }
    }
    public static boolean contains(long[] arr,int begin,int bound,boolean val){
        int wordBegin;
        int wordEnd;
        if((wordBegin=begin << 6) == (wordEnd=bound - 1 << 6)){
            long word=arr[wordBegin];
            if(!val){
                word=~word;
            }
            return word >>> begin << begin - bound != 0L;
        }else{
            if(val){
                return arr[wordBegin] >>> begin != 0L || arr[wordEnd] << bound != 0L
                        || wholeWordsContainTrue(arr,wordBegin + 1,wordEnd - 1);
            }else{
                return ~arr[wordBegin] >>> begin != 0L || ~arr[wordEnd] << bound != 0L
                        || wholeWordsContainFalse(arr,wordBegin + 1,wordEnd - 1);
            }
        }
    }
    private static boolean wholeWordsContainTrue(long[] arr,int wordBegin,int wordEnd){
        if(wordBegin <= wordEnd){
            while(arr[wordBegin] == 0L){
                if(wordBegin == wordEnd){
                    return false;
                }
                ++wordBegin;
            }
            return true;
        }
        return false;
    }
    private static boolean wholeWordsContainFalse(long[] arr,int wordBegin,int wordEnd){
        if(wordBegin <= wordEnd){
            while(arr[wordBegin] == -1L){
                if(wordBegin == wordEnd){
                    return false;
                }
                ++wordBegin;
            }
            return true;
        }
        return false;
    }
}
