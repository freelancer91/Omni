package omni.impl.seq.dbllnk.ofref;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.impl.CheckedCollection;

class CheckedAscendingSubItr<E> implements OmniIterator.OfRef<E>{
  transient final CheckedSubList<E> parent;
  transient Node<E> cursor;
  transient Node<E> lastRet;
  transient int modCount;
  CheckedAscendingSubItr(CheckedSubList<E> parent){
    this.parent=parent;
    this.cursor=parent.head;
    this.modCount=parent.modCount;
  }
  CheckedAscendingSubItr(CheckedSubList<E> parent,Node<E> cursor){
    this.parent=parent;
    this.cursor=cursor;
    this.modCount=parent.modCount;
  }
  @Override public void forEachRemaining(Consumer<? super E> action){
    Node<E> cursor;
    if((cursor=this.cursor)!=null){
      final int modCount=this.modCount;
      final var parent=this.parent;
      try{
        CheckedSubList.uncheckedForEachForward(cursor,cursor=parent.tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,parent.root.modCount);
      }
      this.cursor=null;
      this.lastRet=cursor;
    }
  }
  @Override public boolean hasNext(){
    return this.cursor!=null;
  }
  @Override public E next(){
    CheckedSubList<E> parent;
    CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
    Node<E> cursor;
    if((cursor=this.cursor)!=null){
      if(cursor==parent.tail){
        this.cursor=null;
      }else{
        this.cursor=cursor.next;
      }
      this.lastRet=cursor;
      return cursor.val;
    }
    throw new NoSuchElementException();
  }
  @Override public void remove(){
    parent.ascItrRemove(this);
    this.lastRet=null;
  }
}