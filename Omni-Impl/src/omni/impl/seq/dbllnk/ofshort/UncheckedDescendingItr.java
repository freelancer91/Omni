package omni.impl.seq.dbllnk.ofshort;
import java.util.function.Consumer;
import omni.function.ShortConsumer;
import omni.impl.seq.dbllnk.ofshort.AbstractSeq.Unchecked;
class UncheckedDescendingItr extends UncheckedAscendingItr{
  UncheckedDescendingItr(Unchecked root){
    super(root,root.tail);
  }
  @Override public void forEachRemaining(Consumer<? super Short> action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachReverse(cursor,action::accept);
      this.cursor=null;
    }
  }
  @Override public void forEachRemaining(ShortConsumer action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachReverse(cursor,action);
      this.cursor=null;
    }
  }
  @Override public short nextShort(){
    Node lastRet;
    cursor=(lastRet=cursor).prev;
    return lastRet.val;
  }
  @Override public void remove(){
    Unchecked root;
    if(--(root=this.root).size!=0){
      Node cursor;
      if((cursor=this.cursor)!=null){
        Node lastRet;
        if((lastRet=cursor.next)==root.tail){
          AbstractSeq.staticSetTail(root,cursor);
        }else{
          AbstractSeq.joinNodes(cursor,lastRet.next);
        }
      }else{
        AbstractSeq.staticEraseHead(root,root.head);
      }
    }else{
      AbstractSeq.staticInit(root,null);
    }
  }
}