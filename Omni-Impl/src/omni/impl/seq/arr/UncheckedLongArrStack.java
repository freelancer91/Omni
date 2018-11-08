package omni.impl.seq.arr;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.util.ArrCopy;
public class UncheckedLongArrStack extends AbstractLongArrSeq.Unchecked implements OmniStack.OfLong{
    UncheckedLongArrStack(){
        super();
    }
    UncheckedLongArrStack(int capacity){
        super(capacity);
    }
    private UncheckedLongArrStack(int size,long[] arr){
        super(size,arr);
    }
    @Override
    public Object clone(){
        final long[] arr;
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(this.arr,0,arr=new long[size],0,size);
        }else{
            arr=null;
        }
        return new UncheckedLongArrStack(size,arr);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public OmniIterator.OfLong iterator(){
        return new DescendingItr(this);
    }
    @Override
    public Long poll(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopLong(size-1);
        }
        return null;
    }
    @Override
    public double pollDouble(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopLong(size-1);
        }
        return Double.NaN;
    }
    @Override
    public float pollFloat(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopLong(size-1);
        }
        return Float.NaN;
    }
    @Override
    public long pollLong(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopLong(size-1);
        }
        return Long.MIN_VALUE;
    }
    @Override
    public Long pop(){
        return super.uncheckedPopLong(size-1);
    }
    @Override
    public long popLong(){
        return super.uncheckedPopLong(size-1);
    }
    @Override
    public void push(Long val){
        super.push(val);
    }
    @Override
    void uncheckedCopyInto(double[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(float[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(long[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(Long[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(Object[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(int size,LongConsumer action){
        uncheckedReverseForEachInRange(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(int size){
        return reverseHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedRemoveFirstMatch(int size,long val){
        final var arr=this.arr;
        int index;
        for(index=--size;arr[index]!=val;--index){
            if(index==0){
                return false;
            }
        }
        eraseIndexHelper(arr,index,size);
        this.size=size;
        return true;
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder){
        reverseToString(arr,0,size,builder);
    }
    private static class DescendingItr extends AbstractDescendingItr implements OmniIterator.OfLong{
        private DescendingItr(Unchecked root){
            super(root);
        }
        @Override
        public void forEachRemaining(Consumer<? super Long> action){
            int cursor;
            if((cursor=this.cursor)!=0){
                uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
                this.cursor=0;
            }
        }
        @Override
        public void forEachRemaining(LongConsumer action){
            int cursor;
            if((cursor=this.cursor)!=0){
                uncheckedReverseForEachInRange(root.arr,0,cursor,action);
                this.cursor=0;
            }
        }
        @Override
        public boolean hasNext(){
            return cursor!=0;
        }
        @Override
        public Long next(){
            return super.nextLong();
        }
        @Override
        public void remove(){
            final Unchecked root;
            eraseIndexHelper((root=this.root).arr,cursor,--root.size);
        }
    }
}
