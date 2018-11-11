package omni.impl.seq.arr.offloat;
import java.util.function.Consumer;
import omni.api.OmniListIterator;
import omni.function.FloatConsumer;
class UncheckedBidirectionalSubItr extends AbstractSeq.Unchecked.AbstractSubList.AbstractBidirectionalItr
    implements OmniListIterator.OfFloat{
  UncheckedBidirectionalSubItr(UncheckedSubList parent){
    super(parent);
  }
  UncheckedBidirectionalSubItr(UncheckedSubList parent,int cursor){
    super(parent,cursor);
  }
  @Override public void forEachRemaining(Consumer<? super Float> action){
    final int cursor,bound;
    final AbstractSeq.Unchecked.AbstractSubList parent;
    if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
      AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
      this.cursor=bound;
      lastRet=bound-1;
    }
  }
  @Override public void forEachRemaining(FloatConsumer action){
    final int cursor,bound;
    final AbstractSeq.Unchecked.AbstractSubList parent;
    if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
      AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
      this.cursor=bound;
      lastRet=bound-1;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=parent.getBound();
  }
  @Override public boolean hasPrevious(){
    return cursor!=parent.rootOffset;
  }
  @Override public float nextFloat(){
    final int lastRet;
    this.lastRet=lastRet=cursor++;
    return parent.root.arr[lastRet];
  }
  @Override public int nextIndex(){
    return cursor-parent.rootOffset;
  }
  @Override public float previousFloat(){
    final int lastRet;
    this.lastRet=lastRet=--cursor;
    return parent.root.arr[lastRet];
  }
  @Override public int previousIndex(){
    return cursor-parent.rootOffset-1;
  }
  @Override public void remove(){
    final AbstractSeq.Unchecked.AbstractSubList parent;
    final AbstractSeq.Unchecked root;
    final int lastRet;
    AbstractSeq.eraseIndexHelper((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
    AbstractSeq.Unchecked.AbstractSubList.bubbleUpDecrementSize(parent.parent);
    --parent.size;
    cursor=lastRet;
  }
  @Override public void set(float val){
    parent.root.arr[lastRet]=val;
  }
}