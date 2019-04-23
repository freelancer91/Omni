package omni.api;
import omni.util.PeekAndPollIfc;
import omni.util.TypeUtil;
public interface OmniDeque extends OmniCollection,OmniQueue,OmniStack
{
  public default boolean removeFirstOccurrence(boolean val)
  {
    return removeVal(val);
  }
  public abstract boolean removeFirstOccurrence(byte val);
  public abstract boolean removeFirstOccurrence(char val);
  public abstract boolean removeFirstOccurrence(short val);
  public default boolean removeFirstOccurrence(int val)
  {
    return removeVal(val);
  }
  public default boolean removeFirstOccurrence(long val)
  {
    return removeVal(val);
  }
  public default boolean removeFirstOccurrence(float val)
  {
    return removeVal(val);
  }
  public default boolean removeFirstOccurrence(double val)
  {
    return removeVal(val);
  }
  public abstract boolean removeFirstOccurrence(Object val);
  public abstract boolean removeLastOccurrence(boolean val);
  public abstract boolean removeLastOccurrence(byte val);
  public abstract boolean removeLastOccurrence(char val);
  public abstract boolean removeLastOccurrence(short val);
  public abstract boolean removeLastOccurrence(int val);
  public abstract boolean removeLastOccurrence(long val);
  public abstract boolean removeLastOccurrence(float val);
  public abstract boolean removeLastOccurrence(double val);
  public abstract boolean removeLastOccurrence(Object val);
  public abstract boolean removeFirstOccurrence(Boolean val);
  public abstract boolean removeFirstOccurrence(Byte val);
  public abstract boolean removeFirstOccurrence(Character val);
  public abstract boolean removeFirstOccurrence(Short val);
  public abstract boolean removeFirstOccurrence(Integer val);
  public abstract boolean removeFirstOccurrence(Long val);
  public abstract boolean removeFirstOccurrence(Float val);
  public abstract boolean removeFirstOccurrence(Double val);
  public abstract boolean removeLastOccurrence(Boolean val);
  public abstract boolean removeLastOccurrence(Byte val);
  public abstract boolean removeLastOccurrence(Character val);
  public abstract boolean removeLastOccurrence(Short val);
  public abstract boolean removeLastOccurrence(Integer val);
  public abstract boolean removeLastOccurrence(Long val);
  public abstract boolean removeLastOccurrence(Float val);
  public abstract boolean removeLastOccurrence(Double val);
  public abstract interface OfPrimitive extends OmniCollection,OmniCollection.OfPrimitive,OmniStack,OmniQueue,OmniStack.OfPrimitive,OmniDeque
  {
    public abstract void addFirst(boolean val);
    public abstract void addLast(boolean val);
    public abstract boolean offerFirst(boolean val);
    public abstract boolean offerLast(boolean val);
    @Override
    public default boolean removeFirstOccurrence(Boolean val)
    {
      return val!=null && removeFirstOccurrence(val.booleanValue());
    }
    @Override
    public default boolean removeLastOccurrence(Boolean val)
    {
      return val!=null && removeLastOccurrence(val.booleanValue());
    }
    @Override
    public default boolean removeFirstOccurrence(Byte val)
    {
      return val!=null && removeFirstOccurrence(val.byteValue());
    }
    @Override
    public default boolean removeLastOccurrence(Byte val)
    {
      return val!=null && removeLastOccurrence(val.byteValue());
    }
    @Override
    public default boolean removeFirstOccurrence(Character val)
    {
      return val!=null && removeFirstOccurrence(val.charValue());
    }
    @Override
    public default boolean removeLastOccurrence(Character val)
    {
      return val!=null && removeLastOccurrence(val.charValue());
    }
    @Override
    public default boolean removeFirstOccurrence(Short val)
    {
      return val!=null && removeFirstOccurrence(val.shortValue());
    }
    @Override
    public default boolean removeLastOccurrence(Short val)
    {
      return val!=null && removeLastOccurrence(val.shortValue());
    }
    @Override
    public default boolean removeFirstOccurrence(Integer val)
    {
      return val!=null && removeFirstOccurrence(val.intValue());
    }
    @Override
    public default boolean removeLastOccurrence(Integer val)
    {
      return val!=null && removeLastOccurrence(val.intValue());
    }
    @Override
    public default boolean removeFirstOccurrence(Long val)
    {
      return val!=null && removeFirstOccurrence(val.longValue());
    }
    @Override
    public default boolean removeLastOccurrence(Long val)
    {
      return val!=null && removeLastOccurrence(val.longValue());
    }
    @Override
    public default boolean removeFirstOccurrence(Float val)
    {
      return val!=null && removeFirstOccurrence(val.floatValue());
    }
    @Override
    public default boolean removeLastOccurrence(Float val)
    {
      return val!=null && removeLastOccurrence(val.floatValue());
    }
    @Override
    public default boolean removeFirstOccurrence(Double val)
    {
      return val!=null && removeFirstOccurrence(val.doubleValue());
    }
    @Override
    public default boolean removeLastOccurrence(Double val)
    {
      return val!=null && removeLastOccurrence(val.doubleValue());
    }
  }
  public abstract interface DoubleOutput<E> extends OmniDeque,OmniQueue.DoubleOutput<E>,OmniStack.DoubleOutput<E>
  {
    public abstract double getFirstDouble();
    public abstract double removeFirstDouble();
    public abstract double getLastDouble();
    public abstract double removeLastDouble();
    public abstract double peekFirstDouble();
    public abstract double peekLastDouble();
    public abstract double pollFirstDouble();
    public abstract double pollLastDouble();
    public abstract OmniIterator.DoubleOutput<E> descendingIterator();
  }
  public abstract interface FloatOutput<E> extends DoubleOutput<E>,OmniQueue.FloatOutput<E>,OmniStack.FloatOutput<E>
  {
    public abstract float getFirstFloat();
    public abstract float removeFirstFloat();
    public abstract float getLastFloat();
    public abstract float removeLastFloat();
    public abstract float peekFirstFloat();
    public abstract float peekLastFloat();
    public abstract float pollFirstFloat();
    public abstract float pollLastFloat();
    public abstract OmniIterator.FloatOutput<E> descendingIterator();
  }
  public abstract interface LongOutput<E> extends FloatOutput<E>,OmniQueue.LongOutput<E>,OmniStack.LongOutput<E>
  {
    public abstract long getFirstLong();
    public abstract long removeFirstLong();
    public abstract long getLastLong();
    public abstract long removeLastLong();
    public abstract long peekFirstLong();
    public abstract long peekLastLong();
    public abstract long pollFirstLong();
    public abstract long pollLastLong();
    public abstract OmniIterator.LongOutput<E> descendingIterator();
  }
  public abstract interface IntOutput<E> extends LongOutput<E>,OmniQueue.IntOutput<E>,OmniStack.IntOutput<E>
  {
    public abstract int getFirstInt();
    public abstract int removeFirstInt();
    public abstract int getLastInt();
    public abstract int removeLastInt();
    public abstract int peekFirstInt();
    public abstract int peekLastInt();
    public abstract int pollFirstInt();
    public abstract int pollLastInt();
    public abstract OmniIterator.IntOutput<E> descendingIterator();
  }
  public abstract interface ShortOutput<E> extends IntOutput<E>,OmniQueue.ShortOutput<E>,OmniStack.ShortOutput<E>
  {
    public abstract short getFirstShort();
    public abstract short removeFirstShort();
    public abstract short getLastShort();
    public abstract short removeLastShort();
    public abstract short peekFirstShort();
    public abstract short peekLastShort();
    public abstract short pollFirstShort();
    public abstract short pollLastShort();
    public abstract OmniIterator.ShortOutput<E> descendingIterator();
  }
  public abstract interface CharOutput<E> extends IntOutput<E>,OmniQueue.CharOutput<E>,OmniStack.CharOutput<E>
  {
    public abstract char getFirstChar();
    public abstract char removeFirstChar();
    public abstract char getLastChar();
    public abstract char removeLastChar();
    public abstract char peekFirstChar();
    public abstract char peekLastChar();
    public abstract char pollFirstChar();
    public abstract char pollLastChar();
    public abstract OmniIterator.CharOutput<E> descendingIterator();
  }
  public abstract interface ByteOutput<E> extends ShortOutput<E>,OmniQueue.ByteOutput<E>,OmniStack.ByteOutput<E>
  {
    public abstract byte getFirstByte();
    public abstract byte removeFirstByte();
    public abstract byte getLastByte();
    public abstract byte removeLastByte();
    public abstract byte peekFirstByte();
    public abstract byte peekLastByte();
    public abstract byte pollFirstByte();
    public abstract byte pollLastByte();
    public abstract OmniIterator.ByteOutput<E> descendingIterator();
  }
  public abstract interface BooleanInput extends OmniDeque,OmniQueue.BooleanInput,OmniStack.BooleanInput
  {
    public abstract boolean offerLast(boolean val);
    public abstract boolean offerLast(Boolean val);
    public abstract boolean offerFirst(boolean val);
    public abstract boolean offerFirst(Boolean val);
    public abstract void addLast(boolean val);
    public abstract void addLast(Boolean val);
    public abstract void addFirst(boolean val);
    public abstract void addFirst(Boolean val);
  }
  public abstract interface ByteInput extends BooleanInput,OmniQueue.ByteInput,OmniStack.ByteInput
  {
    public abstract boolean offerLast(byte val);
    public abstract boolean offerLast(Byte val);
    public abstract boolean offerFirst(byte val);
    public abstract boolean offerFirst(Byte val);
    public abstract void addLast(byte val);
    public abstract void addLast(Byte val);
    public abstract void addFirst(byte val);
    public abstract void addFirst(Byte val);
  }
  public abstract interface CharInput extends BooleanInput,OmniQueue.CharInput,OmniStack.CharInput
  {
    public abstract boolean offerLast(char val);
    public abstract boolean offerLast(Character val);
    public abstract boolean offerFirst(char val);
    public abstract boolean offerFirst(Character val);
    public abstract void addLast(char val);
    public abstract void addLast(Character val);
    public abstract void addFirst(char val);
    public abstract void addFirst(Character val);
  }
  public abstract interface ShortInput extends ByteInput,OmniQueue.ShortInput,OmniStack.ShortInput
  {
    public abstract boolean offerLast(short val);
    public abstract boolean offerLast(Short val);
    public abstract boolean offerFirst(short val);
    public abstract boolean offerFirst(Short val);
    public abstract void addLast(short val);
    public abstract void addLast(Short val);
    public abstract void addFirst(short val);
    public abstract void addFirst(Short val);
  }
  public abstract interface IntInput extends CharInput,ShortInput,OmniQueue.IntInput,OmniStack.IntInput
  {
    public abstract boolean offerLast(int val);
    public abstract boolean offerLast(Integer val);
    public abstract boolean offerFirst(int val);
    public abstract boolean offerFirst(Integer val);
    public abstract void addLast(int val);
    public abstract void addLast(Integer val);
    public abstract void addFirst(int val);
    public abstract void addFirst(Integer val);
  }
  public abstract interface LongInput extends IntInput,OmniQueue.LongInput,OmniStack.LongInput
  {
    public abstract boolean offerLast(long val);
    public abstract boolean offerLast(Long val);
    public abstract boolean offerFirst(long val);
    public abstract boolean offerFirst(Long val);
    public abstract void addLast(long val);
    public abstract void addLast(Long val);
    public abstract void addFirst(long val);
    public abstract void addFirst(Long val);
  }
  public abstract interface FloatInput extends LongInput,OmniQueue.FloatInput,OmniStack.FloatInput
  {
    public abstract boolean offerLast(float val);
    public abstract boolean offerLast(Float val);
    public abstract boolean offerFirst(float val);
    public abstract boolean offerFirst(Float val);
    public abstract void addLast(float val);
    public abstract void addLast(Float val);
    public abstract void addFirst(float val);
    public abstract void addFirst(Float val);
  }
  public abstract interface OfBoolean extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfBoolean,PeekAndPollIfc.BooleanOutput<Boolean>,OmniStack,OmniStack.OfPrimitive,OmniStack.OfBoolean,OmniQueue,OmniQueue.OfBoolean,OmniDeque,OfPrimitive
  ,BooleanInput,ByteOutput<Boolean>,CharOutput<Boolean>
  {
    public abstract void addFirst(Boolean val);
    public abstract void addLast(boolean val);
    public abstract void addLast(Boolean val);
    public abstract boolean offerFirst(boolean val);
    public abstract boolean offerLast(boolean val);
    public abstract boolean offerFirst(Boolean val);
    public abstract boolean offerLast(Boolean val);
    public abstract Boolean getFirst();
    public abstract Boolean getLast();
    public abstract Boolean removeFirst();
    public abstract Boolean removeLast();
    public default boolean getFirstBoolean()
    {
      return booleanElement();
    }
    public abstract boolean getLastBoolean();
    public default boolean removeFirstBoolean()
    {
      return popBoolean();
    }
    public abstract boolean removeLastBoolean();
    @Override
    public default boolean removeBoolean()
    {
      return popBoolean();
    }
    //TODO get methods
    //TODO remove
    //TODO getAndRemoveImpls
    public default byte getFirstByte()
    {
      return TypeUtil.castToByte(booleanElement());
    }
    public default char getFirstChar()
    {
      return TypeUtil.castToChar(booleanElement());
    }
    public default short getFirstShort()
    {
      return TypeUtil.castToByte(booleanElement());
    }
    public default int getFirstInt()
    {
      return TypeUtil.castToByte(booleanElement());
    }
    public default long getFirstLong()
    {
      return TypeUtil.castToLong(booleanElement());
    }
    public default float getFirstFloat()
    {
      return TypeUtil.castToFloat(booleanElement());
    }
    public default double getFirstDouble()
    {
      return TypeUtil.castToDouble(booleanElement());
    }
    public default byte getLastByte()
    {
      return TypeUtil.castToByte(getLastBoolean());
    }
    public default char getLastChar()
    {
      return TypeUtil.castToChar(getLastBoolean());
    }
    public default short getLastShort()
    {
      return TypeUtil.castToByte(getLastBoolean());
    }
    public default int getLastInt()
    {
      return TypeUtil.castToByte(getLastBoolean());
    }
    public default long getLastLong()
    {
      return TypeUtil.castToLong(getLastBoolean());
    }
    public default float getLastFloat()
    {
      return TypeUtil.castToFloat(getLastBoolean());
    }
    public default double getLastDouble()
    {
      return TypeUtil.castToDouble(getLastBoolean());
    }
    public default byte removeFirstByte()
    {
      return TypeUtil.castToByte(popBoolean());
    }
    public default char removeFirstChar()
    {
      return TypeUtil.castToChar(popBoolean());
    }
    public default short removeFirstShort()
    {
      return TypeUtil.castToByte(popBoolean());
    }
    public default int removeFirstInt()
    {
      return TypeUtil.castToByte(popBoolean());
    }
    public default long removeFirstLong()
    {
      return TypeUtil.castToLong(popBoolean());
    }
    public default float removeFirstFloat()
    {
      return TypeUtil.castToFloat(popBoolean());
    }
    public default double removeFirstDouble()
    {
      return TypeUtil.castToDouble(popBoolean());
    }
    public default byte removeLastByte()
    {
      return TypeUtil.castToByte(removeLastBoolean());
    }
    public default char removeLastChar()
    {
      return TypeUtil.castToChar(removeLastBoolean());
    }
    public default short removeLastShort()
    {
      return TypeUtil.castToByte(removeLastBoolean());
    }
    public default int removeLastInt()
    {
      return TypeUtil.castToByte(removeLastBoolean());
    }
    public default long removeLastLong()
    {
      return TypeUtil.castToLong(removeLastBoolean());
    }
    public default float removeLastFloat()
    {
      return TypeUtil.castToFloat(removeLastBoolean());
    }
    public default double removeLastDouble()
    {
      return TypeUtil.castToDouble(removeLastBoolean());
    }
    public abstract Boolean peekFirst();
    public abstract Boolean peekLast();
    public abstract Boolean pollFirst();
    public abstract Boolean pollLast();
    public default boolean peekFirstBoolean()
    {
      return peekBoolean();
    }
    public abstract boolean peekLastBoolean();
    public default boolean pollFirstBoolean()
    {
      return pollBoolean();
    }
    public abstract boolean pollLastBoolean();
    public default double peekFirstDouble()
    {
      return peekDouble();
    }
    public abstract double peekLastDouble();
    public default double pollFirstDouble()
    {
      return pollDouble();
    }
    public abstract double pollLastDouble();
    public default float peekFirstFloat()
    {
      return peekFloat();
    }
    public abstract float peekLastFloat();
    public default float pollFirstFloat()
    {
      return pollFloat();
    }
    public abstract float pollLastFloat();
    public default long peekFirstLong()
    {
      return peekLong();
    }
    public abstract long peekLastLong();
    public default long pollFirstLong()
    {
      return pollLong();
    }
    public abstract long pollLastLong();
    public default int peekFirstInt()
    {
      return peekInt();
    }
    public abstract int peekLastInt();
    public default int pollFirstInt()
    {
      return pollInt();
    }
    public abstract int pollLastInt();
    public default short peekFirstShort()
    {
      return peekShort();
    }
    public abstract short peekLastShort();
    public default short pollFirstShort()
    {
      return pollShort();
    }
    public abstract short pollLastShort();
    public default byte peekFirstByte()
    {
      return peekByte();
    }
    public abstract byte peekLastByte();
    public default byte pollFirstByte()
    {
      return pollByte();
    }
    public abstract byte pollLastByte();
    public default char peekFirstChar()
    {
      return peekChar();
    }
    public abstract char peekLastChar();
    public default char pollFirstChar()
    {
      return pollChar();
    }
    public abstract char pollLastChar();
    @Override
    public default boolean removeLastOccurrence(byte val)
    {
      return removeLastOccurrence((int)val);
    }
    @Override
    public default boolean removeLastOccurrence(char val)
    {
      return removeLastOccurrence((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(byte val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(char val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(short val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeLastOccurrence(short val)
    {
      return removeLastOccurrence((int)val);
    }
    //TODO removeFirstOccurrence defaults
    //TODO removeLastOccurrence defaults
    public abstract OmniIterator.OfBoolean descendingIterator();
  }
  public abstract interface OfByte extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfByte,PeekAndPollIfc.ByteOutput<Byte>,OmniStack,OmniStack.OfPrimitive,OmniStack.OfByte,OmniQueue,OmniQueue.OfByte,OmniDeque,OfPrimitive
  ,ByteInput,ByteOutput<Byte>
  {
    public default void addFirst(byte val)
    {
      push(val);
    }
    public abstract void addFirst(Byte val);
    public abstract void addLast(byte val);
    public abstract void addLast(Byte val);
    public abstract boolean offerFirst(byte val);
    public abstract boolean offerLast(byte val);
    public abstract boolean offerFirst(Byte val);
    public abstract boolean offerLast(Byte val);
    public default void addFirst(boolean val)
    {
      push((byte)TypeUtil.
      castToByte
      (val));
    }
    public default void addLast(boolean val)
    {
      addLast((byte)TypeUtil.
      castToByte
      (val));
    }
    public default void addFirst(Boolean val)
    {
      push((byte)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default void addLast(Boolean val)
    {
      addLast((byte)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default boolean offerFirst(boolean val)
    {
      return offerFirst((byte)TypeUtil.
      castToByte
      (val));
    }
    public default boolean offerLast(boolean val)
    {
      return offerLast((byte)TypeUtil.
      castToByte
      (val));
    }
    public default boolean offerFirst(Boolean val)
    {
      return offerFirst((byte)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default boolean offerLast(Boolean val)
    {
      return offerLast((byte)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public abstract Byte getFirst();
    public abstract Byte getLast();
    public abstract Byte removeFirst();
    public abstract Byte removeLast();
    public default byte getFirstByte()
    {
      return byteElement();
    }
    public abstract byte getLastByte();
    public default byte removeFirstByte()
    {
      return popByte();
    }
    public abstract byte removeLastByte();
    @Override
    public default byte removeByte()
    {
      return popByte();
    }
    //TODO get methods
    //TODO remove
    public default double getFirstDouble()
    {
      return byteElement();
    }
    public default double getLastDouble()
    {
      return getLastByte();
    }
    public default double removeFirstDouble()
    {
      return popByte();
    }
    public default double removeLastDouble()
    {
      return removeLastByte();
    }
    public default float getFirstFloat()
    {
      return byteElement();
    }
    public default float getLastFloat()
    {
      return getLastByte();
    }
    public default float removeFirstFloat()
    {
      return popByte();
    }
    public default float removeLastFloat()
    {
      return removeLastByte();
    }
    public default long getFirstLong()
    {
      return byteElement();
    }
    public default long getLastLong()
    {
      return getLastByte();
    }
    public default long removeFirstLong()
    {
      return popByte();
    }
    public default long removeLastLong()
    {
      return removeLastByte();
    }
    public default int getFirstInt()
    {
      return byteElement();
    }
    public default int getLastInt()
    {
      return getLastByte();
    }
    public default int removeFirstInt()
    {
      return popByte();
    }
    public default int removeLastInt()
    {
      return removeLastByte();
    }
    public default short getFirstShort()
    {
      return byteElement();
    }
    public default short getLastShort()
    {
      return getLastByte();
    }
    public default short removeFirstShort()
    {
      return popByte();
    }
    public default short removeLastShort()
    {
      return removeLastByte();
    }
    public abstract Byte peekFirst();
    public abstract Byte peekLast();
    public abstract Byte pollFirst();
    public abstract Byte pollLast();
    public default byte peekFirstByte()
    {
      return peekByte();
    }
    public abstract byte peekLastByte();
    public default byte pollFirstByte()
    {
      return pollByte();
    }
    public abstract byte pollLastByte();
    public default double peekFirstDouble()
    {
      return peekDouble();
    }
    public abstract double peekLastDouble();
    public default double pollFirstDouble()
    {
      return pollDouble();
    }
    public abstract double pollLastDouble();
    public default float peekFirstFloat()
    {
      return peekFloat();
    }
    public abstract float peekLastFloat();
    public default float pollFirstFloat()
    {
      return pollFloat();
    }
    public abstract float pollLastFloat();
    public default long peekFirstLong()
    {
      return peekLong();
    }
    public abstract long peekLastLong();
    public default long pollFirstLong()
    {
      return pollLong();
    }
    public abstract long pollLastLong();
    public default int peekFirstInt()
    {
      return peekInt();
    }
    public abstract int peekLastInt();
    public default int pollFirstInt()
    {
      return pollInt();
    }
    public abstract int pollLastInt();
    public default short peekFirstShort()
    {
      return peekShort();
    }
    public abstract short peekLastShort();
    public default short pollFirstShort()
    {
      return pollShort();
    }
    public abstract short pollLastShort();
    @Override
    public default boolean removeFirstOccurrence(byte val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(char val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(short val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeLastOccurrence(short val)
    {
      return removeLastOccurrence((int)val);
    }
    //TODO removeFirstOccurrence defaults
    //TODO removeLastOccurrence defaults
    public abstract OmniIterator.OfByte descendingIterator();
  }
  public abstract interface OfChar extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfChar,PeekAndPollIfc.CharOutput<Character>,OmniStack,OmniStack.OfPrimitive,OmniStack.OfChar,OmniQueue,OmniQueue.OfChar,OmniDeque,OfPrimitive
  ,CharInput,CharOutput<Character>
  {
    public default void addFirst(char val)
    {
      push(val);
    }
    public abstract void addFirst(Character val);
    public abstract void addLast(char val);
    public abstract void addLast(Character val);
    public abstract boolean offerFirst(char val);
    public abstract boolean offerLast(char val);
    public abstract boolean offerFirst(Character val);
    public abstract boolean offerLast(Character val);
    public default void addFirst(boolean val)
    {
      push((char)TypeUtil.
      castToChar
      (val));
    }
    public default void addLast(boolean val)
    {
      addLast((char)TypeUtil.
      castToChar
      (val));
    }
    public default void addFirst(Boolean val)
    {
      push((char)TypeUtil.
      castToChar
      (val.booleanValue()));
    }
    public default void addLast(Boolean val)
    {
      addLast((char)TypeUtil.
      castToChar
      (val.booleanValue()));
    }
    public default boolean offerFirst(boolean val)
    {
      return offerFirst((char)TypeUtil.
      castToChar
      (val));
    }
    public default boolean offerLast(boolean val)
    {
      return offerLast((char)TypeUtil.
      castToChar
      (val));
    }
    public default boolean offerFirst(Boolean val)
    {
      return offerFirst((char)TypeUtil.
      castToChar
      (val.booleanValue()));
    }
    public default boolean offerLast(Boolean val)
    {
      return offerLast((char)TypeUtil.
      castToChar
      (val.booleanValue()));
    }
    public abstract Character getFirst();
    public abstract Character getLast();
    public abstract Character removeFirst();
    public abstract Character removeLast();
    public default char getFirstChar()
    {
      return charElement();
    }
    public abstract char getLastChar();
    public default char removeFirstChar()
    {
      return popChar();
    }
    public abstract char removeLastChar();
    @Override
    public default char removeChar()
    {
      return popChar();
    }
    //TODO get methods
    //TODO remove
    public default double getFirstDouble()
    {
      return charElement();
    }
    public default double getLastDouble()
    {
      return getLastChar();
    }
    public default double removeFirstDouble()
    {
      return popChar();
    }
    public default double removeLastDouble()
    {
      return removeLastChar();
    }
    public default float getFirstFloat()
    {
      return charElement();
    }
    public default float getLastFloat()
    {
      return getLastChar();
    }
    public default float removeFirstFloat()
    {
      return popChar();
    }
    public default float removeLastFloat()
    {
      return removeLastChar();
    }
    public default long getFirstLong()
    {
      return charElement();
    }
    public default long getLastLong()
    {
      return getLastChar();
    }
    public default long removeFirstLong()
    {
      return popChar();
    }
    public default long removeLastLong()
    {
      return removeLastChar();
    }
    public default int getFirstInt()
    {
      return charElement();
    }
    public default int getLastInt()
    {
      return getLastChar();
    }
    public default int removeFirstInt()
    {
      return popChar();
    }
    public default int removeLastInt()
    {
      return removeLastChar();
    }
    public abstract Character peekFirst();
    public abstract Character peekLast();
    public abstract Character pollFirst();
    public abstract Character pollLast();
    public default char peekFirstChar()
    {
      return peekChar();
    }
    public abstract char peekLastChar();
    public default char pollFirstChar()
    {
      return pollChar();
    }
    public abstract char pollLastChar();
    public default double peekFirstDouble()
    {
      return peekDouble();
    }
    public abstract double peekLastDouble();
    public default double pollFirstDouble()
    {
      return pollDouble();
    }
    public abstract double pollLastDouble();
    public default float peekFirstFloat()
    {
      return peekFloat();
    }
    public abstract float peekLastFloat();
    public default float pollFirstFloat()
    {
      return pollFloat();
    }
    public abstract float pollLastFloat();
    public default long peekFirstLong()
    {
      return peekLong();
    }
    public abstract long peekLastLong();
    public default long pollFirstLong()
    {
      return pollLong();
    }
    public abstract long pollLastLong();
    public default int peekFirstInt()
    {
      return peekInt();
    }
    public abstract int peekLastInt();
    public default int pollFirstInt()
    {
      return pollInt();
    }
    public abstract int pollLastInt();
    @Override
    public default boolean removeFirstOccurrence(byte val){
      return removeVal((short)val);
    }
    @Override
    public default boolean removeFirstOccurrence(char val){
      return removeVal(val);
    }
    @Override
    public default boolean removeFirstOccurrence(short val){
      return removeVal(val);
    }
    @Override
    public default boolean removeLastOccurrence(byte val){
      return removeLastOccurrence((short)val);
    }
    //TODO removeFirstOccurrence defaults
    //TODO removeLastOccurrence defaults
    public abstract OmniIterator.OfChar descendingIterator();
  }
  public abstract interface OfShort extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfShort,PeekAndPollIfc.ShortOutput<Short>,OmniStack,OmniStack.OfPrimitive,OmniStack.OfShort,OmniQueue,OmniQueue.OfShort,OmniDeque,OfPrimitive
  ,ShortInput,ShortOutput<Short>
  {
    public default void addFirst(short val)
    {
      push(val);
    }
    public abstract void addFirst(Short val);
    public abstract void addLast(short val);
    public abstract void addLast(Short val);
    public abstract boolean offerFirst(short val);
    public abstract boolean offerLast(short val);
    public abstract boolean offerFirst(Short val);
    public abstract boolean offerLast(Short val);
    public default void addFirst(boolean val)
    {
      push((short)TypeUtil.
      castToByte
      (val));
    }
    public default void addLast(boolean val)
    {
      addLast((short)TypeUtil.
      castToByte
      (val));
    }
    public default void addFirst(Boolean val)
    {
      push((short)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default void addLast(Boolean val)
    {
      addLast((short)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default boolean offerFirst(boolean val)
    {
      return offerFirst((short)TypeUtil.
      castToByte
      (val));
    }
    public default boolean offerLast(boolean val)
    {
      return offerLast((short)TypeUtil.
      castToByte
      (val));
    }
    public default boolean offerFirst(Boolean val)
    {
      return offerFirst((short)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default boolean offerLast(Boolean val)
    {
      return offerLast((short)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default void addFirst(byte val)
    {
      push((short)val);
    }
    public default void addLast(byte val)
    {
      addLast((short)val);
    }
    public default boolean offerFirst(byte val)
    {
      return offerFirst((short)val);
    }
    public default boolean offerLast(byte val)
    {
      return offerLast((short)val);
    }
    public default void addFirst(Byte val)
    {
      push((short)val.byteValue());
    }
    public default void addLast(Byte val)
    {
      addLast((short)val.byteValue());
    }
    public default boolean offerFirst(Byte val)
    {
      return offerFirst((short)val.byteValue());
    }
    public default boolean offerLast(Byte val)
    {
      return offerLast((short)val.byteValue());
    }
    public abstract Short getFirst();
    public abstract Short getLast();
    public abstract Short removeFirst();
    public abstract Short removeLast();
    public default short getFirstShort()
    {
      return shortElement();
    }
    public abstract short getLastShort();
    public default short removeFirstShort()
    {
      return popShort();
    }
    public abstract short removeLastShort();
    @Override
    public default short removeShort()
    {
      return popShort();
    }
    //TODO get methods
    //TODO remove
    public default double getFirstDouble()
    {
      return shortElement();
    }
    public default double getLastDouble()
    {
      return getLastShort();
    }
    public default double removeFirstDouble()
    {
      return popShort();
    }
    public default double removeLastDouble()
    {
      return removeLastShort();
    }
    public default float getFirstFloat()
    {
      return shortElement();
    }
    public default float getLastFloat()
    {
      return getLastShort();
    }
    public default float removeFirstFloat()
    {
      return popShort();
    }
    public default float removeLastFloat()
    {
      return removeLastShort();
    }
    public default long getFirstLong()
    {
      return shortElement();
    }
    public default long getLastLong()
    {
      return getLastShort();
    }
    public default long removeFirstLong()
    {
      return popShort();
    }
    public default long removeLastLong()
    {
      return removeLastShort();
    }
    public default int getFirstInt()
    {
      return shortElement();
    }
    public default int getLastInt()
    {
      return getLastShort();
    }
    public default int removeFirstInt()
    {
      return popShort();
    }
    public default int removeLastInt()
    {
      return removeLastShort();
    }
    public abstract Short peekFirst();
    public abstract Short peekLast();
    public abstract Short pollFirst();
    public abstract Short pollLast();
    public default short peekFirstShort()
    {
      return peekShort();
    }
    public abstract short peekLastShort();
    public default short pollFirstShort()
    {
      return pollShort();
    }
    public abstract short pollLastShort();
    public default double peekFirstDouble()
    {
      return peekDouble();
    }
    public abstract double peekLastDouble();
    public default double pollFirstDouble()
    {
      return pollDouble();
    }
    public abstract double pollLastDouble();
    public default float peekFirstFloat()
    {
      return peekFloat();
    }
    public abstract float peekLastFloat();
    public default float pollFirstFloat()
    {
      return pollFloat();
    }
    public abstract float pollLastFloat();
    public default long peekFirstLong()
    {
      return peekLong();
    }
    public abstract long peekLastLong();
    public default long pollFirstLong()
    {
      return pollLong();
    }
    public abstract long pollLastLong();
    public default int peekFirstInt()
    {
      return peekInt();
    }
    public abstract int peekLastInt();
    public default int pollFirstInt()
    {
      return pollInt();
    }
    public abstract int pollLastInt();
    @Override
    public default boolean removeFirstOccurrence(byte val){
      return removeVal((short)val);
    }
    @Override
    public default boolean removeFirstOccurrence(char val){
      return removeVal(val);
    }
    @Override
    public default boolean removeFirstOccurrence(short val){
      return removeVal(val);
    }
    @Override
    public default boolean removeLastOccurrence(byte val){
      return removeLastOccurrence((short)val);
    }
    //TODO removeFirstOccurrence defaults
    //TODO removeLastOccurrence defaults
    public abstract OmniIterator.OfShort descendingIterator();
  }
  public abstract interface OfInt extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfInt,PeekAndPollIfc.IntOutput<Integer>,OmniStack,OmniStack.OfPrimitive,OmniStack.OfInt,OmniQueue,OmniQueue.OfInt,OmniDeque,OfPrimitive
  ,IntInput,IntOutput<Integer>
  {
    public default void addFirst(int val)
    {
      push(val);
    }
    public abstract void addFirst(Integer val);
    public abstract void addLast(int val);
    public abstract void addLast(Integer val);
    public abstract boolean offerFirst(int val);
    public abstract boolean offerLast(int val);
    public abstract boolean offerFirst(Integer val);
    public abstract boolean offerLast(Integer val);
    public default void addFirst(boolean val)
    {
      push((int)TypeUtil.
      castToByte
      (val));
    }
    public default void addLast(boolean val)
    {
      addLast((int)TypeUtil.
      castToByte
      (val));
    }
    public default void addFirst(Boolean val)
    {
      push((int)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default void addLast(Boolean val)
    {
      addLast((int)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default boolean offerFirst(boolean val)
    {
      return offerFirst((int)TypeUtil.
      castToByte
      (val));
    }
    public default boolean offerLast(boolean val)
    {
      return offerLast((int)TypeUtil.
      castToByte
      (val));
    }
    public default boolean offerFirst(Boolean val)
    {
      return offerFirst((int)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default boolean offerLast(Boolean val)
    {
      return offerLast((int)TypeUtil.
      castToByte
      (val.booleanValue()));
    }
    public default void addFirst(char val)
    {
      push((int)val);
    }
    public default void addLast(char val)
    {
      addLast((int)val);
    }
    public default boolean offerFirst(char val)
    {
      return offerFirst((int)val);
    }
    public default boolean offerLast(char val)
    {
      return offerLast((int)val);
    }
    public default void addFirst(Character val)
    {
      push((int)val.charValue());
    }
    public default void addLast(Character val)
    {
      addLast((int)val.charValue());
    }
    public default boolean offerFirst(Character val)
    {
      return offerFirst((int)val.charValue());
    }
    public default boolean offerLast(Character val)
    {
      return offerLast((int)val.charValue());
    }
    public default void addFirst(byte val)
    {
      push((int)val);
    }
    public default void addLast(byte val)
    {
      addLast((int)val);
    }
    public default boolean offerFirst(byte val)
    {
      return offerFirst((int)val);
    }
    public default boolean offerLast(byte val)
    {
      return offerLast((int)val);
    }
    public default void addFirst(Byte val)
    {
      push((int)val.byteValue());
    }
    public default void addLast(Byte val)
    {
      addLast((int)val.byteValue());
    }
    public default boolean offerFirst(Byte val)
    {
      return offerFirst((int)val.byteValue());
    }
    public default boolean offerLast(Byte val)
    {
      return offerLast((int)val.byteValue());
    }
    public default void addFirst(short val)
    {
      push((int)val);
    }
    public default void addLast(short val)
    {
      addLast((int)val);
    }
    public default boolean offerFirst(short val)
    {
      return offerFirst((int)val);
    }
    public default boolean offerLast(short val)
    {
      return offerLast((int)val);
    }
    public default void addFirst(Short val)
    {
      push((int)val.shortValue());
    }
    public default void addLast(Short val)
    {
      addLast((int)val.shortValue());
    }
    public default boolean offerFirst(Short val)
    {
      return offerFirst((int)val.shortValue());
    }
    public default boolean offerLast(Short val)
    {
      return offerLast((int)val.shortValue());
    }
    public abstract Integer getFirst();
    public abstract Integer getLast();
    public abstract Integer removeFirst();
    public abstract Integer removeLast();
    public default int getFirstInt()
    {
      return intElement();
    }
    public abstract int getLastInt();
    public default int removeFirstInt()
    {
      return popInt();
    }
    public abstract int removeLastInt();
    @Override
    public default int removeInt()
    {
      return popInt();
    }
    //TODO get methods
    //TODO remove
    public default double getFirstDouble()
    {
      return intElement();
    }
    public default double getLastDouble()
    {
      return getLastInt();
    }
    public default double removeFirstDouble()
    {
      return popInt();
    }
    public default double removeLastDouble()
    {
      return removeLastInt();
    }
    public default float getFirstFloat()
    {
      return intElement();
    }
    public default float getLastFloat()
    {
      return getLastInt();
    }
    public default float removeFirstFloat()
    {
      return popInt();
    }
    public default float removeLastFloat()
    {
      return removeLastInt();
    }
    public default long getFirstLong()
    {
      return intElement();
    }
    public default long getLastLong()
    {
      return getLastInt();
    }
    public default long removeFirstLong()
    {
      return popInt();
    }
    public default long removeLastLong()
    {
      return removeLastInt();
    }
    public abstract Integer peekFirst();
    public abstract Integer peekLast();
    public abstract Integer pollFirst();
    public abstract Integer pollLast();
    public default int peekFirstInt()
    {
      return peekInt();
    }
    public abstract int peekLastInt();
    public default int pollFirstInt()
    {
      return pollInt();
    }
    public abstract int pollLastInt();
    public default double peekFirstDouble()
    {
      return peekDouble();
    }
    public abstract double peekLastDouble();
    public default double pollFirstDouble()
    {
      return pollDouble();
    }
    public abstract double pollLastDouble();
    public default float peekFirstFloat()
    {
      return peekFloat();
    }
    public abstract float peekLastFloat();
    public default float pollFirstFloat()
    {
      return pollFloat();
    }
    public abstract float pollLastFloat();
    public default long peekFirstLong()
    {
      return peekLong();
    }
    public abstract long peekLastLong();
    public default long pollFirstLong()
    {
      return pollLong();
    }
    public abstract long pollLastLong();
    @Override
    public default boolean removeLastOccurrence(byte val)
    {
      return removeLastOccurrence((int)val);
    }
    @Override
    public default boolean removeLastOccurrence(char val)
    {
      return removeLastOccurrence((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(byte val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(char val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(short val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeLastOccurrence(short val)
    {
      return removeLastOccurrence((int)val);
    }
    //TODO removeFirstOccurrence defaults
    //TODO removeLastOccurrence defaults
    public abstract OmniIterator.OfInt descendingIterator();
  }
  public abstract interface OfLong extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfLong,PeekAndPollIfc.LongOutput<Long>,OmniStack,OmniStack.OfPrimitive,OmniStack.OfLong,OmniQueue,OmniQueue.OfLong,OmniDeque,OfPrimitive
  ,LongInput,LongOutput<Long>
  {
    public default void addFirst(long val)
    {
      push(val);
    }
    public abstract void addFirst(Long val);
    public abstract void addLast(long val);
    public abstract void addLast(Long val);
    public abstract boolean offerFirst(long val);
    public abstract boolean offerLast(long val);
    public abstract boolean offerFirst(Long val);
    public abstract boolean offerLast(Long val);
    public default void addFirst(boolean val)
    {
      push((long)TypeUtil.
      castToLong
      (val));
    }
    public default void addLast(boolean val)
    {
      addLast((long)TypeUtil.
      castToLong
      (val));
    }
    public default void addFirst(Boolean val)
    {
      push((long)TypeUtil.
      castToLong
      (val.booleanValue()));
    }
    public default void addLast(Boolean val)
    {
      addLast((long)TypeUtil.
      castToLong
      (val.booleanValue()));
    }
    public default boolean offerFirst(boolean val)
    {
      return offerFirst((long)TypeUtil.
      castToLong
      (val));
    }
    public default boolean offerLast(boolean val)
    {
      return offerLast((long)TypeUtil.
      castToLong
      (val));
    }
    public default boolean offerFirst(Boolean val)
    {
      return offerFirst((long)TypeUtil.
      castToLong
      (val.booleanValue()));
    }
    public default boolean offerLast(Boolean val)
    {
      return offerLast((long)TypeUtil.
      castToLong
      (val.booleanValue()));
    }
    public default void addFirst(char val)
    {
      push((long)val);
    }
    public default void addLast(char val)
    {
      addLast((long)val);
    }
    public default boolean offerFirst(char val)
    {
      return offerFirst((long)val);
    }
    public default boolean offerLast(char val)
    {
      return offerLast((long)val);
    }
    public default void addFirst(Character val)
    {
      push((long)val.charValue());
    }
    public default void addLast(Character val)
    {
      addLast((long)val.charValue());
    }
    public default boolean offerFirst(Character val)
    {
      return offerFirst((long)val.charValue());
    }
    public default boolean offerLast(Character val)
    {
      return offerLast((long)val.charValue());
    }
    public default void addFirst(byte val)
    {
      push((long)val);
    }
    public default void addLast(byte val)
    {
      addLast((long)val);
    }
    public default boolean offerFirst(byte val)
    {
      return offerFirst((long)val);
    }
    public default boolean offerLast(byte val)
    {
      return offerLast((long)val);
    }
    public default void addFirst(Byte val)
    {
      push((long)val.byteValue());
    }
    public default void addLast(Byte val)
    {
      addLast((long)val.byteValue());
    }
    public default boolean offerFirst(Byte val)
    {
      return offerFirst((long)val.byteValue());
    }
    public default boolean offerLast(Byte val)
    {
      return offerLast((long)val.byteValue());
    }
    public default void addFirst(short val)
    {
      push((long)val);
    }
    public default void addLast(short val)
    {
      addLast((long)val);
    }
    public default boolean offerFirst(short val)
    {
      return offerFirst((long)val);
    }
    public default boolean offerLast(short val)
    {
      return offerLast((long)val);
    }
    public default void addFirst(Short val)
    {
      push((long)val.shortValue());
    }
    public default void addLast(Short val)
    {
      addLast((long)val.shortValue());
    }
    public default boolean offerFirst(Short val)
    {
      return offerFirst((long)val.shortValue());
    }
    public default boolean offerLast(Short val)
    {
      return offerLast((long)val.shortValue());
    }
    public default void addFirst(int val)
    {
      push((long)val);
    }
    public default void addLast(int val)
    {
      addLast((long)val);
    }
    public default boolean offerFirst(int val)
    {
      return offerFirst((long)val);
    }
    public default boolean offerLast(int val)
    {
      return offerLast((long)val);
    }
    public default void addFirst(Integer val)
    {
      push((long)val.intValue());
    }
    public default void addLast(Integer val)
    {
      addLast((long)val.intValue());
    }
    public default boolean offerFirst(Integer val)
    {
      return offerFirst((long)val.intValue());
    }
    public default boolean offerLast(Integer val)
    {
      return offerLast((long)val.intValue());
    }
    public abstract Long getFirst();
    public abstract Long getLast();
    public abstract Long removeFirst();
    public abstract Long removeLast();
    public default long getFirstLong()
    {
      return longElement();
    }
    public abstract long getLastLong();
    public default long removeFirstLong()
    {
      return popLong();
    }
    public abstract long removeLastLong();
    @Override
    public default long removeLong()
    {
      return popLong();
    }
    //TODO get methods
    //TODO remove
    public default double getFirstDouble()
    {
      return longElement();
    }
    public default double getLastDouble()
    {
      return getLastLong();
    }
    public default double removeFirstDouble()
    {
      return popLong();
    }
    public default double removeLastDouble()
    {
      return removeLastLong();
    }
    public default float getFirstFloat()
    {
      return longElement();
    }
    public default float getLastFloat()
    {
      return getLastLong();
    }
    public default float removeFirstFloat()
    {
      return popLong();
    }
    public default float removeLastFloat()
    {
      return removeLastLong();
    }
    public abstract Long peekFirst();
    public abstract Long peekLast();
    public abstract Long pollFirst();
    public abstract Long pollLast();
    public default long peekFirstLong()
    {
      return peekLong();
    }
    public abstract long peekLastLong();
    public default long pollFirstLong()
    {
      return pollLong();
    }
    public abstract long pollLastLong();
    public default double peekFirstDouble()
    {
      return peekDouble();
    }
    public abstract double peekLastDouble();
    public default double pollFirstDouble()
    {
      return pollDouble();
    }
    public abstract double pollLastDouble();
    public default float peekFirstFloat()
    {
      return peekFloat();
    }
    public abstract float peekLastFloat();
    public default float pollFirstFloat()
    {
      return pollFloat();
    }
    public abstract float pollLastFloat();
    @Override
    public default boolean removeLastOccurrence(byte val)
    {
      return removeLastOccurrence((int)val);
    }
    @Override
    public default boolean removeLastOccurrence(char val)
    {
      return removeLastOccurrence((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(byte val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(char val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(short val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeLastOccurrence(short val)
    {
      return removeLastOccurrence((int)val);
    }
    //TODO removeFirstOccurrence defaults
    //TODO removeLastOccurrence defaults
    public abstract OmniIterator.OfLong descendingIterator();
  }
  public abstract interface OfFloat extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfFloat,PeekAndPollIfc.FloatOutput<Float>,OmniStack,OmniStack.OfPrimitive,OmniStack.OfFloat,OmniQueue,OmniQueue.OfFloat,OmniDeque,OfPrimitive
  ,FloatInput,FloatOutput<Float>
  {
    public default void addFirst(float val)
    {
      push(val);
    }
    public abstract void addFirst(Float val);
    public abstract void addLast(float val);
    public abstract void addLast(Float val);
    public abstract boolean offerFirst(float val);
    public abstract boolean offerLast(float val);
    public abstract boolean offerFirst(Float val);
    public abstract boolean offerLast(Float val);
    public default void addFirst(boolean val)
    {
      push((float)TypeUtil.
      castToFloat
      (val));
    }
    public default void addLast(boolean val)
    {
      addLast((float)TypeUtil.
      castToFloat
      (val));
    }
    public default void addFirst(Boolean val)
    {
      push((float)TypeUtil.
      castToFloat
      (val.booleanValue()));
    }
    public default void addLast(Boolean val)
    {
      addLast((float)TypeUtil.
      castToFloat
      (val.booleanValue()));
    }
    public default boolean offerFirst(boolean val)
    {
      return offerFirst((float)TypeUtil.
      castToFloat
      (val));
    }
    public default boolean offerLast(boolean val)
    {
      return offerLast((float)TypeUtil.
      castToFloat
      (val));
    }
    public default boolean offerFirst(Boolean val)
    {
      return offerFirst((float)TypeUtil.
      castToFloat
      (val.booleanValue()));
    }
    public default boolean offerLast(Boolean val)
    {
      return offerLast((float)TypeUtil.
      castToFloat
      (val.booleanValue()));
    }
    public default void addFirst(char val)
    {
      push((float)val);
    }
    public default void addLast(char val)
    {
      addLast((float)val);
    }
    public default boolean offerFirst(char val)
    {
      return offerFirst((float)val);
    }
    public default boolean offerLast(char val)
    {
      return offerLast((float)val);
    }
    public default void addFirst(Character val)
    {
      push((float)val.charValue());
    }
    public default void addLast(Character val)
    {
      addLast((float)val.charValue());
    }
    public default boolean offerFirst(Character val)
    {
      return offerFirst((float)val.charValue());
    }
    public default boolean offerLast(Character val)
    {
      return offerLast((float)val.charValue());
    }
    public default void addFirst(byte val)
    {
      push((float)val);
    }
    public default void addLast(byte val)
    {
      addLast((float)val);
    }
    public default boolean offerFirst(byte val)
    {
      return offerFirst((float)val);
    }
    public default boolean offerLast(byte val)
    {
      return offerLast((float)val);
    }
    public default void addFirst(Byte val)
    {
      push((float)val.byteValue());
    }
    public default void addLast(Byte val)
    {
      addLast((float)val.byteValue());
    }
    public default boolean offerFirst(Byte val)
    {
      return offerFirst((float)val.byteValue());
    }
    public default boolean offerLast(Byte val)
    {
      return offerLast((float)val.byteValue());
    }
    public default void addFirst(short val)
    {
      push((float)val);
    }
    public default void addLast(short val)
    {
      addLast((float)val);
    }
    public default boolean offerFirst(short val)
    {
      return offerFirst((float)val);
    }
    public default boolean offerLast(short val)
    {
      return offerLast((float)val);
    }
    public default void addFirst(Short val)
    {
      push((float)val.shortValue());
    }
    public default void addLast(Short val)
    {
      addLast((float)val.shortValue());
    }
    public default boolean offerFirst(Short val)
    {
      return offerFirst((float)val.shortValue());
    }
    public default boolean offerLast(Short val)
    {
      return offerLast((float)val.shortValue());
    }
    public default void addFirst(int val)
    {
      push((float)val);
    }
    public default void addLast(int val)
    {
      addLast((float)val);
    }
    public default boolean offerFirst(int val)
    {
      return offerFirst((float)val);
    }
    public default boolean offerLast(int val)
    {
      return offerLast((float)val);
    }
    public default void addFirst(Integer val)
    {
      push((float)val.intValue());
    }
    public default void addLast(Integer val)
    {
      addLast((float)val.intValue());
    }
    public default boolean offerFirst(Integer val)
    {
      return offerFirst((float)val.intValue());
    }
    public default boolean offerLast(Integer val)
    {
      return offerLast((float)val.intValue());
    }
    public default void addFirst(long val)
    {
      push((float)val);
    }
    public default void addLast(long val)
    {
      addLast((float)val);
    }
    public default boolean offerFirst(long val)
    {
      return offerFirst((float)val);
    }
    public default boolean offerLast(long val)
    {
      return offerLast((float)val);
    }
    public default void addFirst(Long val)
    {
      push((float)val.longValue());
    }
    public default void addLast(Long val)
    {
      addLast((float)val.longValue());
    }
    public default boolean offerFirst(Long val)
    {
      return offerFirst((float)val.longValue());
    }
    public default boolean offerLast(Long val)
    {
      return offerLast((float)val.longValue());
    }
    public abstract Float getFirst();
    public abstract Float getLast();
    public abstract Float removeFirst();
    public abstract Float removeLast();
    public default float getFirstFloat()
    {
      return floatElement();
    }
    public abstract float getLastFloat();
    public default float removeFirstFloat()
    {
      return popFloat();
    }
    public abstract float removeLastFloat();
    @Override
    public default float removeFloat()
    {
      return popFloat();
    }
    //TODO get methods
    //TODO remove
    public default double getFirstDouble()
    {
      return floatElement();
    }
    public default double getLastDouble()
    {
      return getLastFloat();
    }
    public default double removeFirstDouble()
    {
      return popFloat();
    }
    public default double removeLastDouble()
    {
      return removeLastFloat();
    }
    public abstract Float peekFirst();
    public abstract Float peekLast();
    public abstract Float pollFirst();
    public abstract Float pollLast();
    public default float peekFirstFloat()
    {
      return peekFloat();
    }
    public abstract float peekLastFloat();
    public default float pollFirstFloat()
    {
      return pollFloat();
    }
    public abstract float pollLastFloat();
    public default double peekFirstDouble()
    {
      return peekDouble();
    }
    public abstract double peekLastDouble();
    public default double pollFirstDouble()
    {
      return pollDouble();
    }
    public abstract double pollLastDouble();
    @Override
    public default boolean removeFirstOccurrence(byte val){
      return removeVal((short)val);
    }
    @Override
    public default boolean removeFirstOccurrence(char val){
      return removeVal(val);
    }
    @Override
    public default boolean removeFirstOccurrence(short val){
      return removeVal(val);
    }
    @Override
    public default boolean removeLastOccurrence(byte val){
      return removeLastOccurrence((short)val);
    }
    //TODO removeFirstOccurrence defaults
    //TODO removeLastOccurrence defaults
    public abstract OmniIterator.OfFloat descendingIterator();
  }
  public abstract interface OfDouble extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfDouble,PeekAndPollIfc.DoubleOutput<Double>,OmniStack,OmniStack.OfPrimitive,OmniStack.OfDouble,OmniQueue,OmniQueue.OfDouble,OmniDeque,OfPrimitive
  ,FloatInput,DoubleOutput<Double>
  {
    public default void addFirst(double val)
    {
      push(val);
    }
    public abstract void addFirst(Double val);
    public abstract void addLast(double val);
    public abstract void addLast(Double val);
    public abstract boolean offerFirst(double val);
    public abstract boolean offerLast(double val);
    public abstract boolean offerFirst(Double val);
    public abstract boolean offerLast(Double val);
    public default void addFirst(boolean val)
    {
      push((double)TypeUtil.
      castToDouble
      (val));
    }
    public default void addLast(boolean val)
    {
      addLast((double)TypeUtil.
      castToDouble
      (val));
    }
    public default void addFirst(Boolean val)
    {
      push((double)TypeUtil.
      castToDouble
      (val.booleanValue()));
    }
    public default void addLast(Boolean val)
    {
      addLast((double)TypeUtil.
      castToDouble
      (val.booleanValue()));
    }
    public default boolean offerFirst(boolean val)
    {
      return offerFirst((double)TypeUtil.
      castToDouble
      (val));
    }
    public default boolean offerLast(boolean val)
    {
      return offerLast((double)TypeUtil.
      castToDouble
      (val));
    }
    public default boolean offerFirst(Boolean val)
    {
      return offerFirst((double)TypeUtil.
      castToDouble
      (val.booleanValue()));
    }
    public default boolean offerLast(Boolean val)
    {
      return offerLast((double)TypeUtil.
      castToDouble
      (val.booleanValue()));
    }
    public default void addFirst(char val)
    {
      push((double)val);
    }
    public default void addLast(char val)
    {
      addLast((double)val);
    }
    public default boolean offerFirst(char val)
    {
      return offerFirst((double)val);
    }
    public default boolean offerLast(char val)
    {
      return offerLast((double)val);
    }
    public default void addFirst(Character val)
    {
      push((double)val.charValue());
    }
    public default void addLast(Character val)
    {
      addLast((double)val.charValue());
    }
    public default boolean offerFirst(Character val)
    {
      return offerFirst((double)val.charValue());
    }
    public default boolean offerLast(Character val)
    {
      return offerLast((double)val.charValue());
    }
    public default void addFirst(byte val)
    {
      push((double)val);
    }
    public default void addLast(byte val)
    {
      addLast((double)val);
    }
    public default boolean offerFirst(byte val)
    {
      return offerFirst((double)val);
    }
    public default boolean offerLast(byte val)
    {
      return offerLast((double)val);
    }
    public default void addFirst(Byte val)
    {
      push((double)val.byteValue());
    }
    public default void addLast(Byte val)
    {
      addLast((double)val.byteValue());
    }
    public default boolean offerFirst(Byte val)
    {
      return offerFirst((double)val.byteValue());
    }
    public default boolean offerLast(Byte val)
    {
      return offerLast((double)val.byteValue());
    }
    public default void addFirst(short val)
    {
      push((double)val);
    }
    public default void addLast(short val)
    {
      addLast((double)val);
    }
    public default boolean offerFirst(short val)
    {
      return offerFirst((double)val);
    }
    public default boolean offerLast(short val)
    {
      return offerLast((double)val);
    }
    public default void addFirst(Short val)
    {
      push((double)val.shortValue());
    }
    public default void addLast(Short val)
    {
      addLast((double)val.shortValue());
    }
    public default boolean offerFirst(Short val)
    {
      return offerFirst((double)val.shortValue());
    }
    public default boolean offerLast(Short val)
    {
      return offerLast((double)val.shortValue());
    }
    public default void addFirst(int val)
    {
      push((double)val);
    }
    public default void addLast(int val)
    {
      addLast((double)val);
    }
    public default boolean offerFirst(int val)
    {
      return offerFirst((double)val);
    }
    public default boolean offerLast(int val)
    {
      return offerLast((double)val);
    }
    public default void addFirst(Integer val)
    {
      push((double)val.intValue());
    }
    public default void addLast(Integer val)
    {
      addLast((double)val.intValue());
    }
    public default boolean offerFirst(Integer val)
    {
      return offerFirst((double)val.intValue());
    }
    public default boolean offerLast(Integer val)
    {
      return offerLast((double)val.intValue());
    }
    public default void addFirst(long val)
    {
      push((double)val);
    }
    public default void addLast(long val)
    {
      addLast((double)val);
    }
    public default boolean offerFirst(long val)
    {
      return offerFirst((double)val);
    }
    public default boolean offerLast(long val)
    {
      return offerLast((double)val);
    }
    public default void addFirst(Long val)
    {
      push((double)val.longValue());
    }
    public default void addLast(Long val)
    {
      addLast((double)val.longValue());
    }
    public default boolean offerFirst(Long val)
    {
      return offerFirst((double)val.longValue());
    }
    public default boolean offerLast(Long val)
    {
      return offerLast((double)val.longValue());
    }
    public default void addFirst(float val)
    {
      push((double)val);
    }
    public default void addLast(float val)
    {
      addLast((double)val);
    }
    public default boolean offerFirst(float val)
    {
      return offerFirst((double)val);
    }
    public default boolean offerLast(float val)
    {
      return offerLast((double)val);
    }
    public default void addFirst(Float val)
    {
      push((double)val.floatValue());
    }
    public default void addLast(Float val)
    {
      addLast((double)val.floatValue());
    }
    public default boolean offerFirst(Float val)
    {
      return offerFirst((double)val.floatValue());
    }
    public default boolean offerLast(Float val)
    {
      return offerLast((double)val.floatValue());
    }
    public abstract Double getFirst();
    public abstract Double getLast();
    public abstract Double removeFirst();
    public abstract Double removeLast();
    public default double getFirstDouble()
    {
      return doubleElement();
    }
    public abstract double getLastDouble();
    public default double removeFirstDouble()
    {
      return popDouble();
    }
    public abstract double removeLastDouble();
    @Override
    public default double removeDouble()
    {
      return popDouble();
    }
    //TODO get methods
    //TODO remove
    public abstract Double peekFirst();
    public abstract Double peekLast();
    public abstract Double pollFirst();
    public abstract Double pollLast();
    public default double peekFirstDouble()
    {
      return peekDouble();
    }
    public abstract double peekLastDouble();
    public default double pollFirstDouble()
    {
      return pollDouble();
    }
    public abstract double pollLastDouble();
    @Override
    public default boolean removeLastOccurrence(byte val)
    {
      return removeLastOccurrence((int)val);
    }
    @Override
    public default boolean removeLastOccurrence(char val)
    {
      return removeLastOccurrence((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(byte val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(char val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeFirstOccurrence(short val)
    {
      return removeVal((int)val);
    }
    @Override
    public default boolean removeLastOccurrence(short val)
    {
      return removeLastOccurrence((int)val);
    }
    //TODO removeFirstOccurrence defaults
    //TODO removeLastOccurrence defaults
    public abstract OmniIterator.OfDouble descendingIterator();
  }
  public abstract interface OfRef<E> extends OmniCollection,OmniCollection.OfRef<E>,PeekAndPollIfc<E>,OmniStack,OmniStack.OfRef<E>,OmniQueue,OmniQueue.OfRef<E>,OmniDeque
  {
    public abstract void addFirst(E val);
    public abstract void addLast(E val);
    OmniIterator.OfRef<E> descendingIterator();
    public abstract E getFirst();
    public abstract E getLast();
    public abstract boolean offerFirst(E val);
    public abstract boolean offerLast(E val);
    public abstract E peekFirst();
    public abstract E peekLast();
    public abstract E pollFirst();
    public abstract E pollLast();
    public abstract E removeFirst();
    public abstract E removeLast();
    public default boolean removeFirstOccurrence(Boolean val)
    {
      return removeVal(val);
    }
    public default boolean removeFirstOccurrence(Byte val)
    {
      return removeVal(val);
    }
    public default boolean removeFirstOccurrence(Character val)
    {
      return removeVal(val);
    }
    public default boolean removeFirstOccurrence(Short val)
    {
      return removeVal(val);
    }
    public default boolean removeFirstOccurrence(Integer val)
    {
      return removeVal(val);
    }
    public default boolean removeFirstOccurrence(Long val)
    {
      return removeVal(val);
    }
    public default boolean removeFirstOccurrence(Float val)
    {
      return removeVal(val);
    }
    public default boolean removeFirstOccurrence(Double val)
    {
      return removeVal(val);
    }
    public default boolean removeFirstOccurrence(byte val)
    {
      return removeVal(val);
    }
    public default boolean removeFirstOccurrence(char val)
    {
      return removeVal(val);
    }
    public default boolean removeFirstOccurrence(short val)
    {
      return removeVal(val);
    }
  }
}
