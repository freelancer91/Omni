package omni.impl.seq.arr.ofref;
import java.util.function.Consumer;
import omni.api.OmniIterator;
class UncheckedAscendingItr<E> implements OmniIterator.OfRef<E>{
  private transient final UncheckedList<E> root;
  private transient int cursor;
  UncheckedAscendingItr(UncheckedList<E> root){
    this.root=root;
  }
  @Override public void forEachRemaining(Consumer<? super E> action){
    final UncheckedList<E> root;
    final int cursor,bound;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
      this.cursor=bound;
    }
  }
  @Override public boolean hasNext(){
    return this.cursor!=root.size;
  }
  @Override @SuppressWarnings("unchecked") public E next(){
    return (E)root.arr[cursor++];
  }
  @Override public void remove(){
    final UncheckedList<E> root;
    AbstractSeq.eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
  }
}