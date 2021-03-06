package omni.function;
import java.util.Comparator;
@FunctionalInterface
public interface DoubleComparator extends Comparator<Double>
{
  public abstract int compare(final double val1,final double val2);
  public default int compare(final Double val1,final Double val2){
    return compare((double)val1,(double)val2);
  }
  @Override public default DoubleComparator reversed(){
    return (val1,val2)->compare(val2,val1);
  }
  public static int descendingCompare(double val1,double val2){
    if(val1>val2){
      return -1;
    }else if(val1<val2){
      return 1;
    }
    final long thisBits,thatBits;
    if((thisBits=Double.doubleToLongBits(val1))>(thatBits=Double.doubleToLongBits(val2))){
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
