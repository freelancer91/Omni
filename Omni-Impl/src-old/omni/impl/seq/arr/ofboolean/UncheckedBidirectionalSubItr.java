package omni.impl.seq.arr.ofboolean;
import java.util.function.Consumer;
import omni.api.OmniListIterator;
import omni.function.BooleanConsumer;
class UncheckedBidirectionalSubItr extends AbstractSeq.Unchecked.AbstractSubList.AbstractBidirectionalItr
implements OmniListIterator.OfBoolean{
    UncheckedBidirectionalSubItr(UncheckedSubList parent){
        super(parent);
    }
    UncheckedBidirectionalSubItr(UncheckedSubList parent,int cursor){
        super(parent,cursor);
    }
    @Override public void forEachRemaining(BooleanConsumer action){
        final int cursor,bound;
        final AbstractSeq.Unchecked.AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size)){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
            lastRet=bound-1;
        }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int cursor,bound;
        final AbstractSeq.Unchecked.AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size)){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
            this.cursor=bound;
            lastRet=bound-1;
        }
    }
    @Override public boolean hasNext(){
        final AbstractSeq.Unchecked.AbstractSubList parent;
        return cursor!=(parent=this.parent).rootOffset+parent.size;
    }
    @Override public boolean hasPrevious(){
        return cursor!=parent.rootOffset;
    }
    @Override public boolean nextBoolean(){
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return parent.root.arr[lastRet];
    }
    @Override public int nextIndex(){
        return cursor-parent.rootOffset;
    }
    @Override public boolean previousBoolean(){
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return parent.root.arr[lastRet];
    }
    @Override public int previousIndex(){
        return cursor-parent.rootOffset-1;
    }
    @Override public void remove(){
        final AbstractSeq.Unchecked.AbstractSubList parent;
        final AbstractSeq.Unchecked root;
        final int lastRet;
        AbstractSeq.eraseIndexHelper((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        AbstractSeq.Unchecked.AbstractSubList.bubbleUpDecrementSize(parent.parent);
        --parent.size;
        cursor=lastRet;
    }
    @Override public void set(boolean val){
        parent.root.arr[lastRet]=val;
    }
}