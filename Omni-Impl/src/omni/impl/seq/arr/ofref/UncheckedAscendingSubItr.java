package omni.impl.seq.arr.ofref;
import java.util.function.Consumer;
import omni.api.OmniIterator;
class UncheckedAscendingSubItr<E> implements OmniIterator.OfRef<E>{
  private transient final UncheckedSubList<E> parent;
  private transient int cursor;
  UncheckedAscendingSubItr(UncheckedSubList<E> parent){
    this.parent=parent;
    this.cursor=parent.rootOffset;
  }
  @Override public void forEachRemaining(Consumer<? super E> action){
    final int cursor,bound;
    final UncheckedSubList<E> parent;
    if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
      AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
      this.cursor=bound;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=parent.getBound();
  }
  @Override @SuppressWarnings("unchecked") public E next(){
    return (E)parent.root.arr[cursor++];
  }
  @Override public void remove(){
    final UncheckedSubList<E> parent;
    final AbstractSeq.Unchecked<E> root;
    AbstractSeq.eraseIndexHelper((root=(parent=this.parent).root).arr,--cursor,--root.size);
    UncheckedSubList.bubbleUpDecrementSize(parent.parent);
    --parent.size;
  }
}