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
}
