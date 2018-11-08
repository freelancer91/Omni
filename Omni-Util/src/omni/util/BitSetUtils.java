package omni.util;
public interface BitSetUtils{
    // TODO optimize, especially in boolean and byte cases
    static long[] getBitSet(int count){
        return new long[(count-1>>6)+1];
    }
}
