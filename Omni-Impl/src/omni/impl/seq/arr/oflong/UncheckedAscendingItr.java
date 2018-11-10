package omni.impl.seq.arr.oflong;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractLongItr;
class UncheckedAscendingItr extends AbstractLongItr implements OmniIterator.OfLong{
    private transient final UncheckedList root;
    private transient int cursor;
    UncheckedAscendingItr(UncheckedList root){
        this.root=root;
    }
    @Override
    public void forEachRemaining(Consumer<? super Long> action){
        final UncheckedList root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size)){
            AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
            this.cursor=bound;
        }
    }
    @Override
    public void forEachRemaining(LongConsumer action){
        final UncheckedList root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size)){
            AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
            this.cursor=bound;
        }
    }
    @Override
    public boolean hasNext(){
        return cursor!=root.size;
    }
    @Override
    public long nextLong(){
        return root.arr[cursor++];
    }
    @Override
    public void remove(){
        final UncheckedList root;
        AbstractSeq.eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
    }
}