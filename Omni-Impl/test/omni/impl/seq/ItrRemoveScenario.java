package omni.impl.seq;

public enum ItrRemoveScenario{
    PostNext(null,false,true),PostPrevious(null,false,false),PostInit(IllegalStateException.class,true,true),
    PostAdd(IllegalStateException.class,true,false),PostRemove(IllegalStateException.class,false,true);
    final Class<? extends Throwable> expectedException;
    final boolean validWithEmptySeq;
    final boolean validWithForwardItr;
    ItrRemoveScenario(Class<? extends Throwable> expectedException,boolean validWithEmptySeq,
            boolean validWithForwardItr){
        this.expectedException=expectedException;
        this.validWithEmptySeq=validWithEmptySeq;
        this.validWithForwardItr=validWithForwardItr;
    }
}
