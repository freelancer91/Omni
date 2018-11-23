package omni.util;
import java.lang.reflect.Array;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import omni.function.CharPredicate;
import omni.function.FloatPredicate;
import omni.function.ShortPredicate;
public final class OmniArray{
    // TODO use a template to reduce the amount of hand-written code
    public static final int DEFAULT_ARR_SEQ_CAP=10;
    private static final int DEFAULT_ARR_SEQ_CAP_PLUS_50_PCT=DEFAULT_ARR_SEQ_CAP+(DEFAULT_ARR_SEQ_CAP>>>1);
    public static final int MAX_ARR_SIZE=Integer.MAX_VALUE-8;
    private static final int MAX_ARR_SIZE_50_PCT_GROWTH_THRESHOLD=(MAX_ARR_SIZE/3<<1)+1;
    public static int growBy100Pct(int currentCapacity){
        if(currentCapacity!=0){
            if(currentCapacity<MAX_ARR_SIZE){
                if(currentCapacity>MAX_ARR_SIZE>>>1){ return MAX_ARR_SIZE; }
                return currentCapacity<<1;
            }else if(currentCapacity==Integer.MAX_VALUE){ throw new OutOfMemoryError(); }
        }
        return currentCapacity+1;
    }
    public static int growBy100Pct(int currentCapacity,int minCapacity){
        if(currentCapacity!=0){
            if(minCapacity<MAX_ARR_SIZE){
                if(minCapacity<0){ throw new OutOfMemoryError(); }
                if(currentCapacity>MAX_ARR_SIZE>>>1){ return MAX_ARR_SIZE; }
                if((currentCapacity<<=1)>=minCapacity){ return currentCapacity; }
            }
        }else if(minCapacity<0){ throw new OutOfMemoryError(); }
        return minCapacity;
    }
    public static int growBy50Pct(int currentCapacity){
        if(currentCapacity>1){
            if(currentCapacity<MAX_ARR_SIZE){
                if(currentCapacity>MAX_ARR_SIZE_50_PCT_GROWTH_THRESHOLD){ return MAX_ARR_SIZE; }
                return currentCapacity+(currentCapacity>>>1);
            }else if(currentCapacity==Integer.MAX_VALUE){ throw new OutOfMemoryError(); }
        }
        return currentCapacity+1;
    }
    public static int growBy50Pct(int currentCapacity,int minCapacity){
        if(currentCapacity>1){
            if(minCapacity<MAX_ARR_SIZE){
                if(minCapacity<0){ throw new OutOfMemoryError(); }
                if(currentCapacity>MAX_ARR_SIZE_50_PCT_GROWTH_THRESHOLD){ return MAX_ARR_SIZE; }
                if((currentCapacity+=currentCapacity>>>1)>=minCapacity){ return currentCapacity; }
            }
        }else if(minCapacity<0){ throw new OutOfMemoryError(); }
        return minCapacity;
    }
    public static int growToArrSeqDefault(int minCapacity){
        if(minCapacity>=DEFAULT_ARR_SEQ_CAP_PLUS_50_PCT){ return minCapacity; }
        if(minCapacity>=DEFAULT_ARR_SEQ_CAP){ return DEFAULT_ARR_SEQ_CAP_PLUS_50_PCT; }
        return DEFAULT_ARR_SEQ_CAP;
    }
    @SuppressWarnings("unchecked") public static <T> T[] uncheckedArrResize(int size,T[] arr){
        switch(size-arr.length){
        case -1:
            arr[size]=null;
        default:
            break;
        case 1:
            arr=(T[])Array.newInstance(arr.getClass().getComponentType(),size);
        }
        return arr;
    }
    private OmniArray(){}
    public interface OfBoolean{
        public static final boolean[] DEFAULT_ARR=new boolean[]{};
        public static final Boolean[] DEFAULT_BOXED_ARR=new Boolean[]{};
    }
    public interface OfByte{
        public static final byte[] DEFAULT_ARR=new byte[]{};
        public static final Byte[] DEFAULT_BOXED_ARR=new Byte[]{};
    }
    public interface OfChar{
        public static final char[] DEFAULT_ARR=new char[]{};
        public static final Character[] DEFAULT_BOXED_ARR=new Character[]{};
        static IntPredicate getIndexPredicate(char[] arr,CharPredicate predicate){
            return index->predicate.test(arr[index]);
        }
    }
    public interface OfDouble{
        public static final double[] DEFAULT_ARR=new double[]{};
        public static final Double[] DEFAULT_BOXED_ARR=new Double[]{};
        static IntPredicate getDblBitsIndexPredicate(long[] arr,DoublePredicate predicate){
            return index->predicate.test(Double.longBitsToDouble(arr[index]));
        }
        static IntPredicate getIndexPredicate(double[] arr,DoublePredicate predicate){
            return index->predicate.test(arr[index]);
        }
    }
    public interface OfFloat{
        public static final float[] DEFAULT_ARR=new float[]{};
        public static final Float[] DEFAULT_BOXED_ARR=new Float[]{};
        static IntPredicate getFltBitsIndexPredicate(int[] arr,FloatPredicate predicate){
            return index->predicate.test(Float.intBitsToFloat(arr[index]));
        }
        static IntPredicate getIndexPredicate(float[] arr,FloatPredicate predicate){
            return index->predicate.test(arr[index]);
        }
    }
    public interface OfInt{
        public static final int[] DEFAULT_ARR=new int[]{};
        public static final Integer[] DEFAULT_BOXED_ARR=new Integer[]{};
        static IntPredicate getIndexPredicate(int[] arr,IntPredicate predicate){
            return index->predicate.test(arr[index]);
        }
    }
    public interface OfLong{
        public static final long[] DEFAULT_ARR=new long[]{};
        public static final Long[] DEFAULT_BOXED_ARR=new Long[]{};
        static IntPredicate getIndexPredicate(long[] arr,LongPredicate predicate){
            return index->predicate.test(arr[index]);
        }
    }
    public interface OfRef{
        public static final Object[] DEFAULT_ARR=new Object[]{};
        static <E> IntPredicate getIndexPredicate(E[] arr,Predicate<? super E> predicate){
            return index->predicate.test(arr[index]);
        }
        static void nullifyRange(Object[] arr,int begin,int end){
            for(;;++begin){
                arr[begin]=null;
                if(begin==end){ return; }
            }
        }
    }
    public interface OfShort{
        public static final short[] DEFAULT_ARR=new short[]{};
        public static final Short[] DEFAULT_BOXED_ARR=new Short[]{};
        static IntPredicate getIndexPredicate(short[] arr,ShortPredicate predicate){
            return index->predicate.test(arr[index]);
        }
    }
}
