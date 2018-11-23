package omni.impl.seq.arr.offloat;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.FloatConsumer;
import omni.impl.AbstractFloatItr;
class UncheckedDescendingItr extends AbstractFloatItr implements OmniIterator.OfFloat{
  private transient final UncheckedStack root;
  private transient int cursor;
  UncheckedDescendingItr(UncheckedStack root){
    this.root=root;
    cursor=root.size;
  }
  @Override public void forEachRemaining(Consumer<? super Float> action){
    int cursor;
    if((cursor=this.cursor)!=0){
      AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
      this.cursor=0;
    }
  }
  @Override public void forEachRemaining(FloatConsumer action){
    int cursor;
    if((cursor=this.cursor)!=0){
      AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action);
      this.cursor=0;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=0;
  }
  @Override public float nextFloat(){
    return root.arr[--cursor];
  }
  @Override public void remove(){
    final UncheckedStack root;
    AbstractSeq.eraseIndexHelper((root=this.root).arr,cursor,--root.size);
  }
}