package omni.function;
import java.util.Comparator;
@FunctionalInterface
public interface IntComparator extends Comparator<Integer>
{
  public abstract int compare(final int val1,final int val2);
  public default int compare(final Integer val1,final Integer val2){
    return compare((int)val1,(int)val2);
  }
  @Override public default IntComparator reversed(){
    return (val1,val2)->compare(val2,val1);
  }
  public static int descendingCompare(int val1,int val2){
    if(val1>val2){
      return -1;
    }else if(val1<val2){
      return 1;
    }
    return 0;
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
