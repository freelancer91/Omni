package omni.api;
public interface OmniSet extends OmniCollection{
    interface OfBoolean extends OmniSet,OmniCollection.OfBoolean{
    }
    interface OfByte extends OmniSet,OmniCollection.OfByte{
    }
    interface OfChar extends OmniSet,OmniCollection.OfChar{
    }
    interface OfDouble extends OmniSet,OmniCollection.OfDouble{
    }
    interface OfFloat extends OmniSet,OmniCollection.OfFloat{
    }
    interface OfInt extends OmniSet,OmniCollection.OfInt{
    }
    interface OfLong extends OmniSet,OmniCollection.OfLong{
    }
    interface OfRef<E>extends OmniSet,OmniCollection.OfRef<E>{
    }
    interface OfShort extends OmniSet,OmniCollection.OfShort{
    }
}
