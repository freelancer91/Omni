package omni.impl.set;

public enum ItrRemoveScenario{
    PostNext(null,false),PostRemove(IllegalStateException.class,false),PostInit(IllegalStateException.class,true);
    final Class<? extends Throwable> expectedException;
    final boolean validForEmptySet;
    ItrRemoveScenario(Class<? extends Throwable> expectedException,boolean validForEmptySet){
        this.expectedException=expectedException;
        this.validForEmptySet=validForEmptySet;
    }
}
