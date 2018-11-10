package omni.impl.seq.arr.ofbyte;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.function.ByteConsumer;
import omni.impl.AbstractByteItr;
class UncheckedDescendingItr extends AbstractByteItr implements OmniIterator.OfByte{
    private transient final UncheckedStack root;
    private transient int cursor;
    UncheckedDescendingItr(UncheckedStack root){
        this.root=root;
        cursor=root.size;
    }
    @Override
    public void forEachRemaining(ByteConsumer action){
        int cursor;
        if((cursor=this.cursor)!=0){
            AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action);
            this.cursor=0;
        }
    }
    @Override
    public void forEachRemaining(Consumer<? super Byte> action){
        int cursor;
        if((cursor=this.cursor)!=0){
            AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
            this.cursor=0;
        }
    }
    @Override
    public boolean hasNext(){
        return cursor!=0;
    }
    @Override
    public byte nextByte(){
        return root.arr[--cursor];
    }
    @Override
    public void remove(){
        final UncheckedStack root;
        AbstractSeq.eraseIndexHelper((root=this.root).arr,cursor,--root.size);
    }
}