package omni.impl;

public enum CheckedType{
    CHECKED(true),UNCHECKED(false);
    public final boolean checked;
    CheckedType(boolean checked){
        this.checked=checked;
    }
    public UnsupportedOperationException invalid() {
      return new UnsupportedOperationException("Unsupported CheckedType "+this);
    }
}
