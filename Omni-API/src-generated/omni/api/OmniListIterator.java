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
    public abstract void set(boolean val);
    @Override
    public default void add(Boolean val)
    {
      add((boolean)(val));
    }
    @Override
    public default void set(Boolean val)
    {
      set((boolean)(val));
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
    public default char previousChar(){
      return TypeUtil.castToChar(previousBoolean());
    }
    public default double previousDouble(){
      return TypeUtil.castToDouble(previousBoolean());
    }
    public default float previousFloat(){
      return TypeUtil.castToFloat(previousBoolean());
    }
    public default int previousInt(){
      return TypeUtil.castToByte(previousBoolean());
    }
    public default long previousLong(){
      return TypeUtil.castToLong(previousBoolean());
    }
    public default short prevousShort(){
      return TypeUtil.castToByte(previousBoolean());
    }
  }
  public abstract interface OfByte extends OmniListIterator<Byte>,OmniIterator.OfByte
  {
    public abstract void add(byte val);
    public abstract void set(byte val);
    @Override
    public default void add(Byte val)
    {
      add((byte)(val));
    }
    @Override
    public default void set(Byte val)
    {
      set((byte)(val));
    }
    public default void add(boolean val)
    {
      add(TypeUtil.castToByte(val));
    }
    public default void set(boolean val)
    {
      set(TypeUtil.castToByte(val));
    }
    public default void add(Boolean val)
    {
      add(TypeUtil.castToByte(val));
    }
    public default void set(Boolean val)
    {
      set(TypeUtil.castToByte(val));
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
    public abstract void set(char val);
    @Override
    public default void add(Character val)
    {
      add((char)(val));
    }
    @Override
    public default void set(Character val)
    {
      set((char)(val));
    }
    public default void add(boolean val)
    {
      add(TypeUtil.castToChar(val));
    }
    public default void set(boolean val)
    {
      set(TypeUtil.castToChar(val));
    }
    public default void add(Boolean val)
    {
      add(TypeUtil.castToChar(val));
    }
    public default void set(Boolean val)
    {
      set(TypeUtil.castToChar(val));
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
    public abstract void set(short val);
    @Override
    public default void add(Short val)
    {
      add((short)(val));
    }
    @Override
    public default void set(Short val)
    {
      set((short)(val));
    }
    public default void add(boolean val)
    {
      add((short)TypeUtil.castToByte(val));
    }
    public default void set(boolean val)
    {
      set((short)TypeUtil.castToByte(val));
    }
    public default void add(Boolean val)
    {
      add((short)TypeUtil.castToByte(val));
    }
    public default void set(Boolean val)
    {
      set((short)TypeUtil.castToByte(val));
    }
    public default void add(byte val)
    {
      add((short)(val));
    }
    public default void set(byte val)
    {
      set((short)(val));
    }
    public default void add(Byte val)
    {
      add((short)(byte)(val));
    }
    public default void set(Byte val)
    {
      set((short)(byte)(val));
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
    public abstract void set(int val);
    @Override
    public default void add(Integer val)
    {
      add((int)(val));
    }
    @Override
    public default void set(Integer val)
    {
      set((int)(val));
    }
    public default void add(boolean val)
    {
      add((int)TypeUtil.castToByte(val));
    }
    public default void set(boolean val)
    {
      set((int)TypeUtil.castToByte(val));
    }
    public default void add(Boolean val)
    {
      add((int)TypeUtil.castToByte(val));
    }
    public default void set(Boolean val)
    {
      set((int)TypeUtil.castToByte(val));
    }
    public default void add(byte val)
    {
      add((int)(val));
    }
    public default void set(byte val)
    {
      set((int)(val));
    }
    public default void add(Byte val)
    {
      add((int)(byte)(val));
    }
    public default void set(Byte val)
    {
      set((int)(byte)(val));
    }
    public default void add(char val)
    {
      add((int)(val));
    }
    public default void set(char val)
    {
      set((int)(val));
    }
    public default void add(Character val)
    {
      add((int)(char)(val));
    }
    public default void set(Character val)
    {
      set((int)(char)(val));
    }
    public default void add(short val)
    {
      add((int)(val));
    }
    public default void set(short val)
    {
      set((int)(val));
    }
    public default void add(Short val)
    {
      add((int)(short)(val));
    }
    public default void set(Short val)
    {
      set((int)(short)(val));
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
    public abstract void set(long val);
    @Override
    public default void add(Long val)
    {
      add((long)(val));
    }
    @Override
    public default void set(Long val)
    {
      set((long)(val));
    }
    public default void add(boolean val)
    {
      add(TypeUtil.castToLong(val));
    }
    public default void set(boolean val)
    {
      set(TypeUtil.castToLong(val));
    }
    public default void add(Boolean val)
    {
      add(TypeUtil.castToLong(val));
    }
    public default void set(Boolean val)
    {
      set(TypeUtil.castToLong(val));
    }
    public default void add(byte val)
    {
      add((long)(val));
    }
    public default void set(byte val)
    {
      set((long)(val));
    }
    public default void add(Byte val)
    {
      add((long)(byte)(val));
    }
    public default void set(Byte val)
    {
      set((long)(byte)(val));
    }
    public default void add(char val)
    {
      add((long)(val));
    }
    public default void set(char val)
    {
      set((long)(val));
    }
    public default void add(Character val)
    {
      add((long)(char)(val));
    }
    public default void set(Character val)
    {
      set((long)(char)(val));
    }
    public default void add(short val)
    {
      add((long)(val));
    }
    public default void set(short val)
    {
      set((long)(val));
    }
    public default void add(Short val)
    {
      add((long)(short)(val));
    }
    public default void set(Short val)
    {
      set((long)(short)(val));
    }
    public default void add(int val)
    {
      add((long)(val));
    }
    public default void set(int val)
    {
      set((long)(val));
    }
    public default void add(Integer val)
    {
      add((long)(int)(val));
    }
    public default void set(Integer val)
    {
      set((long)(int)(val));
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
    public abstract void set(float val);
    @Override
    public default void add(Float val)
    {
      add((float)(val));
    }
    @Override
    public default void set(Float val)
    {
      set((float)(val));
    }
    public default void add(boolean val)
    {
      add(TypeUtil.castToFloat(val));
    }
    public default void set(boolean val)
    {
      set(TypeUtil.castToFloat(val));
    }
    public default void add(Boolean val)
    {
      add(TypeUtil.castToFloat(val));
    }
    public default void set(Boolean val)
    {
      set(TypeUtil.castToFloat(val));
    }
    public default void add(byte val)
    {
      add((float)(val));
    }
    public default void set(byte val)
    {
      set((float)(val));
    }
    public default void add(Byte val)
    {
      add((float)(byte)(val));
    }
    public default void set(Byte val)
    {
      set((float)(byte)(val));
    }
    public default void add(char val)
    {
      add((float)(val));
    }
    public default void set(char val)
    {
      set((float)(val));
    }
    public default void add(Character val)
    {
      add((float)(char)(val));
    }
    public default void set(Character val)
    {
      set((float)(char)(val));
    }
    public default void add(short val)
    {
      add((float)(val));
    }
    public default void set(short val)
    {
      set((float)(val));
    }
    public default void add(Short val)
    {
      add((float)(short)(val));
    }
    public default void set(Short val)
    {
      set((float)(short)(val));
    }
    public default void add(int val)
    {
      add((float)(val));
    }
    public default void set(int val)
    {
      set((float)(val));
    }
    public default void add(Integer val)
    {
      add((float)(int)(val));
    }
    public default void set(Integer val)
    {
      set((float)(int)(val));
    }
    public default void add(long val)
    {
      add((float)(val));
    }
    public default void set(long val)
    {
      set((float)(val));
    }
    public default void add(Long val)
    {
      add((float)(long)(val));
    }
    public default void set(Long val)
    {
      set((float)(long)(val));
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
    public abstract void set(double val);
    @Override
    public default void add(Double val)
    {
      add((double)(val));
    }
    @Override
    public default void set(Double val)
    {
      set((double)(val));
    }
    public default void add(boolean val)
    {
      add(TypeUtil.castToDouble(val));
    }
    public default void set(boolean val)
    {
      set(TypeUtil.castToDouble(val));
    }
    public default void add(Boolean val)
    {
      add(TypeUtil.castToDouble(val));
    }
    public default void set(Boolean val)
    {
      set(TypeUtil.castToDouble(val));
    }
    public default void add(byte val)
    {
      add((double)(val));
    }
    public default void set(byte val)
    {
      set((double)(val));
    }
    public default void add(Byte val)
    {
      add((double)(byte)(val));
    }
    public default void set(Byte val)
    {
      set((double)(byte)(val));
    }
    public default void add(char val)
    {
      add((double)(val));
    }
    public default void set(char val)
    {
      set((double)(val));
    }
    public default void add(Character val)
    {
      add((double)(char)(val));
    }
    public default void set(Character val)
    {
      set((double)(char)(val));
    }
    public default void add(short val)
    {
      add((double)(val));
    }
    public default void set(short val)
    {
      set((double)(val));
    }
    public default void add(Short val)
    {
      add((double)(short)(val));
    }
    public default void set(Short val)
    {
      set((double)(short)(val));
    }
    public default void add(int val)
    {
      add((double)(val));
    }
    public default void set(int val)
    {
      set((double)(val));
    }
    public default void add(Integer val)
    {
      add((double)(int)(val));
    }
    public default void set(Integer val)
    {
      set((double)(int)(val));
    }
    public default void add(long val)
    {
      add((double)(val));
    }
    public default void set(long val)
    {
      set((double)(val));
    }
    public default void add(Long val)
    {
      add((double)(long)(val));
    }
    public default void set(Long val)
    {
      set((double)(long)(val));
    }
    public default void add(float val)
    {
      add((double)(val));
    }
    public default void set(float val)
    {
      set((double)(val));
    }
    public default void add(Float val)
    {
      add((double)(float)(val));
    }
    public default void set(Float val)
    {
      set((double)(float)(val));
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
