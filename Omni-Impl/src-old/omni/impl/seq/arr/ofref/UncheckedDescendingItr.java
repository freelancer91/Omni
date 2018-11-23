package omni.impl.seq.arr.ofref;
import java.util.function.Consumer;
import omni.api.OmniIterator;
class UncheckedDescendingItr<E> implements OmniIterator.OfRef<E>{
  private transient final UncheckedStack<E> root;
  private transient int cursor;
  UncheckedDescendingItr(UncheckedStack<E> root){
    this.root=root;
    this.cursor=root.size;
  }
  @Override public void forEachRemaining(Consumer<? super E> action){
    final int cursor;
    if((cursor=this.cursor)!=0){
      AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action);
      this.cursor=0;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=0;
  }
  @Override @SuppressWarnings("unchecked") public E next(){
    return (E)root.arr[--cursor];
  }
  @Override public void remove(){
    final UncheckedStack<E> root;
    AbstractSeq.eraseIndexHelper((root=this.root).arr,cursor,--root.size);
  }
}