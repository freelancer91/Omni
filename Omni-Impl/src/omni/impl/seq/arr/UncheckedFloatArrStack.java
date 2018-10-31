package omni.impl.seq.arr;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.function.FloatConsumer;
import omni.util.ArrCopy;
public class UncheckedFloatArrStack extends AbstractFloatArrSeq.Unchecked implements OmniStack.OfFloat{
  UncheckedFloatArrStack(){
    super();
  }
  UncheckedFloatArrStack(int capacity){
    super(capacity);
  }
  private UncheckedFloatArrStack(int size,float[] arr){
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
    return new UncheckedFloatArrStack(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfFloat iterator(){
    return new DescendingItr(this);
  }
  @Override public Float poll(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopFloat(size-1); }
    return null;
  }
  @Override public double pollDouble(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopFloat(size-1); }
    return Double.NaN;
  }
  @Override public float pollFloat(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPopFloat(size-1); }
    return Float.NaN;
  }
  @Override public Float pop(){
    return super.uncheckedPopFloat(size-1);
  }
  @Override public float popFloat(){
    return super.uncheckedPopFloat(size-1);
  }
  @Override public void push(Float val){
    super.push(val);
  }
  @Override void uncheckedCopyInto(double[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(float[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(Float[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedCopyInto(Object[] arr,int size){
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override void uncheckedForEach(int size,FloatConsumer action){
    uncheckedReverseForEachInRange(arr,0,size,action);
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
    eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override void uncheckedToString(int size,StringBuilder builder){
    reverseToString(arr,0,size,builder);
  }
  private static class DescendingItr extends AbstractDescendingItr implements OmniIterator.OfFloat{
    private DescendingItr(Unchecked root){
      super(root);
    }
    @Override public void remove(){
      final Unchecked root;
      eraseIndexHelper((root=this.root).arr,cursor,--root.size);
    }
    @Override public void forEachRemaining(Consumer<? super Float> action){
      int cursor;
      if((cursor=this.cursor)!=0){
        uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
        this.cursor=0;
      }
    }
    @Override public void forEachRemaining(FloatConsumer action){
      int cursor;
      if((cursor=this.cursor)!=0){
        uncheckedReverseForEachInRange(root.arr,0,cursor,action);
        this.cursor=0;
      }
    }
    @Override public boolean hasNext(){
      return cursor!=0;
    }
    @Override public Float next(){
      return super.nextFloat();
    }
  }
}
