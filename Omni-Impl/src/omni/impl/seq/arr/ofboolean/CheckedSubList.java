package omni.impl.seq.arr.ofboolean;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
class CheckedSubList extends AbstractSeq.Checked.AbstractSubList implements OmniList.OfBoolean{
    private static void bubbleUpIncrementModCount(AbstractSeq.Checked.AbstractSubList parent){
        while(parent!=null){
            ++parent.modCount;
            parent=parent.parent;
        }
    }
    private CheckedSubList(AbstractSeq.Checked root,CheckedSubList parent,int rootOffset,int size,int modCount){
        super(root,parent,rootOffset,size,modCount);
    }
    CheckedSubList(AbstractSeq.Checked root,int rootOffset,int size){
        super(root,rootOffset,size);
    }
    @Override
    public boolean add(Boolean val){
        return super.add(val);
    }
    @Override
    public void add(int index,Boolean val){
        super.add(index,val);
    }
    @Override
    public Object clone(){
        final var root=checkModCountAndGetRoot();
        final boolean[] arr;
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new boolean[size],0,size);
        }else{
            arr=null;
        }
        return new CheckedList(size,arr);
    }
    @Override
    public boolean contains(boolean val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0
                &&AbstractSeq.uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override
    public boolean contains(double val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            final long bits;
            if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
                v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
            }else{
                return false;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return false;
    }
    @Override
    public boolean contains(float val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
            default:
                return false;
            case 0:
            case Integer.MIN_VALUE:
                v=false;
                break;
            case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return false;
    }
    @Override
    public boolean contains(int val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            switch(val){
            default:
                return false;
            case 0:
                v=false;
                break;
            case 1:
                v=true;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return false;
    }
    @Override
    public boolean contains(long val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            if(val==0){
                v=false;
            }else if(val==1){
                v=true;
            }else{
                return false;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return false;
    }
    @Override
    public boolean contains(Object val){
        final var root=checkModCountAndGetRoot();
        final int size,rootOffset;
        return (size=this.size)!=0&&val instanceof Boolean
                &&AbstractSeq.uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,(boolean)val);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void forEach(BooleanConsumer action){
        final int size;
        if((size=this.size)!=0){
            uncheckedForEach(size,action);
        }else{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public void forEach(Consumer<? super Boolean> action){
        final int size;
        if((size=this.size)!=0){
            uncheckedForEach(size,action::accept);
        }else{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public Boolean get(int index){
        return super.getBoolean(index);
    }
    @Override
    public int indexOf(boolean val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
        }
        return -1;
    }
    @Override
    public int indexOf(double val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            final long bits;
            if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
                v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
            }else{
                return -1;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return -1;
    }
    @Override
    public int indexOf(float val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
            default:
                return -1;
            case 0:
            case Integer.MIN_VALUE:
                v=false;
                break;
            case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return -1;
    }
    @Override
    public int indexOf(int val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            switch(val){
            default:
                return -1;
            case 0:
                v=false;
                break;
            case 1:
                v=true;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return -1;
    }
    @Override
    public int indexOf(long val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            if(val==0){
                v=false;
            }else if(val==1){
                v=true;
            }else{
                return -1;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return -1;
    }
    @Override
    public int indexOf(Object val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0&&val instanceof Boolean){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(boolean)val);
        }
        return -1;
    }
    @Override
    public OmniIterator.OfBoolean iterator(){
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new CheckedBidirectionalSubItr(this,modCount);
    }
    @Override
    public int lastIndexOf(boolean val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
        }
        return -1;
    }
    @Override
    public int lastIndexOf(double val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            final long bits;
            if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
                v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
            }else{
                return -1;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return -1;
    }
    @Override
    public int lastIndexOf(float val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
            default:
                return -1;
            case 0:
            case Integer.MIN_VALUE:
                v=false;
                break;
            case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return -1;
    }
    @Override
    public int lastIndexOf(int val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            switch(val){
            default:
                return -1;
            case 0:
                v=false;
                break;
            case 1:
                v=true;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return -1;
    }
    @Override
    public int lastIndexOf(long val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            if(val==0){
                v=false;
            }else if(val==1){
                v=true;
            }else{
                return -1;
            }
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
        }
        return -1;
    }
    @Override
    public int lastIndexOf(Object val){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0&&val instanceof Boolean){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(boolean)val);
        }
        return -1;
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(){
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new CheckedBidirectionalSubItr(this,modCount);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(int index){
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkWriteHi(index,size);
        return new CheckedBidirectionalSubItr(this,modCount,index+rootOffset);
    }
    @Override
    public void put(int index,boolean val){
        final AbstractSeq.Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,size);
        root.arr[index+rootOffset]=val;
    }
    @Override
    public Boolean remove(int index){
        return super.removeBooleanAt(index);
    }
    @Override
    public boolean remove(Object val){
        final int size;
        if((size=this.size)!=0&&val instanceof Boolean){
            return uncheckedRemoveFirstMatch(size,(boolean)val);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedRemoveFirstMatch(size,val);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(double val){
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            final long bits;
            if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
                v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
            }else{
                CheckedCollection.checkModCount(modCount,root.modCount);
                return false;
            }
            return uncheckedRemoveFirstMatch(size,v);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(float val){
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
            default:
                CheckedCollection.checkModCount(modCount,root.modCount);
                return false;
            case 0:
            case Integer.MIN_VALUE:
                v=false;
                break;
            case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            return uncheckedRemoveFirstMatch(size,v);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(int val){
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            switch(val){
            default:
                CheckedCollection.checkModCount(modCount,root.modCount);
                return false;
            case 0:
                v=false;
                break;
            case 1:
                v=true;
            }
            return uncheckedRemoveFirstMatch(size,v);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public boolean removeVal(long val){
        final int size;
        if((size=this.size)!=0){
            final boolean v;
            if(val==0){
                v=false;
            }else if(val==1){
                v=true;
            }else{
                CheckedCollection.checkModCount(modCount,root.modCount);
                return false;
            }
            return uncheckedRemoveFirstMatch(size,v);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
    }
    @Override
    public void replaceAll(BooleanPredicate operator){
        final int size;
        if((size=this.size)!=0){
            uncheckedReplaceAll(size,operator);
        }else{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator){
        final int size;
        if((size=this.size)!=0){
            uncheckedReplaceAll(size,operator::apply);
        }else{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public void reverseSort(){
        int modCount;
        final AbstractSeq.Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)>1){
            final int rootOffset;
            AbstractSeq.uncheckedReverseSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
            root.modCount=++modCount;
            this.modCount=modCount;
            bubbleUpIncrementModCount(parent);
        }
    }
    @Override
    public Boolean set(int index,Boolean val){
        return super.set(index,val);
    }
    @Override
    public void sort(){
        int modCount;
        final AbstractSeq.Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)>1){
            final int rootOffset;
            AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
            root.modCount=++modCount;
            this.modCount=modCount;
            bubbleUpIncrementModCount(parent);
        }
    }
    @Override
    public void sort(BooleanComparator sorter){
        final int size;
        if((size=this.size)>1){
            uncheckedSort(size,sorter);
        }else{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter){
        final int size;
        if((size=this.size)>1){
            uncheckedSort(size,sorter::compare);
        }else{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex){
        final AbstractSeq.Checked root;
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
        return new CheckedSubList(root,this,rootOffset+fromIndex,toIndex-fromIndex,modCount);
    }
    @Override
    public Boolean[] toArray(){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final Boolean[] dst;
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Boolean[size],0,size);
            return dst;
        }
        return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
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
    public boolean[] toBooleanArray(){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final boolean[] dst;
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new boolean[size],0,size);
            return dst;
        }
        return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override
    public byte[] toByteArray(){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final byte[] dst;
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new byte[size],0,size);
            return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override
    public char[] toCharArray(){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final char[] dst;
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new char[size],0,size);
            return dst;
        }
        return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override
    public double[] toDoubleArray(){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final double[] dst;
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new double[size],0,size);
            return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public float[] toFloatArray(){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final float[] dst;
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new float[size],0,size);
            return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public int[] toIntArray(){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final int[] dst;
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new int[size],0,size);
            return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public long[] toLongArray(){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final long[] dst;
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new long[size],0,size);
            return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public short[] toShortArray(){
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0){
            final short[] dst;
            ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new short[size],0,size);
            return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
    }
    private AbstractSeq.Checked checkModCountAndGetRoot(){
        final AbstractSeq.Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        return root;
    }
    private void uncheckedForEach(int size,BooleanConsumer action){
        final int modCount=this.modCount;
        final var root=this.root;
        try{
            int rootOffset;
            AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
        }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
        }
    }
    private boolean uncheckedRemoveFirstMatch(int size,boolean val){
        int modCount;
        final AbstractSeq.Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        int offset;
        final int bound=(offset=rootOffset)+--size;
        final var arr=root.arr;
        do{
            if(arr[offset]==val){
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
    private void uncheckedReplaceAll(int size,BooleanPredicate operator){
        int modCount=this.modCount;
        final var root=this.root;
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
    }
    private void uncheckedSort(int size,BooleanComparator sorter){
        int modCount=this.modCount;
        final var root=this.root;
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
    }
}