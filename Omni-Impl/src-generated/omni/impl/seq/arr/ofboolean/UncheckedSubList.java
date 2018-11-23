package omni.impl.seq.arr.ofboolean;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.util.OmniArray;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import java.util.function.Consumer;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import java.util.function.IntFunction;
import omni.function.BooleanComparator;
import omni.util.TypeUtil;
class UncheckedSubList extends AbstractSeq.Unchecked.AbstractSubList implements OmniList.OfBoolean
{
  //TODO user macros to clean up the template
  UncheckedSubList(AbstractSeq.Unchecked root,AbstractSeq.Unchecked.AbstractSubList parent,int rootOffset,int size)
  {
    super(root,parent,rootOffset,size);
  }
  UncheckedSubList(AbstractSeq.Unchecked root,int rootOffset,int size)
  {
    super(root,rootOffset,size);
  }
  @Override
  public Object clone()
  {
    final boolean[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new boolean[size],0,size);
    }
    else
    {
      arr=null;
    }
    return new UncheckedList(size,arr);
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO
    return false;
  }
    @Override
  public OmniIterator.OfBoolean iterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfBoolean listIterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfBoolean listIterator(int index)
  {
    //TODO
    return null;
  }
  @Override
  public OmniList.OfBoolean subList(int fromIndex,int toIndex)
  {
    return new UncheckedSubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
  }
  @Override
  public void sort(BooleanComparator sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
    }
  }
  @Override
  public void replaceAll(BooleanPredicate operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
    }
  }
  @Override
  public void put(int index,boolean val)
  {
    root.arr[index+rootOffset]=val;
  }
  @Override
  public boolean set(int index,boolean val)
  {
    final boolean[] arr;
    final var oldVal=(boolean)(arr=root.arr)[index+=rootOffset];
    arr[index]=val;
    return oldVal;
  }
  @Override
  public boolean getBoolean(int index)
  {
    return (boolean)root.arr[index+rootOffset];
  }
  @Override
  public void sort()
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    }
  }
  @Override
  public void reverseSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    }
  }
  @Override
  public boolean removeBooleanAt(int index)
  {
    final AbstractSeq.Unchecked root;
    final boolean[] arr;
    final var removed=(boolean)(arr=(root=this.root).arr)[index+=rootOffset];
    ArrSeqUtil.eraseIndexHelper(arr,index,--root.size);
    bubbleUpDecrementSize(parent);
    --size;
    return removed;
  }
  @Override
  public String toString()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final StringBuilder builder;
      final int rootOffset;
      ArrSeqUtil.forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override
  public int hashCode()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      return ArrSeqUtil.forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    return 1;
  }
  @Override
  public void forEach(BooleanConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
    }
  }
  @Override
  public Boolean[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Boolean[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Boolean[size],0,size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0)
    {
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst,0,size);
    }
    return dst;
  }
  @Override
  public <T> T[] toArray(T[] arr)
  {
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
    }
    else if(arr.length!=0)
    {
      arr[0]=null;
    }
    return arr;
  }
  private boolean uncheckedremoveVal(int size,boolean val)
  {
    final AbstractSeq.Unchecked root;
    final var arr=(root=this.root).arr;
    int index;
    final int bound=(index=this.rootOffset)+(--size);
    while(val^(arr[index]))
    {
      if(++index==bound)
      {
        return false;
      }
    }
    ArrSeqUtil.eraseIndexHelper(arr,index,--root.size);
    bubbleUpDecrementSize(parent);
    this.size=size;
    return true;
  }
  private boolean uncheckedcontains(int size,boolean val)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  private int uncheckedindexOf(int size,boolean val)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedindexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override
  public boolean remove(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Boolean)
      {
        return uncheckedremoveVal(size,(boolean)(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Boolean)
      {
        return uncheckedcontains(size,(boolean)(val));
      }
    }
    return false;
  }
  @Override
  public int indexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Boolean)
      {
        return uncheckedindexOf(size,(boolean)(val));
      }
    }
    return -1;
  }
  @Override
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,(val));
    }
    return false;
  }
  @Override
  public int indexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,(val));
    }
    return -1;
  }
  @Override
  public boolean contains(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      switch(val){
        default:
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedcontains(size,v);
    }
    return false;
  }
  @Override
  public boolean removeVal(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      switch(val){
        default:
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedremoveVal(size,v);
    }
    return false;
  }
  @Override
  public int indexOf(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      switch(val){
        default:
          return -1;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedindexOf(size,v);
    }
    return -1;
  }
  public boolean contains(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      if(val==0){
        v=false;
      }else if(val==1){
        v=true;
      }else{
        return false;
      }
      {
        return uncheckedcontains(size,v);
      }
    }
    return false;
  }
  public boolean removeVal(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      if(val==0){
        v=false;
      }else if(val==1){
        v=true;
      }else{
        return false;
      }
      {
        return uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  public int indexOf(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      if(val==0){
        v=false;
      }else if(val==1){
        v=true;
      }else{
        return -1;
      }
      {
        return uncheckedindexOf(size,v);
      }
    }
    return -1;
  }
  public boolean contains(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return uncheckedcontains(size,v);
      }
    }
    return false;
  }
  public boolean removeVal(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  public int indexOf(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      switch(Float.floatToRawIntBits(val)){
        default:
          return -1;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return uncheckedindexOf(size,v);
      }
    }
    return -1;
  }
  public boolean contains(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
        v=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        v=true;
      }else{
        return false;
      }
      {
        return uncheckedcontains(size,v);
      }
    }
    return false;
  }
  public boolean removeVal(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
        v=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        v=true;
      }else{
        return false;
      }
      {
        return uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  public int indexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
        v=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        v=true;
      }else{
        return -1;
      }
      {
        return uncheckedindexOf(size,v);
      }
    }
    return -1;
  }
  @Override
  protected int uncheckedlastIndexOf(int size,boolean val)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override
  public void forEach(Consumer<? super Boolean> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
    }
  }
  @Override
  public void sort(Comparator<? super Boolean> sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
    }
  }
  @Override
  public void replaceAll(UnaryOperator<Boolean> operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
    }
  }
  @Override
  public double[] toDoubleArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final double[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new double[size],0,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override
  public float[] toFloatArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new float[size],0,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override
  public long[] toLongArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new long[size],0,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override
  public int[] toIntArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new int[size],0,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override
  public short[] toShortArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new short[size],0,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override
  public byte[] toByteArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new byte[size],0,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override
  public boolean[] toBooleanArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new boolean[size],0,size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override
  public char[] toCharArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new char[size],0,size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
}
