package omni.impl;

public enum SorterOrder{
  Ascending,
  Descending;
  
  public UnsupportedOperationException invalid() {
    return new UnsupportedOperationException("Invalid SorterOrder "+this);
  }
}
