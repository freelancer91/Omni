package omni.util;
import java.lang.reflect.Array;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import omni.function.CharPredicate;
import omni.function.FloatPredicate;
import omni.function.ShortPredicate;
public final class OmniArray
{
  private OmniArray()
  {
    super();
  }
  public static final int DEFAULT_ARR_SEQ_CAP=10;
  private static final int DEFAULT_ARR_SEQ_CAP_PLUS_50_PCT=DEFAULT_ARR_SEQ_CAP+(DEFAULT_ARR_SEQ_CAP>>>1);
  public static final int MAX_ARR_SIZE=Integer.MAX_VALUE-8;
  private static final int MAX_ARR_SIZE_50_PCT_GROWTH_THRESHOLD=(MAX_ARR_SIZE/3<<1)+1;
  public static int growBy100Pct(int currentCapacity){
    if(currentCapacity!=0){
      if(currentCapacity<MAX_ARR_SIZE){
        if(currentCapacity>MAX_ARR_SIZE>>>1){ return MAX_ARR_SIZE; }
        return currentCapacity<<1;
      }else if(currentCapacity==Integer.MAX_VALUE){
        throw new OutOfMemoryError();
      }
    }
    return currentCapacity+1;
  }
  public static int growBy100Pct(int currentCapacity,int minCapacity){
    if(currentCapacity!=0){
      if(minCapacity<MAX_ARR_SIZE){
        if(minCapacity<0){
          throw new OutOfMemoryError();
        }
        if(currentCapacity>MAX_ARR_SIZE>>>1){ return MAX_ARR_SIZE; }
        if((currentCapacity<<=1)>=minCapacity){ return currentCapacity; }
      }
    }else if(minCapacity<0){
      throw new OutOfMemoryError();
    }
    return minCapacity;
  }
  public static int growBy50Pct(int currentCapacity){
    if(currentCapacity>1){
      if(currentCapacity<MAX_ARR_SIZE){
        if(currentCapacity>MAX_ARR_SIZE_50_PCT_GROWTH_THRESHOLD){ return MAX_ARR_SIZE; }
        return currentCapacity+(currentCapacity>>>1);
      }else if(currentCapacity==Integer.MAX_VALUE){
        throw new OutOfMemoryError();
      }
    }
    return currentCapacity+1;
  }
  public static int growBy50Pct(int currentCapacity,int minCapacity){
    if(currentCapacity>1){
      if(minCapacity<MAX_ARR_SIZE){
        if(minCapacity<0){
          throw new OutOfMemoryError();
        }
        if(currentCapacity>MAX_ARR_SIZE_50_PCT_GROWTH_THRESHOLD){ return MAX_ARR_SIZE; }
        if((currentCapacity+=currentCapacity>>>1)>=minCapacity){ return currentCapacity; }
      }
    }else if(minCapacity<0){
      throw new OutOfMemoryError();
    }
    return minCapacity;
  }
  public static int growToArrSeqDefault(int minCapacity){
    if(minCapacity>=DEFAULT_ARR_SEQ_CAP_PLUS_50_PCT){ return minCapacity; }
    if(minCapacity>=DEFAULT_ARR_SEQ_CAP){ return DEFAULT_ARR_SEQ_CAP_PLUS_50_PCT; }
    return DEFAULT_ARR_SEQ_CAP;
  }
  @SuppressWarnings("unchecked") public static <T> T[] uncheckedArrResize(int size,T[] arr){
    switch(Integer.signum(size-arr.length)){
    case -1:
        arr[size]=null;
    default:
        break;
    case 1:
        arr=(T[])Array.newInstance(arr.getClass().getComponentType(),size);
    }
    return arr;
  }
  public interface OfBoolean
  {
    public static final boolean[] DEFAULT_ARR=new boolean[]{};
    public static final Boolean[] DEFAULT_BOXED_ARR=new Boolean[]{};
    static void reverseRange(boolean[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
  }
  public interface OfByte
  {
    public static final byte[] DEFAULT_ARR=new byte[]{};
    public static final Byte[] DEFAULT_BOXED_ARR=new Byte[]{};
    static void reverseRange(byte[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
  }
  public interface OfChar
  {
    public static final char[] DEFAULT_ARR=new char[]{};
    public static final Character[] DEFAULT_BOXED_ARR=new Character[]{};
    static void reverseRange(char[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    static IntPredicate getIndexPredicate(CharPredicate predicate,char...arr)
    {
      return index->predicate.test(arr[index]);
    }
  }
  public interface OfShort
  {
    public static final short[] DEFAULT_ARR=new short[]{};
    public static final Short[] DEFAULT_BOXED_ARR=new Short[]{};
    static void reverseRange(short[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    static IntPredicate getIndexPredicate(ShortPredicate predicate,short...arr)
    {
      return index->predicate.test(arr[index]);
    }
  }
  public interface OfInt
  {
    public static final int[] DEFAULT_ARR=new int[]{};
    public static final Integer[] DEFAULT_BOXED_ARR=new Integer[]{};
    static void reverseRange(int[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    static IntPredicate getIndexPredicate(IntPredicate predicate,int...arr)
    {
      return index->predicate.test(arr[index]);
    }
  }
  public interface OfLong
  {
    public static final long[] DEFAULT_ARR=new long[]{};
    public static final Long[] DEFAULT_BOXED_ARR=new Long[]{};
    static void reverseRange(long[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    static IntPredicate getIndexPredicate(LongPredicate predicate,long...arr)
    {
      return index->predicate.test(arr[index]);
    }
  }
  public interface OfFloat
  {
    public static final float[] DEFAULT_ARR=new float[]{};
    public static final Float[] DEFAULT_BOXED_ARR=new Float[]{};
    static void reverseRange(float[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    static IntPredicate getIndexPredicate(FloatPredicate predicate,float...arr)
    {
      return index->predicate.test(arr[index]);
    }
    static IntPredicate getFltBitsIndexPredicate(FloatPredicate predicate,int...arr)
    {
      return index->predicate.test(Float.intBitsToFloat(arr[index]));
    }
  }
  public interface OfDouble
  {
    public static final double[] DEFAULT_ARR=new double[]{};
    public static final Double[] DEFAULT_BOXED_ARR=new Double[]{};
    static void reverseRange(double[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    static IntPredicate getIndexPredicate(DoublePredicate predicate,double...arr)
    {
      return index->predicate.test(arr[index]);
    }
    static IntPredicate getDblBitsIndexPredicate(DoublePredicate predicate,long...arr)
    {
      return index->predicate.test(Double.longBitsToDouble(arr[index]));
    }
  }
  public interface OfRef
  {
    public static final Object[] DEFAULT_ARR=new Object[]{};
    static void reverseRange(Object[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    static void nullifyRange(Object[] arr,int begin,int end)
    {
      for(;;++begin){
        arr[begin]=null;
        if(begin==end){ return; }
      }
    }
    @SafeVarargs
    static <E> IntPredicate getIndexPredicate(Predicate<? super E> predicate,E...arr)
    {
      return index->predicate.test(arr[index]);
    }
  }
}
