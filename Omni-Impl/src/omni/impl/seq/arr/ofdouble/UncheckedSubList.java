package omni.impl.seq.arr.ofdouble;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.DoubleComparator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
class UncheckedSubList extends AbstractSeq.Unchecked.AbstractSubList implements OmniList.OfDouble{
  UncheckedSubList(AbstractSeq.Unchecked root,int rootOffset,int size){
    super(root,rootOffset,size);
  }
  private UncheckedSubList(AbstractSeq.Unchecked root,UncheckedSubList parent,int rootOffset,int size){
    super(root,parent,rootOffset,size);
  }
  @Override public boolean add(boolean val){
    return super.add(TypeUtil.castToDouble(val));
  }
  @Override public boolean add(char val){
    return super.add(val);
  }
  @Override public boolean add(Double val){
    return super.add(val);
  }
  @Override public boolean add(float val){
    return super.add(val);
  }
  @Override public boolean add(int val){
    return super.add(val);
  }
  @Override public boolean add(long val){
    return super.add(val);
  }
  @Override public boolean add(short val){
    return super.add(val);
  }
  @Override public Object clone(){
    final double[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new double[size],0,size);
    }else{
      arr=null;
    }
    return new UncheckedList(size,arr);
  }
  @Override public boolean contains(boolean val){
    int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedContainsDblBits(size,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedContainsDbl0(size);
    }
    return false;
  }
  @Override public boolean contains(double val){
    final int size,rootOffset;
    return (size=this.size)!=0
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override public boolean contains(float val){
    int size;
    if((size=this.size)!=0){
      if(val==val){ return uncheckedContainsDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedContainsDblNaN(size);
    }
    return false;
  }
  @Override public boolean contains(int val){
    int size;
    if((size=this.size)!=0){
      if(val!=0){ return uncheckedContainsDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedContainsDbl0(size);
    }
    return false;
  }
  @Override public boolean contains(long val){
    int size;
    if((size=this.size)!=0){
      if(val!=0){ return TypeUtil.checkCastToDouble(val)
          &&uncheckedContainsDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedContainsDbl0(size);
    }
    return false;
  }
  @Override public boolean contains(Object val){
    final int size,rootOffset;
    return (size=this.size)!=0&&val instanceof Double
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,(double)val);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public void forEach(Consumer<? super Double> action){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
    }
  }
  @Override public void forEach(DoubleConsumer action){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
    }
  }
  @Override public int hashCode(){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    return 1;
  }
  @Override public int indexOf(boolean val){
    int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedIndexOfDblBits(size,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedIndexOfDbl0(size);
    }
    return -1;
  }
  @Override public int indexOf(double val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int indexOf(float val){
    int size;
    if((size=this.size)!=0){
      if(val==val){ return uncheckedIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedIndexOfDblNaN(size);
    }
    return -1;
  }
  @Override public int indexOf(int val){
    int size;
    if((size=this.size)!=0){
      if(val!=0){ return uncheckedIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedIndexOfDbl0(size);
    }
    return -1;
  }
  @Override public int indexOf(long val){
    int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToDouble(val)){ return -1; }
        return uncheckedIndexOfDblBits(size,Double.doubleToRawLongBits(val));
      }
      return uncheckedIndexOfDbl0(size);
    }
    return -1;
  }
  @Override public int indexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Double){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,(double)val);
    }
    return -1;
  }
  @Override public OmniIterator.OfDouble iterator(){
    return new UncheckedAscendingSubItr(this);
  }
  @Override public OmniListIterator.OfDouble listIterator(){
    return new UncheckedBidirectionalSubItr(this);
  }
  @Override public OmniListIterator.OfDouble listIterator(int index){
    return new UncheckedBidirectionalSubItr(this,index+rootOffset);
  }
  @Override public boolean remove(Object val){
    final int size;
    return (size=this.size)!=0&&val instanceof Double&&uncheckedRemove(size,(double)val);
  }
  @Override public boolean removeVal(boolean val){
    final int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedRemoveDblBits(size,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedRemoveDbl0(size);
    }
    return false;
  }
  @Override public boolean removeVal(double val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemove(size,val);
  }
  @Override public boolean removeVal(float val){
    final int size;
    if((size=this.size)!=0){
      if(val==val){ return uncheckedRemoveDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveDblNaN(size);
    }
    return false;
  }
  @Override public boolean removeVal(int val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return uncheckedRemoveDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveDbl0(size);
    }
    return false;
  }
  @Override public boolean removeVal(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return TypeUtil.checkCastToDouble(val)
          &&uncheckedRemoveDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveDblNaN(size);
    }
    return false;
  }
  @Override public void replaceAll(DoubleUnaryOperator operator){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      AbstractSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Double> operator){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      AbstractSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
    }
  }
  @Override public void reverseSort(){
    int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedReverseSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
    }
  }
  @Override public void sort(){
    int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
    }
  }
  @Override public void sort(Comparator<? super Double> sorter){
    final int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
    }
  }
  @Override public void sort(DoubleComparator sorter){
    final int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
    }
  }
  @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
    return new UncheckedSubList(root,this,rootOffset+fromIndex,toIndex-fromIndex);
  }
  @Override public Double[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Double[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Double[size],0,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst,0,size);
    }
    return dst;
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public double[] toDoubleArray(){
    final int size;
    if((size=this.size)!=0){
      final double[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new double[size],0,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public String toString(){
    final int size;
    if((size=this.size)!=0){
      final StringBuilder builder;
      final int rootOffset;
      AbstractSeq.forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  private boolean uncheckedContainsDbl0(int size){
    final int rootOffset;
    return AbstractSeq.uncheckedAnyMatchesDbl0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private boolean uncheckedContainsDblBits(int size,long dblBits){
    final int rootOffset;
    return AbstractSeq.uncheckedAnyMatchesDblBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,dblBits);
  }
  private boolean uncheckedContainsDblNaN(int size){
    final int rootOffset;
    return AbstractSeq.uncheckedAnyMatchesDblNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private int uncheckedIndexOfDbl0(int size){
    final int rootOffset;
    return AbstractSeq.uncheckedIndexOfDbl0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private int uncheckedIndexOfDblBits(int size,long dblBits){
    final int rootOffset;
    return AbstractSeq.uncheckedIndexOfDblBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,dblBits);
  }
  private int uncheckedIndexOfDblNaN(int size){
    final int rootOffset;
    return AbstractSeq.uncheckedIndexOfDblNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private boolean uncheckedRemove(int size,double val){
    if(val==val){ return uncheckedRemoveDblBits(size,Double.doubleToRawLongBits(val)); }
    return uncheckedRemoveDblNaN(size);
  }
  private boolean uncheckedRemoveDbl0(int size){
    int offset;
    final int bound=(offset=rootOffset)+--size;
    final AbstractSeq.Unchecked root;
    final var arr=(root=this.root).arr;
    do{
      if(arr[offset]==0){
        AbstractSeq.eraseIndexHelper(arr,offset,--root.size);
        bubbleUpDecrementSize(parent);
        this.size=size;
        return true;
      }
    }while(++offset!=bound);
    return false;
  }
  private boolean uncheckedRemoveDblBits(int size,long dblBits){
    int offset;
    final int bound=(offset=rootOffset)+--size;
    final AbstractSeq.Unchecked root;
    final var arr=(root=this.root).arr;
    do{
      if(Double.doubleToRawLongBits(arr[offset])==dblBits){
        AbstractSeq.eraseIndexHelper(arr,offset,--root.size);
        bubbleUpDecrementSize(parent);
        this.size=size;
        return true;
      }
    }while(++offset!=bound);
    return false;
  }
  private boolean uncheckedRemoveDblNaN(int size){
    int offset;
    final int bound=(offset=rootOffset)+--size;
    final AbstractSeq.Unchecked root;
    final var arr=(root=this.root).arr;
    do{
      if(Double.isNaN(arr[offset])){
        AbstractSeq.eraseIndexHelper(arr,offset,--root.size);
        bubbleUpDecrementSize(parent);
        this.size=size;
        return true;
      }
    }while(++offset!=bound);
    return false;
  }
}