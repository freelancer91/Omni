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
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
class CheckedSubList extends AbstractSeq.Checked.AbstractSubList implements OmniList.OfFloat{
  private static void bubbleUpIncrementModCount(AbstractSeq.Checked.AbstractSubList parent){
    while(parent!=null){
      ++parent.modCount;
      parent=parent.parent;
    }
  }
  CheckedSubList(AbstractSeq.Checked root,int rootOffset,int size){
    super(root,rootOffset,size);
  }
  private CheckedSubList(AbstractSeq.Checked root,CheckedSubList parent,int rootOffset,int size,int modCount){
    super(root,parent,rootOffset,size,modCount);
  }
  @Override public boolean add(boolean val){
    return super.add(TypeUtil.castToFloat(val));
  }
  @Override public boolean add(char val){
    return super.add(val);
  }
  @Override public boolean add(Float val){
    return super.add(val);
  }
  @Override public boolean add(int val){
    return super.add(val);
  }
  @Override public void add(int index,Float val){
    super.add(index,val);
  }
  @Override public boolean add(long val){
    return super.add(val);
  }
  @Override public boolean add(short val){
    return super.add(val);
  }
  @Override public Object clone(){
    final var root=checkModCountAndGetRoot();
    final float[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new float[size],0,size);
    }else{
      arr=null;
    }
    return new CheckedList(size,arr);
  }
  @Override public boolean contains(boolean val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedContainsFltBits(root,size,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedContainsFlt0(root,size);
    }
    return false;
  }
  @Override public boolean contains(byte val){
    final var root=checkModCountAndGetRoot();
    final int size;
    return (size=this.size)!=0&&uncheckedContainsRawInt(root,size,val);
  }
  @Override public boolean contains(char val){
    final var root=checkModCountAndGetRoot();
    final int size;
    return (size=this.size)!=0&&uncheckedContainsRawInt(root,size,val);
  }
  @Override public boolean contains(double val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final float v;
      if((v=(float)val)==val){ return uncheckedContainsFltBits(root,size,Float.floatToRawIntBits(v)); }
      return v!=v&&uncheckedContainsFltNaN(root,size);
    }
    return false;
  }
  @Override public boolean contains(float val){
    final var root=checkModCountAndGetRoot();
    final int size;
    return (size=this.size)!=0&&uncheckedContains(root,size,val);
  }
  @Override public boolean contains(int val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedContainsFltBits(root,size,Float.floatToRawIntBits(val)); }
      return uncheckedContainsFlt0(root,size);
    }
    return false;
  }
  @Override public boolean contains(long val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedContainsFltBits(root,size,Float.floatToRawIntBits(val)); }
      return uncheckedContainsFlt0(root,size);
    }
    return false;
  }
  @Override public boolean contains(Object val){
    final var root=checkModCountAndGetRoot();
    final int size;
    return (size=this.size)!=0&&val instanceof Float&&uncheckedContains(root,size,(float)val);
  }
  @Override public boolean contains(short val){
    final var root=checkModCountAndGetRoot();
    final int size;
    return (size=this.size)!=0&&uncheckedContainsRawInt(root,size,val);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public void forEach(Consumer<? super Float> action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action::accept);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public void forEach(FloatConsumer action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public Float get(int index){
    return super.getFloat(index);
  }
  @Override public int indexOf(boolean val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedIndexOfFltBits(root,size,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedIndexOfFlt0(root,size);
    }
    return -1;
  }
  @Override public int indexOf(char val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){ return uncheckedIndexOfRawInt(root,size,val); }
    return -1;
  }
  @Override public int indexOf(double val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final float v;
      if((v=(float)val)==val){
        return uncheckedIndexOfFltBits(root,size,Float.floatToRawIntBits(v));
      }else if(v!=v){ return uncheckedIndexOfFltNaN(root,size); }
    }
    return -1;
  }
  @Override public int indexOf(float val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){ return uncheckedIndexOf(root,size,val); }
    return -1;
  }
  @Override public int indexOf(int val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToFloat(val)){ return -1; }
        return uncheckedIndexOfFltBits(root,size,Float.floatToRawIntBits(val));
      }
      return uncheckedIndexOfFlt0(root,size);
    }
    return -1;
  }
  @Override public int indexOf(long val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToFloat(val)){ return -1; }
        return uncheckedIndexOfFltBits(root,size,Float.floatToRawIntBits(val));
      }
      return uncheckedIndexOfFlt0(root,size);
    }
    return -1;
  }
  @Override public int indexOf(Object val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0&&val instanceof Float){ return uncheckedIndexOf(root,size,(float)val); }
    return -1;
  }
  @Override public int indexOf(short val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){ return uncheckedIndexOfRawInt(root,size,val); }
    return -1;
  }
  @Override public OmniIterator.OfFloat iterator(){
    final int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
    return new CheckedBidirectionalSubItr(this,modCount);
  }
  @Override public int lastIndexOf(boolean val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedLastIndexOfFltBits(root,size,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedLastIndexOfFlt0(root,size);
    }
    return -1;
  }
  @Override public int lastIndexOf(char val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOfRawInt(root,size,val); }
    return -1;
  }
  @Override public int lastIndexOf(double val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final float v;
      if((v=(float)val)==val){
        return uncheckedLastIndexOfFltBits(root,size,Float.floatToRawIntBits(v));
      }else if(v!=v){ return uncheckedLastIndexOfFltNaN(root,size); }
    }
    return -1;
  }
  @Override public int lastIndexOf(float val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOf(root,size,val); }
    return -1;
  }
  @Override public int lastIndexOf(int val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToFloat(val)){ return -1; }
        return uncheckedLastIndexOfFltBits(root,size,Float.floatToRawIntBits(val));
      }
      return uncheckedLastIndexOfFlt0(root,size);
    }
    return -1;
  }
  @Override public int lastIndexOf(long val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToFloat(val)){ return -1; }
        return uncheckedLastIndexOfFltBits(root,size,Float.floatToRawIntBits(val));
      }
      return uncheckedLastIndexOfFlt0(root,size);
    }
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0&&val instanceof Float){ return uncheckedLastIndexOf(root,size,(float)val); }
    return -1;
  }
  @Override public int lastIndexOf(short val){
    final var root=checkModCountAndGetRoot();
    int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOfRawInt(root,size,val); }
    return -1;
  }
  @Override public OmniListIterator.OfFloat listIterator(){
    final int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
    return new CheckedBidirectionalSubItr(this,modCount);
  }
  @Override public OmniListIterator.OfFloat listIterator(int index){
    final int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
    CheckedCollection.checkLo(index);
    CheckedCollection.checkWriteHi(index,size);
    return new CheckedBidirectionalSubItr(this,modCount,index+rootOffset);
  }
  @Override public void put(int index,float val){
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,size);
    root.arr[index+rootOffset]=val;
  }
  @Override public Float remove(int index){
    return super.removeFloatAt(index);
  }
  @Override public boolean remove(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Float){ return uncheckedRemove(size,(float)val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(boolean val){
    final int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedRemoveFltBits(size,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedRemoveFlt0(size);
    }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(byte val){
    final int size;
    if((size=this.size)!=0){ return uncheckedRemoveRawInt(size,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(char val){
    final int size;
    if((size=this.size)!=0){ return uncheckedRemoveRawInt(size,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(double val){
    final int size;
    if((size=this.size)!=0){
      float v;
      if((v=(float)val)==val){
        return uncheckedRemoveFltBits(size,Float.floatToRawIntBits(v));
      }else if(v!=v){ return uncheckedRemoveFltNaN(size); }
    }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(float val){
    final int size;
    if((size=this.size)!=0){ return uncheckedRemove(size,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(int val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(TypeUtil.checkCastToFloat(val)){ return uncheckedRemoveFltBits(size,Float.floatToRawIntBits(val)); }
      }else{
        return uncheckedRemoveFlt0(size);
      }
    }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(TypeUtil.checkCastToFloat(val)){ return uncheckedRemoveFltBits(size,Float.floatToRawIntBits(val)); }
      }else{
        return uncheckedRemoveFlt0(size);
      }
    }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(short val){
    final int size;
    if((size=this.size)!=0){ return uncheckedRemoveRawInt(size,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public void replaceAll(FloatUnaryOperator operator){
    final int size;
    if((size=this.size)!=0){
      uncheckedReplaceAll(size,operator);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public void replaceAll(UnaryOperator<Float> operator){
    final int size;
    if((size=this.size)!=0){
      uncheckedReplaceAll(size,operator::apply);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public void reverseSort(){
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
  @Override public Float set(int index,Float val){
    return super.set(index,val);
  }
  @Override public void sort(){
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
  @Override public void sort(Comparator<? super Float> sorter){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(size,sorter::compare);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public void sort(FloatComparator sorter){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(size,sorter);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
    final AbstractSeq.Checked root;
    final int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
    return new CheckedSubList(root,this,rootOffset+fromIndex,toIndex-fromIndex,modCount);
  }
  @Override public Float[] toArray(){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final Float[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Float[size],0,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
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
  @Override public <T> T[] toArray(T[] dst){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public double[] toDoubleArray(){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final double[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new double[size],0,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final float[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new float[size],0,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  private AbstractSeq.Checked checkModCountAndGetRoot(){
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    return root;
  }
  private boolean uncheckedContains(AbstractSeq.Checked root,int size,float val){
    if(val==val){ return uncheckedContainsFltBits(root,size,Float.floatToRawIntBits(val)); }
    return uncheckedContainsFltNaN(root,size);
  }
  private boolean uncheckedContainsFlt0(AbstractSeq.Checked root,int size){
    final int rootOffset;
    return AbstractSeq.uncheckedAnyMatchesFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private boolean uncheckedContainsFltBits(AbstractSeq.Checked root,int size,int fltBits){
    final int rootOffset;
    return AbstractSeq.uncheckedAnyMatchesFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,fltBits);
  }
  private boolean uncheckedContainsFltNaN(AbstractSeq.Checked root,int size){
    final int rootOffset;
    return AbstractSeq.uncheckedAnyMatchesFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private boolean uncheckedContainsRawInt(AbstractSeq.Checked root,int size,int val){
    if(val!=0){ return uncheckedContainsFltBits(root,size,Float.floatToRawIntBits(val)); }
    return uncheckedContainsFlt0(root,size);
  }
  private void uncheckedForEach(int size,FloatConsumer action){
    final int modCount=this.modCount;
    final var root=this.root;
    try{
      int rootOffset;
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  private int uncheckedIndexOf(AbstractSeq.Checked root,int size,float val){
    if(val==val){ return uncheckedIndexOfFltBits(root,size,Float.floatToRawIntBits(val)); }
    return uncheckedIndexOfFltNaN(root,size);
  }
  private int uncheckedIndexOfFlt0(AbstractSeq.Checked root,int size){
    final int rootOffset;
    return AbstractSeq.uncheckedIndexOfFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private int uncheckedIndexOfFltBits(AbstractSeq.Checked root,int size,int fltBits){
    final int rootOffset;
    return AbstractSeq.uncheckedIndexOfFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,fltBits);
  }
  private int uncheckedIndexOfFltNaN(AbstractSeq.Checked root,int size){
    final int rootOffset;
    return AbstractSeq.uncheckedIndexOfFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private int uncheckedIndexOfRawInt(AbstractSeq.Checked root,int size,int val){
    if(val!=0){ return uncheckedIndexOfFltBits(root,size,Float.floatToRawIntBits(val)); }
    return uncheckedIndexOfFlt0(root,size);
  }
  private int uncheckedLastIndexOf(AbstractSeq.Checked root,int size,float val){
    if(val==val){ return uncheckedLastIndexOfFltBits(root,size,Float.floatToRawIntBits(val)); }
    return uncheckedLastIndexOfFltNaN(root,size);
  }
  private int uncheckedLastIndexOfFlt0(AbstractSeq.Checked root,int size){
    final int rootOffset;
    return AbstractSeq.uncheckedLastIndexOfFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private int uncheckedLastIndexOfFltBits(AbstractSeq.Checked root,int size,int fltBits){
    final int rootOffset;
    return AbstractSeq.uncheckedLastIndexOfFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,fltBits);
  }
  private int uncheckedLastIndexOfFltNaN(AbstractSeq.Checked root,int size){
    final int rootOffset;
    return AbstractSeq.uncheckedLastIndexOfFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private int uncheckedLastIndexOfRawInt(AbstractSeq.Checked root,int size,int val){
    if(val!=0){ return uncheckedLastIndexOfFltBits(root,size,Float.floatToRawIntBits(val)); }
    return uncheckedLastIndexOfFlt0(root,size);
  }
  private boolean uncheckedRemove(int size,float val){
    if(val==val){ return uncheckedRemoveFltBits(size,Float.floatToRawIntBits(val)); }
    return uncheckedRemoveFltNaN(size);
  }
  private boolean uncheckedRemoveFlt0(int size){
    int modCount;
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    int offset;
    final int bound=(offset=rootOffset)+--size;
    final var arr=root.arr;
    do{
      if(arr[offset]==0){
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
  private boolean uncheckedRemoveFltBits(int size,int fltBits){
    int modCount;
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    int offset;
    final int bound=(offset=rootOffset)+--size;
    final var arr=root.arr;
    do{
      if(Float.floatToRawIntBits(arr[offset])==fltBits){
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
  private boolean uncheckedRemoveFltNaN(int size){
    int modCount;
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    int offset;
    final int bound=(offset=rootOffset)+--size;
    final var arr=root.arr;
    do{
      if(Float.isNaN(arr[offset])){
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
  private boolean uncheckedRemoveRawInt(int size,int val){
    if(val!=0){ return uncheckedRemoveFltBits(size,Float.floatToRawIntBits(val)); }
    return uncheckedRemoveFlt0(size);
  }
  private void uncheckedReplaceAll(int size,FloatUnaryOperator operator){
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
  private void uncheckedSort(int size,FloatComparator sorter){
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