package omni.impl.seq.arr.offloat;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.FloatConsumer;
import omni.impl.AbstractFloatItr;
class UncheckedAscendingItr extends AbstractFloatItr implements OmniIterator.OfFloat{
  private transient final UncheckedList root;
  private transient int cursor;
  UncheckedAscendingItr(UncheckedList root){
    this.root=root;
  }
  @Override public void forEachRemaining(Consumer<? super Float> action){
    final UncheckedList root;
    final int cursor,bound;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      UncheckedList.uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
      this.cursor=bound;
    }
  }
  @Override public void forEachRemaining(FloatConsumer action){
    final UncheckedList root;
    final int cursor,bound;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      UncheckedList.uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
      this.cursor=bound;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=root.size;
  }
  @Override public float nextFloat(){
    return root.arr[cursor++];
  }
  @Override public void remove(){
    final UncheckedList root;
    UncheckedList.eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
  }
}