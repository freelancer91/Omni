package omni.impl.seq.arr.ofshort;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.ShortConsumer;
import omni.impl.AbstractShortItr;
class UncheckedAscendingItr extends AbstractShortItr implements OmniIterator.OfShort{
    private transient final UncheckedList root;
    private transient int cursor;
    UncheckedAscendingItr(UncheckedList root){
        this.root=root;
    }
    @Override
    public void forEachRemaining(Consumer<? super Short> action){
        final UncheckedList root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size)){
            AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
            this.cursor=bound;
        }
    }
    @Override
    public void forEachRemaining(ShortConsumer action){
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
    public short nextShort(){
        return root.arr[cursor++];
    }
    @Override
    public void remove(){
        final UncheckedList root;
        AbstractSeq.eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
    }
}