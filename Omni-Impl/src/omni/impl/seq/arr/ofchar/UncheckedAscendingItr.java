package omni.impl.seq.arr.ofchar;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.CharConsumer;
import omni.impl.AbstractCharItr;
class UncheckedAscendingItr extends AbstractCharItr implements OmniIterator.OfChar{
    private transient final UncheckedList root;
    private transient int cursor;
    UncheckedAscendingItr(UncheckedList root){
        this.root=root;
    }
    @Override
    public void forEachRemaining(CharConsumer action){
        final UncheckedList root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size)){
            AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
            this.cursor=bound;
        }
    }
    @Override
    public void forEachRemaining(Consumer<? super Character> action){
        final UncheckedList root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size)){
            AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
            this.cursor=bound;
        }
    }
    @Override
    public boolean hasNext(){
        return cursor!=root.size;
    }
    @Override
    public char nextChar(){
        return root.arr[cursor++];
    }
    @Override
    public void remove(){
        final UncheckedList root;
        AbstractSeq.eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
    }
}