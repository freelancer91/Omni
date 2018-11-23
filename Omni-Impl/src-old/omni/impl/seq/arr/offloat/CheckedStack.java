package omni.impl.seq.arr.offloat;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.function.FloatConsumer;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
public class CheckedStack extends AbstractSeq.Checked implements OmniStack.OfFloat{
  CheckedStack(){
    super();
  }
  CheckedStack(int capacity){
    super(capacity);
  }
  private CheckedStack(int size,float[] arr){
    super(size,arr);
  }
  @Override public Object clone(){
    final float[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new float[size],0,size);
    }else{
      arr=null;
    }
    return new CheckedStack(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfFloat iterator(){
    return new CheckedDescendingItr(this);
  }
  @Override public Float poll(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopFloat(size-1);
    }
    return null;
  }
  @Override public double pollDouble(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopFloat(size-1);
    }
    return Double.NaN;
  }
  @Override public float pollFloat(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPopFloat(size-1);
    }
    return Float.NaN;
  }
  @Override public Float pop(){
    return super.popFloat();
  }
  @Override public void push(Float val){
    super.push(val);
  }
  @Override void uncheckedCopyInto(double[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(float[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Float[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,FloatConsumer action){
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
  @Override boolean uncheckedRemoveFirstFlt0(int size){
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
  @Override boolean uncheckedRemoveFirstFltBits(int size,int fltBits){
    final var arr=this.arr;
    int index;
    for(index=--size;Float.floatToRawIntBits(arr[index])!=fltBits;--index){
      if(index==0){ return false; }
    }
    ++modCount;
    eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override boolean uncheckedRemoveFirstFltNaN(int size){
    final var arr=this.arr;
    int index;
    for(index=--size;!Float.isNaN(arr[index]);--index){
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
