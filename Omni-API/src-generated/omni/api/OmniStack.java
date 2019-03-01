package omni.api;
import omni.util.PeekAndPollIfc;
import omni.util.TypeUtil;
public abstract interface OmniStack extends OmniCollection
{
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
    public abstract interface OfPrimitive extends OmniCollection,OmniStack,OmniCollection.OfPrimitive
    {
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
    public abstract interface OfBoolean extends OmniCollection,OmniStack,OmniCollection.OfPrimitive,OfPrimitive,OmniCollection.OfBoolean,PeekAndPollIfc<Boolean>,PeekAndPollIfc.BooleanInput
    ,PeekAndPollIfc.DoubleInput
    ,PeekAndPollIfc.FloatInput
    ,PeekAndPollIfc.LongInput
    ,PeekAndPollIfc.IntInput
    ,PeekAndPollIfc.ShortInput
    ,PeekAndPollIfc.ByteInput
    ,PeekAndPollIfc.CharInput
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
    public abstract interface OfByte extends OmniCollection,OmniStack,OmniCollection.OfPrimitive,OfPrimitive,OmniCollection.OfByte,PeekAndPollIfc<Byte>,PeekAndPollIfc.ByteInput
    ,PeekAndPollIfc.DoubleInput
    ,PeekAndPollIfc.FloatInput
    ,PeekAndPollIfc.LongInput
    ,PeekAndPollIfc.IntInput
    ,PeekAndPollIfc.ShortInput
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
    public abstract interface OfChar extends OmniCollection,OmniStack,OmniCollection.OfPrimitive,OfPrimitive,OmniCollection.OfChar,PeekAndPollIfc<Character>,PeekAndPollIfc.CharInput
    ,PeekAndPollIfc.DoubleInput
    ,PeekAndPollIfc.FloatInput
    ,PeekAndPollIfc.LongInput
    ,PeekAndPollIfc.IntInput
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
    public abstract interface OfShort extends OmniCollection,OmniStack,OmniCollection.OfPrimitive,OfPrimitive,OmniCollection.OfShort,PeekAndPollIfc<Short>,PeekAndPollIfc.ShortInput
    ,PeekAndPollIfc.DoubleInput
    ,PeekAndPollIfc.FloatInput
    ,PeekAndPollIfc.LongInput
    ,PeekAndPollIfc.IntInput
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
        push(val.shortValue());
      }
      @Override
      public default int search(byte val)
      {
        return search((short)val);
      }
    }
    public abstract interface OfInt extends OmniCollection,OmniStack,OmniCollection.OfPrimitive,OfPrimitive,OmniCollection.OfInt,PeekAndPollIfc<Integer>,PeekAndPollIfc.IntInput
    ,PeekAndPollIfc.DoubleInput
    ,PeekAndPollIfc.FloatInput
    ,PeekAndPollIfc.LongInput
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
      public default void push(byte val)
      {
        push((int)val);
      }
      public default void push(Byte val)
      {
        push(val.intValue());
      }
      public default void push(short val)
      {
        push((int)val);
      }
      public default void push(Short val)
      {
        push(val.intValue());
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
    public abstract interface OfLong extends OmniCollection,OmniStack,OmniCollection.OfPrimitive,OfPrimitive,OmniCollection.OfLong,PeekAndPollIfc<Long>,PeekAndPollIfc.LongInput
    ,PeekAndPollIfc.DoubleInput
    ,PeekAndPollIfc.FloatInput
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
      public default void push(byte val)
      {
        push((long)val);
      }
      public default void push(Byte val)
      {
        push(val.longValue());
      }
      public default void push(short val)
      {
        push((long)val);
      }
      public default void push(Short val)
      {
        push(val.longValue());
      }
      public default void push(int val)
      {
        push((long)val);
      }
      public default void push(Integer val)
      {
        push(val.longValue());
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
    public abstract interface OfFloat extends OmniCollection,OmniStack,OmniCollection.OfPrimitive,OfPrimitive,OmniCollection.OfFloat,PeekAndPollIfc<Float>,PeekAndPollIfc.FloatInput
    ,PeekAndPollIfc.DoubleInput
    {
      public abstract Float pop();
      public abstract float popFloat();
      public default double popDouble()
      {
        return popFloat();
      }
      public abstract void push(float val);
      public abstract void push(Float val);
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
      public default void push(byte val)
      {
        push((float)val);
      }
      public default void push(Byte val)
      {
        push(val.floatValue());
      }
      public default void push(short val)
      {
        push((float)val);
      }
      public default void push(Short val)
      {
        push(val.floatValue());
      }
      public default void push(int val)
      {
        push((float)val);
      }
      public default void push(Integer val)
      {
        push(val.floatValue());
      }
      public default void push(long val)
      {
        push((float)val);
      }
      public default void push(Long val)
      {
        push(val.floatValue());
      }
      @Override
      public default int search(byte val)
      {
        return search((short)val);
      }
    }
    public abstract interface OfDouble extends OmniCollection,OmniStack,OmniCollection.OfPrimitive,OfPrimitive,OmniCollection.OfDouble,PeekAndPollIfc<Double>,PeekAndPollIfc.DoubleInput
    {
      public abstract Double pop();
      public abstract double popDouble();
      public abstract void push(double val);
      public abstract void push(Double val);
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
      public default void push(byte val)
      {
        push((double)val);
      }
      public default void push(Byte val)
      {
        push(val.doubleValue());
      }
      public default void push(short val)
      {
        push((double)val);
      }
      public default void push(Short val)
      {
        push(val.doubleValue());
      }
      public default void push(int val)
      {
        push((double)val);
      }
      public default void push(Integer val)
      {
        push(val.doubleValue());
      }
      public default void push(long val)
      {
        push((double)val);
      }
      public default void push(Long val)
      {
        push(val.doubleValue());
      }
      public default void push(float val)
      {
        push((double)val);
      }
      public default void push(Float val)
      {
        push(val.doubleValue());
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
  public abstract interface OfRef<E> extends OmniCollection,OmniStack,OmniCollection.OfRef<E>,PeekAndPollIfc<E>
  {
    public abstract E pop();
    public abstract void push(E val);
  }
}
