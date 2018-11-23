package omni.impl.seq.arr.ofref;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
public class CheckedStack<E>extends AbstractSeq.Checked<E> implements OmniStack.OfRef<E>{
  CheckedStack(){
    super();
  }
  CheckedStack(int capacity){
    super(capacity);
  }
  private CheckedStack(int size,Object[] arr){
    super(size,arr);
  }
  @Override public Object clone(){
    final Object[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new Object[size],0,size);
    }else{
      arr=null;
    }
    return new CheckedStack<>(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfRef<E> iterator(){
    return new CheckedDescendingItr<>(this);
  }
  @Override public E poll(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPop(size-1);
    }
    return null;
  }
  @Override public E pop(){
    final int size;
    if((size=this.size)!=0){
      ++modCount;
      return super.uncheckedPop(size-1);
    }
    throw new NoSuchElementException();
  }
  @Override public int search(Object val){
    final int size;
    if((size=this.size)!=0){
      final var arr=this.arr;
      if(val!=null){
        final int expectedModCount=modCount;
        try{
          return uncheckedSearch(arr,size,val::equals);
        }finally{
          CheckedCollection.checkModCount(expectedModCount,modCount);
        }
      }
      return uncheckedSearch(arr,size,Objects::isNull);
    }
    return -1;
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,Consumer<? super E> action){
    final int modCount=this.modCount;
    try{
      uncheckedReverseForEachInRange(arr,0,size,action);
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override int uncheckedHashCode(int size){
    final int modCount=this.modCount;
    try{
      return reverseHashCode(arr,0,size);
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override boolean uncheckedRemoveFirstMatch(int size,Predicate<Object> pred){
    final var arr=this.arr;
    int index;
    for(index=--size;!pred.test(arr[index]);--index){
      if(index==0){ return false; }
    }
    ++modCount;
    eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override boolean uncheckedRemoveFirstNonNull(int size,Object nonNull){
    final int expectedModCount=modCount;
    try{
      final var arr=this.arr;
      int index;
      for(index=--size;!nonNull.equals(arr[index]);--index){
        if(index==0){
          CheckedCollection.checkModCount(expectedModCount,modCount);
          return false;
        }
      }
      CheckedCollection.checkModCount(expectedModCount,modCount);
      modCount=expectedModCount+1;
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }catch(final RuntimeException e){
      throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
    }
  }
  @Override void uncheckedToString(int size,StringBuilder builder){
    final int modCount=this.modCount;
    try{
      reverseToString(arr,0,size,builder);
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
}