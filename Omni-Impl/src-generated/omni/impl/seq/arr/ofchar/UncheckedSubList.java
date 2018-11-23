package omni.impl.seq.arr.ofchar;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.util.OmniArray;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.function.CharConsumer;
import omni.function.CharUnaryOperator;
import java.util.function.Consumer;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import java.util.function.IntFunction;
import omni.function.CharComparator;
import omni.util.TypeUtil;
class UncheckedSubList extends AbstractSeq.Unchecked.AbstractSubList implements OmniList.OfChar
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
    final char[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new char[size],0,size);
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
  public OmniIterator.OfChar iterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfChar listIterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfChar listIterator(int index)
  {
    //TODO
    return null;
  }
  @Override
  public OmniList.OfChar subList(int fromIndex,int toIndex)
  {
    return new UncheckedSubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
  }
  @Override
  public void sort(CharComparator sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
    }
  }
  @Override
  public void replaceAll(CharUnaryOperator operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
    }
  }
  @Override
  public void put(int index,char val)
  {
    root.arr[index+rootOffset]=val;
  }
  @Override
  public char set(int index,char val)
  {
    final char[] arr;
    final var oldVal=(char)(arr=root.arr)[index+=rootOffset];
    arr[index]=val;
    return oldVal;
  }
  @Override
  public char getChar(int index)
  {
    return (char)root.arr[index+rootOffset];
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
  public char removeCharAt(int index)
  {
    final AbstractSeq.Unchecked root;
    final char[] arr;
    final var removed=(char)(arr=(root=this.root).arr)[index+=rootOffset];
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
  public void forEach(CharConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
    }
  }
  @Override
  public Character[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Character[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Character[size],0,size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
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
  private boolean uncheckedremoveVal(int size,int val)
  {
    final AbstractSeq.Unchecked root;
    final var arr=(root=this.root).arr;
    int index;
    final int bound=(index=this.rootOffset)+(--size);
    while(val!=(arr[index]))
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
  private boolean uncheckedcontains(int size,int val)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  private int uncheckedindexOf(int size,int val)
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
      if(val instanceof Character)
      {
        return uncheckedremoveVal(size,(char)(val));
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
      if(val instanceof Character)
      {
        return uncheckedcontains(size,(char)(val));
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
      if(val instanceof Character)
      {
        return uncheckedindexOf(size,(char)(val));
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
      return uncheckedcontains(size,TypeUtil.castToChar(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,TypeUtil.castToChar(val));
    }
    return false;
  }
  @Override
  public int indexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,TypeUtil.castToChar(val));
    }
    return -1;
  }
  public boolean contains(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedcontains(size,(val));
      }
    }
    return false;
  }
  public boolean removeVal(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  public int indexOf(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedindexOf(size,(val));
      }
    }
    return -1;
  }
  @Override
  public boolean contains(short val)
  {
    if(val>=0)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedcontains(size,(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(short val)
  {
    if(val>=0)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  @Override
  public int indexOf(short val)
  {
    if(val>=0)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedindexOf(size,(val));
      }
    }
    return -1;
  }
  @Override
  public boolean contains(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(char)val)
      {
        return uncheckedcontains(size,(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(char)val)
      {
        return uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  @Override
  public int indexOf(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(char)val)
      {
        return uncheckedindexOf(size,(val));
      }
    }
    return -1;
  }
  public boolean contains(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
      {
        return uncheckedindexOf(size,v);
      }
    }
    return -1;
  }
  @Override
  protected int uncheckedlastIndexOf(int size,int val)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
  }
  @Override
  public void forEach(Consumer<? super Character> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
    }
  }
  @Override
  public void sort(Comparator<? super Character> sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
    }
  }
  @Override
  public void replaceAll(UnaryOperator<Character> operator)
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
