package omni.impl.seq.arr.ofbyte;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.function.ByteConsumer;
import omni.function.ByteUnaryOperator;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import omni.function.ByteComparator;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniList.OfByte
{
  UncheckedList()
  {
    super();
  }
  UncheckedList(int capacity)
  {
    super(capacity);
  }
  UncheckedList(int size,byte[] arr)
  {
    super(size,arr);
  }
  @Override
  public Object clone()
  {
    final byte[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr=new byte[size],0,size);
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
  public OmniIterator.OfByte iterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfByte listIterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfByte listIterator(int index)
  {
    //TODO
    return null;
  }
  @Override
  public OmniList.OfByte subList(int fromIndex,int toIndex)
  {
    //TODO
    return null;
  }
  @Override
  public void sort(ByteComparator sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      SortUtil.uncheckedsort(arr,0,size-1,sorter);
    }
  }
  @Override
  public void replaceAll(ByteUnaryOperator operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      ArrSeqUtil.uncheckedReplaceAll(arr,0,size,operator);
    }
  }
  @Override
  public void put(int index,byte val)
  {
    arr[index]=val;
  }
  @Override
  void uncheckedCopyInto(Byte[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,ByteConsumer action)
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
  public void replaceAll(UnaryOperator<Byte> operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      ArrSeqUtil.uncheckedReplaceAll(arr,0,size,operator::apply);
    }
  }
  @Override
  public void sort(Comparator<? super Byte> sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      SortUtil.uncheckedsort(arr,0,size-1,sorter::compare);
    }
  }
  @Override
  void uncheckedCopyInto(long[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  boolean uncheckedremoveVal(int size,int val)
  {
    final var arr=this.arr;
    int index;
    for(index=0,--size;val!=(arr[index]);++index)
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
  void uncheckedCopyInto(int[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedCopyInto(short[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedCopyInto(byte[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
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
