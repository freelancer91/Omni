package omni.function;
import java.util.Comparator;
@FunctionalInterface
public interface FloatComparator extends Comparator<Float>
{
  public abstract int compare(final float val1,final float val2);
  public default int compare(final Float val1,final Float val2){
    return compare((float)val1,(float)val2);
  }
  public static int descendingCompare(float val1,float val2){
    if(val1>val2){
      return -1;
    }else if(val1<val2){
      return 1;
    }
    final int thisBits,thatBits;
    if((thisBits=Float.floatToIntBits(val1))==(thatBits=Float.floatToIntBits(val2))){
      return 0;
    }else if(thisBits<thatBits){
      return -1;
    }
    return 1;
  }
  //TODO override reversed()
  //TODO override thenComparing(Comparator<? super T> other)
  //TODO override thenComparing(Function<? super T,? extends U> keyExtractor,Comparator<? super U> keyComparator);
  //TODO override thenComparing(Function<? super T,? extends U> keyExtractor);
  //TODO override thenComparingInt(ToIntFunction<? super T> keyExtractor);
  //TODO override thenComparingLong(ToLongFunction<? super T> keyExtractor);
  //TODO override thenComparingDouble(ToDoubleFunction<? super T> keyExtractor);
  //TODO static methods
}
