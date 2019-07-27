package omni.impl.seq;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.api.OmniStack;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.AbstractBooleanItr;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
import omni.util.ToStringUtil.OmniStringBuilderByte;
import omni.util.TypeUtil;
public abstract class PackedBooleanArrSeq extends AbstractBooleanArrSeq implements OmniCollection.OfBoolean{
    private static final long serialVersionUID=1L;
    private static int getRemainingBitCountAndSet(final long[] words,int wordOffset,final int wordBound,
            final long valToSet){
        int bitCount=0;
        for(;;){
            words[wordOffset]=valToSet;
            if(++wordOffset == wordBound){
                return bitCount;
            }
            bitCount+=Long.bitCount(words[wordOffset]);
        }
    }
    private static long partialWordShiftDown(long word,int shift){
        long mask;
        return word & (mask=(1L << shift) - 1) | word >>> 1 & ~mask;
    }
    transient long[] words;
    PackedBooleanArrSeq(){
        super();
    }
    PackedBooleanArrSeq(int initialCapacity){
        super();
        if(initialCapacity > 64){
            words=new long[(initialCapacity - 1 >> 6) + 1];
        }
    }
    PackedBooleanArrSeq(int size,long[] words){
        super(size);
        this.words=words;
    }
    @Override
    public boolean popBoolean(){
        final int size;
        return (words[(size=--this.size) >> 6] & 1L << size) != 0;
    }
    @Override
    public void readExternal(ObjectInput in) throws IOException{
        int size;
        this.size=size=in.readInt();
        if(size != 0){
            long[] words;
            OmniArray.OfLong.readArray(words=new long[(size=size - 1 >> 6) + 1],0,size,in);
            this.words=words;
        }
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException{
        int size;
        out.writeInt(size=this.size);
        if(size != 0){
            OmniArray.OfLong.writeArray(words,0,size - 1 >> 6,out);
        }
    }
    @Override
    void uncheckedAppend(int size,boolean val){
        long[] words;
        int wordBound;
        if((words=this.words).length == (wordBound=size >> 6)){
            ArrCopy.uncheckedCopy(words,0,words=new long[OmniArray.growBy50Pct(wordBound)],0,wordBound);
            this.words=words;
        }
        if(val){
            words[wordBound]|=1L << size;
        }else{
            words[wordBound]&=~(1L << size);
        }
        this.size=size + 1;
    }
    @Override
    boolean uncheckedcontains(final int bound,final boolean val){
        final var words=this.words;
        final var wordBound=bound >> 6;
        final long finalWord;
        if(val){
            for(var wordOffset=0;wordOffset < wordBound;++wordOffset){
                if(words[wordOffset] != 0){
                    return true;
                }
            }
            finalWord=words[wordBound];
        }else{
            for(var wordOffset=0;wordOffset < wordBound;++wordOffset){
                if(words[wordOffset] != -1){
                    return true;
                }
            }
            finalWord=~words[wordBound];
        }
        return finalWord << -bound - 1 != 0;
    }
    @Override
    int uncheckedindexOf(int size,boolean val){
        final var words=this.words;
        final var wordBound=--size >> 6;
        final long finalWord;
        if(val){
            for(var wordOffset=0;wordOffset < wordBound;++wordOffset){
                final int tail0s;
                if((tail0s=Long.numberOfTrailingZeros(words[wordOffset])) != 64){
                    return tail0s + (wordOffset << 6);
                }
            }
            finalWord=words[wordBound];
        }else{
            for(var wordOffset=0;wordOffset < wordBound;++wordOffset){
                final int tail0s;
                if((tail0s=Long.numberOfTrailingZeros(~words[wordOffset])) != 64){
                    return tail0s + (wordOffset << 6);
                }
            }
            finalWord=~words[wordBound];
        }
        final int tail0s;
        if((tail0s=Long.numberOfTrailingZeros(finalWord)) > (size & 63)){
            return -1;
        }
        return tail0s + (wordBound << 6);
    }
    @Override
    void uncheckedInit(boolean val){
        long[] words;
        if((words=this.words) == null){
            this.words=new long[]{val?1L:0L};
        }else{
            words[0]=val?1L:0L;
        }
    }
    @Override
    int uncheckedlastIndexOf(final int size,final boolean val){
        final var words=this.words;
        final int bound;
        var wordBound=(bound=size - 1) >> 6;
        if(val){
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(words[wordBound] << -size)) != 64){
                return bound - lead0s;
            }
            while(--wordBound >= 0){
                if((lead0s=Long.numberOfLeadingZeros(words[wordBound])) != 64){
                    return (wordBound + 1 << 6) - lead0s;
                }
            }
        }else{
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(~(words[wordBound] << -size))) != 64){
                return bound - lead0s;
            }
            while(--wordBound >= 0){
                if((lead0s=Long.numberOfLeadingZeros(~words[wordBound])) != 64){
                    return (wordBound + 1 << 6) - lead0s;
                }
            }
        }
        return -1;
    }
    @Override
    int uncheckedRemoveIfImpl(final int size,final BooleanPredicate filter){
        final var words=this.words;
        final int wordBound;
        int bitCount;
        if((bitCount=Long.bitCount(words[wordBound=size - 1 >> 6] << -size)) == 0){
            final var removeFalse=filter.test(false);
            for(int wordOffset=0;wordOffset < wordBound;++wordOffset){
                if((bitCount+=Long.bitCount(words[wordOffset])) != 0){
                    if(filter.test(true)){
                        if(removeFalse){
                            // remove all
                            return size;
                        }
                        // remove only true
                        return bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,0L);
                    }else{
                        if(removeFalse){
                            // remove only false
                            return size - (bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,-1L));
                        }
                        // remove none
                        return 0;
                    }
                }
            }
            // only false was encountered
            return removeFalse?size:0;
        }else if(bitCount == 64 - (-size & 63)){
            final var removeTrue=filter.test(true);
            for(int wordOffset=0;wordOffset < wordBound;++wordOffset){
                final int numTrue;
                bitCount+=numTrue=Long.bitCount(words[wordOffset]);
                if(numTrue != 64){
                    if(filter.test(false)){
                        if(removeTrue){
                            // remove all
                            return size;
                        }
                        /// remove only false
                        return size - (bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,-1L));
                    }else{
                        if(removeTrue){
                            // remove only true
                            return bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,0L);
                        }
                        // remove none
                        return 0;
                    }
                }
            }
            // only true was encountered
            return removeTrue?bitCount - size:0;
        }else{
            if(filter.test(true)){
                if(filter.test(false)){
                    // remove all
                    return size;
                }else{
                    // remove only true
                    return bitCount + getRemainingBitCountAndSet(words,0,wordBound,0L);
                }
            }else{
                if(filter.test(false)){
                    // remove only false
                    return size - (bitCount + getRemainingBitCountAndSet(words,0,wordBound,-1L));
                }else{
                    // remove none
                    return 0;
                }
            }
        }
    }
    @Override
    int uncheckedsearch(final int size,final boolean val){
        final var words=this.words;
        var wordBound=size - 1 >> 6;
        if(val){
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(words[wordBound] << -size)) != 64){
                return lead0s + 1;
            }
            while(--wordBound >= 0){
                if((lead0s=Long.numberOfLeadingZeros(words[wordBound])) != 64){
                    return size - ((wordBound + 1 << 6) - lead0s);
                }
            }
        }else{
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(~(words[wordBound] << -size))) != 64){
                return lead0s + 1;
            }
            while(--wordBound >= 0){
                if((lead0s=Long.numberOfLeadingZeros(~words[wordBound])) != 64){
                    return size - ((wordBound + 1 << 6) - lead0s);
                }
            }
        }
        return -1;
    }
    private boolean uncheckedcontains(int offset,int size,boolean val){
        // TODO can this be streamlined?
        final var words=this.words;
        final int bound;
        int wordOffset;
        int wordBound;
        if((wordOffset=offset >> 6) == (wordBound=(bound=offset + size - 1) >> 6)){
            return ((val?words[wordOffset]:~words[wordOffset]) << -bound)
                    + Long.MIN_VALUE >= (1L << (offset & 63) + (-bound & 63)) + Long.MIN_VALUE;
        }
        final long finalWord;
        if(val){
            if((offset & 63) != 0 && words[wordOffset++] >>> offset != 0){
                return true;
            }
            while(wordOffset != wordBound){
                if(words[wordOffset] != 0){
                    return true;
                }
                ++wordOffset;
            }
            finalWord=words[wordBound];
        }else{
            if((offset & 63) != 0 && (words[wordOffset++] | (1L << offset) - 1) != -1L){
                return true;
            }
            while(wordOffset != wordBound){
                if(words[wordOffset] != -1L){
                    return true;
                }
                ++wordOffset;
            }
            finalWord=~words[wordBound];
        }
        return finalWord << -bound - 1 != 0;
    }
    private int uncheckedindexOf(int offset,int size,boolean val){
        // TODO can this be streamlined?
        final var words=this.words;
        final int bound,wordBound;
        int wordOffset;
        if((wordOffset=offset >> 6) == (wordBound=(bound=offset + size - 1) >> 6)){
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros((val?words[wordOffset]:~words[wordOffset]) >> offset)) < size){
                return tail0s;
            }
            return -1;
        }
        final long finalWord;
        int i,firstWord0s=offset & 63;
        if(val){
            if(firstWord0s != 0){
                int index;
                if((index=Long.numberOfTrailingZeros(words[wordOffset++] >>> offset)) != 64){
                    return index;
                }
                firstWord0s=64 - firstWord0s;
            }
            for(i=wordOffset;i < wordBound;++i){
                final int tail0s;
                if((tail0s=Long.numberOfTrailingZeros(words[wordOffset])) != 64){
                    return tail0s + (i - wordOffset << 6) + firstWord0s;
                }
            }
            finalWord=words[wordBound];
        }else{
            if(firstWord0s != 0){
                int index;
                if((index=Long.numberOfTrailingZeros(~words[wordOffset++] >> offset)) != 64){
                    return index;
                }
                firstWord0s=64 - firstWord0s;
            }
            for(i=wordOffset;i < wordBound;++i){
                final int tail0s;
                if((tail0s=Long.numberOfTrailingZeros(~words[wordOffset])) != 64){
                    return tail0s + (i - wordOffset << 6) + firstWord0s;
                }
            }
            finalWord=~words[wordBound];
        }
        final int tail0s;
        if((tail0s=Long.numberOfTrailingZeros(finalWord)) > (bound & 63)){
            return -1;
        }
        return tail0s + (i - wordOffset << 6) + firstWord0s;
    }
    private int uncheckedlastIndexOf(final int offset,int size,boolean val){
        // TODO can this be streamlined?
        final var words=this.words;
        final int bound,wordBound;
        int wordOffset;
        if((wordOffset=offset >> 6) == (wordBound=(bound=offset + size - 1) >> 6)){
            int tail0s;
            if((tail0s=Long.numberOfLeadingZeros((val?words[wordOffset]:~words[wordOffset]) << -bound)) < size){
                return size - tail0s;
            }
            return -1;
        }
//        final var words=this.words;
//        var wordOffset=offset>>6;
//        var wordBound=(bound-1)>>6;
//        int firstWord0s;
//        if(val) {
//
//            if((firstWord0s=(bound&63))!=0) {
//                int index;
//                if((index=Long.numberOfLeadingZeros(words[wordBound])<<-bound)!=64) {
//                    return bound-index-offset;
//                }
//            }
//        }else {
//
//        }
//
        // TODO
        return -1;
    }
    private int uncheckedRemoveIfImpl(int size,BooleanPredicate filter,
            CheckedCollection.AbstractModCountChecker modCountChecker){
        final var words=this.words;
        final int wordBound;
        int bitCount;
        if((bitCount=Long.bitCount(words[wordBound=size - 1 >> 6] << -size)) == 0){
            final var removeFalse=filter.test(false);
            for(int wordOffset=0;wordOffset < wordBound;++wordOffset){
                if((bitCount+=Long.bitCount(words[wordOffset])) != 0){
                    final var removeTrue=filter.test(true);
                    modCountChecker.checkModCount();
                    if(removeTrue){
                        if(removeFalse){
                            // remove all
                            return size;
                        }
                        // remove only true
                        return bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,0L);
                    }else{
                        if(removeFalse){
                            // remove only false
                            return size - (bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,-1L));
                        }
                        // remove none
                        return 0;
                    }
                }
            }
            modCountChecker.checkModCount();
            // only false was encountered
            return removeFalse?size:0;
        }else if(bitCount == 64 - (-size & 63)){
            final var removeTrue=filter.test(true);
            for(int wordOffset=0;wordOffset < wordBound;++wordOffset){
                final int numTrue;
                bitCount+=numTrue=Long.bitCount(words[wordOffset]);
                if(numTrue != 64){
                    final var removeFalse=filter.test(false);
                    modCountChecker.checkModCount();
                    if(removeFalse){
                        if(removeTrue){
                            // remove all
                            return size;
                        }
                        /// remove only false
                        return size - (bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,-1L));
                    }else{
                        if(removeTrue){
                            // remove only true
                            return bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,0L);
                        }
                        // remove none
                        return 0;
                    }
                }
            }
            modCountChecker.checkModCount();
            // only true was encountered
            return removeTrue?bitCount - size:0;
        }else{
            final var removeTrue=filter.test(true);
            final var removeFalse=filter.test(false);
            modCountChecker.checkModCount();
            if(removeTrue){
                if(removeFalse){
                    // remove all
                    return size;
                }else{
                    // remove only true
                    return bitCount + getRemainingBitCountAndSet(words,0,wordBound,0L);
                }
            }else{
                if(removeFalse){
                    // remove only false
                    return size - (bitCount + getRemainingBitCountAndSet(words,0,wordBound,-1L));
                }else{
                    // remove none
                    return 0;
                }
            }
        }
    }
    private long uncheckedRemoveIndex(final int index,int bound){
        final long[] words;
        int wordOffset;
        final int wordBound;
        final long retWord;
        var word=(words=this.words)[wordOffset=index >> 6]=partialWordShiftDown(retWord=words[wordOffset],index);
        if(wordOffset == (wordBound=bound >> 6)){
            words[wordOffset]=word;
        }else{
            words[wordOffset]=word | (word=words[++wordOffset]) << 63;
            while(wordOffset != wordBound){
                words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
            }
            words[wordBound]=word >>> 1;
        }
        size=bound;
        return retWord;
    }
    private void uncheckedRemoveIndexNoRet(final int index){
        final long[] words;
        int wordOffset;
        final int wordBound,bound;
        var word=(words=this.words)[wordOffset=index >> 6]=partialWordShiftDown(words[wordOffset],index);
        if(wordOffset == (wordBound=(bound=size - 1) >> 6)){
            words[wordOffset]=word;
        }else{
            words[wordOffset]=word | (word=words[++wordOffset]) << 63;
            while(wordOffset != wordBound){
                words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
            }
            words[wordBound]=word >>> 1;
        }
        size=bound;
    }
    public static class CheckedList extends UncheckedList{
        private static final long serialVersionUID=1L;
        transient int modCount;
        public CheckedList(){
            super();
        }
        public CheckedList(int initialCapacity){
            super(initialCapacity);
        }
        CheckedList(int size,long[] words){
            super(size,words);
        }
        @Override
        public void add(int index,boolean val){
            final int size;
            CheckedCollection.checkLo(index);
            CheckedCollection.checkWriteHi(index,size=this.size);
            ++modCount;
            if(size != 0){
                ((UncheckedList)this).uncheckedInsert(index,size,val);
            }else{
                super.uncheckedInit(val);
            }
        }
        @Override
        public void clear(){
            if(size != 0){
                ++modCount;
                size=0;
            }
        }
        @Override
        public Object clone(){
            int size;
            long[] words;
            if((size=this.size) != 0){
                final int arrLength;
                ArrCopy.uncheckedCopy(this.words,0,words=new long[arrLength=(size - 1 >> 6) + 1],0,arrLength);
            }else{
                words=null;
            }
            return new CheckedList(size,words);
        }
        @Override
        public boolean equals(Object val){
            // TODO implements equals method for CheckedList
            return false;
        }
        @Override
        public boolean getBoolean(int index){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
            return super.getBoolean(index);
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            return new Itr(this);
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(){
            return new ListItr(this);
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(int index){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkWriteHi(index,size);
            return new ListItr(this,index);
        }
        @Override
        public void push(boolean val){
            ++modCount;
            super.push(val);
        }
        @Override
        public void put(int index,boolean val){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
            super.put(index,val);
        }
        @Override
        public boolean removeBooleanAt(int index){
            CheckedCollection.checkLo(index);
            int size;
            CheckedCollection.checkReadHi(index,size=this.size);
            ++modCount;
            return (((PackedBooleanArrSeq)this).uncheckedRemoveIndex(index,size - 1) & 1L << index) != 0;
        }
        @Override
        public void replaceAll(BooleanPredicate operator){
            final int size;
            if((size=this.size) != 0){
                final int modCount=this.modCount;
                try{
                    super.uncheckedReplaceAll(0,size,operator);
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                    this.modCount=modCount + 1;
                }
            }
        }
        @Override
        public void replaceAll(UnaryOperator<Boolean> operator){
            final int size;
            if((size=this.size) != 0){
                final int modCount=this.modCount;
                try{
                    super.uncheckedReplaceAll(0,size,operator::apply);
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                    this.modCount=modCount + 1;
                }
            }
        }
        @Override
        public boolean set(int index,boolean val){
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
            return super.set(index,val);
        }
        @Override
        public void sort(BooleanComparator sorter){
            final int size;
            if((size=this.size) > 1){
                if(sorter == null){
                    super.uncheckedStableAscendingSort(size);
                    ++modCount;
                }else{
                    final int modCount=this.modCount;
                    try{
                        super.uncheckedSort(size,sorter);
                    }catch(final ArrayIndexOutOfBoundsException e){
                        throw new IllegalArgumentException("Comparison method violates its general contract!",e);
                    }finally{
                        CheckedCollection.checkModCount(modCount,this.modCount);
                        this.modCount=modCount + 1;
                    }
                }
            }
        }
        @Override
        public void sort(Comparator<? super Boolean> sorter){
            final int size;
            if((size=this.size) > 1){
                if(sorter == null){
                    super.uncheckedStableAscendingSort(size);
                    ++modCount;
                }else{
                    final int modCount=this.modCount;
                    try{
                        super.uncheckedSort(size,sorter::compare);
                    }catch(final ArrayIndexOutOfBoundsException e){
                        throw new IllegalArgumentException("Comparison method violates its general contract!",e);
                    }finally{
                        CheckedCollection.checkModCount(modCount,this.modCount);
                        this.modCount=modCount + 1;
                    }
                }
            }
        }
        @Override
        public void stableAscendingSort(){
            final int size;
            if((size=this.size) > 1){
                super.uncheckedStableAscendingSort(size);
                ++modCount;
            }
        }
        @Override
        public void stableDescendingSort(){
            final int size;
            if((size=this.size) > 1){
                super.uncheckedStableDescendingSort(size);
                ++modCount;
            }
        }
        @Override
        public OmniList.OfBoolean subList(int fromIndex,int toIndex){
            return new CheckedSubList(this,fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,size));
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            return super.toArray(arrSize->{
                final int modCount=this.modCount;
                try{
                    return arrConstructor.apply(arrSize);
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                }
            });
        }
        @Override
        public void writeExternal(ObjectOutput out) throws IOException{
            final int modCount=this.modCount;
            try{
                super.writeExternal(out);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        void uncheckedForEach(int size,BooleanConsumer action){
            final int modCount=this.modCount;
            try{
                super.uncheckedForEach(size,action);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        boolean uncheckedRemoveIf(int size,BooleanPredicate filter){
            final int modCount=this.modCount;
            try{
                if(size != (size-=((PackedBooleanArrSeq)this).uncheckedRemoveIfImpl(size,filter,
                        new ModCountChecker(modCount)))){
                    this.modCount=modCount + 1;
                    this.size=size;
                    return true;
                }
                return false;
            }catch(final ConcurrentModificationException e){
                throw e;
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,this.modCount,e);
            }
        }
        @Override
        boolean uncheckedremoveVal(int size,boolean val){
            if(super.uncheckedremoveVal(size,val)){
                ++modCount;
                return true;
            }
            return false;
        }
        private static class Itr extends AbstractBooleanItr{
            transient final CheckedList parent;
            transient int cursor;
            transient int lastRet;
            transient int modCount;
            private Itr(CheckedList parent){
                this.parent=parent;
                cursor=0;
                modCount=parent.modCount;
                lastRet=-1;
            }
            private Itr(CheckedList parent,int cursor){
                this.parent=parent;
                this.cursor=cursor;
                modCount=parent.modCount;
                lastRet=-1;
            }
            private Itr(Itr itr){
                parent=itr.parent;
                cursor=itr.cursor;
                lastRet=itr.lastRet;
                modCount=itr.modCount;
            }
            @Override
            public Object clone(){
                return new Itr(this);
            }
            @Override
            public void forEachRemaining(BooleanConsumer action){
                final int cursor;
                final int bound;
                final CheckedList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).size)){
                    final int modCount=this.modCount;
                    final int lastRet;
                    try{
                        UncheckedList.uncheckedForEach(parent.words,cursor,lastRet=bound - 1,action);
                    }finally{
                        CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
                    }
                    this.cursor=bound;
                    this.lastRet=lastRet;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                final int cursor;
                final int bound;
                final CheckedList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).size)){
                    final int modCount=this.modCount;
                    final int lastRet;
                    try{
                        UncheckedList.uncheckedForEach(parent.words,cursor,lastRet=bound - 1,action::accept);
                    }finally{
                        CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
                    }
                    this.cursor=bound;
                    this.lastRet=lastRet;
                }
            }
            @Override
            public boolean hasNext(){
                return cursor < parent.size;
            }
            @Override
            public boolean nextBoolean(){
                final CheckedList root;
                CheckedCollection.checkModCount(modCount,(root=parent).modCount);
                final int cursor;
                if((cursor=this.cursor) < root.size){
                    lastRet=cursor;
                    this.cursor=cursor + 1;
                    return (root.words[cursor >> 6] & 1L << cursor) != 0;
                }
                throw new NoSuchElementException();
            }
            @Override
            public void remove(){
                final int lastRet;
                if((lastRet=this.lastRet) != -1){
                    int modCount;
                    final CheckedList root;
                    CheckedCollection.checkModCount(modCount=this.modCount,(root=parent).modCount);
                    root.modCount=++modCount;
                    this.modCount=modCount;
                    ((PackedBooleanArrSeq)root).uncheckedRemoveIndexNoRet(lastRet);
                    cursor=lastRet;
                    this.lastRet=-1;
                    return;
                }
                throw new IllegalStateException();
            }
        }
        private static class ListItr extends Itr implements OmniListIterator.OfBoolean{
            private ListItr(CheckedList parent){
                super(parent);
            }
            private ListItr(CheckedList parent,int cursor){
                super(parent,cursor);
            }
            private ListItr(ListItr itr){
                super(itr);
            }
            @Override
            public void add(boolean val){
                int modCount;
                final CheckedList root;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=parent).modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                lastRet=-1;
                final int rootSize;
                if((rootSize=root.size) != 0){
                    ((UncheckedList)root).uncheckedInsert(cursor++,rootSize,val);
                }else{
                    root.uncheckedInit(val);
                    ++cursor;
                }
            }
            @Override
            public Object clone(){
                return new ListItr(this);
            }
            @Override
            public boolean hasPrevious(){
                return cursor > 0;
            }
            @Override
            public int nextIndex(){
                return cursor;
            }
            @Override
            public boolean previousBoolean(){
                final CheckedList root;
                int cursor;
                CheckedCollection.checkModCount(modCount,(root=parent).modCount);
                if((cursor=this.cursor) > 0){
                    lastRet=--cursor;
                    this.cursor=cursor;
                    return (root.words[cursor >> 6] & 1L << cursor) != 0;
                }
                throw new NoSuchElementException();
            }
            @Override
            public int previousIndex(){
                return cursor - 1;
            }
            @Override
            public void set(boolean val){
                final int lastRet;
                if((lastRet=this.lastRet) != -1){
                    final CheckedList root;
                    CheckedCollection.checkModCount(modCount,(root=parent).modCount);
                    final long mask;
                    final long word;
                    final long[] words;
                    final int wordIndex;
                    if(((word=(words=root.words)[wordIndex=lastRet >> 6]) & (mask=1L << lastRet)) != 0){
                        words[wordIndex]=word | mask;
                    }else{
                        words[wordIndex]=word & ~mask;
                    }
                    return;
                }
                throw new IllegalStateException();
            }
        }
        private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
            ModCountChecker(int modCount){
                super(modCount);
            }
            @Override
            protected int getActualModCount(){
                return modCount;
            }
        }
    }
    public static class CheckedStack extends UncheckedStack{
        private static final long serialVersionUID=1L;
        transient int modCount;
        public CheckedStack(){
            super();
        }
        public CheckedStack(int initialCapacity){
            super(initialCapacity);
        }
        CheckedStack(int size,long[] words){
            super(size,words);
        }
        @Override
        public void clear(){
            if(size != 0){
                ++modCount;
                size=0;
            }
        }
        @Override
        public Object clone(){
            int size;
            long[] words;
            if((size=this.size) != 0){
                final int arrLength;
                ArrCopy.uncheckedCopy(this.words,0,words=new long[arrLength=(size - 1 >> 6) + 1],0,arrLength);
            }else{
                words=null;
            }
            return new CheckedStack(size,words);
        }
        @Override
        public boolean equals(Object val){
            // TODO implements equals method for CheckedStack
            return false;
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            return new Itr(this);
        }
        @Override
        public Boolean poll(){
            int size;
            if((size=this.size) != 0){
                ++modCount;
                this.size=--size;
                return (words[size >> 6] & 1L << size) != 0;
            }
            return null;
        }
        @Override
        public boolean pollBoolean(){
            int size;
            if((size=this.size) != 0){
                ++modCount;
                this.size=--size;
                return (words[size >> 6] & 1L << size) != 0;
            }
            return false;
        }
        @Override
        public byte pollByte(){
            int size;
            if((size=this.size) != 0){
                ++modCount;
                this.size=--size;
                return (byte)(words[size >> 6] >> size & 1);
            }
            return Byte.MIN_VALUE;
        }
        @Override
        public char pollChar(){
            int size;
            if((size=this.size) != 0){
                ++modCount;
                this.size=--size;
                return (char)(words[size >> 6] >> size & 1);
            }
            return Character.MIN_VALUE;
        }
        @Override
        public double pollDouble(){
            int size;
            if((size=this.size) != 0){
                ++modCount;
                this.size=--size;
                return words[size >> 6] >> size & 1;
            }
            return Double.NaN;
        }
        @Override
        public float pollFloat(){
            int size;
            if((size=this.size) != 0){
                ++modCount;
                this.size=--size;
                return words[size >> 6] >> size & 1;
            }
            return Float.NaN;
        }
        @Override
        public int pollInt(){
            int size;
            if((size=this.size) != 0){
                ++modCount;
                this.size=--size;
                return (int)(words[size >> 6] >> size & 1);
            }
            return Integer.MIN_VALUE;
        }
        @Override
        public long pollLong(){
            int size;
            if((size=this.size) != 0){
                ++modCount;
                this.size=--size;
                return words[size >> 6] >> size & 1;
            }
            return Long.MIN_VALUE;
        }
        @Override
        public short pollShort(){
            int size;
            if((size=this.size) != 0){
                ++modCount;
                this.size=--size;
                return (short)(words[size >> 6] >> size & 1);
            }
            return Short.MIN_VALUE;
        }
        @Override
        public boolean popBoolean(){
            int size;
            if((size=this.size) != 0){
                ++modCount;
                this.size=--size;
                return (words[size >> 6] & 1L << size) != 0;
            }
            throw new NoSuchElementException();
        }
        @Override
        public void push(boolean val){
            ++modCount;
            super.push(val);
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            return super.toArray(arrSize->{
                final int modCount=this.modCount;
                try{
                    return arrConstructor.apply(arrSize);
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                }
            });
        }
        @Override
        public void writeExternal(ObjectOutput out) throws IOException{
            final int modCount=this.modCount;
            try{
                super.writeExternal(out);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        void uncheckedForEach(int size,BooleanConsumer action){
            final int modCount=this.modCount;
            try{
                UncheckedStack.descendingForEach(words,size,action);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        boolean uncheckedRemoveIf(int size,BooleanPredicate filter){
            final int modCount=this.modCount;
            try{
                if(size != (size-=((PackedBooleanArrSeq)this).uncheckedRemoveIfImpl(size,filter,
                        new ModCountChecker(modCount)))){
                    this.modCount=modCount + 1;
                    this.size=size;
                    return true;
                }
                return false;
            }catch(final ConcurrentModificationException e){
                throw e;
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,this.modCount,e);
            }
        }
        @Override
        boolean uncheckedremoveVal(int size,boolean val){
            if(super.uncheckedremoveVal(size,val)){
                ++modCount;
                return true;
            }
            return false;
        }
        private static class Itr extends AbstractBooleanItr{
            transient final CheckedStack parent;
            transient int cursor;
            transient int lastRet;
            transient int modCount;
            private Itr(CheckedStack parent){
                this.parent=parent;
                cursor=parent.size;
                modCount=parent.modCount;
                lastRet=-1;
            }
            private Itr(Itr itr){
                parent=itr.parent;
                cursor=itr.cursor;
                lastRet=itr.lastRet;
                modCount=itr.modCount;
            }
            @Override
            public Object clone(){
                return new Itr(this);
            }
            @Override
            public void forEachRemaining(BooleanConsumer action){
                final int cursor;
                if((cursor=this.cursor) > 0){
                    final int modCount=this.modCount;
                    final var parent=this.parent;
                    try{
                        UncheckedStack.descendingForEach(parent.words,cursor,action);
                    }finally{
                        CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
                    }
                    this.cursor=0;
                    lastRet=0;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                final int cursor;
                if((cursor=this.cursor) > 0){
                    final int modCount=this.modCount;
                    final var parent=this.parent;
                    try{
                        UncheckedStack.descendingForEach(parent.words,cursor,action::accept);
                    }finally{
                        CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
                    }
                    this.cursor=0;
                    lastRet=0;
                }
            }
            @Override
            public boolean hasNext(){
                return cursor > 0;
            }
            @Override
            public boolean nextBoolean(){
                final CheckedStack root;
                CheckedCollection.checkModCount(modCount,(root=parent).modCount);
                int cursor;
                if((cursor=this.cursor) > 0){
                    lastRet=--cursor;
                    this.cursor=cursor;
                    return (root.words[cursor >> 6] & 1L << cursor) != 0;
                }
                throw new NoSuchElementException();
            }
            @Override
            public void remove(){
                final int lastRet;
                if((lastRet=this.lastRet) != -1){
                    int modCount;
                    final CheckedStack root;
                    CheckedCollection.checkModCount(modCount=this.modCount,(root=parent).modCount);
                    root.modCount=++modCount;
                    this.modCount=modCount;
                    ((PackedBooleanArrSeq)root).uncheckedRemoveIndexNoRet(lastRet);
                    this.lastRet=-1;
                    return;
                }
                throw new IllegalStateException();
            }
        }
        private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
            ModCountChecker(int modCount){
                super(modCount);
            }
            @Override
            protected int getActualModCount(){
                return modCount;
            }
        }
    }
    public static class CheckedSubList extends AbstractSeq<Boolean>
            implements
            BooleanSubListDefault,
            Cloneable,
            RandomAccess{
        private static final long serialVersionUID=1L;
        transient int modCount;
        transient final int rootOffset;
        transient final CheckedList root;
        transient final CheckedSubList parent;
        private CheckedSubList(CheckedList root,int rootOffset,int size){
            super(size);
            this.root=root;
            parent=null;
            this.rootOffset=rootOffset;
            modCount=root.modCount;
        }
        private CheckedSubList(CheckedSubList parent,int rootOffset,int size){
            super(size);
            root=parent.root;
            this.parent=parent;
            this.rootOffset=rootOffset;
            modCount=parent.modCount;
        }
        @Override
        public boolean add(boolean val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public void add(int index,boolean val){
            // TODO Auto-generated method stub
        }
        @Override
        public void clear(){
            // TODO Auto-generated method stub
        }
        @Override
        public Object clone(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final long[] copy;
            final int size;
            if((size=this.size) != 0){
                int copyLength;
                ArrCopy.uncheckedCopy(root.words,rootOffset,copy=new long[copyLength=(size - 1 >> 6) + 1],0,copyLength);
            }else{
                copy=null;
            }
            return new CheckedList(size,copy);
        }
        @Override
        public boolean contains(boolean val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        return ((PackedBooleanArrSeq)root).uncheckedcontains(rootOffset,size,val);
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return false;
        }
        @Override
        public boolean contains(double val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final boolean v;
                            long bits;
                            if(((bits=Double.doubleToRawLongBits(val)) & Long.MAX_VALUE) == 0){
                                v=false;
                            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                v=true;
                            }else{
                                break returnFalse;
                            }
                            return ((PackedBooleanArrSeq)root).uncheckedcontains(rootOffset,size,v);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return false;
        }
        @Override
        public boolean contains(float val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final boolean v;
                            switch(Float.floatToRawIntBits(val)){
                            default:
                                break returnFalse;
                            case 0:
                            case Integer.MIN_VALUE:
                                v=false;
                                break;
                            case TypeUtil.FLT_TRUE_BITS:
                                v=true;
                            }
                            return ((PackedBooleanArrSeq)root).uncheckedcontains(rootOffset,size,v);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return false;
        }
        @Override
        public boolean contains(int val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final boolean v;
                            switch(val){
                            default:
                                break returnFalse;
                            case 0:
                                v=false;
                                break;
                            case 1:
                                v=true;
                            }
                            return ((PackedBooleanArrSeq)root).uncheckedcontains(rootOffset,size,v);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return false;
        }
        @Override
        public boolean contains(long val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final boolean v;
                            if(val == 0L){
                                v=false;
                            }else if(val == 1L){
                                v=true;
                            }else{
                                break returnFalse;
                            }
                            return ((PackedBooleanArrSeq)root).uncheckedcontains(rootOffset,size,v);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return false;
        }
        @Override
        public boolean contains(Object val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        // TODO a pattern-matching switch statement would be great here
                        returnFalse:for(;;){
                            final boolean b;
                            if(val instanceof Boolean){
                                b=(boolean)val;
                            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                                switch(((Number)val).intValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    b=false;
                                    break;
                                case 1:
                                    b=true;
                                }
                            }else if(val instanceof Float){
                                switch(Float.floatToRawIntBits((float)val)){
                                default:
                                    break returnFalse;
                                case 0:
                                case Integer.MIN_VALUE:
                                    b=false;
                                    break;
                                case TypeUtil.FLT_TRUE_BITS:
                                    b=true;
                                }
                            }else if(val instanceof Double){
                                final long bits;
                                if(((bits=Double.doubleToRawLongBits((double)val)) & Long.MAX_VALUE) == 0){
                                    b=false;
                                }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                    b=true;
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Long){
                                final long v;
                                if((v=(long)val) == 0L){
                                    b=false;
                                }else if(v == 1L){
                                    b=true;
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Character){
                                switch(((Character)val).charValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    b=false;
                                    break;
                                case 1:
                                    b=true;
                                }
                            }else{
                                break returnFalse;
                            }
                            return ((PackedBooleanArrSeq)root).uncheckedcontains(rootOffset,size,b);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return false;
        }
        @Override
        public boolean equals(Object val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public void forEach(BooleanConsumer action){
            // TODO Auto-generated method stub
        }
        @Override
        public void forEach(Consumer<? super Boolean> action){
            // TODO Auto-generated method stub
        }
        @Override
        public boolean getBoolean(int index){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public int hashCode(){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(boolean val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(double val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(float val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(int val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(long val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(Object val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public boolean isEmpty(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return size == 0;
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public int lastIndexOf(boolean val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int lastIndexOf(double val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int lastIndexOf(float val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int lastIndexOf(int val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int lastIndexOf(long val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int lastIndexOf(Object val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(int index){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public void put(int index,boolean val){
            // TODO Auto-generated method stub
        }
        @Override
        public boolean remove(Object val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeBooleanAt(int index){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeIf(BooleanPredicate filter){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeIf(Predicate<? super Boolean> filter){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeVal(boolean val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeVal(double val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeVal(float val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeVal(int val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeVal(long val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public void replaceAll(BooleanPredicate operator){
            // TODO Auto-generated method stub
        }
        @Override
        public void replaceAll(UnaryOperator<Boolean> operator){
            // TODO Auto-generated method stub
        }
        @Override
        public boolean set(int index,boolean val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public int size(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return size;
        }
        @Override
        public void sort(BooleanComparator sorter){
            // TODO Auto-generated method stub
        }
        @Override
        public void sort(Comparator<? super Boolean> sorter){
            // TODO Auto-generated method stub
        }
        @Override
        public void stableAscendingSort(){
            // TODO Auto-generated method stub
        }
        @Override
        public void stableDescendingSort(){
            // TODO Auto-generated method stub
        }
        @Override
        public OmniList.OfBoolean subList(int fromIndex,int toIndex){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Boolean[] toArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public <T> T[] toArray(T[] dst){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public boolean[] toBooleanArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public byte[] toByteArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public char[] toCharArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public double[] toDoubleArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public float[] toFloatArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public int[] toIntArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public long[] toLongArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public short[] toShortArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public String toString(){
            // TODO Auto-generated method stub
            return null;
        }
        private Object writeReplace(){
            final CheckedList root;
            return new SerializableSubList((root=this.root).words,size,rootOffset,root.new ModCountChecker(modCount));
        }
        private static class SerializableSubList implements Serializable{
            private static final long serialVersionUID=1L;
            private transient long[] words;
            private transient int size;
            private transient final int rootOffset;
            private transient final CheckedList.ModCountChecker modCountChecker;
            private SerializableSubList(long[] words,int size,int rootOffset,
                    CheckedList.ModCountChecker modCountChecker){
                this.words=words;
                this.size=size;
                this.rootOffset=rootOffset;
                this.modCountChecker=modCountChecker;
            }
            private void readObject(ObjectInputStream ois) throws IOException{
                int size;
                this.size=size=ois.readInt();
                if(size != 0){
                    long[] words;
                    OmniArray.OfLong.readArray(words=new long[(size=size - 1 >> 6) + 1],0,size,ois);
                    this.words=words;
                }
            }
            private Object readResolve(){
                return new CheckedList(size,words);
            }
            private void writeObject(ObjectOutputStream oos) throws IOException{
                try{
                    int size;
                    oos.writeInt(size=this.size);
                    if(size != 0){
                        int rootOffset=this.rootOffset;
                        final int wordBound=(rootOffset=this.rootOffset) + size - 1 >> 6;
                        int wordIndex=rootOffset >> 6;
                        if((rootOffset & 63) == 0){
                            // alligned
                            OmniArray.OfLong.writeArray(words,wordIndex,wordBound,oos);
                        }else{
                            // unalligned
                            final long[] words;
                            long word=(words=this.words)[wordIndex];
                            do{
                                oos.writeLong(word >>> rootOffset | (word=words[++wordIndex]) << -rootOffset);
                            }while(wordIndex != wordBound);
                        }
                    }
                }finally{
                    modCountChecker.checkModCount();
                }
            }
        }
    }
    public static class UncheckedList extends PackedBooleanArrSeq implements BooleanListDefault,Cloneable,RandomAccess{
        private static final long serialVersionUID=1L;
        private static int countRemainingBits(long[] words,int wordBound){
            int bitCount=0;
            while(wordBound > 0){
                bitCount+=Long.bitCount(words[--wordBound]);
            }
            return bitCount;
        }
        private static void falseComesFirst(long[] words,int falseCount,int wordBound){
            int wordIndex;
            words[wordIndex=falseCount >> 6]=-1L << falseCount;
            for(int i=0;i < wordIndex;++i){
                words[i]=0;
            }
            for(int i=wordIndex + 1;i <= wordBound;++i){
                words[i]=-1L;
            }
        }
        private static void trueComesFirst(long[] words,int trueCount,int wordBound){
            int wordIndex;
            words[wordIndex=trueCount >> 6]=(1L << trueCount) - 1;
            for(int i=0;i < wordIndex;++i){
                words[i]=-1L;
            }
            for(int i=wordIndex + 1;i <= wordBound;++i){
                words[i]=0L;
            }
        }
        private static void uncheckedForEach(long[] words,int cursor,int bound,BooleanConsumer action){
            for(var word=words[cursor >> 6];;){
                action.accept((word >> cursor & 1) != 0);
                if(cursor == bound){
                    return;
                }
                if((++cursor & 63) == 0){
                    word=words[cursor >> 6];
                }
            }
        }
        public UncheckedList(){
            super();
        }
        public UncheckedList(int initialCapacity){
            super(initialCapacity);
        }
        UncheckedList(int size,long[] words){
            super(size,words);
        }
        @Override
        public void add(int index,boolean val){
            final int size;
            if((size=this.size) != 0){
                uncheckedInsert(index,size,val);
            }else{
                super.uncheckedInit(val);
            }
        }
        @Override
        public Object clone(){
            int size;
            long[] words;
            if((size=this.size) != 0){
                final int arrLength;
                ArrCopy.uncheckedCopy(this.words,0,words=new long[arrLength=(size - 1 >> 6) + 1],0,arrLength);
            }else{
                words=null;
            }
            return new UncheckedList(size,words);
        }
        @Override
        public boolean equals(Object val){
            // TODO implement equals method
            return false;
        }
        @Override
        public boolean getBoolean(int index){
            return (words[index >> 6] & 1L << index) != 0;
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            return new Itr(this);
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(){
            return new ListItr(this);
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(int index){
            return new ListItr(this,index);
        }
        @Override
        public void put(int index,boolean val){
            if(val){
                words[index >> 6]|=1L << index;
            }else{
                words[index >> 6]&=~(1L << index);
            }
        }
        @Override
        public boolean removeBooleanAt(int index){
            return (super.uncheckedRemoveIndex(index,size - 1) & 1L << index) != 0;
        }
        @Override
        public void replaceAll(BooleanPredicate operator){
            int size;
            if((size=this.size) != 0){
                uncheckedReplaceAll(0,size,operator);
            }
        }
        @Override
        public void replaceAll(UnaryOperator<Boolean> operator){
            int size;
            if((size=this.size) != 0){
                uncheckedReplaceAll(0,size,operator::apply);
            }
        }
        @Override
        public boolean set(int index,boolean val){
            final long mask;
            final long word;
            final long[] words;
            final int wordIndex;
            if(((word=(words=this.words)[wordIndex=index >> 6]) & (mask=1L << index)) != 0){
                if(!val){
                    words[wordIndex]=word & ~mask;
                }
                return true;
            }else{
                if(val){
                    words[wordIndex]=word | mask;
                }
                return false;
            }
        }
        @Override
        public void sort(BooleanComparator sorter){
            int size;
            if((size=this.size) > 1){
                if(sorter == null){
                    uncheckedStableAscendingSort(size);
                }else{
                    uncheckedSort(size,sorter);
                }
            }
        }
        @Override
        public void sort(Comparator<? super Boolean> sorter){
            int size;
            if((size=this.size) > 1){
                if(sorter == null){
                    uncheckedStableAscendingSort(size);
                }else{
                    uncheckedSort(size,sorter::compare);
                }
            }
        }
        @Override
        public void stableAscendingSort(){
            int size;
            if((size=this.size) > 1){
                uncheckedStableAscendingSort(size);
            }
        }
        @Override
        public void stableDescendingSort(){
            int size;
            if((size=this.size) > 1){
                uncheckedStableDescendingSort(size);
            }
        }
        @Override
        public OmniList.OfBoolean subList(int fromIndex,int toIndex){
            return new UncheckedSubList(this,fromIndex,toIndex - fromIndex);
        }
        @Override
        void uncheckedCopyInto(boolean[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[i]=(word >> i & 1) != 0;
                    if(++i == length){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(Boolean[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[i]=(word >> i & 1) != 0;
                    if(++i == length){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(byte[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[i]=(byte)(word >> i & 1);
                    if(++i == length){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(char[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[i]=(char)(word >> i & 1);
                    if(++i == length){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(double[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[i]=word >> i & 1;
                    if(++i == length){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(float[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[i]=word >> i & 1;
                    if(++i == length){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(int[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[i]=(int)(word >> i & 1);
                    if(++i == length){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(long[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[i]=word >> i & 1;
                    if(++i == length){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(Object[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[i]=(word >> i & 1) != 0;
                    if(++i == length){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(short[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[i]=(short)(word >> i & 1);
                    if(++i == length){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedForEach(int size,BooleanConsumer action){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    action.accept((word >> i & 1) != 0);
                    if(++i == size){
                        return;
                    }
                }while((i & 63) != 0);
            }
        }
        @Override
        int uncheckedHashCode(int size){
            int i;
            final long[] words;
            long word;
            int hash=((word=(words=this.words)[i=0]) & 1) != 0?1231:1237;
            while(++i != size){
                if((i & 63) == 0){
                    word=words[i >> 6];
                }
                hash=hash * 31 + ((word >> i & 1) != 0?1231:1237);
            }
            return hash;
        }
        @Override
        boolean uncheckedremoveVal(int size,boolean val){
            final var words=this.words;
            final int bound;
            final var wordBound=(bound=size - 1) >> 6;
            long word;
            int tail0s,wordOffset=0;
            goToShiftDown:for(;;){
                if(val){
                    for(;wordOffset < wordBound;++wordOffset){
                        if((tail0s=Long.numberOfTrailingZeros(word=words[wordOffset])) != 64){
                            break goToShiftDown;
                        }
                    }
                    if((tail0s=Long.numberOfTrailingZeros((word=words[wordBound]) & (1L << size) - 1)) != 64){
                        words[wordBound]=partialWordShiftDown(word,tail0s);
                        this.size=bound;
                        return true;
                    }
                    return false;
                }else{
                    for(;wordOffset < wordBound;++wordOffset){
                        if((tail0s=Long.numberOfTrailingZeros(~(word=words[wordOffset]))) != 64){
                            break goToShiftDown;
                        }
                    }
                    if((tail0s=Long.numberOfTrailingZeros(~((word=words[wordBound]) & (1L << size) - 1))) != 64){
                        words[wordBound]=partialWordShiftDown(word,tail0s);
                        this.size=bound;
                        return true;
                    }
                    return false;
                }
            }
            words[wordOffset]=partialWordShiftDown(word,tail0s) | (word=words[++wordOffset]) << 63;
            while(wordOffset != wordBound){
                words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
            }
            words[wordBound]=word >>> 1;
            this.size=bound;
            return true;
//      final var words=this.words;
//      final int bound;
//      final var wordBound=(bound=size - 1) >> 6;
//      int lead0s;
//      long word;
//      int wordOffset;
//      goToShiftDown:for(;;){
//        if(val){
//          if((lead0s=Long.numberOfLeadingZeros((word=words[wordBound]) << -size)) != 64){
//            words[wordBound]=partialWordShiftDown(word,-lead0s - 1);
//            this.size=bound;
//            return true;
//          }
//          for(wordOffset=wordBound - 1;wordOffset >= 0;--wordOffset){
//            if((lead0s=Long.numberOfLeadingZeros(word=words[wordOffset])) != 64){
//              break goToShiftDown;
//            }
//          }
//        }else{
//          if((lead0s=Long.numberOfLeadingZeros(~((word=words[wordBound]) << -size))) != 64){
//            words[wordBound]=partialWordShiftDown(word,-lead0s - 1);
//            this.size=bound;
//            return true;
//          }
//          for(wordOffset=wordBound - 1;wordOffset >= 0;--wordOffset){
//            if((lead0s=Long.numberOfLeadingZeros(~(word=words[wordOffset]))) != 64){
//              break goToShiftDown;
//            }
//          }
//        }
//        return false;
//      }
//      words[wordOffset]=partialWordShiftDown(word,-lead0s - 1) | (word=words[++wordOffset]) << 63;
//      while(wordOffset != wordBound){
//        words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
//      }
//      words[wordBound]=word >>> 1;
//      this.size=bound;
//      return true;
        }
        @Override
        int uncheckedToString(int size,byte[] buffer){
            final var words=this.words;
            for(int i=0,bufferOffset=1;;){
                final var word=words[i >> 6];
                do{
                    bufferOffset=ToStringUtil.getStringBoolean((word >> i & 1) != 0,buffer,bufferOffset);
                    if(++i == size){
                        return bufferOffset;
                    }
                    buffer[bufferOffset]=(byte)',';
                    buffer[++bufferOffset]=(byte)' ';
                    ++bufferOffset;
                }while((i & 63) != 0);
            }
        }
        @Override
        void uncheckedToString(int size,OmniStringBuilderByte builder){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    builder.uncheckedAppendBoolean((word >> i & 1) != 0);
                    if(++i == size){
                        return;
                    }
                    builder.uncheckedAppendCommaAndSpace();
                }while((i & 63) != 0);
            }
        }
        private void uncheckedInsert(int index,int size,boolean val){
            if(size == index){
                super.uncheckedAppend(size,val);
            }else{
                this.size=size + 1;
                final long[] words;
                final int wordIndex=index >> 6;
                var word=(words=this.words)[size>>=6];
                final long[] tmp;
                if(words.length == size){
                    ArrCopy.semicheckedCopy(words,0,tmp=new long[OmniArray.growBy50Pct(size)],0,wordIndex);
                    this.words=tmp;
                }else{
                    tmp=words;
                }
                for(;wordIndex != size;){
                    tmp[size]=word << 1 | (word=words[--size]) >>> 63;
                }
                long mask;
                word=word << 1 & (mask=-1L << index) | word & (mask=~mask);
                if(val){
                    tmp[size]=word | mask + 1;
                }else{
                    tmp[size]=word & ~(mask + 1);
                }
            }
        }
        private void uncheckedReplaceAll(int offset,int size,BooleanPredicate operator){
            final long[] words;
            int wordOffset;
            for(var word=(words=this.words)[wordOffset=offset >> 6];;){
                final long mask;
                if(operator.test((word & (mask=1L << offset)) != 0)){
                    word|=mask;
                }else{
                    word&=~mask;
                }
                if(++offset == size){
                    words[offset >> 6]=word;
                    return;
                }
                if((offset & 63) == 0){
                    words[wordOffset]=word;
                    word=words[++wordOffset];
                }
            }
        }
        private void uncheckedSort(int size,BooleanComparator sorter){
            int bitCount;
            int wordBound;
            final long[] words;
            for(;;){
                if((bitCount=Long.bitCount((words=this.words)[wordBound=size - 1 >> 6] << -size)) == 0){
                    // only encountered false
                    if((bitCount+=countRemainingBits(words,wordBound)) != 0){
                        break;
                    }
                }else if(bitCount == 64 - (-size & 63)){
                    // only encountered true
                    if((bitCount+=countRemainingBits(words,wordBound)) != size){
                        break;
                    }
                }else{
                    switch(Integer.signum(sorter.compare(false,true))){
                    case 1:
                        // true comes first
                        bitCount+=countRemainingBits(words,wordBound);
                        trueComesFirst(words,bitCount,wordBound);
                    case 0:
                        // already sorted
                        return;
                    default:
                        // false comes first
                        bitCount+=countRemainingBits(words,wordBound);
                        falseComesFirst(words,size - bitCount,wordBound);
                    }
                }
                return;
            }
            switch(Integer.signum(sorter.compare(false,true))){
            case 1:
                trueComesFirst(words,bitCount,wordBound);
            case 0:
                // already sorted
                return;
            default:
                falseComesFirst(words,size - bitCount,wordBound);
            }
        }
        private void uncheckedStableAscendingSort(int size){
            final long[] words;
            int wordBound;
            int bitCount=Long.bitCount((words=this.words)[wordBound=size - 1 >> 6] << -size);
            while(wordBound > 0){
                bitCount+=Long.bitCount(words[--wordBound]);
            }
            if(bitCount != 0 && bitCount != size){
                falseComesFirst(words,size - bitCount,wordBound);
            }
        }
        private void uncheckedStableDescendingSort(int size){
            final long[] words;
            int wordBound;
            int bitCount=Long.bitCount((words=this.words)[wordBound=size - 1 >> 6] << -size);
            while(wordBound > 0){
                bitCount+=Long.bitCount(words[--wordBound]);
            }
            if(bitCount != 0 && bitCount != size){
                trueComesFirst(words,bitCount,wordBound);
            }
        }
        private static class Itr extends AbstractBooleanItr{
            transient final UncheckedList parent;
            transient int cursor;
            Itr(Itr itr){
                parent=itr.parent;
                cursor=itr.cursor;
            }
            Itr(UncheckedList parent){
                this.parent=parent;
                cursor=0;
            }
            Itr(UncheckedList parent,int cursor){
                this.parent=parent;
                this.cursor=cursor;
            }
            @Override
            public Object clone(){
                return new Itr(this);
            }
            @Override
            public void forEachRemaining(BooleanConsumer action){
                final int cursor,size;
                final UncheckedList parent;
                if((cursor=this.cursor) < (size=(parent=this.parent).size)){
                    uncheckedForEach(parent.words,cursor,size - 1,action);
                    this.cursor=size;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                final int cursor,size;
                final UncheckedList parent;
                if((cursor=this.cursor) < (size=(parent=this.parent).size)){
                    uncheckedForEach(parent.words,cursor,size - 1,action::accept);
                    this.cursor=size;
                }
            }
            @Override
            public boolean hasNext(){
                return cursor < parent.size;
            }
            @Override
            public boolean nextBoolean(){
                final int cursor;
                return (parent.words[(cursor=this.cursor++) >> 6] & 1L << cursor) != 0;
            }
            @Override
            public void remove(){
                ((PackedBooleanArrSeq)parent).uncheckedRemoveIndexNoRet(cursor - 1);
            }
        }
        private static class ListItr extends Itr implements OmniListIterator.OfBoolean{
            transient int lastRet;
            ListItr(ListItr itr){
                super(itr);
                lastRet=itr.lastRet;
            }
            ListItr(UncheckedList parent){
                super(parent);
            }
            ListItr(UncheckedList parent,int cursor){
                super(parent,cursor);
            }
            @Override
            public void add(boolean val){
                final UncheckedList root;
                final int rootSize;
                if((rootSize=(root=parent).size) != 0){
                    root.uncheckedInsert(cursor++,rootSize,val);
                }else{
                    ((PackedBooleanArrSeq)root).uncheckedInit(val);
                    ++cursor;
                }
            }
            @Override
            public Object clone(){
                return new ListItr(this);
            }
            @Override
            public void forEachRemaining(BooleanConsumer action){
                int cursor;
                final int bound;
                final UncheckedList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).size)){
                    uncheckedForEach(parent.words,cursor,cursor=bound - 1,action);
                    lastRet=cursor;
                    this.cursor=bound;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                int cursor;
                final int bound;
                final UncheckedList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).size)){
                    uncheckedForEach(parent.words,cursor,cursor=bound - 1,action::accept);
                    lastRet=cursor;
                    this.cursor=bound;
                }
            }
            @Override
            public boolean hasPrevious(){
                return cursor > 0;
            }
            @Override
            public boolean nextBoolean(){
                final int lastRet;
                this.lastRet=lastRet=cursor++;
                return (parent.words[lastRet >> 6] & 1L << lastRet) != 0;
            }
            @Override
            public int nextIndex(){
                return cursor;
            }
            @Override
            public boolean previousBoolean(){
                final int lastRet;
                this.lastRet=lastRet=--cursor;
                return (parent.words[lastRet >> 6] & 1L << lastRet) != 0;
            }
            @Override
            public int previousIndex(){
                return cursor - 1;
            }
            @Override
            public void remove(){
                final int lastRet;
                ((PackedBooleanArrSeq)parent).uncheckedRemoveIndexNoRet(lastRet=this.lastRet);
                cursor=lastRet;
            }
            @Override
            public void set(boolean val){
                final long mask;
                final long word;
                final long[] words;
                final int wordIndex;
                final int lastRet;
                if(((word=(words=parent.words)[wordIndex=(lastRet=this.lastRet) >> 6]) & (mask=1L << lastRet)) != 0){
                    words[wordIndex]=word | mask;
                }else{
                    words[wordIndex]=word & ~mask;
                }
            }
        }
    }
    public static class UncheckedStack extends PackedBooleanArrSeq implements OmniStack.OfBoolean{
        private static final long serialVersionUID=1L;
        private static void descendingForEach(long[] words,int cursor,BooleanConsumer action){
            for(;;){
                final var word=words[--cursor >> 6];
                for(;;){
                    action.accept((word >> cursor & 1) != 0);
                    if(cursor == 0){
                        return;
                    }
                    if((cursor & 63) == 0){
                        break;
                    }
                    --cursor;
                }
            }
        }
        public UncheckedStack(){
            super();
        }
        public UncheckedStack(int initialCapacity){
            super(initialCapacity);
        }
        UncheckedStack(int size,long[] words){
            super(size,words);
        }
        @Override
        public Object clone(){
            int size;
            long[] words;
            if((size=this.size) != 0){
                final int arrLength;
                ArrCopy.uncheckedCopy(this.words,0,words=new long[arrLength=(size - 1 >> 6) + 1],0,arrLength);
            }else{
                words=null;
            }
            return new UncheckedStack(size,words);
        }
        @Override
        public boolean equals(Object val){
            // TODO implements equals method
            return false;
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            return new Itr(this);
        }
        @Override
        public Boolean peek(){
            int size;
            if((size=this.size) != 0){
                return (words[--size >> 6] & 1L << size) != 0;
            }
            return null;
        }
        @Override
        public boolean peekBoolean(){
            int size;
            if((size=this.size) != 0){
                return (words[--size >> 6] & 1L << size) != 0;
            }
            return false;
        }
        @Override
        public byte peekByte(){
            int size;
            if((size=this.size) != 0){
                return (byte)(words[--size >> 6] >> size & 1);
            }
            return Byte.MIN_VALUE;
        }
        @Override
        public char peekChar(){
            int size;
            if((size=this.size) != 0){
                return (char)(words[--size >> 6] >> size & 1);
            }
            return Character.MIN_VALUE;
        }
        @Override
        public double peekDouble(){
            int size;
            if((size=this.size) != 0){
                return words[--size >> 6] >> size & 1L;
            }
            return Double.NaN;
        }
        @Override
        public float peekFloat(){
            int size;
            if((size=this.size) != 0){
                return words[--size >> 6] >> size & 1L;
            }
            return Float.NaN;
        }
        @Override
        public int peekInt(){
            int size;
            if((size=this.size) != 0){
                return (int)(words[--size >> 6] >> size & 1);
            }
            return Integer.MIN_VALUE;
        }
        @Override
        public long peekLong(){
            int size;
            if((size=this.size) != 0){
                return words[--size >> 6] >> size & 1L;
            }
            return Long.MIN_VALUE;
        }
        @Override
        public short peekShort(){
            int size;
            if((size=this.size) != 0){
                return (short)(words[--size >> 6] >> size & 1);
            }
            return Short.MIN_VALUE;
        }
        @Override
        public Boolean poll(){
            int size;
            if((size=this.size) != 0){
                this.size=--size;
                return (words[size >> 6] & 1L << size) != 0;
            }
            return null;
        }
        @Override
        public boolean pollBoolean(){
            int size;
            if((size=this.size) != 0){
                this.size=--size;
                return (words[size >> 6] & 1L << size) != 0;
            }
            return false;
        }
        @Override
        public byte pollByte(){
            int size;
            if((size=this.size) != 0){
                this.size=--size;
                return (byte)(words[size >> 6] >> size & 1);
            }
            return Byte.MIN_VALUE;
        }
        @Override
        public char pollChar(){
            int size;
            if((size=this.size) != 0){
                this.size=--size;
                return (char)(words[size >> 6] >> size & 1);
            }
            return Character.MIN_VALUE;
        }
        @Override
        public double pollDouble(){
            int size;
            if((size=this.size) != 0){
                this.size=--size;
                return words[size >> 6] >> size & 1;
            }
            return Double.NaN;
        }
        @Override
        public float pollFloat(){
            int size;
            if((size=this.size) != 0){
                this.size=--size;
                return words[size >> 6] >> size & 1;
            }
            return Float.NaN;
        }
        @Override
        public int pollInt(){
            int size;
            if((size=this.size) != 0){
                this.size=--size;
                return (int)(words[size >> 6] >> size & 1);
            }
            return Integer.MIN_VALUE;
        }
        @Override
        public long pollLong(){
            int size;
            if((size=this.size) != 0){
                this.size=--size;
                return words[size >> 6] >> size & 1;
            }
            return Long.MIN_VALUE;
        }
        @Override
        public short pollShort(){
            int size;
            if((size=this.size) != 0){
                this.size=--size;
                return (short)(words[size >> 6] >> size & 1);
            }
            return Short.MIN_VALUE;
        }
        @Override
        void uncheckedCopyInto(boolean[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[--length]=(word >> i & 1) != 0;
                    if(length == 0){
                        return;
                    }
                }while((++i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(Boolean[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[--length]=(word >> i & 1) != 0;
                    if(length == 0){
                        return;
                    }
                }while((++i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(byte[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[--length]=(byte)(word >> i & 1);
                    if(length == 0){
                        return;
                    }
                }while((++i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(char[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[--length]=(char)(word >> i & 1);
                    if(length == 0){
                        return;
                    }
                }while((++i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(double[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[--length]=word >> i & 1;
                    if(length == 0){
                        return;
                    }
                }while((++i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(float[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[--length]=word >> i & 1;
                    if(length == 0){
                        return;
                    }
                }while((++i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(int[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[--length]=(int)(word >> i & 1);
                    if(length == 0){
                        return;
                    }
                }while((++i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(long[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[--length]=word >> i & 1;
                    if(length == 0){
                        return;
                    }
                }while((++i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(Object[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[--length]=(word >> i & 1) != 0;
                    if(length == 0){
                        return;
                    }
                }while((++i & 63) != 0);
            }
        }
        @Override
        void uncheckedCopyInto(short[] dst,int length){
            final var words=this.words;
            for(int i=0;;){
                final var word=words[i >> 6];
                do{
                    dst[--length]=(short)(word >> i & 1);
                    if(length == 0){
                        return;
                    }
                }while((++i & 63) != 0);
            }
        }
        @Override
        void uncheckedForEach(int size,BooleanConsumer action){
            descendingForEach(words,size,action);
        }
        @Override
        int uncheckedHashCode(int size){
            final long[] words;
            long word;
            int hash=((word=(words=this.words)[--size]) & 1) != 0?1231:1237;
            while(size != 0){
                if((--size & 63) == 63){
                    word=words[size >> 6];
                }
                hash=hash * 31 + ((word >> size & 1) != 0?1231:1237);
            }
            return hash;
        }
        @Override
        boolean uncheckedremoveVal(int size,boolean val){
            final var words=this.words;
            final int bound;
            final var wordBound=(bound=size - 1) >> 6;
            int lead0s;
            long word;
            int wordOffset;
            goToShiftDown:for(;;){
                if(val){
                    if((lead0s=Long.numberOfLeadingZeros((word=words[wordBound]) << -size)) != 64){
                        words[wordBound]=partialWordShiftDown(word,-lead0s - 1);
                        this.size=bound;
                        return true;
                    }
                    for(wordOffset=wordBound - 1;wordOffset >= 0;--wordOffset){
                        if((lead0s=Long.numberOfLeadingZeros(word=words[wordOffset])) != 64){
                            break goToShiftDown;
                        }
                    }
                }else{
                    if((lead0s=Long.numberOfLeadingZeros(~((word=words[wordBound]) << -size))) != 64){
                        words[wordBound]=partialWordShiftDown(word,-lead0s - 1);
                        this.size=bound;
                        return true;
                    }
                    for(wordOffset=wordBound - 1;wordOffset >= 0;--wordOffset){
                        if((lead0s=Long.numberOfLeadingZeros(~(word=words[wordOffset]))) != 64){
                            break goToShiftDown;
                        }
                    }
                }
                return false;
            }
            words[wordOffset]=partialWordShiftDown(word,-lead0s - 1) | (word=words[++wordOffset]) << 63;
            while(wordOffset != wordBound){
                words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
            }
            words[wordBound]=word >>> 1;
            this.size=bound;
            return true;
        }
        @Override
        int uncheckedToString(int size,byte[] buffer){
            final var words=this.words;
            for(int bufferOffset=1;;){
                final var word=words[--size >> 6];
                for(;;){
                    bufferOffset=ToStringUtil.getStringBoolean((word >> size & 1) != 0,buffer,bufferOffset);
                    if(size == 0){
                        return bufferOffset;
                    }
                    buffer[bufferOffset]=(byte)',';
                    buffer[++bufferOffset]=(byte)' ';
                    ++bufferOffset;
                    if((size & 63) == 0){
                        break;
                    }
                    --size;
                }
            }
        }
        @Override
        void uncheckedToString(int size,OmniStringBuilderByte builder){
            final var words=this.words;
            for(;;){
                final var word=words[--size >> 6];
                for(;;){
                    builder.uncheckedAppendBoolean((word >> size & 1) != 0);
                    if(size == 0){
                        return;
                    }
                    builder.uncheckedAppendCommaAndSpace();
                    if((size & 63) == 0){
                        break;
                    }
                    --size;
                }
            }
        }
        private static class Itr extends AbstractBooleanItr{
            private transient final UncheckedStack parent;
            private transient int cursor;
            private Itr(Itr itr){
                parent=itr.parent;
                cursor=itr.cursor;
            }
            private Itr(UncheckedStack parent){
                this.parent=parent;
                cursor=parent.size;
            }
            @Override
            public Object clone(){
                return new Itr(this);
            }
            @Override
            public void forEachRemaining(BooleanConsumer action){
                final int cursor;
                if((cursor=this.cursor) > 0){
                    descendingForEach(parent.words,cursor,action);
                    this.cursor=0;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                final int cursor;
                if((cursor=this.cursor) > 0){
                    descendingForEach(parent.words,cursor,action::accept);
                    this.cursor=0;
                }
            }
            @Override
            public boolean hasNext(){
                return cursor > 0;
            }
            @Override
            public boolean nextBoolean(){
                final int cursor;
                return (parent.words[(cursor=--this.cursor) >> 6] & 1L << cursor) != 0;
            }
            @Override
            public void remove(){
                ((PackedBooleanArrSeq)parent).uncheckedRemoveIndexNoRet(cursor);
            }
        }
    }
    public static class UncheckedSubList extends AbstractSeq<Boolean>
            implements
            BooleanSubListDefault,
            Cloneable,
            RandomAccess{
        private static final long serialVersionUID=1L;
        transient final int rootOffset;
        transient final UncheckedList root;
        transient final UncheckedSubList parent;
        private UncheckedSubList(UncheckedList root,int rootOffset,int size){
            super(size);
            this.root=root;
            parent=null;
            this.rootOffset=rootOffset;
        }
        private UncheckedSubList(UncheckedSubList parent,int rootOffset,int size){
            super(size);
            root=parent.root;
            this.parent=parent;
            this.rootOffset=rootOffset;
        }
        @Override
        public boolean add(boolean val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public void add(int index,boolean val){
            for(var curr=parent;curr != null;++curr.size,curr=curr.parent){}
            ++size;
            final UncheckedList root;
            final int rootSize;
            if((rootSize=(root=this.root).size) != 0){
                root.uncheckedInsert(rootOffset + index,rootSize,val);
            }else{
                ((PackedBooleanArrSeq)root).uncheckedInit(val);
            }
        }
        @Override
        public void clear(){
            // TODO Auto-generated method stub
        }
        @Override
        public Object clone(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public boolean contains(boolean val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean contains(double val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean contains(float val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean contains(int val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean contains(long val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean contains(Object val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean equals(Object val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public void forEach(BooleanConsumer action){
            // TODO Auto-generated method stub
        }
        @Override
        public void forEach(Consumer<? super Boolean> action){
            // TODO Auto-generated method stub
        }
        @Override
        public boolean getBoolean(int index){
            return (root.words[(index+=rootOffset) >> 6] & 1L << index) != 0;
        }
        @Override
        public int hashCode(){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(boolean val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(double val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(float val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(int val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(long val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int indexOf(Object val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public int lastIndexOf(boolean val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int lastIndexOf(double val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int lastIndexOf(float val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int lastIndexOf(int val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int lastIndexOf(long val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public int lastIndexOf(Object val){
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(int index){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public void put(int index,boolean val){
            index+=rootOffset;
            if(val){
                root.words[index >> 6]|=1L << index;
            }else{
                root.words[index >> 6]&=~(1L << index);
            }
        }
        @Override
        public boolean remove(Object val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeBooleanAt(int index){
            var curr=this;
            do{
                --curr.size;
            }while((curr=curr.parent) != null);
            final PackedBooleanArrSeq root;
            return ((root=this.root).uncheckedRemoveIndex(index+=rootOffset,root.size - 1) & 1L << index) != 0;
        }
        @Override
        public boolean removeIf(BooleanPredicate filter){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeIf(Predicate<? super Boolean> filter){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeVal(boolean val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeVal(double val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeVal(float val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeVal(int val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean removeVal(long val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public void replaceAll(BooleanPredicate operator){
            // TODO Auto-generated method stub
        }
        @Override
        public void replaceAll(UnaryOperator<Boolean> operator){
            // TODO Auto-generated method stub
        }
        @Override
        public boolean set(int index,boolean val){
            final long mask;
            final long word;
            final long[] words;
            final int wordIndex;
            if(((word=(words=root.words)[wordIndex=(index+=rootOffset) >> 6]) & (mask=1L << index)) != 0){
                if(!val){
                    words[wordIndex]=word & ~mask;
                }
                return true;
            }else{
                if(val){
                    words[wordIndex]=word | mask;
                }
                return false;
            }
        }
        @Override
        public void sort(BooleanComparator sorter){
            // TODO Auto-generated method stub
        }
        @Override
        public void sort(Comparator<? super Boolean> sorter){
            // TODO Auto-generated method stub
        }
        @Override
        public void stableAscendingSort(){
            // TODO Auto-generated method stub
        }
        @Override
        public void stableDescendingSort(){
            // TODO Auto-generated method stub
        }
        @Override
        public OmniList.OfBoolean subList(int fromIndex,int toIndex){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Boolean[] toArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public <T> T[] toArray(T[] dst){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public boolean[] toBooleanArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public byte[] toByteArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public char[] toCharArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public double[] toDoubleArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public float[] toFloatArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public int[] toIntArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public long[] toLongArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public short[] toShortArray(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public String toString(){
            // TODO Auto-generated method stub
            return null;
        }
        private Object writeReplace(){
            return new SerializableSubList(root.words,size,rootOffset);
        }
        private static class SerializableSubList implements Serializable{
            private static final long serialVersionUID=1L;
            private transient long[] words;
            private transient int size;
            private transient final int rootOffset;
            private SerializableSubList(long[] words,int size,int rootOffset){
                this.words=words;
                this.size=size;
                this.rootOffset=rootOffset;
            }
            private void readObject(ObjectInputStream ois) throws IOException{
                int size;
                this.size=size=ois.readInt();
                if(size != 0){
                    long[] words;
                    OmniArray.OfLong.readArray(words=new long[(size=size - 1 >> 6) + 1],0,size,ois);
                    this.words=words;
                }
            }
            private Object readResolve(){
                return new UncheckedList(size,words);
            }
            private void writeObject(ObjectOutputStream oos) throws IOException{
                {
                    int size;
                    oos.writeInt(size=this.size);
                    if(size != 0){
                        int rootOffset=this.rootOffset;
                        final int wordBound=(rootOffset=this.rootOffset) + size - 1 >> 6;
                        int wordIndex=rootOffset >> 6;
                        if((rootOffset & 63) == 0){
                            // alligned
                            OmniArray.OfLong.writeArray(words,wordIndex,wordBound,oos);
                        }else{
                            // unalligned
                            final long[] words;
                            long word=(words=this.words)[wordIndex];
                            do{
                                oos.writeLong(word >>> rootOffset | (word=words[++wordIndex]) << -rootOffset);
                            }while(wordIndex != wordBound);
                        }
                    }
                }
            }
        }
    }
}
