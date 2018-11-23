package omni.impl.seq.dbllnk.ofref;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.impl.seq.dbllnk.ofref.AbstractSeq.Unchecked;
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
      UncheckedList.uncheckedForEachForward(cursor,root.tail,action);
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
          UncheckedList.staticSetHead(root,cursor);
        }else{
          UncheckedList.joinNodes(lastRet.prev,cursor);
        }
      }else{
        UncheckedList.staticEraseTail(root,root.tail);
      }
    }else{
      UncheckedList.staticInit(root,null);
    }
  }
}