package omni.impl;

public enum FunctionCallType{
    UNBOXED(false),BOXED(true);
    public boolean boxed;
    FunctionCallType(boolean boxed){
        this.boxed=boxed;
    }
}
