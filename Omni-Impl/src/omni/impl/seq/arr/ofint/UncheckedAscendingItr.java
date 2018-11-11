package omni.impl.seq.arr.ofint;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractIntItr;
class UncheckedAscendingItr extends AbstractIntItr implements OmniIterator.OfInt{
  private transient final UncheckedList root;
  private transient int cursor;
  UncheckedAscendingItr(UncheckedList root){
    this.root=root;
  }
  @Override public void forEachRemaining(Consumer<? super Integer> action){
    final UncheckedList root;
    final int cursor,bound;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
      this.cursor=bound;
    }
  }
  @Override public void forEachRemaining(IntConsumer action){
    final UncheckedList root;
    final int cursor,bound;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
      this.cursor=bound;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=root.size;
  }
  @Override public int nextInt(){
    return root.arr[cursor++];
  }
  @Override public void remove(){
    final UncheckedList root;
    AbstractSeq.eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
  }
}