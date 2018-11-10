package omni.impl.seq.arr.ofref;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.OmniPred;
public class UncheckedList<E>extends AbstractSeq.Unchecked<E> implements OmniList.OfRef<E>{
    UncheckedList(){
        super();
    }
    UncheckedList(int capacity){
        super(capacity);
    }
    UncheckedList(int size,Object[] arr){
        super(size,arr);
    }
    @Override
    public Object clone(){
        final Object[] arr;
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(this.arr,0,arr=new Object[size],0,size);
        }else{
            arr=null;
        }
        return new UncheckedList<>(size,arr);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public int indexOf(Object val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public OmniIterator.OfRef<E> iterator(){
        return new UncheckedAscendingItr<>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(){
        return new UncheckedBidirectionalItr<>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index){
        return new UncheckedBidirectionalItr<>(this,index);
    }
    @Override
    public E remove(int index){
        final Object[] arr;
        @SuppressWarnings("unchecked")
        final var removed=(E)(arr=this.arr)[index];
        eraseIndexHelper(arr,index,--size);
        return removed;
    }
    @Override
    public void replaceAll(UnaryOperator<E> operator){
        final int size;
        if((size=this.size)!=0){
            uncheckedReplaceAll(arr,0,size,operator);
        }
    }
    @Override
    public void reverseSort(){
        final int size;
        if((size=this.size)>1){
            uncheckedSort(arr,0,size-1,Comparator.reverseOrder());
        }
    }
    @Override
    public void sort(){
        final int size;
        if((size=this.size)>1){
            uncheckedSort(arr,0,size-1,Comparator.naturalOrder());
        }
    }
    @Override
    public void sort(Comparator<? super E> sorter){
        final int size;
        if((size=this.size)>1){
            uncheckedSort(arr,0,size-1,sorter);
        }
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
        return new UncheckedSubList<>(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedForEach(int size,Consumer<? super E> action){
        uncheckedForwardForEachInRange(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(int size){
        return forwardHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedRemoveFirstMatch(int size,Predicate<Object> pred){
        final var arr=this.arr;
        int index=0;
        do{
            if(pred.test(arr[index])){
                eraseIndexHelper(arr,index,--size);
                this.size=size;
                return true;
            }
        }while(++index!=size);
        return false;
    }
    @Override
    boolean uncheckedRemoveFirstNonNull(int size,Object nonNull){
        final var arr=this.arr;
        int index=0;
        do{
            if(nonNull.equals(arr[index])){
                eraseIndexHelper(arr,index,--size);
                this.size=size;
                return true;
            }
        }while(++index!=size);
        return false;
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder){
        forwardToString(arr,0,size,builder);
    }
}