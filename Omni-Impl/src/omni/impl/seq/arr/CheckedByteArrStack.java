package omni.impl.seq.arr;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.function.ByteConsumer;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
public class CheckedByteArrStack extends AbstractByteArrSeq.Checked implements OmniStack.OfByte{
    CheckedByteArrStack(){
        super();
    }
    CheckedByteArrStack(int capacity){
        super(capacity);
    }
    private CheckedByteArrStack(int size,byte[] arr){
        super(size,arr);
    }
    @Override
    public Object clone(){
        final byte[] arr;
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(this.arr,0,arr=new byte[size],0,size);
        }else{
            arr=null;
        }
        return new CheckedByteArrStack(size,arr);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public OmniIterator.OfByte iterator(){
        return new DescendingItr(this);
    }
    @Override
    public Byte poll(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopByte(size-1);
        }
        return null;
    }
    @Override
    public byte pollByte(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopByte(size-1);
        }
        return Byte.MIN_VALUE;
    }
    @Override
    public double pollDouble(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopByte(size-1);
        }
        return Double.NaN;
    }
    @Override
    public float pollFloat(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopByte(size-1);
        }
        return Float.NaN;
    }
    @Override
    public int pollInt(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopByte(size-1);
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public long pollLong(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopByte(size-1);
        }
        return Long.MIN_VALUE;
    }
    @Override
    public short pollShort(){
        final int size;
        if((size=this.size)!=0){
            ++modCount;
            return super.uncheckedPopByte(size-1);
        }
        return Short.MIN_VALUE;
    }
    @Override
    public Byte pop(){
        return super.popByte();
    }
    @Override
    public void push(Byte val){
        super.push(val);
    }
    @Override
    void uncheckedCopyInto(byte[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Byte[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
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
    void uncheckedCopyInto(int[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(short[] dst,int length){
        ArrCopy.uncheckedReverseCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedForEach(int size,ByteConsumer action){
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
    boolean uncheckedRemoveFirstMatch(int size,int val){
        final var arr=this.arr;
        int index;
        for(index=--size;arr[index]!=val;--index){
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
    private static class DescendingItr extends AbstractDescendingItr implements OmniIterator.OfByte{
        private DescendingItr(Checked root){
            super(root);
        }
        @Override
        public void forEachRemaining(ByteConsumer action){
            final int cursor;
            if((cursor=this.cursor)!=0){
                uncheckedForEachRemaining(cursor,action);
            }
        }
        @Override
        public void forEachRemaining(Consumer<? super Byte> action){
            final int cursor;
            if((cursor=this.cursor)!=0){
                uncheckedForEachRemaining(cursor,action::accept);
            }
        }
        @Override
        public boolean hasNext(){
            return cursor!=0;
        }
        @Override
        public Byte next(){
            return super.nextByte();
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
        private void uncheckedForEachRemaining(int cursor,ByteConsumer action){
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