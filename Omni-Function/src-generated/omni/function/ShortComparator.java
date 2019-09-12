package omni.function;
import java.util.Comparator;
@FunctionalInterface
public interface ShortComparator extends Comparator<Short>
{
  public abstract int compare(final short val1,final short val2);
  public default int compare(final Short val1,final Short val2){
    return compare((short)val1,(short)val2);
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
