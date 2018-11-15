package omni.impl.seq.dbllnk.ofboolean;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.BooleanConsumer;
import omni.impl.AbstractBooleanItr;
import omni.impl.seq.dbllnk.ofboolean.AbstractSeq.Unchecked;
class UncheckedAscendingItr extends AbstractBooleanItr implements OmniIterator.OfBoolean{
  transient final Unchecked root;
  transient Node cursor;
  UncheckedAscendingItr(Unchecked root){
    this.root=root;
    cursor=root.head;
  }
  UncheckedAscendingItr(Unchecked root,Node cursor){
    this.root=root;
    this.cursor=cursor;
  }
  @Override public void forEachRemaining(Consumer<? super Boolean> action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,root.tail,action::accept);
      this.cursor=null;
    }
  }
  @Override public void forEachRemaining(BooleanConsumer action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,root.tail,action);
      this.cursor=null;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=null;
  }
  @Override public boolean nextBoolean(){
    Node lastRet;
    cursor=(lastRet=cursor).next;
    return lastRet.val;
  }
  @Override public void remove(){
    Unchecked root;
    if(--(root=this.root).size!=0){
      Node cursor;
      if((cursor=this.cursor)!=null){
        Node lastRet;
        if((lastRet=cursor.prev)==root.head){
          AbstractSeq.staticSetHead(root,cursor);
        }else{
          AbstractSeq.joinNodes(lastRet.prev,cursor);
        }
      }else{
        AbstractSeq.staticEraseTail(root,root.tail);
      }
    }else{
      AbstractSeq.staticInit(root,null);
    }
  }
}
