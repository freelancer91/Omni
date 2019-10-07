package omni.impl;

public enum SortOrder{
  Ascending {
    @Override
    public SortOrder reverse(){
        return Descending;
    }
},
  Descending {
    @Override
    public SortOrder reverse(){
        return Ascending;
    }
};
  
  public UnsupportedOperationException invalid() {
    return new UnsupportedOperationException("Invalid SorterOrder "+this);
  }
  
  public abstract SortOrder reverse();
}
