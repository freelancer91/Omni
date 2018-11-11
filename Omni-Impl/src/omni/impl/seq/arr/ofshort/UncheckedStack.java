package omni.impl.seq.arr.ofshort;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.function.ShortConsumer;
import omni.util.ArrCopy;
public class UncheckedStack extends AbstractSeq.Unchecked implements OmniStack.OfShort{
  UncheckedStack(){
    super();
  }
  UncheckedStack(int capacity){
    super(capacity);
  }
  private UncheckedStack(int size,short[] arr){
    super(size,arr);
  }
  @Override public Object clone(){
    final short[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new short[size],0,size);
    }else{
      arr=null;
    }
    return new UncheckedStack(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfShort iterator(){
    return new UncheckedDescendingItr(this);
  }
  @Override public Short poll(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopShort(size-1); }
    return null;
  }
  @Override public double pollDouble(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopShort(size-1); }
    return Double.NaN;
  }
  @Override public float pollFloat(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopShort(size-1); }
    return Float.NaN;
  }
  @Override public int pollInt(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopShort(size-1); }
    return Integer.MIN_VALUE;
  }
  @Override public long pollLong(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopShort(size-1); }
    return Long.MIN_VALUE;
  }
  @Override public short pollShort(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopShort(size-1); }
    return Short.MIN_VALUE;
  }
  @Override public Short pop(){
    return super.uncheckedPopShort(size-1);
  }
  @Override public short popShort(){
    return super.uncheckedPopShort(size-1);
  }
  @Override public void push(Short val){
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
  @Override void uncheckedCopyInto(long[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(Object[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(short[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(Short[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedForEach(int size,ShortConsumer action){
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
}
