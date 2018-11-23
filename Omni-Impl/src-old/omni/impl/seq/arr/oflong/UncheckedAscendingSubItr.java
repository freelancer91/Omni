package omni.impl.seq.arr.oflong;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractLongItr;
class UncheckedAscendingSubItr extends AbstractLongItr implements OmniIterator.OfLong{
    private transient final UncheckedSubList parent;
    private transient int cursor;
    UncheckedAscendingSubItr(UncheckedSubList parent){
        this.parent=parent;
        cursor=parent.rootOffset;
    }
    @Override public void forEachRemaining(Consumer<? super Long> action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size)){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
            this.cursor=bound;
        }
    }
    @Override public void forEachRemaining(LongConsumer action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size)){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
        }
    }
    @Override public boolean hasNext(){
        final UncheckedSubList parent;
        return cursor!=(parent=this.parent).rootOffset+parent.size;
    }
    @Override public long nextLong(){
        return parent.root.arr[cursor++];
    }
    @Override public void remove(){
        final UncheckedSubList parent;
        final AbstractSeq.Unchecked root;
        AbstractSeq.eraseIndexHelper((root=(parent=this.parent).root).arr,--cursor,--root.size);
        AbstractSeq.Unchecked.AbstractSubList.bubbleUpDecrementSize(parent.parent);
        --parent.size;
    }
}