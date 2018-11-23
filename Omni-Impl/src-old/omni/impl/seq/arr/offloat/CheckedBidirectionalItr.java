package omni.impl.seq.arr.offloat;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import omni.api.OmniListIterator;
import omni.function.FloatConsumer;
import omni.impl.CheckedCollection;
class CheckedBidirectionalItr extends AbstractSeq.Checked.AbstractBidirectionalItr implements OmniListIterator.OfFloat{
  CheckedBidirectionalItr(CheckedList root){
    super(root);
  }
  CheckedBidirectionalItr(CheckedList root,int cursor){
    super(root,cursor);
  }
  @Override public void forEachRemaining(Consumer<? super Float> action){
    final AbstractSeq.Checked root;
    final int cursor,bound;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      final int expectedModCount=modCount;
      try{
        AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
      }finally{
        CheckedCollection.checkModCount(expectedModCount,root.modCount);
      }
      this.cursor=bound;
      lastRet=bound-1;
    }
  }
  @Override public void forEachRemaining(FloatConsumer action){
    final AbstractSeq.Checked root;
    final int cursor,bound;
    if((cursor=this.cursor)!=(bound=(root=this.root).size)){
      final int expectedModCount=modCount;
      try{
        AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
      }finally{
        CheckedCollection.checkModCount(expectedModCount,root.modCount);
      }
      this.cursor=bound;
      lastRet=bound-1;
    }
  }
  @Override public boolean hasNext(){
    return cursor!=root.size;
  }
  @Override public boolean hasPrevious(){
    return cursor!=0;
  }
  @Override public float nextFloat(){
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    final int cursor;
    if((cursor=this.cursor)!=root.size){
      lastRet=cursor;
      this.cursor=cursor+1;
      return root.arr[cursor];
    }
    throw new NoSuchElementException();
  }
  @Override public int nextIndex(){
    return cursor;
  }
  @Override public float previousFloat(){
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    int cursor;
    if((cursor=this.cursor)!=0){
      lastRet=--cursor;
      this.cursor=cursor;
      return root.arr[cursor];
    }
    throw new NoSuchElementException();
  }
  @Override public int previousIndex(){
    return cursor-1;
  }
  @Override public void remove(){
    final int lastRet;
    if((lastRet=this.lastRet)!=-1){
      final AbstractSeq.Checked root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      AbstractSeq.eraseIndexHelper(root.arr,lastRet,--root.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      cursor=lastRet;
      this.lastRet=-1;
      return;
    }
    throw new IllegalStateException();
  }
  @Override public void set(float val){
    final int lastRet;
    if((lastRet=this.lastRet)!=-1){
      final AbstractSeq.Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.arr[lastRet]=val;
      return;
    }
    throw new IllegalStateException();
  }
}