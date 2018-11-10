package omni.impl.seq.dbllnk.ofref;

import java.util.function.Consumer;
import omni.impl.seq.dbllnk.ofref.AbstractRefDblLnkSeq.Unchecked;

class UncheckedDescendingItr<E>extends UncheckedAscendingItr<E>{
  UncheckedDescendingItr(Unchecked<E> root){
    super(root,root.tail);
  }
  @Override public void forEachRemaining(Consumer<? super E> action){
    Node<E> cursor;
    if((cursor=this.cursor)!=null){
      UncheckedRefDblLnkList.uncheckedForEachReverse(cursor,action);
      this.cursor=null;
    }
  }
  @Override public E next(){
    Node<E> lastRet;
    cursor=(lastRet=cursor).prev;
    return lastRet.val;
  }
  @Override public void remove(){
    Unchecked<E> root;
    if(--(root=this.root).size!=0){
      Node<E> cursor;
      if((cursor=this.cursor)!=null){
        Node<E> lastRet;
        if((lastRet=cursor.next)==root.tail){
          UncheckedRefDblLnkList.staticSetTail(root,cursor);
        }else{
          UncheckedRefDblLnkList.joinNodes(cursor,lastRet.next);
        }
      }else{
        UncheckedRefDblLnkList.staticEraseHead(root,root.head);
      }
    }else{
      UncheckedRefDblLnkList.staticInit(root,null);
    }
  }
}