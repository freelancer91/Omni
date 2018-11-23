package omni.impl.seq.dbllnk.ofref;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import omni.impl.seq.dbllnk.ofref.AbstractSeq.Checked;
class CheckedBidirectionalItr<E>extends CheckedAscendingItr<E> implements OmniListIterator.OfRef<E>{
  private transient int nextIndex;
  CheckedBidirectionalItr(Checked<E> root){
    super(root);
  }
  CheckedBidirectionalItr(Checked<E> root,Node<E> cursor,int index){
    super(root,cursor);
    this.nextIndex=index;
  }
  @Override public void add(E val){
    int modCount;
    Checked<E> root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    this.modCount=modCount;
    if(++root.size!=1){
      Node<E> cursor;
      if((cursor=this.cursor)==null){
        root.tail=new Node<>(root.tail,val);
      }else if(cursor==root.head){
        root.head=new Node<>(val,cursor);
      }else{
        new Node<>(cursor.prev,val,cursor);
      }
    }else{
      CheckedList.staticInit(root,new Node<>(val));
    }
    ++nextIndex;
    lastRet=null;
  }
  @Override public void forEachRemaining(Consumer<? super E> action){
    Node<E> cursor;
    if((cursor=this.cursor)!=null){
      final int modCount=this.modCount;
      final var root=this.root;
      try{
        CheckedList.uncheckedForEachForward(cursor,cursor=root.tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      this.nextIndex=root.size;
      this.cursor=null;
      lastRet=cursor;
    }
  }
  @Override public boolean hasPrevious(){
    return this.nextIndex!=0;
  }
  @Override public E next(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    Node<E> cursor;
    if((cursor=this.cursor)!=null){
      ++nextIndex;
      this.cursor=cursor.next;
      lastRet=cursor;
      return cursor.val;
    }
    throw new NoSuchElementException();
  }
  @Override public int nextIndex(){
    return this.nextIndex;
  }
  @Override public E previous(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    int nextIndex;
    if((nextIndex=this.nextIndex)!=0){
      this.nextIndex=nextIndex-1;
      Node<E> cursor;
      this.cursor=cursor=this.cursor.prev;
      lastRet=cursor;
      return cursor.val;
    }
    throw new NoSuchElementException();
  }
  @Override public int previousIndex(){
    return this.nextIndex-1;
  }
  @Override public void set(E val){
    Node<E> lastRet;
    if((lastRet=this.lastRet)!=null){
      CheckedCollection.checkModCount(modCount,root.modCount);
      lastRet.val=val;
      return;
    }
    throw new IllegalStateException();
  }
  @Override void uncheckedRemove(Node<E> lastRet,Checked<E> root){
    if(--root.size!=0){
      Node<E> cursor;
      if(lastRet!=(cursor=this.cursor)){
        --nextIndex;
        if(lastRet==root.head){
          CheckedList.staticSetHead(root,cursor);
        }else if(lastRet==root.tail){
          CheckedList.staticEraseTail(root,lastRet);
        }else{
          CheckedList.joinNodes(lastRet.prev,cursor);
        }
      }else{
        if(lastRet==root.head){
          CheckedList.staticEraseHead(root,lastRet);
        }else if(lastRet==root.tail){
          CheckedList.staticEraseTail(root,lastRet);
        }else{
          CheckedList.joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }else{
      CheckedList.staticInit(root,null);
      if(lastRet!=cursor){
        --nextIndex;
      }
    }
  }
}