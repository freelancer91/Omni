package omni.impl.seq.dbllnk.oflong;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractLongItr;
import omni.impl.CheckedCollection;
class CheckedAscendingSubItr extends AbstractLongItr implements OmniIterator.OfLong{
  transient final CheckedSubList parent;
  transient Node cursor;
  transient Node lastRet;
  transient int modCount;
  CheckedAscendingSubItr(CheckedSubList parent){
    this.parent=parent;
    cursor=parent.head;
    modCount=parent.modCount;
  }
  CheckedAscendingSubItr(CheckedSubList parent,Node cursor){
    this.parent=parent;
    this.cursor=cursor;
    modCount=parent.modCount;
  }
  @Override public void forEachRemaining(Consumer<? super Long> action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      final int modCount=this.modCount;
      final var parent=this.parent;
      try{
        CheckedSubList.uncheckedForEachForward(cursor,cursor=parent.tail,action::accept);
      }finally{
        CheckedCollection.checkModCount(modCount,parent.root.modCount);
      }
      this.cursor=null;
      lastRet=cursor;
    }
  }
  @Override public void forEachRemaining(LongConsumer action){
    Node cursor;
    if((cursor=this.cursor)!=null){
      final int modCount=this.modCount;
      final var parent=this.parent;
      try{
        CheckedSubList.uncheckedForEachForward(cursor,cursor=parent.tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,parent.root.modCount);
      }
      this.cursor=null;
      lastRet=cursor;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=null;
  }
  @Override public long nextLong(){
    CheckedSubList parent;
    CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
    Node cursor;
    if((cursor=this.cursor)!=null){
      if(cursor==parent.tail){
        this.cursor=null;
      }else{
        this.cursor=cursor.next;
      }
      lastRet=cursor;
      return cursor.val;
    }
    throw new NoSuchElementException();
  }
  @Override public void remove(){
    parent.ascItrRemove(this);
    lastRet=null;
  }
}
