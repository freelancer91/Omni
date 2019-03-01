package omni.api;
public abstract interface OmniSet extends OmniCollection
{
    public abstract interface OfBoolean extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfBoolean,OmniSet{}
    public abstract interface OfByte extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfByte,OmniSet{}
    public abstract interface OfChar extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfChar,OmniSet{}
    public abstract interface OfShort extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfShort,OmniSet{}
    public abstract interface OfInt extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfInt,OmniSet{}
    public abstract interface OfLong extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfLong,OmniSet{}
    public abstract interface OfFloat extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfFloat,OmniSet{}
    public abstract interface OfDouble extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfDouble,OmniSet{}
  public abstract interface OfRef<E>extends OmniCollection,OmniCollection.OfRef<E>,OmniSet{}
}
