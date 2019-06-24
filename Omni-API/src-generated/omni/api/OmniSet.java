package omni.api;
public abstract interface OmniSet<E> extends OmniCollection<E>
{
    public abstract interface OfBoolean extends OmniCollection<Boolean>,OmniCollection.OfPrimitive<Boolean>,OmniCollection.OfBoolean,OmniSet<Boolean>
  ,BooleanInput<Boolean>,ByteOutput<Boolean>,CharOutput<Boolean>
    {}
    public abstract interface OfByte extends OmniCollection<Byte>,OmniCollection.OfPrimitive<Byte>,OmniCollection.OfByte,OmniSet<Byte>
  ,ByteInput<Byte>,ByteOutput<Byte>
    {}
    public abstract interface OfChar extends OmniCollection<Character>,OmniCollection.OfPrimitive<Character>,OmniCollection.OfChar,OmniSet<Character>
  ,CharInput<Character>,CharOutput<Character>
    {}
    public abstract interface OfShort extends OmniCollection<Short>,OmniCollection.OfPrimitive<Short>,OmniCollection.OfShort,OmniSet<Short>
  ,ShortInput<Short>,ShortOutput<Short>
    {}
    public abstract interface OfInt extends OmniCollection<Integer>,OmniCollection.OfPrimitive<Integer>,OmniCollection.OfInt,OmniSet<Integer>
  ,IntInput<Integer>,IntOutput<Integer>
    {}
    public abstract interface OfLong extends OmniCollection<Long>,OmniCollection.OfPrimitive<Long>,OmniCollection.OfLong,OmniSet<Long>
  ,LongInput<Long>,LongOutput<Long>
    {}
    public abstract interface OfFloat extends OmniCollection<Float>,OmniCollection.OfPrimitive<Float>,OmniCollection.OfFloat,OmniSet<Float>
  ,FloatInput<Float>,FloatOutput<Float>
    {}
    public abstract interface OfDouble extends OmniCollection<Double>,OmniCollection.OfPrimitive<Double>,OmniCollection.OfDouble,OmniSet<Double>
  ,FloatInput<Double>,DoubleOutput<Double>
    {}
  public abstract interface DoubleOutput<E> extends OmniSet<E>,OmniCollection.DoubleOutput<E>{
  }
  public abstract interface FloatOutput<E> extends DoubleOutput<E>,OmniCollection.FloatOutput<E>{
  }
  public abstract interface LongOutput<E> extends FloatOutput<E>,OmniCollection.LongOutput<E>{
  }
  public abstract interface IntOutput<E> extends LongOutput<E>,OmniCollection.IntOutput<E>{
  }
  public abstract interface ShortOutput<E> extends IntOutput<E>,OmniCollection.ShortOutput<E>{
  }
  public abstract interface CharOutput<E> extends IntOutput<E>,OmniCollection.CharOutput<E>{
  }
  public abstract interface ByteOutput<E> extends ShortOutput<E>,OmniCollection.ByteOutput<E>{
  }
  public abstract interface BooleanInput<E> extends OmniSet<E>,OmniCollection.BooleanInput<E>{
  }
  public abstract interface ByteInput<E> extends BooleanInput<E>,OmniCollection.ByteInput<E>{
  }
  public abstract interface CharInput<E> extends BooleanInput<E>,OmniCollection.CharInput<E>{
  }
  public abstract interface ShortInput<E> extends ByteInput<E>,OmniCollection.ShortInput<E>{
  }
  public abstract interface IntInput<E> extends CharInput<E>,ShortInput<E>,OmniCollection.IntInput<E>{
  }
  public abstract interface LongInput<E> extends IntInput<E>,OmniCollection.LongInput<E>{
  }
  public abstract interface FloatInput<E> extends LongInput<E>,OmniCollection.FloatInput<E>{
  }
  public abstract interface OfRef<E>extends OmniCollection<E>,OmniCollection.OfRef<E>,OmniSet<E>{}
}
