package omni.impl.seq.arr.ofbyte;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.function.ByteConsumer;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
public class CheckedStack extends AbstractSeq.Checked implements OmniStack.OfByte{
  CheckedStack(){
    super();
  }
  CheckedStack(int capacity){
    super(capacity);
  }
  private CheckedStack(int size,byte[] arr){
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
    return new CheckedStack(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfByte iterator(){
    return new CheckedDescendingItr(this);
  }
  @Override public Byte poll(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopByte(size-1);
    }
    return null;
  }
  @Override public byte pollByte(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopByte(size-1);
    }
    return Byte.MIN_VALUE;
  }
  @Override public double pollDouble(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopByte(size-1);
    }
    return Double.NaN;
  }
  @Override public float pollFloat(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopByte(size-1);
    }
    return Float.NaN;
  }
  @Override public int pollInt(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopByte(size-1);
    }
    return Integer.MIN_VALUE;
  }
  @Override public long pollLong(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopByte(size-1);
    }
    return Long.MIN_VALUE;
  }
  @Override public short pollShort(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopByte(size-1);
    }
    return Short.MIN_VALUE;
  }
  @Override public Byte pop(){
    return super.popByte();
  }
  @Override public void push(Byte val){
    super.push(val);
  }
  @Override void uncheckedCopyInto(byte[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Byte[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(double[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(float[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(int[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(long[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(short[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,ByteConsumer action){
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
  @Override boolean uncheckedRemoveFirstMatch(int size,int val){
    final var arr=this.arr;
    int index;
    for(index=--size;arr[index]!=val;--index){
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