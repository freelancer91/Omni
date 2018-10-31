package omni.impl.seq.arr;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.DoubleComparator;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class CheckedDoubleArrList extends AbstractDoubleArrSeq.Checked implements OmniList.OfDouble{
  CheckedDoubleArrList(){
    super();
  }
  CheckedDoubleArrList(int capacity){
    super(capacity);
  }
  private CheckedDoubleArrList(int size,double[] arr){
    super(size,arr);
  }
  @Override public void add(int index,Double val){
    super.add(index,val);
  }
  @Override public Object clone(){
    final double[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new double[size],0,size);
    }else{
      arr=null;
    }
    return new CheckedDoubleArrList(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Double get(int index){
    return super.getDouble(index);
  }
  @Override public OmniIterator.OfDouble iterator(){
    return new BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfDouble listIterator(){
    return new BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfDouble listIterator(int index){
    CheckedCollection.checkLo(index);
    CheckedCollection.checkWriteHi(index,size);
    return new BidirectionalItr(this,index);
  }
  @Override public Double remove(int index){
    return super.removeDoubleAt(index);
  }
  @Override public void reverseSort(){
    final int size;
    if((size=this.size)>1){
      uncheckedReverseSort(arr,0,size-1);
      ++modCount;
    }
  }
  @Override public Double set(int index,Double val){
    return super.set(index,val);
  }
  @Override public void sort(){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(arr,0,size-1);
      ++modCount;
    }
  }
  @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
    CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
    return new SubList(this,fromIndex,toIndex-fromIndex);
  }
  @Override void uncheckedCopyInto(double[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Double[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,DoubleConsumer action){
    final int modCount=this.modCount;
    try{
      uncheckedForwardForEachInRange(arr,0,size,action);
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override int uncheckedHashCode(int size){
    return forwardHashCode(arr,0,size);
  }
  @Override boolean uncheckedRemoveFirstDbl0(int size){
    final var arr=this.arr;
    int index=0;
    do{
      if(arr[index]==0){
        ++modCount;
        eraseIndexHelper(arr,index,--size);
        this.size=size;
        return true;
      }
    }while(++index!=size);
    return false;
  }
  @Override boolean uncheckedRemoveFirstDblBits(int size,long dblBits){
    final var arr=this.arr;
    int index=0;
    do{
      if(Double.doubleToRawLongBits(arr[index])==dblBits){
        ++modCount;
        eraseIndexHelper(arr,index,--size);
        this.size=size;
        return true;
      }
    }while(++index!=size);
    return false;
  }
  @Override boolean uncheckedRemoveFirstDblNaN(int size){
    final var arr=this.arr;
    int index=0;
    do{
      if(Double.isNaN(arr[index])){
        ++modCount;
        eraseIndexHelper(arr,index,--size);
        this.size=size;
        return true;
      }
    }while(++index!=size);
    return false;
  }
  @Override void uncheckedToString(int size,StringBuilder builder){
    forwardToString(arr,0,size,builder);
  }
  private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfDouble{
    private BidirectionalItr(Checked root){
      super(root);
    }
    private BidirectionalItr(Checked root,int cursor){
      super(root,cursor);
    }
    @Override public boolean hasPrevious(){
      return cursor!=0;
    }
    @Override public int nextIndex(){
      return cursor;
    }
    @Override public double previousDouble(){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int cursor;
      if((cursor=this.cursor)!=0){
        lastRet=--cursor;
        this.cursor=cursor;
        return root.arr[cursor];
      }
      throw new NoSuchElementException();
    }
    @Override public int previousIndex(){
      return cursor-1;
    }
    @Override public void set(double val){
      final int lastRet;
      if((lastRet=this.lastRet)!=-1){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.arr[lastRet]=val;
        return;
      }
      throw new IllegalStateException();
    }
    @Override public void forEachRemaining(Consumer<? super Double> action){
      final Checked root;
      final int cursor,bound;
      if((cursor=this.cursor)!=(bound=(root=this.root).size)){
        final int expectedModCount=modCount;
        try{
          uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
        }finally{
          CheckedCollection.checkModCount(expectedModCount,root.modCount);
        }
        this.cursor=bound;
        lastRet=bound-1;
      }
    }
    @Override public void forEachRemaining(DoubleConsumer action){
      final Checked root;
      final int cursor,bound;
      if((cursor=this.cursor)!=(bound=(root=this.root).size)){
        final int expectedModCount=modCount;
        try{
          uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
        }finally{
          CheckedCollection.checkModCount(expectedModCount,root.modCount);
        }
        this.cursor=bound;
        lastRet=bound-1;
      }
    }
    @Override public boolean hasNext(){
      return cursor!=root.size;
    }
    @Override public double nextDouble(){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int cursor;
      if((cursor=this.cursor)!=root.size){
        lastRet=cursor;
        this.cursor=cursor+1;
        return root.arr[cursor];
      }
      throw new NoSuchElementException();
    }
    @Override public void remove(){
      final int lastRet;
      if((lastRet=this.lastRet)!=-1){
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        eraseIndexHelper(root.arr,lastRet,--root.size);
        root.modCount=++modCount;
        this.modCount=modCount;
        cursor=lastRet;
        this.lastRet=-1;
        return;
      }
      throw new IllegalStateException();
    }
  }
  private static class SubList extends AbstractSubList implements OmniList.OfDouble{
    private static void bubbleUpIncrementModCount(AbstractSubList parent){
      while(parent!=null){
        ++parent.modCount;
        parent=parent.parent;
      }
    }
    private SubList(Checked root,int rootOffset,int size){
      super(root,rootOffset,size);
    }
    private SubList(Checked root,SubList parent,int rootOffset,int size,int modCount){
      super(root,parent,rootOffset,size,modCount);
    }
    @Override public boolean add(boolean val){
      return super.add(TypeUtil.castToDouble(val));
    }
    @Override public boolean add(char val){
      return super.add(val);
    }
    @Override public boolean add(Double val){
      return super.add(val);
    }
    @Override public boolean add(float val){
      return super.add(val);
    }
    @Override public boolean add(int val){
      return super.add(val);
    }
    @Override public void add(int index,Double val){
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
      final double[] arr;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new double[size],0,size);
      }else{
        arr=null;
      }
      return new CheckedDoubleArrList(size,arr);
    }
    @Override public boolean contains(boolean val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){
        if(val){ return uncheckedContainsDblBits(root,size,TypeUtil.DBL_TRUE_BITS); }
        return uncheckedContainsDbl0(root,size);
      }
      return false;
    }
    @Override public boolean contains(byte val){
      final var root=checkModCountAndGetRoot();
      final int size;
      return (size=this.size)!=0&&uncheckedContains(root,size,val);
    }
    @Override public boolean contains(char val){
      final var root=checkModCountAndGetRoot();
      final int size;
      return (size=this.size)!=0&&uncheckedContains(root,size,val);
    }
    @Override public boolean contains(double val){
      final var root=checkModCountAndGetRoot();
      final int size;
      return (size=this.size)!=0&&uncheckedContains(root,size,val);
    }
    @Override public boolean contains(float val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){
        if(val==val){ return uncheckedContainsDblBits(root,size,Double.doubleToRawLongBits(val)); }
        return uncheckedContainsDblNaN(root,size);
      }
      return false;
    }
    @Override public boolean contains(int val){
      final var root=checkModCountAndGetRoot();
      final int size;
      return (size=this.size)!=0&&uncheckedContains(root,size,val);
    }
    @Override public boolean contains(long val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        if(val!=0){ return TypeUtil.checkCastToDouble(val)
            &&uncheckedContainsDblBits(root,size,Double.doubleToRawLongBits(val)); }
        return uncheckedContainsDbl0(root,size);
      }
      return false;
    }
    @Override public boolean contains(Object val){
      final var root=checkModCountAndGetRoot();
      final int size;
      return (size=this.size)!=0&&val instanceof Double&&uncheckedContains(root,size,(double)val);
    }
    @Override public boolean contains(short val){
      final var root=checkModCountAndGetRoot();
      final int size;
      return (size=this.size)!=0&&uncheckedContains(root,size,val);
    }
    @Override public boolean equals(Object val){
      // TODO Auto-generated method stub
      return false;
    }
    @Override public void forEach(Consumer<? super Double> action){
      final int size;
      if((size=this.size)!=0){
        uncheckedForEach(size,action::accept);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void forEach(DoubleConsumer action){
      final int size;
      if((size=this.size)!=0){
        uncheckedForEach(size,action);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public Double get(int index){
      return super.getDouble(index);
    }
    @Override public int indexOf(boolean val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){
        if(val){ return uncheckedIndexOfDblBits(root,size,TypeUtil.DBL_TRUE_BITS); }
        return uncheckedIndexOfDbl0(root,size);
      }
      return -1;
    }
    @Override public int indexOf(double val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){ return uncheckedIndexOf(root,size,val); }
      return -1;
    }
    @Override public int indexOf(float val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){
        if(val==val){ return uncheckedIndexOfDblBits(root,size,Double.doubleToRawLongBits(val)); }
        return uncheckedIndexOfDblNaN(root,size);
      }
      return -1;
    }
    @Override public int indexOf(int val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){
        if(val!=0){ return uncheckedIndexOfDblBits(root,size,Double.doubleToRawLongBits(val)); }
        return uncheckedIndexOfDbl0(root,size);
      }
      return -1;
    }
    @Override public int indexOf(long val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){
        if(val!=0){
          if(!TypeUtil.checkCastToDouble(val)){ return -1; }
          return uncheckedIndexOfDblBits(root,size,Double.doubleToRawLongBits(val));
        }
        return uncheckedIndexOfDbl0(root,size);
      }
      return -1;
    }
    @Override public int indexOf(Object val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0&&val instanceof Double){ return uncheckedIndexOf(root,size,(double)val); }
      return -1;
    }
    @Override public OmniIterator.OfDouble iterator(){
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      return new BidirectionalItr(this,modCount);
    }
    @Override public int lastIndexOf(boolean val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){
        if(val){ return uncheckedLastIndexOfDblBits(root,size,TypeUtil.DBL_TRUE_BITS); }
        return uncheckedLastIndexOfDbl0(root,size);
      }
      return -1;
    }
    @Override public int lastIndexOf(double val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){ return uncheckedLastIndexOf(root,size,val); }
      return -1;
    }
    @Override public int lastIndexOf(float val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){
        if(val==val){ return uncheckedLastIndexOfDblBits(root,size,Double.doubleToRawLongBits(val)); }
        return uncheckedLastIndexOfDblNaN(root,size);
      }
      return -1;
    }
    @Override public int lastIndexOf(int val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){
        if(val!=0){ return uncheckedLastIndexOfDblBits(root,size,Double.doubleToRawLongBits(val)); }
        return uncheckedLastIndexOfDbl0(root,size);
      }
      return -1;
    }
    @Override public int lastIndexOf(long val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0){
        if(val!=0){
          if(!TypeUtil.checkCastToDouble(val)){ return -1; }
          return uncheckedLastIndexOfDblBits(root,size,Double.doubleToRawLongBits(val));
        }
        return uncheckedLastIndexOfDbl0(root,size);
      }
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0&&val instanceof Double){ return uncheckedLastIndexOf(root,size,(double)val); }
      return -1;
    }
    @Override public OmniListIterator.OfDouble listIterator(){
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      return new BidirectionalItr(this,modCount);
    }
    @Override public OmniListIterator.OfDouble listIterator(int index){
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size);
      return new BidirectionalItr(this,modCount,index+rootOffset);
    }
    @Override public void put(int index,double val){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      root.arr[index+rootOffset]=val;
    }
    @Override public Double remove(int index){
      return super.removeDoubleAt(index);
    }
    @Override public boolean remove(Object val){
      final int size;
      if((size=this.size)!=0&&val instanceof Double){ return uncheckedRemove(size,(double)val); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(boolean val){
      final int size;
      if((size=this.size)!=0){
        if(val){ return uncheckedRemoveDblBits(size,TypeUtil.DBL_TRUE_BITS); }
        return uncheckedRemoveDbl0(size);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(byte val){
      final int size;
      if((size=this.size)!=0){ return uncheckedRemove(size,val); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(char val){
      final int size;
      if((size=this.size)!=0){ return uncheckedRemove(size,val); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(double val){
      final int size;
      if((size=this.size)!=0){ return uncheckedRemove(size,val); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(float val){
      final int size;
      if((size=this.size)!=0){
        if(val==val){ return uncheckedRemoveDblBits(size,Double.doubleToRawLongBits(val)); }
        return uncheckedRemoveDblNaN(size);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      final int size;
      if((size=this.size)!=0){ return uncheckedRemove(size,val); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      final int size;
      if((size=this.size)!=0){
        if(val!=0){
          if(TypeUtil.checkCastToDouble(val)){ return uncheckedRemoveDblBits(size,Double.doubleToRawLongBits(val)); }
        }else{
          return uncheckedRemoveDbl0(size);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(short val){
      final int size;
      if((size=this.size)!=0){ return uncheckedRemove(size,val); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public void replaceAll(DoubleUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        uncheckedReplaceAll(size,operator);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Double> operator){
      final int size;
      if((size=this.size)!=0){
        uncheckedReplaceAll(size,operator::apply);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void reverseSort(){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        uncheckedReverseSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementModCount(parent);
      }
    }
    @Override public Double set(int index,Double val){
      return super.set(index,val);
    }
    @Override public void sort(){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        AbstractDoubleArrSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementModCount(parent);
      }
    }
    @Override public void sort(Comparator<? super Double> sorter){
      final int size;
      if((size=this.size)>1){
        uncheckedSort(size,sorter::compare);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(DoubleComparator sorter){
      final int size;
      if((size=this.size)>1){
        uncheckedSort(size,sorter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
      final Checked root;
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
      return new SubList(root,this,rootOffset+fromIndex,toIndex-fromIndex,modCount);
    }
    @Override public Double[] toArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final Double[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
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
    private Checked checkModCountAndGetRoot(){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      return root;
    }
    private boolean uncheckedContains(Checked root,int size,double val){
      if(val==val){ return uncheckedContainsDblBits(root,size,Double.doubleToRawLongBits(val)); }
      return uncheckedContainsDblNaN(root,size);
    }
    private boolean uncheckedContains(Checked root,int size,int val){
      if(val!=0){ return uncheckedContainsDblBits(root,size,Double.doubleToRawLongBits(val)); }
      return uncheckedContainsDbl0(root,size);
    }
    private boolean uncheckedContainsDbl0(Checked root,int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedContainsDbl0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private boolean uncheckedContainsDblBits(Checked root,int size,long dblBits){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedContainsDblBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,dblBits);
    }
    private boolean uncheckedContainsDblNaN(Checked root,int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedContainsDblNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private void uncheckedForEach(int size,DoubleConsumer action){
      final int modCount=this.modCount;
      final var root=this.root;
      try{
        int rootOffset;
        uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private int uncheckedIndexOf(Checked root,int size,double val){
      if(val==val){ return uncheckedIndexOfDblBits(root,size,Double.doubleToRawLongBits(val)); }
      return uncheckedIndexOfDblNaN(root,size);
    }
    private int uncheckedIndexOfDbl0(Checked root,int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedIndexOfDbl0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedIndexOfDblBits(Checked root,int size,long dblBits){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedIndexOfDblBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,dblBits);
    }
    private int uncheckedIndexOfDblNaN(Checked root,int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedIndexOfDblNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedLastIndexOf(Checked root,int size,double val){
      if(val==val){ return uncheckedLastIndexOfDblBits(root,size,Double.doubleToRawLongBits(val)); }
      return uncheckedLastIndexOfDblNaN(root,size);
    }
    private int uncheckedLastIndexOfDbl0(Checked root,int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedLastIndexOfDbl0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedLastIndexOfDblBits(Checked root,int size,long dblBits){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedLastIndexOfDblBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          dblBits);
    }
    private int uncheckedLastIndexOfDblNaN(Checked root,int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedLastIndexOfDblNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private boolean uncheckedRemove(int size,double val){
      if(val==val){ return uncheckedRemoveDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveDblNaN(size);
    }
    private boolean uncheckedRemove(int size,int val){
      if(val!=0){ return uncheckedRemoveDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveDbl0(size);
    }
    private boolean uncheckedRemoveDbl0(int size){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int offset;
      final int bound=(offset=rootOffset)+--size;
      final var arr=root.arr;
      do{
        if(arr[offset]==0){
          root.modCount=++modCount;
          this.modCount=modCount;
          eraseIndexHelper(arr,offset,--root.size);
          bubbleUpDecrementSize(parent);
          this.size=size;
          return true;
        }
      }while(++offset!=bound);
      return false;
    }
    private boolean uncheckedRemoveDblBits(int size,long dblBits){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int offset;
      final int bound=(offset=rootOffset)+--size;
      final var arr=root.arr;
      do{
        if(Double.doubleToRawLongBits(arr[offset])==dblBits){
          root.modCount=++modCount;
          this.modCount=modCount;
          eraseIndexHelper(arr,offset,--root.size);
          bubbleUpDecrementSize(parent);
          this.size=size;
          return true;
        }
      }while(++offset!=bound);
      return false;
    }
    private boolean uncheckedRemoveDblNaN(int size){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int offset;
      final int bound=(offset=rootOffset)+--size;
      final var arr=root.arr;
      do{
        if(Double.isNaN(arr[offset])){
          root.modCount=++modCount;
          this.modCount=modCount;
          eraseIndexHelper(arr,offset,--root.size);
          bubbleUpDecrementSize(parent);
          this.size=size;
          return true;
        }
      }while(++offset!=bound);
      return false;
    }
    private void uncheckedReplaceAll(int size,DoubleUnaryOperator operator){
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        AbstractDoubleArrSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      bubbleUpIncrementModCount(parent);
    }
    private void uncheckedSort(int size,DoubleComparator sorter){
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        AbstractDoubleArrSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      bubbleUpIncrementModCount(parent);
    }
    private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfDouble{
      private BidirectionalItr(SubList parent,int modCount){
        super(parent,modCount);
      }
      private BidirectionalItr(SubList parent,int modCount,int cursor){
        super(parent,modCount,cursor);
      }
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final Checked root;
          final AbstractSubList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          eraseIndexHelper(root.arr,lastRet,--root.size);
          bubbleUpDecrementSize(parent.parent);
          --parent.size;
          root.modCount=++modCount;
          parent.modCount=modCount;
          this.modCount=modCount;
          cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final int cursor,bound;
        final AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
          final var root=parent.root;
          final int expectedModCount=modCount;
          try{
            uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
          }finally{
            CheckedCollection.checkModCount(expectedModCount,root.modCount);
          }
          this.cursor=bound;
          lastRet=bound-1;
        }
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final int cursor,bound;
        final AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
          final var root=parent.root;
          final int expectedModCount=modCount;
          try{
            uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
          }finally{
            CheckedCollection.checkModCount(expectedModCount,root.modCount);
          }
          this.cursor=bound;
          lastRet=bound-1;
        }
      }
      @Override public boolean hasNext(){
        return cursor!=parent.getBound();
      }
      @Override public boolean hasPrevious(){
        return cursor!=parent.rootOffset;
      }
      @Override public double nextDouble(){
        final Checked root;
        final AbstractSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)!=parent.getBound()){
          lastRet=cursor;
          this.cursor=cursor+1;
          return root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public int nextIndex(){
        return cursor-parent.rootOffset;
      }
      @Override public double previousDouble(){
        final Checked root;
        final AbstractSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        int cursor;
        if((cursor=this.cursor)!=parent.rootOffset){
          lastRet=--cursor;
          this.cursor=cursor;
          return root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public int previousIndex(){
        return cursor-parent.rootOffset-1;
      }
      @Override public void set(double val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final Checked root;
          CheckedCollection.checkModCount(modCount,(root=parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
    }
  }
}
