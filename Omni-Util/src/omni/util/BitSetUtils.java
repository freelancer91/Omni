package omni.util;
public interface BitSetUtils{
    static long[] getBitSet(int count){
        return new long[(count-1>>6)+1];
    }
    static boolean containsword(long word,int val){
        return (word&1L<<val)!=0;
    }
}
