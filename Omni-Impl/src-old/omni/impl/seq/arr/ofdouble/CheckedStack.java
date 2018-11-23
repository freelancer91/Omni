package omni.impl.seq.arr.ofdouble;
import java.util.function.DoubleConsumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
public class CheckedStack extends AbstractSeq.Checked implements OmniStack.OfDouble{
  CheckedStack(){
    super();
  }
  CheckedStack(int capacity){
    super(capacity);
  }
  private CheckedStack(int size,double[] arr){
    super(size,arr);
  }
  @Override public Object clone(){
    final double[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new double[size],0,size);
    }else{
      arr=null;
    }
    return new CheckedStack(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfDouble iterator(){
    return new CheckedDescendingItr(this);
  }
  @Override public Double poll(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopDouble(size-1);
    }
    return null;
  }
  @Override public double pollDouble(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopDouble(size-1);
    }
    return Double.NaN;
  }
  @Override public Double pop(){
    return super.popDouble();
  }
  @Override public void push(Double val){
    super.push(val);
  }
  @Override void uncheckedCopyInto(double[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Double[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,DoubleConsumer action){
    final int modCount=this.modCount;
    try{
      uncheckedReverseForEachInRange(arr,0,size,action);
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override int uncheckedHashCode(int size){
    return reverseHashCode(arr,0,size);
  }
  @Override boolean uncheckedRemoveFirstDbl0(int size){
    final var arr=this.arr;
    int index;
    for(index=--size;arr[index]!=0;--index){
      if(index==0){ return false; }
    }
    ++modCount;
    eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override boolean uncheckedRemoveFirstDblBits(int size,long dblBits){
    final var arr=this.arr;
    int index;
    for(index=--size;Double.doubleToRawLongBits(arr[index])!=dblBits;--index){
      if(index==0){ return false; }
    }
    ++modCount;
    eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override boolean uncheckedRemoveFirstDblNaN(int size){
    final var arr=this.arr;
    int index;
    for(index=--size;!Double.isNaN(arr[index]);--index){
      if(index==0){ return false; }
    }
    ++modCount;
    eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override void uncheckedToString(int size,StringBuilder builder){
    reverseToString(arr,0,size,builder);
  }
}
