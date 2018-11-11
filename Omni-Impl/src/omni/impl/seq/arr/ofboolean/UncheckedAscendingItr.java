package omni.impl.seq.arr.ofboolean;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.BooleanConsumer;
import omni.impl.AbstractBooleanItr;
class UncheckedAscendingItr extends AbstractBooleanItr implements OmniIterator.OfBoolean{
  private transient final UncheckedList root;
  private transient int cursor;
  UncheckedAscendingItr(UncheckedList root){
    this.root=root;
  }
  @Override public void forEachRemaining(BooleanConsumer action){
    final UncheckedList root;
    final int cursor,bound;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
      this.cursor=bound;
    }
  }
  @Override public void forEachRemaining(Consumer<? super Boolean> action){
    final UncheckedList root;
    final int cursor,bound;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
      this.cursor=bound;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=root.size;
  }
  @Override public boolean nextBoolean(){
    return root.arr[cursor++];
  }
  @Override public void remove(){
    final UncheckedList root;
    AbstractSeq.eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
  }
}