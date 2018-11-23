package omni.api;
public interface OmniSet extends OmniCollection{
    interface OfBoolean extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfBoolean,OmniSet{
    }
    interface OfByte extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfByte,OmniSet{
    }
    interface OfChar extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfChar,OmniSet{
    }
    interface OfDouble extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfDouble,OmniSet{
    }
    interface OfFloat extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfFloat,OmniSet{
    }
    interface OfInt extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfInt,OmniSet{
    }
    interface OfLong extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfLong,OmniSet{
    }
    interface OfRef<E>extends OmniCollection,OmniCollection.OfRef<E>,OmniSet{
    }
    interface OfShort extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfShort,OmniSet{
    }
}
