package omni.function;
import java.util.Comparator;
@FunctionalInterface
public interface ByteComparator extends Comparator<Byte>
{
  public abstract int compare(final byte val1,final byte val2);
  public default int compare(final Byte val1,final Byte val2){
    return compare((byte)val1,(byte)val2);
  }
  @Override public default ByteComparator reversed(){
    return (val1,val2)->compare(val2,val1);
  }
  public static int descendingCompare(byte val1,byte val2){
    return val2-val1;
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
