package omni.impl.seq.arr.ofref;
import java.util.function.Consumer;
import omni.api.OmniListIterator;
class UncheckedBidirectionalItr<E>extends AbstractSeq.Unchecked.AbstractBidirectionalItr<E>
        implements
        OmniListIterator.OfRef<E>{
    UncheckedBidirectionalItr(UncheckedList<E> root){
        super(root);
    }
    UncheckedBidirectionalItr(UncheckedList<E> root,int cursor){
        super(root,cursor);
    }
    @Override
    public void forEachRemaining(Consumer<? super E> action){
        final int cursor,bound;
        final AbstractSeq.Unchecked<E> root;
        if((cursor=this.cursor)!=(bound=(root=this.root).size)){
            AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
            this.cursor=bound;
            lastRet=bound-1;
        }
    }
    @Override
    public boolean hasNext(){
        return cursor!=root.size;
    }
    @Override
    public boolean hasPrevious(){
        return cursor!=0;
    }
    @Override
    @SuppressWarnings("unchecked")
    public E next(){
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return (E)root.arr[lastRet];
    }
    @Override
    public int nextIndex(){
        return cursor;
    }
    @Override
    @SuppressWarnings("unchecked")
    public E previous(){
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return (E)root.arr[lastRet];
    }
    @Override
    public int previousIndex(){
        return cursor-1;
    }
    @Override
    public void remove(){
        final AbstractSeq.Unchecked<E> root;
        final int lastRet;
        AbstractSeq.eraseIndexHelper((root=this.root).arr,lastRet=this.lastRet,--root.size);
        cursor=lastRet;
    }
    @Override
    public void set(E val){
        root.arr[lastRet]=val;
    }
}