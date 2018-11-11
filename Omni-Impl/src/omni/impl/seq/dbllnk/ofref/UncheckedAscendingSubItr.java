package omni.impl.seq.dbllnk.ofref;
import java.util.function.Consumer;
import omni.api.OmniIterator;
class UncheckedAscendingSubItr<E> implements OmniIterator.OfRef<E>{
  transient final UncheckedSubList<E> parent;
  transient Node<E> cursor;
  UncheckedAscendingSubItr(UncheckedSubList<E> parent){
    this.parent=parent;
    this.cursor=parent.head;
  }
  UncheckedAscendingSubItr(UncheckedSubList<E> parent,Node<E> cursor){
    this.parent=parent;
    this.cursor=cursor;
  }
  @Override public void forEachRemaining(Consumer<? super E> action){
    Node<E> cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,parent.tail,action);
      this.cursor=null;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=null;
  }
  @Override public E next(){
    Node<E> cursor;
    if((cursor=this.cursor)==parent.tail){
      this.cursor=null;
    }else{
      this.cursor=cursor.next;
    }
    return cursor.val;
  }
  @Override public void remove(){
    parent.ascItrRemove(this.cursor);
  }
}