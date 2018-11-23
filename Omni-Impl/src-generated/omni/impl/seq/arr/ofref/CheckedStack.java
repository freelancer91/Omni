package omni.impl.seq.arr.ofref;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.impl.seq.arr.ArrSeqUtil;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import omni.impl.CheckedCollection;
public class CheckedStack<E> extends AbstractSeq.Checked<E> implements OmniStack.OfRef<E>
{
  CheckedStack()
  {
    super();
  }
  CheckedStack(int capacity)
  {
    super(capacity);
  }
  private CheckedStack(int size,Object[] arr)
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
    return new CheckedStack<E>(size,arr);
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
      ++modCount;
      return (E)(super.uncheckedPop(size-1));
    }
    return null;
  }
  @Override
  public E pop()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return super.uncheckedPop(size-1);
    }
    throw new NoSuchElementException();
  }
  @Override
  void uncheckedCopyInto(Object[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,Consumer<? super E> action)
  {
    final int modCount=this.modCount;
    try
    {
      ArrSeqUtil.uncheckedReverseForEach(arr,0,size,action);
    }
    finally
    {
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override
  int uncheckedHashCode(int size)
  {
    final int modCount=this.modCount;
    try
    {
      return ArrSeqUtil.reverseHashCode(arr,0,size);
    }
    finally
    {
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override
  void uncheckedToString(int size,StringBuilder builder)
  {
    final int modCount=this.modCount;
    try
    {
      ArrSeqUtil.reverseToString(arr,0,size,builder);
    }
    finally
    {
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override
  public void push(E val)
  {
    ++modCount;
    super.push((E)val);
  }
  @Override
  public int search(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        final int modCount=this.modCount;
        try
        {
          return ArrSeqUtil.uncheckedsearch(arr,size,val::equals);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
      return ArrSeqUtil.uncheckedsearch(arr,size,Objects::isNull);
    }
    return -1;
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
     ++modCount;
     ArrSeqUtil.eraseIndexHelper(arr,index,size);
     this.size=size;
     return true;
   }
  @Override
  boolean uncheckedremoveValNonNull(int size,Object nonNull){
    final var arr=this.arr;
    final int modCount=this.modCount;
    int index;
    try
    {
      for(index=--size;!nonNull.equals(arr[index]);--index){
        if(index==0){ return false; }
      }
    }
    finally
    {
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
    this.modCount=modCount+1;
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
}
