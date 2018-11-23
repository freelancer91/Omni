package omni.impl.seq.dbllnk.ofbyte;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.ByteConsumer;
import omni.impl.AbstractByteItr;
class UncheckedAscendingSubItr extends AbstractByteItr implements OmniIterator.OfByte{
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
  @Override public void forEachRemaining(Consumer<? super Byte> action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,parent.tail,action::accept);
      this.cursor=null;
    }
  }
  @Override public void forEachRemaining(ByteConsumer action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,parent.tail,action);
      this.cursor=null;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=null;
  }
  @Override public byte nextByte(){
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