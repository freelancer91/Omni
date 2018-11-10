package omni.impl.seq.arr.ofbyte;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.ByteConsumer;
import omni.impl.AbstractByteItr;
class UncheckedAscendingItr extends AbstractByteItr implements OmniIterator.OfByte{
    private transient final UncheckedList root;
    private transient int cursor;
    UncheckedAscendingItr(UncheckedList root){
        this.root=root;
    }
    @Override
    public void forEachRemaining(ByteConsumer action){
        final UncheckedList root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size)){
            AbstractSeq.uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
            this.cursor=bound;
        }
    }
    @Override
    public void forEachRemaining(Consumer<? super Byte> action){
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
    public byte nextByte(){
        return root.arr[cursor++];
    }
    @Override
    public void remove(){
        final UncheckedList root;
        AbstractSeq.eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
    }
}