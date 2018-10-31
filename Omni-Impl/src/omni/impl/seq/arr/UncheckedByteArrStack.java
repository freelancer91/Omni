package omni.impl.seq.arr;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.function.ByteConsumer;
import omni.util.ArrCopy;
public class UncheckedByteArrStack extends AbstractByteArrSeq.Unchecked implements OmniStack.OfByte{
  UncheckedByteArrStack(){
    super();
  }
  UncheckedByteArrStack(int capacity){
    super(capacity);
  }
  private UncheckedByteArrStack(int size,byte[] arr){
    super(size,arr);
  }
  @Override public Object clone(){
    final byte[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new byte[size],0,size);
    }else{
      arr=null;
    }
    return new UncheckedByteArrStack(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfByte iterator(){
    return new DescendingItr(this);
  }
  @Override public Byte poll(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopByte(size-1); }
    return null;
  }
  @Override public byte pollByte(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopByte(size-1); }
    return Byte.MIN_VALUE;
  }
  @Override public double pollDouble(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopByte(size-1); }
    return Double.NaN;
  }
  @Override public float pollFloat(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopByte(size-1); }
    return Float.NaN;
  }
  @Override public int pollInt(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopByte(size-1); }
    return Integer.MIN_VALUE;
  }
  @Override public long pollLong(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopByte(size-1); }
    return Long.MIN_VALUE;
  }
  @Override public short pollShort(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopByte(size-1); }
    return Short.MIN_VALUE;
  }
  @Override public Byte pop(){
    return super.uncheckedPopByte(size-1);
  }
  @Override public byte popByte(){
    return super.uncheckedPopByte(size-1);
  }
  @Override public void push(Byte val){
    super.push(val);
  }
  @Override void uncheckedCopyInto(byte[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(Byte[] arr,int size){
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
  @Override void uncheckedForEach(int size,ByteConsumer action){
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
  private static class DescendingItr extends AbstractDescendingItr implements OmniIterator.OfByte{
    private DescendingItr(Unchecked root){
      super(root);
    }
    @Override public void forEachRemaining(ByteConsumer action){
      int cursor;
      if((cursor=this.cursor)!=0){
        uncheckedReverseForEachInRange(root.arr,0,cursor,action);
        this.cursor=0;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      int cursor;
      if((cursor=this.cursor)!=0){
        uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
        this.cursor=0;
      }
    }
    @Override public boolean hasNext(){
      return cursor!=0;
    }
    @Override public Byte next(){
      return super.nextByte();
    }
    @Override public void remove(){
      final Unchecked root;
      eraseIndexHelper((root=this.root).arr,cursor,--root.size);
    }
  }
}
