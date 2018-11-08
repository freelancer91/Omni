package omni.impl.seq.arr;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.function.FloatConsumer;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
public class CheckedFloatArrStack extends AbstractFloatArrSeq.Checked implements OmniStack.OfFloat{
    CheckedFloatArrStack(){
        super();
    }
    CheckedFloatArrStack(int capacity){
        super(capacity);
    }
    private CheckedFloatArrStack(int size,float[] arr){
        super(size,arr);
    }
    @Override
    public Object clone(){
        final float[] arr;
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(this.arr,0,arr=new float[size],0,size);
        }else{
            arr=null;
        }
        return new CheckedFloatArrStack(size,arr);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public OmniIterator.OfFloat iterator(){
        return new DescendingItr(this);
    }
    @Override
    public Float poll(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopFloat(size-1);
        }
        return null;
    }
    @Override
    public double pollDouble(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopFloat(size-1);
        }
        return Double.NaN;
    }
    @Override
    public float pollFloat(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopFloat(size-1);
        }
        return Float.NaN;
    }
    @Override
    public Float pop(){
        return super.popFloat();
    }
    @Override
    public void push(Float val){
        super.push(val);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Float[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedForEach(int size,FloatConsumer action){
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
    boolean uncheckedRemoveFirstFlt0(int size){
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
    boolean uncheckedRemoveFirstFltBits(int size,int fltBits){
        final var arr=this.arr;
        int index;
        for(index=--size;Float.floatToRawIntBits(arr[index])!=fltBits;--index){
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
    boolean uncheckedRemoveFirstFltNaN(int size){
        final var arr=this.arr;
        int index;
        for(index=--size;!Float.isNaN(arr[index]);--index){
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
    private static class DescendingItr extends AbstractDescendingItr implements OmniIterator.OfFloat{
        private DescendingItr(Checked root){
            super(root);
        }
        @Override
        public void forEachRemaining(Consumer<? super Float> action){
            final int cursor;
            if((cursor=this.cursor)!=0){
                uncheckedForEachRemaining(cursor,action::accept);
            }
        }
        @Override
        public void forEachRemaining(FloatConsumer action){
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
        public Float next(){
            return super.nextFloat();
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
        private void uncheckedForEachRemaining(int cursor,FloatConsumer action){
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
