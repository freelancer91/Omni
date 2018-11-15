package omni.impl.seq.dbllnk.oflong;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import omni.api.OmniListIterator;
import omni.impl.seq.dbllnk.oflong.AbstractSeq.Unchecked;
class UncheckedBidirectionalItr extends UncheckedAscendingItr implements OmniListIterator.OfLong{
  private transient Node lastRet;
  private transient int nextIndex;
  UncheckedBidirectionalItr(Unchecked root){
    super(root);
  }
  UncheckedBidirectionalItr(Unchecked root,Node cursor,int nextIndex){
    super(root,cursor);
    this.nextIndex=nextIndex;
  }
  @Override public void add(long val){
    Unchecked root;
    if(++(root=this.root).size!=1){
      Node cursor;
      if((cursor=this.cursor)==null){
        root.tail=new Node(root.tail,val);
      }else if(cursor==root.head){
        root.head=new Node(val,cursor);
      }else{
        new Node(cursor.prev,val,cursor);
      }
    }else{
      AbstractSeq.staticInit(root,new Node(val));
    }
    ++nextIndex;
    lastRet=null;
  }
  @Override public void forEachRemaining(Consumer<? super Long> action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      Node bound;
      Unchecked root;
      AbstractSeq.uncheckedForEachForward(cursor,bound=(root=this.root).tail,action::accept);
      this.cursor=null;
      lastRet=bound;
      nextIndex=root.size;
    }
  }
  @Override public void forEachRemaining(LongConsumer action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      Node bound;
      Unchecked root;
      AbstractSeq.uncheckedForEachForward(cursor,bound=(root=this.root).tail,action);
      this.cursor=null;
      lastRet=bound;
      nextIndex=root.size;
    }
  }
  @Override public boolean hasPrevious(){
    return nextIndex!=0;
  }
  @Override public long nextLong(){
    Node lastRet;
    this.lastRet=lastRet=cursor;
    cursor=lastRet.next;
    ++nextIndex;
    return lastRet.val;
  }
  @Override public int nextIndex(){
    return nextIndex;
  }
  @Override public long previousLong(){
    Node lastRet;
    this.lastRet=lastRet=cursor.prev;
    cursor=lastRet;
    --nextIndex;
    return lastRet.val;
  }
  @Override public int previousIndex(){
    return nextIndex-1;
  }
  @Override public void remove(){
    final var lastRet=this.lastRet;
    Unchecked root;
    if(--(root=this.root).size!=0){
      Node cursor;
      if(lastRet!=(cursor=this.cursor)){
        --nextIndex;
        if(lastRet==root.head){
          AbstractSeq.staticSetHead(root,cursor);
        }else if(lastRet==root.tail){
          AbstractSeq.staticEraseTail(root,lastRet);
        }else{
          AbstractSeq.joinNodes(lastRet.prev,cursor);
        }
      }else{
        if(lastRet==root.head){
          AbstractSeq.staticEraseHead(root,lastRet);
        }else if(lastRet==root.tail){
          AbstractSeq.staticEraseTail(root,lastRet);
        }else{
          AbstractSeq.joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }else{
      AbstractSeq.staticInit(root,null);
      if(lastRet!=cursor){
        --nextIndex;
      }
    }
    this.lastRet=null;
  }
  @Override public void set(long val){
    lastRet.val=val;
  }
}
