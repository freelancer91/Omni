package omni.impl2;

import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.util.ArrCopy;
import omni.util.OmniPred;
public class UncheckedRefArrStack<E>extends AbstractRefArrSeq.Unchecked<E> implements OmniStack.OfRef<E>{
    UncheckedRefArrStack(){
        super();
    }
    UncheckedRefArrStack(int size,Object[] arr){
        super(size,arr);
    }
    UncheckedRefArrStack(int capacity){
        super(capacity);
    }
    @Override
    public int search(Object val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    private static class DescendingItr<E> implements OmniIterator.OfRef<E>{
        transient final Unchecked<E> root;
        transient int cursor;
        DescendingItr(Unchecked<E> root){
            this.root=root;
            this.cursor=root.size;
        }
        @Override
        public boolean hasNext(){
            return this.cursor!=0;
        }
        @SuppressWarnings("unchecked")
        @Override
        public E next(){
            return (E)root.arr[--cursor];
        }
        @Override
        public void remove(){
            Unchecked<E> root;
            eraseIndex((root=this.root).arr,cursor,--root.size);
        }
        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> action){
            int cursor;
            if((cursor=this.cursor)!=0){
                final var arr=root.arr;
                ArrSeq.uncheckedForEachReverse(0,cursor-1,index->action.accept((E)arr[index]));
                this.cursor=0;
            }
        }
    }
    @Override
    public OmniIterator.OfRef<E> iterator(){
        return new DescendingItr<>(this);
    }
    @Override
    public E poll(){
        int size;
        if((size=this.size)!=0){
            return super.uncheckedPop(size-1);
        }
        return null;
    }
    @Override
    public E pop(){
        return super.uncheckedPop(size-1);
    }
    @Override
    void uncheckedCopyInto(Object[] arr,int length){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,length);
    }
    @SuppressWarnings("unchecked")
    @Override
    void uncheckedForEach(int size,Consumer<? super E> action){
        ArrSeq.uncheckedForEachReverse(0,size-1,index->action.accept((E)arr[index]));
    }
    @Override
    boolean uncheckedRemoveFirstMatch(int size,Predicate<Object> predicate){
        final var arr=this.arr;
        int index;
        for(index=--size;!predicate.test(arr[index]);--index){
            if(index==0){
                return false;
            }
        }
        eraseIndex(arr,index,size);
        return true;
    }
    @Override
    boolean uncheckedRemoveFirstNonNull(int size,Object nonNull){
        final var arr=this.arr;
        int index;
        for(index=--size;!nonNull.equals(arr[index]);--index){
            if(index==0){
                return false;
            }
        }
        eraseIndex(arr,index,size);
        return true;
    }
}
