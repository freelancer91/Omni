package omni.impl.seq.arr.ofdouble;
import java.util.function.DoubleConsumer;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
public class CheckedList extends AbstractSeq.Checked implements OmniList.OfDouble{
    CheckedList(){
        super();
    }
    CheckedList(int capacity){
        super(capacity);
    }
    CheckedList(int size,double[] arr){
        super(size,arr);
    }
    @Override
    public void add(int index,Double val){
        super.add(index,val);
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
        return new CheckedList(size,arr);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public Double get(int index){
        return super.getDouble(index);
    }
    @Override
    public OmniIterator.OfDouble iterator(){
        return new CheckedBidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfDouble listIterator(){
        return new CheckedBidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfDouble listIterator(int index){
        CheckedCollection.checkLo(index);
        CheckedCollection.checkWriteHi(index,size);
        return new CheckedBidirectionalItr(this,index);
    }
    @Override
    public Double remove(int index){
        return super.removeDoubleAt(index);
    }
    @Override
    public void reverseSort(){
        final int size;
        if((size=this.size)>1){
            uncheckedReverseSort(arr,0,size-1);
            ++modCount;
        }
    }
    @Override
    public Double set(int index,Double val){
        return super.set(index,val);
    }
    @Override
    public void sort(){
        final int size;
        if((size=this.size)>1){
            uncheckedSort(arr,0,size-1);
            ++modCount;
        }
    }
    @Override
    public OmniList.OfDouble subList(int fromIndex,int toIndex){
        CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
        return new CheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Double[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedForEach(int size,DoubleConsumer action){
        final int modCount=this.modCount;
        try{
            uncheckedForwardForEachInRange(arr,0,size,action);
        }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
        }
    }
    @Override
    int uncheckedHashCode(int size){
        return forwardHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedRemoveFirstDbl0(int size){
        final var arr=this.arr;
        int index=0;
        do{
            if(arr[index]==0){
                ++modCount;
                eraseIndexHelper(arr,index,--size);
                this.size=size;
                return true;
            }
        }while(++index!=size);
        return false;
    }
    @Override
    boolean uncheckedRemoveFirstDblBits(int size,long dblBits){
        final var arr=this.arr;
        int index=0;
        do{
            if(Double.doubleToRawLongBits(arr[index])==dblBits){
                ++modCount;
                eraseIndexHelper(arr,index,--size);
                this.size=size;
                return true;
            }
        }while(++index!=size);
        return false;
    }
    @Override
    boolean uncheckedRemoveFirstDblNaN(int size){
        final var arr=this.arr;
        int index=0;
        do{
            if(Double.isNaN(arr[index])){
                ++modCount;
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
