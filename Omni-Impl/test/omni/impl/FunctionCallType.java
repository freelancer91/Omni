package omni.impl;

public enum FunctionCallType{
    Boxed(true),Unboxed(false);
    public final boolean boxed;
    FunctionCallType(boolean boxed){
        this.boxed=boxed;
    }
}
