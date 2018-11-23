package omni.impl.seq.arr.ofboolean;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.function.BooleanConsumer;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
public class UncheckedStack extends AbstractSeq.Unchecked implements OmniStack.OfBoolean{
  UncheckedStack(){
    super();
  }
  UncheckedStack(int capacity){
    super(capacity);
  }
  private UncheckedStack(int size,boolean[] arr){
    super(size,arr);
  }
  @Override public Object clone(){
    final boolean[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new boolean[size],0,size);
    }else{
      arr=null;
    }
    return new UncheckedStack(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfBoolean iterator(){
    return new UncheckedDescendingItr(this);
  }
  @Override public Boolean poll(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopBoolean(size-1); }
    return null;
  }
  @Override public boolean pollBoolean(){
    final int size;
    return (size=this.size)!=0&&super.uncheckedPopBoolean(size-1);
  }
  @Override public byte pollByte(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToByte(super.uncheckedPopBoolean(size-1)); }
    return Byte.MIN_VALUE;
  }
  @Override public char pollChar(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToChar(super.uncheckedPopBoolean(size-1)); }
    return Character.MIN_VALUE;
  }
  @Override public double pollDouble(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToDouble(super.uncheckedPopBoolean(size-1)); }
    return Double.NaN;
  }
  @Override public float pollFloat(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToFloat(super.uncheckedPopBoolean(size-1)); }
    return Float.NaN;
  }
  @Override public int pollInt(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToByte(super.uncheckedPopBoolean(size-1)); }
    return Integer.MIN_VALUE;
  }
  @Override public long pollLong(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToLong(super.uncheckedPopBoolean(size-1)); }
    return Long.MIN_VALUE;
  }
  @Override public short pollShort(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToByte(super.uncheckedPopBoolean(size-1)); }
    return Short.MIN_VALUE;
  }
  @Override public Boolean pop(){
    return super.uncheckedPopBoolean(size-1);
  }
  @Override public boolean popBoolean(){
    return super.uncheckedPopBoolean(size-1);
  }
  @Override public void push(Boolean val){
    super.push(val);
  }
  @Override void uncheckedCopyInto(boolean[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(Boolean[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(byte[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(char[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
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
  @Override void uncheckedForEach(int size,BooleanConsumer action){
    uncheckedReverseForEachInRange(arr,0,size,action);
  }
  @Override int uncheckedHashCode(int size){
    return reverseHashCode(arr,0,size);
  }
  @Override boolean uncheckedRemoveFirstMatch(int size,boolean val){
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
