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
public interface OmniIterator<E>extends Iterator<E>{
  @Override boolean hasNext();
  interface OfBoolean extends OmniIterator<Boolean>,PrimitiveIterator<Boolean,BooleanConsumer>{
    @Override void forEachRemaining(BooleanConsumer action);
    @Override void forEachRemaining(Consumer<? super Boolean> action);
    @Override Boolean next();
    boolean nextBoolean();
    default byte nextByte(){
      return TypeUtil.castToByte(nextBoolean());
    }
    default char nextChar(){
      return TypeUtil.castToChar(nextBoolean());
    }
    default double nextDouble(){
      return TypeUtil.castToDouble(nextBoolean());
    }
    default float nextFloat(){
      return TypeUtil.castToFloat(nextBoolean());
    }
    default int nextInt(){
      return TypeUtil.castToByte(nextBoolean());
    }
    default long nextLong(){
      return TypeUtil.castToLong(nextBoolean());
    }
    default short nextShort(){
      return TypeUtil.castToByte(nextBoolean());
    }
  }
  interface OfByte extends OmniIterator<Byte>,PrimitiveIterator<Byte,ByteConsumer>{
    @Override void forEachRemaining(ByteConsumer action);
    @Override void forEachRemaining(Consumer<? super Byte> action);
    @Override Byte next();
    byte nextByte();
    default double nextDouble(){
      return nextByte();
    }
    default float nextFloat(){
      return nextByte();
    }
    default int nextInt(){
      return nextByte();
    }
    default long nextLong(){
      return nextByte();
    }
    default short nextShort(){
      return nextByte();
    }
  }
  interface OfChar extends OmniIterator<Character>,PrimitiveIterator<Character,CharConsumer>{
    @Override void forEachRemaining(CharConsumer action);
    @Override void forEachRemaining(Consumer<? super Character> action);
    @Override Character next();
    char nextChar();
    default double nextDouble(){
      return nextChar();
    }
    default float nextFloat(){
      return nextChar();
    }
    default int nextInt(){
      return nextChar();
    }
    default long nextLong(){
      return nextChar();
    }
  }
  interface OfDouble extends OmniIterator<Double>,PrimitiveIterator.OfDouble{
    @Override void forEachRemaining(Consumer<? super Double> action);
    @Override void forEachRemaining(DoubleConsumer action);
    @Override Double next();
    @Override double nextDouble();
  }
  interface OfFloat extends OmniIterator<Float>,PrimitiveIterator<Float,FloatConsumer>{
    @Override void forEachRemaining(Consumer<? super Float> action);
    @Override void forEachRemaining(FloatConsumer action);
    @Override Float next();
    default double nextDouble(){
      return nextFloat();
    }
    float nextFloat();
  }
  interface OfInt extends OmniIterator<Integer>,PrimitiveIterator.OfInt{
    @Override void forEachRemaining(Consumer<? super Integer> action);
    @Override void forEachRemaining(IntConsumer action);
    @Override Integer next();
    default double nextDouble(){
      return nextInt();
    }
    default float nextFloat(){
      return nextInt();
    }
    @Override int nextInt();
    default long nextLong(){
      return nextInt();
    }
  }
  interface OfLong extends OmniIterator<Long>,PrimitiveIterator.OfLong{
    @Override void forEachRemaining(Consumer<? super Long> action);
    @Override void forEachRemaining(LongConsumer action);
    @Override Long next();
    default double nextDouble(){
      return nextLong();
    }
    default float nextFloat(){
      return nextLong();
    }
    @Override long nextLong();
  }
  interface OfRef<E>extends OmniIterator<E>{
    @Override void forEachRemaining(Consumer<? super E> action);
    @Override E next();
  }
  interface OfShort extends OmniIterator<Short>,PrimitiveIterator<Short,ShortConsumer>{
    @Override void forEachRemaining(Consumer<? super Short> action);
    @Override void forEachRemaining(ShortConsumer action);
    @Override Short next();
    default double nextDouble(){
      return nextShort();
    }
    default float nextFloat(){
      return nextShort();
    }
    default int nextInt(){
      return nextShort();
    }
    default long nextLong(){
      return nextShort();
    }
    short nextShort();
  }
}
