package omni.impl.seq.arr.ofboolean;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.BooleanConsumer;
import omni.impl.AbstractBooleanItr;
class UncheckedAscendingSubItr extends AbstractBooleanItr implements OmniIterator.OfBoolean{
    private transient final UncheckedSubList parent;
    private transient int cursor;
    UncheckedAscendingSubItr(UncheckedSubList parent){
        this.parent=parent;
        cursor=parent.rootOffset;
    }
    @Override
    public void forEachRemaining(BooleanConsumer action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
        }
    }
    @Override
    public void forEachRemaining(Consumer<? super Boolean> action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
            this.cursor=bound;
        }
    }
    @Override
    public boolean hasNext(){
        return cursor!=parent.getBound();
    }
    @Override
    public boolean nextBoolean(){
        return parent.root.arr[cursor++];
    }
    @Override
    public void remove(){
        final UncheckedSubList parent;
        final AbstractSeq.Unchecked root;
        AbstractSeq.eraseIndexHelper((root=(parent=this.parent).root).arr,--cursor,--root.size);
        AbstractSeq.Unchecked.AbstractSubList.bubbleUpDecrementSize(parent.parent);
        --parent.size;
    }
}