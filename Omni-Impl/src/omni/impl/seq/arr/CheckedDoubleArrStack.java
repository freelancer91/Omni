package omni.impl.seq.arr;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
public class CheckedDoubleArrStack extends AbstractDoubleArrSeq.Checked implements OmniStack.OfDouble{
    CheckedDoubleArrStack(){
        super();
    }
    CheckedDoubleArrStack(int capacity){
        super(capacity);
    }
    private CheckedDoubleArrStack(int size,double[] arr){
        super(size,arr);
    }
    @Override
    public Object clone(){
        final double[] arr;
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(this.arr,0,arr=new double[size],0,size);
        }else{
            arr=null;
        }
        return new CheckedDoubleArrStack(size,arr);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public OmniIterator.OfDouble iterator(){
        return new DescendingItr(this);
    }
    @Override
    public Double poll(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopDouble(size-1);
        }
        return null;
    }
    @Override
    public double pollDouble(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopDouble(size-1);
        }
        return Double.NaN;
    }
    @Override
    public Double pop(){
        return super.popDouble();
    }
    @Override
    public void push(Double val){
        super.push(val);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Double[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedForEach(int size,DoubleConsumer action){
        final int modCount=this.modCount;
        try{
            uncheckedReverseForEachInRange(arr,0,size,action);
        }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
        }
    }
    @Override
    int uncheckedHashCode(int size){
        return reverseHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedRemoveFirstDbl0(int size){
        final var arr=this.arr;
        int index;
        for(index=--size;arr[index]!=0;--index){
            if(index==0){
                return false;
            }
        }
        ++modCount;
        eraseIndexHelper(arr,index,size);
        this.size=size;
        return true;
    }
    @Override
    boolean uncheckedRemoveFirstDblBits(int size,long dblBits){
        final var arr=this.arr;
        int index;
        for(index=--size;Double.doubleToRawLongBits(arr[index])!=dblBits;--index){
            if(index==0){
                return false;
            }
        }
        ++modCount;
        eraseIndexHelper(arr,index,size);
        this.size=size;
        return true;
    }
    @Override
    boolean uncheckedRemoveFirstDblNaN(int size){
        final var arr=this.arr;
        int index;
        for(index=--size;!Double.isNaN(arr[index]);--index){
            if(index==0){
                return false;
            }
        }
        ++modCount;
        eraseIndexHelper(arr,index,size);
        this.size=size;
        return true;
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder){
        reverseToString(arr,0,size,builder);
    }
    private static class DescendingItr extends AbstractDescendingItr implements OmniIterator.OfDouble{
        private DescendingItr(Checked root){
            super(root);
        }
        @Override
        public void forEachRemaining(Consumer<? super Double> action){
            final int cursor;
            if((cursor=this.cursor)!=0){
                uncheckedForEachRemaining(cursor,action::accept);
            }
        }
        @Override
        public void forEachRemaining(DoubleConsumer action){
            final int cursor;
            if((cursor=this.cursor)!=0){
                uncheckedForEachRemaining(cursor,action);
            }
        }
        @Override
        public boolean hasNext(){
            return cursor!=0;
        }
        @Override
        public Double next(){
            return super.nextDouble();
        }
        @Override
        public void remove(){
            final int lastRet;
            if((lastRet=this.lastRet)!=-1){
                final Checked root;
                int modCount;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                eraseIndexHelper(root.arr,lastRet,--root.size);
                root.modCount=++modCount;
                this.modCount=modCount;
                this.lastRet=-1;
                return;
            }
            throw new IllegalStateException();
        }
        private void uncheckedForEachRemaining(int cursor,DoubleConsumer action){
            final var root=this.root;
            final int expectedModCount=modCount;
            try{
                uncheckedReverseForEachInRange(root.arr,0,cursor,action);
            }finally{
                CheckedCollection.checkModCount(expectedModCount,root.modCount);
            }
            this.cursor=0;
            lastRet=0;
        }
    }
}
