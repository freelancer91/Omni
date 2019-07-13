package omni.api;
import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import omni.function.BooleanConsumer;
import omni.function.ByteConsumer;
import omni.function.CharConsumer;
import omni.function.FloatConsumer;
import omni.function.ShortConsumer;
import omni.util.TypeUtil;
public interface OmniIterator<E> extends Iterator<E>
{
  //TODO add the rest of the methods
  //public abstract E next();
  //@Override
  //public abstract boolean hasNext();
  public abstract Object clone();
  public abstract interface OfBoolean extends OmniIterator<Boolean>
  ,PrimitiveIterator<Boolean,BooleanConsumer>
  ,CharOutput<Boolean>,ByteOutput<Boolean>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Boolean> action);
    @Override
    public abstract void forEachRemaining(BooleanConsumer action);
    public abstract boolean nextBoolean();
    @Override
    public abstract Boolean next();
    @Override public default byte nextByte(){
      return TypeUtil.castToByte(nextBoolean());
    }
    @Override public default char nextChar(){
      return TypeUtil.castToChar(nextBoolean());
    }
    @Override public default double nextDouble(){
      return TypeUtil.castToDouble(nextBoolean());
    }
    @Override public default float nextFloat(){
      return TypeUtil.castToFloat(nextBoolean());
    }
    @Override public default int nextInt(){
      return TypeUtil.castToByte(nextBoolean());
    }
    @Override public default long nextLong(){
      return TypeUtil.castToLong(nextBoolean());
    }
    @Override public default short nextShort(){
      return TypeUtil.castToByte(nextBoolean());
    }
  }
  public abstract interface OfByte extends OmniIterator<Byte>
  ,PrimitiveIterator<Byte,ByteConsumer>
  ,ByteOutput<Byte>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Byte> action);
    @Override
    public abstract void forEachRemaining(ByteConsumer action);
    public abstract byte nextByte();
    @Override
    public abstract Byte next();
    @Override public default double nextDouble()
    {
      return nextByte();
    }
    @Override public default float nextFloat()
    {
      return nextByte();
    }
    @Override public default long nextLong()
    {
      return nextByte();
    }
    @Override public default int nextInt()
    {
      return nextByte();
    }
    @Override public default short nextShort()
    {
      return nextByte();
    }
  }
  public abstract interface OfChar extends OmniIterator<Character>
  ,PrimitiveIterator<Character,CharConsumer>
  ,CharOutput<Character>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Character> action);
    @Override
    public abstract void forEachRemaining(CharConsumer action);
    public abstract char nextChar();
    @Override
    public abstract Character next();
    @Override public default double nextDouble()
    {
      return nextChar();
    }
    @Override public default float nextFloat()
    {
      return nextChar();
    }
    @Override public default long nextLong()
    {
      return nextChar();
    }
    @Override public default int nextInt()
    {
      return nextChar();
    }
  }
  public abstract interface OfShort extends OmniIterator<Short>
  ,PrimitiveIterator<Short,ShortConsumer>
  ,ShortOutput<Short>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Short> action);
    @Override
    public abstract void forEachRemaining(ShortConsumer action);
    public abstract short nextShort();
    @Override
    public abstract Short next();
    @Override public default double nextDouble()
    {
      return nextShort();
    }
    @Override public default float nextFloat()
    {
      return nextShort();
    }
    @Override public default long nextLong()
    {
      return nextShort();
    }
    @Override public default int nextInt()
    {
      return nextShort();
    }
  }
  public abstract interface OfInt extends OmniIterator<Integer>
  ,PrimitiveIterator.OfInt
  ,IntOutput<Integer>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Integer> action);
    @Override
    public abstract void forEachRemaining(IntConsumer action);
    @Override
    public abstract int nextInt();
    @Override
    public abstract Integer next();
    @Override public default double nextDouble()
    {
      return nextInt();
    }
    @Override public default float nextFloat()
    {
      return nextInt();
    }
    @Override public default long nextLong()
    {
      return nextInt();
    }
  }
  public abstract interface OfLong extends OmniIterator<Long>
  ,PrimitiveIterator.OfLong
  ,LongOutput<Long>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Long> action);
    @Override
    public abstract void forEachRemaining(LongConsumer action);
    @Override
    public abstract long nextLong();
    @Override
    public abstract Long next();
    @Override public default double nextDouble()
    {
      return nextLong();
    }
    @Override public default float nextFloat()
    {
      return nextLong();
    }
  }
  public abstract interface OfFloat extends OmniIterator<Float>
  ,PrimitiveIterator<Float,FloatConsumer>
  ,FloatOutput<Float>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Float> action);
    @Override
    public abstract void forEachRemaining(FloatConsumer action);
    public abstract float nextFloat();
    @Override
    public abstract Float next();
    @Override public default double nextDouble()
    {
      return nextFloat();
    }
  }
  public abstract interface OfDouble extends OmniIterator<Double>
  ,PrimitiveIterator.OfDouble
  ,DoubleOutput<Double>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Double> action);
    @Override
    public abstract void forEachRemaining(DoubleConsumer action);
    @Override
    public abstract double nextDouble();
    @Override
    public abstract Double next();
  }
  public abstract interface DoubleOutput<E> extends OmniIterator<E>{
    public abstract double nextDouble();
  }
  public abstract interface FloatOutput<E> extends DoubleOutput<E>{
    public abstract float nextFloat();
  }
  public abstract interface LongOutput<E> extends FloatOutput<E>{
    public abstract long nextLong();
  }
  public abstract interface IntOutput<E> extends LongOutput<E>{
    public abstract int nextInt();
  }
  public abstract interface ShortOutput<E> extends IntOutput<E>{
    public abstract short nextShort();
  }
  public abstract interface CharOutput<E> extends IntOutput<E>{
    public abstract char nextChar();
  }
  public abstract interface ByteOutput<E> extends ShortOutput<E>{
    public abstract byte nextByte();
  }
  public interface OfRef<E>extends OmniIterator<E>
  {
    //@Override
    //public abstract void forEachRemaining(Consumer<? super E> action);
    //@Override
    //public abstract E next();
  }
}
