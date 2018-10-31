package omni.impl.seq.arr;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.util.ArrCopy;
import omni.util.OmniPred;
public class UncheckedRefArrStack<E>extends AbstractRefArrSeq.Unchecked<E> implements OmniStack.OfRef<E>{
  UncheckedRefArrStack(){
    super();
  }
  UncheckedRefArrStack(int capacity){
    super(capacity);
  }
  private UncheckedRefArrStack(int size,Object[] arr){
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
    return new UncheckedRefArrStack<>(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfRef<E> iterator(){
    return new DescendingItr<>(this);
  }
  @Override public E poll(){
    final int size;
    if((size=this.size)!=0){ return super.uncheckedPop(size-1); }
    return null;
  }
  @Override public E pop(){
    return super.uncheckedPop(size-1);
  }
  @Override public int search(Object val){
    final int size;
    if((size=this.size)!=0){ return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,Consumer<? super E> action){
    uncheckedReverseForEachInRange(arr,0,size,action);
  }
  @Override int uncheckedHashCode(int size){
    return reverseHashCode(arr,0,size);
  }
  @Override boolean uncheckedRemoveFirstMatch(int size,Predicate<Object> pred){
    final var arr=this.arr;
    int index;
    for(index=--size;!pred.test(arr[index]);--index){
      if(index==0){ return false; }
    }
    eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override boolean uncheckedRemoveFirstNonNull(int size,Object nonNull){
    final var arr=this.arr;
    int index;
    for(index=--size;!nonNull.equals(arr[index]);--index){
      if(index==0){ return false; }
    }
    eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override void uncheckedToString(int size,StringBuilder builder){
    reverseToString(arr,0,size,builder);
  }
  private static class DescendingItr<E> implements OmniIterator.OfRef<E>{
    private transient final Unchecked<E> root;
    private transient int cursor;
    private DescendingItr(Unchecked<E> root){
      this.root=root;
      this.cursor=root.size;
    }
    @Override public void remove(){
      final Unchecked<E> root;
      eraseIndexHelper((root=this.root).arr,cursor,--root.size);
    }
    @Override public void forEachRemaining(Consumer<? super E> action){
      final int cursor;
      if((cursor=this.cursor)!=0){
        uncheckedReverseForEachInRange(root.arr,0,cursor,action);
        this.cursor=0;
      }
    }
    @Override public boolean hasNext(){
      return cursor!=0;
    }
    @Override @SuppressWarnings("unchecked") public E next(){
      return (E)root.arr[--cursor];
    }
  }
}