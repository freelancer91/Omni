package omni.impl.seq.dbllnk.ofref;

import java.util.function.Consumer;
import omni.api.OmniListIterator;
import omni.impl.seq.dbllnk.ofref.AbstractRefDblLnkSeq.Unchecked;

class UncheckedBidirectionalItr<E>extends UncheckedAscendingItr<E> implements OmniListIterator.OfRef<E>{
  private transient Node<E> lastRet;
  private transient int nextIndex;
  UncheckedBidirectionalItr(Unchecked<E> root){
    super(root);
  }
  UncheckedBidirectionalItr(Unchecked<E> root,Node<E> cursor,int nextIndex){
    super(root,cursor);
    this.nextIndex=nextIndex;
  }
  @Override public void add(E val){
    Unchecked<E> root;
    if(++(root=this.root).size!=1){
      Node<E> cursor;
      if((cursor=this.cursor)==null){
        root.tail=new Node<>(root.tail,val);
      }else if(cursor==root.head){
        root.head=new Node<>(val,cursor);
      }else{
        new Node<>(cursor.prev,val,cursor);
      }
    }else{
      UncheckedRefDblLnkList.staticInit(root,new Node<>(val));
    }
    ++nextIndex;
    this.lastRet=null;
  }
  @Override public void forEachRemaining(Consumer<? super E> action){
    Node<E> cursor;
    if((cursor=this.cursor)!=null){
      Node<E> bound;
      Unchecked<E> root;
      UncheckedRefDblLnkList.uncheckedForEachForward(cursor,bound=(root=this.root).tail,action);
      this.cursor=null;
      this.lastRet=bound;
      this.nextIndex=root.size;
    }
  }
  @Override public boolean hasPrevious(){
    return this.nextIndex!=0;
  }
  @Override public E next(){
    Node<E> lastRet;
    this.lastRet=lastRet=cursor;
    cursor=lastRet.next;
    ++nextIndex;
    return lastRet.val;
  }
  @Override public int nextIndex(){
    return this.nextIndex;
  }
  @Override public E previous(){
    Node<E> lastRet;
    this.lastRet=lastRet=cursor.prev;
    cursor=lastRet;
    --nextIndex;
    return lastRet.val;
  }
  @Override public int previousIndex(){
    return this.nextIndex-1;
  }
  @Override public void remove(){
    final var lastRet=this.lastRet;
    Unchecked<E> root;
    if(--(root=this.root).size!=0){
      Node<E> cursor;
      if(lastRet!=(cursor=this.cursor)){
        --nextIndex;
        if(lastRet==root.head){
          UncheckedRefDblLnkList.staticSetHead(root,cursor);
        }else if(lastRet==root.tail){
          UncheckedRefDblLnkList.staticEraseTail(root,lastRet);
        }else{
          UncheckedRefDblLnkList.joinNodes(lastRet.prev,cursor);
        }
      }else{
        if(lastRet==root.head){
          UncheckedRefDblLnkList.staticEraseHead(root,lastRet);
        }else if(lastRet==root.tail){
          UncheckedRefDblLnkList.staticEraseTail(root,lastRet);
        }else{
          UncheckedRefDblLnkList.joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }else{
      UncheckedRefDblLnkList.staticInit(root,null);
      if(lastRet!=cursor){
        --nextIndex;
      }
    }
    this.lastRet=null;
  }
  @Override public void set(E val){
    lastRet.val=val;
  }
}