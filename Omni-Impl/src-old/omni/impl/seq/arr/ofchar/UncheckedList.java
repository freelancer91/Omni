package omni.impl.seq.arr.ofchar;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.CharConsumer;
import omni.util.ArrCopy;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniList.OfChar{
  UncheckedList(){
    super();
  }
  UncheckedList(int capacity){
    super(capacity);
  }
  UncheckedList(int size,char[] arr){
    super(size,arr);
  }
  @Override public Object clone(){
    final char[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new char[size],0,size);
    }else{
      arr=null;
    }
    return new UncheckedList(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfChar iterator(){
    return new UncheckedAscendingItr(this);
  }
  @Override public OmniListIterator.OfChar listIterator(){
    return new UncheckedBidirectionalItr(this);
  }
  @Override public OmniListIterator.OfChar listIterator(int index){
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
  @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
    return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
  }
  @Override void uncheckedCopyInto(char[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Character[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(double[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(float[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(int[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(long[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,CharConsumer action){
    uncheckedForwardForEachInRange(arr,0,size,action);
  }
  @Override int uncheckedHashCode(int size){
    return forwardHashCode(arr,0,size);
  }
  @Override boolean uncheckedRemoveFirstMatch(int size,int val){
    final var arr=this.arr;
    int index=0;
    do{
      if(arr[index]==val){
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