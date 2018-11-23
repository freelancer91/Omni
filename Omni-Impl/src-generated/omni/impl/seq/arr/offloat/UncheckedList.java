package omni.impl.seq.arr.offloat;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.function.FloatConsumer;
import omni.function.FloatUnaryOperator;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import omni.function.FloatComparator;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniList.OfFloat
{
  UncheckedList()
  {
    super();
  }
  UncheckedList(int capacity)
  {
    super(capacity);
  }
  UncheckedList(int size,float[] arr)
  {
    super(size,arr);
  }
  @Override
  public Object clone()
  {
    final float[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr=new float[size],0,size);
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
  public OmniIterator.OfFloat iterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfFloat listIterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfFloat listIterator(int index)
  {
    //TODO
    return null;
  }
  @Override
  public OmniList.OfFloat subList(int fromIndex,int toIndex)
  {
    //TODO
    return null;
  }
  @Override
  public void sort(FloatComparator sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      SortUtil.uncheckedsort(arr,0,size-1,sorter);
    }
  }
  @Override
  public void replaceAll(FloatUnaryOperator operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      ArrSeqUtil.uncheckedReplaceAll(arr,0,size,operator);
    }
  }
  @Override
  public void put(int index,float val)
  {
    arr[index]=val;
  }
  @Override
  void uncheckedCopyInto(Float[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,FloatConsumer action)
  {
    ArrSeqUtil.uncheckedForwardForEach(arr,0,size,action);
  }
  @Override
  int uncheckedHashCode(int size)
  {
    return ArrSeqUtil.forwardHashCode(arr,0,size);
  }
  @Override
  void uncheckedToString(int size,StringBuilder builder)
  {
    ArrSeqUtil.forwardToString(arr,0,size,builder);
  }
  @Override
  public void sort()
  {
    final int size;
    if((size=this.size)>1)
    {
      SortUtil.uncheckedsort(arr,0,size-1);
    }
  }
  @Override
  public void reverseSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      SortUtil.uncheckedreverseSort(arr,0,size-1);
    }
  }
  @Override
  public void replaceAll(UnaryOperator<Float> operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      ArrSeqUtil.uncheckedReplaceAll(arr,0,size,operator::apply);
    }
  }
  @Override
  public void sort(Comparator<? super Float> sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      SortUtil.uncheckedsort(arr,0,size-1,sorter::compare);
    }
  }
  @Override
  boolean uncheckedremoveVal0(int size)
  {
    final var arr=this.arr;
    int index;
    for(index=0,--size;0!=(arr[index]);++index)
    {
      if(index==size)
      {
        return false;
      }
    }
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override
  boolean uncheckedremoveValBits(int size,int bits)
  {
    final var arr=this.arr;
    int index;
    for(index=0,--size;bits!=Float.floatToRawIntBits(arr[index]);++index)
    {
      if(index==size)
      {
        return false;
      }
    }
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override
  boolean uncheckedremoveValNaN(int size)
  {
    final var arr=this.arr;
    int index;
    for(index=0,--size;!Float.isNaN(arr[index]);++index)
    {
      if(index==size)
      {
        return false;
      }
    }
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override
  void uncheckedCopyInto(Object[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedCopyInto(double[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedCopyInto(float[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
}
