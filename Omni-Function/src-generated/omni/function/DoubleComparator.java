package omni.function;
import java.util.Comparator;
@FunctionalInterface
public interface DoubleComparator extends Comparator<Double>
{
  public abstract int compare(final double val1,final double val2);
  public default int compare(final Double val1,final Double val2){
    return compare((double)val1,(double)val2);
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
