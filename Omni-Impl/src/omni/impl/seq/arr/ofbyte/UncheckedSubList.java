package omni.impl.seq.arr.ofbyte;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.ByteComparator;
import omni.function.ByteConsumer;
import omni.function.ByteUnaryOperator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
class UncheckedSubList extends AbstractSeq.Unchecked.AbstractSubList implements OmniList.OfByte{
  UncheckedSubList(AbstractSeq.Unchecked root,int rootOffset,int size){
    super(root,rootOffset,size);
  }
  private UncheckedSubList(AbstractSeq.Unchecked root,UncheckedSubList parent,int rootOffset,int size){
    super(root,parent,rootOffset,size);
  }
  @Override public boolean add(boolean val){
    return super.add(TypeUtil.castToByte(val));
  }
  @Override public boolean add(Byte val){
    return super.add(val);
  }
  @Override public void add(int index,Byte val){
    super.add(index,val);
  }
  @Override public Object clone(){
    final byte[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new byte[size],0,size);
    }else{
      arr=null;
    }
    return new UncheckedList(size,arr);
  }
  @Override public boolean contains(boolean val){
    final int size,rootOffset;
    return (size=this.size)!=0
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,TypeUtil.castToByte(val));
  }
  @Override public boolean contains(byte val){
    final int size,rootOffset;
    return (size=this.size)!=0
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override public boolean contains(char val){
    final int size,rootOffset;
    return val<=Byte.MAX_VALUE&&(size=this.size)!=0
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override public boolean contains(double val){
    final int size,rootOffset,v;
    return (size=this.size)!=0&&val==(v=(byte)val)
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
  }
  @Override public boolean contains(float val){
    final int size,rootOffset,v;
    return (size=this.size)!=0&&val==(v=(byte)val)
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
  }
  @Override public boolean contains(int val){
    final int size,rootOffset;
    return (size=this.size)!=0&&val==(byte)val
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override public boolean contains(long val){
    final int size,rootOffset,v;
    return (size=this.size)!=0&&val==(v=(byte)val)
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
  }
  @Override public boolean contains(Object val){
    final int size,rootOffset;
    return (size=this.size)!=0&&val instanceof Byte
        &&AbstractSeq.uncheckedAnyMatches(root.arr,rootOffset=this.rootOffset,rootOffset+size,(byte)val);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public void forEach(ByteConsumer action){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
    }
  }
  @Override public void forEach(Consumer<? super Byte> action){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      AbstractSeq.uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
    }
  }
  @Override public Byte get(int index){
    return super.getByte(index);
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
          TypeUtil.castToByte(val));
    }
    return -1;
  }
  @Override public int indexOf(byte val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int indexOf(char val){
    final int size;
    if(val<=Byte.MAX_VALUE&&(size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int indexOf(double val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(byte)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int indexOf(float val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(byte)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int indexOf(int val){
    final int size;
    if((size=this.size)!=0&&val==(byte)val){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int indexOf(long val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(byte)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int indexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Byte){
      final int rootOffset;
      return AbstractSeq.uncheckedIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,(byte)val);
    }
    return -1;
  }
  @Override public OmniIterator.OfByte iterator(){
    return new UncheckedAscendingSubItr(this);
  }
  @Override public int lastIndexOf(boolean val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,
          TypeUtil.castToByte(val));
    }
    return -1;
  }
  @Override public int lastIndexOf(byte val){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int lastIndexOf(char val){
    final int size;
    if(val<=Byte.MAX_VALUE&&(size=this.size)!=0){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int lastIndexOf(double val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(byte)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int lastIndexOf(float val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(byte)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int lastIndexOf(int val){
    final int size;
    if((size=this.size)!=0&&val==(byte)val){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    return -1;
  }
  @Override public int lastIndexOf(long val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(byte)val)){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
    }
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Byte){
      final int rootOffset;
      return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,(byte)val);
    }
    return -1;
  }
  @Override public OmniListIterator.OfByte listIterator(){
    return new UncheckedBidirectionalSubItr(this);
  }
  @Override public OmniListIterator.OfByte listIterator(int index){
    return new UncheckedBidirectionalSubItr(this,index+rootOffset);
  }
  @Override public Byte remove(int index){
    return super.removeByteAt(index);
  }
  @Override public boolean remove(Object val){
    final int size;
    return (size=this.size)!=0&&val instanceof Byte&&uncheckedRemoveFirstMatch(size,(byte)val);
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
    return val<=Byte.MAX_VALUE&&(size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
  }
  @Override public boolean removeVal(double val){
    final int size,v;
    return (size=this.size)!=0&&val==(v=(byte)val)&&uncheckedRemoveFirstMatch(size,v);
  }
  @Override public boolean removeVal(float val){
    final int size,v;
    return (size=this.size)!=0&&val==(v=(byte)val)&&uncheckedRemoveFirstMatch(size,v);
  }
  @Override public boolean removeVal(int val){
    final int size;
    return (size=this.size)!=0&&val==(byte)val&&uncheckedRemoveFirstMatch(size,val);
  }
  @Override public boolean removeVal(long val){
    final int size,v;
    return (size=this.size)!=0&&val==(v=(byte)val)&&uncheckedRemoveFirstMatch(size,v);
  }
  @Override public void replaceAll(ByteUnaryOperator operator){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      AbstractSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Byte> operator){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      AbstractSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
    }
  }
  @Override public void reverseSort(){
    int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedReverseSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
    }
  }
  @Override public Byte set(int index,Byte val){
    return super.set(index,val);
  }
  @Override public void sort(){
    int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
    }
  }
  @Override public void sort(ByteComparator sorter){
    final int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
    }
  }
  @Override public void sort(Comparator<? super Byte> sorter){
    final int size;
    if((size=this.size)>1){
      final int rootOffset;
      AbstractSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
    }
  }
  @Override public OmniList.OfByte subList(int fromIndex,int toIndex){
    return new UncheckedSubList(root,this,rootOffset+fromIndex,toIndex-fromIndex);
  }
  @Override public Byte[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Byte[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Byte[size],0,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
  @Override public byte[] toByteArray(){
    final int size;
    if((size=this.size)!=0){
      final byte[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new byte[size],0,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
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
      AbstractSeq.forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  private boolean uncheckedRemoveFirstMatch(int size,int val){
    int offset;
    final int bound=(offset=rootOffset)+--size;
    final AbstractSeq.Unchecked root;
    final var arr=(root=this.root).arr;
    do{
      if(arr[offset]==val){
        AbstractSeq.eraseIndexHelper(arr,offset,--root.size);
        bubbleUpDecrementSize(parent);
        this.size=size;
        return true;
      }
    }while(++offset!=bound);
    return false;
  }
}