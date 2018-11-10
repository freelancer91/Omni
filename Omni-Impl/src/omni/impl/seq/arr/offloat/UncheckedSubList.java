package omni.impl.seq.arr.offloat;
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
class UncheckedSubList extends AbstractSeq.Unchecked.AbstractSubList implements OmniList.OfFloat{
    UncheckedSubList(AbstractSeq.Unchecked root,int rootOffset,int size){
        super(root,rootOffset,size);
    }
    private UncheckedSubList(AbstractSeq.Unchecked root,UncheckedSubList parent,int rootOffset,int size){
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
        return new UncheckedList(size,arr);
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
                &&AbstractSeq.uncheckedContainsRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override
    public boolean contains(char val){
        final int size,rootOffset;
        return (size=this.size)!=0
                &&AbstractSeq.uncheckedContainsRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
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
        return (size=this.size)!=0
                &&AbstractSeq.uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
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
                &&AbstractSeq.uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,(float)val);
    }
    @Override
    public boolean contains(short val){
        final int size,rootOffset;
        return (size=this.size)!=0
                &&AbstractSeq.uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
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
            AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    action::accept);
        }
    }
    @Override
    public void forEach(FloatConsumer action){
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
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
            return AbstractSeq.forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
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
            return AbstractSeq.uncheckedIndexOfRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
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
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
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
            return AbstractSeq.uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(float)val);
        }
        return -1;
    }
    @Override
    public int indexOf(short val){
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedIndexOfRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
        }
        return -1;
    }
    @Override
    public OmniIterator.OfFloat iterator(){
        return new UncheckedAscendingSubItr(this);
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
            return AbstractSeq.uncheckedLastIndexOfRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
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
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
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
            return AbstractSeq.uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(float)val);
        }
        return -1;
    }
    @Override
    public int lastIndexOf(short val){
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            return AbstractSeq.uncheckedLastIndexOfRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
        }
        return -1;
    }
    @Override
    public OmniListIterator.OfFloat listIterator(){
        return new UncheckedBidirectionalSubItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator(int index){
        return new UncheckedBidirectionalSubItr(this,index+rootOffset);
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
            AbstractSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
        }
    }
    @Override
    public void replaceAll(UnaryOperator<Float> operator){
        final int size;
        if((size=this.size)!=0){
            final int rootOffset;
            AbstractSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
        }
    }
    @Override
    public void reverseSort(){
        int size;
        if((size=this.size)>1){
            final int rootOffset;
            AbstractSeq.uncheckedReverseSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
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
            AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
        }
    }
    @Override
    public void sort(Comparator<? super Float> sorter){
        final int size;
        if((size=this.size)>1){
            final int rootOffset;
            AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
        }
    }
    @Override
    public void sort(FloatComparator sorter){
        final int size;
        if((size=this.size)>1){
            final int rootOffset;
            AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
        }
    }
    @Override
    public OmniList.OfFloat subList(int fromIndex,int toIndex){
        return new UncheckedSubList(root,this,rootOffset+fromIndex,toIndex-fromIndex);
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
            AbstractSeq.forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,
                    builder=new StringBuilder("["));
            return builder.append(']').toString();
        }
        return "[]";
    }
    private boolean uncheckedContainsFlt0(int size){
        final int rootOffset;
        return AbstractSeq.uncheckedContainsFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private boolean uncheckedContainsFltBits(int size,int fltBits){
        final int rootOffset;
        return AbstractSeq.uncheckedContainsFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,fltBits);
    }
    private boolean uncheckedContainsFltNaN(int size){
        final int rootOffset;
        return AbstractSeq.uncheckedContainsFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedIndexOfFlt0(int size){
        final int rootOffset;
        return AbstractSeq.uncheckedIndexOfFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedIndexOfFltBits(int size,int fltBits){
        final int rootOffset;
        return AbstractSeq.uncheckedIndexOfFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,fltBits);
    }
    private int uncheckedIndexOfFltNaN(int size){
        final int rootOffset;
        return AbstractSeq.uncheckedIndexOfFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedLastIndexOfFlt0(int size){
        final int rootOffset;
        return AbstractSeq.uncheckedLastIndexOfFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedLastIndexOfFltBits(int size,int fltBits){
        final int rootOffset;
        return AbstractSeq.uncheckedLastIndexOfFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,fltBits);
    }
    private int uncheckedLastIndexOfFltNaN(int size){
        final int rootOffset;
        return AbstractSeq.uncheckedLastIndexOfFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
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
        final AbstractSeq.Unchecked root;
        final var arr=(root=this.root).arr;
        do{
            if(arr[offset]==0){
                AbstractSeq.eraseIndexHelper(arr,offset,--root.size);
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
        final AbstractSeq.Unchecked root;
        final var arr=(root=this.root).arr;
        do{
            if(Float.floatToRawIntBits(arr[offset])==fltBits){
                AbstractSeq.eraseIndexHelper(arr,offset,--root.size);
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
        final AbstractSeq.Unchecked root;
        final var arr=(root=this.root).arr;
        do{
            if(Float.isNaN(arr[offset])){
                AbstractSeq.eraseIndexHelper(arr,offset,--root.size);
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
}