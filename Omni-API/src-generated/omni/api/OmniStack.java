package omni.api;
import omni.util.PeekAndPollIfc;
import omni.util.TypeUtil;
public abstract interface OmniStack<E> extends OmniCollection<E>,PeekAndPollIfc<E>
{
    public abstract E pop();
    public abstract Object clone();
    public abstract int search(Object val);
    public abstract int search(boolean val);
    public abstract int search(byte val);
    public abstract int search(char val);
    public abstract int search(short val);
    public abstract int search(int val);
    public abstract int search(long val);
    public abstract int search(float val);
    public abstract int search(double val);
    public abstract int search(Boolean val);
    public abstract int search(Byte val);
    public abstract int search(Character val);
    public abstract int search(Short val);
    public abstract int search(Integer val);
    public abstract int search(Long val);
    public abstract int search(Float val);
    public abstract int search(Double val);
    public abstract interface OfPrimitive<E> extends OmniCollection<E>,OmniStack<E>,OmniCollection.OfPrimitive<E>
    {
      public abstract void push(boolean val);
      @Override
      public default int search(Boolean val)
      {
        if(val!=null)
        {
          return search(val.
          booleanValue
          ());
        }
        return -1;
      }
      @Override
      public default int search(Byte val)
      {
        if(val!=null)
        {
          return search(val.
          byteValue
          ());
        }
        return -1;
      }
      @Override
      public default int search(Character val)
      {
        if(val!=null)
        {
          return search(val.
          charValue
          ());
        }
        return -1;
      }
      @Override
      public default int search(Short val)
      {
        if(val!=null)
        {
          return search(val.
          shortValue
          ());
        }
        return -1;
      }
      @Override
      public default int search(Integer val)
      {
        if(val!=null)
        {
          return search(val.
          intValue
          ());
        }
        return -1;
      }
      @Override
      public default int search(Long val)
      {
        if(val!=null)
        {
          return search(val.
          longValue
          ());
        }
        return -1;
      }
      @Override
      public default int search(Float val)
      {
        if(val!=null)
        {
          return search(val.
          floatValue
          ());
        }
        return -1;
      }
      @Override
      public default int search(Double val)
      {
        if(val!=null)
        {
          return search(val.
          doubleValue
          ());
        }
        return -1;
      }
    }
    public abstract interface OfBoolean extends OmniCollection<Boolean>,OmniStack<Boolean>,OmniCollection.OfPrimitive<Boolean>,OfPrimitive<Boolean>,OmniCollection.OfBoolean,PeekAndPollIfc.BooleanOutput<Boolean>
    ,BooleanInput<Boolean>,ByteOutput<Boolean>,CharOutput<Boolean>
    {
      public abstract Boolean pop();
      public abstract boolean popBoolean();
      public default byte popByte()
      {
        return TypeUtil.castToByte(popBoolean());
      }
      public default char popChar()
      {
        return TypeUtil.castToChar(popBoolean());
      }
      public default short popShort()
      {
        return TypeUtil.castToByte(popBoolean());
      }
      public default int popInt()
      {
        return TypeUtil.castToByte(popBoolean());
      }
      public default long popLong()
      {
        return TypeUtil.castToLong(popBoolean());
      }
      public default float popFloat()
      {
        return TypeUtil.castToFloat(popBoolean());
      }
      public default double popDouble()
      {
        return TypeUtil.castToDouble(popBoolean());
      }
      @Override
      public abstract void push(boolean val);
      public abstract void push(Boolean val);
      @Override
      public default int search(short val)
      {
        return search((int)val);
      }
      @Override
      public default int search(byte val)
      {
        return search((int)val);
      }
      @Override
      public default int search(char val)
      {
        return search((int)val);
      }
    }
    public abstract interface OfByte extends OmniCollection<Byte>,OmniStack<Byte>,OmniCollection.OfPrimitive<Byte>,OfPrimitive<Byte>,OmniCollection.OfByte,PeekAndPollIfc.ByteOutput<Byte>
    ,ByteInput<Byte>,ByteOutput<Byte>
    {
      public abstract Byte pop();
      public abstract byte popByte();
      public default double popDouble()
      {
        return popByte();
      }
      public default float popFloat()
      {
        return popByte();
      }
      public default long popLong()
      {
        return popByte();
      }
      public default int popInt()
      {
        return popByte();
      }
      public default short popShort()
      {
        return popByte();
      }
      public abstract void push(byte val);
      public abstract void push(Byte val);
      @Override
      public default void push(boolean val)
      {
        push((byte)TypeUtil.
        castToByte
        (val));
      }
      public default void push(Boolean val)
      {
        push((byte)TypeUtil.
        castToByte
        (val.booleanValue()));
      }
      @Override
      public default int search(short val)
      {
        return search((int)val);
      }
    }
    public abstract interface OfChar extends OmniCollection<Character>,OmniStack<Character>,OmniCollection.OfPrimitive<Character>,OfPrimitive<Character>,OmniCollection.OfChar,PeekAndPollIfc.CharOutput<Character>
    ,CharInput<Character>,CharOutput<Character>
    {
      public abstract Character pop();
      public abstract char popChar();
      public default double popDouble()
      {
        return popChar();
      }
      public default float popFloat()
      {
        return popChar();
      }
      public default long popLong()
      {
        return popChar();
      }
      public default int popInt()
      {
        return popChar();
      }
      public abstract void push(char val);
      public abstract void push(Character val);
      @Override
      public default void push(boolean val)
      {
        push((char)TypeUtil.
        castToChar
        (val));
      }
      public default void push(Boolean val)
      {
        push((char)TypeUtil.
        castToChar
        (val.booleanValue()));
      }
      @Override
      public default int search(byte val)
      {
        return search((short)val);
      }
    }
    public abstract interface OfShort extends OmniCollection<Short>,OmniStack<Short>,OmniCollection.OfPrimitive<Short>,OfPrimitive<Short>,OmniCollection.OfShort,PeekAndPollIfc.ShortOutput<Short>
    ,ShortInput<Short>,ShortOutput<Short>
    {
      public abstract Short pop();
      public abstract short popShort();
      public default double popDouble()
      {
        return popShort();
      }
      public default float popFloat()
      {
        return popShort();
      }
      public default long popLong()
      {
        return popShort();
      }
      public default int popInt()
      {
        return popShort();
      }
      public abstract void push(short val);
      public abstract void push(Short val);
      @Override
      public default void push(boolean val)
      {
        push((short)TypeUtil.
        castToByte
        (val));
      }
      public default void push(Boolean val)
      {
        push((short)TypeUtil.
        castToByte
        (val.booleanValue()));
      }
      public default void push(byte val)
      {
        push((short)val);
      }
      public default void push(Byte val)
      {
        push((short)val.byteValue());
      }
      @Override
      public default int search(byte val)
      {
        return search((short)val);
      }
    }
    public abstract interface OfInt extends OmniCollection<Integer>,OmniStack<Integer>,OmniCollection.OfPrimitive<Integer>,OfPrimitive<Integer>,OmniCollection.OfInt,PeekAndPollIfc.IntOutput<Integer>
    ,IntInput<Integer>,IntOutput<Integer>
    {
      public abstract Integer pop();
      public abstract int popInt();
      public default double popDouble()
      {
        return popInt();
      }
      public default float popFloat()
      {
        return popInt();
      }
      public default long popLong()
      {
        return popInt();
      }
      public abstract void push(int val);
      public abstract void push(Integer val);
      @Override
      public default void push(boolean val)
      {
        push((int)TypeUtil.
        castToByte
        (val));
      }
      public default void push(Boolean val)
      {
        push((int)TypeUtil.
        castToByte
        (val.booleanValue()));
      }
      public default void push(char val)
      {
        push((int)val);
      }
      public default void push(Character val)
      {
        push((int)val.charValue());
      }
      public default void push(byte val)
      {
        push((int)val);
      }
      public default void push(Byte val)
      {
        push((int)val.byteValue());
      }
      public default void push(short val)
      {
        push((int)val);
      }
      public default void push(Short val)
      {
        push((int)val.shortValue());
      }
      @Override
      public default int search(short val)
      {
        return search((int)val);
      }
      @Override
      public default int search(byte val)
      {
        return search((int)val);
      }
      @Override
      public default int search(char val)
      {
        return search((int)val);
      }
    }
    public abstract interface OfLong extends OmniCollection<Long>,OmniStack<Long>,OmniCollection.OfPrimitive<Long>,OfPrimitive<Long>,OmniCollection.OfLong,PeekAndPollIfc.LongOutput<Long>
    ,LongInput<Long>,LongOutput<Long>
    {
      public abstract Long pop();
      public abstract long popLong();
      public default double popDouble()
      {
        return popLong();
      }
      public default float popFloat()
      {
        return popLong();
      }
      public abstract void push(long val);
      public abstract void push(Long val);
      @Override
      public default void push(boolean val)
      {
        push((long)TypeUtil.
        castToLong
        (val));
      }
      public default void push(Boolean val)
      {
        push((long)TypeUtil.
        castToLong
        (val.booleanValue()));
      }
      public default void push(char val)
      {
        push((long)val);
      }
      public default void push(Character val)
      {
        push((long)val.charValue());
      }
      public default void push(byte val)
      {
        push((long)val);
      }
      public default void push(Byte val)
      {
        push((long)val.byteValue());
      }
      public default void push(short val)
      {
        push((long)val);
      }
      public default void push(Short val)
      {
        push((long)val.shortValue());
      }
      public default void push(int val)
      {
        push((long)val);
      }
      public default void push(Integer val)
      {
        push((long)val.intValue());
      }
      @Override
      public default int search(short val)
      {
        return search((int)val);
      }
      @Override
      public default int search(byte val)
      {
        return search((int)val);
      }
      @Override
      public default int search(char val)
      {
        return search((int)val);
      }
    }
    public abstract interface OfFloat extends OmniCollection<Float>,OmniStack<Float>,OmniCollection.OfPrimitive<Float>,OfPrimitive<Float>,OmniCollection.OfFloat,PeekAndPollIfc.FloatOutput<Float>
    ,FloatInput<Float>,FloatOutput<Float>
    {
      public abstract Float pop();
      public abstract float popFloat();
      public default double popDouble()
      {
        return popFloat();
      }
      public abstract void push(float val);
      public abstract void push(Float val);
      @Override
      public default void push(boolean val)
      {
        push((float)TypeUtil.
        castToFloat
        (val));
      }
      public default void push(Boolean val)
      {
        push((float)TypeUtil.
        castToFloat
        (val.booleanValue()));
      }
      public default void push(char val)
      {
        push((float)val);
      }
      public default void push(Character val)
      {
        push((float)val.charValue());
      }
      public default void push(byte val)
      {
        push((float)val);
      }
      public default void push(Byte val)
      {
        push((float)val.byteValue());
      }
      public default void push(short val)
      {
        push((float)val);
      }
      public default void push(Short val)
      {
        push((float)val.shortValue());
      }
      public default void push(int val)
      {
        push((float)val);
      }
      public default void push(Integer val)
      {
        push((float)val.intValue());
      }
      public default void push(long val)
      {
        push((float)val);
      }
      public default void push(Long val)
      {
        push((float)val.longValue());
      }
      @Override
      public default int search(byte val)
      {
        return search((short)val);
      }
    }
    public abstract interface OfDouble extends OmniCollection<Double>,OmniStack<Double>,OmniCollection.OfPrimitive<Double>,OfPrimitive<Double>,OmniCollection.OfDouble,PeekAndPollIfc.DoubleOutput<Double>
    ,FloatInput<Double>,DoubleOutput<Double>
    {
      public abstract Double pop();
      public abstract double popDouble();
      public abstract void push(double val);
      public abstract void push(Double val);
      @Override
      public default void push(boolean val)
      {
        push((double)TypeUtil.
        castToDouble
        (val));
      }
      public default void push(Boolean val)
      {
        push((double)TypeUtil.
        castToDouble
        (val.booleanValue()));
      }
      public default void push(char val)
      {
        push((double)val);
      }
      public default void push(Character val)
      {
        push((double)val.charValue());
      }
      public default void push(byte val)
      {
        push((double)val);
      }
      public default void push(Byte val)
      {
        push((double)val.byteValue());
      }
      public default void push(short val)
      {
        push((double)val);
      }
      public default void push(Short val)
      {
        push((double)val.shortValue());
      }
      public default void push(int val)
      {
        push((double)val);
      }
      public default void push(Integer val)
      {
        push((double)val.intValue());
      }
      public default void push(long val)
      {
        push((double)val);
      }
      public default void push(Long val)
      {
        push((double)val.longValue());
      }
      public default void push(float val)
      {
        push((double)val);
      }
      public default void push(Float val)
      {
        push((double)val.floatValue());
      }
      @Override
      public default int search(short val)
      {
        return search((int)val);
      }
      @Override
      public default int search(byte val)
      {
        return search((int)val);
      }
      @Override
      public default int search(char val)
      {
        return search((int)val);
      }
    }
    public abstract interface DoubleOutput<E> extends OmniStack<E>,OmniCollection.DoubleOutput<E>
    {
      public abstract double popDouble();
    }
    public abstract interface FloatOutput<E> extends DoubleOutput<E>,OmniCollection.FloatOutput<E>
    {
      public abstract float popFloat();
    }
    public abstract interface LongOutput<E> extends FloatOutput<E>,OmniCollection.LongOutput<E>
    {
      public abstract long popLong();
    }
    public abstract interface IntOutput<E> extends LongOutput<E>,OmniCollection.IntOutput<E>
    {
      public abstract int popInt();
    }
    public abstract interface ShortOutput<E> extends IntOutput<E>,OmniCollection.ShortOutput<E>
    {
      public abstract short popShort();
    }
    public abstract interface CharOutput<E> extends IntOutput<E>,OmniCollection.CharOutput<E>
    {
      public abstract char popChar();
    }
    public abstract interface ByteOutput<E> extends ShortOutput<E>,OmniCollection.ByteOutput<E>
    {
      public abstract byte popByte();
    }
    public abstract interface BooleanInput<E> extends OmniStack<E>,OmniCollection.BooleanInput<E>{
      public abstract void push(boolean val);
      public abstract void push(Boolean val);
    }
    public abstract interface ByteInput<E> extends BooleanInput<E>,OmniCollection.ByteInput<E>{
      public abstract void push(byte val);
      public abstract void push(Byte val);
    }
    public abstract interface CharInput<E> extends BooleanInput<E>,OmniCollection.CharInput<E>{
      public abstract void push(char val);
      public abstract void push(Character val);
    }
    public abstract interface ShortInput<E> extends ByteInput<E>,OmniCollection.ShortInput<E>{
      public abstract void push(short val);
      public abstract void push(Short val);
    }
    public abstract interface IntInput<E> extends CharInput<E>,ShortInput<E>,OmniCollection.IntInput<E>{
      public abstract void push(int val);
      public abstract void push(Integer val);
    }
    public abstract interface LongInput<E> extends IntInput<E>,OmniCollection.LongInput<E>{
      public abstract void push(long val);
      public abstract void push(Long val);
    }
    public abstract interface FloatInput<E> extends LongInput<E>,OmniCollection.FloatInput<E>{
      public abstract void push(float val);
      public abstract void push(Float val);
    }
  public abstract interface OfRef<E> extends OmniCollection<E>,OmniStack<E>,OmniCollection.OfRef<E>,PeekAndPollIfc<E>
  {
    public abstract E pop();
    public abstract void push(E val);
  }
}
