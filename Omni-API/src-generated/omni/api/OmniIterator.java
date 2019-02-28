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
  //@Override
  //public abstract boolean hasNext();
  public abstract interface OfBoolean extends OmniIterator<Boolean>
  ,PrimitiveIterator<Boolean,BooleanConsumer>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Boolean> action);
    @Override
    public abstract void forEachRemaining(BooleanConsumer action);
    public abstract boolean nextBoolean();
    @Override
    public abstract Boolean next();
    public default byte nextByte(){
      return TypeUtil.castToByte(nextBoolean());
    }
    public default char nextChar(){
      return TypeUtil.castToChar(nextBoolean());
    }
    public default double nextDouble(){
      return TypeUtil.castToDouble(nextBoolean());
    }
    public default float nextFloat(){
      return TypeUtil.castToFloat(nextBoolean());
    }
    public default int nextInt(){
      return TypeUtil.castToByte(nextBoolean());
    }
    public default long nextLong(){
      return TypeUtil.castToLong(nextBoolean());
    }
    public default short nextShort(){
      return TypeUtil.castToByte(nextBoolean());
    }
  }
  public abstract interface OfByte extends OmniIterator<Byte>
  ,PrimitiveIterator<Byte,ByteConsumer>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Byte> action);
    @Override
    public abstract void forEachRemaining(ByteConsumer action);
    public abstract byte nextByte();
    @Override
    public abstract Byte next();
    public default double nextDouble()
    {
      return nextByte();
    }
    public default float nextFloat()
    {
      return nextByte();
    }
    public default long nextLong()
    {
      return nextByte();
    }
    public default int nextInt()
    {
      return nextByte();
    }
    public default short nextShort()
    {
      return nextByte();
    }
  }
  public abstract interface OfChar extends OmniIterator<Character>
  ,PrimitiveIterator<Character,CharConsumer>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Character> action);
    @Override
    public abstract void forEachRemaining(CharConsumer action);
    public abstract char nextChar();
    @Override
    public abstract Character next();
    public default double nextDouble()
    {
      return nextChar();
    }
    public default float nextFloat()
    {
      return nextChar();
    }
    public default long nextLong()
    {
      return nextChar();
    }
    public default int nextInt()
    {
      return nextChar();
    }
  }
  public abstract interface OfShort extends OmniIterator<Short>
  ,PrimitiveIterator<Short,ShortConsumer>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Short> action);
    @Override
    public abstract void forEachRemaining(ShortConsumer action);
    public abstract short nextShort();
    @Override
    public abstract Short next();
    public default double nextDouble()
    {
      return nextShort();
    }
    public default float nextFloat()
    {
      return nextShort();
    }
    public default long nextLong()
    {
      return nextShort();
    }
    public default int nextInt()
    {
      return nextShort();
    }
  }
  public abstract interface OfInt extends OmniIterator<Integer>
  ,PrimitiveIterator.OfInt
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Integer> action);
    @Override
    public abstract void forEachRemaining(IntConsumer action);
    @Override
    public abstract int nextInt();
    @Override
    public abstract Integer next();
    public default double nextDouble()
    {
      return nextInt();
    }
    public default float nextFloat()
    {
      return nextInt();
    }
    public default long nextLong()
    {
      return nextInt();
    }
  }
  public abstract interface OfLong extends OmniIterator<Long>
  ,PrimitiveIterator.OfLong
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Long> action);
    @Override
    public abstract void forEachRemaining(LongConsumer action);
    @Override
    public abstract long nextLong();
    @Override
    public abstract Long next();
    public default double nextDouble()
    {
      return nextLong();
    }
    public default float nextFloat()
    {
      return nextLong();
    }
  }
  public abstract interface OfFloat extends OmniIterator<Float>
  ,PrimitiveIterator<Float,FloatConsumer>
  {
    @Override
    public abstract void forEachRemaining(Consumer<? super Float> action);
    @Override
    public abstract void forEachRemaining(FloatConsumer action);
    public abstract float nextFloat();
    @Override
    public abstract Float next();
    public default double nextDouble()
    {
      return nextFloat();
    }
  }
  public abstract interface OfDouble extends OmniIterator<Double>
  ,PrimitiveIterator.OfDouble
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
  public interface OfRef<E>extends OmniIterator<E>
  {
    //@Override
    //public abstract void forEachRemaining(Consumer<? super E> action);
    //@Override
    //public abstract E next();
  }
}
