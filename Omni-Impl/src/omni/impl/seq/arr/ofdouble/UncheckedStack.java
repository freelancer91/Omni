package omni.impl.seq.arr.ofdouble;
import java.util.function.DoubleConsumer;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.util.ArrCopy;
public class UncheckedStack extends AbstractSeq.Unchecked implements OmniStack.OfDouble{
    UncheckedStack(){
        super();
    }
    UncheckedStack(int capacity){
        super(capacity);
    }
    private UncheckedStack(int size,double[] arr){
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
        return new UncheckedStack(size,arr);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public OmniIterator.OfDouble iterator(){
        return new UncheckedDescendingItr(this);
    }
    @Override
    public Double poll(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopDouble(size-1);
        }
        return null;
    }
    @Override
    public double pollDouble(){
        final int size;
        if((size=this.size)!=0){
            return super.uncheckedPopDouble(size-1);
        }
        return Double.NaN;
    }
    @Override
    public Double pop(){
        return super.uncheckedPopDouble(size-1);
    }
    @Override
    public double popDouble(){
        return super.uncheckedPopDouble(size-1);
    }
    @Override
    public void push(Double val){
        super.push(val);
    }
    @Override
    void uncheckedCopyInto(double[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(Double[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(Object[] arr,int size){
        ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(int size,DoubleConsumer action){
        uncheckedReverseForEachInRange(arr,0,size,action);
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
        eraseIndexHelper(arr,index,size);
        this.size=size;
        return true;
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder){
        reverseToString(arr,0,size,builder);
    }
}
