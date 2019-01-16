package omni.impl.seq;
import omni.api.OmniCollection;
abstract class AbstractSeq implements OmniCollection{
  transient int size;
  protected AbstractSeq(){
    super();
  }
  protected AbstractSeq(int size){
    super();
    this.size=size;
  }
  @Override public void clear(){
    size=0;
  }
  @Override public abstract Object clone();
  @Override public abstract boolean equals(Object val);
  @Override public abstract int hashCode();
  @Override public boolean isEmpty(){
    return size==0;
  }
  @Override public int size(){
    return size;
  }
  @Override public abstract String toString();
  static abstract class Of16BitPrimitive extends AbstractSeq{
    public Of16BitPrimitive(){
      super();
    }
    public Of16BitPrimitive(int size){
      super(size);
    }
    @Override public boolean contains(byte val){
      return contains((short)val);
    }
    @Override public boolean removeVal(byte val){
      return removeVal((short)val);
    }
  }
  static abstract class OfDouble extends AbstractSeq implements OmniCollection.OfDouble{
    public OfDouble(){
      super();
    }
    public OfDouble(int size){
      super(size);
    }
    @Override public boolean contains(byte val){
      return contains((int)val);
    }
    @Override public boolean contains(char val){
      return contains((int)val);
    }
    @Override public boolean contains(short val){
      return contains((int)val);
    }
    @Override public boolean removeVal(byte val){
      return removeVal((int)val);
    }
    @Override public boolean removeVal(char val){
      return removeVal((int)val);
    }
    @Override public boolean removeVal(short val){
      return removeVal((int)val);
    }
  }
  static abstract class OfFloat extends AbstractSeq implements OmniCollection.OfFloat{
    public OfFloat(){
      super();
    }
    public OfFloat(int size){
      super(size);
    }
    @Override public boolean contains(byte val){
      return containsRawInt(val);
    }
    @Override public boolean contains(char val){
      return containsRawInt(val);
    }
    @Override public boolean contains(short val){
      return containsRawInt(val);
    }
    @Override public boolean removeVal(byte val){
      return removeValRawInt(val);
    }
    @Override public boolean removeVal(char val){
      return removeValRawInt(val);
    }
    @Override public boolean removeVal(short val){
      return removeValRawInt(val);
    }
    protected abstract boolean containsRawInt(int val);
    protected abstract boolean removeValRawInt(int val);
  }
  static abstract class OfSignedIntegralPrimitive extends AbstractSeq{
    public OfSignedIntegralPrimitive(){
      super();
    }
    public OfSignedIntegralPrimitive(int size){
      super(size);
    }
    @Override public boolean contains(byte val){
      return contains((int)val);
    }
    @Override public boolean contains(char val){
      return contains((int)val);
    }
    @Override public boolean removeVal(byte val){
      return removeVal((int)val);
    }
    @Override public boolean removeVal(char val){
      return removeVal((int)val);
    }
  }
}
