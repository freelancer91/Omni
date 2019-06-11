package omni.impl;

import java.util.ConcurrentModificationException;

public enum IllegalMod{
    NoMod(null),
    ModSeq(ConcurrentModificationException.class),
    ModParent(ConcurrentModificationException.class),
    ModRoot(ConcurrentModificationException.class),
    ModItr(ConcurrentModificationException.class);
    public final Class<? extends Throwable> expectedException;
    IllegalMod(Class<? extends Throwable> expectedException){
        this.expectedException=expectedException;
    }
}
