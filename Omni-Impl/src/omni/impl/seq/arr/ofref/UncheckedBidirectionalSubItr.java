package omni.impl.seq.arr.ofref;
import java.util.function.Consumer;
import omni.api.OmniListIterator;
class UncheckedBidirectionalSubItr<E>extends AbstractSeq.Unchecked.AbstractSubList.AbstractBidirectionalItr<E>
        implements
        OmniListIterator.OfRef<E>{
    UncheckedBidirectionalSubItr(UncheckedSubList<E> parent){
        super(parent);
    }
    UncheckedBidirectionalSubItr(UncheckedSubList<E> parent,int cursor){
        super(parent,cursor);
    }
    @Override
    public void forEachRemaining(Consumer<? super E> action){
        final int cursor,bound;
        final AbstractSeq.Unchecked.AbstractSubList<E> parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
            lastRet=bound-1;
        }
    }
    @Override
    public boolean hasNext(){
        return cursor!=parent.getBound();
    }
    @Override
    public boolean hasPrevious(){
        return cursor!=parent.rootOffset;
    }
    @Override
    @SuppressWarnings("unchecked")
    public E next(){
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return (E)parent.root.arr[lastRet];
    }
    @Override
    public int nextIndex(){
        return cursor-parent.rootOffset;
    }
    @Override
    @SuppressWarnings("unchecked")
    public E previous(){
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return (E)parent.root.arr[lastRet];
    }
    @Override
    public int previousIndex(){
        return cursor-parent.rootOffset-1;
    }
    @Override
    public void remove(){
        final AbstractSeq.Unchecked.AbstractSubList<E> parent;
        final AbstractSeq.Unchecked<E> root;
        final int lastRet;
        AbstractSeq.eraseIndexHelper((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        UncheckedSubList.bubbleUpDecrementSize(parent.parent);
        --parent.size;
        cursor=lastRet;
    }
    @Override
    public void set(E val){
        parent.root.arr[lastRet]=val;
    }
}