package omni.function;
import java.util.Comparator;
@FunctionalInterface
public interface CharComparator extends Comparator<Character>
{
  public abstract int compare(final char val1,final char val2);
  public default int compare(final Character val1,final Character val2){
    return compare((char)val1,(char)val2);
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
