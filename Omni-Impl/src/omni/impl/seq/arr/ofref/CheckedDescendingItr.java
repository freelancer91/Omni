package omni.impl.seq.arr.ofref;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.impl.CheckedCollection;
class CheckedDescendingItr<E> implements OmniIterator.OfRef<E>{
    private transient final CheckedStack<E> root;
    private transient int cursor;
    private transient int lastRet=-1;
    private transient int modCount;
    CheckedDescendingItr(CheckedStack<E> root){
        this.root=root;
        this.cursor=root.size;
        this.modCount=root.modCount;
    }
    @Override
    public void forEachRemaining(Consumer<? super E> action){
        final int cursor;
        if((cursor=this.cursor)!=0){
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
    @Override
    public boolean hasNext(){
        return cursor!=0;
    }
    @Override
    @SuppressWarnings("unchecked")
    public E next(){
        final CheckedStack<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int cursor;
        if((cursor=this.cursor)!=root.size){
            lastRet=cursor;
            this.cursor=cursor+1;
            return (E)root.arr[cursor];
        }
        throw new NoSuchElementException();
    }
    @Override
    public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
            final CheckedStack<E> root;
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
}