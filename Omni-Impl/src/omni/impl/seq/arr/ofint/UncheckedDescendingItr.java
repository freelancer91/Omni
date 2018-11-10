package omni.impl.seq.arr.ofint;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractIntItr;
class UncheckedDescendingItr extends AbstractIntItr implements OmniIterator.OfInt{
    private transient final UncheckedStack root;
    private transient int cursor;
    UncheckedDescendingItr(UncheckedStack root){
        this.root=root;
        cursor=root.size;
    }
    @Override
    public void forEachRemaining(Consumer<? super Integer> action){
        int cursor;
        if((cursor=this.cursor)!=0){
            AbstractSeq.uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
            this.cursor=0;
        }
    }
    @Override
    public void forEachRemaining(IntConsumer action){
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
    public int nextInt(){
        return root.arr[--cursor];
    }
    @Override
    public void remove(){
        final UncheckedStack root;
        AbstractSeq.eraseIndexHelper((root=this.root).arr,cursor,--root.size);
    }
}