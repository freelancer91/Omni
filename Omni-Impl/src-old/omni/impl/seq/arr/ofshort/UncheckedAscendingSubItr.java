package omni.impl.seq.arr.ofshort;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.ShortConsumer;
import omni.impl.AbstractShortItr;
class UncheckedAscendingSubItr extends AbstractShortItr implements OmniIterator.OfShort{
    private transient final UncheckedSubList parent;
    private transient int cursor;
    UncheckedAscendingSubItr(UncheckedSubList parent){
        this.parent=parent;
        cursor=parent.rootOffset;
    }
    @Override public void forEachRemaining(Consumer<? super Short> action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size)){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
            this.cursor=bound;
        }
    }
    @Override public void forEachRemaining(ShortConsumer action){
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
    @Override public short nextShort(){
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