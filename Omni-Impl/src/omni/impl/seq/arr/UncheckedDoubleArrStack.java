package omni.impl.seq.arr;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.util.ArrCopy;
public class UncheckedDoubleArrStack extends AbstractDoubleArrSeq.Unchecked implements OmniStack.OfDouble{
  UncheckedDoubleArrStack(){
    super();
  }
  UncheckedDoubleArrStack(int capacity){
    super(capacity);
  }
  private UncheckedDoubleArrStack(int size,double[] arr){
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
    return new UncheckedDoubleArrStack(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfDouble iterator(){
    return new DescendingItr(this);
  }
  @Override public Double poll(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopDouble(size-1); }
    return null;
  }
  @Override public double pollDouble(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopDouble(size-1); }
    return Double.NaN;
  }
  @Override public Double pop(){
    return super.uncheckedPopDouble(size-1);
  }
  @Override public double popDouble(){
    return super.uncheckedPopDouble(size-1);
  }
  @Override public void push(Double val){
    super.push(val);
  }
  @Override void uncheckedCopyInto(double[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(Double[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(Object[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedForEach(int size,DoubleConsumer action){
    uncheckedReverseForEachInRange(arr,0,size,action);
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
    eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override void uncheckedToString(int size,StringBuilder builder){
    reverseToString(arr,0,size,builder);
  }
  private static class DescendingItr extends AbstractDescendingItr implements OmniIterator.OfDouble{
    private DescendingItr(Unchecked root){
      super(root);
    }
    @Override public void remove(){
      final Unchecked root;
      eraseIndexHelper((root=this.root).arr,cursor,--root.size);
    }
    @Override public void forEachRemaining(Consumer<? super Double> action){
      int cursor;
      if((cursor=this.cursor)!=0){
        uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
        this.cursor=0;
      }
    }
    @Override public void forEachRemaining(DoubleConsumer action){
      int cursor;
      if((cursor=this.cursor)!=0){
        uncheckedReverseForEachInRange(root.arr,0,cursor,action);
        this.cursor=0;
      }
    }
    @Override public boolean hasNext(){
      return cursor!=0;
    }
    @Override public Double next(){
      return super.nextDouble();
    }
  }
}
