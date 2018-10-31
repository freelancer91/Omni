package omni.impl.seq.arr;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.util.ArrCopy;
public class UncheckedIntArrStack extends AbstractIntArrSeq.Unchecked implements OmniStack.OfInt{
  UncheckedIntArrStack(){
    super();
  }
  UncheckedIntArrStack(int capacity){
    super(capacity);
  }
  private UncheckedIntArrStack(int size,int[] arr){
    super(size,arr);
  }
  @Override public Object clone(){
    final int[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new int[size],0,size);
    }else{
      arr=null;
    }
    return new UncheckedIntArrStack(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfInt iterator(){
    return new DescendingItr(this);
  }
  @Override public Integer poll(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopInt(size-1); }
    return null;
  }
  @Override public double pollDouble(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopInt(size-1); }
    return Double.NaN;
  }
  @Override public float pollFloat(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopInt(size-1); }
    return Float.NaN;
  }
  @Override public int pollInt(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopInt(size-1); }
    return Integer.MIN_VALUE;
  }
  @Override public long pollLong(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopInt(size-1); }
    return Long.MIN_VALUE;
  }
  @Override public Integer pop(){
    return super.uncheckedPopInt(size-1);
  }
  @Override public int popInt(){
    return super.uncheckedPopInt(size-1);
  }
  @Override public void push(Integer val){
    super.push(val);
  }
  @Override void uncheckedCopyInto(double[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(float[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(int[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(Integer[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(long[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(Object[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedForEach(int size,IntConsumer action){
    uncheckedReverseForEachInRange(arr,0,size,action);
  }
  @Override int uncheckedHashCode(int size){
    return reverseHashCode(arr,0,size);
  }
  @Override boolean uncheckedRemoveFirstMatch(int size,int val){
    final var arr=this.arr;
    int index;
    for(index=--size;arr[index]!=val;--index){
      if(index==0){ return false; }
    }
    eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override void uncheckedToString(int size,StringBuilder builder){
    reverseToString(arr,0,size,builder);
  }
  private static class DescendingItr extends AbstractDescendingItr implements OmniIterator.OfInt{
    private DescendingItr(Unchecked root){
      super(root);
    }
    @Override public void remove(){
      final Unchecked root;
      eraseIndexHelper((root=this.root).arr,cursor,--root.size);
    }
    @Override public void forEachRemaining(Consumer<? super Integer> action){
      int cursor;
      if((cursor=this.cursor)!=0){
        uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
        this.cursor=0;
      }
    }
    @Override public void forEachRemaining(IntConsumer action){
      int cursor;
      if((cursor=this.cursor)!=0){
        uncheckedReverseForEachInRange(root.arr,0,cursor,action);
        this.cursor=0;
      }
    }
    @Override public boolean hasNext(){
      return cursor!=0;
    }
    @Override public Integer next(){
      return super.nextInt();
    }
  }
}
