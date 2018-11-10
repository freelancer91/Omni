package omni.impl.seq.arr.ofboolean;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.BooleanConsumer;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
public class CheckedList extends AbstractSeq.Checked implements OmniList.OfBoolean{
    CheckedList(){
        super();
    }
    CheckedList(int capacity){
        super(capacity);
    }
    CheckedList(int size,boolean[] arr){
        super(size,arr);
    }
    @Override
    public void add(int index,Boolean val){
        super.add(index,val);
    }
    @Override
    public Object clone(){
        final boolean[] arr;
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(this.arr,0,arr=new boolean[size],0,size);
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
    public Boolean get(int index){
        return super.getBoolean(index);
    }
    @Override
    public OmniIterator.OfBoolean iterator(){
        return new CheckedBidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(){
        return new CheckedBidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(int index){
        CheckedCollection.checkLo(index);
        CheckedCollection.checkWriteHi(index,size);
        return new CheckedBidirectionalItr(this,index);
    }
    @Override
    public Boolean remove(int index){
        return super.removeBooleanAt(index);
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
    public Boolean set(int index,Boolean val){
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
    public OmniList.OfBoolean subList(int fromIndex,int toIndex){
        CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
        return new CheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(boolean[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Boolean[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(byte[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(char[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(int[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(short[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedForEach(int size,BooleanConsumer action){
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
    boolean uncheckedRemoveFirstMatch(int size,boolean val){
        final var arr=this.arr;
        int index=0;
        do{
            if(arr[index]==val){
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