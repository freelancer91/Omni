package omni.impl.seq.arr;
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
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class UncheckedShortArrList extends AbstractShortArrSeq.Unchecked implements OmniList.OfShort{
  UncheckedShortArrList(){
    super();
  }
  UncheckedShortArrList(int capacity){
    super(capacity);
  }
  private UncheckedShortArrList(int size,short[] arr){
    super(size,arr);
  }
  @Override public void add(int index,Short val){
    super.add(index,val);
  }
  @Override public Object clone(){
    final short[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new short[size],0,size);
    }else{
      arr=null;
    }
    return new UncheckedShortArrList(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Short get(int index){
    return super.getShort(index);
  }
  @Override public OmniIterator.OfShort iterator(){
    return new AscendingItr(this);
  }
  @Override public OmniListIterator.OfShort listIterator(){
    return new BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfShort listIterator(int index){
    return new BidirectionalItr(this,index);
  }
  @Override public Short remove(int index){
    return super.removeShortAt(index);
  }
  @Override public void reverseSort(){
    int size;
    if((size=this.size)>1){
      uncheckedReverseSort(arr,0,size-1);
    }
  }
  @Override public Short set(int index,Short val){
    return super.set(index,val);
  }
  @Override public void sort(){
    int size;
    if((size=this.size)>1){
      uncheckedSort(arr,0,size-1);
    }
  }
  @Override public OmniList.OfShort subList(int fromIndex,int toIndex){
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
  @Override void uncheckedCopyInto(long[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(short[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Short[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,ShortConsumer action){
    uncheckedForwardForEachInRange(arr,0,size,action);
  }
  @Override int uncheckedHashCode(int size){
    return forwardHashCode(arr,0,size);
  }
  @Override boolean uncheckedRemoveFirstMatch(int size,int val){
    final var arr=this.arr;
    int index=0;
    do{
      if(arr[index]==val){
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
  private static class AscendingItr extends AbstractAscendingItr implements OmniIterator.OfShort{
    private AscendingItr(Unchecked root){
      super(root);
    }
    @Override public Short next(){
      return super.nextShort();
    }
    @Override public void forEachRemaining(Consumer<? super Short> action){
      final Unchecked root;
      final int cursor,bound;
      if((cursor=this.cursor)!=(bound=(root=this.root).size)){
        uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
        this.cursor=bound;
      }
    }
    @Override public void forEachRemaining(ShortConsumer action){
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
    @Override public void remove(){
      final Unchecked root;
      eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
    }
  }
  private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfShort{
    private BidirectionalItr(Unchecked root){
      super(root);
    }
    private BidirectionalItr(Unchecked root,int cursor){
      super(root,cursor);
    }
    @Override public void forEachRemaining(Consumer<? super Short> action){
      final int cursor,bound;
      final Unchecked root;
      if((cursor=this.cursor)!=(bound=(root=this.root).size)){
        uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
        this.cursor=bound;
        lastRet=bound-1;
      }
    }
    @Override public void forEachRemaining(ShortConsumer action){
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
    @Override public boolean hasPrevious(){
      return cursor!=0;
    }
    @Override public int nextIndex(){
      return cursor;
    }
    @Override public short nextShort(){
      final int lastRet;
      this.lastRet=lastRet=cursor++;
      return root.arr[lastRet];
    }
    @Override public int previousIndex(){
      return cursor-1;
    }
    @Override public short previousShort(){
      final int lastRet;
      this.lastRet=lastRet=--cursor;
      return root.arr[lastRet];
    }
    @Override public void set(short val){
      root.arr[lastRet]=val;
    }
  }
  private static class SubList extends AbstractSubList implements OmniList.OfShort{
    private SubList(Unchecked root,int rootOffset,int size){
      super(root,rootOffset,size);
    }
    private SubList(Unchecked root,SubList parent,int rootOffset,int size){
      super(root,parent,rootOffset,size);
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
      final short[] arr;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new short[size],0,size);
      }else{
        arr=null;
      }
      return new UncheckedShortArrList(size,arr);
    }
    @Override public boolean contains(boolean val){
      final int size,rootOffset;
      return (size=this.size)!=0
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,TypeUtil.castToByte(val));
    }
    @Override public boolean contains(byte val){
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(char val){
      final int size,rootOffset;
      return val<=Short.MAX_VALUE&&(size=this.size)!=0
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(double val){
      final int size,rootOffset,v;
      return (size=this.size)!=0&&val==(v=(short)val)
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    @Override public boolean contains(float val){
      final int size,rootOffset,v;
      return (size=this.size)!=0&&val==(v=(short)val)
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    @Override public boolean contains(int val){
      final int size,rootOffset;
      return (size=this.size)!=0&&val==(short)val
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(long val){
      final int size,rootOffset,v;
      return (size=this.size)!=0&&val==(v=(short)val)
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    @Override public boolean contains(Object val){
      final int size,rootOffset;
      return (size=this.size)!=0&&val instanceof Short
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,(short)val);
    }
    @Override public boolean contains(short val){
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean equals(Object val){
      // TODO Auto-generated method stub
      return false;
    }
    @Override public void forEach(Consumer<? super Short> action){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
      }
    }
    @Override public void forEach(ShortConsumer action){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
      }
    }
    @Override public Short get(int index){
      return super.getShort(index);
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
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override public int indexOf(char val){
      final int size;
      if(val<=Short.MAX_VALUE&&(size=this.size)!=0){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public int indexOf(double val){
      final int size,v;
      if((size=this.size)!=0&&val==(v=(short)val)){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int indexOf(float val){
      final int size,v;
      if((size=this.size)!=0&&val==(v=(short)val)){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int indexOf(int val){
      final int size;
      if((size=this.size)!=0&&val==(short)val){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public int indexOf(long val){
      final int size,v;
      if((size=this.size)!=0&&val==(v=(short)val)){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int indexOf(Object val){
      final int size;
      if((size=this.size)!=0&&val instanceof Short){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(short)val);
      }
      return -1;
    }
    @Override public int indexOf(short val){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public OmniIterator.OfShort iterator(){
      return new AscendingItr(this);
    }
    @Override public int lastIndexOf(boolean val){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override public int lastIndexOf(char val){
      final int size;
      if(val<=Short.MAX_VALUE&&(size=this.size)!=0){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public int lastIndexOf(double val){
      final int size,v;
      if((size=this.size)!=0&&val==(v=(short)val)){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int lastIndexOf(float val){
      final int size,v;
      if((size=this.size)!=0&&val==(v=(short)val)){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int lastIndexOf(int val){
      final int size;
      if((size=this.size)!=0&&val==(short)val){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public int lastIndexOf(long val){
      final int size,v;
      if((size=this.size)!=0&&val==(v=(short)val)){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      final int size;
      if((size=this.size)!=0&&val instanceof Short){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(short)val);
      }
      return -1;
    }
    @Override public int lastIndexOf(short val){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public OmniListIterator.OfShort listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfShort listIterator(int index){
      return new BidirectionalItr(this,index+rootOffset);
    }
    @Override public Short remove(int index){
      return super.removeShortAt(index);
    }
    private boolean uncheckedRemoveFirstMatch(int size,int val){
      int offset;
      final int bound=(offset=rootOffset)+--size;
      final Unchecked root;
      final var arr=(root=this.root).arr;
      do{
        if(arr[offset]==val){
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
      return (size=this.size)!=0&&val instanceof Short&&uncheckedRemoveFirstMatch(size,(short)val);
    }
    @Override public boolean removeVal(boolean val){
      final int size;
      return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,TypeUtil.castToByte(val));
    }
    @Override public boolean removeVal(byte val){
      final int size;
      return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
    }
    @Override public boolean removeVal(char val){
      final int size;
      return val<=Short.MAX_VALUE&&(size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
    }
    @Override public boolean removeVal(double val){
      final int size,v;
      return (size=this.size)!=0&&val==(v=(short)val)&&uncheckedRemoveFirstMatch(size,v);
    }
    @Override public boolean removeVal(float val){
      final int size,v;
      return (size=this.size)!=0&&val==(v=(short)val)&&uncheckedRemoveFirstMatch(size,v);
    }
    @Override public boolean removeVal(int val){
      final int size;
      return (size=this.size)!=0&&val==(short)val&&uncheckedRemoveFirstMatch(size,val);
    }
    @Override public boolean removeVal(long val){
      final int size,v;
      return (size=this.size)!=0&&val==(v=(short)val)&&uncheckedRemoveFirstMatch(size,v);
    }
    @Override public boolean removeVal(short val){
      final int size;
      return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
    }
    @Override public void replaceAll(ShortUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
      }
    }
    @Override public void replaceAll(UnaryOperator<Short> operator){
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
    @Override public Short set(int index,Short val){
      return super.set(index,val);
    }
    @Override public void sort(){
      int size;
      if((size=this.size)>1){
        final int rootOffset;
        uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
      }
    }
    @Override public void sort(Comparator<? super Short> sorter){
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
      }
    }
    @Override public void sort(ShortComparator sorter){
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
      }
    }
    @Override public OmniList.OfShort subList(int fromIndex,int toIndex){
      return new SubList(root,this,rootOffset+fromIndex,toIndex-fromIndex);
    }
    @Override public Short[] toArray(){
      final int size;
      if((size=this.size)!=0){
        final Short[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Short[size],0,size);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_BOXED_ARR;
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
    @Override public float[] toFloatArray(){
      final int size;
      if((size=this.size)!=0){
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      final int size;
      if((size=this.size)!=0){
        final int[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      final int size;
      if((size=this.size)!=0){
        final long[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      final int size;
      if((size=this.size)!=0){
        final short[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new short[size],0,size);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
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
    private static class AscendingItr extends AbstractAscendingItr implements OmniIterator.OfShort{
      private AscendingItr(SubList parent){
        super(parent);
      }
      @Override public void remove(){
        final AbstractSubList parent;
        final Unchecked root;
        eraseIndexHelper((root=(parent=this.parent).root).arr,--cursor,--root.size);
        bubbleUpDecrementSize(parent.parent);
        --parent.size;
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final int cursor,bound;
        final AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
          uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(ShortConsumer action){
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
      @Override public Short next(){
        return super.nextShort();
      }
    }
    private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfShort{
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
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final int cursor,bound;
        final AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
          uncheckedForwardForEachInRange(parent.root.arr,cursor,bound,action::accept);
          this.cursor=bound;
          lastRet=bound-1;
        }
      }
      @Override public void forEachRemaining(ShortConsumer action){
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
      @Override public int nextIndex(){
        return cursor-parent.rootOffset;
      }
      @Override public short nextShort(){
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return parent.root.arr[lastRet];
      }
      @Override public int previousIndex(){
        return cursor-parent.rootOffset-1;
      }
      @Override public short previousShort(){
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return parent.root.arr[lastRet];
      }
      @Override public void set(short val){
        parent.root.arr[lastRet]=val;
      }
    }
  }
}