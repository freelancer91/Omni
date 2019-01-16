package omni.util;
public interface BitSetUtils{
  static long[] getBitSet(int count){
    return new long[(count-1>>6)+1];
  }
}
