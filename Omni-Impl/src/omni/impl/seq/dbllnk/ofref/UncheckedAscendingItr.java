package omni.impl.seq.dbllnk.ofref;

import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.impl.seq.dbllnk.ofref.AbstractRefDblLnkSeq.Unchecked;

class UncheckedAscendingItr<E> implements OmniIterator.OfRef<E>{
  transient final Unchecked<E> root;
  transient Node<E> cursor;
  UncheckedAscendingItr(Unchecked<E> root){
    this.root=root;
    this.cursor=root.head;
  }
  UncheckedAscendingItr(Unchecked<E> root,Node<E> cursor){
    this.root=root;
    this.cursor=cursor;
  }
  @Override public void forEachRemaining(Consumer<? super E> action){
    Node<E> cursor;
    if((cursor=this.cursor)!=null){
      UncheckedRefDblLnkList.uncheckedForEachForward(cursor,root.tail,action);
      this.cursor=null;
    }
  }
  @Override public boolean hasNext(){
    return this.cursor!=null;
  }
  @Override public E next(){
    Node<E> lastRet;
    this.cursor=(lastRet=this.cursor).next;
    return lastRet.val;
  }
  @Override public void remove(){
    Unchecked<E> root;
    if(--(root=this.root).size!=0){
      Node<E> cursor;
      if((cursor=this.cursor)!=null){
        Node<E> lastRet;
        if((lastRet=cursor.prev)==root.head){
          UncheckedRefDblLnkList.staticSetHead(root,cursor);
        }else{
          UncheckedRefDblLnkList.joinNodes(lastRet.prev,cursor);
        }
      }else{
        UncheckedRefDblLnkList.staticEraseTail(root,root.tail);
      }
    }else{
      UncheckedRefDblLnkList.staticInit(root,null);
    }
  }
}