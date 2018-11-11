package omni.impl.seq.arr.ofshort;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.ShortComparator;
import omni.function.ShortConsumer;
import omni.function.ShortUnaryOperator;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
class CheckedSubList extends AbstractSeq.Checked.AbstractSubList implements OmniList.OfShort{
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
    return super.add(TypeUtil.castToByte(val));
  }
  @Override public void add(int index,Short val){
    super.add(index,val);
  }
  @Override public boolean add(Short val){
    return super.add(val);
  }
  @Override public Object clone(){
    final var root=checkModCountAndGetRoot();
    final short[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new short[size],0,size);
    }else{
      arr=null;
    }
    return new CheckedList(size,arr);
  }
  @Override public boolean contains(boolean val){
    final var root=checkModCountAndGetRoot();
    final int size,rootOffset;
    return (size=this.size)!=0
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,TypeUtil.castToByte(val));
  }
  @Override public boolean contains(byte val){
    final var root=checkModCountAndGetRoot();
    final int size,rootOffset;
    return (size=this.size)!=0
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override public boolean contains(char val){
    final var root=checkModCountAndGetRoot();
    final int size,rootOffset;
    return val<=Short.MAX_VALUE&&(size=this.size)!=0
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override public boolean contains(double val){
    final var root=checkModCountAndGetRoot();
    final int size,rootOffset,v;
    return (size=this.size)!=0&&val==(v=(short)val)
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
  }
  @Override public boolean contains(float val){
    final var root=checkModCountAndGetRoot();
    final int size,rootOffset,v;
    return (size=this.size)!=0&&val==(v=(short)val)
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
  }
  @Override public boolean contains(int val){
    final var root=checkModCountAndGetRoot();
    final int size,rootOffset;
    return (size=this.size)!=0&&val==(short)val
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override public boolean contains(long val){
    final var root=checkModCountAndGetRoot();
    final int size,rootOffset,v;
    return (size=this.size)!=0&&val==(v=(short)val)
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
  }
  @Override public boolean contains(Object val){
    final var root=checkModCountAndGetRoot();
    final int size,rootOffset;
    return (size=this.size)!=0&&val instanceof Short
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,(short)val);
  }
  @Override public boolean contains(short val){
    final var root=checkModCountAndGetRoot();
    final int size,rootOffset;
    return (size=this.size)!=0
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public void forEach(Consumer<? super Short> action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action::accept);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public void forEach(ShortConsumer action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public Short get(int index){
    return super.getShort(index);
  }
  @Override public int hashCode(){
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    return 1;
  }
  @Override public int indexOf(boolean val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          TypeUtil.castToByte(val));
    }
    return -1;
  }
  @Override public int indexOf(char val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if(val<=Short.MAX_VALUE&&(size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int indexOf(double val){
    final var root=checkModCountAndGetRoot();
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int indexOf(float val){
    final var root=checkModCountAndGetRoot();
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int indexOf(int val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0&&val==(short)val){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int indexOf(long val){
    final var root=checkModCountAndGetRoot();
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int indexOf(Object val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0&&val instanceof Short){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,(short)val);
    }
    return -1;
  }
  @Override public int indexOf(short val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public OmniIterator.OfShort iterator(){
    final int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
    return new CheckedBidirectionalSubItr(this,modCount);
  }
  @Override public int lastIndexOf(boolean val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          TypeUtil.castToByte(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(char val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if(val<=Short.MAX_VALUE&&(size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int lastIndexOf(double val){
    final var root=checkModCountAndGetRoot();
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int lastIndexOf(float val){
    final var root=checkModCountAndGetRoot();
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int lastIndexOf(int val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0&&val==(short)val){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int lastIndexOf(long val){
    final var root=checkModCountAndGetRoot();
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0&&val instanceof Short){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,(short)val);
    }
    return -1;
  }
  @Override public int lastIndexOf(short val){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public OmniListIterator.OfShort listIterator(){
    final int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
    return new CheckedBidirectionalSubItr(this,modCount);
  }
  @Override public OmniListIterator.OfShort listIterator(int index){
    final int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
    CheckedCollection.checkLo(index);
    CheckedCollection.checkWriteHi(index,size);
    return new CheckedBidirectionalSubItr(this,modCount,index+rootOffset);
  }
  @Override public void put(int index,short val){
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,size);
    root.arr[index+rootOffset]=val;
  }
  @Override public Short remove(int index){
    return super.removeShortAt(index);
  }
  @Override public boolean remove(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Short){ return uncheckedRemoveFirstMatch(size,(short)val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(boolean val){
    final int size;
    if((size=this.size)!=0){ return uncheckedRemoveFirstMatch(size,TypeUtil.castToByte(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(byte val){
    final int size;
    if((size=this.size)!=0){ return uncheckedRemoveFirstMatch(size,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(char val){
    final int size;
    if(val<=Short.MAX_VALUE&&(size=this.size)!=0){ return uncheckedRemoveFirstMatch(size,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(double val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedRemoveFirstMatch(size,v); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(float val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedRemoveFirstMatch(size,v); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(int val){
    final int size;
    if((size=this.size)!=0&&val==(short)val){ return uncheckedRemoveFirstMatch(size,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(long val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedRemoveFirstMatch(size,v); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(short val){
    final int size;
    if((size=this.size)!=0){ return uncheckedRemoveFirstMatch(size,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public void replaceAll(ShortUnaryOperator operator){
    final int size;
    if((size=this.size)!=0){
      uncheckedReplaceAll(size,operator);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public void replaceAll(UnaryOperator<Short> operator){
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
  @Override public Short set(int index,Short val){
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
  @Override public void sort(Comparator<? super Short> sorter){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(size,sorter::compare);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public void sort(ShortComparator sorter){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(size,sorter);
    }else{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public OmniList.OfShort subList(int fromIndex,int toIndex){
    final AbstractSeq.Checked root;
    final int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
    return new CheckedSubList(root,this,rootOffset+fromIndex,toIndex-fromIndex,modCount);
  }
  @Override public Short[] toArray(){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final Short[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Short[size],0,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_BOXED_ARR;
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
  @Override public int[] toIntArray(){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final int[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new int[size],0,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final long[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new long[size],0,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    final var root=checkModCountAndGetRoot();
    final int size;
    if((size=this.size)!=0){
      final short[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new short[size],0,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public String toString(){
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    final int size;
    if((size=this.size)!=0){
      final StringBuilder builder;
      final int rootOffset;
      AbstractSeq.forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  private AbstractSeq.Checked checkModCountAndGetRoot(){
    final AbstractSeq.Checked root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    return root;
  }
  private void uncheckedForEach(int size,ShortConsumer action){
    final int modCount=this.modCount;
    final var root=this.root;
    try{
      int rootOffset;
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  private boolean uncheckedRemoveFirstMatch(int size,int val){
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
  private void uncheckedReplaceAll(int size,ShortUnaryOperator operator){
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
  private void uncheckedSort(int size,ShortComparator sorter){
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