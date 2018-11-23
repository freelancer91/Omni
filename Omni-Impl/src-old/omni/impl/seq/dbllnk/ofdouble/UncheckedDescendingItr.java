package omni.impl.seq.dbllnk.ofdouble;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import omni.impl.seq.dbllnk.ofdouble.AbstractSeq.Unchecked;
class UncheckedDescendingItr extends UncheckedAscendingItr{
  UncheckedDescendingItr(Unchecked root){
    super(root,root.tail);
  }
  @Override public void forEachRemaining(Consumer<? super Double> action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachReverse(cursor,action::accept);
      this.cursor=null;
    }
  }
  @Override public void forEachRemaining(DoubleConsumer action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachReverse(cursor,action);
      this.cursor=null;
    }
  }
  @Override public double nextDouble(){
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