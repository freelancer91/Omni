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
//TODO integration into Java Collection library
public abstract interface OmniCollection<E> extends Cloneable
{
  public abstract OmniIterator<E> iterator();
  public abstract void clear();
  public abstract boolean contains(Object val);
  public abstract boolean contains(boolean val);
  public abstract boolean contains(byte val);
  public abstract boolean contains(char val);
  public abstract boolean contains(short val);
  public abstract boolean contains(int val);
  public abstract boolean contains(long val);
  public abstract boolean contains(float val);
  public abstract boolean contains(double val);
  public abstract boolean contains(Boolean val);
  public abstract boolean contains(Integer val);
  public abstract boolean contains(Long val);
  public abstract boolean contains(Float val);
  public abstract boolean contains(Double val);
  public abstract boolean contains(Byte val);
  public abstract boolean contains(Character val);
  public abstract boolean contains(Short val);
  public abstract boolean removeVal(Byte val);
  public abstract boolean removeVal(Character val);
  public abstract boolean removeVal(Short val);
  public abstract boolean remove(Object val);
  public abstract boolean removeVal(boolean val);
  public abstract boolean removeVal(byte val);
  public abstract boolean removeVal(char val);
  public abstract boolean removeVal(short val);
  public abstract boolean removeVal(int val);
  public abstract boolean removeVal(long val);
  public abstract boolean removeVal(float val);
  public abstract boolean removeVal(double val);
  public abstract boolean removeVal(Boolean val);
  public abstract boolean removeVal(Integer val);
  public abstract boolean removeVal(Long val);
  public abstract boolean removeVal(Float val);
  public abstract boolean removeVal(Double val);
  public abstract boolean isEmpty();
  public abstract int size();
  public abstract Object[] toArray();
  public abstract <T> T[] toArray(IntFunction<T[]> arrConstructor);
  public abstract <T> T[] toArray(T[] dst);
  public abstract Object clone();
  public abstract interface OfPrimitive<E> extends OmniCollection<E>
  {
    public abstract boolean add(boolean val);
    @Override
    public default boolean contains(Boolean val)
    {
      return val!=null && contains(
        val.booleanValue()
      );
    }
    @Override
    public default boolean contains(Integer val)
    {
      return val!=null && contains(
        val.intValue()
      );
    }
    @Override
    public default boolean contains(Long val)
    {
      return val!=null && contains(
        val.longValue()
      );
    }
    @Override
    public default boolean contains(Float val)
    {
      return val!=null && contains(
        val.floatValue()
      );
    }
    @Override
    public default boolean contains(Double val)
    {
      return val!=null && contains(
        val.doubleValue()
      );
    }
    @Override
    public default boolean removeVal(Boolean val)
    {
      return val!=null && removeVal(
        val.booleanValue()
      );
    }
    @Override
    public default boolean removeVal(Integer val)
    {
      return val!=null && removeVal(
        val.intValue()
      );
    }
    @Override
    public default boolean removeVal(Long val)
    {
      return val!=null && removeVal(
        val.longValue()
      );
    }
    @Override
    public default boolean removeVal(Float val)
    {
      return val!=null && removeVal(
        val.floatValue()
      );
    }
    @Override
    public default boolean removeVal(Double val)
    {
      return val!=null && removeVal(
        val.doubleValue()
      );
    }
  }
  public abstract interface OfBoolean extends OfPrimitive<Boolean>,Iterable<Boolean>
  ,BooleanInput<Boolean>,ByteOutput<Boolean>,CharOutput<Boolean>
  {
    @Override
    public default boolean contains(Byte val)
    {
      return val!=null && contains(
        val.intValue()
      );
    }
    @Override
    public default boolean contains(Character val)
    {
      return val!=null && contains(
        val.charValue()
      );
    }
    @Override
    public default boolean contains(Short val)
    {
      return val!=null && contains(
        val.intValue()
      );
    }
    @Override
    public default boolean removeVal(Byte val)
    {
      return val!=null && removeVal(
        val.intValue()
      );
    }
    @Override
    public default boolean removeVal(Character val)
    {
      return val!=null && removeVal(
        val.charValue()
      );
    }
    @Override
    public default boolean removeVal(Short val)
    {
      return val!=null && removeVal(
        val.intValue()
      );
    }
    @Override
    public abstract boolean add(boolean val);
    public abstract boolean add(Boolean val);
    public abstract void forEach(BooleanConsumer action);
    public abstract void forEach(Consumer<? super Boolean> action);
    public abstract boolean removeIf(BooleanPredicate filter);
    public abstract boolean removeIf(Predicate<? super Boolean> filter);
    public abstract OmniIterator.OfBoolean iterator();
    @Override
    public abstract Boolean[] toArray();
    public abstract boolean[] toBooleanArray();
    public abstract double[] toDoubleArray();
    public abstract float[] toFloatArray();
    public abstract long[] toLongArray();
    public abstract int[] toIntArray();
    public abstract short[] toShortArray();
    public abstract byte[] toByteArray();
    public abstract char[] toCharArray();
    @Override
    public default boolean contains(short val)
    {
      return contains((int)val);
    }
    @Override
    public default boolean removeVal(short val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean contains(byte val)
    {
      return contains((int)val);
    }
    @Override
    public default boolean contains(char val)
    {
      return contains((int)val);
    }
    @Override
    public default boolean removeVal(byte val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeVal(char val)
    {
      return removeVal((int)val);
    }
  }
  public abstract interface OfByte extends OfPrimitive<Byte>,Iterable<Byte>
  ,ByteInput<Byte>,ByteOutput<Byte>
  {
    @Override
    public default boolean contains(Byte val)
    {
      return val!=null && contains(
        val.byteValue()
      );
    }
    @Override
    public default boolean contains(Character val)
    {
      return val!=null && contains(
        val.charValue()
      );
    }
    @Override
    public default boolean contains(Short val)
    {
      return val!=null && contains(
        val.intValue()
      );
    }
    @Override
    public default boolean removeVal(Byte val)
    {
      return val!=null && removeVal(
        val.byteValue()
      );
    }
    @Override
    public default boolean removeVal(Character val)
    {
      return val!=null && removeVal(
        val.charValue()
      );
    }
    @Override
    public default boolean removeVal(Short val)
    {
      return val!=null && removeVal(
        val.intValue()
      );
    }
    @Override
    public abstract boolean add(boolean val);
    public abstract boolean add(Byte val);
    public abstract void forEach(ByteConsumer action);
    public abstract void forEach(Consumer<? super Byte> action);
    public abstract boolean removeIf(BytePredicate filter);
    public abstract boolean removeIf(Predicate<? super Byte> filter);
    public abstract OmniIterator.OfByte iterator();
    @Override
    public abstract Byte[] toArray();
    public abstract byte[] toByteArray();
    public abstract double[] toDoubleArray();
    public abstract float[] toFloatArray();
    public abstract long[] toLongArray();
    public abstract int[] toIntArray();
    public abstract short[] toShortArray();
    @Override
    public default boolean contains(short val)
    {
      return contains((int)val);
    }
    @Override
    public default boolean removeVal(short val)
    {
      return removeVal((int)val);
    }
    public abstract boolean add(byte val);
    public default boolean add(Boolean val)
    {
      return add(val.booleanValue());
    }
  }
  public abstract interface OfChar extends OfPrimitive<Character>,Iterable<Character>
  ,CharInput<Character>,CharOutput<Character>
  {
    @Override
    public default boolean contains(Byte val)
    {
      return val!=null && contains(
        val.byteValue()
      );
    }
    @Override
    public default boolean contains(Character val)
    {
      return val!=null && contains(
        val.charValue()
      );
    }
    @Override
    public default boolean contains(Short val)
    {
      return val!=null && contains(
        val.shortValue()
      );
    }
    @Override
    public default boolean removeVal(Byte val)
    {
      return val!=null && removeVal(
        val.byteValue()
      );
    }
    @Override
    public default boolean removeVal(Character val)
    {
      return val!=null && removeVal(
        val.charValue()
      );
    }
    @Override
    public default boolean removeVal(Short val)
    {
      return val!=null && removeVal(
        val.shortValue()
      );
    }
    @Override
    public abstract boolean add(boolean val);
    public abstract boolean add(Character val);
    public abstract void forEach(CharConsumer action);
    public abstract void forEach(Consumer<? super Character> action);
    public abstract boolean removeIf(CharPredicate filter);
    public abstract boolean removeIf(Predicate<? super Character> filter);
    public abstract OmniIterator.OfChar iterator();
    @Override
    public abstract Character[] toArray();
    public abstract char[] toCharArray();
    public abstract double[] toDoubleArray();
    public abstract float[] toFloatArray();
    public abstract long[] toLongArray();
    public abstract int[] toIntArray();
    public abstract boolean add(char val);
    public default boolean add(Boolean val)
    {
      return add(val.booleanValue());
    }
  }
  public abstract interface OfShort extends OfPrimitive<Short>,Iterable<Short>
  ,ShortInput<Short>,ShortOutput<Short>
  {
    @Override
    public default boolean contains(Byte val)
    {
      return val!=null && contains(
        val.shortValue()
      );
    }
    @Override
    public default boolean contains(Character val)
    {
      return val!=null && contains(
        val.charValue()
      );
    }
    @Override
    public default boolean contains(Short val)
    {
      return val!=null && contains(
        val.shortValue()
      );
    }
    @Override
    public default boolean removeVal(Byte val)
    {
      return val!=null && removeVal(
        val.shortValue()
      );
    }
    @Override
    public default boolean removeVal(Character val)
    {
      return val!=null && removeVal(
        val.charValue()
      );
    }
    @Override
    public default boolean removeVal(Short val)
    {
      return val!=null && removeVal(
        val.shortValue()
      );
    }
    @Override
    public abstract boolean add(boolean val);
    public abstract boolean add(Short val);
    public abstract void forEach(ShortConsumer action);
    public abstract void forEach(Consumer<? super Short> action);
    public abstract boolean removeIf(ShortPredicate filter);
    public abstract boolean removeIf(Predicate<? super Short> filter);
    public abstract OmniIterator.OfShort iterator();
    @Override
    public abstract Short[] toArray();
    public abstract short[] toShortArray();
    public abstract double[] toDoubleArray();
    public abstract float[] toFloatArray();
    public abstract long[] toLongArray();
    public abstract int[] toIntArray();
    public default boolean add(Byte val)
    {
      return add((byte)val.byteValue());
    }
    public abstract boolean add(short val);
    public default boolean add(Boolean val)
    {
      return add(val.booleanValue());
    }
  }
  public abstract interface OfInt extends OfPrimitive<Integer>,Iterable<Integer>
  ,IntInput<Integer>,IntOutput<Integer>
  {
    @Override
    public default boolean contains(Byte val)
    {
      return val!=null && contains(
        val.intValue()
      );
    }
    @Override
    public default boolean contains(Character val)
    {
      return val!=null && contains(
        val.charValue()
      );
    }
    @Override
    public default boolean contains(Short val)
    {
      return val!=null && contains(
        val.intValue()
      );
    }
    @Override
    public default boolean removeVal(Byte val)
    {
      return val!=null && removeVal(
        val.intValue()
      );
    }
    @Override
    public default boolean removeVal(Character val)
    {
      return val!=null && removeVal(
        val.charValue()
      );
    }
    @Override
    public default boolean removeVal(Short val)
    {
      return val!=null && removeVal(
        val.intValue()
      );
    }
    @Override
    public abstract boolean add(boolean val);
    public abstract boolean add(Integer val);
    public abstract void forEach(IntConsumer action);
    public abstract void forEach(Consumer<? super Integer> action);
    public abstract boolean removeIf(IntPredicate filter);
    public abstract boolean removeIf(Predicate<? super Integer> filter);
    public abstract OmniIterator.OfInt iterator();
    @Override
    public abstract Integer[] toArray();
    public abstract int[] toIntArray();
    public abstract double[] toDoubleArray();
    public abstract float[] toFloatArray();
    public abstract long[] toLongArray();
    @Override
    public default boolean contains(short val)
    {
      return contains((int)val);
    }
    @Override
    public default boolean removeVal(short val)
    {
      return removeVal((int)val);
    }
    public default boolean add(Character val)
    {
      return add((char)val.charValue());
    }
    public default boolean add(short val)
    {
      return add((int)val);
    }
    public default boolean add(Short val)
    {
      return add((int)val.shortValue());
    }
    public default boolean add(Byte val)
    {
      return add((byte)val.byteValue());
    }
    public abstract boolean add(int val);
    public default boolean add(Boolean val)
    {
      return add(val.booleanValue());
    }
  }
  public abstract interface OfLong extends OfPrimitive<Long>,Iterable<Long>
  ,LongInput<Long>,LongOutput<Long>
  {
    @Override
    public default boolean contains(Byte val)
    {
      return val!=null && contains(
        val.intValue()
      );
    }
    @Override
    public default boolean contains(Character val)
    {
      return val!=null && contains(
        val.charValue()
      );
    }
    @Override
    public default boolean contains(Short val)
    {
      return val!=null && contains(
        val.intValue()
      );
    }
    @Override
    public default boolean removeVal(Byte val)
    {
      return val!=null && removeVal(
        val.intValue()
      );
    }
    @Override
    public default boolean removeVal(Character val)
    {
      return val!=null && removeVal(
        val.charValue()
      );
    }
    @Override
    public default boolean removeVal(Short val)
    {
      return val!=null && removeVal(
        val.intValue()
      );
    }
    @Override
    public abstract boolean add(boolean val);
    public abstract boolean add(Long val);
    public abstract void forEach(LongConsumer action);
    public abstract void forEach(Consumer<? super Long> action);
    public abstract boolean removeIf(LongPredicate filter);
    public abstract boolean removeIf(Predicate<? super Long> filter);
    public abstract OmniIterator.OfLong iterator();
    @Override
    public abstract Long[] toArray();
    public abstract long[] toLongArray();
    public abstract double[] toDoubleArray();
    public abstract float[] toFloatArray();
    @Override
    public default boolean contains(short val)
    {
      return contains((int)val);
    }
    @Override
    public default boolean removeVal(short val)
    {
      return removeVal((int)val);
    }
    public default boolean add(Character val)
    {
      return add((char)val.charValue());
    }
    public default boolean add(short val)
    {
      return add((int)val);
    }
    public default boolean add(Short val)
    {
      return add((int)val.shortValue());
    }
    public default boolean add(Byte val)
    {
      return add((byte)val.byteValue());
    }
    public abstract boolean add(int val);
    public default boolean add(Integer val)
    {
      return add(val.intValue());
    }
    public abstract boolean add(long val);
    public default boolean add(Boolean val)
    {
      return add(val.booleanValue());
    }
  }
  public abstract interface OfFloat extends OfPrimitive<Float>,Iterable<Float>
  ,FloatInput<Float>,FloatOutput<Float>
  {
    @Override
    public default boolean contains(Byte val)
    {
      return val!=null && contains(
        val.shortValue()
      );
    }
    @Override
    public default boolean contains(Character val)
    {
      return val!=null && contains(
        val.charValue()
      );
    }
    @Override
    public default boolean contains(Short val)
    {
      return val!=null && contains(
        val.shortValue()
      );
    }
    @Override
    public default boolean removeVal(Byte val)
    {
      return val!=null && removeVal(
        val.shortValue()
      );
    }
    @Override
    public default boolean removeVal(Character val)
    {
      return val!=null && removeVal(
        val.charValue()
      );
    }
    @Override
    public default boolean removeVal(Short val)
    {
      return val!=null && removeVal(
        val.shortValue()
      );
    }
    @Override
    public abstract boolean add(boolean val);
    public abstract boolean add(Float val);
    public abstract void forEach(FloatConsumer action);
    public abstract void forEach(Consumer<? super Float> action);
    public abstract boolean removeIf(FloatPredicate filter);
    public abstract boolean removeIf(Predicate<? super Float> filter);
    public abstract OmniIterator.OfFloat iterator();
    @Override
    public abstract Float[] toArray();
    public abstract float[] toFloatArray();
    public abstract double[] toDoubleArray();
    public default boolean contains(byte val){
      return contains((short)val);
    }
    public default boolean removeVal(byte val){
      return removeVal((short)val);
    }
    public default boolean add(byte val)
    {
      return add((short)val);
    }
    public default boolean add(Byte val)
    {
      return add((short)val.byteValue());
    }
    public abstract boolean add(char val);
    public default boolean add(Character val)
    {
      return add(val.charValue());
    }
    public abstract boolean add(long val);
    public default boolean add(Long val)
    {
      return add(val.longValue());
    }
    public abstract boolean add(short val);
    public default boolean add(Short val)
    {
      return add(val.shortValue());
    }
    public abstract boolean add(int val);
    public default boolean add(Integer val)
    {
      return add(val.intValue());
    }
    public abstract boolean add(float val);
    public default boolean add(Boolean val)
    {
      return add(val.booleanValue());
    }
  }
  public abstract interface OfDouble extends OfPrimitive<Double>,Iterable<Double>
  ,FloatInput<Double>,DoubleOutput<Double>
  {
    @Override
    public default boolean contains(Byte val)
    {
      return val!=null && contains(
        val.intValue()
      );
    }
    @Override
    public default boolean contains(Character val)
    {
      return val!=null && contains(
        (int)val.charValue()
      );
    }
    @Override
    public default boolean contains(Short val)
    {
      return val!=null && contains(
        val.intValue()
      );
    }
    @Override
    public default boolean removeVal(Byte val)
    {
      return val!=null && removeVal(
        val.intValue()
      );
    }
    @Override
    public default boolean removeVal(Character val)
    {
      return val!=null && removeVal(
        (int)val.charValue()
      );
    }
    @Override
    public default boolean removeVal(Short val)
    {
      return val!=null && removeVal(
        val.intValue()
      );
    }
    @Override
    public abstract boolean add(boolean val);
    public abstract boolean add(Double val);
    public abstract void forEach(DoubleConsumer action);
    public abstract void forEach(Consumer<? super Double> action);
    public abstract boolean removeIf(DoublePredicate filter);
    public abstract boolean removeIf(Predicate<? super Double> filter);
    public abstract OmniIterator.OfDouble iterator();
    @Override
    public abstract Double[] toArray();
    public abstract double[] toDoubleArray();
    public abstract boolean add(float val);
    public default boolean add(Float val)
    {
      return add(val.floatValue());
    }
    public default boolean contains(byte val){
      return contains((int)val);
    }
    public default boolean removeVal(byte val){
      return removeVal((int)val);
    }
    public default boolean contains(short val){
      return contains((int)val);
    }
    public default boolean removeVal(short val){
      return removeVal((int)val);
    }
    public default boolean contains(char val){
      return contains((int)val);
    }
    public default boolean removeVal(char val){
      return removeVal((int)val);
    }
    public default boolean add(byte val)
    {
      return add((int)val);
    }
    public default boolean add(Byte val)
    {
      return add(val.intValue());
    }
    public default boolean add(char val){
      return add((int)val);
    }
    public default boolean add(Character val)
    {
      return add((int)val.charValue());
    }
    public abstract boolean add(long val);
    public default boolean add(Long val)
    {
      return add(val.longValue());
    }
    public default boolean add(short val){
      return add((int)val);
    }
    public default boolean add(Short val)
    {
      return add(val.intValue());
    }
    public abstract boolean add(int val);
    public default boolean add(Integer val)
    {
      return add(val.intValue());
    }
    public abstract boolean add(double val);
    public default boolean add(Boolean val)
    {
      return add(val.booleanValue());
    }
  }
  public abstract interface DoubleOutput<E> extends OmniCollection<E>{
    public abstract double[] toDoubleArray();
    public abstract OmniIterator.DoubleOutput<E> iterator(); 
  }
  public abstract interface FloatOutput<E> extends DoubleOutput<E>{
    public abstract float[] toFloatArray();
    public abstract OmniIterator.FloatOutput<E> iterator(); 
  }
  public abstract interface LongOutput<E> extends FloatOutput<E>{
    public abstract long[] toLongArray();
    public abstract OmniIterator.LongOutput<E> iterator(); 
  }
  public abstract interface IntOutput<E> extends LongOutput<E>{
    public abstract int[] toIntArray();
    public abstract OmniIterator.IntOutput<E> iterator(); 
  }
  public abstract interface ShortOutput<E> extends IntOutput<E>{
    public abstract short[] toShortArray();
    public abstract OmniIterator.ShortOutput<E> iterator(); 
  }
  public abstract interface CharOutput<E> extends IntOutput<E>{
    public abstract char[] toCharArray();
    public abstract OmniIterator.CharOutput<E> iterator(); 
  }
  public abstract interface ByteOutput<E> extends ShortOutput<E>{
    public abstract byte[] toByteArray();
    public abstract OmniIterator.ByteOutput<E> iterator(); 
  }
  public abstract interface BooleanInput<E> extends OmniCollection<E>{
    public abstract boolean add(boolean val);
    public abstract boolean add(Boolean val);
  }
  public abstract interface ByteInput<E> extends BooleanInput<E>{
    public abstract boolean add(byte val);
    public abstract boolean add(Byte val);
  }
  public abstract interface CharInput<E> extends BooleanInput<E>{
    public abstract boolean add(char val);
    public abstract boolean add(Character val);
  }
  public abstract interface ShortInput<E> extends ByteInput<E>{
    public abstract boolean add(short val);
    public abstract boolean add(Short val);
  }
  public abstract interface IntInput<E> extends CharInput<E>,ShortInput<E>{
    public abstract boolean add(int val);
    public abstract boolean add(Integer val);
  }
  public abstract interface LongInput<E> extends IntInput<E>{
    public abstract boolean add(long val);
    public abstract boolean add(Long val);
  }
  public abstract interface FloatInput<E> extends LongInput<E>{
    public abstract boolean add(float val);
    public abstract boolean add(Float val);
  }
  public abstract interface OfRef<E> extends OmniCollection<E>,Iterable<E>
  {
    public abstract boolean add(E val);
    public abstract void forEach(Consumer<? super E> action);
    public abstract OmniIterator.OfRef<E> iterator();
    public abstract boolean removeIf(Predicate<? super E> filter);
  }
}
