package omni.impl.seq.arr.ofref;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.OmniPred;
class UncheckedSubList<E>extends AbstractSeq.Unchecked.AbstractSubList<E> implements OmniList.OfRef<E>{
  static void bubbleUpDecrementSize(AbstractSeq.Unchecked.AbstractSubList<?> parent){
    while(parent!=null){
      --parent.size;
      parent=parent.parent;
    }
  }
  UncheckedSubList(AbstractSeq.Unchecked<E> root,int rootOffset,int size){
    super(root,rootOffset,size);
  }
  private UncheckedSubList(AbstractSeq.Unchecked<E> root,UncheckedSubList<E> parent,int rootOffset,int size){
    super(root,parent,rootOffset,size);
  }
  @Override public Object clone(){
    final Object[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new Object[size],0,size);
    }else{
      arr=null;
    }
    return new UncheckedList<>(size,arr);
  }
  @Override public boolean contains(boolean val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Boolean val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(byte val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Byte val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(char val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Character val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(double val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Double val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(float val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Float val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(int val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Integer val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(long val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Long val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Object val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(short val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Short val){
    final int size,rootOffset;
    return (size=this.size)!=0&&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,
        OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public void forEach(Consumer<? super E> action){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
    }
  }
  @SuppressWarnings("unchecked") @Override public E get(int index){
    return (E)root.arr[index+rootOffset];
  }
  @Override public int hashCode(){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    return 1;
  }
  @Override public int indexOf(boolean val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(Boolean val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(byte val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(Byte val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(char val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(Character val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(double val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(Double val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(float val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(Float val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(int val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(Integer val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(long val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(Long val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(Object val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(short val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int indexOf(Short val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public OmniIterator.OfRef<E> iterator(){
    return new UncheckedAscendingSubItr<>(this);
  }
  @Override public int lastIndexOf(boolean val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(Boolean val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(byte val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(Byte val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(char val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(Character val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(double val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(Double val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(float val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(Float val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(int val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(Integer val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(long val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(Long val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(short val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(Short val){
    int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override public OmniListIterator.OfRef<E> listIterator(){
    return new UncheckedBidirectionalSubItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(int index){
    return new UncheckedBidirectionalSubItr<>(this,index+rootOffset);
  }
  @Override public void put(int index,E val){
    root.arr[index+rootOffset]=val;
  }
  @Override public E remove(int index){
    final AbstractSeq.Unchecked<E> root;
    final Object[] arr;
    @SuppressWarnings("unchecked") final var removed=(E)(arr=(root=this.root).arr)[index+=rootOffset];
    AbstractSeq.eraseIndexHelper(arr,index,--root.size);
    bubbleUpDecrementSize(parent);
    --size;
    return removed;
  }
  @Override public boolean remove(Object val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(boolean val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Boolean val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(byte val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Byte val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(char val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Character val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(double val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Double val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(float val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Float val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(int val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Integer val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(long val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Long val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(short val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Short val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public void replaceAll(UnaryOperator<E> operator){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      AbstractSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
    }
  }
  @Override public void reverseSort(){
    final int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Comparator.reverseOrder());
    }
  }
  @Override public E set(int index,E val){
    final Object[] arr;
    @SuppressWarnings("unchecked") final var oldVal=(E)(arr=root.arr)[index+=rootOffset];
    arr[index]=val;
    return oldVal;
  }
  @Override public void sort(){
    final int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Comparator.naturalOrder());
    }
  }
  @Override public void sort(Comparator<? super E> sorter){
    final int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
    }
  }
  @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
    return new UncheckedSubList<>(root,this,rootOffset+fromIndex,toIndex-fromIndex);
  }
  @Override public Object[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Object[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Object[size],0,size);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst,0,size);
    }
    return dst;
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public String toString(){
    final int size;
    if((size=this.size)!=0){
      final StringBuilder builder;
      final int rootOffset;
      AbstractSeq.forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  private boolean uncheckedRemoveFirstMatch(int size,Predicate<Object> pred){
    int offset;
    final int bound=(offset=rootOffset)+--size;
    final AbstractSeq.Unchecked<E> root;
    final var arr=(root=this.root).arr;
    do{
      if(pred.test(arr[offset])){
        AbstractSeq.eraseIndexHelper(arr,offset,--root.size);
        bubbleUpDecrementSize(parent);
        this.size=size;
        return true;
      }
    }while(++offset!=bound);
    return false;
  }
}