package omni.impl;

public enum IteratorRemoveScenario{
    PostInit(IllegalStateException.class),PostNext(null),PostPrev(null),PostAdd(IllegalStateException.class),
    PostRemove(IllegalStateException.class);
    public final Class<? extends Throwable> expectedException;
    IteratorRemoveScenario(Class<? extends Throwable> expectedException){
        this.expectedException=expectedException;
    }
}
