package omni.impl.seq.arr.ofdouble;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractDoubleItr;
class UncheckedAscendingSubItr extends AbstractDoubleItr implements OmniIterator.OfDouble{
    private transient final UncheckedSubList parent;
    private transient int cursor;
    UncheckedAscendingSubItr(UncheckedSubList parent){
        this.parent=parent;
        cursor=parent.rootOffset;
    }
    @Override
    public void forEachRemaining(Consumer<? super Double> action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
            this.cursor=bound;
        }
    }
    @Override
    public void forEachRemaining(DoubleConsumer action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
        }
    }
    @Override
    public boolean hasNext(){
        return cursor!=parent.getBound();
    }
    @Override
    public double nextDouble(){
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