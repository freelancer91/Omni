package omni.impl.seq.arr.ofref;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractRefList;
import omni.impl.seq.arr.AbstractSubArrSeq;
import omni.util.ArrCopy;
import omni.util.BitSetUtils;
import omni.util.OmniArray;
import omni.util.OmniPred;
abstract class AbstractSeq<E>extends AbstractRefList<E>{
    // TODO redo toString implementation
    static void eraseIndexHelper(Object[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
        arr[newSize]=null;
    }
    static int forwardHashCode(Object[] arr,int offset,int bound){
        int hash=31+Objects.hashCode(arr[offset]);
        while(++offset!=bound){
            hash=hash*31+Objects.hashCode(arr[offset]);
        }
        return hash;
    }
    static void forwardToString(Object[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    static int reverseHashCode(Object[] arr,int offset,int bound){
        int hash=31+Objects.hashCode(arr[--bound]);
        while(bound!=offset){
            hash=hash*31+Objects.hashCode(arr[--bound]);
        }
        return hash;
    }
    static void reverseToString(Object[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    static boolean uncheckedAnyMatches(Object[] arr,int offset,int bound,Predicate<Object> pred){
        while(!pred.test(arr[offset])){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    static <E> void uncheckedForwardForEachInRange(Object[] arr,int offset,int bound,Consumer<? super E> action){
        do{
            action.accept((E)arr[offset]);
        }while(++offset!=bound);
    }
    static int uncheckedIndexOf(Object[] arr,int offset,int bound,Predicate<Object> pred){
        int index=offset;
        do{
            if(pred.test(arr[index])){
                return index-offset;
            }
        }while(++index!=bound);
        return -1;
    }
    static int uncheckedIndexOf(Object[] arr,int size,Predicate<Object> pred){
        int index=0;
        do{
            if(pred.test(arr[index])){
                return index;
            }
        }while(++index!=size);
        return -1;
    }
    static int uncheckedLastIndexOf(Object[] arr,int offset,int bound,Predicate<Object> pred){
        do{
            if(pred.test(arr[--bound])){
                return bound-offset;
            }
        }while(bound!=offset);
        return -1;
    }
    @SuppressWarnings("unchecked")
    static <E> void uncheckedReplaceAll(Object[] arr,int offset,int bound,Function<? super E,? extends E> operator){
        do{
            arr[offset]=operator.apply((E)arr[offset]);
        }while(++offset!=bound);
    }
    @SuppressWarnings("unchecked")
    static <E> void uncheckedReverseForEachInRange(Object[] arr,int offset,int bound,Consumer<? super E> action){
        do{
            action.accept((E)arr[--bound]);
        }while(bound!=offset);
    }
    static int uncheckedSearch(Object[] arr,int size,Predicate<Object> pred){
        int index;
        for(index=size-1;!pred.test(arr[index]);--index){
            if(index==0){
                return -1;
            }
        }
        return size-index;
    }
    static <E> void uncheckedSort(Object[] arr,int begin,int end,Comparator<? super E> sorter){
        // TODO
    }
    transient Object[] arr;
    private AbstractSeq(){
        super();
        this.arr=OmniArray.OfRef.DEFAULT_ARR;
    }
    private AbstractSeq(int capacity){
        super();
        switch(capacity){
        default:
            this.arr=new Object[capacity];
            return;
        case OmniArray.DEFAULT_ARR_SEQ_CAP:
            this.arr=OmniArray.OfRef.DEFAULT_ARR;
        case 0:
        }
    }
    private AbstractSeq(int size,Object[] arr){
        super(size);
        this.arr=arr;
    }
    public boolean contains(boolean val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Boolean val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(byte val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Byte val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(char val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Character val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(double val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Double val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(float val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Float val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(int val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Integer val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(long val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Long val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(short val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Short val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    public void forEach(Consumer<? super E> action){
        final int size;
        if((size=this.size)!=0){
            uncheckedForEach(size,action);
        }
    }
    @SuppressWarnings("unchecked")
    public E get(int index){
        return (E)arr[index];
    }
    @Override
    public int hashCode(){
        final int size;
        if((size=this.size)!=0){
            return uncheckedHashCode(size);
        }
        return 1;
    }
    public int indexOf(boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(Boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(byte val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(Byte val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(char val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(Character val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(double val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(Double val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(float val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(Float val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(int val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(Integer val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(long val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(Long val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(short val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int indexOf(Short val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @SuppressWarnings("unchecked")
    public E peek(){
        final int size;
        if((size=this.size)!=0){
            return (E)arr[size-1];
        }
        return null;
    }
    public void push(E val){
        final int size;
        if((size=this.size)!=0){
            uncheckedAppend(val,size);
        }else{
            uncheckedInit(val);
        }
    }
    public void put(int index,E val){
        arr[index]=val;
    }
    public boolean remove(Object val){
        final int size;
        if((size=this.size)!=0){
            if(val!=null){
                return uncheckedRemoveFirstNonNull(size,val);
            }
            return uncheckedRemoveFirstMatch(size,Objects::isNull);
        }
        return false;
    }
    public boolean removeIf(Predicate<? super E> filter){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveIf(size,filter);
    }
    public boolean removeVal(boolean val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Boolean val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(byte val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Byte val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(char val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Character val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(double val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Double val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(float val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Float val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(int val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Integer val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(long val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Long val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(short val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Short val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
    }
    public int search(boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(byte val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Byte val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(char val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Character val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(double val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Double val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(float val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Float val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(int val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Integer val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(long val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Long val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(short val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Short val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedSearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public E set(int index,E val){
        final Object[] arr;
        @SuppressWarnings("unchecked")
        final var oldVal=(E)(arr=this.arr)[index];
        arr[index]=val;
        return oldVal;
    }
    public Object[] toArray(){
        final int size;
        if((size=this.size)!=0){
            final Object[] dst;
            uncheckedCopyInto(dst=new Object[size],size);
            return dst;
        }
        return OmniArray.OfRef.DEFAULT_ARR;
    }
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final int size;
        final T[] dst=arrConstructor.apply(size=this.size);
        if(size!=0){
            uncheckedCopyInto(dst,size);
        }
        return dst;
    }
    public <T> T[] toArray(T[] dst){
        final int size;
        if((size=this.size)!=0){
            uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),size);
        }else if(dst.length!=0){
            dst[0]=null;
        }
        return dst;
    }
    @Override
    public String toString(){
        final int size;
        if((size=this.size)!=0){
            final StringBuilder builder;
            uncheckedToString(size,builder=new StringBuilder("["));
            return builder.append(']').toString();
        }
        return "[]";
    }
    @Override
    protected int uncheckedLastIndexOfMatch(int size,Predicate<Object> pred){
        final var arr=this.arr;
        do{
            if(pred.test(arr[--size])){
                return size;
            }
        }while(size!=0);
        return -1;
    }
    @Override
    protected int uncheckedLastIndexOfNonNull(int size,Object nonNull){
        final var arr=this.arr;
        do{
            if(nonNull.equals(arr[--size])){
                return size;
            }
        }while(size!=0);
        return -1;
    }
    abstract void uncheckedCopyInto(Object[] dst,int length);
    abstract void uncheckedForEach(int size,Consumer<? super E> action);
    abstract int uncheckedHashCode(int size);
    E uncheckedPop(int newSize){
        size=newSize;
        final Object[] arr;
        @SuppressWarnings("unchecked")
        final var popped=(E)(arr=this.arr)[newSize];
        arr[newSize]=null;
        return popped;
    }
    abstract boolean uncheckedRemoveFirstMatch(int size,Predicate<Object> pred);
    abstract boolean uncheckedRemoveFirstNonNull(int size,Object nonNull);
    abstract boolean uncheckedRemoveIf(int size,Predicate<? super E> filter);
    abstract void uncheckedToString(int size,StringBuilder builder);
    private int finalizeSubListBatchRemove(Object[] arr,int newBound,int oldBound){
        final int rootSize,newRootSize,numRemoved;
        size=newRootSize=(rootSize=size)-(numRemoved=oldBound-newBound);
        ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
        OmniArray.OfRef.nullifyRange(arr,newRootSize,rootSize-1);
        return numRemoved;
    }
    private Object[] growInsert(Object[] arr,int index,int size){
        if(arr.length==size){
            ArrCopy.semicheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,index);
            this.arr=arr;
        }
        return arr;
    }
    private void uncheckedAppend(E val,int size){
        Object[] arr;
        if((arr=this.arr).length==size){
            ArrCopy.uncheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,size);
        }
        arr[size]=val;
        this.size=size+1;
    }
    private void uncheckedInit(E val){
        Object[] arr;
        if((arr=this.arr)==OmniArray.OfRef.DEFAULT_ARR){
            this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(arr==null){
            this.arr=arr=new Object[1];
        }
        arr[0]=val;
        size=1;
    }
    private void uncheckedInsert(int index,E val,int size){
        final int tailDist;
        if((tailDist=size-index)==0){
            uncheckedAppend(val,size);
        }else{
            Object[] arr;
            ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
            arr[index]=val;
            this.size=size+1;
        }
    }
    abstract static class Checked<E>extends AbstractSeq<E>{
        private @SuppressWarnings("unchecked")
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
        private static int pullSurvivorsDown(Object[] arr,long[] survivorSet,int dstOffset,int srcOffset,
                int numSurvivors){
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
        public boolean add(E val){
            ++modCount;
            super.push(val);
            return true;
        }
        public void add(int index,E val){
            CheckedCollection.checkLo(index);
            final int size;
            CheckedCollection.checkWriteHi(index,size=this.size);
            ++modCount;
            if(size!=0){
                super.uncheckedInsert(index,val,size);
            }else{
                super.uncheckedInit(val);
            }
        }
        @Override
        public void clear(){
            final int size;
            if((size=this.size)!=0){
                ++modCount;
                OmniArray.OfRef.nullifyRange(arr,0,size-1);
                this.size=0;
            }
        }
        public boolean contains(Object val){
            final int size;
            if((size=this.size)!=0){
                final var arr=this.arr;
                if(val!=null){
                    final int expectedModCount=modCount;
                    try{
                        return uncheckedAnyMatches(arr,0,size,val::equals);
                    }finally{
                        CheckedCollection.checkModCount(expectedModCount,modCount);
                    }
                }
                return uncheckedAnyMatches(arr,0,size,Objects::isNull);
            }
            return false;
        }
        @Override
        public E get(int index){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkWriteHi(index,size);
            return super.get(index);
        }
        @Override
        public void push(E val){
            ++modCount;
            super.push(val);
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
        protected int uncheckedLastIndexOfNonNull(int size,Object nonNull){
            final int modCount=this.modCount;
            try{
                return super.uncheckedLastIndexOfNonNull(size,nonNull);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        @SuppressWarnings("unchecked")
        boolean uncheckedRemoveIf(int size,Predicate<? super E> filter){
            final int expectedModCount=modCount;
            final var arr=this.arr;
            int srcOffset=0;
            try{
                do{
                    if(filter.test((E)arr[srcOffset])){
                        int dstOffset=srcOffset;
                        while(++srcOffset!=size){
                            final Object v;
                            if(!filter.test((E)(v=arr[srcOffset]))){
                                final long[] survivors;
                                int numSurvivors;
                                if((numSurvivors=size-++srcOffset)!=0){
                                    numSurvivors=markSurvivors(arr,survivors=BitSetUtils.getBitSet(numSurvivors),
                                            srcOffset,size,filter);
                                    CheckedCollection.checkModCount(expectedModCount,modCount);
                                    arr[dstOffset++]=v;
                                    if(numSurvivors!=0){
                                        dstOffset=pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,numSurvivors);
                                    }
                                }else{
                                    CheckedCollection.checkModCount(expectedModCount,modCount);
                                    arr[dstOffset++]=v;
                                }
                                OmniArray.OfRef.nullifyRange(arr,dstOffset,--size);
                                this.size=size;
                                return true;
                            }
                        }
                        CheckedCollection.checkModCount(expectedModCount,modCount);
                        OmniArray.OfRef.nullifyRange(arr,dstOffset,--size);
                        this.size=size;
                        return true;
                    }
                }while(++srcOffset!=size);
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
            }
            CheckedCollection.checkModCount(expectedModCount,modCount);
            return false;
        }
        static abstract class AbstractBidirectionalItr<E>{
            transient int lastRet=-1;
            transient final Checked<E> root;
            transient int cursor;
            transient int modCount;
            AbstractBidirectionalItr(Checked<E> root){
                this.root=root;
                this.modCount=root.modCount;
            }
            AbstractBidirectionalItr(Checked<E> root,int cursor){
                this.root=root;
                this.modCount=root.modCount;
                this.cursor=cursor;
            }
            public void add(E val){
                final Checked<E> root;
                int modCount;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                final int rootSize,cursor=this.cursor;
                if((rootSize=root.size)!=0){
                    ((AbstractSeq<E>)root).uncheckedInsert(cursor,val,rootSize);
                }else{
                    ((AbstractSeq<E>)root).uncheckedInit(val);
                }
                root.modCount=++modCount;
                this.modCount=modCount;
                this.cursor=cursor+1;
                this.lastRet=-1;
            }
        }
        static abstract class AbstractSubList<E>extends AbstractSubArrSeq{
            private static void bubbleUpDecrementSize(Checked.AbstractSubList<?> parent,int numToRemove){
                while(parent!=null){
                    parent.size-=numToRemove;
                    ++parent.modCount;
                    parent=parent.parent;
                }
            }
            private static void bubbleUpIncrementSize(Checked.AbstractSubList<?> parent){
                while(parent!=null){
                    ++parent.size;
                    ++parent.modCount;
                    parent=parent.parent;
                }
            }
            transient final Checked<E> root;
            transient final Checked.AbstractSubList<E> parent;
            transient int modCount;
            AbstractSubList(Checked<E> root,Checked.AbstractSubList<E> parent,int rootOffset,int size,int modCount){
                super(rootOffset,size);
                this.root=root;
                this.parent=parent;
                this.modCount=modCount;
            }
            AbstractSubList(Checked<E> root,int rootOffset,int size){
                super(rootOffset,size);
                this.root=root;
                this.parent=null;
                this.modCount=root.modCount;
            }
            public boolean add(E val){
                final Checked<E> root;
                int modCount;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                final int rootSize;
                final int size=this.size;
                if((rootSize=root.size)!=0){
                    ((AbstractSeq<E>)root).uncheckedInsert(rootOffset+size,val,rootSize);
                }else{
                    ((AbstractSeq<E>)root).uncheckedInit(val);
                }
                root.modCount=++modCount;
                this.modCount=modCount;
                bubbleUpIncrementSize(parent);
                this.size=size+1;
                return true;
            }
            public void add(int index,E val){
                final Checked<E> root;
                int modCount;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                CheckedCollection.checkLo(index);
                final int size;
                CheckedCollection.checkWriteHi(index,size=this.size);
                final int rootSize;
                if((rootSize=root.size)!=0){
                    ((AbstractSeq<E>)root).uncheckedInsert(rootOffset+index,val,rootSize);
                }else{
                    ((AbstractSeq<E>)root).uncheckedInit(val);
                }
                root.modCount=++modCount;
                this.modCount=modCount;
                bubbleUpIncrementSize(parent);
                this.size=size+1;
            }
            @Override
            public void clear(){
                final var root=this.root;
                final int modCount=this.modCount;
                final int size;
                if((size=this.size)!=0){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    this.modCount=modCount+1;
                    this.size=0;
                    bubbleUpDecrementSize(parent,size);
                    final int oldRootSize,newRootSize;
                    root.size=newRootSize=(oldRootSize=root.size)-size;
                    final Object[] arr;
                    final int rootOffset;
                    ArrCopy.semicheckedCopy(arr=root.arr,(rootOffset=this.rootOffset)+size,arr,rootOffset,
                            newRootSize-rootOffset);
                    OmniArray.OfRef.nullifyRange(arr,newRootSize,oldRootSize-1);
                }else{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }
            @Override
            public boolean isEmpty(){
                CheckedCollection.checkModCount(this.modCount,root.modCount);
                return size==0;
            }
            @SuppressWarnings("unchecked")
            public boolean removeIf(Predicate<? super E> filter){
                final var root=this.root;
                int expectedModCount=modCount;
                int size;
                if((size=this.size)!=0){
                    final var arr=root.arr;
                    int srcOffset;
                    final int srcBound=(srcOffset=rootOffset)+size;
                    try{
                        do{
                            if(filter.test((E)arr[srcOffset])){
                                int dstOffset=srcOffset;
                                while(++srcOffset!=srcBound){
                                    final Object v;
                                    if(!filter.test((E)(v=arr[srcOffset]))){
                                        final long[] survivors;
                                        int numSurvivors;
                                        if((numSurvivors=size-++srcOffset)!=0){
                                            numSurvivors=markSurvivors(arr,
                                                    survivors=BitSetUtils.getBitSet(numSurvivors),srcOffset,srcBound,
                                                    filter);
                                            CheckedCollection.checkModCount(expectedModCount,root.modCount);
                                            arr[dstOffset++]=v;
                                            if(numSurvivors!=0){
                                                dstOffset=pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,
                                                        numSurvivors);
                                            }
                                        }else{
                                            CheckedCollection.checkModCount(expectedModCount,root.modCount);
                                            arr[dstOffset++]=v;
                                        }
                                        root.modCount=++expectedModCount;
                                        modCount=expectedModCount;
                                        this.size=size-(size=((AbstractSeq<E>)root).finalizeSubListBatchRemove(arr,
                                                dstOffset,srcBound));
                                        bubbleUpDecrementSize(parent,size);
                                        return true;
                                    }
                                }
                                CheckedCollection.checkModCount(expectedModCount,root.modCount);
                                root.modCount=++expectedModCount;
                                modCount=expectedModCount;
                                this.size=size-(size=((AbstractSeq<E>)root).finalizeSubListBatchRemove(arr,dstOffset,
                                        srcBound));
                                bubbleUpDecrementSize(parent,size);
                                return true;
                            }
                        }while(++srcOffset!=srcBound);
                    }catch(final RuntimeException e){
                        throw CheckedCollection.checkModCount(expectedModCount,root.modCount,e);
                    }
                }
                CheckedCollection.checkModCount(expectedModCount,root.modCount);
                return false;
            }
            @Override
            public int size(){
                CheckedCollection.checkModCount(this.modCount,root.modCount);
                return size;
            }
            static abstract class AbstractBidirectionalItr<E>{
                transient final Checked.AbstractSubList<E> parent;
                transient int cursor;
                transient int lastRet=-1;
                transient int modCount;
                AbstractBidirectionalItr(Checked.AbstractSubList<E> parent,int modCount){
                    this.parent=parent;
                    this.cursor=parent.rootOffset;
                    this.modCount=modCount;
                }
                AbstractBidirectionalItr(Checked.AbstractSubList<E> parent,int modCount,int cursor){
                    this.parent=parent;
                    this.cursor=cursor;
                    this.modCount=modCount;
                }
                public void add(E val){
                    final Checked.AbstractSubList<E> parent;
                    final Checked<E> root;
                    int modCount;
                    CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
                    final int rootSize;
                    final int cursor=this.cursor;
                    if((rootSize=root.size)!=0){
                        ((AbstractSeq<E>)root).uncheckedInsert(cursor,val,rootSize);
                    }else{
                        ((AbstractSeq<E>)root).uncheckedInit(val);
                    }
                    bubbleUpIncrementSize(parent.parent);
                    parent.modCount=++modCount;
                    root.modCount=modCount;
                    ++parent.size;
                    this.cursor=cursor+1;
                    this.lastRet=-1;
                }
            }
        }
    }
    static abstract class Unchecked<E>extends AbstractSeq<E>{
        @SuppressWarnings("unchecked")
        private static <E> int pullSurvivorsDown(Object[] arr,int srcBegin,int srcEnd,Predicate<? super E> filter){
            int dstOffset=srcBegin;
            while(srcBegin!=srcEnd){
                final Object v;
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
        public boolean add(E val){
            super.push(val);
            return true;
        }
        public void add(int index,E val){
            final int size;
            if((size=this.size)!=0){
                super.uncheckedInsert(index,val,size);
            }else{
                super.uncheckedInit(val);
            }
        }
        @Override
        public void clear(){
            final int size;
            if((size=this.size)!=0){
                OmniArray.OfRef.nullifyRange(arr,0,size-1);
                this.size=0;
            }
        }
        public boolean contains(Object val){
            final int size;
            return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
        }
        @Override
        @SuppressWarnings("unchecked")
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
        static abstract class AbstractBidirectionalItr<E>{
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
            public void add(E val){
                final AbstractSeq<E> root;
                final int rootSize,cursor=this.cursor;
                if((rootSize=(root=this.root).size)!=0){
                    root.uncheckedInsert(cursor,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                this.cursor=cursor+1;
            }
        }
        static abstract class AbstractSubList<E>extends AbstractSubArrSeq{
            private static void bubbleUpDecrementSize(AbstractSubList<?> parent,int numToRemove){
                while(parent!=null){
                    parent.size-=numToRemove;
                    parent=parent.parent;
                }
            }
            private static void bubbleUpIncrementSize(AbstractSubList<?> parent){
                while(parent!=null){
                    ++parent.size;
                    parent=parent.parent;
                }
            }
            transient final Unchecked<E> root;
            transient final AbstractSubList<E> parent;
            AbstractSubList(Unchecked<E> root,AbstractSubList<E> parent,int rootOffset,int size){
                super(rootOffset,size);
                this.root=root;
                this.parent=parent;
            }
            AbstractSubList(Unchecked<E> root,int rootOffset,int size){
                super(rootOffset,size);
                this.root=root;
                this.parent=null;
            }
            public boolean add(E val){
                final AbstractSeq<E> root;
                final int rootSize;
                final int size=this.size;
                if((rootSize=(root=this.root).size)!=0){
                    root.uncheckedInsert(size+rootOffset,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                this.size=size+1;
                bubbleUpIncrementSize(parent);
                return true;
            }
            public void add(int index,E val){
                final AbstractSeq<E> root;
                final int rootSize;
                if((rootSize=(root=this.root).size)!=0){
                    root.uncheckedInsert(index+rootOffset,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                ++size;
                bubbleUpIncrementSize(parent);
            }
            @Override
            public void clear(){
                int size;
                if((size=this.size)!=0){
                    this.size=0;
                    bubbleUpDecrementSize(parent,size);
                    final int oldRootSize,newRootSize;
                    final Unchecked<E> root;
                    (root=this.root).size=newRootSize=(oldRootSize=root.size)-size;
                    final Object[] arr;
                    ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
                    OmniArray.OfRef.nullifyRange(arr,newRootSize,oldRootSize-1);
                }
            }
            @SuppressWarnings("unchecked")
            public boolean removeIf(Predicate<? super E> filter){
                int size;
                if((size=this.size)!=0){
                    final AbstractSeq<E> root;
                    final var arr=(root=this.root).arr;
                    int srcOffset;
                    final int srcBound=(srcOffset=rootOffset)+size;
                    do{
                        if(filter.test((E)arr[srcOffset])){
                            this.size=size-(size=root.finalizeSubListBatchRemove(arr,
                                    pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),srcBound));
                            bubbleUpDecrementSize(parent,size);
                            return true;
                        }
                    }while(++srcOffset!=srcBound);
                }
                return false;
            }
            static abstract class AbstractBidirectionalItr<E>{
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
                public void add(E val){
                    final AbstractSubList<E> parent;
                    final AbstractSeq<E> root;
                    final int rootSize,cursor=this.cursor;
                    if((rootSize=(root=(parent=this.parent).root).size)!=0){
                        root.uncheckedInsert(cursor,val,rootSize);
                    }else{
                        root.uncheckedInit(val);
                    }
                    bubbleUpIncrementSize(parent.parent);
                    ++parent.size;
                    this.cursor=cursor+1;
                }
            }
        }
    }
}
