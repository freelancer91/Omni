package omni.impl.seq;
import static omni.impl.seq.PackedBooleanArrSeq.UncheckedList.*;
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
import java.util.function.LongUnaryOperator;
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
import omni.util.BitSetUtil;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
import omni.util.ToStringUtil.OmniStringBuilderByte;
import omni.util.TypeUtil;
public abstract class PackedBooleanArrSeq extends AbstractBooleanArrSeq implements OmniCollection.OfBoolean{
    private static final long serialVersionUID=1L;
    private static int removeFalse(long[] words,int offset,int bitCount,int size){
        for(int j=offset;;bitCount+=Long.bitCount(words[--j])){
            words[j]=-1L;
            if(j == 0){
                setRange(words,offset,(size-=bitCount) - 1 >> 6,-1L);
                return size;
            }
        }
    }
    private static void removeIndexShiftDown(long[] words,int tail0s,int wordOffset,long word,int rootBound){
        final var mask=(1L << tail0s) - 1;
        if(wordOffset == (rootBound>>=6)){
            words[wordOffset]=word & mask | word >>> 1 & ~mask;
        }else{
            words[wordOffset]=word & mask | word >>> 1 & ~mask | (word=words[++wordOffset]) << -1;
            while(wordOffset != rootBound){
                words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << -1;
            }
            words[wordOffset]=word >>> 1;
        }
    }
    private static int removeTrue(long[] words,int offset,int bitCount){
        for(int j=offset;;bitCount+=Long.bitCount(words[--j])){
            words[j]=0L;
            if(j == 0){
                setRange(words,offset,bitCount - 1 >> 6,0L);
                return bitCount;
            }
        }
    }
    private static void setRange(long[] words,int offset,int bound,long val){
        while(offset < bound){
            words[offset]=val;
            ++offset;
        }
    }
    private static int setRemainingAndCountBits(long[] words,int wordBound,long val){
        for(int bitCount=0;;){
            words[wordBound]=val;
            if(wordBound == 0){
                return bitCount;
            }
            bitCount+=Long.bitCount(words[--wordBound]);
        }
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
        return BitSetUtil.uncheckedcontains(words,bound,BitSetUtil.getWordFlipper(val));
    }
    @Override
    int uncheckedindexOf(int size,boolean val){
        final var words=this.words;
        final var wordFlipper=BitSetUtil.getWordFlipper(val);
        var wordOffset=0;
        int tail0s;
        goToFound:for(;;){
            for(final var wordBound=--size >> 6;wordOffset < wordBound;++wordOffset){
                if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(words[wordOffset]))) != 64){
                    break goToFound;
                }
            }
            if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(words[wordOffset]))) > (size & 63)){
                return -1;
            }
            break goToFound;
        }
        return tail0s + (wordOffset << 6);
    }
    @Override
    void uncheckedInit(boolean val){
        long[] words;
        if((words=this.words) == null){
            this.words=new long[]{val?1L:0L};
        }else{
            words[0]=val?1L:0L;
        }
        size=1;
    }
    @Override
    int uncheckedlastIndexOf(int size,final boolean val){
        final var words=this.words;
        int wordBound;
        final LongUnaryOperator wordFlipper;
        int lead0s;
        if((lead0s=Long.numberOfLeadingZeros((wordFlipper=BitSetUtil.getWordFlipper(val))
                .applyAsLong(words[wordBound=--size >> 6]) << ~size)) != 64){
            return (size & 63) - lead0s + (wordBound << 6);
        }
        while(--wordBound >= 0){
            if((lead0s=Long.numberOfLeadingZeros(wordFlipper.applyAsLong(words[wordBound]))) != 64){
                return 63 - lead0s + (wordBound << 6);
            }
        }
        return -1;
    }
    
    @Override
    int uncheckedRemoveIfImpl(final int size,final BooleanPredicate filter){
        final long[] words;
        final int wordBound;
        int bitCount;
        if((bitCount=Long.bitCount((words=this.words)[wordBound=size - 1 >> 6] << -size)) == 0){
            if(filter.test(false)){
                for(int i=wordBound;i != 0;){
                    if((bitCount=Long.bitCount(words[--i])) != 0){
                        if(!filter.test(true)){
                            return removeFalse(words,i,bitCount,size);
                        }
                        break;
                    }
                }
                return size;
            }else{
                for(int i=wordBound;i != 0;){
                    if((bitCount=Long.bitCount(words[--i])) != 0){
                        if(filter.test(true)){
                            return removeTrue(words,i,bitCount);
                        }
                        break;
                    }
                }
                return 0;
            }
        }else if(bitCount == 64 - (-size & 63)){
            if(filter.test(true)){
                for(int i=wordBound;i != 0;){
                    int numTrue;
                    bitCount+=numTrue=Long.bitCount(words[--i]);
                    if(numTrue != 64){
                        if(!filter.test(false)){
                            return removeTrue(words,i,bitCount);
                        }
                        break;
                    }
                }
                return size;
            }else{
                for(int i=wordBound;i != 0;){
                    int numTrue;
                    bitCount+=numTrue=Long.bitCount(words[--i]);
                    if(numTrue != 64){
                        if(filter.test(false)){
                            return removeFalse(words,i,bitCount,size);
                        }
                        break;
                    }
                }
                return 0;
            }
        }else{
            if(filter.test(false)){
                if(filter.test(true)){
                    return size;
                }else{
                    return size - (bitCount + setRemainingAndCountBits(words,wordBound,-1L));
                }
            }else{
                if(filter.test(true)){
                    return bitCount + setRemainingAndCountBits(words,wordBound,0L);
                }else{
                    return 0;
                }
            }
        }
    }
    @Override
    int uncheckedsearch(int size,final boolean val){
        final var words=this.words;
        int wordBound;
        int lead0s;
        final LongUnaryOperator wordFlipper;
        if((lead0s=Long.numberOfLeadingZeros((wordFlipper=BitSetUtil.getWordFlipper(val))
                .applyAsLong(words[wordBound=--size >> 6]) << ~size)) != 64){
            return lead0s + 1;
        }
        while(--wordBound >= 0){
            if((lead0s=Long.numberOfLeadingZeros(wordFlipper.applyAsLong(words[wordBound]))) != 64){
                return size - (62 - lead0s + (wordBound << 6));
            }
        }
        return -1;
    }
    private int uncheckedRemoveIfImpl(int size,BooleanPredicate filter,
            CheckedCollection.AbstractModCountChecker modCountChecker){
        final long[] words;
        final int wordBound;
        int bitCount;
        if((bitCount=Long.bitCount((words=this.words)[wordBound=size - 1 >> 6] << -size)) == 0){
            final var removeFalse=filter.test(false);
            for(int i=wordBound;i != 0;){
                if((bitCount=Long.bitCount(words[--i])) != 0){
                    final var removeTrue=filter.test(true);
                    modCountChecker.checkModCount();
                    if(removeFalse){
                        if(removeTrue){
                            return size;
                        }else{
                            return removeFalse(words,i,bitCount,size);
                        }
                    }else{
                        if(removeTrue){
                            return removeTrue(words,i,bitCount);
                        }else{
                            return 0;
                        }
                    }
                }
            }
            modCountChecker.checkModCount();
            if(removeFalse){
                return size;
            }else{
                return 0;
            }
        }else if(bitCount == 64 - (-size & 63)){
            final var removeTrue=filter.test(true);
            for(int i=wordBound;i != 0;){
                int numTrue;
                bitCount+=numTrue=Long.bitCount(words[--i]);
                if(numTrue != 64){
                    final var removeFalse=filter.test(false);
                    modCountChecker.checkModCount();
                    if(removeTrue){
                        if(removeFalse){
                            return size;
                        }else{
                            return removeTrue(words,i,bitCount);
                        }
                    }else{
                        if(removeFalse){
                            return removeFalse(words,i,bitCount,size);
                        }else{
                            return 0;
                        }
                    }
                }
            }
            modCountChecker.checkModCount();
            if(removeTrue){
                return size;
            }else{
                return 0;
            }
        }else{
            final var removeFalse=filter.test(false);
            final var removeTrue=filter.test(true);
            modCountChecker.checkModCount();
            if(removeFalse){
                if(removeTrue){
                    return size;
                }else{
                    return size - (bitCount + setRemainingAndCountBits(words,wordBound,-1L));
                }
            }else{
                if(removeTrue){
                    return bitCount + setRemainingAndCountBits(words,wordBound,0L);
                }else{
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
        var word=(words=this.words)[wordOffset=index >> 6]=BitSetUtil.partialWordShiftDown(retWord=words[wordOffset],
                index);
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
        var word=(words=this.words)[wordOffset=index >> 6]=BitSetUtil.partialWordShiftDown(words[wordOffset],index);
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
        private int multiWordRemoveIfImpl(int offset,int size,BooleanPredicate filter,int bound,int wordOffset,int wordBound,ModCountChecker modCountChecker) {
            final var words=this.words;
            long word;
            int i;
            int bitCount=Long.bitCount((word=words[wordOffset])>>>offset);
            goToRemoveAll:for(;;) {
                if(bitCount==0) {
                    boolean removeFalse=filter.test(false);
                    for(i=wordOffset;++i<wordBound;) {
                        if((bitCount=Long.bitCount(words[i]))!=0) {
                            boolean removeTrue=filter.test(true);
                            modCountChecker.checkModCount();
                            if(removeTrue) {
                                
                                if(removeFalse) {
                                    break goToRemoveAll;
                                }
                                super.removeAllTrue(words,offset,bitCount+=super.countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound),bound,size=this.size);
                                this.size=size-bitCount;
                                return bitCount;
                            }else if(removeFalse) {
                                super.removeAllFalse(words,offset,bitCount=size-(bitCount+super.countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound)),bound,size=this.size);
                                this.size=size-bitCount;
                                return bitCount;
                            }
                            return 0;
                        }
                    }
                    if((bitCount=Long.bitCount(words[wordBound]<<-bound))==0) {
                        modCountChecker.checkModCount();
                        if(removeFalse) {
                            break goToRemoveAll;
                        }
                    }else {
                        boolean removeTrue=filter.test(true);
                        modCountChecker.checkModCount();
                        if(removeTrue) {
                            if(removeFalse) {
                                break goToRemoveAll;
                            }
                            super.removeAllTrue(words,offset,bitCount,bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }else if(removeFalse) {
                            super.removeAllFalse(words,offset,bitCount=size-bitCount,bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }
                    }
                   
                }else if(bitCount==64-(offset&63)) {
                    boolean removeTrue=filter.test(true);
                    for(i=wordOffset;++i<wordBound;) {
                        int numTrue;
                        bitCount+=numTrue=Long.bitCount(words[i]);
                        if(numTrue!=64) {
                            boolean removeFalse=filter.test(false);
                            modCountChecker.checkModCount();
                            if(removeFalse) {
                                if(removeTrue) {
                                    break goToRemoveAll;
                                }
                                super.removeAllFalse(words,offset,bitCount=size-(bitCount+super.countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound)),bound,size=this.size);
                                this.size=size-bitCount;
                                return bitCount;
                            }else if(removeTrue) {
                                super.removeAllTrue(words,offset,bitCount+=super.countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound),bound,size=this.size);
                                this.size=size-bitCount;
                                return bitCount;
                            }
                            return 0;
                        }
                    }
                    if((bitCount+=Long.bitCount(words[wordBound]<<-bound))==size) {
                        modCountChecker.checkModCount();
                        if(removeTrue) {
                            break goToRemoveAll;
                        }
                    }else {
                        boolean removeFalse=filter.test(false);
                        modCountChecker.checkModCount();
                        if(removeFalse) {
                            if(removeTrue) {
                                break goToRemoveAll;
                            }
                            super.removeAllFalse(words,offset,bitCount=size-bitCount,bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }else if(removeTrue) {
                            super.removeAllTrue(words,offset,bitCount,bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }
                    }
                  
                }else {
                    i=wordOffset;
                    boolean removeFalse=filter.test(false);
                    boolean removeTrue=filter.test(true);
                    modCountChecker.checkModCount();
                    if(removeFalse) {
                        if(removeTrue) {
                            break goToRemoveAll;
                        }else {
                            super.removeAllFalse(words,offset,bitCount=size-(bitCount+super.countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound)),bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }
                        
                    }else {
                        if(removeTrue) {
                            super.removeAllTrue(words,offset,bitCount+=super.countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound),bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }
                       
                    }
                }
                return 0;
            } 
            super.removeIfShiftDown(bitCount=this.size,size,wordOffset,wordBound,words,word&(1L<<offset)-1 | words[wordBound]&~((1L<<bound)-1)>>>size);
            this.size=bitCount-size;
            return size;
        }
        
      
        
       
        private int singleWordRemoveIfImpl(int offset,int size,BooleanPredicate filter,int bound,int wordBound,ModCountChecker modCountChecker){
            final var words=this.words;
            long word;
            int bitCount;
            goToRemoveAll:for(;;) {
                int numFalse;
                if((bitCount=Long.bitCount((word=words[wordBound])&-1L>>>-size<<offset))==0) {
                    boolean removeFalse=filter.test(false);
                    modCountChecker.checkModCount();
                    if(removeFalse) {
                        break goToRemoveAll;
                    }
                }else if((numFalse=size-bitCount)==0) {
                    boolean removeTrue=filter.test(true);
                    modCountChecker.checkModCount();
                    if(removeTrue) {
                       break goToRemoveAll;
                    }
                }else {
                    boolean removeFalse=filter.test(false);
                    boolean removeTrue=filter.test(true);
                    modCountChecker.checkModCount();
                    if(removeFalse) {
                        if(removeTrue) {
                            break goToRemoveAll;
                        }else {
                            super.removeIfShiftDown(size=this.size,numFalse,wordBound,wordBound,words,word&(1L<<offset)-1 | (1L<<bitCount)-1<<offset | (word&~((1L<<bound)-1))>>>numFalse);
                            this.size=size-numFalse;
                            return numFalse;
                        }
                    }else if(removeTrue) {
                        super.removeIfShiftDown(size=this.size,bitCount,wordBound,wordBound,words,word&(1L<<offset)-1 | (word&~(-1L>>>-bound))>>>bitCount);
                        this.size=size-bitCount;
                        return bitCount;
                    }
                }
                return 0;
            }
            super.removeIfShiftDown(bitCount=this.size,size,wordBound,wordBound,words,word&(1L<<offset)-1 | (word&~((1L<<bound)-1))>>>size);
            this.size=bitCount-size;
            return size;
        }
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
                    try{
                        ((UncheckedList)parent).uncheckedForEach(cursor,bound,action::accept);
                    }finally{
                        CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
                    }
                    this.cursor=bound;
                    lastRet=bound - 1;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                final int cursor;
                final int bound;
                final CheckedList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).size)){
                    final int modCount=this.modCount;
                    try{
                        ((UncheckedList)parent).uncheckedForEach(cursor,bound,action::accept);
                    }finally{
                        CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
                    }
                    this.cursor=bound;
                    lastRet=bound - 1;
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
                    final var words=root.words;
                    if(val){
                        words[lastRet >> 6]|=1L << lastRet;
                    }else{
                        words[lastRet >> 6]&=~(1L << lastRet);
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
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr != null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
            if((modCount=root.size) != 0){
                ((UncheckedList)root).uncheckedInsert(rootOffset + size++,modCount,val);
            }else{
                ((PackedBooleanArrSeq)root).uncheckedInit(val);
                ++size;
            }
            return true;
        }
        @Override
        public void add(int index,boolean val){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            CheckedCollection.checkLo(index);
            final int size;
            CheckedCollection.checkWriteHi(index,size=this.size);
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr != null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
            this.size=size + 1;
            if((modCount=root.size) != 0){
                ((UncheckedList)root).uncheckedInsert(rootOffset + index,modCount,val);
            }else{
                ((PackedBooleanArrSeq)root).uncheckedInit(val);
            }
        }
        @Override
        public void clear(){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            int size;
            if((size=this.size) != 0){
                root.modCount=++modCount;
                this.modCount=modCount;
                for(var curr=parent;curr != null;curr.size-=size,curr.modCount=modCount,curr=curr.parent){}
                this.size=0;
                final int offset,tailLength,rootBound;
                root.size=(rootBound=root.size) - size;
                if((tailLength=rootBound - (size=(offset=rootOffset) + size)) != 0){
                    final var words=root.words;
                    if((offset & 63) == 0){
                        if((size & 63) == 0){
                            ArrCopy.uncheckedSelfCopy(words,size >> 6,offset >> 6,(tailLength - 1 >> 6) + 1);
                        }else{
                            BitSetUtil.srcUnallignedPullDown(words,offset >> 6,size,rootBound - 1 >> 6);
                        }
                    }else{
                        BitSetUtil.dstUnallignedPullDown(words,offset,size,rootBound - 1 >> 6);
                    }
                }
            }
        }
        @Override
        public Object clone(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final long[] copy,words;
            final int size;
            if((size=this.size) != 0){
                final int offset;
                int dstWordBound=size - 1 >> 6;
                words=root.words;
                var srcWordOffset=(offset=rootOffset) >> 6;
                if((offset & 63) == 0){
                    ArrCopy.uncheckedCopy(words,srcWordOffset,copy=new long[++dstWordBound],0,dstWordBound);
                }else{
                    copy=new long[dstWordBound + 1];
                    var word=words[srcWordOffset];
                    int srcWordBound;
                    if((srcWordBound=offset + size - 1 >> 6) - srcWordOffset > dstWordBound){
                        for(int dstWordOffset=0;;++dstWordOffset){
                            copy[dstWordOffset]=word >>> offset | (word=words[++srcWordOffset]) << -offset;
                            if(dstWordOffset == dstWordBound){
                                break;
                            }
                        }
                    }else{
                        for(int dstWordOffset=0;srcWordOffset != srcWordBound;++dstWordOffset){
                            copy[dstWordOffset]=word >>> offset | (word=words[++srcWordOffset]) << -offset;
                        }
                        copy[dstWordBound]=word >>> offset;
                    }
                }
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
                        return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,BitSetUtil.getWordFlipper(val));
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
                            final LongUnaryOperator wordFlipper;
                            long bits;
                            if(((bits=Double.doubleToRawLongBits(val)) & Long.MAX_VALUE) == 0){
                                wordFlipper=BitSetUtil::flip;
                            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,wordFlipper);
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
                            final LongUnaryOperator wordFlipper;
                            switch(Float.floatToRawIntBits(val)){
                            default:
                                break returnFalse;
                            case 0:
                            case Integer.MIN_VALUE:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case TypeUtil.FLT_TRUE_BITS:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,wordFlipper);
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
                            final LongUnaryOperator wordFlipper;
                            switch(val){
                            default:
                                break returnFalse;
                            case 0:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case 1:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,wordFlipper);
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
                            final LongUnaryOperator wordFlipper;
                            if(val == 0L){
                                wordFlipper=BitSetUtil::flip;
                            }else if(val == 1L){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,wordFlipper);
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
                            final LongUnaryOperator wordFlipper;
                            if(val instanceof Boolean){
                                wordFlipper=BitSetUtil.getWordFlipper((boolean)val);
                            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                                switch(((Number)val).intValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Float){
                                switch(Float.floatToRawIntBits((float)val)){
                                default:
                                    break returnFalse;
                                case 0:
                                case Integer.MIN_VALUE:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case TypeUtil.FLT_TRUE_BITS:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Double){
                                final long bits;
                                if(((bits=Double.doubleToRawLongBits((double)val)) & Long.MAX_VALUE) == 0){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Long){
                                final long v;
                                if((v=(long)val) == 0L){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(v == 1L){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Character){
                                switch(((Character)val).charValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else{
                                break returnFalse;
                            }
                            return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,wordFlipper);
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
            final int modCount=this.modCount;
            final var root=this.root;
            try{
                final int size;
                if((size=this.size) != 0){
                    final int rootOffset;
                    ((UncheckedList)root).uncheckedForEach(rootOffset=this.rootOffset,rootOffset + size,action);
                }
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
        }
        @Override
        public void forEach(Consumer<? super Boolean> action){
            final int modCount=this.modCount;
            final var root=this.root;
            try{
                final int size;
                if((size=this.size) != 0){
                    final int rootOffset;
                    ((UncheckedList)root).uncheckedForEach(rootOffset=this.rootOffset,rootOffset + size,action::accept);
                }
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
        }
        @Override
        public boolean getBoolean(int index){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
            return (root.words[(index+=rootOffset) >> 6] >> index & 1) != 0;
        }
        @Override
        public int hashCode(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                return ((UncheckedList)root).uncheckedHashCode(rootOffset=this.rootOffset,rootOffset + size);
            }
            return 1;
        }
        @Override
        public int indexOf(boolean val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        return ((UncheckedList)root).uncheckedindexOf(rootOffset,size,BitSetUtil.getWordFlipper(val));
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public int indexOf(double val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            long bits;
                            if(((bits=Double.doubleToRawLongBits(val)) & Long.MAX_VALUE) == 0){
                                wordFlipper=BitSetUtil::flip;
                            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return ((UncheckedList)root).uncheckedindexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public int indexOf(float val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(Float.floatToRawIntBits(val)){
                            default:
                                break returnFalse;
                            case 0:
                            case Integer.MIN_VALUE:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case TypeUtil.FLT_TRUE_BITS:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return ((UncheckedList)root).uncheckedindexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public int indexOf(int val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(val){
                            default:
                                break returnFalse;
                            case 0:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case 1:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return ((UncheckedList)root).uncheckedindexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public int indexOf(long val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val == 0L){
                                wordFlipper=BitSetUtil::flip;
                            }else if(val == 1L){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return ((UncheckedList)root).uncheckedindexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public int indexOf(Object val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        // TODO a pattern-matching switch statement would be great here
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val instanceof Boolean){
                                wordFlipper=BitSetUtil.getWordFlipper((boolean)val);
                            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                                switch(((Number)val).intValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Float){
                                switch(Float.floatToRawIntBits((float)val)){
                                default:
                                    break returnFalse;
                                case 0:
                                case Integer.MIN_VALUE:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case TypeUtil.FLT_TRUE_BITS:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Double){
                                final long bits;
                                if(((bits=Double.doubleToRawLongBits((double)val)) & Long.MAX_VALUE) == 0){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Long){
                                final long v;
                                if((v=(long)val) == 0L){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(v == 1L){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Character){
                                switch(((Character)val).charValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else{
                                break returnFalse;
                            }
                            return ((UncheckedList)root).uncheckedindexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public boolean isEmpty(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return size == 0;
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return new Itr(this);
        }
        @Override
        public int lastIndexOf(boolean val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        return ((UncheckedList)root).uncheckedlastIndexOf(rootOffset,size,
                                BitSetUtil.getWordFlipper(val));
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public int lastIndexOf(double val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            long bits;
                            if(((bits=Double.doubleToRawLongBits(val)) & Long.MAX_VALUE) == 0){
                                wordFlipper=BitSetUtil::flip;
                            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return ((UncheckedList)root).uncheckedlastIndexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public int lastIndexOf(float val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(Float.floatToRawIntBits(val)){
                            default:
                                break returnFalse;
                            case 0:
                            case Integer.MIN_VALUE:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case TypeUtil.FLT_TRUE_BITS:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return ((UncheckedList)root).uncheckedlastIndexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public int lastIndexOf(int val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(val){
                            default:
                                break returnFalse;
                            case 0:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case 1:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return ((UncheckedList)root).uncheckedlastIndexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public int lastIndexOf(long val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val == 0L){
                                wordFlipper=BitSetUtil::flip;
                            }else if(val == 1L){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return ((UncheckedList)root).uncheckedlastIndexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public int lastIndexOf(Object val){
            {
                final var root=this.root;
                final int modCount=this.modCount;
                try{
                    final int size;
                    if((size=this.size) != 0){
                        // TODO a pattern-matching switch statement would be great here
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val instanceof Boolean){
                                wordFlipper=BitSetUtil.getWordFlipper((boolean)val);
                            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                                switch(((Number)val).intValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Float){
                                switch(Float.floatToRawIntBits((float)val)){
                                default:
                                    break returnFalse;
                                case 0:
                                case Integer.MIN_VALUE:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case TypeUtil.FLT_TRUE_BITS:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Double){
                                final long bits;
                                if(((bits=Double.doubleToRawLongBits((double)val)) & Long.MAX_VALUE) == 0){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Long){
                                final long v;
                                if((v=(long)val) == 0L){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(v == 1L){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Character){
                                switch(((Character)val).charValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else{
                                break returnFalse;
                            }
                            return ((UncheckedList)root).uncheckedlastIndexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
                finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            }// end val check
            return -1;
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return new ListItr(this);
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(int index){
            CheckedCollection.checkModCount(modCount,root.modCount);
            CheckedCollection.checkLo(index);
            CheckedCollection.checkWriteHi(index,size);
            return new ListItr(this,index + rootOffset);
        }
        @Override
        public void put(int index,boolean val){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,this.size);
            index+=rootOffset;
            if(val) {
                root.words[index>>6]|=1L<<index;
            }else {
                root.words[index>>6]&=~(1L<<index);
            }
          }
        @Override
        public boolean remove(Object val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        // TODO a pattern-matching switch statement would be great here
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val instanceof Boolean){
                                wordFlipper=BitSetUtil.getWordFlipper((boolean)val);
                            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                                switch(((Number)val).intValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Float){
                                switch(Float.floatToRawIntBits((float)val)){
                                default:
                                    break returnFalse;
                                case 0:
                                case Integer.MIN_VALUE:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case TypeUtil.FLT_TRUE_BITS:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Double){
                                final long bits;
                                if(((bits=Double.doubleToRawLongBits((double)val)) & Long.MAX_VALUE) == 0){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Long){
                                final long v;
                                if((v=(long)val) == 0L){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(v == 1L){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Character){
                                switch(((Character)val).charValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else{
                                break returnFalse;
                            }
                            return this.uncheckedremoveVal(size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public boolean removeBooleanAt(int index){
            int modCount;
            final CheckedList root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            CheckedCollection.checkLo(index);
            final int size;
            CheckedCollection.checkReadHi(index,size=this.size);
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
            final var ret=((PackedBooleanArrSeq)root).uncheckedRemoveIndex(index+=rootOffset,root.size-1);
            this.size=size-1;
            return (ret>>index&1)!=0;
          }
        @Override
        public boolean removeIf(BooleanPredicate filter){
            int modCount=this.modCount;
            final var root=this.root;
            int size;
            if((size=this.size)!=0){
              try
              {
                  final int numRemoved,offset,wordOffset,bound,wordBound;
                  final var modCountChecker=root.new ModCountChecker(modCount);
                  if((wordOffset=(offset=this.rootOffset)>>6)==(wordBound=(bound=offset+size)-1>>6)) {
                      numRemoved=root.singleWordRemoveIfImpl(offset,size,filter,bound,wordBound,modCountChecker);
                  }else {
                      numRemoved=root.multiWordRemoveIfImpl(offset,size,filter,bound,wordOffset,wordBound,modCountChecker);
                  }
                  if(size!=(size-=numRemoved)) {
                      root.modCount=++modCount;
                      this.modCount=modCount;
                      for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
                      this.size=size;
                      
                      return true;
                  }
                  return false;
              }
              catch(ConcurrentModificationException e){
                throw e;
              }catch(RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,root.modCount,e);
              }
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
        @Override
        public boolean removeIf(Predicate<? super Boolean> filter){
            int modCount=this.modCount;
            final var root=this.root;
            int size;
            if((size=this.size)!=0){
              try
              {
                  final int numRemoved,offset,wordOffset,bound,wordBound;
                  final var modCountChecker=root.new ModCountChecker(modCount);
                  if((wordOffset=(offset=this.rootOffset)>>6)==(wordBound=(bound=offset+size)-1>>6)) {
                      numRemoved=root.singleWordRemoveIfImpl(offset,size,filter::test,bound,wordBound,modCountChecker);
                  }else {
                      numRemoved=root.multiWordRemoveIfImpl(offset,size,filter::test,bound,wordOffset,wordBound,modCountChecker);
                  }
                  if(size!=(size-=numRemoved)) {
                      root.modCount=++modCount;
                      this.modCount=modCount;
                      for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
                      this.size=size;
                      return true;
                  }
                  return false;
              }
              catch(ConcurrentModificationException e){
                throw e;
              }catch(RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,root.modCount,e);
              }
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
        @Override
        public boolean removeVal(boolean val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        return this.uncheckedremoveVal(size,BitSetUtil.getWordFlipper(val));
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public boolean removeVal(double val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            long bits;
                            if(((bits=Double.doubleToRawLongBits(val)) & Long.MAX_VALUE) == 0){
                                wordFlipper=BitSetUtil::flip;
                            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return this.uncheckedremoveVal(size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public boolean removeVal(float val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(Float.floatToRawIntBits(val)){
                            default:
                                break returnFalse;
                            case 0:
                            case Integer.MIN_VALUE:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case TypeUtil.FLT_TRUE_BITS:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return this.uncheckedremoveVal(size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public boolean removeVal(int val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(val){
                            default:
                                break returnFalse;
                            case 0:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case 1:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return this.uncheckedremoveVal(size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public boolean removeVal(long val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val == 0L){
                                wordFlipper=BitSetUtil::flip;
                            }else if(val == 1L){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return this.uncheckedremoveVal(size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public void replaceAll(BooleanPredicate operator){
            int modCount=this.modCount;
            final var root=this.root;
            try{
                final int size;
                if((size=this.size) == 0){
                    return;
                }
                final int rootOffset;
                ((UncheckedList)root).uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset + size,operator);
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
            root.modCount=++modCount;
            var curr=this;
            do{
                curr.modCount=modCount;
            }while((curr=curr.parent) != null);
        }
        @Override
        public void replaceAll(UnaryOperator<Boolean> operator){
            int modCount=this.modCount;
            final var root=this.root;
            try{
                final int size;
                if((size=this.size) == 0){
                    return;
                }
                final int rootOffset;
                ((UncheckedList)root).uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset + size,operator::apply);
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
            root.modCount=++modCount;
            var curr=this;
            do{
                curr.modCount=modCount;
            }while((curr=curr.parent) != null);
        }
        @Override
        public boolean set(int index,boolean val){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            CheckedCollection.checkLo(index);
            CheckedCollection.checkReadHi(index,size);
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
        public int size(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return size;
        }
        @Override
        public void sort(BooleanComparator sorter){
            final int size;
            if((size=this.size)<2){
              CheckedCollection.checkModCount(modCount,root.modCount);
              return;
            }
            final var root=this.root;
            int modCount=this.modCount;
            if(sorter==null){
              CheckedCollection.checkModCount(modCount,root.modCount);
              ((UncheckedList)root).uncheckedStableAscendingSort(rootOffset,size);
              root.modCount=++modCount;
              this.modCount=modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            }else{
              try{
                  final int wordOffset;
                  final int bound;
                  final int wordBound;
                  final int offset;
                  if((wordOffset=(offset=this.rootOffset)>>6)==(wordBound=(bound=offset+size)-1>>6)) {
                      ((UncheckedList)root).singleWordSort(offset,size,bound,wordBound,sorter);
                  }else {
                      ((UncheckedList)root). multiWordSort(offset,wordOffset,bound,wordBound,sorter);
                  }
              }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
              }
            }
          }
        @Override
        public void sort(Comparator<? super Boolean> sorter){
            final int size;
            if((size=this.size)<2){
              CheckedCollection.checkModCount(modCount,root.modCount);
              return;
            }
            final var root=this.root;
            int modCount=this.modCount;
            if(sorter==null){
              CheckedCollection.checkModCount(modCount,root.modCount);
              ((UncheckedList)root).uncheckedStableAscendingSort(rootOffset,size);
              root.modCount=++modCount;
              this.modCount=modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            }else{
              try{
                  final int wordOffset;
                  final int bound;
                  final int wordBound;
                  final int offset;
                  if((wordOffset=(offset=this.rootOffset)>>6)==(wordBound=(bound=offset+size)-1>>6)) {
                      ((UncheckedList)root).singleWordSort(offset,size,bound,wordBound,sorter::compare);
                  }else {
                      ((UncheckedList)root). multiWordSort(offset,wordOffset,bound,wordBound,sorter::compare);
                  }
              }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
              }
            }
          }
        @Override
        public void stableAscendingSort(){
            final int size;
            if((size=this.size)<2){
              CheckedCollection.checkModCount(modCount,root.modCount);
              return;
            }
            int modCount=this.modCount;
            final var root=this.root;
            try{
              ((UncheckedList)root).uncheckedStableAscendingSort(rootOffset,size);
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
              root.modCount=++modCount;
              this.modCount=modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
            }
          }
        @Override
        public void stableDescendingSort(){
            final int size;
            if((size=this.size)<2){
              CheckedCollection.checkModCount(modCount,root.modCount);
              return;
            }
            int modCount=this.modCount;
            final var root=this.root;
            try{
                ((UncheckedList)root).uncheckedStableDescendingSort(rootOffset,size);
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
              root.modCount=++modCount;
              this.modCount=modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
            }
          }
        @Override
        public OmniList.OfBoolean subList(int fromIndex,int toIndex){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return new CheckedSubList(this,rootOffset + fromIndex,
                    CheckedCollection.checkSubListRange(fromIndex,toIndex,size));
        }
        @Override
        public Boolean[] toArray(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final Boolean[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new Boolean[size]);
                return dst;
            }
            return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            final CheckedList root;
            final T[] dst;
            final int size;
            final var modCount=this.modCount;
            try{
                dst=arrConstructor.apply(size=this.size);
            }finally{
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            }
            if(size != 0){
                final int rootOffset;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,dst);
            }
            return dst;
        }
        @Override
        public <T> T[] toArray(T[] dst){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=OmniArray.uncheckedArrResize(size,dst));
            }else if(dst.length != 0){
                dst[0]=null;
            }
            return dst;
        }
        @Override
        public boolean[] toBooleanArray(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final boolean[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new boolean[size]);
                return dst;
            }
            return OmniArray.OfBoolean.DEFAULT_ARR;
        }
        @Override
        public byte[] toByteArray(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final byte[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new byte[size]);
                return dst;
            }
            return OmniArray.OfByte.DEFAULT_ARR;
        }
        @Override
        public char[] toCharArray(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final char[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new char[size]);
                return dst;
            }
            return OmniArray.OfChar.DEFAULT_ARR;
        }
        @Override
        public double[] toDoubleArray(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final double[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new double[size]);
                return dst;
            }
            return OmniArray.OfDouble.DEFAULT_ARR;
        }
        @Override
        public float[] toFloatArray(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final float[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new float[size]);
                return dst;
            }
            return OmniArray.OfFloat.DEFAULT_ARR;
        }
        @Override
        public int[] toIntArray(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final int[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new int[size]);
                return dst;
            }
            return OmniArray.OfInt.DEFAULT_ARR;
        }
        @Override
        public long[] toLongArray(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final long[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new long[size]);
                return dst;
            }
            return OmniArray.OfLong.DEFAULT_ARR;
        }
        @Override
        public short[] toShortArray(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final short[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new short[size]);
                return dst;
            }
            return OmniArray.OfShort.DEFAULT_ARR;
        }
        @Override
        public String toString(){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            int size;
            if((size=this.size) != 0){
                final byte[] buffer;
                final var rootOffset=this.rootOffset;
                if(size <= OmniArray.MAX_ARR_SIZE / 7){
                    (buffer=new byte[size * 7])[size=((UncheckedList)root).uncheckedToString(rootOffset,
                            rootOffset + size,buffer)]=(byte)']';
                    buffer[0]=(byte)'[';
                    return new String(buffer,0,size + 1,ToStringUtil.IOS8859CharSet);
                }else{
                    final ToStringUtil.OmniStringBuilderByte builder;
                    ((UncheckedList)root).uncheckedToString(rootOffset,rootOffset + size,
                            builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
                    builder.uncheckedAppendChar((byte)']');
                    (buffer=builder.buffer)[0]=(byte)'[';
                    return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
                }
            }
            return "[]";
        }
        private boolean uncheckedremoveVal(int size,LongUnaryOperator wordFlipper){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            final var words=root.words;
            final int bound;
            int offset;
            final var wordBound=(bound=(offset=rootOffset) + size - 1) >> 6;
            var wordOffset=offset >> 6;
            long word;
            int tail0s;
            goToRemoveIndex:for(;;){
                if((offset&=63) != 0){
                    tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(word=words[wordOffset]) & -1L<<offset);
                    if(wordOffset == wordBound){
                        if(tail0s <=(bound&63)){
                            break goToRemoveIndex;
                        }
                        return false;
                    }else if(tail0s != 64){
                        break goToRemoveIndex;
                    }
                    ++wordOffset;
                }
                for(;wordOffset < wordBound;++wordOffset){
                    if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(word=words[wordOffset]))) != 64){
                        break goToRemoveIndex;
                    }
                }
                if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(word=words[wordOffset]))) <= (bound
                        & 63)){
                    break goToRemoveIndex;
                }
                return false;
            }
            removeIndexShiftDown(words,tail0s,wordOffset,word,--root.size);
            root.modCount=++modCount;
            this.modCount=modCount;
            this.size=size - 1;
            for(var curr=parent;curr != null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
            return true;
        }
        private Object writeReplace(){
            final CheckedList root;
            return new SerializableSubList((root=this.root).words,size,rootOffset,root.new ModCountChecker(modCount));
        }
        private static class Itr extends AbstractBooleanItr{
            transient final CheckedSubList parent;
            transient int cursor;
            transient int lastRet;
            transient int modCount;
            private Itr(CheckedSubList parent){
                this.parent=parent;
                cursor=parent.rootOffset;
                modCount=parent.modCount;
                lastRet=-1;
            }
            private Itr(CheckedSubList parent,int cursor){
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
                final CheckedSubList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).rootOffset + parent.size)){
                    final int modCount=this.modCount;
                    final var root=parent.root;
                    try{
                        ((UncheckedList)root).uncheckedForEach(cursor,bound,action);
                    }finally{
                        CheckedCollection.checkModCount(modCount,root.modCount,cursor,this.cursor);
                    }
                    this.cursor=bound;
                    lastRet=bound - 1;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                final int cursor;
                final int bound;
                final CheckedSubList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).rootOffset + parent.size)){
                    final int modCount=this.modCount;
                    final var root=parent.root;
                    try{
                        ((UncheckedList)root).uncheckedForEach(cursor,bound,action::accept);
                    }finally{
                        CheckedCollection.checkModCount(modCount,root.modCount,cursor,this.cursor);
                    }
                    this.cursor=bound;
                    lastRet=bound - 1;
                }
            }
            @Override
            public boolean hasNext(){
                final CheckedSubList parent;
                return cursor < (parent=this.parent).rootOffset + parent.size;
            }
            @Override
            public boolean nextBoolean(){
                final CheckedList root;
                final CheckedSubList parent;
                CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
                final int cursor;
                if((cursor=this.cursor) < parent.rootOffset + parent.size){
                    lastRet=cursor;
                    this.cursor=cursor + 1;
                    return (root.words[cursor >> 6] >> cursor & 1) != 0;
                }
                throw new NoSuchElementException();
            }
            @Override
            public void remove(){
                final int lastRet;
                if((lastRet=this.lastRet) != -1){
                    int modCount;
                    final CheckedList root;
                    CheckedSubList parent;
                    CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
                    root.modCount=++modCount;
                    do{
                        parent.modCount=modCount;
                        --parent.size;
                    }while((parent=parent.parent) != null);
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
            private ListItr(CheckedSubList parent){
                super(parent);
            }
            private ListItr(CheckedSubList parent,int cursor){
                super(parent,cursor);
            }
            private ListItr(ListItr itr){
                super(itr);
            }
            @Override
            public void add(boolean val){
                int modCount;
                final CheckedList root;
                CheckedSubList parent;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
                root.modCount=++modCount;
                do{
                    parent.modCount=modCount;
                    ++parent.size;
                }while((parent=parent.parent) != null);
                this.modCount=modCount;
                lastRet=-1;
                final int rootSize;
                if((rootSize=root.size) != 0){
                    ((UncheckedList)root).uncheckedInsert(cursor++,rootSize,val);
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
            public boolean hasPrevious(){
                return cursor > parent.rootOffset;
            }
            @Override
            public int nextIndex(){
                return cursor - parent.rootOffset;
            }
            @Override
            public boolean previousBoolean(){
                final CheckedList root;
                int cursor;
                final CheckedSubList parent;
                CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
                if((cursor=this.cursor) > parent.rootOffset){
                    lastRet=--cursor;
                    this.cursor=cursor;
                    return (root.words[cursor >> 6] >> cursor & 1) != 0;
                }
                throw new NoSuchElementException();
            }
            @Override
            public int previousIndex(){
                return cursor - parent.rootOffset - 1;
            }
            @Override
            public void set(boolean val){
                final int lastRet;
                if((lastRet=this.lastRet) != -1){
                    final CheckedList root;
                    CheckedCollection.checkModCount(modCount,(root=parent.root).modCount);
                    if(val){
                        root.words[lastRet >> 6]|=1L << lastRet;
                    }else{
                        root.words[lastRet >> 6]&=~(1L << lastRet);
                    }
                    return;
                }
                throw new IllegalStateException();
            }
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
                    final long[] words;
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
                        final int rootOffset;
                        final int wordBound=(rootOffset=this.rootOffset) + size - 1 >> 6;
                        int wordIndex=rootOffset >> 6;
                        final var words=this.words;
                        if((rootOffset & 63) == 0){
                            // alligned
                            OmniArray.OfLong.writeArray(words,wordIndex,wordBound,oos);
                        }else{
                            // unalligned
                            long word;
                            for(word=words[wordIndex];wordIndex != wordBound;oos
                                    .writeLong(word >>> rootOffset | (word=words[++wordIndex]) << -rootOffset)){}
                            oos.writeLong(word >>> rootOffset);
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
        
        private static void removeAllTrue(long[] words,int offset,int numRemoved,int bound,int rootBound) {
            //TODO bugtest
            int wordOffset;
            final int wordBound=bound-1>>6;
            final int falseWordBound;
            if((falseWordBound=(bound-=numRemoved)-1>>6)==(wordOffset=offset>>6)) {
                final long word;
                words[wordOffset]=words[wordOffset]&(1L<<offset)-1|(word=words[wordBound])<<bound;
                removeIfShiftDown(rootBound,numRemoved,wordOffset+1,wordBound,words,word);
            }else {
                words[wordOffset]=words[wordOffset]&(1L<<offset)-1;
                setRange(words,wordOffset+1,falseWordBound,0L);
                removeIfShiftDown(rootBound,numRemoved,falseWordBound,wordBound,words,words[wordBound]>>>numRemoved);
            }
        }
        private static void removeAllFalse(long[] words,int offset,int numRemoved,int bound,int rootBound) {
            //TODO bugtest
            int wordOffset=offset>>6;
            int wordBound=bound-1>>6;
            int numRetained=(bound-=numRemoved)-offset;
            int trueWordBound=bound-1>>6;
            if(trueWordBound==wordOffset) {
                final long word;
                words[wordOffset]=words[wordOffset]&(1L<<offset)-1|(1L<<numRetained)-1|(word=words[wordBound])<<bound;
                removeIfShiftDown(rootBound,numRemoved,wordOffset+1,wordBound,words,word);
            }else {
                words[wordOffset]=words[wordOffset]&(1L<<offset)-1|-1L<<offset;
                setRange(words,wordOffset+1,trueWordBound,-1L);
                removeIfShiftDown(rootBound,numRemoved,trueWordBound,wordBound,words,words[wordBound]>>>numRemoved|(1L<<bound)-1);
            }
        }
        private int multiWordRemoveIfImpl(int offset,int size,BooleanPredicate filter,int bound,int wordOffset,int wordBound) {
            final var words=this.words;
            long word;
            int i;
            int bitCount=Long.bitCount((word=words[wordOffset])>>>offset);
            goToRemoveAll:for(;;) {
                if(bitCount==0) {
                    boolean removeFalse=filter.test(false);
                    for(i=wordOffset;++i<wordBound;) {
                        if((bitCount=Long.bitCount(words[i]))!=0) {
                            if(filter.test(true)) {
                                if(removeFalse) {
                                    break goToRemoveAll;
                                }
                                removeAllTrue(words,offset,bitCount+=countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound),bound,size=this.size);
                                this.size=size-bitCount;
                                return bitCount;
                            }else if(removeFalse) {
                                removeAllFalse(words,offset,bitCount=size-(bitCount+countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound)),bound,size=this.size);
                                this.size=size-bitCount;
                                return bitCount;
                            }
                            return 0;
                        }
                    }
                    if((bitCount=Long.bitCount(words[wordBound]<<-bound))==0) {
                        if(removeFalse) {
                            break goToRemoveAll;
                        }
                    }else {
                        if(filter.test(true)) {
                            if(removeFalse) {
                                break goToRemoveAll;
                            }
                            removeAllTrue(words,offset,bitCount,bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }else if(removeFalse) {
                            removeAllFalse(words,offset,bitCount=size-bitCount,bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }
                    }
                   
                }else if(bitCount==64-(offset&63)) {
                    boolean removeTrue=filter.test(true);
                    for(i=wordOffset;++i<wordBound;) {
                        int numTrue;
                        bitCount+=numTrue=Long.bitCount(words[i]);
                        if(numTrue!=64) {
                            if(filter.test(false)) {
                                if(removeTrue) {
                                    break goToRemoveAll;
                                }
                                removeAllFalse(words,offset,bitCount=size-(bitCount+countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound)),bound,size=this.size);
                                this.size=size-bitCount;
                                return bitCount;
                            }else if(removeTrue) {
                                removeAllTrue(words,offset,bitCount+=countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound),bound,size=this.size);
                                this.size=size-bitCount;
                                return bitCount;
                            }
                            return 0;
                        }
                    }
                    if((bitCount+=Long.bitCount(words[wordBound]<<-bound))==size) {
                        if(removeTrue) {
                            break goToRemoveAll;
                        }
                    }else {
                        if(filter.test(false)) {
                            if(removeTrue) {
                                break goToRemoveAll;
                            }
                            removeAllFalse(words,offset,bitCount=size-bitCount,bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }else if(removeTrue) {
                            removeAllTrue(words,offset,bitCount,bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }
                    }
                  
                }else {
                    i=wordOffset;
                    if(filter.test(false)) {
                        if(filter.test(true)) {
                            break goToRemoveAll;
                        }else {
                            removeAllFalse(words,offset,bitCount=size-(bitCount+countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound)),bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }
                        
                    }else {
                        if(filter.test(true)) {
                            removeAllTrue(words,offset,bitCount+=countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound),bound,size=this.size);
                            this.size=size-bitCount;
                            return bitCount;
                        }
                       
                    }
                }
                return 0;
            } 
            removeIfShiftDown(bitCount=this.size,size,wordOffset,wordBound,words,word&(1L<<offset)-1 | words[wordBound]&~((1L<<bound)-1)>>>size);
            this.size=bitCount-size;
            return size;
        }
        
      
        
       
        private int singleWordRemoveIfImpl(int offset,int size,BooleanPredicate filter,int bound,int wordBound){
            final var words=this.words;
            long word;
            int bitCount;
            goToRemoveAll:for(;;) {
                int numFalse;
                if((bitCount=Long.bitCount((word=words[wordBound])&-1L>>>-size<<offset))==0) {
                    if(filter.test(false)) {
                        
                        break goToRemoveAll;
                    }
                }else if((numFalse=size-bitCount)==0) {
                    if(filter.test(true)) {
                       break goToRemoveAll;
                    }
                }else {
                    if(filter.test(false)) {
                        if(filter.test(true)) {
                            break goToRemoveAll;
                        }else {
                            removeIfShiftDown(size=this.size,numFalse,wordBound,wordBound,words,word&(1L<<offset)-1 | (1L<<bitCount)-1<<offset | (word&~((1L<<bound)-1))>>>numFalse);
                            this.size=size-numFalse;
                            return numFalse;
                        }
                    }else if(filter.test(true)) {
                        removeIfShiftDown(size=this.size,bitCount,wordBound,wordBound,words,word&(1L<<offset)-1 | (word&~(-1L>>>-bound))>>>bitCount);
                        this.size=size-bitCount;
                        return bitCount;
                    }
                }
                return 0;
            }
            removeIfShiftDown(bitCount=this.size,size,wordBound,wordBound,words,word&(1L<<offset)-1 | (word&~((1L<<bound)-1))>>>size);
            this.size=bitCount-size;
            return size;
        }

        private static void removeIfShiftDown(int rootSize,int numRemoved,int wordOffset,int wordBound,final long[] words,long word){
            for(int rootWordBound=rootSize-1>>6;wordBound!=rootWordBound;word>>>=numRemoved,++wordOffset) {
                words[wordOffset]=word|(word=words[++wordBound])<<-numRemoved;
            }
            words[wordOffset]=word;
        }
        

        private void multiWordSort(int offset,final int wordOffset,final int bound,
                int wordBound,BooleanComparator sorter){
            final var words=this.words;
            int i;
            int bitCount;
            outer:for(;;) {
                if((bitCount=Long.bitCount(words[wordOffset]>>>offset))==0) {
                    for(i=wordOffset;++i<wordBound;) {
                        if((bitCount=Long.bitCount(words[i]))!=0) {
                            break outer;
                        }
                    }
                    if((bitCount=Long.bitCount(words[wordBound]&-1L>>>-bound))==0) {
                        return;
                    }
                }else if(bitCount == 64 - (offset&63)) {
                    for(i=wordOffset;++i<wordBound;) {
                        int numTrue;
                        bitCount+=numTrue=Long.bitCount(words[i]);
                        if(numTrue!=64) {
                           break outer;
                        }
                    }
                    int numTrue;
                    bitCount+=numTrue=Long.bitCount(words[wordBound]&-1L>>>-bound);
                    if(numTrue==64-(-bound&63)) {
                        return;
                    }
                }else {
                    switch(Integer.signum(sorter.compare(false,true))) {
                    case -1:
                        falseComesFirst(words,bitCount+countRemainingBits(words,wordOffset+1,wordBound)+Long.bitCount(words[wordBound]<<-bound),offset,bound);
                    case 0:
                        //already sorted
                        return;
                    default:
                        trueComesFirst(words,bitCount+countRemainingBits(words,wordOffset+1,wordBound)+Long.bitCount(words[wordBound]<<-bound),offset,bound);
                    }
                    return;
                }
                switch(Integer.signum(sorter.compare(false,true))) {
                case -1:
                    falseComesFirst(words,bitCount,offset,bound);
                case 0:
                    //already sorted
                    break;
                default:
                    trueComesFirst(words,bitCount,offset,bound);
                }
                return;
            }
            switch(Integer.signum(sorter.compare(false,true))) {
            case -1:
                falseComesFirst(words,bitCount+countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound),offset,bound);
            case 0:
                //already sorted
                break;
            default:
                trueComesFirst(words,bitCount+countRemainingBits(words,i+1,wordBound)+Long.bitCount(words[wordBound]<<-bound),offset,bound);
            }
        }

        private void singleWordSort(int offset,int size,final int bound,int wordBound,BooleanComparator sorter){
            final var words=this.words;
            final long mask,word;
            final int bitCount;
            if((bitCount=Long.bitCount((word=words[wordBound])&(mask=-1L>>>-size<<offset)))!=0 && bitCount!=size) {
                switch(Integer.signum(sorter.compare(false,true))) {
                case -1:
                    words[wordBound]=word&~mask | -1L<<-bitCount>>>-bound;
                case 0:
                    break;
                default:
                    words[wordBound]=word&~mask | -1L>>>-bitCount<<offset;
                }
            }
        }
        private static void falseComesFirst(long[] words,int falseCount,int wordBound){
            int wordIndex;
            words[wordIndex=falseCount >> 6]=-1L << falseCount;
            setRange(words,0,wordIndex,0);
            setRange(words,wordIndex+1,wordBound+1,-1L);
        }
        private void uncheckedStableDescendingSort(int offset,int size) {
            final var words=this.words;
            int wordBound;
            final int bound,wordOffset;
            if((wordOffset=offset>>6)==(wordBound=(bound=offset+size)-1>>6)) {
                final long mask,word;
                final int bitCount;
                if((bitCount=Long.bitCount((word=words[wordBound])&(mask=-1L>>>-size<<offset)))!=0 && bitCount!=size) {
                    words[wordBound]=word&~mask | -1L>>>-bitCount<<offset;
                }
            }else {
                int bitCount=Long.bitCount(words[wordBound]&-1L>>>-bound);
                for(int i=wordBound-1;i>wordOffset;--i) {
                    bitCount+=Long.bitCount(words[i]);
                }
                if((bitCount+=Long.bitCount(words[wordOffset]&-1L<<offset))!=0 && bitCount!=size) {
                    trueComesFirst(words,bitCount,offset,bound);
                }
            }
        }
        
        private void uncheckedStableAscendingSort(int offset,int size){
            final var words=this.words;
            int wordOffset;
            final int bound,wordBound;
            if((wordOffset=offset>>6)==(wordBound=(bound=offset+size)-1>>6)) {
                final long mask,word;
                final int bitCount;
                if((bitCount=Long.bitCount((word=words[wordBound])&(mask=-1L>>>-size<<offset)))!=0 && bitCount!=size) {
                    words[wordBound]=word&~mask | -1L<<-bitCount>>>-bound;
                }
            }else {
                int bitCount=Long.bitCount(words[wordBound]&-1L>>>-bound);
                for(int i=wordBound-1;i>wordOffset;--i) {
                    bitCount+=Long.bitCount(words[i]);
                }
                if((bitCount+=Long.bitCount(words[wordOffset]&-1L<<offset))!=0 && bitCount!=size) {
                   falseComesFirst(words,bitCount,offset,bound);
                }
            }
        }
        private static void trueComesFirst(long[] words,int bitCount,int offset,int bound) {
            final int wordOffset;
            int wordBound;
            final int trueBound;
            final int trueWordBound;
            final long headWord=words[wordOffset=offset>>6]&~(-1L<<offset);
            final long tailWord=words[wordBound=bound-1>>6]&~(-1L>>>-bound);
            if((trueWordBound=(trueBound=bitCount+offset)-1>>6)==wordBound) {
                words[wordBound]=tailWord|-1L>>>-trueBound;
                setRange(words,wordOffset+1,wordBound,-1L);
            }else {
                words[wordBound]=tailWord;
                if(trueWordBound==wordOffset) {
                    setRange(words,wordOffset+1,wordBound,0L);
                    words[wordOffset]=headWord|-1L>>>-bitCount<<offset;
                    return;
                }else {
                    setRange(words,trueWordBound+1,wordBound,0L);
                    words[trueWordBound]=-1L>>>-trueBound;
                    setRange(words,wordOffset+1,trueWordBound,-1L);
                }
            }
            words[wordOffset]=headWord|-1L<<offset;
        }
        private static void falseComesFirst(long[] words,int bitCount,int offset,int bound) {
            int wordOffset;
            final int wordBound;
            final int falseBound;
            final int falseWordBound;
            final long headWord=words[wordOffset=offset>>6]&~(-1L<<offset);
            final long tailWord=words[wordBound=bound-1>>6]&~(-1L>>>-bound);
            if((falseWordBound=(falseBound=bound-bitCount)-1>>6)==wordOffset) {
                words[wordOffset]=headWord|~(-1L>>>-falseBound);
                setRange(words,wordOffset+1,wordBound,-1L);
            }else {
                words[wordOffset]=headWord;
                if(falseWordBound==wordBound) {
                    setRange(words,wordOffset+1,wordBound,0L);
                    words[wordBound]=tailWord|-1L>>>-bitCount<<falseBound;
                    return;
                }else {
                    setRange(words,wordOffset+1,falseWordBound,0L);
                    words[falseWordBound]=-1L<<falseBound;
                    setRange(words,falseWordBound+1,wordBound,-1L);
                }
            }
            words[wordBound]=tailWord|-1L>>>-bound;
        }
        
        private void uncheckedStableAscendingSort(int size){
            final long[] words;
            int wordBound;
            int bitCount=Long.bitCount((words=this.words)[wordBound=size - 1 >> 6] << -size);
            for(int i=wordBound - 1;i >= 0;--i){
                bitCount+=Long.bitCount(words[i]);
            }
            if(bitCount != 0 && bitCount != size){
                falseComesFirst(words,size - bitCount,wordBound);
            }
        }
        private static long[] growAndInsert(int index,int size,boolean val,final long[] words){
            int wordIndex;
            var word=words[wordIndex=index >> 6];
            final long[] tmp;
            ArrCopy.semicheckedCopy(words,0,tmp=new long[OmniArray.growBy50Pct(size)],0,wordIndex);
            var mask=1L << index;
            if(val){
                tmp[wordIndex]=mask | --mask & word | ~mask & word << 1;
            }else{
                tmp[wordIndex]=~mask & (--mask & word | ~mask & word << 1);
            }
            for(final int wordBound=size - 1;wordIndex < wordBound;){
                tmp[++wordIndex]=word >>> 63 | (word=words[wordIndex]) << 1;
            }
            tmp[wordIndex + 1]=word >>> 63;
            return tmp;
        }
        private static void insertNoGrow(int index,int size,boolean val,final long[] words){
            int wordIndex;
            var word=words[wordIndex=index >> 6];
            var mask=1L << index;
            if(val){
                words[wordIndex]=mask | --mask & word | ~mask & word << 1;
            }else{
                words[wordIndex]=~mask & (--mask & word | ~mask & word << 1);
            }
            while(wordIndex < size){
                words[++wordIndex]=word >>> 63 | (word=words[wordIndex]) << 1;
            }
        }
        private static void trueComesFirst(long[] words,int trueCount,int wordBound){
            int wordIndex;
            words[wordIndex=trueCount >> 6]=(1L << trueCount) - 1;
            setRange(words,0,wordIndex,-1L);
            setRange(words,wordIndex+1,wordBound+1,0L);
        }
        private static void uncheckedCopyInto(long[] words,int srcOffset,int srcBound,boolean[] dst){
            for(int dstOffset=0;;){
                final var word=words[srcOffset >> 6];
                do{
                    dst[dstOffset]=(word >> srcOffset & 1) != 0;
                    if(++srcOffset == srcBound){
                        return;
                    }
                    ++dstOffset;
                }while((srcOffset & 63) != 0);
            }
        }
        private static void uncheckedCopyInto(long[] words,int srcOffset,int srcBound,Boolean[] dst){
            for(int dstOffset=0;;){
                final var word=words[srcOffset >> 6];
                do{
                    dst[dstOffset]=(word >> srcOffset & 1) != 0;
                    if(++srcOffset == srcBound){
                        return;
                    }
                    ++dstOffset;
                }while((srcOffset & 63) != 0);
            }
        }
        private static void uncheckedCopyInto(long[] words,int srcOffset,int srcBound,byte[] dst){
            for(int dstOffset=0;;){
                final var word=words[srcOffset >> 6];
                do{
                    dst[dstOffset]=(byte)(word >> srcOffset & 1);
                    if(++srcOffset == srcBound){
                        return;
                    }
                    ++dstOffset;
                }while((srcOffset & 63) != 0);
            }
        }
        private static void uncheckedCopyInto(long[] words,int srcOffset,int srcBound,char[] dst){
            for(int dstOffset=0;;){
                final var word=words[srcOffset >> 6];
                do{
                    dst[dstOffset]=(char)(word >> srcOffset & 1);
                    if(++srcOffset == srcBound){
                        return;
                    }
                    ++dstOffset;
                }while((srcOffset & 63) != 0);
            }
        }
        private static void uncheckedCopyInto(long[] words,int srcOffset,int srcBound,double[] dst){
            for(int dstOffset=0;;){
                final var word=words[srcOffset >> 6];
                do{
                    dst[dstOffset]=word >> srcOffset & 1;
                    if(++srcOffset == srcBound){
                        return;
                    }
                    ++dstOffset;
                }while((srcOffset & 63) != 0);
            }
        }
        private static void uncheckedCopyInto(long[] words,int srcOffset,int srcBound,float[] dst){
            for(int dstOffset=0;;){
                final var word=words[srcOffset >> 6];
                do{
                    dst[dstOffset]=word >> srcOffset & 1;
                    if(++srcOffset == srcBound){
                        return;
                    }
                    ++dstOffset;
                }while((srcOffset & 63) != 0);
            }
        }
        private static void uncheckedCopyInto(long[] words,int srcOffset,int srcBound,int[] dst){
            for(int dstOffset=0;;){
                final var word=words[srcOffset >> 6];
                do{
                    dst[dstOffset]=(int)(word >> srcOffset & 1);
                    if(++srcOffset == srcBound){
                        return;
                    }
                    ++dstOffset;
                }while((srcOffset & 63) != 0);
            }
        }
        private static void uncheckedCopyInto(long[] words,int srcOffset,int srcBound,long[] dst){
            for(int dstOffset=0;;){
                final var word=words[srcOffset >> 6];
                do{
                    dst[dstOffset]=word >> srcOffset & 1;
                    if(++srcOffset == srcBound){
                        return;
                    }
                    ++dstOffset;
                }while((srcOffset & 63) != 0);
            }
        }
        private static void uncheckedCopyInto(long[] words,int srcOffset,int srcBound,Object[] dst){
            for(int dstOffset=0;;){
                final var word=words[srcOffset >> 6];
                do{
                    dst[dstOffset]=(word >> srcOffset & 1) != 0;
                    if(++srcOffset == srcBound){
                        return;
                    }
                    ++dstOffset;
                }while((srcOffset & 63) != 0);
            }
        }
        private static void uncheckedCopyInto(long[] words,int srcOffset,int srcBound,short[] dst){
            for(int dstOffset=0;;){
                final var word=words[srcOffset >> 6];
                do{
                    dst[dstOffset]=(short)(word >> srcOffset & 1);
                    if(++srcOffset == srcBound){
                        return;
                    }
                    ++dstOffset;
                }while((srcOffset & 63) != 0);
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
            uncheckedCopyInto(words,0,length,dst);
        }
        @Override
        void uncheckedCopyInto(Boolean[] dst,int length){
            uncheckedCopyInto(words,0,length,dst);
        }
        @Override
        void uncheckedCopyInto(byte[] dst,int length){
            uncheckedCopyInto(words,0,length,dst);
        }
        @Override
        void uncheckedCopyInto(char[] dst,int length){
            uncheckedCopyInto(words,0,length,dst);
        }
        @Override
        void uncheckedCopyInto(double[] dst,int length){
            uncheckedCopyInto(words,0,length,dst);
        }
        @Override
        void uncheckedCopyInto(float[] dst,int length){
            uncheckedCopyInto(words,0,length,dst);
        }
        @Override
        void uncheckedCopyInto(int[] dst,int length){
            uncheckedCopyInto(words,0,length,dst);
        }
        @Override
        void uncheckedCopyInto(long[] dst,int length){
            uncheckedCopyInto(words,0,length,dst);
        }
        @Override
        void uncheckedCopyInto(Object[] dst,int length){
            uncheckedCopyInto(words,0,length,dst);
        }
        @Override
        void uncheckedCopyInto(short[] dst,int length){
            uncheckedCopyInto(words,0,length,dst);
        }
        @Override
        void uncheckedForEach(int size,BooleanConsumer action){
            uncheckedForEach(0,size,action);
        }
        @Override
        int uncheckedHashCode(int size){
            return uncheckedHashCode(0,size);
        }
        @Override
        boolean uncheckedremoveVal(int size,boolean val){
            final var words=this.words;
            final int bound;
            int tail0s;
            final var wordFlipper=BitSetUtil.getWordFlipper(val);
            int wordOffset=0;
            for(final var wordBound=(bound=size - 1) >> 6;wordOffset < wordBound;++wordOffset){
                long word;
                if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(word=words[wordOffset]))) != 64){
                    words[wordOffset]=BitSetUtil.partialWordShiftDown(word,tail0s) | (word=words[++wordOffset]) << 63;
                    while(wordOffset != wordBound){
                        words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
                    }
                    words[wordBound]=word >>> 1;
                    this.size=bound;
                    return true;
                }
            }
            final long finalWord;
            final long adjustedFinalWord=wordFlipper.applyAsLong(finalWord=words[wordOffset]);
            if((tail0s=Long.numberOfTrailingZeros(
                    (size & 63) == 0?adjustedFinalWord:adjustedFinalWord & (1L << size) - 1)) != 64){
                words[wordOffset]=BitSetUtil.partialWordShiftDown(finalWord,tail0s);
                this.size=bound;
                return true;
            }
            return false;
        }
        @Override
        int uncheckedToString(int size,byte[] buffer){
            return uncheckedToString(0,size,buffer);
        }
        @Override
        void uncheckedToString(int size,OmniStringBuilderByte builder){
            uncheckedToString(0,size,builder);
        }
        private void uncheckedForEach(int offset,int bound,BooleanConsumer action){
            for(final var words=this.words;;){
                final var word=words[offset >> 6];
                do{
                    action.accept((word >> offset & 1) != 0);
                    if(++offset == bound){
                        return;
                    }
                }while((offset & 63) != 0);
            }
        }
        private int uncheckedHashCode(int offset,int bound){
            final long[] words;
            long word;
            int hash=((word=(words=this.words)[offset >> 6]) >> offset & 1) != 0?31 + 1231:31 + 1237;
            while(++offset != bound){
                if((offset & 63) == 0){
                    word=words[offset >> 6];
                }
                hash=hash * 31 + ((word >> offset & 1) != 0?1231:1237);
            }
            return hash;
        }
        private int uncheckedindexOf(int offset,int size,LongUnaryOperator wordFlipper){
            final var words=this.words;
            final int bound;
            final var wordBound=(bound=offset + size - 1) >> 6;
            var wordOffset=offset >> 6;
            int i;
            if((offset&=63) != 0){
                final var tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(words[wordOffset]) >>> offset);
                if(wordOffset == wordBound){
                    if(tail0s < size){
                        return tail0s;
                    }
                    return -1;
                }else if(tail0s != 64){
                    return tail0s;
                }
                i=++wordOffset;
            }else{
                i=wordOffset++;
            }
            for(;i < wordBound;++i){
                final int tail0s;
                if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(words[i]))) != 64){
                    return tail0s + 64 - offset + (i - wordOffset << 6);
                }
            }
            final int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(words[i]))) <= (bound & 63)){
                return tail0s + 64 - offset + (i - wordOffset << 6);
            }
            return -1;
        }
        private void uncheckedInsert(int index,int size,boolean val){
            if(size == index){
                super.uncheckedAppend(size,val);
            }else{
                this.size=size + 1;
                final long[] words;
                if((words=this.words).length == (size>>=6)){
                    this.words=growAndInsert(index,size,val,words);
                }else{
                    insertNoGrow(index,size,val,words);
                }
            }
        }
        private int uncheckedlastIndexOf(int offset,int size,LongUnaryOperator wordFlipper){
            final var words=this.words;
            int bound;
            var wordBound=(bound=offset + size) - 1 >> 6;
            final var wordOffset=offset >> 6;
            if((bound&=63) != 0){
                final var lead0s=Long.numberOfLeadingZeros(wordFlipper.applyAsLong(words[wordBound]) << -bound);
                if(--wordBound < wordOffset){
                    if((size-=lead0s) < 0){
                        return -1;
                    }
                    return size - 1;
                }
                if(lead0s != 64){
                    return size - lead0s - 1;
                }
                size-=bound;
            }
            for(;wordBound > wordOffset;--wordBound,size-=64){
                final int lead0s;
                if((lead0s=Long.numberOfLeadingZeros(wordFlipper.applyAsLong(words[wordBound]))) != 64){
                    return size - lead0s - 1;
                }
            }
            final int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(wordFlipper.applyAsLong(words[wordBound]))) < 64 - (offset & 63)){
                return size - lead0s - 1;
            }
            return -1;
        }
        private void uncheckedReplaceAll(int offset,int bound,BooleanPredicate operator){
            final long[] words;
            int wordOffset;
            for(var word=(words=this.words)[wordOffset=offset >> 6];;){
                final long mask;
                if(operator.test((word & (mask=1L << offset)) != 0)){
                    word|=mask;
                }else{
                    word&=~mask;
                }
                if(++offset == bound){
                    words[wordOffset]=word;
                    return;
                }
                if((offset & 63) == 0){
                    words[wordOffset]=word;
                    word=words[++wordOffset];
                }
            }
        }
        
        private static int countRemainingBits(long[] words,int wordOffset,int wordBound) {
            int bitCount=0;
            while(wordOffset<wordBound) {
                bitCount+=Long.bitCount(words[wordOffset]);
                ++wordOffset;
            }
            return bitCount;
        }
    
        
     
        
        
        private void uncheckedSort(int size,BooleanComparator sorter){
            int bitCount;
            int wordBound;
            final long[] words;
            if((bitCount=Long.bitCount((words=this.words)[wordBound=size - 1 >> 6] << -size)) == 0){
                for(int i=wordBound;--i>=0;) {
                   if((bitCount=Long.bitCount(words[i]))!=0) {
                       switch(Integer.signum(sorter.compare(false,true))) {
                       case -1:
                           falseComesFirst(words,size-(bitCount+countRemainingBits(words,0,i)),wordBound);
                       case 0:
                           break;
                       default:
                           trueComesFirst(words,bitCount+countRemainingBits(words,0,i),wordBound);
                       }
                       break;
                   }
                }
            }else if(bitCount == 64 - (-size & 63)){
                for(int i=wordBound;--i>=0;) {
                    int numTrue;
                    bitCount+=numTrue=Long.bitCount(words[i]);
                    if(numTrue!=64) {
                        switch(Integer.signum(sorter.compare(false,true))) {
                        case -1:
                            falseComesFirst(words,size-(bitCount+countRemainingBits(words,0,i)),wordBound);
                        case 0:
                            break;
                        default:
                            trueComesFirst(words,bitCount+countRemainingBits(words,0,i),wordBound);
                        }
                        break;
                    }
                }
            }else{
                switch(Integer.signum(sorter.compare(false,true))) {
                case -1:
                    falseComesFirst(words,size-(bitCount+countRemainingBits(words,0,wordBound)),wordBound);
                case 0:
                    break;
                default:
                    trueComesFirst(words,bitCount+countRemainingBits(words,0,wordBound),wordBound);
                }
            }
        }

        
        private void uncheckedStableDescendingSort(int size){
            final long[] words;
            int wordBound;
            int bitCount=Long.bitCount((words=this.words)[wordBound=size - 1 >> 6] << -size);
            for(int i=wordBound - 1;i >= 0;--i){
                bitCount+=Long.bitCount(words[i]);
            }
            if(bitCount != 0 && bitCount != size){
                trueComesFirst(words,bitCount,wordBound);
            }
        }
        private int uncheckedToString(int offset,int bound,byte[] buffer){
            final var words=this.words;
            for(int bufferOffset=1;;){
                final var word=words[offset >> 6];
                do{
                    bufferOffset=ToStringUtil.getStringBoolean((word >> offset & 1) != 0,buffer,bufferOffset);
                    if(++offset == bound){
                        return bufferOffset;
                    }
                    buffer[bufferOffset]=(byte)',';
                    buffer[++bufferOffset]=(byte)' ';
                    ++bufferOffset;
                }while((offset & 63) != 0);
            }
        }
        private void uncheckedToString(int offset,int bound,OmniStringBuilderByte builder){
            for(final var words=this.words;;){
                final var word=words[offset >> 6];
                do{
                    builder.uncheckedAppendBoolean((word >> offset & 1) != 0);
                    if(++offset == bound){
                        return;
                    }
                    builder.uncheckedAppendCommaAndSpace();
                }while((offset & 63) != 0);
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
                    parent.uncheckedForEach(cursor,size,action);
                    this.cursor=size;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                final int cursor,size;
                final UncheckedList parent;
                if((cursor=this.cursor) < (size=(parent=this.parent).size)){
                    parent.uncheckedForEach(cursor,size,action::accept);
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
                ((PackedBooleanArrSeq)parent).uncheckedRemoveIndexNoRet(--cursor);
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
                    parent.uncheckedForEach(cursor,bound,action);
                    lastRet=bound - 1;
                    this.cursor=bound;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                int cursor;
                final int bound;
                final UncheckedList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).size)){
                    parent.uncheckedForEach(cursor,bound,action::accept);
                    lastRet=bound - 1;
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
                final var lastRet=this.lastRet;
                final var words=parent.words;
                if(val){
                    words[lastRet >> 6]|=1L << lastRet;
                }else{
                    words[lastRet >> 6]&=~(1L << lastRet);
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
            int hash=((word=(words=this.words)[--size >> 6]) >> size & 1) != 0?31 + 1231:31 + 1237;
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
            final LongUnaryOperator wordFlipper;
            long word;
            if((lead0s=Long.numberOfLeadingZeros(
                    (wordFlipper=BitSetUtil.getWordFlipper(val)).applyAsLong(word=words[wordBound]) << -size)) == 64){
                for(int wordOffset=wordBound - 1;wordOffset >= 0;--wordOffset){
                    if((lead0s=Long.numberOfLeadingZeros(wordFlipper.applyAsLong(word=words[wordOffset]))) != 64){
                        words[wordOffset]=BitSetUtil.partialWordShiftDown(word,-lead0s - 1)
                                | (word=words[++wordOffset]) << 63;
                        while(wordOffset != wordBound){
                            words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
                        }
                        words[wordBound]=word >>> 1;
                        this.size=bound;
                        return true;
                    }
                }
                return false;
            }
            words[wordBound]=BitSetUtil.partialWordShiftDown(word,bound - lead0s);
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
            for(var curr=parent;curr != null;++curr.size,curr=curr.parent){}
            final UncheckedList root;
            final int rootSize;
            if((rootSize=(root=this.root).size) != 0){
                root.uncheckedInsert(rootOffset + size++,rootSize,val);
            }else{
                ((PackedBooleanArrSeq)root).uncheckedInit(val);
                ++size;
            }
            return true;
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
            int size;
            if((size=this.size) != 0){
                for(var curr=parent;curr != null;curr.size-=size,curr=curr.parent){}
                this.size=0;
                final UncheckedList root;
                final int offset,tailLength,rootBound;
                (root=this.root).size=(rootBound=root.size) - size;
                if((tailLength=rootBound - (size=(offset=rootOffset) + size)) != 0){
                    final var words=root.words;
                    if((offset & 63) == 0){
                        if((size & 63) == 0){
                            ArrCopy.uncheckedSelfCopy(words,size >> 6,offset >> 6,(tailLength - 1 >> 6) + 1);
                        }else{
                            BitSetUtil.srcUnallignedPullDown(words,offset >> 6,size,rootBound - 1 >> 6);
                        }
                    }else{
                        BitSetUtil.dstUnallignedPullDown(words,offset,size,rootBound - 1 >> 6);
                    }
                }
            }
        }
        @Override
        public Object clone(){
            final long[] copy,words;
            final int size;
            if((size=this.size) != 0){
                final int offset;
                int dstWordBound=size - 1 >> 6;
                words=root.words;
                var srcWordOffset=(offset=rootOffset) >> 6;
                if((offset & 63) == 0){
                    ArrCopy.uncheckedCopy(words,srcWordOffset,copy=new long[++dstWordBound],0,dstWordBound);
                }else{
                    copy=new long[dstWordBound + 1];
                    var word=words[srcWordOffset];
                    int srcWordBound;
                    if((srcWordBound=offset + size - 1 >> 6) - srcWordOffset > dstWordBound){
                        for(int dstWordOffset=0;;++dstWordOffset){
                            copy[dstWordOffset]=word >>> offset | (word=words[++srcWordOffset]) << -offset;
                            if(dstWordOffset == dstWordBound){
                                break;
                            }
                        }
                    }else{
                        for(int dstWordOffset=0;srcWordOffset != srcWordBound;++dstWordOffset){
                            copy[dstWordOffset]=word >>> offset | (word=words[++srcWordOffset]) << -offset;
                        }
                        copy[dstWordBound]=word >>> offset;
                    }
                }
            }else{
                copy=null;
            }
            return new UncheckedList(size,copy);
        }
        @Override
        public boolean contains(boolean val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,BitSetUtil.getWordFlipper(val));
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public boolean contains(double val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            long bits;
                            if(((bits=Double.doubleToRawLongBits(val)) & Long.MAX_VALUE) == 0){
                                wordFlipper=BitSetUtil::flip;
                            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public boolean contains(float val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(Float.floatToRawIntBits(val)){
                            default:
                                break returnFalse;
                            case 0:
                            case Integer.MIN_VALUE:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case TypeUtil.FLT_TRUE_BITS:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public boolean contains(int val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(val){
                            default:
                                break returnFalse;
                            case 0:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case 1:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public boolean contains(long val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val == 0L){
                                wordFlipper=BitSetUtil::flip;
                            }else if(val == 1L){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public boolean contains(Object val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        // TODO a pattern-matching switch statement would be great here
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val instanceof Boolean){
                                wordFlipper=BitSetUtil.getWordFlipper((boolean)val);
                            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                                switch(((Number)val).intValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Float){
                                switch(Float.floatToRawIntBits((float)val)){
                                default:
                                    break returnFalse;
                                case 0:
                                case Integer.MIN_VALUE:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case TypeUtil.FLT_TRUE_BITS:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Double){
                                final long bits;
                                if(((bits=Double.doubleToRawLongBits((double)val)) & Long.MAX_VALUE) == 0){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Long){
                                final long v;
                                if((v=(long)val) == 0L){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(v == 1L){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Character){
                                switch(((Character)val).charValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else{
                                break returnFalse;
                            }
                            return BitSetUtil.uncheckedcontains(root.words,rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
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
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                root.uncheckedForEach(rootOffset=this.rootOffset,rootOffset + size,action);
            }
        }
        @Override
        public void forEach(Consumer<? super Boolean> action){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                root.uncheckedForEach(rootOffset=this.rootOffset,rootOffset + size,action::accept);
            }
        }
        @Override
        public boolean getBoolean(int index){
            return (root.words[(index+=rootOffset) >> 6] >> index & 1) != 0;
        }
        @Override
        public int hashCode(){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                return root.uncheckedHashCode(rootOffset=this.rootOffset,rootOffset + size);
            }
            return 1;
        }
        @Override
        public int indexOf(boolean val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        return root.uncheckedindexOf(rootOffset,size,BitSetUtil.getWordFlipper(val));
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public int indexOf(double val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            long bits;
                            if(((bits=Double.doubleToRawLongBits(val)) & Long.MAX_VALUE) == 0){
                                wordFlipper=BitSetUtil::flip;
                            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return root.uncheckedindexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public int indexOf(float val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(Float.floatToRawIntBits(val)){
                            default:
                                break returnFalse;
                            case 0:
                            case Integer.MIN_VALUE:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case TypeUtil.FLT_TRUE_BITS:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return root.uncheckedindexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public int indexOf(int val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(val){
                            default:
                                break returnFalse;
                            case 0:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case 1:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return root.uncheckedindexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public int indexOf(long val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val == 0L){
                                wordFlipper=BitSetUtil::flip;
                            }else if(val == 1L){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return root.uncheckedindexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public int indexOf(Object val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        // TODO a pattern-matching switch statement would be great here
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val instanceof Boolean){
                                wordFlipper=BitSetUtil.getWordFlipper((boolean)val);
                            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                                switch(((Number)val).intValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Float){
                                switch(Float.floatToRawIntBits((float)val)){
                                default:
                                    break returnFalse;
                                case 0:
                                case Integer.MIN_VALUE:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case TypeUtil.FLT_TRUE_BITS:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Double){
                                final long bits;
                                if(((bits=Double.doubleToRawLongBits((double)val)) & Long.MAX_VALUE) == 0){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Long){
                                final long v;
                                if((v=(long)val) == 0L){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(v == 1L){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Character){
                                switch(((Character)val).charValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else{
                                break returnFalse;
                            }
                            return root.uncheckedindexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            return new Itr(this);
        }
        @Override
        public int lastIndexOf(boolean val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        return root.uncheckedlastIndexOf(rootOffset,size,BitSetUtil.getWordFlipper(val));
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public int lastIndexOf(double val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            long bits;
                            if(((bits=Double.doubleToRawLongBits(val)) & Long.MAX_VALUE) == 0){
                                wordFlipper=BitSetUtil::flip;
                            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return root.uncheckedlastIndexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public int lastIndexOf(float val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(Float.floatToRawIntBits(val)){
                            default:
                                break returnFalse;
                            case 0:
                            case Integer.MIN_VALUE:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case TypeUtil.FLT_TRUE_BITS:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return root.uncheckedlastIndexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public int lastIndexOf(int val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(val){
                            default:
                                break returnFalse;
                            case 0:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case 1:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return root.uncheckedlastIndexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public int lastIndexOf(long val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val == 0L){
                                wordFlipper=BitSetUtil::flip;
                            }else if(val == 1L){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return root.uncheckedlastIndexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public int lastIndexOf(Object val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        // TODO a pattern-matching switch statement would be great here
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val instanceof Boolean){
                                wordFlipper=BitSetUtil.getWordFlipper((boolean)val);
                            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                                switch(((Number)val).intValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Float){
                                switch(Float.floatToRawIntBits((float)val)){
                                default:
                                    break returnFalse;
                                case 0:
                                case Integer.MIN_VALUE:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case TypeUtil.FLT_TRUE_BITS:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Double){
                                final long bits;
                                if(((bits=Double.doubleToRawLongBits((double)val)) & Long.MAX_VALUE) == 0){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Long){
                                final long v;
                                if((v=(long)val) == 0L){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(v == 1L){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Character){
                                switch(((Character)val).charValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else{
                                break returnFalse;
                            }
                            return root.uncheckedlastIndexOf(rootOffset,size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return -1;
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(){
            return new ListItr(this);
        }
        @Override
        public OmniListIterator.OfBoolean listIterator(int index){
            return new ListItr(this,index + rootOffset);
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
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        // TODO a pattern-matching switch statement would be great here
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val instanceof Boolean){
                                wordFlipper=BitSetUtil.getWordFlipper((boolean)val);
                            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                                switch(((Number)val).intValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Float){
                                switch(Float.floatToRawIntBits((float)val)){
                                default:
                                    break returnFalse;
                                case 0:
                                case Integer.MIN_VALUE:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case TypeUtil.FLT_TRUE_BITS:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else if(val instanceof Double){
                                final long bits;
                                if(((bits=Double.doubleToRawLongBits((double)val)) & Long.MAX_VALUE) == 0){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Long){
                                final long v;
                                if((v=(long)val) == 0L){
                                    wordFlipper=BitSetUtil::flip;
                                }else if(v == 1L){
                                    wordFlipper=LongUnaryOperator.identity();
                                }else{
                                    break returnFalse;
                                }
                            }else if(val instanceof Character){
                                switch(((Character)val).charValue()){
                                default:
                                    break returnFalse;
                                case 0:
                                    wordFlipper=BitSetUtil::flip;
                                    break;
                                case 1:
                                    wordFlipper=LongUnaryOperator.identity();
                                }
                            }else{
                                break returnFalse;
                            }
                            return this.uncheckedremoveVal(size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public boolean removeBooleanAt(int index){
            var curr=this;
            do{
                --curr.size;
            }while((curr=curr.parent) != null);
            final PackedBooleanArrSeq root;
            return ((root=this.root).uncheckedRemoveIndex(index+=rootOffset,root.size - 1)>>index&1) != 0;
        }
        @Override
        public boolean removeIf(BooleanPredicate filter){
            int size;
            if((size=this.size)!=0){
              {
                  final int numRemoved,offset,wordOffset,bound,wordBound;
                  if((wordOffset=(offset=this.rootOffset)>>6)==(wordBound=(bound=offset+size)-1>>6)) {
                      numRemoved=root.singleWordRemoveIfImpl(offset,size,filter,bound,wordBound);
                  }else {
                      numRemoved=root.multiWordRemoveIfImpl(offset,size,filter,bound,wordOffset,wordBound);
                  }
                  if(size!=(size-=numRemoved)) {
                      for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
                      this.size=size;
                      return true;
                  }
              }
            }
            return false;
          }
        @Override
        public boolean removeIf(Predicate<? super Boolean> filter){
            int size;
            if((size=this.size)!=0){
              {
                  final int numRemoved,offset,wordOffset,bound,wordBound;
                  if((wordOffset=(offset=this.rootOffset)>>6)==(wordBound=(bound=offset+size)-1>>6)) {
                      numRemoved=root.singleWordRemoveIfImpl(offset,size,filter::test,bound,wordBound);
                  }else {
                      numRemoved=root.multiWordRemoveIfImpl(offset,size,filter::test,bound,wordOffset,wordBound);
                  }
                  if(size!=(size-=numRemoved)) {
                      for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
                      this.size=size;
                      return true;
                  }
              }
            }
            return false;
          }
        @Override
        public boolean removeVal(boolean val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        return this.uncheckedremoveVal(size,BitSetUtil.getWordFlipper(val));
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public boolean removeVal(double val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            long bits;
                            if(((bits=Double.doubleToRawLongBits(val)) & Long.MAX_VALUE) == 0){
                                wordFlipper=BitSetUtil::flip;
                            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return this.uncheckedremoveVal(size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public boolean removeVal(float val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(Float.floatToRawIntBits(val)){
                            default:
                                break returnFalse;
                            case 0:
                            case Integer.MIN_VALUE:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case TypeUtil.FLT_TRUE_BITS:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return this.uncheckedremoveVal(size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public boolean removeVal(int val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            switch(val){
                            default:
                                break returnFalse;
                            case 0:
                                wordFlipper=BitSetUtil::flip;
                                break;
                            case 1:
                                wordFlipper=LongUnaryOperator.identity();
                            }
                            return this.uncheckedremoveVal(size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public boolean removeVal(long val){
            {
                {
                    final int size;
                    if((size=this.size) != 0){
                        returnFalse:for(;;){
                            final LongUnaryOperator wordFlipper;
                            if(val == 0L){
                                wordFlipper=BitSetUtil::flip;
                            }else if(val == 1L){
                                wordFlipper=LongUnaryOperator.identity();
                            }else{
                                break returnFalse;
                            }
                            return this.uncheckedremoveVal(size,wordFlipper);
                        }
                    } // end size check
                } // end checked sublist try modcount
            }// end val check
            return false;
        }
        @Override
        public void replaceAll(BooleanPredicate operator){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                root.uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset + size,operator);
            }
        }
        @Override
        public void replaceAll(UnaryOperator<Boolean> operator){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                root.uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset + size,operator::apply);
            }
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
           final int size;
           if((size=this.size)>1) {
               if(sorter==null) {
                   root.uncheckedStableAscendingSort(rootOffset,size);
               }else {
                   final int wordOffset;
                   final int bound;
                   final int wordBound;
                   final int offset;
                   if((wordOffset=(offset=this.rootOffset)>>6)==(wordBound=(bound=offset+size)-1>>6)) {
                       root.singleWordSort(offset,size,bound,wordBound,sorter);
                   }else {
                       root. multiWordSort(offset,wordOffset,bound,wordBound,sorter);
                   }
               }
           }
        }
        @Override
        public void sort(Comparator<? super Boolean> sorter){
            final int size;
            if((size=this.size)>1) {
                if(sorter==null) {
                    root.uncheckedStableAscendingSort(rootOffset,size);
                }else {
                    final int wordOffset;
                    final int bound;
                    final int wordBound;
                    final int offset;
                    if((wordOffset=(offset=this.rootOffset)>>6)==(wordBound=(bound=offset+size)-1>>6)) {
                        root.singleWordSort(offset,size,bound,wordBound,sorter::compare);
                    }else {
                        root. multiWordSort(offset,wordOffset,bound,wordBound,sorter::compare);
                    }
                }
            }
         }
        @Override
        public void stableAscendingSort(){
            final int size;
            if((size=this.size)>1) {
                root.uncheckedStableAscendingSort(rootOffset,size);
            }
        }
        @Override
        public void stableDescendingSort(){
            final int size;
            if((size=this.size)>1) {
                root.uncheckedStableDescendingSort(rootOffset,size);
            }
        }
        @Override
        public OmniList.OfBoolean subList(int fromIndex,int toIndex){
            return new UncheckedSubList(this,rootOffset + fromIndex,toIndex - fromIndex);
        }
        @Override
        public Boolean[] toArray(){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final Boolean[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new Boolean[size]);
                return dst;
            }
            return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            final int size;
            final T[] dst=arrConstructor.apply(size=this.size);
            if(size != 0){
                final int rootOffset;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,dst);
            }
            return dst;
        }
        @Override
        public <T> T[] toArray(T[] dst){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=OmniArray.uncheckedArrResize(size,dst));
            }else if(dst.length != 0){
                dst[0]=null;
            }
            return dst;
        }
        @Override
        public boolean[] toBooleanArray(){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final boolean[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new boolean[size]);
                return dst;
            }
            return OmniArray.OfBoolean.DEFAULT_ARR;
        }
        @Override
        public byte[] toByteArray(){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final byte[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new byte[size]);
                return dst;
            }
            return OmniArray.OfByte.DEFAULT_ARR;
        }
        @Override
        public char[] toCharArray(){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final char[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new char[size]);
                return dst;
            }
            return OmniArray.OfChar.DEFAULT_ARR;
        }
        @Override
        public double[] toDoubleArray(){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final double[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new double[size]);
                return dst;
            }
            return OmniArray.OfDouble.DEFAULT_ARR;
        }
        @Override
        public float[] toFloatArray(){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final float[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new float[size]);
                return dst;
            }
            return OmniArray.OfFloat.DEFAULT_ARR;
        }
        @Override
        public int[] toIntArray(){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final int[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new int[size]);
                return dst;
            }
            return OmniArray.OfInt.DEFAULT_ARR;
        }
        @Override
        public long[] toLongArray(){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final long[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new long[size]);
                return dst;
            }
            return OmniArray.OfLong.DEFAULT_ARR;
        }
        @Override
        public short[] toShortArray(){
            final int size;
            if((size=this.size) != 0){
                final int rootOffset;
                final short[] dst;
                UncheckedList.uncheckedCopyInto(root.words,rootOffset=this.rootOffset,rootOffset + size,
                        dst=new short[size]);
                return dst;
            }
            return OmniArray.OfShort.DEFAULT_ARR;
        }
        @Override
        public String toString(){
            int size;
            if((size=this.size) != 0){
                final byte[] buffer;
                final var rootOffset=this.rootOffset;
                if(size <= OmniArray.MAX_ARR_SIZE / 7){
                    (buffer=new byte[size * 7])[size=root.uncheckedToString(rootOffset,rootOffset + size,
                            buffer)]=(byte)']';
                    buffer[0]=(byte)'[';
                    return new String(buffer,0,size + 1,ToStringUtil.IOS8859CharSet);
                }else{
                    final ToStringUtil.OmniStringBuilderByte builder;
                    root.uncheckedToString(rootOffset,rootOffset + size,
                            builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
                    builder.uncheckedAppendChar((byte)']');
                    (buffer=builder.buffer)[0]=(byte)'[';
                    return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
                }
            }
            return "[]";
        }
        private boolean uncheckedremoveVal(final int size,LongUnaryOperator wordFlipper){
            final UncheckedList root;
            final var words=(root=this.root).words;
            final int bound;
            int offset;
            final var wordBound=(bound=(offset=rootOffset) + size - 1) >> 6;
            var wordOffset=offset >> 6;
            long word;
            int tail0s;
            goToRemoveIndex:for(;;){
                if((offset&=63) != 0){
                    tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(word=words[wordOffset]) & -1L<<offset);
                    if(wordOffset == wordBound){
                        if(tail0s <=(bound&63)){
                            break goToRemoveIndex;
                        }
                        return false;
                    }else if(tail0s != 64){
                        break goToRemoveIndex;
                    }
                    ++wordOffset;
                }
                for(;wordOffset < wordBound;++wordOffset){
                    if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(word=words[wordOffset]))) != 64){
                        break goToRemoveIndex;
                    }
                }
                if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(word=words[wordOffset]))) <= (bound
                        & 63)){
                    break goToRemoveIndex;
                }
                return false;
            }
            removeIndexShiftDown(words,tail0s,wordOffset,word,--root.size);
            this.size=size - 1;
            for(var curr=parent;curr != null;--curr.size,curr=curr.parent){}
            return true;
        }
        private Object writeReplace(){
            return new SerializableSubList(root.words,size,rootOffset);
        }
        private static class Itr extends AbstractBooleanItr{
            transient final UncheckedSubList parent;
            transient int cursor;
            private Itr(Itr itr){
                parent=itr.parent;
                cursor=itr.cursor;
            }
            private Itr(UncheckedSubList parent){
                this.parent=parent;
                cursor=parent.rootOffset;
            }
            private Itr(UncheckedSubList parent,int cursor){
                this.parent=parent;
                this.cursor=cursor;
            }
            @Override
            public Object clone(){
                return new Itr(this);
            }
            @Override
            public void forEachRemaining(BooleanConsumer action){
                final int cursor,bound;
                final UncheckedSubList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).rootOffset + parent.size)){
                    parent.root.uncheckedForEach(cursor,bound,action);
                    this.cursor=bound;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                final int cursor,bound;
                final UncheckedSubList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).rootOffset + parent.size)){
                    parent.root.uncheckedForEach(cursor,bound,action::accept);
                    this.cursor=bound;
                }
            }
            @Override
            public boolean hasNext(){
                final UncheckedSubList parent;
                return cursor < (parent=this.parent).rootOffset + parent.size;
            }
            @Override
            public boolean nextBoolean(){
                final int cursor;
                return (parent.root.words[(cursor=this.cursor++) >> 6] >> cursor & 1) != 0;
            }
            @Override
            public void remove(){
                UncheckedSubList parent;
                ((PackedBooleanArrSeq)(parent=this.parent).root).uncheckedRemoveIndexNoRet(--cursor);
                do{
                    --parent.size;
                }while((parent=parent.parent) != null);
            }
        }
        private static class ListItr extends Itr implements OmniListIterator.OfBoolean{
            transient int lastRet;
            private ListItr(ListItr itr){
                super(itr);
                lastRet=itr.lastRet;
            }
            private ListItr(UncheckedSubList parent){
                super(parent);
            }
            private ListItr(UncheckedSubList parent,int cursor){
                super(parent,cursor);
            }
            @Override
            public void add(boolean val){
                final UncheckedList root;
                final int rootSize;
                UncheckedSubList parent;
                if((rootSize=(root=(parent=this.parent).root).size) != 0){
                    root.uncheckedInsert(cursor++,rootSize,val);
                }else{
                    ((PackedBooleanArrSeq)root).uncheckedInit(val);
                    ++cursor;
                }
                do{
                    ++parent.size;
                }while((parent=parent.parent) != null);
            }
            @Override
            public Object clone(){
                return new ListItr(this);
            }
            @Override
            public void forEachRemaining(BooleanConsumer action){
                final int cursor,bound;
                final UncheckedSubList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).rootOffset + parent.size)){
                    parent.root.uncheckedForEach(cursor,bound,action);
                    this.cursor=bound;
                    lastRet=bound - 1;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                final int cursor,bound;
                final UncheckedSubList parent;
                if((cursor=this.cursor) < (bound=(parent=this.parent).rootOffset + parent.size)){
                    parent.root.uncheckedForEach(cursor,bound,action::accept);
                    this.cursor=bound;
                    lastRet=bound - 1;
                }
            }
            @Override
            public boolean hasPrevious(){
                return cursor > parent.rootOffset;
            }
            @Override
            public boolean nextBoolean(){
                int lastRet;
                this.lastRet=lastRet=cursor++;
                return (parent.root.words[lastRet >> 6] >> lastRet & 1) != 0;
            }
            @Override
            public int nextIndex(){
                return cursor - parent.rootOffset;
            }
            @Override
            public boolean previousBoolean(){
                final int lastRet;
                this.lastRet=lastRet=--cursor;
                return (parent.root.words[lastRet >> 6] >> lastRet & 1) != 0;
            }
            @Override
            public int previousIndex(){
                return cursor - parent.rootOffset - 1;
            }
            @Override
            public void remove(){
                UncheckedSubList parent;
                final int lastRet;
                ((PackedBooleanArrSeq)(parent=this.parent).root).uncheckedRemoveIndexNoRet(lastRet=this.lastRet);
                cursor=lastRet;
                do{
                    --parent.size;
                }while((parent=parent.parent) != null);
            }
            @Override
            public void set(boolean val){
                final int lastRet=this.lastRet;
                if(val){
                    parent.root.words[lastRet >> 6]|=1L << lastRet;
                }else{
                    parent.root.words[lastRet >> 6]&=~(1L << lastRet);
                }
            }
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
                        final int rootOffset;
                        final int wordBound=(rootOffset=this.rootOffset) + size - 1 >> 6;
                        int wordIndex=rootOffset >> 6;
                        final var words=this.words;
                        if((rootOffset & 63) == 0){
                            // alligned
                            OmniArray.OfLong.writeArray(words,wordIndex,wordBound,oos);
                        }else{
                            // unalligned
                            long word;
                            for(word=words[wordIndex];wordIndex != wordBound;oos
                                    .writeLong(word >>> rootOffset | (word=words[++wordIndex]) << -rootOffset)){}
                            oos.writeLong(word >>> rootOffset);
                        }
                    }
                }
            }
        }
    }
}
