package omni.impl.seq;

import omni.util.NotYetImplementedException;

interface SequenceEqualityUtil{
    static boolean isEqual(PackedBooleanArrDeq pbad,int pbadTail,BooleanArrDeq bad,int badTail) {
        //TODO
        throw NotYetImplementedException.getNYI();
    }
    static boolean isEqual(PackedBooleanArrDeq pbad,int pbadTail,RefArrDeq<?> rad,int radTail) {
        //TODO
        throw NotYetImplementedException.getNYI();
    }
    static boolean isEqual(PackedBooleanArrDeq pbad,int pbadTail,BooleanDblLnkSeq bdls,int bdlsSize) {
        //TODO
        throw NotYetImplementedException.getNYI();
    }
    static boolean isEqual(PackedBooleanArrDeq pbad,int pbadTail,RefDblLnkSeq<?> rdls,int rdlsSize) {
        //TODO
        throw NotYetImplementedException.getNYI();
    }
}
