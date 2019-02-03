/**
 *
 */
/** @author lyonste */
module omni.util{
    requires transitive omni.function;
    exports omni.util to omni.api,omni.impl,omni.impl.seq,omni.impl.seq.arr;
    requires junit;
}