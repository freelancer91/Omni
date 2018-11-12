package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractDoubleList extends AbstractSeq.OfDouble{
  protected AbstractDoubleList(){
    super();
  }
  protected AbstractDoubleList(int size){
    super(size);
  }
  protected abstract double getDouble(int index);
  public Double get(int index){
    return getDouble(index);
  }
  protected abstract double removeDoubleAt(int index);
  public Double remove(int index){
    return removeDoubleAt(index);
  }
  protected abstract double set(int index,double val);
  public Double set(int index,Double val){
    return set(index,(double)val);
  }
  protected abstract void add(int index,double val);
  public void add(int index,Double val){
    add(index,(double)val);
  }
  public int lastIndexOf(boolean val){
    final int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedLastIndexOfDblBits(size,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedLastIndexOfDbl0(size);
    }
    return -1;
  }
  public int lastIndexOf(double val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOf(size,val); }
    return -1;
  }
  public int lastIndexOf(float val){
    final int size;
    if((size=this.size)!=0){
      if(val==val){ return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedLastIndexOfDblNaN(size);
    }
    return -1;
  }
  public int lastIndexOf(int val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedLastIndexOfDbl0(size);
    }
    return -1;
  }
  public int lastIndexOf(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToDouble(val)){ return -1; }
        return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val));
      }
      return uncheckedLastIndexOfDbl0(size);
    }
    return -1;
  }
  public int lastIndexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Double){ return uncheckedLastIndexOf(size,(double)val); }
    return -1;
  }
  protected abstract int uncheckedLastIndexOfDbl0(int size);
  protected abstract int uncheckedLastIndexOfDblBits(int size,long dblBits);
  protected abstract int uncheckedLastIndexOfDblNaN(int size);
  private int uncheckedLastIndexOf(int size,double val){
    if(val==val){ return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
    return uncheckedLastIndexOfDblNaN(size);
  }
}
