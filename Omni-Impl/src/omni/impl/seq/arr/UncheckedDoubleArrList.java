package omni.impl.seq.arr;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.DoubleComparator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class UncheckedDoubleArrList extends AbstractDoubleArrSeq.Unchecked implements OmniList.OfDouble{
  UncheckedDoubleArrList(){
    super();
  }
  UncheckedDoubleArrList(int capacity){
    super(capacity);
  }
  private UncheckedDoubleArrList(int size,double[] arr){
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
    return new UncheckedDoubleArrList(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Double get(int index){
    return super.getDouble(index);
  }
  @Override public OmniIterator.OfDouble iterator(){
    return new AscendingItr(this);
  }
  @Override public OmniListIterator.OfDouble listIterator(){
    return new BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfDouble listIterator(int index){
    return new BidirectionalItr(this,index);
  }
  @Override public Double remove(int index){
    return super.removeDoubleAt(index);
  }
  @Override public void reverseSort(){
    int size;
    if((size=this.size)>1){
      uncheckedReverseSort(arr,0,size-1);
    }
  }
  @Override public Double set(int index,Double val){
    return super.set(index,val);
  }
  @Override public void sort(){
    int size;
    if((size=this.size)>1){
      uncheckedSort(arr,0,size-1);
    }
  }
  @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
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
    uncheckedForwardForEachInRange(arr,0,size,action);
  }
  @Override int uncheckedHashCode(int size){
    return forwardHashCode(arr,0,size);
  }
  @Override boolean uncheckedRemoveFirstDbl0(int size){
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
  @Override boolean uncheckedRemoveFirstDblBits(int size,long dblBits){
    final var arr=this.arr;
    int index=0;
    do{
      if(Double.doubleToRawLongBits(arr[index])==dblBits){
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
  private static class AscendingItr extends AbstractAscendingItr implements OmniIterator.OfDouble{
    private AscendingItr(Unchecked root){
      super(root);
    }
    @Override public void forEachRemaining(Consumer<? super Double> action){
      final Unchecked root;
      final int cursor,bound;
      if((cursor=this.cursor)!=(bound=(root=this.root).size)){
        uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
        this.cursor=bound;
      }
    }
    @Override public void forEachRemaining(DoubleConsumer action){
      final Unchecked root;
      final int cursor,bound;
      if((cursor=this.cursor)!=(bound=(root=this.root).size)){
        uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
        this.cursor=bound;
      }
    }
    @Override public boolean hasNext(){
      return cursor!=root.size;
    }
    @Override public Double next(){
      return super.nextDouble();
    }
    @Override public void remove(){
      final Unchecked root;
      eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
    }
  }
  private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfDouble{
    private BidirectionalItr(Unchecked root){
      super(root);
    }
    @Override public void forEachRemaining(Consumer<? super Double> action){
      final int cursor,bound;
      final Unchecked root;
      if((cursor=this.cursor)!=(bound=(root=this.root).size)){
        uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
        this.cursor=bound;
        lastRet=bound-1;
      }
    }
    @Override public void forEachRemaining(DoubleConsumer action){
      final int cursor,bound;
      final Unchecked root;
      if((cursor=this.cursor)!=(bound=(root=this.root).size)){
        uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
        this.cursor=bound;
        lastRet=bound-1;
      }
    }
    @Override public boolean hasNext(){
      return cursor!=root.size;
    }
    @Override public void remove(){
      final Unchecked root;
      final int lastRet;
      eraseIndexHelper((root=this.root).arr,lastRet=this.lastRet,--root.size);
      cursor=lastRet;
    }
    private BidirectionalItr(Unchecked root,int cursor){
      super(root,cursor);
    }
    @Override public boolean hasPrevious(){
      return cursor!=0;
    }
    @Override public double nextDouble(){
      final int lastRet;
      this.lastRet=lastRet=cursor++;
      return root.arr[lastRet];
    }
    @Override public int nextIndex(){
      return cursor;
    }
    @Override public double previousDouble(){
      final int lastRet;
      this.lastRet=lastRet=--cursor;
      return root.arr[lastRet];
    }
    @Override public int previousIndex(){
      return cursor-1;
    }
    @Override public void set(double val){
      root.arr[lastRet]=val;
    }
  }
  private static class SubList extends AbstractSubList implements OmniList.OfDouble{
    private SubList(Unchecked root,int rootOffset,int size){
      super(root,rootOffset,size);
    }
    private SubList(Unchecked root,SubList parent,int rootOffset,int size){
      super(root,parent,rootOffset,size);
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
      final double[] arr;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new double[size],0,size);
      }else{
        arr=null;
      }
      return new UncheckedDoubleArrList(size,arr);
    }
    @Override public boolean contains(boolean val){
      int size;
      if((size=this.size)!=0){
        if(val){ return uncheckedContainsDblBits(size,TypeUtil.DBL_TRUE_BITS); }
        return uncheckedContainsDbl0(size);
      }
      return false;
    }
    @Override public boolean contains(byte val){
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(char val){
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(double val){
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(float val){
      int size;
      if((size=this.size)!=0){
        if(val==val){ return uncheckedContainsDblBits(size,Double.doubleToRawLongBits(val)); }
        return uncheckedContainsDblNaN(size);
      }
      return false;
    }
    @Override public boolean contains(int val){
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(long val){
      int size;
      if((size=this.size)!=0){
        if(val!=0){ return TypeUtil.checkCastToDouble(val)
            &&uncheckedContainsDblBits(size,Double.doubleToRawLongBits(val)); }
        return uncheckedContainsDbl0(size);
      }
      return false;
    }
    @Override public boolean contains(Object val){
      final int size,rootOffset;
      return (size=this.size)!=0&&val instanceof Double
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,(double)val);
    }
    @Override public boolean contains(short val){
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean equals(Object val){
      // TODO Auto-generated method stub
      return false;
    }
    @Override public void forEach(Consumer<? super Double> action){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
      }
    }
    @Override public void forEach(DoubleConsumer action){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
      }
    }
    @Override public Double get(int index){
      return super.getDouble(index);
    }
    @Override public int hashCode(){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      return 1;
    }
    @Override public int indexOf(boolean val){
      int size;
      if((size=this.size)!=0){
        if(val){ return uncheckedIndexOfDblBits(size,TypeUtil.DBL_TRUE_BITS); }
        return uncheckedIndexOfDbl0(size);
      }
      return -1;
    }
    @Override public int indexOf(double val){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public int indexOf(float val){
      int size;
      if((size=this.size)!=0){
        if(val==val){ return uncheckedIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
        return uncheckedIndexOfDblNaN(size);
      }
      return -1;
    }
    @Override public int indexOf(int val){
      int size;
      if((size=this.size)!=0){
        if(val!=0){ return uncheckedIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
        return uncheckedIndexOfDbl0(size);
      }
      return -1;
    }
    @Override public int indexOf(long val){
      int size;
      if((size=this.size)!=0){
        if(val!=0){
          if(!TypeUtil.checkCastToDouble(val)){ return -1; }
          return uncheckedIndexOfDblBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedIndexOfDbl0(size);
      }
      return -1;
    }
    @Override public int indexOf(Object val){
      final int size;
      if((size=this.size)!=0&&val instanceof Double){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(double)val);
      }
      return -1;
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new AscendingItr(this);
    }
    @Override public int lastIndexOf(boolean val){
      int size;
      if((size=this.size)!=0){
        if(val){ return uncheckedLastIndexOfDblBits(size,TypeUtil.DBL_TRUE_BITS); }
        return uncheckedLastIndexOfDbl0(size);
      }
      return -1;
    }
    @Override public int lastIndexOf(double val){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public int lastIndexOf(float val){
      int size;
      if((size=this.size)!=0){
        if(val==val){ return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
        return uncheckedLastIndexOfDblNaN(size);
      }
      return -1;
    }
    @Override public int lastIndexOf(int val){
      int size;
      if((size=this.size)!=0){
        if(val!=0){ return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
        return uncheckedLastIndexOfDbl0(size);
      }
      return -1;
    }
    @Override public int lastIndexOf(long val){
      int size;
      if((size=this.size)!=0){
        if(val!=0){
          if(!TypeUtil.checkCastToDouble(val)){ return -1; }
          return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedLastIndexOfDbl0(size);
      }
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      final int size;
      if((size=this.size)!=0&&val instanceof Double){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(double)val);
      }
      return -1;
    }
    @Override public OmniListIterator.OfDouble listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfDouble listIterator(int index){
      return new BidirectionalItr(this,index+rootOffset);
    }
    @Override public Double remove(int index){
      return super.removeDoubleAt(index);
    }
    @Override public boolean remove(Object val){
      final int size;
      return (size=this.size)!=0&&val instanceof Double&&uncheckedRemove(size,(double)val);
    }
    @Override public boolean removeVal(boolean val){
      final int size;
      if((size=this.size)!=0){
        if(val){ return uncheckedRemoveDblBits(size,TypeUtil.DBL_TRUE_BITS); }
        return uncheckedRemoveDbl0(size);
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      final int size;
      return (size=this.size)!=0&&uncheckedRemove(size,val);
    }
    @Override public boolean removeVal(char val){
      final int size;
      return (size=this.size)!=0&&uncheckedRemove(size,val);
    }
    @Override public boolean removeVal(double val){
      final int size;
      return (size=this.size)!=0&&uncheckedRemove(size,val);
    }
    @Override public boolean removeVal(float val){
      final int size;
      if((size=this.size)!=0){
        if(val==val){ return uncheckedRemoveDblBits(size,Double.doubleToRawLongBits(val)); }
        return uncheckedRemoveDblNaN(size);
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      final int size;
      return (size=this.size)!=0&&uncheckedRemove(size,val);
    }
    @Override public boolean removeVal(long val){
      final int size;
      if((size=this.size)!=0){
        if(val!=0){ return TypeUtil.checkCastToDouble(val)
            &&uncheckedRemoveDblBits(size,Double.doubleToRawLongBits(val)); }
        return uncheckedRemoveDblNaN(size);
      }
      return false;
    }
    @Override public boolean removeVal(short val){
      final int size;
      return (size=this.size)!=0&&uncheckedRemove(size,val);
    }
    @Override public void replaceAll(DoubleUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
      }
    }
    @Override public void replaceAll(UnaryOperator<Double> operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
      }
    }
    @Override public void reverseSort(){
      int size;
      if((size=this.size)>1){
        final int rootOffset;
        uncheckedReverseSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
      }
    }
    @Override public Double set(int index,Double val){
      return super.set(index,val);
    }
    @Override public void sort(){
      int size;
      if((size=this.size)>1){
        final int rootOffset;
        uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
      }
    }
    @Override public void sort(Comparator<? super Double> sorter){
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
      }
    }
    @Override public void sort(DoubleComparator sorter){
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
      }
    }
    @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
      return new SubList(root,this,rootOffset+fromIndex,toIndex-fromIndex);
    }
    @Override public Double[] toArray(){
      final int size;
      if((size=this.size)!=0){
        final Double[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
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
    @Override public double[] toDoubleArray(){
      final int size;
      if((size=this.size)!=0){
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public String toString(){
      final int size;
      if((size=this.size)!=0){
        final StringBuilder builder;
        final int rootOffset;
        forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
        return builder.append(']').toString();
      }
      return "[]";
    }
    private boolean uncheckedContainsDbl0(int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedContainsDbl0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private boolean uncheckedContainsDblBits(int size,long dblBits){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedContainsDblBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,dblBits);
    }
    private boolean uncheckedContainsDblNaN(int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedContainsDblNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedIndexOfDbl0(int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedIndexOfDbl0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedIndexOfDblBits(int size,long dblBits){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedIndexOfDblBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,dblBits);
    }
    private int uncheckedIndexOfDblNaN(int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedIndexOfDblNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedLastIndexOfDbl0(int size){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedLastIndexOfDbl0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedLastIndexOfDblBits(int size,long dblBits){
      final int rootOffset;
      return AbstractDoubleArrSeq.uncheckedLastIndexOfDblBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          dblBits);
    }
    private int uncheckedLastIndexOfDblNaN(int size){
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
    private boolean uncheckedRemoveDblBits(int size,long dblBits){
      int offset;
      final int bound=(offset=rootOffset)+--size;
      final Unchecked root;
      final var arr=(root=this.root).arr;
      do{
        if(Double.doubleToRawLongBits(arr[offset])==dblBits){
          eraseIndexHelper(arr,offset,--root.size);
          bubbleUpDecrementSize(parent);
          this.size=size;
          return true;
        }
      }while(++offset!=bound);
      return false;
    }
    private boolean uncheckedRemoveDblNaN(int size){
      int offset;
      final int bound=(offset=rootOffset)+--size;
      final Unchecked root;
      final var arr=(root=this.root).arr;
      do{
        if(Double.isNaN(arr[offset])){
          eraseIndexHelper(arr,offset,--root.size);
          bubbleUpDecrementSize(parent);
          this.size=size;
          return true;
        }
      }while(++offset!=bound);
      return false;
    }
    private static class AscendingItr extends AbstractAscendingItr implements OmniIterator.OfDouble{
      private AscendingItr(SubList parent){
        super(parent);
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final int cursor,bound;
        final AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
          uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final int cursor,bound;
        final AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
          uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
          this.cursor=bound;
        }
      }
      @Override public boolean hasNext(){
        return cursor!=parent.getBound();
      }
      @Override public Double next(){
        return super.nextDouble();
      }
      @Override public void remove(){
        final AbstractSubList parent;
        final Unchecked root;
        eraseIndexHelper((root=(parent=this.parent).root).arr,--cursor,--root.size);
        bubbleUpDecrementSize(parent.parent);
        --parent.size;
      }
    }
    private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfDouble{
      private BidirectionalItr(SubList parent){
        super(parent);
      }
      private BidirectionalItr(SubList parent,int cursor){
        super(parent,cursor);
      }
      @Override public void remove(){
        final AbstractSubList parent;
        final Unchecked root;
        final int lastRet;
        eraseIndexHelper((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        bubbleUpDecrementSize(parent.parent);
        --parent.size;
        cursor=lastRet;
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final int cursor,bound;
        final AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
          uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
          this.cursor=bound;
          lastRet=bound-1;
        }
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final int cursor,bound;
        final AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
          uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action);
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
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return parent.root.arr[lastRet];
      }
      @Override public int nextIndex(){
        return cursor-parent.rootOffset;
      }
      @Override public double previousDouble(){
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return parent.root.arr[lastRet];
      }
      @Override public int previousIndex(){
        return cursor-parent.rootOffset-1;
      }
      @Override public void set(double val){
        parent.root.arr[lastRet]=val;
      }
    }
  }
}
