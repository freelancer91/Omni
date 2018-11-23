package omni.impl.seq.arr.ofchar;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.CharConsumer;
import omni.impl.AbstractCharItr;
import omni.impl.CheckedCollection;
class CheckedDescendingItr extends AbstractCharItr implements OmniIterator.OfChar{
  private transient final CheckedStack root;
  private transient int cursor;
  private transient int lastRet=-1;
  private transient int modCount;
  CheckedDescendingItr(CheckedStack root){
    this.root=root;
    cursor=root.size;
    modCount=root.modCount;
  }
  @Override public void forEachRemaining(CharConsumer action){
    final int cursor;
    if((cursor=this.cursor)!=0){
      uncheckedForEachRemaining(cursor,action);
    }
  }
  @Override public void forEachRemaining(Consumer<? super Character> action){
    final int cursor;
    if((cursor=this.cursor)!=0){
      uncheckedForEachRemaining(cursor,action::accept);
    }
  }
  @Override public boolean hasNext(){
    return cursor!=0;
  }
  @Override public char nextChar(){
    final CheckedStack root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    int cursor;
    if((cursor=this.cursor)!=0){
      lastRet=--cursor;
      this.cursor=cursor;
      return root.arr[cursor];
    }
    throw new NoSuchElementException();
  }
  @Override public void remove(){
    final int lastRet;
    if((lastRet=this.lastRet)!=-1){
      final CheckedStack root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      AbstractSeq.eraseIndexHelper(root.arr,lastRet,--root.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      this.lastRet=-1;
      return;
    }
    throw new IllegalStateException();
  }
  private void uncheckedForEachRemaining(int cursor,CharConsumer action){
    final var root=this.root;
    final int expectedModCount=modCount;
    try{
      AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action);
    }finally{
      CheckedCollection.checkModCount(expectedModCount,root.modCount);
    }
    this.cursor=0;
    lastRet=0;
  }
}