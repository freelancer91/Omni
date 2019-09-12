package omni.function;
import java.util.Comparator;
@FunctionalInterface
public interface FloatComparator extends Comparator<Float>
{
  public abstract int compare(final float val1,final float val2);
  public default int compare(final Float val1,final Float val2){
    return compare((float)val1,(float)val2);
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
