package omni.impl.seq.dbllnk.offloat;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.FloatConsumer;
import omni.impl.AbstractFloatItr;
import omni.impl.seq.dbllnk.offloat.AbstractSeq.Unchecked;
class UncheckedAscendingItr extends AbstractFloatItr implements OmniIterator.OfFloat{
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
  @Override public void forEachRemaining(Consumer<? super Float> action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,root.tail,action::accept);
      this.cursor=null;
    }
  }
  @Override public void forEachRemaining(FloatConsumer action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      AbstractSeq.uncheckedForEachForward(cursor,root.tail,action);
      this.cursor=null;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=null;
  }
  @Override public float nextFloat(){
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
