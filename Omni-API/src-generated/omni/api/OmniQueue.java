package omni.api;
import omni.util.PeekAndPollIfc;
import omni.util.TypeUtil;
public abstract interface OmniQueue extends OmniCollection
{
  public abstract interface OfBoolean extends OfPrimitive,OmniCollection,OmniQueue,OmniCollection.OfPrimitive,OmniCollection.OfBoolean,PeekAndPollIfc.BooleanOutput<Boolean>
  ,BooleanInput,ByteOutput<Boolean>,CharOutput<Boolean>
  {
    public abstract boolean booleanElement();
    public abstract Boolean element();
    public abstract boolean removeBoolean();
    public abstract Boolean remove();
    public default byte byteElement(){
      return TypeUtil.castToByte(booleanElement());
    }
    public default char charElement(){
      return TypeUtil.castToChar(booleanElement());
    }
    public default double doubleElement(){
      return TypeUtil.castToDouble(booleanElement());
    }
    public default float floatElement(){
      return TypeUtil.castToFloat(booleanElement());
    }
    public default int intElement(){
      return TypeUtil.castToByte(booleanElement());
    }
    public default long longElement(){
      return TypeUtil.castToLong(booleanElement());
    }
    public default byte removeByte(){
      return TypeUtil.castToByte(removeBoolean());
    }
    public default char removeChar(){
      return TypeUtil.castToChar(removeBoolean());
    }
    public default double removeDouble(){
      return TypeUtil.castToDouble(removeBoolean());
    }
    public default float removeFloat(){
      return TypeUtil.castToFloat(removeBoolean());
    }
    public default int removeInt(){
      return TypeUtil.castToByte(removeBoolean());
    }
    public default long removeLong(){
      return TypeUtil.castToLong(removeBoolean());
    }
    public default short removeShort(){
      return TypeUtil.castToByte(removeBoolean());
    }
    public default short shortElement(){
      return TypeUtil.castToByte(booleanElement());
    }
    @Override
    public abstract boolean offer(boolean val);
    public abstract boolean offer(Boolean val);
  }
  public abstract interface OfByte extends OfPrimitive,OmniCollection,OmniQueue,OmniCollection.OfPrimitive,OmniCollection.OfByte,PeekAndPollIfc.ByteOutput<Byte>
  ,ByteInput,ByteOutput<Byte>
  {
    public abstract byte byteElement();
    public abstract Byte element();
    public abstract byte removeByte();
    public abstract Byte remove();
    public default double doubleElement()
    {
      return byteElement();
    }
    public default double removeDouble()
    {
      return removeByte();
    }
    public default float floatElement()
    {
      return byteElement();
    }
    public default float removeFloat()
    {
      return removeByte();
    }
    public default long longElement()
    {
      return byteElement();
    }
    public default long removeLong()
    {
      return removeByte();
    }
    public default int intElement()
    {
      return byteElement();
    }
    public default int removeInt()
    {
      return removeByte();
    }
    public default short shortElement()
    {
      return byteElement();
    }
    public default short removeShort()
    {
      return removeByte();
    }
    public abstract boolean offer(byte val);
    public abstract boolean offer(Byte val);
    @Override
    public default boolean offer(boolean val)
    {
      return offer((byte)TypeUtil.
      castToByte
      (val));
    }
    public default boolean offer(Boolean val)
    {
      return offer((byte)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
  }
  public abstract interface OfChar extends OfPrimitive,OmniCollection,OmniQueue,OmniCollection.OfPrimitive,OmniCollection.OfChar,PeekAndPollIfc.CharOutput<Character>
  ,CharInput,CharOutput<Character>
  {
    public abstract char charElement();
    public abstract Character element();
    public abstract char removeChar();
    public abstract Character remove();
    public default double doubleElement()
    {
      return charElement();
    }
    public default double removeDouble()
    {
      return removeChar();
    }
    public default float floatElement()
    {
      return charElement();
    }
    public default float removeFloat()
    {
      return removeChar();
    }
    public default long longElement()
    {
      return charElement();
    }
    public default long removeLong()
    {
      return removeChar();
    }
    public default int intElement()
    {
      return charElement();
    }
    public default int removeInt()
    {
      return removeChar();
    }
    public abstract boolean offer(char val);
    public abstract boolean offer(Character val);
    @Override
    public default boolean offer(boolean val)
    {
      return offer((char)TypeUtil.
      castToChar
      (val));
    }
    public default boolean offer(Boolean val)
    {
      return offer((char)TypeUtil.
      castToChar
      (val.booleanValue()));
    }
  }
  public abstract interface OfShort extends OfPrimitive,OmniCollection,OmniQueue,OmniCollection.OfPrimitive,OmniCollection.OfShort,PeekAndPollIfc.ShortOutput<Short>
  ,ShortInput,ShortOutput<Short>
  {
    public abstract short shortElement();
    public abstract Short element();
    public abstract short removeShort();
    public abstract Short remove();
    public default double doubleElement()
    {
      return shortElement();
    }
    public default double removeDouble()
    {
      return removeShort();
    }
    public default float floatElement()
    {
      return shortElement();
    }
    public default float removeFloat()
    {
      return removeShort();
    }
    public default long longElement()
    {
      return shortElement();
    }
    public default long removeLong()
    {
      return removeShort();
    }
    public default int intElement()
    {
      return shortElement();
    }
    public default int removeInt()
    {
      return removeShort();
    }
    public abstract boolean offer(short val);
    public abstract boolean offer(Short val);
    @Override
    public default boolean offer(boolean val)
    {
      return offer((short)TypeUtil.
      castToByte
      (val));
    }
    public default boolean offer(Boolean val)
    {
      return offer((short)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default boolean offer(byte val)
    {
      return offer((short)val);
    }
    public default boolean offer(Byte val)
    {
      return offer((short)val.byteValue());
    }
  }
  public abstract interface OfInt extends OfPrimitive,OmniCollection,OmniQueue,OmniCollection.OfPrimitive,OmniCollection.OfInt,PeekAndPollIfc.IntOutput<Integer>
  ,IntInput,IntOutput<Integer>
  {
    public abstract int intElement();
    public abstract Integer element();
    public abstract int removeInt();
    public abstract Integer remove();
    public default double doubleElement()
    {
      return intElement();
    }
    public default double removeDouble()
    {
      return removeInt();
    }
    public default float floatElement()
    {
      return intElement();
    }
    public default float removeFloat()
    {
      return removeInt();
    }
    public default long longElement()
    {
      return intElement();
    }
    public default long removeLong()
    {
      return removeInt();
    }
    public abstract boolean offer(int val);
    public abstract boolean offer(Integer val);
    @Override
    public default boolean offer(boolean val)
    {
      return offer((int)TypeUtil.
      castToByte
      (val));
    }
    public default boolean offer(Boolean val)
    {
      return offer((int)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default boolean offer(char val)
    {
      return offer((int)val);
    }
    public default boolean offer(Character val)
    {
      return offer((int)val.charValue());
    }
    public default boolean offer(byte val)
    {
      return offer((int)val);
    }
    public default boolean offer(Byte val)
    {
      return offer((int)val.byteValue());
    }
    public default boolean offer(short val)
    {
      return offer((int)val);
    }
    public default boolean offer(Short val)
    {
      return offer((int)val.shortValue());
    }
  }
  public abstract interface OfLong extends OfPrimitive,OmniCollection,OmniQueue,OmniCollection.OfPrimitive,OmniCollection.OfLong,PeekAndPollIfc.LongOutput<Long>
  ,LongInput,LongOutput<Long>
  {
    public abstract long longElement();
    public abstract Long element();
    public abstract long removeLong();
    public abstract Long remove();
    public default double doubleElement()
    {
      return longElement();
    }
    public default double removeDouble()
    {
      return removeLong();
    }
    public default float floatElement()
    {
      return longElement();
    }
    public default float removeFloat()
    {
      return removeLong();
    }
    public abstract boolean offer(long val);
    public abstract boolean offer(Long val);
    @Override
    public default boolean offer(boolean val)
    {
      return offer((long)TypeUtil.
      castToLong
      (val));
    }
    public default boolean offer(Boolean val)
    {
      return offer((long)TypeUtil.
      castToLong
      (val.booleanValue()));
    }
    public default boolean offer(char val)
    {
      return offer((long)val);
    }
    public default boolean offer(Character val)
    {
      return offer((long)val.charValue());
    }
    public default boolean offer(byte val)
    {
      return offer((long)val);
    }
    public default boolean offer(Byte val)
    {
      return offer((long)val.byteValue());
    }
    public default boolean offer(short val)
    {
      return offer((long)val);
    }
    public default boolean offer(Short val)
    {
      return offer((long)val.shortValue());
    }
    public default boolean offer(int val)
    {
      return offer((long)val);
    }
    public default boolean offer(Integer val)
    {
      return offer((long)val.intValue());
    }
  }
  public abstract interface OfFloat extends OfPrimitive,OmniCollection,OmniQueue,OmniCollection.OfPrimitive,OmniCollection.OfFloat,PeekAndPollIfc.FloatOutput<Float>
  ,FloatInput,FloatOutput<Float>
  {
    public abstract float floatElement();
    public abstract Float element();
    public abstract float removeFloat();
    public abstract Float remove();
    public default double doubleElement()
    {
      return floatElement();
    }
    public default double removeDouble()
    {
      return removeFloat();
    }
    public abstract boolean offer(float val);
    public abstract boolean offer(Float val);
    @Override
    public default boolean offer(boolean val)
    {
      return offer((float)TypeUtil.
      castToFloat
      (val));
    }
    public default boolean offer(Boolean val)
    {
      return offer((float)TypeUtil.
      castToFloat
      (val.booleanValue()));
    }
    public default boolean offer(char val)
    {
      return offer((float)val);
    }
    public default boolean offer(Character val)
    {
      return offer((float)val.charValue());
    }
    public default boolean offer(byte val)
    {
      return offer((float)val);
    }
    public default boolean offer(Byte val)
    {
      return offer((float)val.byteValue());
    }
    public default boolean offer(short val)
    {
      return offer((float)val);
    }
    public default boolean offer(Short val)
    {
      return offer((float)val.shortValue());
    }
    public default boolean offer(int val)
    {
      return offer((float)val);
    }
    public default boolean offer(Integer val)
    {
      return offer((float)val.intValue());
    }
    public default boolean offer(long val)
    {
      return offer((float)val);
    }
    public default boolean offer(Long val)
    {
      return offer((float)val.longValue());
    }
  }
  public abstract interface OfDouble extends OfPrimitive,OmniCollection,OmniQueue,OmniCollection.OfPrimitive,OmniCollection.OfDouble,PeekAndPollIfc.DoubleOutput<Double>
  ,FloatInput,DoubleOutput<Double>
  {
    public abstract double doubleElement();
    public abstract Double element();
    public abstract double removeDouble();
    public abstract Double remove();
    public abstract boolean offer(double val);
    public abstract boolean offer(Double val);
    @Override
    public default boolean offer(boolean val)
    {
      return offer((double)TypeUtil.
      castToDouble
      (val));
    }
    public default boolean offer(Boolean val)
    {
      return offer((double)TypeUtil.
      castToDouble
      (val.booleanValue()));
    }
    public default boolean offer(char val)
    {
      return offer((double)val);
    }
    public default boolean offer(Character val)
    {
      return offer((double)val.charValue());
    }
    public default boolean offer(byte val)
    {
      return offer((double)val);
    }
    public default boolean offer(Byte val)
    {
      return offer((double)val.byteValue());
    }
    public default boolean offer(short val)
    {
      return offer((double)val);
    }
    public default boolean offer(Short val)
    {
      return offer((double)val.shortValue());
    }
    public default boolean offer(int val)
    {
      return offer((double)val);
    }
    public default boolean offer(Integer val)
    {
      return offer((double)val.intValue());
    }
    public default boolean offer(long val)
    {
      return offer((double)val);
    }
    public default boolean offer(Long val)
    {
      return offer((double)val.longValue());
    }
    public default boolean offer(float val)
    {
      return offer((double)val);
    }
    public default boolean offer(Float val)
    {
      return offer((double)val.floatValue());
    }
  }
  public abstract interface OfPrimitive extends OmniQueue,OmniCollection.OfPrimitive
  {
    public abstract boolean offer(boolean val);
  }
  public abstract interface DoubleOutput<E> extends OmniQueue,OmniCollection.DoubleOutput<E>{
    public abstract double doubleElement();
    public abstract double removeDouble();
  }
  public abstract interface FloatOutput<E> extends DoubleOutput<E>,OmniCollection.FloatOutput<E>{
    public abstract float floatElement();
    public abstract float removeFloat();
  }
  public abstract interface LongOutput<E> extends FloatOutput<E>,OmniCollection.LongOutput<E>{
    public abstract long longElement();
    public abstract long removeLong();
  }
  public abstract interface IntOutput<E> extends LongOutput<E>,OmniCollection.IntOutput<E>{
    public abstract int intElement();
    public abstract int removeInt();
  }
  public abstract interface ShortOutput<E> extends IntOutput<E>,OmniCollection.ShortOutput<E>{
    public abstract short shortElement();
    public abstract short removeShort();
  }
  public abstract interface CharOutput<E> extends IntOutput<E>,OmniCollection.CharOutput<E>{
    public abstract char charElement();
    public abstract char removeChar();
  }
  public abstract interface ByteOutput<E> extends ShortOutput<E>,OmniCollection.ByteOutput<E>{
    public abstract byte byteElement();
    public abstract byte removeByte();
  }
  public abstract interface BooleanInput extends OmniQueue,OmniCollection.BooleanInput{
    public abstract boolean offer(boolean val);
    public abstract boolean offer(Boolean val);
  }
  public abstract interface ByteInput extends BooleanInput,OmniCollection.ByteInput{
    public abstract boolean offer(byte val);
    public abstract boolean offer(Byte val);
  }
  public abstract interface CharInput extends BooleanInput,OmniCollection.CharInput{
    public abstract boolean offer(char val);
    public abstract boolean offer(Character val);
  }
  public abstract interface ShortInput extends ByteInput,OmniCollection.ShortInput{
    public abstract boolean offer(short val);
    public abstract boolean offer(Short val);
  }
  public abstract interface IntInput extends CharInput,ShortInput,OmniCollection.IntInput{
    public abstract boolean offer(int val);
    public abstract boolean offer(Integer val);
  }
  public abstract interface LongInput extends IntInput,OmniCollection.LongInput{
    public abstract boolean offer(long val);
    public abstract boolean offer(Long val);
  }
  public abstract interface FloatInput extends LongInput,OmniCollection.FloatInput{
    public abstract boolean offer(float val);
    public abstract boolean offer(Float val);
  }
  public abstract interface OfRef<E> extends OmniCollection,OmniQueue,OmniCollection.OfRef<E>,PeekAndPollIfc<E>
  {
    public abstract E element();
    public abstract boolean offer(E val);
    public abstract E remove();
  }
}
