package omni.api;
public abstract interface OmniSet extends OmniCollection
{
    public abstract interface OfBoolean extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfBoolean,OmniSet
  ,BooleanInput,ByteOutput<Boolean>,CharOutput<Boolean>
    {}
    public abstract interface OfByte extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfByte,OmniSet
  ,ByteInput,ByteOutput<Byte>
    {}
    public abstract interface OfChar extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfChar,OmniSet
  ,CharInput,CharOutput<Character>
    {}
    public abstract interface OfShort extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfShort,OmniSet
  ,ShortInput,ShortOutput<Short>
    {}
    public abstract interface OfInt extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfInt,OmniSet
  ,IntInput,IntOutput<Integer>
    {}
    public abstract interface OfLong extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfLong,OmniSet
  ,LongInput,LongOutput<Long>
    {}
    public abstract interface OfFloat extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfFloat,OmniSet
  ,FloatInput,FloatOutput<Float>
    {}
    public abstract interface OfDouble extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfDouble,OmniSet
  ,FloatInput,DoubleOutput<Double>
    {}
  public abstract interface DoubleOutput<E> extends OmniSet,OmniCollection.DoubleOutput<E>{
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
  public abstract interface BooleanInput extends OmniSet,OmniCollection.BooleanInput{
  }
  public abstract interface ByteInput extends BooleanInput,OmniCollection.ByteInput{
  }
  public abstract interface CharInput extends BooleanInput,OmniCollection.CharInput{
  }
  public abstract interface ShortInput extends ByteInput,OmniCollection.ShortInput{
  }
  public abstract interface IntInput extends CharInput,ShortInput,OmniCollection.IntInput{
  }
  public abstract interface LongInput extends IntInput,OmniCollection.LongInput{
  }
  public abstract interface FloatInput extends LongInput,OmniCollection.FloatInput{
  }
  public abstract interface OfRef<E>extends OmniCollection,OmniCollection.OfRef<E>,OmniSet{}
}
