package omni.impl.seq.arr.oflong;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractLongItr;
class UncheckedDescendingItr extends AbstractLongItr implements OmniIterator.OfLong{
    private transient final UncheckedStack root;
    private transient int cursor;
    UncheckedDescendingItr(UncheckedStack root){
        this.root=root;
        cursor=root.size;
    }
    @Override
    public void forEachRemaining(Consumer<? super Long> action){
        int cursor;
        if((cursor=this.cursor)!=0){
            AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
            this.cursor=0;
        }
    }
    @Override
    public void forEachRemaining(LongConsumer action){
        int cursor;
        if((cursor=this.cursor)!=0){
            AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action);
            this.cursor=0;
        }
    }
    @Override
    public boolean hasNext(){
        return cursor!=0;
    }
    @Override
    public long nextLong(){
        return root.arr[--cursor];
    }
    @Override
    public void remove(){
        final UncheckedStack root;
        AbstractSeq.eraseIndexHelper((root=this.root).arr,cursor,--root.size);
    }
}