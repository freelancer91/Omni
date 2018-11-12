package omni.impl.seq.arr.offloat;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.FloatConsumer;
import omni.util.ArrCopy;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniList.OfFloat{
  UncheckedList(){
    super();
  }
  UncheckedList(int capacity){
    super(capacity);
  }
  UncheckedList(int size,float[] arr){
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
    return new UncheckedList(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfFloat iterator(){
    return new UncheckedAscendingItr(this);
  }
  @Override public OmniListIterator.OfFloat listIterator(){
    return new UncheckedBidirectionalItr(this);
  }
  @Override public OmniListIterator.OfFloat listIterator(int index){
    return new UncheckedBidirectionalItr(this,index);
  }
  @Override public void reverseSort(){
    int size;
    if((size=this.size)>1){
      uncheckedReverseSort(arr,0,size-1);
    }
  }
  @Override public void sort(){
    int size;
    if((size=this.size)>1){
      uncheckedSort(arr,0,size-1);
    }
  }
  @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
    return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
  }
  @Override void uncheckedCopyInto(double[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(float[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Float[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,FloatConsumer action){
    uncheckedForwardForEachInRange(arr,0,size,action);
  }
  @Override int uncheckedHashCode(int size){
    return forwardHashCode(arr,0,size);
  }
  @Override boolean uncheckedRemoveFirstFlt0(int size){
    final var arr=this.arr;
    int index=0;
    do{
      if(arr[index]==0){
        eraseIndexHelper(arr,index,--size);
        this.size=size;
        return true;
      }
    }while(++index!=size);
    return false;
  }
  @Override boolean uncheckedRemoveFirstFltBits(int size,int fltBits){
    final var arr=this.arr;
    int index=0;
    do{
      if(Float.floatToRawIntBits(arr[index])==fltBits){
        eraseIndexHelper(arr,index,--size);
        this.size=size;
        return true;
      }
    }while(++index!=size);
    return false;
  }
  @Override boolean uncheckedRemoveFirstFltNaN(int size){
    final var arr=this.arr;
    int index=0;
    do{
      if(Float.isNaN(arr[index])){
        eraseIndexHelper(arr,index,--size);
        this.size=size;
        return true;
      }
    }while(++index!=size);
    return false;
  }
  @Override void uncheckedToString(int size,StringBuilder builder){
    forwardToString(arr,0,size,builder);
  }
}
