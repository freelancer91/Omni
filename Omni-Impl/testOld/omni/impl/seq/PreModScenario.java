package omni.impl.seq;

import java.util.ConcurrentModificationException;

public enum PreModScenario{
    ModSeq(ConcurrentModificationException.class,false,true),
    ModParent(ConcurrentModificationException.class,true,false),
    ModRoot(ConcurrentModificationException.class,true,false),NoMod(null,true,true);
    public final Class<? extends Throwable> expectedException;
    public final boolean appliesToSubList;
    public final boolean appliesToRootItr;
    PreModScenario(Class<? extends Throwable> expectedException,boolean appliesToSubList,boolean appliesToRootItr){
        this.expectedException=expectedException;
        this.appliesToSubList=appliesToSubList;
        this.appliesToRootItr=appliesToRootItr;
    }
}
