/**
 *
 */
/** @author lyonste */
module omni.util{
    requires transitive omni.function;
    requires org.junit.jupiter.api;
    exports omni.util to omni.api,omni.impl,omni.impl.seq;
}