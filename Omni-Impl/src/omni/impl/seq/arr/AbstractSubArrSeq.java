package omni.impl.seq.arr;
import omni.impl.seq.AbstractSeq;
abstract class AbstractSubArrSeq extends AbstractSeq{
    transient final int rootOffset;
    AbstractSubArrSeq(int rootOffset,int size){
        super(size);
        this.rootOffset=rootOffset;
    }
    final int getBound(){
        return rootOffset+size;
    }
}
