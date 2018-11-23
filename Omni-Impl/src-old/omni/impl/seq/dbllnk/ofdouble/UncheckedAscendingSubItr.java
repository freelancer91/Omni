package omni.impl.seq.dbllnk.ofdouble;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractDoubleItr;
class UncheckedAscendingSubItr extends AbstractDoubleItr implements OmniIterator.OfDouble{
  transient final UncheckedSubList parent;
  transient Node cursor;
  UncheckedAscendingSubItr(UncheckedSubList parent){
    this.parent=parent;
    cursor=parent.head;
  }
  UncheckedAscendingSubItr(UncheckedSubList parent,Node cursor){
    this.parent=parent;
    this.cursor=cursor;
  }
  @Override public void forEachRemaining(Consumer<? super Double> action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,parent.tail,action::accept);
      this.cursor=null;
    }
  }
  @Override public void forEachRemaining(DoubleConsumer action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,parent.tail,action);
      this.cursor=null;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=null;
  }
  @Override public double nextDouble(){
    Node cursor;
    if((cursor=this.cursor)==parent.tail){
      this.cursor=null;
    }else{
      this.cursor=cursor.next;
    }
    return cursor.val;
  }
  @Override public void remove(){
    parent.ascItrRemove(cursor);
  }
}