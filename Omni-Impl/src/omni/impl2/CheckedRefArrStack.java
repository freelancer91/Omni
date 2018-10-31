package omni.impl2;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;

public class CheckedRefArrStack<E>extends AbstractRefArrSeq.Checked<E> implements OmniStack.OfRef<E>{
    CheckedRefArrStack(){
        super();
    }
    CheckedRefArrStack(int size,Object[] arr){
        super(size,arr);
    }
    CheckedRefArrStack(int capacity){
        super(capacity);
    }
    @Override
    public int search(Object val){
        final int size=this.size;
        if(size!=0){
            if(val!=null){
                int modCount=this.modCount;
                try{
                    return ArrSeq.uncheckedSearch(size,index->val.equals(arr[index]));
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                }
            }
            return ArrSeq.uncheckedSearch(size,index->arr[index]==null);
        }
        return -1;
    }
    private static class DescendingItr<E> implements OmniIterator.OfRef<E>{
        transient final Checked<E> root;
        transient int cursor;
        transient int modCount;
        transient int lastRet=-1;
        DescendingItr(Checked<E> root){
            this.root=root;
            this.cursor=root.size;
            this.modCount=root.modCount;
        }
        @Override
        public boolean hasNext(){
            return this.cursor!=0;
        }
        @Override
        @SuppressWarnings("unchecked")
        public E next(){
            final Checked<E> root;
            CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
            int cursor;
            if((cursor=this.cursor)!=0){
                this.lastRet=--cursor;
                this.cursor=cursor;
                return (E)root.arr[cursor];
            }
            throw new NoSuchElementException();
        }
        @Override
        public void remove(){
            int lastRet;
            if((lastRet=this.lastRet)!=-1){
                int modCount;
                Checked<E> root;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                eraseIndex(root.arr,lastRet,--root.size);
                root.modCount=++modCount;
                this.modCount=modCount;
                this.lastRet=-1;
                return;
            }
            throw new IllegalStateException();
        }
        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> action){
            int cursor;
            if((cursor=this.cursor)!=0){
                int modCount=this.modCount;
                final var root=this.root;
                try{
                    final var arr=root.arr;
                    ArrSeq.uncheckedForEachReverse(0,cursor-1,index->action.accept((E)arr[index]));
                }finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
                this.cursor=0;
                this.lastRet=0;
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
            ++modCount;
            return super.uncheckedPop(size-1);
        }
        return null;
    }
    @Override
    public E pop(){
        int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPop(size-1);
        }
        throw new NoSuchElementException();
    }
    @Override
    void uncheckedCopyInto(Object[] arr,int length){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,length);
    }
    @SuppressWarnings("unchecked")
    @Override
    void uncheckedForEach(int size,Consumer<? super E> action){
        int modCount=this.modCount;
        try{
            ArrSeq.uncheckedForEachReverse(0,size-1,index->action.accept((E)arr[index]));
        }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
        }
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
        ++modCount;
        eraseIndex(arr,index,size);
        return true;
    }
    @Override
    boolean uncheckedRemoveFirstNonNull(int size,Object nonNull){
        int modCount=this.modCount;
        final var arr=this.arr;
        int index;
        try{
            for(index=--size;!nonNull.equals(arr[index]);--index){
                if(index==0){
                    return false;
                }
            }
        }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
        eraseIndex(arr,index,size);
        return true;
    }
}
