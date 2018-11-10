package omni.impl.seq.arr.ofref;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.OmniPred;
class CheckedSubList<E>extends AbstractSeq.Checked.AbstractSubList<E> implements OmniList.OfRef<E>{
    static void bubbleUpDecrementSize(AbstractSeq.Checked.AbstractSubList<?> parent){
        while(parent!=null){
            --parent.size;
            ++parent.modCount;
            parent=parent.parent;
        }
    }
    private static void bubbleUpIncrementModCount(AbstractSeq.Checked.AbstractSubList<?> parent){
        while(parent!=null){
            ++parent.modCount;
            parent=parent.parent;
        }
    }
    private CheckedSubList(AbstractSeq.Checked<E> root,CheckedSubList<E> parent,int rootOffset,int size,int modCount){
        super(root,parent,rootOffset,size,modCount);
    }
    CheckedSubList(AbstractSeq.Checked<E> root,int rootOffset,int size){
        super(root,rootOffset,size);
    }
    @Override
    public Object clone(){
        final var root=checkModCountAndGetRoot();
        final Object[] arr;
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new Object[size],0,size);
        }else{
            arr=null;
        }
        return new CheckedList<>(size,arr);
    }
    @Override
    public boolean contains(boolean val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Boolean val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(byte val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Byte val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(char val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Character val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(double val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Double val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(float val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Float val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(int val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Integer val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(long val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Long val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Object val){
        final var root=this.root;
        final int modCount=this.modCount;
        try{
            final int size,rootOffset;
            return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,
                    rootOffset+size,OmniPred.OfRef.getEqualsPred(val));
        }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public boolean contains(short val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Short val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void forEach(Consumer<? super E> action){
        final int modCount=this.modCount;
        final var root=this.root;
        try{
            final int size;
            if((size=this.size)!=0){
                int rootOffset;
                AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
            }
        }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index){
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,size);
        return (E)root.arr[index+rootOffset];
    }
    @Override
    public int hashCode(){
        final var root=this.root;
        final int modCount=this.modCount;
        try{
            final int size;
            if((size=this.size)!=0){
                int rootOffset;
                return AbstractSeq.forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
            }
            return 1;
        }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public int indexOf(boolean val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Boolean val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(byte val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Byte val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(char val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Character val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(double val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Double val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(float val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Float val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(int val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Integer val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(long val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Long val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Object val){
        final AbstractSeq.Checked<E> root=this.root;
        final int modCount=this.modCount;
        try{
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                        OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public int indexOf(short val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Short val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public OmniIterator.OfRef<E> iterator(){
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new CheckedBidirectionalSubItr<>(this,modCount);
    }
    @Override
    public int lastIndexOf(boolean val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Boolean val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(byte val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Byte val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(char val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Character val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(double val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Double val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(float val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Float val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(int val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Integer val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(long val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Long val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Object val){
        final var root=this.root;
        final int modCount=this.modCount;
        try{
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                        OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public int lastIndexOf(short val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Short val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(){
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new CheckedBidirectionalSubItr<>(this,modCount);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index){
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkWriteHi(index,size);
        return new CheckedBidirectionalSubItr<>(this,modCount,index+rootOffset);
    }
    @Override
    public void put(int index,E val){
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,size);
        root.arr[index+rootOffset]=val;
    }
    @Override
    public E remove(int index){
        int modCount;
        final AbstractSeq.Checked<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final Object[] arr;
        @SuppressWarnings("unchecked")
        final var removed=(E)(arr=root.arr)[index+=rootOffset];
        AbstractSeq.eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        this.modCount=++modCount;
        root.modCount=modCount;
        this.size=size-1;
        return removed;
    }
    @Override
    public boolean remove(Object val){
        final int size;
        if((size=this.size)!=0){
            if(val!=null){
                return uncheckedRemoveNonNull(size,val);
            }
            return uncheckedRemoveFirstMatch(size,Objects::isNull);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(Boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(byte val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(Byte val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(char val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(Character val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(double val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(Double val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(float val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(Float val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(int val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(Integer val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(long val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(Long val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(short val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(Short val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public void replaceAll(UnaryOperator<E> operator){
        int modCount=this.modCount;
        final var root=this.root;
        final int size;
        if((size=this.size)!=0){
            try{
                final int rootOffset;
                AbstractSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,root.modCount,e);
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            bubbleUpIncrementModCount(parent);
        }else{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public void reverseSort(){
        int modCount=this.modCount;
        final var root=this.root;
        final int size;
        if((size=this.size)>1){
            try{
                final int rootOffset;
                AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,
                        Comparator.reverseOrder());
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,root.modCount,e);
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            bubbleUpIncrementModCount(parent);
        }else{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public E set(int index,E val){
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,size);
        final Object[] arr;
        @SuppressWarnings("unchecked")
        final var oldVal=(E)(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
    }
    @Override
    public void sort(){
        int modCount=this.modCount;
        final var root=this.root;
        final int size;
        if((size=this.size)>1){
            try{
                final int rootOffset;
                AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,
                        Comparator.naturalOrder());
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,root.modCount,e);
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            bubbleUpIncrementModCount(parent);
        }else{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public void sort(Comparator<? super E> sorter){
        int modCount=this.modCount;
        final var root=this.root;
        final int size;
        if((size=this.size)>1){
            try{
                final int rootOffset;
                AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,root.modCount,e);
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            bubbleUpIncrementModCount(parent);
        }else{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
        final AbstractSeq.Checked<E> root;
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
        return new CheckedSubList<>(root,this,rootOffset+fromIndex,toIndex-fromIndex,modCount);
    }
    @Override
    public Object[] toArray(){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final Object[] dst;
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Object[size],0,size);
            return dst;
        }
        return OmniArray.OfRef.DEFAULT_ARR;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final var root=this.root;
        final T[] dst;
        final int size,modCount=this.modCount;
        try{
            dst=arrConstructor.apply(size=this.size);
        }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
        if(size!=0){
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst,0,size);
        }
        return dst;
    }
    @Override
    public <T> T[] toArray(T[] dst){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
        }else if(dst.length!=0){
            dst[0]=null;
        }
        return dst;
    }
    @Override
    public String toString(){
        final int modCount=this.modCount;
        final var root=this.root;
        try{
            final int size;
            if((size=this.size)!=0){
                final StringBuilder builder;
                final int rootOffset;
                AbstractSeq.forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                        builder=new StringBuilder("["));
                return builder.append(']').toString();
            }
            return "[]";
        }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    private AbstractSeq.Checked<E> checkModCountAndGetRoot(){
        final AbstractSeq.Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        return root;
    }
    private boolean uncheckedRemoveFirstMatch(int size,Predicate<Object> pred){
        int modCount;
        final AbstractSeq.Checked<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        int offset;
        final int bound=(offset=rootOffset)+--size;
        final var arr=root.arr;
        do{
            if(pred.test(arr[offset])){
                root.modCount=++modCount;
                this.modCount=modCount;
                AbstractSeq.eraseIndexHelper(arr,offset,--root.size);
                bubbleUpDecrementSize(parent);
                this.size=size;
                return true;
            }
        }while(++offset!=bound);
        return false;
    }
    private boolean uncheckedRemoveNonNull(int size,Object nonNull){
        int modCount=this.modCount;
        int offset;
        final int bound=(offset=rootOffset)+--size;
        final AbstractSeq.Checked<E> root;
        final var arr=(root=this.root).arr;
        try{
            do{
                if(nonNull.equals(arr[offset])){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    root.modCount=++modCount;
                    this.modCount=modCount;
                    AbstractSeq.eraseIndexHelper(arr,offset,--root.size);
                    bubbleUpDecrementSize(parent);
                    this.size=size;
                    return true;
                }
            }while(++offset!=bound);
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }catch(final RuntimeException e){
            throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
    }
}