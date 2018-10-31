package omni.impl2;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.BitSetUtils;
import omni.util.OmniArray;
import omni.util.OmniPred;
import omni.util.TypeUtil;
abstract class AbstractRefArrSeq<E>extends ArrSeq.OfRef<E> implements OmniCollection.OfRef<E>{
    static int finalizeSubListBatchRemove(AbstractRefArrSeq<?> root,Object[] arr,int newBound,int oldBound){
        final int rootSize,newRootSize,numRemoved;
        root.size=newRootSize=(rootSize=root.size)-(numRemoved=oldBound-newBound);
        ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
        OmniArray.OfRef.nullifyRange(arr,newRootSize,rootSize-1);
        return numRemoved;
    }
    AbstractRefArrSeq(){
        super();
    }
    AbstractRefArrSeq(int capacity){
        super(capacity);
    }
    AbstractRefArrSeq(int size,Object[] arr){
        super(size,arr);
    }
    @Override
    public void clear(){
        final int size=this.size;
        if(size!=0){
            uncheckedClear(size);
        }
    }
    @Override
    public boolean contains(boolean val){
        final int size=this.size;
        return size!=0&&ArrSeq.uncheckedAnyMatches(0,size-1,index->TypeUtil.refEquals(arr[index],val));
    }
    @Override
    public boolean contains(Boolean val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->pred.test(arr[index]));
        }
        return false;
    }
    @Override
    public boolean contains(byte val){
        final int size=this.size;
        return size!=0&&ArrSeq.uncheckedAnyMatches(0,size-1,index->TypeUtil.refEquals(arr[index],val));
    }
    @Override
    public boolean contains(Byte val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->pred.test(arr[index]));
        }
        return false;
    }
    @Override
    public boolean contains(char val){
        final int size=this.size;
        return size!=0&&ArrSeq.uncheckedAnyMatches(0,size-1,index->TypeUtil.refEquals(arr[index],val));
    }
    @Override
    public boolean contains(Character val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->pred.test(arr[index]));
        }
        return false;
    }
    @Override
    public boolean contains(double val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->pred.test(arr[index]));
        }
        return false;
    }
    @Override
    public boolean contains(Double val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->pred.test(arr[index]));
        }
        return false;
    }
    @Override
    public boolean contains(float val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->pred.test(arr[index]));
        }
        return false;
    }
    @Override
    public boolean contains(Float val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->pred.test(arr[index]));
        }
        return false;
    }
    @Override
    public boolean contains(int val){
        final int size=this.size;
        return size!=0&&ArrSeq.uncheckedAnyMatches(0,size-1,index->TypeUtil.refEquals(arr[index],val));
    }
    @Override
    public boolean contains(Integer val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->pred.test(arr[index]));
        }
        return false;
    }
    @Override
    public boolean contains(long val){
        final int size=this.size;
        return size!=0&&ArrSeq.uncheckedAnyMatches(0,size-1,index->TypeUtil.refEquals(arr[index],val));
    }
    @Override
    public boolean contains(Long val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->pred.test(arr[index]));
        }
        return false;
    }
    @Override
    public boolean contains(Object val){
        final int size=this.size;
        if(size!=0){
            if(val!=null){
                return uncheckedContainsNonNull(size,val);
            }
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->arr[index]==null);
        }
        return false;
    }
    @Override
    public boolean contains(short val){
        final int size=this.size;
        return size!=0&&ArrSeq.uncheckedAnyMatches(0,size-1,index->TypeUtil.refEquals(arr[index],val));
    }
    @Override
    public boolean contains(Short val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedAnyMatches(0,size-1,index->pred.test(arr[index]));
        }
        return false;
    }
    @Override
    public void forEach(Consumer<? super E> action){
        final int size=this.size;
        if(size!=0){
            uncheckedForEach(size,action);
        }
    }
    @SuppressWarnings("unchecked")
    public E get(int index){
        return (E)arr[index];
    }
    public int indexOf(boolean val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedFirstIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int indexOf(Boolean val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedFirstIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int indexOf(byte val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedFirstIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int indexOf(Byte val){
        int size;
        if((size=this.size)!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedFirstIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int indexOf(char val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedFirstIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int indexOf(Character val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedFirstIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int indexOf(double val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedFirstIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int indexOf(Double val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedFirstIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int indexOf(float val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedFirstIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int indexOf(Float val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedFirstIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int indexOf(int val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedFirstIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int indexOf(Integer val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedFirstIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int indexOf(long val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedFirstIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int indexOf(Long val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedFirstIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int indexOf(short val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedFirstIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int indexOf(Short val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedFirstIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int lastIndexOf(boolean val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedLastIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int lastIndexOf(Boolean val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedLastIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int lastIndexOf(byte val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedLastIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int lastIndexOf(Byte val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedLastIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int lastIndexOf(char val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedLastIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int lastIndexOf(Character val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedLastIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int lastIndexOf(double val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedLastIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int lastIndexOf(Double val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedLastIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int lastIndexOf(float val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedLastIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int lastIndexOf(Float val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedLastIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int lastIndexOf(int val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedLastIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int lastIndexOf(Integer val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedLastIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int lastIndexOf(long val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedLastIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int lastIndexOf(Long val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedLastIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int lastIndexOf(short val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedLastIndex(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int lastIndexOf(Short val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedLastIndex(size-1,index->pred.test(arr[index]));
        }
        return -1;
    }
    @SuppressWarnings("unchecked")
    public E peek(){
        final int size=this.size;
        if(size!=0){
            return (E)arr[size-1];
        }
        return null;
    }
    public void put(int index,E val){
        arr[index]=val;
    }
    @Override
    public boolean remove(Object val){
        final int size=this.size;
        if(size!=0){
            if(val!=null){
                return uncheckedRemoveFirstNonNull(size,val);
            }
            return uncheckedRemoveFirstMatch(size,Objects::isNull);
        }
        return false;
    }
    @Override
    public boolean removeIf(Predicate<? super E> filter){
        final int size=this.size;
        return size!=0&&uncheckedRemoveIf(size,filter);
    }
    @Override
    public boolean removeVal(boolean val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(Boolean val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(byte val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(Byte val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(char val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(Character val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(double val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(Double val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(float val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(Float val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(int val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(Integer val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(long val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(Long val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(short val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeVal(Short val){
        final int size=this.size;
        return size!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public int search(boolean val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedSearch(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int search(Boolean val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int search(byte val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedSearch(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int search(Byte val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int search(char val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedSearch(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int search(Character val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int search(double val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int search(Double val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int search(float val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int search(Float val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int search(int val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedSearch(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int search(Integer val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int search(long val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedSearch(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int search(Long val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    public int search(short val){
        final int size=this.size;
        if(size!=0){
            return ArrSeq.uncheckedSearch(size-1,index->TypeUtil.refEquals(arr[index],val));
        }
        return -1;
    }
    public int search(Short val){
        final int size=this.size;
        if(size!=0){
            final var pred=OmniPred.OfRef.getEqualsPred(val);
            return ArrSeq.uncheckedSearch(size,index->pred.test(arr[index]));
        }
        return -1;
    }
    public E set(int index,E val){
        final var arr=this.arr;
        @SuppressWarnings("unchecked")
        final var oldVal=(E)arr[index];
        arr[index]=val;
        return oldVal;
    }
    @Override
    public Object[] toArray(){
        final int size=this.size;
        if(size!=0){
            final var dst=new Object[size];
            uncheckedCopyInto(dst,size);
            return dst;
        }
        return OmniArray.OfRef.DEFAULT_ARR;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final int size=this.size;
        final var dst=arrConstructor.apply(size);
        if(size!=0){
            uncheckedCopyInto(dst,size);
        }
        return dst;
    }
    @Override
    public <T> T[] toArray(T[] dst){
        final int size=this.size;
        if(size!=0){
            dst=OmniArray.uncheckedArrResize(size,dst);
            uncheckedCopyInto(dst,size);
        }else if(dst.length!=0){
            dst[0]=null;
        }
        return dst;
    }
    void uncheckedClear(int size){
        OmniArray.OfRef.nullifyRange(arr,0,size-1);
        this.size=0;
    }
    boolean uncheckedContainsNonNull(int size,Object nonNull){
        return ArrSeq.uncheckedAnyMatches(0,size-1,index->nonNull.equals(arr[index]));
    }
    abstract void uncheckedCopyInto(Object[] arr,int length);
    abstract void uncheckedForEach(int size,Consumer<? super E> action);
    abstract boolean uncheckedRemoveFirstMatch(int size,Predicate<Object> predicate);
    abstract boolean uncheckedRemoveFirstNonNull(int size,Object nonNull);
    abstract boolean uncheckedRemoveIf(int size,Predicate<? super E> filter);
    static abstract class Checked<E>extends AbstractRefArrSeq<E>{
        @SuppressWarnings("unchecked")
        static <E> int markSurvivors(Object[] arr,long[] survivorSet,int offset,int bound,Predicate<? super E> filter){
            for(int numSurvivors=0,wordOffset=0;;){
                long word=0L,marker=1L;
                do{
                    if(!filter.test((E)arr[offset])){
                        ++numSurvivors;
                        word|=marker;
                    }
                    if(++offset==bound){
                        survivorSet[wordOffset]=word;
                        return numSurvivors;
                    }
                }while((marker<<=1)!=0);
                survivorSet[wordOffset++]=word;
            }
        }
        static int pullSurvivorsDown(Object[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
            for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
                long survivorWord;
                int runLength;
                if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
                    int wordSrcOffset=srcOffset;
                    do{
                        ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                                runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
                        dstOffset+=runLength;
                        if((numSurvivors-=runLength)==0){
                            return dstOffset;
                        }
                        wordSrcOffset+=runLength;
                    }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
                }
            }
        }
        transient int modCount;
        Checked(){
            super();
        }
        Checked(int capacity){
            super(capacity);
        }
        Checked(int size,Object[] arr){
            super(size,arr);
        }
        @Override
        public boolean add(E val){
            ++modCount;
            final int size=this.size;
            if(size!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
            return true;
        }
        @Override
        public E get(int index){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
            return super.get(index);
        }
        public void push(E val){
            ++modCount;
            final int size=this.size;
            if(size!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
        }
        @Override
        public void put(int index,E val){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
            super.put(index,val);
        }
        @Override
        public E set(int index,E val){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
            return super.set(index,val);
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            return super.toArray(size->{
                final int expectedModCount=modCount;
                try{
                    return arrConstructor.apply(size);
                }finally{
                    CheckedCollection.checkModCount(expectedModCount,modCount);
                }
            });
        }
        @Override
        void uncheckedClear(int size){
            ++modCount;
            super.uncheckedClear(size);
        }
        @Override
        boolean uncheckedContainsNonNull(int size,Object nonNull){
            final int modCount=this.modCount;
            try{
                return super.uncheckedContainsNonNull(size,nonNull);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @SuppressWarnings("unchecked")
        @Override
        boolean uncheckedRemoveIf(int size,Predicate<? super E> filter){
            final int modCount=this.modCount;
            final var arr=this.arr;
            int srcOffset=0;
            try{
                do{
                    if(filter.test((E)arr[srcOffset])){
                        int dstOffset=srcOffset;
                        while(++srcOffset!=size){
                            Object v;
                            if(!filter.test((E)(v=arr[srcOffset]))){
                                long[] survivors;
                                int numSurvivors;
                                if((numSurvivors=size-++srcOffset)!=0){
                                    numSurvivors=markSurvivors(arr,survivors=BitSetUtils.getBitSet(numSurvivors),
                                            srcOffset,size,filter);
                                    CheckedCollection.checkModCount(modCount,this.modCount);
                                    arr[dstOffset++]=v;
                                    if(numSurvivors!=0){
                                        dstOffset=pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,numSurvivors);
                                    }
                                }else{
                                    CheckedCollection.checkModCount(modCount,this.modCount);
                                    arr[dstOffset++]=v;
                                }
                                OmniArray.OfRef.nullifyRange(arr,dstOffset,--size);
                                this.size=size;
                                return true;
                            }
                        }
                        CheckedCollection.checkModCount(modCount,this.modCount);
                        OmniArray.OfRef.nullifyRange(arr,dstOffset,--size);
                        this.size=size;
                        return true;
                    }
                }while(++srcOffset!=size);
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,this.modCount,e);
            }
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
        }
        private Object[] growInsert(Object[] arr,int index,int size){
            if(arr.length==size){
                ArrCopy.semicheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,index);
                this.arr=arr;
            }
            return arr;
        }
        private void uncheckedAppend(E val,int size){
            var arr=this.arr;
            if(arr.length==size){
                ArrCopy.uncheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,size);
                this.arr=arr;
            }
            arr[size]=val;
            this.size=size+1;
        }
        private void uncheckedInit(E val){
            var arr=this.arr;
            if(arr==OmniArray.OfRef.DEFAULT_ARR){
                this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
            }else if(arr==null){
                this.arr=new Object[1];
            }
            arr[0]=val;
            size=1;
        }
        private void uncheckedInsert(int index,E val,int size){
            int tailDist;
            if((tailDist=size-index)==0){
                uncheckedAppend(val,size);
            }else{
                Object[] arr;
                ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
                arr[index]=val;
                this.size=size+1;
            }
        }
        static abstract class AbstractBidirectionalItr<E> implements OmniListIterator.OfRef<E>{
            transient final Checked<E> root;
            transient int cursor;
            transient int lastRet;
            transient int modCount;
            AbstractBidirectionalItr(Checked<E> root){
                this.root=root;
                this.modCount=root.modCount;
            }
            AbstractBidirectionalItr(Checked<E> root,int cursor){
                this.root=root;
                this.cursor=cursor;
                this.modCount=root.modCount;
            }
            @Override
            public void add(E val){
                int modCount=this.modCount;
                final var root=this.root;
                CheckedCollection.checkModCount(modCount,root.modCount);
                int rootSize;
                final int cursor=this.cursor;
                if((rootSize=root.size)!=0){
                    root.uncheckedInsert(cursor,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                root.modCount=++modCount;
                this.modCount=modCount;
                this.cursor=cursor+1;
            }
        }
        static abstract class AbstractSubList<E> implements OmniList.OfRef<E>{
            static void bubbleUpIncrementSize(AbstractSubList<?> parent){
                while(parent!=null){
                    ++parent.size;
                    ++parent.modCount;
                    parent=parent.parent;
                }
            }
            transient final Checked<E> root;
            transient final AbstractSubList<?> parent;
            transient final int rootOffset;
            transient int size;
            transient int modCount;
            AbstractSubList(Checked<E> root,AbstractSubList<E> parent,int rootOffset,int size){
                this.root=root;
                this.parent=parent;
                this.rootOffset=rootOffset;
                this.size=size;
                this.modCount=root.modCount;
            }
            @Override
            public boolean add(E val){
                int modCount=this.modCount;
                final var root=this.root;
                CheckedCollection.checkModCount(modCount,root.modCount);
                final int size=this.size;
                int rootSize;
                if((rootSize=root.size)!=0){
                    root.uncheckedInsert(this.rootOffset+size,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                root.modCount=++modCount;
                this.modCount=modCount;
                bubbleUpIncrementSize(parent);
                this.size=size+1;
                return true;
            }
            @Override
            public void add(int index,E val){
                int modCount=this.modCount;
                final Checked<E> root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                CheckedCollection.checkLo(index);
                int size;
                CheckedCollection.checkWriteHi(index,size=this.size);
                int rootSize;
                if((rootSize=root.size)!=0){
                    root.uncheckedInsert(index+rootOffset,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                root.modCount=++modCount;
                this.modCount=modCount;
                bubbleUpIncrementSize(parent);
                this.size=size+1;
            }
            // private static void bubbleUpDecrementSize(AbstractSubList<?> parent,int
            // numToRemove){
            // while(parent!=null){
            // parent.size-=numToRemove;
            // parent=parent.parent;
            // ++parent.modCount;
            // }
            // }
            // @Override
            // @SuppressWarnings("unchecked")
            // public boolean removeIf(Predicate<? super E> filter){
            // final var root=this.root;
            // int expectedModCount=modCount;
            // int size;
            // if((size=this.size)!=0){
            // final var arr=root.arr;
            // int srcOffset;
            // final int srcBound=(srcOffset=rootOffset)+size;
            // try{
            // do{
            // if(filter.test((E)arr[srcOffset])){
            // int dstOffset=srcOffset;
            // while(++srcOffset!=srcBound){
            // final Object v;
            // if(!filter.test((E)(v=arr[srcOffset]))){
            // final long[] survivors;
            // int numSurvivors;
            // if((numSurvivors=size-++srcOffset)!=0){
            // numSurvivors=markSurvivors(arr,
            // survivors=BitSetUtils.getBitSet(numSurvivors),srcOffset,srcBound,
            // filter);
            // CheckedCollection.checkModCount(expectedModCount,root.modCount);
            // arr[dstOffset++]=v;
            // if(numSurvivors!=0){
            // dstOffset=pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,
            // numSurvivors);
            // }
            // }else{
            // CheckedCollection.checkModCount(expectedModCount,root.modCount);
            // arr[dstOffset++]=v;
            // }
            // root.modCount=++expectedModCount;
            // modCount=expectedModCount;
            // this.size=size-(size=finalizeSubListBatchRemove(root,arr,dstOffset,srcBound));
            // bubbleUpDecrementSize(parent,size);
            // return true;
            // }
            // }
            // CheckedCollection.checkModCount(expectedModCount,root.modCount);
            // root.modCount=++expectedModCount;
            // modCount=expectedModCount;
            // this.size=size-(size=finalizeSubListBatchRemove(root,arr,dstOffset,srcBound));
            // bubbleUpDecrementSize(parent,size);
            // return true;
            // }
            // }while(++srcOffset!=srcBound);
            // }catch(final RuntimeException e){
            // throw CheckedCollection.checkModCount(expectedModCount,root.modCount,e);
            // }
            // }
            // CheckedCollection.checkModCount(expectedModCount,root.modCount);
            // return false;
            // }
            static abstract class AbstractBidirectionalItr<E> implements OmniListIterator.OfRef<E>{
                transient final AbstractSubList<E> parent;
                transient int cursor;
                transient int lastRet;
                transient int modCount;
                AbstractBidirectionalItr(AbstractSubList<E> parent){
                    this.parent=parent;
                    this.cursor=parent.rootOffset;
                    this.modCount=parent.modCount;
                }
                AbstractBidirectionalItr(AbstractSubList<E> parent,int cursor){
                    this.parent=parent;
                    this.cursor=cursor;
                    this.modCount=parent.modCount;
                }
                @Override
                public void add(E val){
                    final var parent=this.parent;
                    final var root=parent.root;
                    int modCount;
                    CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
                    int rootSize;
                    final int cursor=this.cursor;
                    if((rootSize=root.size)!=0){
                        root.uncheckedInsert(cursor,val,rootSize);
                    }else{
                        root.uncheckedInit(val);
                    }
                    root.modCount=++modCount;
                    ++parent.size;
                    parent.modCount=modCount;
                    bubbleUpIncrementSize(parent.parent);
                    this.cursor=cursor+1;
                    this.modCount=modCount;
                    this.lastRet=-1;
                }
            }
        }
    }
    E uncheckedPop(int newSize){
        final var arr=this.arr;
        @SuppressWarnings("unchecked")
        final var polled=(E)arr[newSize];
        arr[newSize]=null;
        this.size=newSize;
        return polled;
    }
    static abstract class Unchecked<E>extends AbstractRefArrSeq<E>{
        @SuppressWarnings("unchecked")
        static <E> int pullSurvivorsDown(Object[] arr,int srcBegin,int srcEnd,Predicate<? super E> filter){
            int dstOffset=srcBegin;
            while(srcBegin!=srcEnd){
                Object v;
                if(!filter.test((E)(v=arr[++srcBegin]))){
                    arr[dstOffset++]=v;
                }
            }
            return dstOffset;
        }
        Unchecked(){
            super();
        }
        Unchecked(int capacity){
            super(capacity);
        }
        Unchecked(int size,Object[] arr){
            super(size,arr);
        }
        @Override
        public boolean add(E val){
            int size=this.size;
            if(size!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
            return true;
        }
        public void push(E val){
            int size=this.size;
            if(size!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
        }
        @SuppressWarnings("unchecked")
        @Override
        boolean uncheckedRemoveIf(int size,Predicate<? super E> filter){
            final var arr=this.arr;
            int srcOffset=0;
            do{
                if(filter.test((E)arr[srcOffset])){
                    OmniArray.OfRef.nullifyRange(arr,srcOffset=pullSurvivorsDown(arr,srcOffset,--size,filter),size);
                    this.size=srcOffset;
                    return true;
                }
            }while(++srcOffset!=size);
            return false;
        }
        private Object[] growInsert(Object[] arr,int index,int size){
            if(arr.length==size){
                ArrCopy.semicheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,index);
                this.arr=arr;
            }
            return arr;
        }
        private void uncheckedAppend(E val,int size){
            var arr=this.arr;
            if(arr.length==size){
                ArrCopy.uncheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,size);
                this.arr=arr;
            }
            arr[size]=val;
            this.size=size+1;
        }
        private void uncheckedInit(E val){
            var arr=this.arr;
            if(arr==OmniArray.OfRef.DEFAULT_ARR){
                this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
            }else if(arr==null){
                this.arr=new Object[1];
            }
            arr[0]=val;
            size=1;
        }
        private void uncheckedInsert(int index,E val,int size){
            int tailDist;
            if((tailDist=size-index)==0){
                uncheckedAppend(val,size);
            }else{
                Object[] arr;
                ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
                arr[index]=val;
                this.size=size+1;
            }
        }
        static abstract class AbstractBidirectionalItr<E> implements OmniListIterator.OfRef<E>{
            transient final Unchecked<E> root;
            transient int cursor;
            transient int lastRet;
            AbstractBidirectionalItr(Unchecked<E> root){
                this.root=root;
            }
            AbstractBidirectionalItr(Unchecked<E> root,int cursor){
                this.root=root;
                this.cursor=cursor;
            }
            @Override
            public void add(E val){
                final int cursor=this.cursor;
                final var root=this.root;
                int rootSize;
                if((rootSize=root.size)!=0){
                    root.uncheckedInsert(cursor,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                this.cursor=cursor+1;
            }
        }
        static abstract class AbstractSubList<E> implements OmniList.OfRef<E>{
            static void bubbleUpIncrementSize(AbstractSubList<?> subSeq){
                while(subSeq!=null){
                    ++subSeq.size;
                    subSeq=subSeq.parent;
                }
            }
            transient final Unchecked<E> root;
            transient final AbstractSubList<?> parent;
            transient final int rootOffset;
            transient int size;
            AbstractSubList(Unchecked<E> root,AbstractSubList<E> parent,int rootOffset,int size){
                this.root=root;
                this.parent=parent;
                this.rootOffset=rootOffset;
                this.size=size;
            }
            @Override
            public boolean add(E val){
                final int size=this.size;
                final var root=this.root;
                int rootSize;
                if((rootSize=root.size)!=0){
                    root.uncheckedInsert(this.rootOffset+size,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                bubbleUpIncrementSize(parent);
                this.size=size+1;
                return true;
            }
            @Override
            public void add(int index,E val){
                final var root=this.root;
                int rootSize;
                if((rootSize=root.size)!=0){
                    root.uncheckedInsert(index+rootOffset,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                bubbleUpIncrementSize(parent);
                ++size;
            }
            // private static void bubbleUpDecrementSize(AbstractSubList<?> parent,int
            // numToRemove){
            // while(parent!=null){
            // parent.size-=numToRemove;
            // parent=parent.parent;
            // }
            // }
            // @Override
            // @SuppressWarnings("unchecked")
            // public boolean removeIf(Predicate<? super E> filter){
            // int size;
            // if((size=this.size)!=0){
            // AbstractRefArrSeq<E> root;
            // final var arr=(root=this.root).arr;
            // int srcOffset;
            // final int srcBound=(srcOffset=rootOffset)+size;
            // do{
            // if(filter.test((E)arr[srcOffset])){
            // this.size=size-(size=finalizeSubListBatchRemove(root,arr,
            // pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),srcBound));
            // bubbleUpDecrementSize(parent,size);
            // return true;
            // }
            // }while(++srcOffset!=srcBound);
            // }
            // return false;
            // }
            static abstract class AbstractBidirectionalItr<E> implements OmniListIterator.OfRef<E>{
                transient final AbstractSubList<E> parent;
                transient int cursor;
                transient int lastRet;
                AbstractBidirectionalItr(AbstractSubList<E> parent){
                    this.parent=parent;
                    this.cursor=parent.rootOffset;
                }
                AbstractBidirectionalItr(AbstractSubList<E> parent,int cursor){
                    this.parent=parent;
                    this.cursor=cursor;
                }
                @Override
                public void add(E val){
                    final int cursor=this.cursor;
                    final var parent=this.parent;
                    Unchecked<E> root;
                    int rootSize;
                    if((rootSize=(root=parent.root).size)!=0){
                        root.uncheckedInsert(cursor,val,rootSize);
                    }else{
                        root.uncheckedInit(val);
                    }
                    ++parent.size;
                    bubbleUpIncrementSize(parent.parent);
                    this.cursor=cursor+1;
                }
            }
        }
    }
}
