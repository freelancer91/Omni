package omni.function;
import java.util.Comparator;
@FunctionalInterface
public interface BooleanComparator extends Comparator<Boolean>
{
  public abstract int compare(final boolean val1,final boolean val2);
  public default int compare(final Boolean val1,final Boolean val2){
    return compare((boolean)val1,(boolean)val2);
  }
  public static int descendingCompare(boolean val1,boolean val2){
    if(val1==val2){
      return 0;
    }else if(val1){
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
