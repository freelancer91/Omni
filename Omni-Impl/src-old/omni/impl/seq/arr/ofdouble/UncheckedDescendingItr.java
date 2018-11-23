package omni.impl.seq.arr.ofdouble;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractDoubleItr;
class UncheckedDescendingItr extends AbstractDoubleItr implements OmniIterator.OfDouble{
  private transient final UncheckedStack root;
  private transient int cursor;
  UncheckedDescendingItr(UncheckedStack root){
    this.root=root;
    cursor=root.size;
  }
  @Override public void forEachRemaining(Consumer<? super Double> action){
    int cursor;
    if((cursor=this.cursor)!=0){
      AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
      this.cursor=0;
    }
  }
  @Override public void forEachRemaining(DoubleConsumer action){
    int cursor;
    if((cursor=this.cursor)!=0){
      AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action);
      this.cursor=0;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=0;
  }
  @Override public double nextDouble(){
    return root.arr[--cursor];
  }
  @Override public void remove(){
    final UncheckedStack root;
    AbstractSeq.eraseIndexHelper((root=this.root).arr,cursor,--root.size);
  }
}