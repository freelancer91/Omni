package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractFloatList extends AbstractSeq.OfFloat{
  protected AbstractFloatList(){
    super();
  }
  protected AbstractFloatList(int size){
    super(size);
  }
  protected abstract float getFloat(int index);
  public Float get(int index){
    return getFloat(index);
  }
  protected abstract float removeFloatAt(int index);
  public Float remove(int index){
    return removeFloatAt(index);
  }
  protected abstract float set(int index,float val);
  public Float set(int index,Float val){
    return set(index,(float)val);
  }
  protected abstract void add(int index,float val);
  public void add(int index,Float val){
    add(index,(float)val);
  }
  public int lastIndexOf(boolean val){
    final int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedLastIndexOfFltBits(size,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedLastIndexOfFlt0(size);
    }
    return -1;
  }
  public int lastIndexOf(char val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOfRawInt(size,val); }
    return -1;
  }
  public int lastIndexOf(double val){
    final int size;
    if((size=this.size)!=0){
      float v;
      if((v=(float)val)==val){
        return uncheckedLastIndexOfFltBits(size,Float.floatToRawIntBits(v));
      }else if(v!=v){ return uncheckedLastIndexOfFlt0(size); }
    }
    return -1;
  }
  public int lastIndexOf(float val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOf(size,val); }
    return -1;
  }
  public int lastIndexOf(int val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToDouble(val)){ return -1; }
        return uncheckedLastIndexOfFltBits(size,Float.floatToRawIntBits(val));
      }
      return uncheckedLastIndexOfFlt0(size);
    }
    return -1;
  }
  public int lastIndexOf(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToDouble(val)){ return -1; }
        return uncheckedLastIndexOfFltBits(size,Float.floatToRawIntBits(val));
      }
      return uncheckedLastIndexOfFlt0(size);
    }
    return -1;
  }
  public int lastIndexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Float){ return uncheckedLastIndexOf(size,(float)val); }
    return -1;
  }
  public int lastIndexOf(short val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOfRawInt(size,val); }
    return -1;
  }
  protected abstract int uncheckedLastIndexOfFlt0(int size);
  protected abstract int uncheckedLastIndexOfFltBits(int size,int fltBits);
  protected abstract int uncheckedLastIndexOfFltNaN(int size);
  private int uncheckedLastIndexOf(int size,float val){
    if(val==val){ return uncheckedLastIndexOfFltBits(size,Float.floatToRawIntBits(val)); }
    return uncheckedLastIndexOfFltNaN(size);
  }
  private int uncheckedLastIndexOfRawInt(int size,int val){
    if(val!=0){ return uncheckedLastIndexOfFltBits(size,Float.floatToRawIntBits(val)); }
    return uncheckedLastIndexOfFlt0(size);
  }
}
