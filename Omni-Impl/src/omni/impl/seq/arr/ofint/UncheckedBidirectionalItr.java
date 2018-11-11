package omni.impl.seq.arr.ofint;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import omni.api.OmniListIterator;
class UncheckedBidirectionalItr extends AbstractSeq.Unchecked.AbstractBidirectionalItr
    implements OmniListIterator.OfInt{
  UncheckedBidirectionalItr(UncheckedList root){
    super(root);
  }
  UncheckedBidirectionalItr(UncheckedList root,int cursor){
    super(root,cursor);
  }
  @Override public void forEachRemaining(Consumer<? super Integer> action){
    final int cursor,bound;
    final AbstractSeq.Unchecked root;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
      this.cursor=bound;
      lastRet=bound-1;
    }
  }
  @Override public void forEachRemaining(IntConsumer action){
    final int cursor,bound;
    final AbstractSeq.Unchecked root;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
      this.cursor=bound;
      lastRet=bound-1;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=root.size;
  }
  @Override public boolean hasPrevious(){
    return cursor!=0;
  }
  @Override public int nextIndex(){
    return cursor;
  }
  @Override public int nextInt(){
    final int lastRet;
    this.lastRet=lastRet=cursor++;
    return root.arr[lastRet];
  }
  @Override public int previousIndex(){
    return cursor-1;
  }
  @Override public int previousInt(){
    final int lastRet;
    this.lastRet=lastRet=--cursor;
    return root.arr[lastRet];
  }
  @Override public void remove(){
    final AbstractSeq.Unchecked root;
    final int lastRet;
    AbstractSeq.eraseIndexHelper((root=this.root).arr,lastRet=this.lastRet,--root.size);
    cursor=lastRet;
  }
  @Override public void set(int val){
    root.arr[lastRet]=val;
  }
}