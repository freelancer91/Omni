package omni.impl.seq.dbllnk.ofint;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractIntItr;
import omni.impl.seq.dbllnk.ofint.AbstractSeq.Unchecked;
class UncheckedAscendingItr extends AbstractIntItr implements OmniIterator.OfInt{
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
  @Override public void forEachRemaining(Consumer<? super Integer> action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,root.tail,action::accept);
      this.cursor=null;
    }
  }
  @Override public void forEachRemaining(IntConsumer action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,root.tail,action);
      this.cursor=null;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=null;
  }
  @Override public int nextInt(){
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
