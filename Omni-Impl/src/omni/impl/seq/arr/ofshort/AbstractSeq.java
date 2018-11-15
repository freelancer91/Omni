package omni.impl.seq.arr.ofshort;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.function.ShortComparator;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import omni.function.ShortUnaryOperator;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractShortList;
import omni.util.ArrCopy;
import omni.util.BitSetUtils;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
import omni.util.TypeUtil;
abstract class AbstractSeq extends AbstractShortList{
    transient short[] arr;
    // TODO redo toString implementation
    static void eraseIndexHelper(short[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
    }
    static int forwardHashCode(short[] arr,int offset,int bound){
        int hash=31+arr[offset];
        while(++offset!=bound){
            hash=hash*31+arr[offset];
        }
        return hash;
    }
    static int forwardToString(short[] arr,int begin,int end,char[] buffer,int bufferOffset){
        for(;;buffer[bufferOffset++]=',',buffer[bufferOffset++]=' ',++begin){
            bufferOffset=ToStringUtil.getStringShort(arr[begin],buffer,bufferOffset);
            if(begin==end){ return bufferOffset; }
        }
    }
    static void forwardToString(short[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    static void forwardToString(short[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder){
        for(;;++begin){
            builder.uncheckedAppendShort(arr[begin]);
            if(begin==end){ return; }
            builder.uncheckedAppendCommaAndSpace();
        }
    }
    static int reverseHashCode(short[] arr,int offset,int bound){
        int hash=31+arr[--bound];
        while(bound!=offset){
            hash=hash*31+arr[--bound];
        }
        return hash;
    }
    static int reverseToString(short[] arr,int begin,int end,char[] buffer,int bufferOffset){
        for(;;buffer[bufferOffset++]=',',buffer[bufferOffset++]=' ',--end){
            bufferOffset=ToStringUtil.getStringShort(arr[end],buffer,bufferOffset);
            if(end==begin){ return bufferOffset; }
        }
    }
    static void reverseToString(short[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    static void reverseToString(short[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder){
        for(;;--end){
            builder.uncheckedAppendShort(arr[end]);
            if(end==begin){ return; }
            builder.uncheckedAppendCommaAndSpace();
        }
    }
    static boolean uncheckedAnyMatches(short[] arr,int offset,int bound,int val){
        while(arr[offset]!=val){
            if(++offset==bound){ return false; }
        }
        return true;
    }
    static void uncheckedForwardForEachInRange(short[] arr,int offset,int bound,ShortConsumer action){
        do{
            action.accept(arr[offset]);
        }while(++offset!=bound);
    }
    static int uncheckedIndexOfMatch(short[] arr,int offset,int bound,int val){
        int index=offset;
        do{
            if(arr[index]==val){ return index-offset; }
        }while(++index!=bound);
        return -1;
    }
    static int uncheckedLastIndexOfMatch(short[] arr,int offset,int bound,int val){
        do{
            if(arr[--bound]==val){ return bound-offset; }
        }while(bound!=offset);
        return -1;
    }
    static void uncheckedReplaceAll(short[] arr,int offset,int bound,ShortUnaryOperator operator){
        do{
            arr[offset]=operator.applyAsShort(arr[offset]);
        }while(++offset!=bound);
    }
    static void uncheckedReverseArr(short[] arr,int begin,int end){
        do{
            final var tmp=arr[begin];
            arr[begin]=arr[end];
            arr[end]=tmp;
        }while(++begin<--end);
    }
    static void uncheckedReverseForEachInRange(short[] arr,int offset,int bound,ShortConsumer action){
        do{
            action.accept(arr[--bound]);
        }while(bound!=offset);
    }
    static void uncheckedReverseSort(short[] arr,int begin,int end){
        // TODO
    }
    static void uncheckedSort(short[] arr,int begin,int end){
        // TODO
    }
    static void uncheckedSort(short[] arr,int begin,int end,ShortComparator sorter){
        // TODO
    }
    private AbstractSeq(){
        super();
        arr=OmniArray.OfShort.DEFAULT_ARR;
    }
    private AbstractSeq(int capacity){
        super();
        switch(capacity){
        default:
            arr=new short[capacity];
            return;
        case OmniArray.DEFAULT_ARR_SEQ_CAP:
            arr=OmniArray.OfShort.DEFAULT_ARR;
        case 0:
        }
    }
    private AbstractSeq(int size,short[] arr){
        super(size);
        this.arr=arr;
    }
    public boolean contains(boolean val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,TypeUtil.castToByte(val));
    }
    public boolean contains(char val){
        final int size;
        return val<=Short.MAX_VALUE&&(size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,val);
    }
    public boolean contains(double val){
        final int size,v;
        return (size=this.size)!=0&&val==(v=(short)val)&&uncheckedAnyMatches(arr,0,size,v);
    }
    public boolean contains(float val){
        final int size,v;
        return (size=this.size)!=0&&val==(v=(short)val)&&uncheckedAnyMatches(arr,0,size,v);
    }
    public boolean contains(int val){
        final int size;
        return (size=this.size)!=0&&val==(short)val&&uncheckedAnyMatches(arr,0,size,val);
    }
    public boolean contains(long val){
        final int size,v;
        return (size=this.size)!=0&&val==(v=(short)val)&&uncheckedAnyMatches(arr,0,size,v);
    }
    public boolean contains(Object val){
        final int size;
        return (size=this.size)!=0&&val instanceof Short&&uncheckedAnyMatches(arr,0,size,(short)val);
    }
    @Override public boolean contains(short val){
        final int size;
        return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,val);
    }
    public void forEach(Consumer<? super Short> action){
        final int size;
        if((size=this.size)!=0){
            uncheckedForEach(size,action::accept);
        }
    }
    public void forEach(ShortConsumer action){
        final int size;
        if((size=this.size)!=0){
            uncheckedForEach(size,action);
        }
    }
    @Override public short getShort(int index){
        return arr[index];
    }
    @Override public int hashCode(){
        final int size;
        if((size=this.size)!=0){ return uncheckedHashCode(size); }
        return 1;
    }
    public int indexOf(boolean val){
        final int size;
        if((size=this.size)!=0){ return uncheckedIndexOfMatch(size,TypeUtil.castToByte(val)); }
        return -1;
    }
    public int indexOf(char val){
        final int size;
        if(val<=Short.MAX_VALUE&&(size=this.size)!=0){ return uncheckedIndexOfMatch(size,val); }
        return -1;
    }
    public int indexOf(double val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedIndexOfMatch(size,v); }
        return -1;
    }
    public int indexOf(float val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedIndexOfMatch(size,v); }
        return -1;
    }
    public int indexOf(int val){
        final int size;
        if((size=this.size)!=0&&val==(short)val){ return uncheckedIndexOfMatch(size,val); }
        return -1;
    }
    public int indexOf(long val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedIndexOfMatch(size,v); }
        return -1;
    }
    public int indexOf(Object val){
        final int size;
        if((size=this.size)!=0&&val instanceof Short){ return uncheckedIndexOfMatch(size,(short)val); }
        return -1;
    }
    public int indexOf(short val){
        final int size;
        if((size=this.size)!=0){ return uncheckedIndexOfMatch(size,val); }
        return -1;
    }
    public Short peek(){
        final int size;
        if((size=this.size)!=0){ return arr[size-1]; }
        return null;
    }
    public double peekDouble(){
        final int size;
        if((size=this.size)!=0){ return arr[size-1]; }
        return Double.NaN;
    }
    public float peekFloat(){
        final int size;
        if((size=this.size)!=0){ return arr[size-1]; }
        return Float.NaN;
    }
    public int peekInt(){
        final int size;
        if((size=this.size)!=0){ return arr[size-1]; }
        return Integer.MIN_VALUE;
    }
    public long peekLong(){
        final int size;
        if((size=this.size)!=0){ return arr[size-1]; }
        return Long.MIN_VALUE;
    }
    public short peekShort(){
        final int size;
        if((size=this.size)!=0){ return arr[size-1]; }
        return Short.MIN_VALUE;
    }
    public void push(short val){
        final int size;
        if((size=this.size)!=0){
            uncheckedAppend(val,size);
        }else{
            uncheckedInit(val);
        }
    }
    public void put(int index,short val){
        arr[index]=val;
    }
    public boolean remove(Object val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,(short)val);
    }
    public boolean removeIf(Predicate<? super Short> filter){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveIf(size,filter::test);
    }
    public boolean removeIf(ShortPredicate filter){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveIf(size,filter);
    }
    public boolean removeVal(boolean val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,TypeUtil.castToByte(val));
    }
    public boolean removeVal(char val){
        final int size;
        return val<=Short.MAX_VALUE&&(size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
    }
    public boolean removeVal(double val){
        final int size,v;
        return (size=this.size)!=0&&(v=(short)val)==val&&uncheckedRemoveFirstMatch(size,v);
    }
    public boolean removeVal(float val){
        final int size,v;
        return (size=this.size)!=0&&(v=(short)val)==val&&uncheckedRemoveFirstMatch(size,v);
    }
    public boolean removeVal(int val){
        final int size;
        return (size=this.size)!=0&&val==(short)val&&uncheckedRemoveFirstMatch(size,val);
    }
    public boolean removeVal(long val){
        final int size,v;
        return (size=this.size)!=0&&(v=(short)val)==val&&uncheckedRemoveFirstMatch(size,v);
    }
    @Override public boolean removeVal(short val){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
    }
    public void replaceAll(ShortUnaryOperator operator){
        final int size;
        if((size=this.size)!=0){
            uncheckedReplaceAll(size,operator);
        }
    }
    public void replaceAll(UnaryOperator<Short> operator){
        final int size;
        if((size=this.size)!=0){
            uncheckedReplaceAll(size,operator::apply);
        }
    }
    public int search(boolean val){
        final int size;
        if((size=this.size)!=0){ return uncheckedSearch(size,TypeUtil.castToByte(val)); }
        return -1;
    }
    public int search(char val){
        final int size;
        if(val<=Short.MAX_VALUE&&(size=this.size)!=0){ return uncheckedSearch(size,val); }
        return -1;
    }
    public int search(double val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedSearch(size,v); }
        return -1;
    }
    public int search(float val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedSearch(size,v); }
        return -1;
    }
    public int search(int val){
        final int size;
        if((size=this.size)!=0&&val==(short)val){ return uncheckedSearch(size,val); }
        return -1;
    }
    public int search(long val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedSearch(size,v); }
        return -1;
    }
    public int search(Object val){
        final int size;
        if((size=this.size)!=0&&val instanceof Short){ return uncheckedSearch(size,(short)val); }
        return -1;
    }
    public int search(short val){
        final int size;
        if((size=this.size)!=0){ return uncheckedSearch(size,val); }
        return -1;
    }
    @Override public short set(int index,short val){
        final short[] arr;
        final var oldVal=(arr=this.arr)[index];
        arr[index]=val;
        return oldVal;
    }
    public void sort(Comparator<? super Short> sorter){
        final int size;
        if((size=this.size)>1){
            uncheckedSort(size,sorter::compare);
        }
    }
    public void sort(ShortComparator sorter){
        final int size;
        if((size=this.size)>1){
            uncheckedSort(size,sorter);
        }
    }
    public Short[] toArray(){
        final int size;
        if((size=this.size)!=0){
            final Short[] dst;
            uncheckedCopyInto(dst=new Short[size],size);
            return dst;
        }
        return OmniArray.OfShort.DEFAULT_BOXED_ARR;
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
    public double[] toDoubleArray(){
        final int size;
        if((size=this.size)!=0){
            final double[] dst;
            uncheckedCopyInto(dst=new double[size],size);
            return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
    }
    public float[] toFloatArray(){
        final int size;
        if((size=this.size)!=0){
            final float[] dst;
            uncheckedCopyInto(dst=new float[size],size);
            return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
    }
    public int[] toIntArray(){
        final int size;
        if((size=this.size)!=0){
            final int[] dst;
            uncheckedCopyInto(dst=new int[size],size);
            return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
    }
    public long[] toLongArray(){
        final int size;
        if((size=this.size)!=0){
            final long[] dst;
            uncheckedCopyInto(dst=new long[size],size);
            return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
    }
    public short[] toShortArray(){
        final int size;
        if((size=this.size)!=0){
            final short[] dst;
            uncheckedCopyInto(dst=new short[size],size);
            return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public String toString(){
        final int size;
        if((size=this.size)!=0){
            final StringBuilder builder;
            uncheckedToString(size,builder=new StringBuilder("["));
            return builder.append(']').toString();
        }
        return "[]";
    }
    @Override protected int uncheckedLastIndexOfMatch(int size,int val){
        final var arr=this.arr;
        do{
            if(arr[--size]==val){ return size; }
        }while(size!=0);
        return -1;
    }
    abstract void uncheckedCopyInto(double[] dst,int length);
    abstract void uncheckedCopyInto(float[] dst,int length);
    abstract void uncheckedCopyInto(int[] dst,int length);
    abstract void uncheckedCopyInto(long[] dst,int length);
    abstract void uncheckedCopyInto(Object[] dst,int length);
    abstract void uncheckedCopyInto(short[] dst,int length);
    abstract void uncheckedCopyInto(Short[] dst,int length);
    abstract void uncheckedForEach(int size,ShortConsumer action);
    abstract int uncheckedHashCode(int size);
    short uncheckedPopShort(int newSize){
        size=newSize;
        return arr[newSize];
    }
    abstract boolean uncheckedRemoveFirstMatch(int size,int val);
    abstract boolean uncheckedRemoveIf(int size,ShortPredicate filter);
    abstract void uncheckedReplaceAll(int size,ShortUnaryOperator operator);
    abstract void uncheckedSort(int size,ShortComparator sorter);
    abstract void uncheckedToString(int size,StringBuilder builder);
    private int finalizeSubListBatchRemove(short[] arr,int newBound,int oldBound){
        final int newRootSize,numRemoved;
        size=newRootSize=size-(numRemoved=oldBound-newBound);
        ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
        return numRemoved;
    }
    private short[] growInsert(short[] arr,int index,int size){
        if(arr.length==size){
            ArrCopy.semicheckedCopy(arr,0,arr=new short[OmniArray.growBy50Pct(size)],0,index);
            this.arr=arr;
        }
        return arr;
    }
    private void uncheckedAppend(short val,int size){
        short[] arr;
        if((arr=this.arr).length==size){
            ArrCopy.uncheckedCopy(arr,0,arr=new short[OmniArray.growBy50Pct(size)],0,size);
        }
        arr[size]=val;
        this.size=size+1;
    }
    private int uncheckedIndexOfMatch(int size,int val){
        final var arr=this.arr;
        int index=0;
        do{
            if(arr[index]==val){ return index; }
        }while(++index!=size);
        return -1;
    }
    private void uncheckedInit(short val){
        short[] arr;
        if((arr=this.arr)==OmniArray.OfShort.DEFAULT_ARR){
            this.arr=arr=new short[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(arr==null){
            this.arr=arr=new short[1];
        }
        arr[0]=val;
        size=1;
    }
    private void uncheckedInsert(int index,short val,int size){
        final int tailDist;
        if((tailDist=size-index)==0){
            uncheckedAppend(val,size);
        }else{
            short[] arr;
            ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
            arr[index]=val;
            this.size=size+1;
        }
    }
    private int uncheckedSearch(int size,int val){
        final var arr=this.arr;
        int index;
        for(index=size-1;arr[index]!=val;--index){
            if(index==0){ return -1; }
        }
        return size-index;
    }
    static abstract class Checked extends AbstractSeq{
        transient int modCount;
        private static int markSurvivors(short[] arr,long[] survivorSet,int offset,int bound,ShortPredicate filter){
            for(int numSurvivors=0,wordOffset=0;;){
                long word=0L,marker=1L;
                do{
                    if(!filter.test(arr[offset])){
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
        private static int pullSurvivorsDown(short[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
            for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
                long survivorWord;
                int runLength;
                if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
                    int wordSrcOffset=srcOffset;
                    do{
                        ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                                runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
                        dstOffset+=runLength;
                        if((numSurvivors-=runLength)==0){ return dstOffset; }
                        wordSrcOffset+=runLength;
                    }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
                }
            }
        }
        Checked(){
            super();
        }
        Checked(int capacity){
            super(capacity);
        }
        Checked(int size,short[] arr){
            super(size,arr);
        }
        public boolean add(boolean val){
            ++modCount;
            super.push(TypeUtil.castToByte(val));
            return true;
        }
        @Override public void add(int index,short val){
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
        public boolean add(short val){
            ++modCount;
            super.push(val);
            return true;
        }
        public boolean add(Short val){
            ++modCount;
            super.push(val);
            return true;
        }
        @Override public void clear(){
            if(size!=0){
                ++modCount;
                size=0;
            }
        }
        @Override public short getShort(int index){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
            return super.getShort(index);
        }
        public short popShort(){
            final int size;
            if((size=this.size)!=0){
                ++modCount;
                return super.uncheckedPopShort(size-1);
            }
            throw new NoSuchElementException();
        }
        @Override public void push(short val){
            ++modCount;
            super.push(val);
        }
        @Override public void put(int index,short val){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
            super.put(index,val);
        }
        @Override public short removeShortAt(int index){
            CheckedCollection.checkLo(index);
            int size;
            CheckedCollection.checkReadHi(index,size=this.size);
            ++modCount;
            final short[] arr;
            final var removed=(arr=this.arr)[index];
            eraseIndexHelper(arr,index,--size);
            this.size=size;
            return removed;
        }
        @Override public short set(int index,short val){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
            return super.set(index,val);
        }
        @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            return super.toArray(size->{
                final int expectedModCount=modCount;
                try{
                    return arrConstructor.apply(size);
                }finally{
                    CheckedCollection.checkModCount(expectedModCount,modCount);
                }
            });
        }
        @Override boolean uncheckedRemoveIf(int size,ShortPredicate filter){
            final int expectedModCount=modCount;
            final var arr=this.arr;
            int srcOffset=0;
            try{
                do{
                    if(filter.test(arr[srcOffset])){
                        int dstOffset=srcOffset;
                        while(++srcOffset!=size){
                            final short v;
                            if(!filter.test(v=arr[srcOffset])){
                                final long[] survivors;
                                int numSurvivors;
                                if((numSurvivors=size-++srcOffset)!=0){
                                    numSurvivors=markSurvivors(arr,survivors=BitSetUtils.getBitSet(numSurvivors),srcOffset,size,filter);
                                    CheckedCollection.checkModCount(expectedModCount,modCount);
                                    arr[dstOffset++]=v;
                                    if(numSurvivors!=0){
                                        dstOffset=pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,numSurvivors);
                                    }
                                }else{
                                    CheckedCollection.checkModCount(expectedModCount,modCount);
                                    arr[dstOffset++]=v;
                                }
                                this.size=size-1;
                                return true;
                            }
                        }
                        CheckedCollection.checkModCount(expectedModCount,modCount);
                        this.size=size-1;
                        return true;
                    }
                }while(++srcOffset!=size);
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
            }
            CheckedCollection.checkModCount(expectedModCount,modCount);
            return false;
        }
        @Override void uncheckedReplaceAll(int size,ShortUnaryOperator operator){
            final int expectedModCount=modCount;
            try{
                uncheckedReplaceAll(arr,0,size,operator);
                CheckedCollection.checkModCount(expectedModCount,modCount);
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
            }
            modCount=expectedModCount+1;
        }
        @Override void uncheckedSort(int size,ShortComparator sorter){
            final int expectedModCount=modCount;
            try{
                uncheckedSort(arr,0,size-1,sorter);
                CheckedCollection.checkModCount(expectedModCount,modCount);
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
            }
            modCount=expectedModCount+1;
        }
        static abstract class AbstractBidirectionalItr{
            transient int lastRet=-1;
            transient final Checked root;
            transient int cursor;
            transient int modCount;
            AbstractBidirectionalItr(Checked root){
                this.root=root;
                modCount=root.modCount;
            }
            AbstractBidirectionalItr(Checked root,int cursor){
                this.root=root;
                modCount=root.modCount;
                this.cursor=cursor;
            }
            public void add(short val){
                final Checked root;
                int modCount;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                final int rootSize,cursor=this.cursor;
                if((rootSize=root.size)!=0){
                    ((AbstractSeq)root).uncheckedInsert(cursor,val,rootSize);
                }else{
                    ((AbstractSeq)root).uncheckedInit(val);
                }
                root.modCount=++modCount;
                this.modCount=modCount;
                this.cursor=cursor+1;
                lastRet=-1;
            }
        }
        static abstract class AbstractSubList extends AbstractShortList{
            @Override protected int uncheckedLastIndexOfMatch(int size,int val){
                Checked root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                int rootOffset;
                return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
            }
            transient final Checked root;
            transient final AbstractSubList parent;
            transient int modCount;
            static void bubbleUpDecrementSize(AbstractSubList parent){
                while(parent!=null){
                    --parent.size;
                    ++parent.modCount;
                    parent=parent.parent;
                }
            }
            private static void bubbleUpDecrementSize(AbstractSubList parent,int numToRemove){
                while(parent!=null){
                    parent.size-=numToRemove;
                    ++parent.modCount;
                    parent=parent.parent;
                }
            }
            private static void bubbleUpIncrementSize(AbstractSubList parent){
                while(parent!=null){
                    ++parent.size;
                    ++parent.modCount;
                    parent=parent.parent;
                }
            }
            transient final int rootOffset;
            AbstractSubList(Checked root,AbstractSubList parent,int rootOffset,int size,int modCount){
                super(size);
                this.rootOffset=rootOffset;
                this.root=root;
                this.parent=parent;
                this.modCount=modCount;
            }
            AbstractSubList(Checked root,int rootOffset,int size){
                super(size);
                this.rootOffset=rootOffset;
                this.root=root;
                parent=null;
                modCount=root.modCount;
            }
            @Override public void add(int index,short val){
                final Checked root;
                int modCount;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                CheckedCollection.checkLo(index);
                final int size;
                CheckedCollection.checkWriteHi(index,size=this.size);
                final int rootSize;
                if((rootSize=root.size)!=0){
                    ((AbstractSeq)root).uncheckedInsert(rootOffset+index,val,rootSize);
                }else{
                    ((AbstractSeq)root).uncheckedInit(val);
                }
                root.modCount=++modCount;
                this.modCount=modCount;
                bubbleUpIncrementSize(parent);
                this.size=size+1;
            }
            public boolean add(short val){
                final Checked root;
                int modCount;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                final int rootSize;
                final int size=this.size;
                if((rootSize=root.size)!=0){
                    ((AbstractSeq)root).uncheckedInsert(rootOffset+size,val,rootSize);
                }else{
                    ((AbstractSeq)root).uncheckedInit(val);
                }
                root.modCount=++modCount;
                this.modCount=modCount;
                bubbleUpIncrementSize(parent);
                this.size=size+1;
                return true;
            }
            @Override public void clear(){
                final var root=this.root;
                final int modCount=this.modCount;
                final int size;
                if((size=this.size)!=0){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    this.modCount=modCount+1;
                    this.size=0;
                    bubbleUpDecrementSize(parent,size);
                    final int newRootSize;
                    root.size=newRootSize=root.size-size;
                    final short[] arr;
                    final int rootOffset;
                    ArrCopy.semicheckedCopy(arr=root.arr,(rootOffset=this.rootOffset)+size,arr,rootOffset,newRootSize-rootOffset);
                }else{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }
            @Override public short getShort(int index){
                final Checked root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                CheckedCollection.checkLo(index);
                CheckedCollection.checkReadHi(index,size);
                return root.arr[index+rootOffset];
            }
            @Override public boolean isEmpty(){
                CheckedCollection.checkModCount(modCount,root.modCount);
                return size==0;
            }
            public boolean removeIf(Predicate<? super Short> filter){
                final int size;
                if((size=this.size)!=0){ return uncheckedRemoveIf(size,filter::test); }
                CheckedCollection.checkModCount(modCount,root.modCount);
                return false;
            }
            public boolean removeIf(ShortPredicate filter){
                final int size;
                if((size=this.size)!=0){ return uncheckedRemoveIf(size,filter); }
                CheckedCollection.checkModCount(modCount,root.modCount);
                return false;
            }
            @Override public short removeShortAt(int index){
                int modCount;
                final Checked root;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                CheckedCollection.checkLo(index);
                final int size;
                CheckedCollection.checkReadHi(index,size=this.size);
                final short[] arr;
                final var removed=(arr=root.arr)[index+=rootOffset];
                eraseIndexHelper(arr,index,--root.size);
                bubbleUpDecrementSize(parent);
                this.modCount=++modCount;
                root.modCount=modCount;
                this.size=size-1;
                return removed;
            }
            @Override public short set(int index,short val){
                final Checked root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                CheckedCollection.checkLo(index);
                CheckedCollection.checkReadHi(index,size);
                final short[] arr;
                final var oldVal=(arr=root.arr)[index+=rootOffset];
                arr[index]=val;
                return oldVal;
            }
            @Override public int size(){
                CheckedCollection.checkModCount(modCount,root.modCount);
                return size;
            }
            private boolean uncheckedRemoveIf(int size,ShortPredicate filter){
                final Checked root;
                int expectedModCount=modCount;
                final var arr=(root=this.root).arr;
                int srcOffset;
                final int srcBound=(srcOffset=rootOffset)+size;
                try{
                    do{
                        if(filter.test(arr[srcOffset])){
                            int dstOffset=srcOffset;
                            while(++srcOffset!=srcBound){
                                final short v;
                                if(!filter.test(v=arr[srcOffset])){
                                    final long[] survivors;
                                    int numSurvivors;
                                    if((numSurvivors=size-++srcOffset)!=0){
                                        numSurvivors=markSurvivors(arr,survivors=BitSetUtils.getBitSet(numSurvivors),srcOffset,srcBound,
                                                filter);
                                        CheckedCollection.checkModCount(expectedModCount,root.modCount);
                                        arr[dstOffset++]=v;
                                        if(numSurvivors!=0){
                                            dstOffset=pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,numSurvivors);
                                        }
                                    }else{
                                        CheckedCollection.checkModCount(expectedModCount,root.modCount);
                                        arr[dstOffset++]=v;
                                    }
                                    root.modCount=++expectedModCount;
                                    modCount=expectedModCount;
                                    this.size=size-(size=((AbstractSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
                                    bubbleUpDecrementSize(parent,size);
                                    return true;
                                }
                            }
                            CheckedCollection.checkModCount(expectedModCount,root.modCount);
                            root.modCount=++expectedModCount;
                            modCount=expectedModCount;
                            this.size=size-(size=((AbstractSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
                            bubbleUpDecrementSize(parent,size);
                            return true;
                        }
                    }while(++srcOffset!=srcBound);
                }catch(final RuntimeException e){
                    throw CheckedCollection.checkModCount(expectedModCount,root.modCount,e);
                }
                CheckedCollection.checkModCount(expectedModCount,root.modCount);
                return false;
            }
            static abstract class AbstractBidirectionalItr{
                transient final AbstractSubList parent;
                transient int cursor;
                transient int lastRet=-1;
                transient int modCount;
                AbstractBidirectionalItr(AbstractSubList parent,int modCount){
                    this.parent=parent;
                    cursor=parent.rootOffset;
                    this.modCount=modCount;
                }
                AbstractBidirectionalItr(AbstractSubList parent,int modCount,int cursor){
                    this.parent=parent;
                    this.cursor=cursor;
                    this.modCount=modCount;
                }
                public void add(short val){
                    final AbstractSubList parent;
                    final Checked root;
                    int modCount;
                    CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
                    final int rootSize;
                    final int cursor=this.cursor;
                    if((rootSize=root.size)!=0){
                        ((AbstractSeq)root).uncheckedInsert(cursor,val,rootSize);
                    }else{
                        ((AbstractSeq)root).uncheckedInit(val);
                    }
                    bubbleUpIncrementSize(parent.parent);
                    parent.modCount=++modCount;
                    root.modCount=modCount;
                    ++parent.size;
                    this.cursor=cursor+1;
                    lastRet=-1;
                }
            }
        }
    }
    static abstract class Unchecked extends AbstractSeq{
        private static int pullSurvivorsDown(short[] arr,int srcBegin,int srcEnd,ShortPredicate filter){
            int dstOffset=srcBegin;
            while(srcBegin!=srcEnd){
                final short v;
                if(!filter.test(v=arr[++srcBegin])){
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
        Unchecked(int size,short[] arr){
            super(size,arr);
        }
        public boolean add(boolean val){
            super.push(TypeUtil.castToByte(val));
            return true;
        }
        @Override public void add(int index,short val){
            final int size;
            if((size=this.size)!=0){
                super.uncheckedInsert(index,val,size);
            }else{
                super.uncheckedInit(val);
            }
        }
        public boolean add(short val){
            super.push(val);
            return true;
        }
        public boolean add(Short val){
            super.push(val);
            return true;
        }
        @Override public short removeShortAt(int index){
            final short[] arr;
            final var removed=(arr=this.arr)[index];
            eraseIndexHelper(arr,index,--size);
            return removed;
        }
        @Override boolean uncheckedRemoveIf(int size,ShortPredicate filter){
            final var arr=this.arr;
            int srcOffset=0;
            do{
                if(filter.test(arr[srcOffset])){
                    this.size=srcOffset=pullSurvivorsDown(arr,srcOffset,size-1,filter);
                    return true;
                }
            }while(++srcOffset!=size);
            return false;
        }
        @Override void uncheckedReplaceAll(int size,ShortUnaryOperator operator){
            uncheckedReplaceAll(arr,0,size,operator);
        }
        @Override void uncheckedSort(int size,ShortComparator sorter){
            uncheckedSort(arr,0,size-1,sorter);
        }
        static abstract class AbstractBidirectionalItr{
            transient final Unchecked root;
            transient int cursor;
            transient int lastRet;
            AbstractBidirectionalItr(Unchecked root){
                this.root=root;
            }
            AbstractBidirectionalItr(Unchecked root,int cursor){
                this.root=root;
                this.cursor=cursor;
            }
            public void add(short val){
                final AbstractSeq root;
                final int rootSize,cursor=this.cursor;
                if((rootSize=(root=this.root).size)!=0){
                    root.uncheckedInsert(cursor,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                this.cursor=cursor+1;
            }
        }
        static abstract class AbstractSubList extends AbstractShortList{
            @Override protected int uncheckedLastIndexOfMatch(int size,int val){
                int rootOffset;
                return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
            }
            transient final Unchecked root;
            transient final AbstractSubList parent;
            static void bubbleUpDecrementSize(AbstractSubList parent){
                while(parent!=null){
                    --parent.size;
                    parent=parent.parent;
                }
            }
            private static void bubbleUpDecrementSize(AbstractSubList parent,int numToRemove){
                while(parent!=null){
                    parent.size-=numToRemove;
                    parent=parent.parent;
                }
            }
            private static void bubbleUpIncrementSize(AbstractSubList parent){
                while(parent!=null){
                    ++parent.size;
                    parent=parent.parent;
                }
            }
            transient final int rootOffset;

            AbstractSubList(Unchecked root,AbstractSubList parent,int rootOffset,int size){
                super(size);
                this.rootOffset=rootOffset;
                this.root=root;
                this.parent=parent;
            }
            AbstractSubList(Unchecked root,int rootOffset,int size){
                super(size);
                this.rootOffset=rootOffset;
                this.root=root;
                parent=null;
            }
            @Override public void add(int index,short val){
                final AbstractSeq root;
                final int rootSize;
                if((rootSize=(root=this.root).size)!=0){
                    root.uncheckedInsert(index+rootOffset,val,rootSize);
                }else{
                    root.uncheckedInit(val);
                }
                ++size;
                bubbleUpIncrementSize(parent);
            }
            public boolean add(short val){
                final AbstractSeq root;
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
            @Override public void clear(){
                int size;
                if((size=this.size)!=0){
                    this.size=0;
                    bubbleUpDecrementSize(parent,size);
                    final int newRootSize;
                    final Unchecked root;
                    (root=this.root).size=newRootSize=root.size-size;
                    final short[] arr;
                    ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
                }
            }
            @Override public short getShort(int index){
                return root.arr[index+rootOffset];
            }
            public void put(int index,short val){
                root.arr[index+rootOffset]=val;
            }
            public boolean removeIf(Predicate<? super Short> filter){
                final int size;
                return (size=this.size)!=0&&uncheckedRemoveIf(size,filter::test);
            }
            public boolean removeIf(ShortPredicate filter){
                final int size;
                return (size=this.size)!=0&&uncheckedRemoveIf(size,filter);
            }
            @Override public short removeShortAt(int index){
                final Unchecked root;
                final short[] arr;
                final var removed=(arr=(root=this.root).arr)[index+=rootOffset];
                eraseIndexHelper(arr,index,--root.size);
                bubbleUpDecrementSize(parent);
                --size;
                return removed;
            }
            @Override public short set(int index,short val){
                final short[] arr;
                final var oldVal=(arr=root.arr)[index+=rootOffset];
                arr[index]=val;
                return oldVal;
            }
            private boolean uncheckedRemoveIf(int size,ShortPredicate filter){
                final AbstractSeq root;
                final var arr=(root=this.root).arr;
                int srcOffset;
                final int srcBound=(srcOffset=rootOffset)+size;
                do{
                    if(filter.test(arr[srcOffset])){
                        this.size=size-(size=root.finalizeSubListBatchRemove(arr,pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),
                                srcBound));
                        bubbleUpDecrementSize(parent,size);
                        return true;
                    }
                }while(++srcOffset!=srcBound);
                return false;
            }
            static abstract class AbstractBidirectionalItr{
                transient final AbstractSubList parent;
                transient int cursor;
                transient int lastRet;
                AbstractBidirectionalItr(AbstractSubList parent){
                    this.parent=parent;
                    cursor=parent.rootOffset;
                }
                AbstractBidirectionalItr(AbstractSubList parent,int cursor){
                    this.parent=parent;
                    this.cursor=cursor;
                }
                public void add(short val){
                    final AbstractSubList parent;
                    final AbstractSeq root;
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
