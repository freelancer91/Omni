package omni.api;
import java.util.ListIterator;
import omni.util.TypeUtil;
public abstract interface OmniListIterator<E> extends OmniIterator<E>,ListIterator<E>
{
  //@Override
  //public abstract boolean hasPrevious();
  //@Override
  //public abstract int nextIndex();
  //@Override
  //public abstract int previousIndex();
  public abstract interface OfBoolean extends OmniListIterator<Boolean>,OmniIterator.OfBoolean
  {
    public abstract void add(boolean val);
    @Override
    public default void add(Boolean val)
    {
      add(val.booleanValue());
    }
    public abstract void set(boolean val);
    @Override
    public default void set(Boolean val)
    {
      set(val.booleanValue());
    }
    public abstract boolean previousBoolean();
    @Override
    public default Boolean next()
    {
      return nextBoolean();
    }
    @Override
    public default Boolean previous()
    {
      return previousBoolean();
    }
    public default byte previousByte(){
      return TypeUtil.castToByte(previousBoolean());
    }
    public default char nextChar(){
      return TypeUtil.castToChar(previousBoolean());
    }
    public default double nextDouble(){
      return TypeUtil.castToDouble(previousBoolean());
    }
    public default float nextFloat(){
      return TypeUtil.castToFloat(previousBoolean());
    }
    public default int nextInt(){
      return TypeUtil.castToByte(previousBoolean());
    }
    public default long nextLong(){
      return TypeUtil.castToLong(previousBoolean());
    }
    public default short nextShort(){
      return TypeUtil.castToByte(previousBoolean());
    }
  }
  public abstract interface OfByte extends OmniListIterator<Byte>,OmniIterator.OfByte
  {
    public abstract void add(byte val);
    @Override
    public default void add(Byte val)
    {
      add(val.byteValue());
    }
    public abstract void set(byte val);
    @Override
    public default void set(Byte val)
    {
      set(val.byteValue());
    }
    public abstract byte previousByte();
    @Override
    public default Byte next()
    {
      return nextByte();
    }
    @Override
    public default Byte previous()
    {
      return previousByte();
    }
    public default double previousDouble()
    {
      return previousByte();
    }
    public default float previousFloat()
    {
      return previousByte();
    }
    public default long previousLong()
    {
      return previousByte();
    }
    public default int previousInt()
    {
      return previousByte();
    }
    public default short previousShort()
    {
      return previousByte();
    }
  }
  public abstract interface OfChar extends OmniListIterator<Character>,OmniIterator.OfChar
  {
    public abstract void add(char val);
    @Override
    public default void add(Character val)
    {
      add(val.charValue());
    }
    public abstract void set(char val);
    @Override
    public default void set(Character val)
    {
      set(val.charValue());
    }
    public abstract char previousChar();
    @Override
    public default Character next()
    {
      return nextChar();
    }
    @Override
    public default Character previous()
    {
      return previousChar();
    }
    public default double previousDouble()
    {
      return previousChar();
    }
    public default float previousFloat()
    {
      return previousChar();
    }
    public default long previousLong()
    {
      return previousChar();
    }
    public default int previousInt()
    {
      return previousChar();
    }
  }
  public abstract interface OfShort extends OmniListIterator<Short>,OmniIterator.OfShort
  {
    public abstract void add(short val);
    @Override
    public default void add(Short val)
    {
      add(val.shortValue());
    }
    public abstract void set(short val);
    @Override
    public default void set(Short val)
    {
      set(val.shortValue());
    }
    public abstract short previousShort();
    @Override
    public default Short next()
    {
      return nextShort();
    }
    @Override
    public default Short previous()
    {
      return previousShort();
    }
    public default double previousDouble()
    {
      return previousShort();
    }
    public default float previousFloat()
    {
      return previousShort();
    }
    public default long previousLong()
    {
      return previousShort();
    }
    public default int previousInt()
    {
      return previousShort();
    }
  }
  public abstract interface OfInt extends OmniListIterator<Integer>,OmniIterator.OfInt
  {
    public abstract void add(int val);
    @Override
    public default void add(Integer val)
    {
      add(val.intValue());
    }
    public abstract void set(int val);
    @Override
    public default void set(Integer val)
    {
      set(val.intValue());
    }
    public abstract int previousInt();
    @Override
    public default Integer next()
    {
      return nextInt();
    }
    @Override
    public default Integer previous()
    {
      return previousInt();
    }
    public default double previousDouble()
    {
      return previousInt();
    }
    public default float previousFloat()
    {
      return previousInt();
    }
    public default long previousLong()
    {
      return previousInt();
    }
  }
  public abstract interface OfLong extends OmniListIterator<Long>,OmniIterator.OfLong
  {
    public abstract void add(long val);
    @Override
    public default void add(Long val)
    {
      add(val.longValue());
    }
    public abstract void set(long val);
    @Override
    public default void set(Long val)
    {
      set(val.longValue());
    }
    public abstract long previousLong();
    @Override
    public default Long next()
    {
      return nextLong();
    }
    @Override
    public default Long previous()
    {
      return previousLong();
    }
    public default double previousDouble()
    {
      return previousLong();
    }
    public default float previousFloat()
    {
      return previousLong();
    }
  }
  public abstract interface OfFloat extends OmniListIterator<Float>,OmniIterator.OfFloat
  {
    public abstract void add(float val);
    @Override
    public default void add(Float val)
    {
      add(val.floatValue());
    }
    public abstract void set(float val);
    @Override
    public default void set(Float val)
    {
      set(val.floatValue());
    }
    public abstract float previousFloat();
    @Override
    public default Float next()
    {
      return nextFloat();
    }
    @Override
    public default Float previous()
    {
      return previousFloat();
    }
    public default double previousDouble()
    {
      return previousFloat();
    }
  }
  public abstract interface OfDouble extends OmniListIterator<Double>,OmniIterator.OfDouble
  {
    public abstract void add(double val);
    @Override
    public default void add(Double val)
    {
      add(val.doubleValue());
    }
    public abstract void set(double val);
    @Override
    public default void set(Double val)
    {
      set(val.doubleValue());
    }
    public abstract double previousDouble();
    @Override
    public default Double next()
    {
      return nextDouble();
    }
    @Override
    public default Double previous()
    {
      return previousDouble();
    }
  }
  public abstract interface OfRef<E> extends OmniListIterator<E>,OmniIterator.OfRef<E>{
    //@Override
    //public abstract void add(E val);
    //@Override
    //public abstract E previous();
    //@Override
    //public abstract void set(E val);
  }
}
