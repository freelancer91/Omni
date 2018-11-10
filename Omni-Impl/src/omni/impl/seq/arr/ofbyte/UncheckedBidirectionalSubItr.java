package omni.impl.seq.arr.ofbyte;
import java.util.function.Consumer;
import omni.api.OmniListIterator;
import omni.function.ByteConsumer;
class UncheckedBidirectionalSubItr extends AbstractSeq.Unchecked.AbstractSubList.AbstractBidirectionalItr
        implements
        OmniListIterator.OfByte{
    UncheckedBidirectionalSubItr(UncheckedSubList parent){
        super(parent);
    }
    UncheckedBidirectionalSubItr(UncheckedSubList parent,int cursor){
        super(parent,cursor);
    }
    @Override
    public void forEachRemaining(ByteConsumer action){
        final int cursor,bound;
        final AbstractSeq.Unchecked.AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
            lastRet=bound-1;
        }
    }
    @Override
    public void forEachRemaining(Consumer<? super Byte> action){
        final int cursor,bound;
        final AbstractSeq.Unchecked.AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
            AbstractSeq.uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
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
    public byte nextByte(){
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return parent.root.arr[lastRet];
    }
    @Override
    public int nextIndex(){
        return cursor-parent.rootOffset;
    }
    @Override
    public byte previousByte(){
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return parent.root.arr[lastRet];
    }
    @Override
    public int previousIndex(){
        return cursor-parent.rootOffset-1;
    }
    @Override
    public void remove(){
        final AbstractSeq.Unchecked.AbstractSubList parent;
        final AbstractSeq.Unchecked root;
        final int lastRet;
        AbstractSeq.eraseIndexHelper((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        AbstractSeq.Unchecked.AbstractSubList.bubbleUpDecrementSize(parent.parent);
        --parent.size;
        cursor=lastRet;
    }
    @Override
    public void set(byte val){
        parent.root.arr[lastRet]=val;
    }
}