package omni.impl.seq.arr;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.function.CharConsumer;
import omni.util.ArrCopy;
public class UncheckedCharArrStack extends AbstractCharArrSeq.Unchecked implements OmniStack.OfChar{
    UncheckedCharArrStack(){
        super();
    }
    UncheckedCharArrStack(int capacity){
        super(capacity);
    }
    private UncheckedCharArrStack(int size,char[] arr){
        super(size,arr);
    }
    @Override
    public Object clone(){
        final char[] arr;
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(this.arr,0,arr=new char[size],0,size);
        }else{
            arr=null;
        }
        return new UncheckedCharArrStack(size,arr);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public OmniIterator.OfChar iterator(){
        return new DescendingItr(this);
    }
    @Override
    public Character poll(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopChar(size-1);
        }
        return null;
    }
    @Override
    public char pollChar(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopChar(size-1);
        }
        return Character.MIN_VALUE;
    }
    @Override
    public double pollDouble(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopChar(size-1);
        }
        return Double.NaN;
    }
    @Override
    public float pollFloat(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopChar(size-1);
        }
        return Float.NaN;
    }
    @Override
    public int pollInt(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopChar(size-1);
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public long pollLong(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopChar(size-1);
        }
        return Long.MIN_VALUE;
    }
    @Override
    public Character pop(){
        return super.uncheckedPopChar(size-1);
    }
    @Override
    public char popChar(){
        return super.uncheckedPopChar(size-1);
    }
    @Override
    public void push(Character val){
        super.push(val);
    }
    @Override
    void uncheckedCopyInto(char[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(Character[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
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
    void uncheckedCopyInto(int[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(long[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(Object[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(int size,CharConsumer action){
        uncheckedReverseForEachInRange(arr,0,size,action);
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
        eraseIndexHelper(arr,index,size);
        this.size=size;
        return true;
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder){
        reverseToString(arr,0,size,builder);
    }
    private static class DescendingItr extends AbstractDescendingItr implements OmniIterator.OfChar{
        private DescendingItr(Unchecked root){
            super(root);
        }
        @Override
        public void forEachRemaining(CharConsumer action){
            int cursor;
            if((cursor=this.cursor)!=0){
                uncheckedReverseForEachInRange(root.arr,0,cursor,action);
                this.cursor=0;
            }
        }
        @Override
        public void forEachRemaining(Consumer<? super Character> action){
            int cursor;
            if((cursor=this.cursor)!=0){
                uncheckedReverseForEachInRange(root.arr,0,cursor,action::accept);
                this.cursor=0;
            }
        }
        @Override
        public boolean hasNext(){
            return cursor!=0;
        }
        @Override
        public Character next(){
            return super.nextChar();
        }
        @Override
        public void remove(){
            final Unchecked root;
            eraseIndexHelper((root=this.root).arr,cursor,--root.size);
        }
    }
}
