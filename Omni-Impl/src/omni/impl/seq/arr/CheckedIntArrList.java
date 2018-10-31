package omni.impl.seq.arr;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class CheckedIntArrList extends AbstractIntArrSeq.Checked implements OmniList.OfInt{
  CheckedIntArrList(){
    super();
  }
  CheckedIntArrList(int capacity){
    super(capacity);
  }
  private CheckedIntArrList(int size,int[] arr){
    super(size,arr);
  }
  @Override public void add(int index,Integer val){
    super.add(index,val);
  }
  @Override public Object clone(){
    final int[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new int[size],0,size);
    }else{
      arr=null;
    }
    return new CheckedIntArrList(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Integer get(int index){
    return super.getInt(index);
  }
  @Override public OmniIterator.OfInt iterator(){
    return new BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfInt listIterator(){
    return new BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfInt listIterator(int index){
    CheckedCollection.checkLo(index);
    CheckedCollection.checkWriteHi(index,size);
    return new BidirectionalItr(this,index);
  }
  @Override public Integer remove(int index){
    return super.removeIntAt(index);
  }
  @Override public void reverseSort(){
    final int size;
    if((size=this.size)>1){
      uncheckedReverseSort(arr,0,size-1);
      ++modCount;
    }
  }
  @Override public Integer set(int index,Integer val){
    return super.set(index,val);
  }
  @Override public void sort(){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(arr,0,size-1);
      ++modCount;
    }
  }
  @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
    CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
    return new SubList(this,fromIndex,toIndex-fromIndex);
  }
  @Override void uncheckedCopyInto(double[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(float[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(int[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Integer[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(long[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,IntConsumer action){
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
  @Override boolean uncheckedRemoveFirstMatch(int size,int val){
    final var arr=this.arr;
    int index=0;
    do{
      if(arr[index]==val){
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
  private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfInt{
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
    @Override public int previousIndex(){
      return cursor-1;
    }
    @Override public int previousInt(){
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
    @Override public void set(int val){
      final int lastRet;
      if((lastRet=this.lastRet)!=-1){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.arr[lastRet]=val;
        return;
      }
      throw new IllegalStateException();
    }
    @Override public void forEachRemaining(Consumer<? super Integer> action){
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
    @Override public void forEachRemaining(IntConsumer action){
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
    @Override public int nextInt(){
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
  private static class SubList extends AbstractSubList implements OmniList.OfInt{
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
      return super.add(TypeUtil.castToByte(val));
    }
    @Override public void add(int index,Integer val){
      super.add(index,val);
    }
    @Override public boolean add(Integer val){
      return super.add(val);
    }
    @Override public Object clone(){
      final var root=checkModCountAndGetRoot();
      final int[] arr;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new int[size],0,size);
      }else{
        arr=null;
      }
      return new CheckedIntArrList(size,arr);
    }
    @Override public boolean contains(boolean val){
      final var root=checkModCountAndGetRoot();
      final int size,rootOffset;
      return (size=this.size)!=0
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,TypeUtil.castToByte(val));
    }
    @Override public boolean contains(byte val){
      final var root=checkModCountAndGetRoot();
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(char val){
      final var root=checkModCountAndGetRoot();
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(double val){
      final var root=checkModCountAndGetRoot();
      final int size,rootOffset,v;
      return (size=this.size)!=0&&val==(v=(int)val)
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    @Override public boolean contains(float val){
      final var root=checkModCountAndGetRoot();
      final int size,rootOffset,v;
      return (size=this.size)!=0&&(double)val==(double)(v=(int)val)
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    @Override public boolean contains(int val){
      final var root=checkModCountAndGetRoot();
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(long val){
      final var root=checkModCountAndGetRoot();
      final int size,rootOffset,v;
      return (size=this.size)!=0&&val==(v=(int)val)
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    @Override public boolean contains(Object val){
      final var root=checkModCountAndGetRoot();
      final int size,rootOffset;
      return (size=this.size)!=0&&val instanceof Integer
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,(int)val);
    }
    @Override public boolean equals(Object val){
      // TODO Auto-generated method stub
      return false;
    }
    @Override public void forEach(Consumer<? super Integer> action){
      final int size;
      if((size=this.size)!=0){
        uncheckedForEach(size,action::accept);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void forEach(IntConsumer action){
      final int size;
      if((size=this.size)!=0){
        uncheckedForEach(size,action);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public Integer get(int index){
      return super.getInt(index);
    }
    @Override public int indexOf(boolean val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override public int indexOf(double val){
      final var root=checkModCountAndGetRoot();
      final int size,v;
      if((size=this.size)!=0&&val==(v=(int)val)){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int indexOf(float val){
      final var root=checkModCountAndGetRoot();
      final int size,v;
      if((size=this.size)!=0&&(double)val==(double)(v=(int)val)){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int indexOf(int val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public int indexOf(long val){
      final var root=checkModCountAndGetRoot();
      final int size,v;
      if((size=this.size)!=0&&val==(v=(int)val)){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int indexOf(Object val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0&&val instanceof Integer){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(int)val);
      }
      return -1;
    }
    @Override public OmniIterator.OfInt iterator(){
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      return new BidirectionalItr(this,modCount);
    }
    @Override public int lastIndexOf(boolean val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override public int lastIndexOf(double val){
      final var root=checkModCountAndGetRoot();
      final int size,v;
      if((size=this.size)!=0&&val==(v=(int)val)){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int lastIndexOf(float val){
      final var root=checkModCountAndGetRoot();
      final int size,v;
      if((size=this.size)!=0&&(double)val==(double)(v=(int)val)){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int lastIndexOf(int val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public int lastIndexOf(long val){
      final var root=checkModCountAndGetRoot();
      final int size,v;
      if((size=this.size)!=0&&val==(v=(int)val)){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0&&val instanceof Integer){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(int)val);
      }
      return -1;
    }
    @Override public OmniListIterator.OfInt listIterator(){
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      return new BidirectionalItr(this,modCount);
    }
    @Override public OmniListIterator.OfInt listIterator(int index){
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size);
      return new BidirectionalItr(this,modCount,index+rootOffset);
    }
    @Override public void put(int index,int val){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      root.arr[index+rootOffset]=val;
    }
    @Override public Integer remove(int index){
      return super.removeIntAt(index);
    }
    private boolean uncheckedRemoveFirstMatch(int size,int val){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int offset;
      final int bound=(offset=rootOffset)+--size;
      final var arr=root.arr;
      do{
        if(arr[offset]==val){
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
    @Override public boolean remove(Object val){
      final int size;
      if((size=this.size)!=0&&val instanceof Integer){ return uncheckedRemoveFirstMatch(size,(int)val); }
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
      if((size=this.size)!=0){ return uncheckedRemoveFirstMatch(size,val); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(double val){
      final int size,v;
      if((size=this.size)!=0&&val==(v=(int)val)){ return uncheckedRemoveFirstMatch(size,v); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(float val){
      final int size,v;
      if((size=this.size)!=0&&(double)val==(double)(v=(int)val)){ return uncheckedRemoveFirstMatch(size,v); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      final int size;
      if((size=this.size)!=0){ return uncheckedRemoveFirstMatch(size,val); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      final int size,v;
      if((size=this.size)!=0&&val==(v=(int)val)){ return uncheckedRemoveFirstMatch(size,v); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public void replaceAll(IntUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        uncheckedReplaceAll(size,operator);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Integer> operator){
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
    @Override public Integer set(int index,Integer val){
      return super.set(index,val);
    }
    @Override public void sort(){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        AbstractIntArrSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementModCount(parent);
      }
    }
    @Override public void sort(Comparator<? super Integer> sorter){
      final int size;
      if((size=this.size)>1){
        uncheckedSort(size,sorter::compare);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(IntBinaryOperator sorter){
      final int size;
      if((size=this.size)>1){
        uncheckedSort(size,sorter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
      final Checked root;
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
      return new SubList(root,this,rootOffset+fromIndex,toIndex-fromIndex,modCount);
    }
    @Override public Integer[] toArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final Integer[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Integer[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_BOXED_ARR;
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
    private Checked checkModCountAndGetRoot(){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      return root;
    }
    private void uncheckedForEach(int size,IntConsumer action){
      final int modCount=this.modCount;
      final var root=this.root;
      try{
        int rootOffset;
        uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private void uncheckedReplaceAll(int size,IntUnaryOperator operator){
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        AbstractIntArrSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      bubbleUpIncrementModCount(parent);
    }
    private void uncheckedSort(int size,IntBinaryOperator sorter){
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        AbstractIntArrSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      bubbleUpIncrementModCount(parent);
    }
    private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfInt{
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
      private BidirectionalItr(SubList parent,int modCount){
        super(parent,modCount);
      }
      private BidirectionalItr(SubList parent,int modCount,int cursor){
        super(parent,modCount,cursor);
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
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
      @Override public void forEachRemaining(IntConsumer action){
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
      @Override public int nextIndex(){
        return cursor-parent.rootOffset;
      }
      @Override public int nextInt(){
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
      @Override public int previousIndex(){
        return cursor-parent.rootOffset-1;
      }
      @Override public int previousInt(){
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
      @Override public void set(int val){
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