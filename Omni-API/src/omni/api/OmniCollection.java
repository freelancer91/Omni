package omni.api;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
//TODO integrate into Java Collection library
public interface OmniCollection{
  void clear();
  boolean contains(boolean val);
  boolean contains(Boolean val);
  boolean contains(byte val);
  boolean contains(Byte val);
  boolean contains(char val);
  boolean contains(Character val);
  boolean contains(double val);
  boolean contains(Double val);
  boolean contains(float val);
  boolean contains(Float val);
  boolean contains(int val);
  boolean contains(Integer val);
  boolean contains(long val);
  boolean contains(Long val);
  boolean contains(Object val);
  boolean contains(short val);
  boolean contains(Short val);
  boolean isEmpty();
  boolean remove(Object val);
  boolean removeVal(boolean val);
  boolean removeVal(Boolean val);
  boolean removeVal(byte val);
  boolean removeVal(Byte val);
  boolean removeVal(char val);
  boolean removeVal(Character val);
  boolean removeVal(double val);
  boolean removeVal(Double val);
  boolean removeVal(float val);
  boolean removeVal(Float val);
  boolean removeVal(int val);
  boolean removeVal(Integer val);
  boolean removeVal(long val);
  boolean removeVal(Long val);
  boolean removeVal(short val);
  boolean removeVal(Short val);
  int size();
  Object[] toArray();
  <T> T[] toArray(IntFunction<T[]> arrConstructor);
  <T> T[] toArray(T[] dst);
  interface OfBoolean extends OfPrimitive{
    boolean add(boolean val);
    boolean add(Boolean val);
    @Override default boolean contains(byte val){
      return contains((int)val);
    }
    @Override default boolean contains(char val){
      return contains((int)val);
    }
    @Override default boolean contains(short val){
      return contains((int)val);
    }
    void forEach(BooleanConsumer action);
    void forEach(Consumer<? super Boolean> action);
    // TODO spliterator,
    // TODO stream
    // TODO vanillaView
    OmniIterator.OfBoolean iterator();
    boolean removeIf(BooleanPredicate filter);
    boolean removeIf(Predicate<? super Boolean> filter);
    @Override default boolean removeVal(byte val){
      return removeVal((int)val);
    }
    @Override default boolean removeVal(char val){
      return removeVal((int)val);
    }
    @Override default boolean removeVal(short val){
      return removeVal((int)val);
    }
    @Override Boolean[] toArray();
    boolean[] toBooleanArray();
    byte[] toByteArray();
    char[] toCharArray();
    double[] toDoubleArray();
    float[] toFloatArray();
    int[] toIntArray();
    long[] toLongArray();
    short[] toShortArray();
  }
  interface OfByte extends OfPrimitive{
    boolean add(boolean val);
    default boolean add(Boolean val){
      return add(val.booleanValue());
    }
    boolean add(byte val);
    boolean add(Byte val);
    @Override default boolean contains(short val){
      return contains((int)val);
    }
    void forEach(ByteConsumer action);
    void forEach(Consumer<? super Byte> action);
    // TODO spliterator,
    // TODO stream
    // TODO vanillaView
    OmniIterator.OfByte iterator();
    boolean removeIf(BytePredicate filter);
    boolean removeIf(Predicate<? super Byte> filter);
    @Override default boolean removeVal(short val){
      return removeVal((int)val);
    }
    @Override Byte[] toArray();
    byte[] toByteArray();
    double[] toDoubleArray();
    float[] toFloatArray();
    int[] toIntArray();
    long[] toLongArray();
    short[] toShortArray();
  }
  interface OfChar extends OfPrimitive{
    boolean add(boolean val);
    default boolean add(Boolean val){
      return add(val.booleanValue());
    }
    boolean add(char val);
    boolean add(Character val);
    void forEach(CharConsumer action);
    void forEach(Consumer<? super Character> action);
    // TODO spliterator,
    // TODO stream
    // TODO vanillaView
    OmniIterator.OfChar iterator();
    boolean removeIf(CharPredicate filter);
    boolean removeIf(Predicate<? super Character> filter);
    @Override Character[] toArray();
    char[] toCharArray();
    double[] toDoubleArray();
    float[] toFloatArray();
    int[] toIntArray();
    long[] toLongArray();
  }
  interface OfDouble extends OfPrimitive{
    boolean add(boolean val);
    default boolean add(Boolean val){
      return add(val.booleanValue());
    }
    default boolean add(byte val){
      return add((short)val);
    }
    default boolean add(Byte val){
      return add(val.shortValue());
    }
    boolean add(char val);
    default boolean add(Character val){
      return add(val.charValue());
    }
    boolean add(double val);
    boolean add(Double val);
    boolean add(float val);
    default boolean add(Float val){
      return add(val.floatValue());
    }
    boolean add(int val);
    default boolean add(Integer val){
      return add(val.intValue());
    }
    boolean add(long val);
    default boolean add(Long val){
      return add(val.longValue());
    }
    boolean add(short val);
    default boolean add(Short val){
      return add(val.shortValue());
    }
    void forEach(Consumer<? super Double> action);
    // TODO spliterator,
    // TODO stream
    // TODO vanillaView
    void forEach(DoubleConsumer action);
    OmniIterator.OfDouble iterator();
    boolean removeIf(DoublePredicate filter);
    boolean removeIf(Predicate<? super Double> filter);
    @Override Double[] toArray();
    double[] toDoubleArray();
  }
  interface OfFloat extends OfPrimitive{
    boolean add(boolean val);
    default boolean add(Boolean val){
      return add(val.booleanValue());
    }
    default boolean add(byte val){
      return add((short)val);
    }
    default boolean add(Byte val){
      return add(val.shortValue());
    }
    boolean add(char val);
    default boolean add(Character val){
      return add(val.charValue());
    }
    boolean add(float val);
    boolean add(Float val);
    boolean add(int val);
    default boolean add(Integer val){
      return add(val.intValue());
    }
    boolean add(long val);
    default boolean add(Long val){
      return add(val.longValue());
    }
    boolean add(short val);
    default boolean add(Short val){
      return add(val.shortValue());
    }
    void forEach(Consumer<? super Float> action);
    // TODO spliterator,
    // TODO stream
    // TODO vanillaView
    void forEach(FloatConsumer action);
    OmniIterator.OfFloat iterator();
    boolean removeIf(FloatPredicate filter);
    boolean removeIf(Predicate<? super Float> filter);
    @Override Float[] toArray();
    double[] toDoubleArray();
    float[] toFloatArray();
  }
  interface OfInt extends OfPrimitive{
    boolean add(boolean val);
    default boolean add(Boolean val){
      return add(val.booleanValue());
    }
    default boolean add(byte val){
      return add((int)val);
    }
    default boolean add(Byte val){
      return add(val.intValue());
    }
    default boolean add(char val){
      return add((int)val);
    }
    default boolean add(Character val){
      return add((int)val.charValue());
    }
    boolean add(int val);
    boolean add(Integer val);
    default boolean add(short val){
      return add((int)val);
    }
    default boolean add(Short val){
      return add(val.intValue());
    }
    @Override default boolean contains(short val){
      return contains((int)val);
    }
    void forEach(Consumer<? super Integer> action);
    // TODO spliterator,
    // TODO stream
    // TODO vanillaView
    void forEach(IntConsumer action);
    OmniIterator.OfInt iterator();
    boolean removeIf(IntPredicate filter);
    boolean removeIf(Predicate<? super Integer> filter);
    @Override default boolean removeVal(short val){
      return removeVal((int)val);
    }
    @Override Integer[] toArray();
    double[] toDoubleArray();
    float[] toFloatArray();
    int[] toIntArray();
    long[] toLongArray();
  }
  interface OfLong extends OfPrimitive{
    boolean add(boolean val);
    default boolean add(Boolean val){
      return add(val.booleanValue());
    }
    default boolean add(byte val){
      return add((int)val);
    }
    default boolean add(Byte val){
      return add(val.intValue());
    }
    default boolean add(char val){
      return add((int)val);
    }
    default boolean add(Character val){
      return add((int)val.charValue());
    }
    boolean add(int val);
    default boolean add(Integer val){
      return add(val.intValue());
    }
    boolean add(long val);
    boolean add(Long val);
    default boolean add(short val){
      return add((int)val);
    }
    default boolean add(Short val){
      return add(val.intValue());
    }
    @Override default boolean contains(short val){
      return contains((int)val);
    }
    void forEach(Consumer<? super Long> action);
    // TODO spliterator,
    // TODO stream
    // TODO vanillaView
    void forEach(LongConsumer action);
    OmniIterator.OfLong iterator();
    boolean removeIf(LongPredicate filter);
    boolean removeIf(Predicate<? super Long> filter);
    @Override default boolean removeVal(short val){
      return removeVal((int)val);
    }
    @Override Long[] toArray();
    double[] toDoubleArray();
    float[] toFloatArray();
    long[] toLongArray();
  }
  interface OfPrimitive extends OmniCollection{
    @Override default boolean contains(Boolean val){
      return contains(val.booleanValue());
    }
    @Override default boolean contains(Byte val){
      return contains(val.byteValue());
    }
    @Override default boolean contains(Character val){
      return contains(val.charValue());
    }
    @Override default boolean contains(Double val){
      return contains(val.doubleValue());
    }
    @Override default boolean contains(Float val){
      return contains(val.floatValue());
    }
    @Override default boolean contains(Integer val){
      return contains(val.intValue());
    }
    @Override default boolean contains(Long val){
      return contains(val.longValue());
    }
    @Override default boolean contains(Short val){
      return contains(val.shortValue());
    }
    @Override default boolean removeVal(Boolean val){
      return removeVal(val.booleanValue());
    }
    @Override default boolean removeVal(Byte val){
      return removeVal(val.byteValue());
    }
    @Override default boolean removeVal(Character val){
      return removeVal(val.charValue());
    }
    @Override default boolean removeVal(Double val){
      return removeVal(val.doubleValue());
    }
    @Override default boolean removeVal(Float val){
      return removeVal(val.floatValue());
    }
    @Override default boolean removeVal(Integer val){
      return removeVal(val.intValue());
    }
    @Override default boolean removeVal(Long val){
      return removeVal(val.longValue());
    }
    @Override default boolean removeVal(Short val){
      return removeVal(val.shortValue());
    }
  }
  interface OfRef<E>extends OmniCollection{
    boolean add(E val);
    void forEach(Consumer<? super E> action);
    // TODO spliterator,
    // TODO stream
    // TODO vanillaView
    OmniIterator.OfRef<E> iterator();
    boolean removeIf(Predicate<? super E> filter);
  }
  interface OfShort extends OfPrimitive{
    boolean add(boolean val);
    default boolean add(Boolean val){
      return add(val.booleanValue());
    }
    default boolean add(byte val){
      return add((short)val);
    }
    default boolean add(Byte val){
      return add(val.shortValue());
    }
    boolean add(short val);
    boolean add(Short val);
    void forEach(Consumer<? super Short> action);
    // TODO spliterator,
    // TODO stream
    // TODO vanillaView
    void forEach(ShortConsumer action);
    OmniIterator.OfShort iterator();
    boolean removeIf(Predicate<? super Short> filter);
    boolean removeIf(ShortPredicate filter);
    @Override Short[] toArray();
    double[] toDoubleArray();
    float[] toFloatArray();
    int[] toIntArray();
    long[] toLongArray();
    short[] toShortArray();
  }
}
