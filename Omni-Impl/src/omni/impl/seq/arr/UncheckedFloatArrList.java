package omni.impl.seq.arr;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.FloatComparator;
import omni.function.FloatConsumer;
import omni.function.FloatUnaryOperator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class UncheckedFloatArrList extends AbstractFloatArrSeq.Unchecked implements OmniList.OfFloat{
    UncheckedFloatArrList(){
        super();
    }
    UncheckedFloatArrList(int capacity){
        super(capacity);
    }
    private UncheckedFloatArrList(int size,float[] arr){
        super(size,arr);
    }
    @Override
    public void add(int index,Float val){
        super.add(index,val);
    }
    @Override
    public Object clone(){
        final float[] arr;
        final int size;
        if((size=this.size)!=0){
            ArrCopy.uncheckedCopy(this.arr,0,arr=new float[size],0,size);
        }else{
            arr=null;
        }
        return new UncheckedFloatArrList(size,arr);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public Float get(int index){
        return super.getFloat(index);
    }
    @Override
    public OmniIterator.OfFloat iterator(){
        return new AscendingItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator(){
        return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator(int index){
        return new BidirectionalItr(this,index);
    }
    @Override
    public Float remove(int index){
        return super.removeFloatAt(index);
    }
    @Override
    public void reverseSort(){
        int size;
        if((size=this.size)>1){
            uncheckedReverseSort(arr,0,size-1);
        }
    }
    @Override
    public Float set(int index,Float val){
        return super.set(index,val);
    }
    @Override
    public void sort(){
        int size;
        if((size=this.size)>1){
            uncheckedSort(arr,0,size-1);
        }
    }
    @Override
    public OmniList.OfFloat subList(int fromIndex,int toIndex){
        return new SubList(this,fromIndex,toIndex-fromIndex);
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
    void uncheckedCopyInto(Float[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length){
        ArrCopy.uncheckedCopy(arr,0,dst,0,length);
    }
    @Override
    void uncheckedForEach(int size,FloatConsumer action){
        uncheckedForwardForEachInRange(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(int size){
        return forwardHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedRemoveFirstFlt0(int size){
        final var arr=this.arr;
        int index=0;
        do{
            if(arr[index]==0){
                eraseIndexHelper(arr,index,--size);
                this.size=size;
                return true;
            }
        }while(++index!=size);
        return false;
    }
    @Override
    boolean uncheckedRemoveFirstFltBits(int size,int fltBits){
        final var arr=this.arr;
        int index=0;
        do{
            if(Float.floatToRawIntBits(arr[index])==fltBits){
                eraseIndexHelper(arr,index,--size);
                this.size=size;
                return true;
            }
        }while(++index!=size);
        return false;
    }
    @Override
    boolean uncheckedRemoveFirstFltNaN(int size){
        final var arr=this.arr;
        int index=0;
        do{
            if(Float.isNaN(arr[index])){
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
    private static class AscendingItr extends AbstractAscendingItr implements OmniIterator.OfFloat{
        private AscendingItr(Unchecked root){
            super(root);
        }
        @Override
        public void forEachRemaining(Consumer<? super Float> action){
            final Unchecked root;
            final int cursor,bound;
            if((cursor=this.cursor)!=(bound=(root=this.root).size)){
                uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
                this.cursor=bound;
            }
        }
        @Override
        public void forEachRemaining(FloatConsumer action){
            final Unchecked root;
            final int cursor,bound;
            if((cursor=this.cursor)!=(bound=(root=this.root).size)){
                uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
                this.cursor=bound;
            }
        }
        @Override
        public boolean hasNext(){
            return cursor!=root.size;
        }
        @Override
        public Float next(){
            return super.nextFloat();
        }
        @Override
        public void remove(){
            final Unchecked root;
            eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
        }
    }
    private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfFloat{
        private BidirectionalItr(Unchecked root){
            super(root);
        }
        private BidirectionalItr(Unchecked root,int cursor){
            super(root,cursor);
        }
        @Override
        public void forEachRemaining(Consumer<? super Float> action){
            final int cursor,bound;
            final Unchecked root;
            if((cursor=this.cursor)!=(bound=(root=this.root).size)){
                uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
                this.cursor=bound;
                lastRet=bound-1;
            }
        }
        @Override
        public void forEachRemaining(FloatConsumer action){
            final int cursor,bound;
            final Unchecked root;
            if((cursor=this.cursor)!=(bound=(root=this.root).size)){
                uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
                this.cursor=bound;
                lastRet=bound-1;
            }
        }
        @Override
        public boolean hasNext(){
            return cursor!=root.size;
        }
        @Override
        public boolean hasPrevious(){
            return cursor!=0;
        }
        @Override
        public float nextFloat(){
            final int lastRet;
            this.lastRet=lastRet=cursor++;
            return root.arr[lastRet];
        }
        @Override
        public int nextIndex(){
            return cursor;
        }
        @Override
        public float previousFloat(){
            final int lastRet;
            this.lastRet=lastRet=--cursor;
            return root.arr[lastRet];
        }
        @Override
        public int previousIndex(){
            return cursor-1;
        }
        @Override
        public void remove(){
            final Unchecked root;
            final int lastRet;
            eraseIndexHelper((root=this.root).arr,lastRet=this.lastRet,--root.size);
            cursor=lastRet;
        }
        @Override
        public void set(float val){
            root.arr[lastRet]=val;
        }
    }
    private static class SubList extends AbstractSubList implements OmniList.OfFloat{
        private SubList(Unchecked root,int rootOffset,int size){
            super(root,rootOffset,size);
        }
        private SubList(Unchecked root,SubList parent,int rootOffset,int size){
            super(root,parent,rootOffset,size);
        }
        @Override
        public boolean add(boolean val){
            return super.add(TypeUtil.castToFloat(val));
        }
        @Override
        public boolean add(char val){
            return super.add(val);
        }
        @Override
        public boolean add(Float val){
            return super.add(val);
        }
        @Override
        public boolean add(int val){
            return super.add(val);
        }
        @Override
        public void add(int index,Float val){
            super.add(index,val);
        }
        @Override
        public boolean add(long val){
            return super.add(val);
        }
        @Override
        public boolean add(short val){
            return super.add(val);
        }
        @Override
        public Object clone(){
            final float[] arr;
            final int size;
            if((size=this.size)!=0){
                ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new float[size],0,size);
            }else{
                arr=null;
            }
            return new UncheckedFloatArrList(size,arr);
        }
        @Override
        public boolean contains(boolean val){
            int size;
            if((size=this.size)!=0){
                if(val){
                    return uncheckedContainsFltBits(size,TypeUtil.FLT_TRUE_BITS);
                }
                return uncheckedContainsFlt0(size);
            }
            return false;
        }
        @Override
        public boolean contains(byte val){
            final int size,rootOffset;
            return (size=this.size)!=0
                    &&uncheckedContainsRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
        }
        @Override
        public boolean contains(char val){
            final int size,rootOffset;
            return (size=this.size)!=0
                    &&uncheckedContainsRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
        }
        @Override
        public boolean contains(double val){
            int size;
            if((size=this.size)!=0){
                float v;
                if((v=(float)val)==val){
                    return uncheckedContainsFltBits(size,Float.floatToRawIntBits(v));
                }
                return v!=v&&uncheckedContainsFltNaN(size);
            }
            return false;
        }
        @Override
        public boolean contains(float val){
            final int size,rootOffset;
            return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
        }
        @Override
        public boolean contains(int val){
            int size;
            if((size=this.size)!=0){
                if(val!=0){
                    return TypeUtil.checkCastToFloat(val)&&uncheckedContainsFltBits(size,Float.floatToRawIntBits(val));
                }
                return uncheckedContainsFlt0(size);
            }
            return false;
        }
        @Override
        public boolean contains(long val){
            int size;
            if((size=this.size)!=0){
                if(val!=0){
                    return TypeUtil.checkCastToFloat(val)&&uncheckedContainsFltBits(size,Float.floatToRawIntBits(val));
                }
                return uncheckedContainsFlt0(size);
            }
            return false;
        }
        @Override
        public boolean contains(Object val){
            final int size,rootOffset;
            return (size=this.size)!=0&&val instanceof Float
                    &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,(float)val);
        }
        @Override
        public boolean contains(short val){
            final int size,rootOffset;
            return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
        }
        @Override
        public boolean equals(Object val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public void forEach(Consumer<? super Float> action){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
            }
        }
        @Override
        public void forEach(FloatConsumer action){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
            }
        }
        @Override
        public Float get(int index){
            return super.getFloat(index);
        }
        @Override
        public int hashCode(){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                return forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
            }
            return 1;
        }
        @Override
        public int indexOf(boolean val){
            int size;
            if((size=this.size)!=0){
                if(val){
                    return uncheckedIndexOfFltBits(size,TypeUtil.FLT_TRUE_BITS);
                }
                return uncheckedIndexOfFlt0(size);
            }
            return -1;
        }
        @Override
        public int indexOf(char val){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                return uncheckedIndexOfRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
            }
            return -1;
        }
        @Override
        public int indexOf(double val){
            final int size;
            if((size=this.size)!=0){
                final float v;
                if((v=(float)val)==val){
                    return uncheckedIndexOfFltBits(size,Float.floatToRawIntBits(v));
                }else if(v!=v){
                    return uncheckedIndexOfFltNaN(size);
                }
            }
            return -1;
        }
        @Override
        public int indexOf(float val){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
            }
            return -1;
        }
        @Override
        public int indexOf(int val){
            int size;
            if((size=this.size)!=0){
                if(val!=0){
                    if(!TypeUtil.checkCastToFloat(val)){
                        return -1;
                    }
                    return uncheckedIndexOfFltBits(size,Float.floatToRawIntBits(val));
                }
                return uncheckedIndexOfFlt0(size);
            }
            return -1;
        }
        @Override
        public int indexOf(long val){
            int size;
            if((size=this.size)!=0){
                if(val!=0){
                    if(!TypeUtil.checkCastToFloat(val)){
                        return -1;
                    }
                    return uncheckedIndexOfFltBits(size,Float.floatToRawIntBits(val));
                }
                return uncheckedIndexOfFlt0(size);
            }
            return -1;
        }
        @Override
        public int indexOf(Object val){
            final int size;
            if((size=this.size)!=0&&val instanceof Float){
                final int rootOffset;
                return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(float)val);
            }
            return -1;
        }
        @Override
        public int indexOf(short val){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                return uncheckedIndexOfRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
            }
            return -1;
        }
        @Override
        public OmniIterator.OfFloat iterator(){
            return new AscendingItr(this);
        }
        @Override
        public int lastIndexOf(boolean val){
            int size;
            if((size=this.size)!=0){
                if(val){
                    return uncheckedLastIndexOfFltBits(size,TypeUtil.FLT_TRUE_BITS);
                }
                return uncheckedLastIndexOfFlt0(size);
            }
            return -1;
        }
        @Override
        public int lastIndexOf(char val){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                return uncheckedLastIndexOfRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
            }
            return -1;
        }
        @Override
        public int lastIndexOf(double val){
            final int size;
            if((size=this.size)!=0){
                final float v;
                if((v=(float)val)==val){
                    return uncheckedLastIndexOfFltBits(size,Float.floatToRawIntBits(v));
                }else if(v!=v){
                    return uncheckedLastIndexOfFltNaN(size);
                }
            }
            return -1;
        }
        @Override
        public int lastIndexOf(float val){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
            }
            return -1;
        }
        @Override
        public int lastIndexOf(int val){
            int size;
            if((size=this.size)!=0){
                if(val!=0){
                    if(!TypeUtil.checkCastToFloat(val)){
                        return -1;
                    }
                    return uncheckedLastIndexOfFltBits(size,Float.floatToRawIntBits(val));
                }
                return uncheckedLastIndexOfFlt0(size);
            }
            return -1;
        }
        @Override
        public int lastIndexOf(long val){
            int size;
            if((size=this.size)!=0){
                if(val!=0){
                    if(!TypeUtil.checkCastToFloat(val)){
                        return -1;
                    }
                    return uncheckedLastIndexOfFltBits(size,Float.floatToRawIntBits(val));
                }
                return uncheckedLastIndexOfFlt0(size);
            }
            return -1;
        }
        @Override
        public int lastIndexOf(Object val){
            final int size;
            if((size=this.size)!=0&&val instanceof Float){
                final int rootOffset;
                return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(float)val);
            }
            return -1;
        }
        @Override
        public int lastIndexOf(short val){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                return uncheckedLastIndexOfRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
            }
            return -1;
        }
        @Override
        public OmniListIterator.OfFloat listIterator(){
            return new BidirectionalItr(this);
        }
        @Override
        public OmniListIterator.OfFloat listIterator(int index){
            return new BidirectionalItr(this,index+rootOffset);
        }
        @Override
        public Float remove(int index){
            return super.removeFloatAt(index);
        }
        @Override
        public boolean remove(Object val){
            final int size;
            return (size=this.size)!=0&&val instanceof Float&&uncheckedRemove(size,(float)val);
        }
        @Override
        public boolean removeVal(boolean val){
            final int size;
            if((size=this.size)!=0){
                if(val){
                    return uncheckedRemoveFltBits(size,TypeUtil.FLT_TRUE_BITS);
                }
                return uncheckedRemoveFlt0(size);
            }
            return false;
        }
        @Override
        public boolean removeVal(byte val){
            final int size;
            return (size=this.size)!=0&&uncheckedRemoveRawInt(size,val);
        }
        @Override
        public boolean removeVal(char val){
            final int size;
            return (size=this.size)!=0&&uncheckedRemoveRawInt(size,val);
        }
        @Override
        public boolean removeVal(double val){
            final int size;
            if((size=this.size)!=0){
                final float v;
                if((v=(float)val)==val){
                    return uncheckedRemoveFltBits(size,Float.floatToRawIntBits(v));
                }
                return v!=v&&uncheckedRemoveFltNaN(size);
            }
            return false;
        }
        @Override
        public boolean removeVal(float val){
            final int size;
            return (size=this.size)!=0&&uncheckedRemove(size,val);
        }
        @Override
        public boolean removeVal(int val){
            final int size;
            if((size=this.size)!=0){
                if(val!=0){
                    return TypeUtil.checkCastToFloat(val)&&uncheckedRemoveFltBits(size,Float.floatToRawIntBits(val));
                }
                return uncheckedRemoveFltNaN(size);
            }
            return false;
        }
        @Override
        public boolean removeVal(long val){
            final int size;
            if((size=this.size)!=0){
                if(val!=0){
                    return TypeUtil.checkCastToFloat(val)&&uncheckedRemoveFltBits(size,Float.floatToRawIntBits(val));
                }
                return uncheckedRemoveFltNaN(size);
            }
            return false;
        }
        @Override
        public boolean removeVal(short val){
            final int size;
            return (size=this.size)!=0&&uncheckedRemoveRawInt(size,val);
        }
        @Override
        public void replaceAll(FloatUnaryOperator operator){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
            }
        }
        @Override
        public void replaceAll(UnaryOperator<Float> operator){
            final int size;
            if((size=this.size)!=0){
                final int rootOffset;
                uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
            }
        }
        @Override
        public void reverseSort(){
            int size;
            if((size=this.size)>1){
                final int rootOffset;
                uncheckedReverseSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
            }
        }
        @Override
        public Float set(int index,Float val){
            return super.set(index,val);
        }
        @Override
        public void sort(){
            int size;
            if((size=this.size)>1){
                final int rootOffset;
                uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
            }
        }
        @Override
        public void sort(Comparator<? super Float> sorter){
            final int size;
            if((size=this.size)>1){
                final int rootOffset;
                uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
            }
        }
        @Override
        public void sort(FloatComparator sorter){
            final int size;
            if((size=this.size)>1){
                final int rootOffset;
                uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
            }
        }
        @Override
        public OmniList.OfFloat subList(int fromIndex,int toIndex){
            return new SubList(root,this,rootOffset+fromIndex,toIndex-fromIndex);
        }
        @Override
        public Float[] toArray(){
            final int size;
            if((size=this.size)!=0){
                final Float[] dst;
                ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Float[size],0,size);
                return dst;
            }
            return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            final int size;
            final T[] dst=arrConstructor.apply(size=this.size);
            if(size!=0){
                ArrCopy.uncheckedCopy(root.arr,rootOffset,dst,0,size);
            }
            return dst;
        }
        @Override
        public <T> T[] toArray(T[] dst){
            final int size;
            if((size=this.size)!=0){
                ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
            }else if(dst.length!=0){
                dst[0]=null;
            }
            return dst;
        }
        @Override
        public double[] toDoubleArray(){
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
            final int size;
            if((size=this.size)!=0){
                final float[] dst;
                ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new float[size],0,size);
                return dst;
            }
            return OmniArray.OfFloat.DEFAULT_ARR;
        }
        @Override
        public String toString(){
            final int size;
            if((size=this.size)!=0){
                final StringBuilder builder;
                final int rootOffset;
                forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
                return builder.append(']').toString();
            }
            return "[]";
        }
        private boolean uncheckedContainsFlt0(int size){
            final int rootOffset;
            return AbstractFloatArrSeq.uncheckedContainsFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        private boolean uncheckedContainsFltBits(int size,int fltBits){
            final int rootOffset;
            return AbstractFloatArrSeq.uncheckedContainsFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    fltBits);
        }
        private boolean uncheckedContainsFltNaN(int size){
            final int rootOffset;
            return AbstractFloatArrSeq.uncheckedContainsFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        private int uncheckedIndexOfFlt0(int size){
            final int rootOffset;
            return AbstractFloatArrSeq.uncheckedIndexOfFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        private int uncheckedIndexOfFltBits(int size,int fltBits){
            final int rootOffset;
            return AbstractFloatArrSeq.uncheckedIndexOfFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    fltBits);
        }
        private int uncheckedIndexOfFltNaN(int size){
            final int rootOffset;
            return AbstractFloatArrSeq.uncheckedIndexOfFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        private int uncheckedLastIndexOfFlt0(int size){
            final int rootOffset;
            return AbstractFloatArrSeq.uncheckedLastIndexOfFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        private int uncheckedLastIndexOfFltBits(int size,int fltBits){
            final int rootOffset;
            return AbstractFloatArrSeq.uncheckedLastIndexOfFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    fltBits);
        }
        private int uncheckedLastIndexOfFltNaN(int size){
            final int rootOffset;
            return AbstractFloatArrSeq.uncheckedLastIndexOfFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        private boolean uncheckedRemove(int size,float val){
            if(val==val){
                return uncheckedRemoveFltBits(size,Float.floatToRawIntBits(val));
            }
            return uncheckedRemoveFltNaN(size);
        }
        private boolean uncheckedRemoveFlt0(int size){
            int offset;
            final int bound=(offset=rootOffset)+--size;
            final Unchecked root;
            final var arr=(root=this.root).arr;
            do{
                if(arr[offset]==0){
                    eraseIndexHelper(arr,offset,--root.size);
                    bubbleUpDecrementSize(parent);
                    this.size=size;
                    return true;
                }
            }while(++offset!=bound);
            return false;
        }
        private boolean uncheckedRemoveFltBits(int size,int fltBits){
            int offset;
            final int bound=(offset=rootOffset)+--size;
            final Unchecked root;
            final var arr=(root=this.root).arr;
            do{
                if(Float.floatToRawIntBits(arr[offset])==fltBits){
                    eraseIndexHelper(arr,offset,--root.size);
                    bubbleUpDecrementSize(parent);
                    this.size=size;
                    return true;
                }
            }while(++offset!=bound);
            return false;
        }
        private boolean uncheckedRemoveFltNaN(int size){
            int offset;
            final int bound=(offset=rootOffset)+--size;
            final Unchecked root;
            final var arr=(root=this.root).arr;
            do{
                if(Float.isNaN(arr[offset])){
                    eraseIndexHelper(arr,offset,--root.size);
                    bubbleUpDecrementSize(parent);
                    this.size=size;
                    return true;
                }
            }while(++offset!=bound);
            return false;
        }
        private boolean uncheckedRemoveRawInt(int size,int val){
            if(val!=0){
                return uncheckedRemoveFltBits(size,Float.floatToRawIntBits(val));
            }
            return uncheckedRemoveFlt0(size);
        }
        private static class AscendingItr extends AbstractAscendingItr implements OmniIterator.OfFloat{
            private AscendingItr(SubList parent){
                super(parent);
            }
            @Override
            public void forEachRemaining(Consumer<? super Float> action){
                final int cursor,bound;
                final AbstractSubList parent;
                if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
                    uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
                    this.cursor=bound;
                }
            }
            @Override
            public void forEachRemaining(FloatConsumer action){
                final int cursor,bound;
                final AbstractSubList parent;
                if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
                    uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
                    this.cursor=bound;
                }
            }
            @Override
            public boolean hasNext(){
                return cursor!=parent.getBound();
            }
            @Override
            public Float next(){
                return super.nextFloat();
            }
            @Override
            public void remove(){
                final AbstractSubList parent;
                final Unchecked root;
                eraseIndexHelper((root=(parent=this.parent).root).arr,--cursor,--root.size);
                bubbleUpDecrementSize(parent.parent);
                --parent.size;
            }
        }
        private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfFloat{
            private BidirectionalItr(SubList parent){
                super(parent);
            }
            private BidirectionalItr(SubList parent,int cursor){
                super(parent,cursor);
            }
            @Override
            public void forEachRemaining(Consumer<? super Float> action){
                final int cursor,bound;
                final AbstractSubList parent;
                if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
                    uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
                    this.cursor=bound;
                    lastRet=bound-1;
                }
            }
            @Override
            public void forEachRemaining(FloatConsumer action){
                final int cursor,bound;
                final AbstractSubList parent;
                if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
                    uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
                    this.cursor=bound;
                    lastRet=bound-1;
                }
            }
            @Override
            public boolean hasNext(){
                return cursor!=parent.getBound();
            }
            @Override
            public boolean hasPrevious(){
                return cursor!=parent.rootOffset;
            }
            @Override
            public float nextFloat(){
                final int lastRet;
                this.lastRet=lastRet=cursor++;
                return parent.root.arr[lastRet];
            }
            @Override
            public int nextIndex(){
                return cursor-parent.rootOffset;
            }
            @Override
            public float previousFloat(){
                final int lastRet;
                this.lastRet=lastRet=--cursor;
                return parent.root.arr[lastRet];
            }
            @Override
            public int previousIndex(){
                return cursor-parent.rootOffset-1;
            }
            @Override
            public void remove(){
                final AbstractSubList parent;
                final Unchecked root;
                final int lastRet;
                eraseIndexHelper((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
                bubbleUpDecrementSize(parent.parent);
                --parent.size;
                cursor=lastRet;
            }
            @Override
            public void set(float val){
                parent.root.arr[lastRet]=val;
            }
        }
    }
}
