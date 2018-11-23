package omni.impl.seq.arr.ofref;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.impl.seq.arr.ArrSeqUtil;
import java.util.function.Predicate;
import java.util.function.Consumer;
public class UncheckedStack<E> extends AbstractSeq.Unchecked<E> implements OmniStack.OfRef<E>
{
  UncheckedStack()
  {
    super();
  }
  UncheckedStack(int capacity)
  {
    super(capacity);
  }
  private UncheckedStack(int size,Object[] arr)
  {
    super(size,arr);
  }
  @Override
  public Object clone()
  {
    final Object[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr=new Object[size],0,size);
    }
    else
    {
      arr=null;
    }
    return new UncheckedStack<E>(size,arr);
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO
    return false;
  }
  @Override
  public OmniIterator.OfRef<E> iterator()
  {
    //TODO
    return null;
  }
  @Override
  public E poll()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (E)(super.uncheckedPop(size-1));
    }
    return null;
  }
  @Override
  public E pop()
  {
    return super.uncheckedPop(size-1);
  }
  @Override
  void uncheckedCopyInto(Object[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,Consumer<? super E> action)
  {
    ArrSeqUtil.uncheckedReverseForEach(arr,0,size,action);
  }
  @Override
  int uncheckedHashCode(int size)
  {
    return ArrSeqUtil.reverseHashCode(arr,0,size);
  }
  @Override
  void uncheckedToString(int size,StringBuilder builder)
  {
    ArrSeqUtil.reverseToString(arr,0,size,builder);
  }
  @Override
  boolean uncheckedremoveVal(int size,Predicate<Object> pred)
  {
    final var arr=this.arr;
    int index;
    for(index=--size;!pred.test(arr[index]);--index)
    {
      if(index==0)
      {
        return false;
      }
    }
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override
  boolean uncheckedremoveValNonNull(int size,Object nonNull)
  {
    final var arr=this.arr;
    int index;
    for(index=--size;!nonNull.equals(arr[index]);--index)
    {
      if(index==0)
      {
        return false;
      }
    }
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
}
