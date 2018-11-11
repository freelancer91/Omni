package omni.impl.seq.arr;
import omni.impl.seq.AbstractSeq;
public abstract class AbstractSubArrSeq extends AbstractSeq{
  public transient final int rootOffset;
  protected AbstractSubArrSeq(int rootOffset,int size){
    super(size);
    this.rootOffset=rootOffset;
  }
  public final int getBound(){
    return rootOffset+size;
  }
  public static abstract class OfDouble extends AbstractSubArrSeq{
    protected OfDouble(int rootOffset,int size){
      super(rootOffset,size);
    }
    public boolean contains(byte val){
      return contains((int)val);
    }
    public boolean contains(char val){
      return contains((int)val);
    }
    public boolean contains(short val){
      return contains((int)val);
    }
    protected abstract boolean contains(int val);
    public boolean removeVal(byte val){
      return removeVal((int)val);
    }
    public boolean removeVal(char val){
      return removeVal((int)val);
    }
    public boolean removeVal(short val){
      return removeVal((int)val);
    }
    protected abstract boolean removeVal(int val);
  }
  public static abstract class OfFloat extends AbstractSubArrSeq{
    public OfFloat(int rootOffset,int size){
      super(rootOffset,size);
    }
    public boolean contains(byte val){
      return containsRawInt(val);
    }
    public boolean contains(char val){
      return containsRawInt(val);
    }
    public boolean contains(short val){
      return containsRawInt(val);
    }
    protected abstract boolean containsRawInt(int val);
    public boolean removeVal(byte val){
      return removeRawInt(val);
    }
    public boolean removeVal(char val){
      return removeRawInt(val);
    }
    public boolean removeVal(short val){
      return removeRawInt(val);
    }
    protected abstract boolean removeRawInt(int val);
  }
  public static abstract class Of16BitPrimitive extends AbstractSubArrSeq{
    protected Of16BitPrimitive(int rootOffset,int size){
      super(rootOffset,size);
    }
    public boolean removeVal(byte val){
      return removeVal((short)val);
    }
    public boolean contains(byte val){
      return contains((short)val);
    }
    protected abstract boolean removeVal(short val);
    protected abstract boolean contains(short val);
  }
  public static abstract class OfSignedIntegralPrimitive extends AbstractSubArrSeq{
    protected OfSignedIntegralPrimitive(int rootOffset,int size){
      super(rootOffset,size);
    }
    public boolean contains(byte val){
      return contains((int)val);
    }
    public boolean contains(char val){
      return contains((int)val);
    }
    public boolean removeVal(byte val){
      return removeVal((int)val);
    }
    public boolean removeVal(char val){
      return removeVal((int)val);
    }
    protected abstract boolean removeVal(int val);
    protected abstract boolean contains(int val);
  }
}
