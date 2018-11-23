package omni.impl.seq.arr.ofref;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
class CheckedBidirectionalSubItr<E>extends AbstractSeq.Checked.AbstractSubList.AbstractBidirectionalItr<E>
implements OmniListIterator.OfRef<E>{
    CheckedBidirectionalSubItr(CheckedSubList<E> parent,int modCount){
        super(parent,modCount);
    }
    CheckedBidirectionalSubItr(CheckedSubList<E> parent,int modCount,int cursor){
        super(parent,modCount,cursor);
    }
    @Override public void forEachRemaining(Consumer<? super E> action){
        final int cursor,bound;
        final AbstractSeq.Checked.AbstractSubList<E> parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size)){
            final var root=parent.root;
            final int expectedModCount=modCount;
            try{
                AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
            }finally{
                CheckedCollection.checkModCount(expectedModCount,root.modCount);
            }
            this.cursor=bound;
            lastRet=bound-1;
        }
    }
    @Override public boolean hasNext(){
        final AbstractSeq.Checked.AbstractSubList<E> parent;
        return cursor!=(parent=this.parent).rootOffset+parent.size;
    }
    @Override public boolean hasPrevious(){
        return cursor!=parent.rootOffset;
    }
    @Override @SuppressWarnings("unchecked") public E next(){
        final AbstractSeq.Checked<E> root;
        final AbstractSeq.Checked.AbstractSubList<E> parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)!=parent.rootOffset+parent.size){
            lastRet=cursor;
            this.cursor=cursor+1;
            return (E)root.arr[cursor];
        }
        throw new NoSuchElementException();
    }
    @Override public int nextIndex(){
        return cursor-parent.rootOffset;
    }
    @Override @SuppressWarnings("unchecked") public E previous(){
        final AbstractSeq.Checked<E> root;
        final AbstractSeq.Checked.AbstractSubList<E> parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        int cursor;
        if((cursor=this.cursor)!=parent.rootOffset){
            lastRet=--cursor;
            this.cursor=cursor;
            return (E)root.arr[cursor];
        }
        throw new NoSuchElementException();
    }
    @Override public int previousIndex(){
        return cursor-parent.rootOffset-1;
    }
    @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
            final AbstractSeq.Checked<E> root;
            final AbstractSeq.Checked.AbstractSubList<E> parent;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
            AbstractSeq.eraseIndexHelper(root.arr,lastRet,--root.size);
            CheckedSubList.bubbleUpDecrementSize(parent.parent);
            --parent.size;
            root.modCount=++modCount;
            parent.modCount=modCount;
            this.modCount=modCount;
            cursor=lastRet;
            this.lastRet=-1;
            return;
        }
        throw new IllegalStateException();
    }
    @Override public void set(E val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
            final AbstractSeq.Checked<E> root;
            CheckedCollection.checkModCount(modCount,(root=parent.root).modCount);
            root.arr[lastRet]=val;
            return;
        }
        throw new IllegalStateException();
    }
}