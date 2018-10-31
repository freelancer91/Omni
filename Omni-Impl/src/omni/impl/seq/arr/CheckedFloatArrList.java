package omni.impl.seq.arr;
import java.util.Comparator;
import java.util.NoSuchElementException;
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
public class CheckedFloatArrList extends AbstractFloatArrSeq.Checked implements OmniList.OfFloat{
  CheckedFloatArrList(){
    super();
  }
  CheckedFloatArrList(int capacity){
    super(capacity);
  }
  private CheckedFloatArrList(int size,float[] arr){
    super(size,arr);
  }
  @Override public void add(int index,Float val){
    super.add(index,val);
  }
  @Override public Object clone(){
    final float[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new float[size],0,size);
    }else{
      arr=null;
    }
    return new CheckedFloatArrList(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Float get(int index){
    return super.getFloat(index);
  }
  @Override public OmniIterator.OfFloat iterator(){
    return new BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfFloat listIterator(){
    return new BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfFloat listIterator(int index){
    CheckedCollection.checkLo(index);
    CheckedCollection.checkWriteHi(index,size);
    return new BidirectionalItr(this,index);
  }
  @Override public Float remove(int index){
    return super.removeFloatAt(index);
  }
  @Override public void reverseSort(){
    final int size;
    if((size=this.size)>1){
      uncheckedReverseSort(arr,0,size-1);
      ++modCount;
    }
  }
  @Override public Float set(int index,Float val){
    return super.set(index,val);
  }
  @Override public void sort(){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(arr,0,size-1);
      ++modCount;
    }
  }
  @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
    CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
    return new SubList(this,fromIndex,toIndex-fromIndex);
  }
  @Override void uncheckedCopyInto(double[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(float[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Float[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,FloatConsumer action){
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
  @Override boolean uncheckedRemoveFirstFlt0(int size){
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
  @Override boolean uncheckedRemoveFirstFltBits(int size,int fltBits){
    final var arr=this.arr;
    int index=0;
    do{
      if(Float.floatToRawIntBits(arr[index])==fltBits){
        ++modCount;
        eraseIndexHelper(arr,index,--size);
        this.size=size;
        return true;
      }
    }while(++index!=size);
    return false;
  }
  @Override boolean uncheckedRemoveFirstFltNaN(int size){
    final var arr=this.arr;
    int index=0;
    do{
      if(Float.isNaN(arr[index])){
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
  private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfFloat{
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
    @Override public float previousFloat(){
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
    @Override public void set(float val){
      final int lastRet;
      if((lastRet=this.lastRet)!=-1){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.arr[lastRet]=val;
        return;
      }
      throw new IllegalStateException();
    }
    @Override public void forEachRemaining(Consumer<? super Float> action){
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
    @Override public void forEachRemaining(FloatConsumer action){
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
    @Override public float nextFloat(){
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
  private static class SubList extends AbstractSubList implements OmniList.OfFloat{
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
      return new CheckedFloatArrList(size,arr);
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
      return new BidirectionalItr(this,modCount);
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
      return new BidirectionalItr(this,modCount);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size);
      return new BidirectionalItr(this,modCount,index+rootOffset);
    }
    @Override public void put(int index,float val){
      final Checked root;
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
    private boolean uncheckedRemoveFlt0(int size){
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
    private boolean uncheckedRemoveFltBits(int size,int fltBits){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int offset;
      final int bound=(offset=rootOffset)+--size;
      final var arr=root.arr;
      do{
        if(Float.floatToRawIntBits(arr[offset])==fltBits){
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
    private boolean uncheckedRemoveFltNaN(int size){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int offset;
      final int bound=(offset=rootOffset)+--size;
      final var arr=root.arr;
      do{
        if(Float.isNaN(arr[offset])){
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
    @Override public Float set(int index,Float val){
      return super.set(index,val);
    }
    @Override public void sort(){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        AbstractFloatArrSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
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
      final Checked root;
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
      return new SubList(root,this,rootOffset+fromIndex,toIndex-fromIndex,modCount);
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
    private Checked checkModCountAndGetRoot(){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      return root;
    }
    private boolean uncheckedContains(Checked root,int size,float val){
      if(val==val){ return uncheckedContainsFltBits(root,size,Float.floatToRawIntBits(val)); }
      return uncheckedContainsFltNaN(root,size);
    }
    private boolean uncheckedContainsFlt0(Checked root,int size){
      final int rootOffset;
      return AbstractFloatArrSeq.uncheckedContainsFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private boolean uncheckedContainsFltBits(Checked root,int size,int fltBits){
      final int rootOffset;
      return AbstractFloatArrSeq.uncheckedContainsFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,fltBits);
    }
    private boolean uncheckedContainsFltNaN(Checked root,int size){
      final int rootOffset;
      return AbstractFloatArrSeq.uncheckedContainsFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private boolean uncheckedContainsRawInt(Checked root,int size,int val){
      if(val!=0){ return uncheckedContainsFltBits(root,size,Float.floatToRawIntBits(val)); }
      return uncheckedContainsFlt0(root,size);
    }
    private void uncheckedForEach(int size,FloatConsumer action){
      final int modCount=this.modCount;
      final var root=this.root;
      try{
        int rootOffset;
        uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private int uncheckedIndexOf(Checked root,int size,float val){
      if(val==val){ return uncheckedIndexOfFltBits(root,size,Float.floatToRawIntBits(val)); }
      return uncheckedIndexOfFltNaN(root,size);
    }
    private int uncheckedIndexOfFlt0(Checked root,int size){
      final int rootOffset;
      return AbstractFloatArrSeq.uncheckedIndexOfFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedIndexOfFltBits(Checked root,int size,int fltBits){
      final int rootOffset;
      return AbstractFloatArrSeq.uncheckedIndexOfFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,fltBits);
    }
    private int uncheckedIndexOfFltNaN(Checked root,int size){
      final int rootOffset;
      return AbstractFloatArrSeq.uncheckedIndexOfFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedIndexOfRawInt(Checked root,int size,int val){
      if(val!=0){ return uncheckedIndexOfFltBits(root,size,Float.floatToRawIntBits(val)); }
      return uncheckedIndexOfFlt0(root,size);
    }
    private int uncheckedLastIndexOf(Checked root,int size,float val){
      if(val==val){ return uncheckedLastIndexOfFltBits(root,size,Float.floatToRawIntBits(val)); }
      return uncheckedLastIndexOfFltNaN(root,size);
    }
    private int uncheckedLastIndexOfFlt0(Checked root,int size){
      final int rootOffset;
      return AbstractFloatArrSeq.uncheckedLastIndexOfFlt0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedLastIndexOfFltBits(Checked root,int size,int fltBits){
      final int rootOffset;
      return AbstractFloatArrSeq.uncheckedLastIndexOfFltBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          fltBits);
    }
    private int uncheckedLastIndexOfFltNaN(Checked root,int size){
      final int rootOffset;
      return AbstractFloatArrSeq.uncheckedLastIndexOfFltNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    private int uncheckedLastIndexOfRawInt(Checked root,int size,int val){
      if(val!=0){ return uncheckedLastIndexOfFltBits(root,size,Float.floatToRawIntBits(val)); }
      return uncheckedLastIndexOfFlt0(root,size);
    }
    private boolean uncheckedRemove(int size,float val){
      if(val==val){ return uncheckedRemoveFltBits(size,Float.floatToRawIntBits(val)); }
      return uncheckedRemoveFltNaN(size);
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
        AbstractFloatArrSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
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
        AbstractFloatArrSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      bubbleUpIncrementModCount(parent);
    }
    private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfFloat{
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
      @Override public void forEachRemaining(Consumer<? super Float> action){
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
      @Override public void forEachRemaining(FloatConsumer action){
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
      @Override public float nextFloat(){
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
      @Override public float previousFloat(){
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
      @Override public void set(float val){
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
