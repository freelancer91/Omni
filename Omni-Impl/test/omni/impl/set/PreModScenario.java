package omni.impl.set;

import java.util.ConcurrentModificationException;

public enum PreModScenario{
    NoMod(null),ModSet(ConcurrentModificationException.class);
    public final Class<? extends Throwable> expectedException;
    PreModScenario(Class<? extends Throwable> expectedException){
        this.expectedException=expectedException;
    }
}
