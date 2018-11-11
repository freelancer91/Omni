package omni.impl.seq.arr.ofint;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractIntItr;
class UncheckedAscendingSubItr extends AbstractIntItr implements OmniIterator.OfInt{
  private transient final UncheckedSubList parent;
  private transient int cursor;
  UncheckedAscendingSubItr(UncheckedSubList parent){
    this.parent=parent;
    cursor=parent.rootOffset;
  }
  @Override public void forEachRemaining(Consumer<? super Integer> action){
    final int cursor,bound;
    final UncheckedSubList parent;
    if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
      AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
      this.cursor=bound;
    }
  }
  @Override public void forEachRemaining(IntConsumer action){
    final int cursor,bound;
    final UncheckedSubList parent;
    if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
      AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
      this.cursor=bound;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=parent.getBound();
  }
  @Override public int nextInt(){
    return parent.root.arr[cursor++];
  }
  @Override public void remove(){
    final UncheckedSubList parent;
    final AbstractSeq.Unchecked root;
    AbstractSeq.eraseIndexHelper((root=(parent=this.parent).root).arr,--cursor,--root.size);
    AbstractSeq.Unchecked.AbstractSubList.bubbleUpDecrementSize(parent.parent);
    --parent.size;
  }
}