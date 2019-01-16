package omni.impl.seq;
import omni.api.OmniDeque;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import java.util.function.IntFunction;
import java.util.NoSuchElementException;
import omni.impl.CheckedCollection;
import omni.util.BitSetUtils;
import java.util.Objects;
import omni.util.OmniPred;
public class RefArrDeque<E> implements OmniDeque.OfRef<E>
{
  transient Object[] arr;
  transient int head;
  transient int tail;
  public RefArrDeque()
  {
    this.tail=-1;
    this.arr=OmniArray.OfRef.DEFAULT_ARR;
  }
  public RefArrDeque(int capacity)
  {
    this.tail=-1;
    switch(capacity)
    {
    default:
      this.arr=new Object[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfRef.DEFAULT_ARR;
    case 0:
    }
  }
  public RefArrDeque(int head,int tail,Object[] arr)
  {
    this.head=head;
    this.tail=tail;
    this.arr=arr;
  }
  @Override
  public Object clone()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var arr=this.arr;
      final Object[] newArr;
      int head,size;
      if((size=tail-(head=this.head))<=0)
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new Object[size+=arr.length],0,head);
        ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new Object[size],0,size);
      }
      return new RefArrDeque<E>(0,size-1,newArr);
    }
    return new RefArrDeque<E>();
  }
  @Override
  public String toString()
  {
    final int tail;
    if((tail=this.tail+1)!=0)
    {
      return toStringHelper(tail);
    }
    return "[]";
  }
  @Override
  public int hashCode()
  {
    final int tail;
    if((tail=this.tail+1)!=0)
    {
      return hashCodeHelper(tail);
    }
    return 1;
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO implements equals method
    return false;
  }
  private String toStringHelper(int tail)
  {
    final Object[] arr;
    int head;
    StringBuilder builder=new StringBuilder("[").append((arr=this.arr)[head=this.head]);
    if(tail<=head)
    {
      for(int bound=arr.length;++head!=bound;builder.append(',').append(' ').append(arr[head])){}
      builder.append(',').append(' ').append(arr[head=0]);
    }
    for(;++head!=tail;builder.append(',').append(' ').append(arr[head])){}
    return builder.append(']').toString();
  }
  private int hashCodeHelper(int tail)
  {
    final Object[] arr;
    int head;
    int hash=31+Objects.hashCode((arr=this.arr)[head=this.head]);
    if(tail<=head)
    {
      for(int bound=arr.length;++head!=bound;hash=(hash*31)+Objects.hashCode(arr[head])){}
      hash=(hash*31)+Objects.hashCode(arr[head=0]);
    }
    for(;++head!=tail;hash=(hash*31)+Objects.hashCode(arr[head])){}
    return hash;
  }
  @SuppressWarnings("unchecked")
  @Override
  public E element()
  {
    return (E)arr[head];
  }
  @SuppressWarnings("unchecked")
  @Override
  public E getLast()
  {
    return (E)arr[tail];
  }
  @Override
  public void addLast(E val)
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      var arr=this.arr;
      final int head;
      switch(Integer.signum((++tail)-(head=this.head)))
      {
        case 0:
          int oldArrSize,newArrSize;
          final Object[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new Object[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
          ArrCopy.uncheckedCopy(arr,head,newArr,newArrSize-=(oldArrSize-=tail),oldArrSize);
          this.head=newArrSize;
          this.arr=arr=newArr;
        case -1:
          break;
        default:
          if(tail==arr.length)
          {
            if(head==0)
            {
              ArrCopy.uncheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(tail)],0,tail);
              this.arr=arr;
            }
            else
            {
              tail=0;
            }
          }
      }
      arr[tail]=val;
      this.tail=tail;
    }
    else
    {
      initialize(val);
    }
  }
  @Override
  public void push(E val)
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      var arr=this.arr;
      int head;
      switch(Integer.signum((++tail)-(head=this.head)))
      {
        case 0:
          int oldArrSize,newArrSize;
          final Object[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new Object[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
          ArrCopy.uncheckedCopy(arr,head,newArr,head=(newArrSize-=(oldArrSize-=tail)),oldArrSize);
          this.arr=arr=newArr;
        case -1:
          --head;
          break;
        default:
          if(--head==-1)
          {
            int arrLength;
            if(tail==(arrLength=arr.length))
            {
              ArrCopy.uncheckedCopy(arr,0,arr=new Object[arrLength=OmniArray.growBy50Pct(tail)],arrLength-=tail,tail);
              this.arr=arr;
            }
            head=arrLength-1;
          }
      }
      arr[head]=val;
      this.head=head;
    }
    else
    {
      initialize(val);
    }
  }
  @Override
  public E pop()
  {
    return uncheckedExtractFirst(tail);
  }
  @Override
  public E removeLast()
  {
    return uncheckedExtractLast(tail);
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    T[] dst;
    int tail;
    if((tail=this.tail+1)!=0)
    {
      var arr=this.arr;
      int head;
      if(tail<=(head=this.head))
      {
        int size;
        ArrCopy.uncheckedCopy(arr,head,dst=arrConstructor.apply(size=tail+arr.length),0,size-=head);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,head,dst=arrConstructor.apply(tail-=head),0,tail);
      }
    }
    else
    {
      dst=arrConstructor.apply(0);
    }
    return dst;
  }
  @Override
  public void clear()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      if(tail<(head=this.head))
      {
        Object[] arr;
        OmniArray.OfRef.nullifyRange(arr=this.arr,0,tail);
        OmniArray.OfRef.nullifyRange(arr,head,arr.length-1);
      }
      else
      {
        OmniArray.OfRef.nullifyRange(arr,head,tail);
      }
      this.tail=-1;
    }
  }
  @SuppressWarnings("unchecked")
  void uncheckedForEach(int tail,Consumer<? super E> action)
  {
    var arr=this.arr;
    int head;
    if(tail<(head=this.head))
    {
      for(int bound=arr.length;;)
      {
        action.accept((E)arr[head]);
        if(++head==bound)
        {
          break;
        }
      }
      head=0;
    }
    for(;;++head)
    {
      action.accept((E)arr[head]);
      if(head==tail)
      {
        return;
      }
    }
  }
  @Override
  public E pollLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      Object[] arr;
      @SuppressWarnings("unchecked")
      final var ret=(E)((arr=this.arr)[tail]);
      switch(Integer.signum(this.head-tail))
      {
      default:
        if(tail==0)
        {
          this.tail=arr.length-1;
          break;
        }
      case -1:
        this.tail=tail-1;
        break;
      case 0:
        this.tail=-1;
      }
      arr[tail]=null;
      return ret;
    }
    return null;
  }
  @Override
  public E poll()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      Object[] arr;
      @SuppressWarnings("unchecked")
      final var ret=(E)((arr=this.arr)[head=this.head]);
      switch(Integer.signum(head-tail))
      {
      default:
        if(head==arr.length-1)
        {
          this.head=0;
          break;
        }
      case -1:
        this.head=head+1;
        break;
      case 0:
        this.tail=-1;
      }
      arr[head]=null;
      return ret;
    }
    return null;
  }
  @Override
  public OmniIterator.OfRef<E> iterator()
  {
    return new AscendingItr<E>(this);
  }
  @Override
  public OmniIterator.OfRef<E> descendingIterator()
  {
    return new DescendingItr<E>(this);
  }
  @Override
  public boolean isEmpty()
  {
    return this.tail==-1;
  }
  @Override
  public int size()
  {
    int tail;
    if((tail=this.tail+1)!=0 && (tail-=head)<1)
    {
      tail+=arr.length;
    }
    return tail;
  }
  @SuppressWarnings("unchecked")
  @Override
  public E peekLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (E)(arr[tail]);
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  @Override
  public E peek()
  {
    if(this.tail!=-1)
    {
      return (E)(arr[head]);
    }
    return null;
  }
  @Override
  public void forEach(Consumer<? super E> action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action);
    }
  }
  @Override
  public boolean add(final E val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerLast(final E val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerFirst(final E val)
  {
    push((val));
    return true;
  }
  @Override
  public boolean offer(final E val)
  {
    addLast((val));
    return true;
  }
  @Override
  public void addFirst(final E val)
  {
    push((E)(val));
  }
  private void initialize(E val)
  {
    Object[] arr;
    if((arr=this.arr)==OmniArray.OfRef.DEFAULT_ARR)
    {
      this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=new Object[1];
    }
    arr[0]=val;
    this.tail=0;
    this.head=0;
  }
  private E uncheckedExtractFirst(int tail)
  {
    Object[] arr;
    int head;
    @SuppressWarnings("unchecked")
    var ret=(E)(arr=this.arr)[head=this.head];
    arr[head]=null;
    switch(Integer.signum(tail-head))
    {
      case -1:
        if(++head==arr.length)
        {
          head=0;
        }
        break;
    case 0:
        tail=-1;
        break;
    default:
        ++head;
    }
    this.head=head;
    return ret;
  }
  private E uncheckedExtractLast(int tail)
  {
    Object[] arr;
    @SuppressWarnings("unchecked")
    var ret=(E)(arr=this.arr)[tail];
    arr[tail]=null;
    switch(Integer.signum(tail-head))
    {
      case -1:
        if(--tail==-1)
        {
          tail=arr.length-1;
        }
        break;
      case 0:
        tail=-1;
        break;
      default:
        --tail;
    }
    this.tail=tail;
    return ret;
  }
  @Override
  public E peekFirst()
  {
    return (E)peek();
  }
  @Override
  public E pollFirst()
  {
    return (E)poll();
  }
  @Override
  public E getFirst()
  {
    return (E)element();
  }
  @Override
  public E removeFirst()
  {
    return (E)pop();
  }
  @Override
  public E remove()
  {
    return (E)pop();
  }
  @Override
  public boolean removeFirstOccurrence(Object val)
  {
    return remove(val);
  }
  @Override
  public Object[] toArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final Object[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new Object[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new Object[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  @Override
  public boolean removeIf(Predicate<? super E> filter)
  {
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter);
  }
  @Override
  public <T> T[] toArray(T[] dst)
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      var arr=this.arr;
      int head;
      if(tail<=(head=this.head))
      {
        int size;
        ArrCopy.uncheckedCopy(arr,head,dst=OmniArray.uncheckedArrResize(size=tail+arr.length,dst),0,size-=head);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,head,dst=OmniArray.uncheckedArrResize(tail-=head,dst),0,tail);
      }
    }
    else if(dst.length!=0)
    {
      dst[0]=null;
    }
    return dst;
  }
  @Override
  public boolean contains(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      {
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final byte val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final char val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final short val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final Boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Byte val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Character val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Short val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Integer val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Double val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public int search(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      {
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int search(final long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final byte val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int search(final char val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int search(final short val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int search(final Boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final Byte val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final Character val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final Short val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final Integer val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final Long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final Float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int search(final Double val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  private static class DescendingItr<E>
    implements OmniIterator.OfRef<E>
  {
    transient final RefArrDeque<E> root;
    transient int nextIndex;
    DescendingItr(RefArrDeque<E> root)
    {
      this.root=root;
      this.nextIndex=root.tail;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final RefArrDeque<E> root;
      return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
    }
    @SuppressWarnings("unchecked")
    @Override
    public E next()
    {
      final Object[] arr;
      int index;
      final E ret=(E)(arr=root.arr)[index=this.nextIndex];
      if(--index==-1)
      {
        index=arr.length-1;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(Consumer<? super E> action)
    {
      final int tail,index,head;
      final RefArrDeque<E> root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        RefArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        Object[] arr;
        RefArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
        RefArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void remove()
    {
      final RefArrDeque<E> root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final Object[] arr;
        int arrLength=(arr=root.arr).length-1;
        if((removeIndex=this.nextIndex+1)>arrLength)
        {
          //0 == removeIndex <=tail < head <= arr.length-1
          int before;
          if((before=arrLength-head)<tail)
          {
            arr[0]=arr[arrLength];
            //cheaper to move elements forward
            if(before==0)
            {
              root.head=0;
              arr[head]=null;
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,head+1,before);
              arr[head]=null;
              root.head=head+1;
            }
            //also move the index to the beginning of the array
            this.nextIndex=0;
          }
          else
          {
            //cheaper to move elements backward
            if(tail==0)
            {
              root.tail=arrLength;
            }
            else
            {
              root.tail=tail-1;
              ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
            }
            arr[tail]=null;
          }
        }
        else
        {
          int headDist,after;
          if((headDist=removeIndex-head)<0)
          {
            //0 < removeIndex <= tail < head < arr.length
            if((headDist=arrLength-head)+removeIndex<(after=tail-removeIndex))
            {
              //cheaper to shift elements forward
              ArrCopy.uncheckedCopy(arr,0,arr,1,removeIndex);
              arr[0]=arr[arrLength];
              if(headDist==0)
              {
                arr[head]=null;
                root.head=0;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,head,arr,head+1,headDist);
                arr[head]=null;
                root.head=head+1;
              }
              //also move the nextIndex up
              this.nextIndex=removeIndex;
            }
            else
            {
              //cheaper to shift elements backwards
              if(after==0)
              {
                root.tail=arrLength;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
                root.tail=tail-1;
              }
              arr[tail]=null;
            }
          }
          else
          {
            //0 <= tail < head <= removeIndex <= arr.length-1
            if(headDist<=(after=arrLength-removeIndex)+tail)
            {
              //cheaper to shift elements forward
              //also move the index up
              if(head==arrLength)
              {
                root.head=0;
              }
              else
              {
                ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
                root.head=head+1;
              }
              arr[head]=null;
              this.nextIndex=removeIndex;
            }
            else
            {
              //cheaper to shift elements down
              ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
              arr[arrLength]=arr[0];
              if(tail==0)
              {
                root.tail=arrLength;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
                root.tail=tail-1;
              }
              arr[tail]=null;
            }
          }
        }
      }
      else
      {
        //non-fragmented
        //0 <= head <= removeIndex <= tail < arr.length
        final var arr=root.arr;
        int headDist,tailDist;
        if((headDist=(removeIndex=this.nextIndex+1)-head)<(tailDist=tail-removeIndex))
        {
          //cheaper to move elements forward
          this.nextIndex=removeIndex;
          ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
          arr[head]=null;
        }
        else
        {
          //cheaper to move elements backward
          ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tailDist);
          arr[tail]=null;
        }
      }
    }
  }
  private static class AscendingItr<E>
    implements OmniIterator.OfRef<E>
  {
    transient final RefArrDeque<E> root;
    transient int nextIndex;
    AscendingItr(RefArrDeque<E> root)
    {
      this.root=root;
      this.nextIndex=root.head;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final RefArrDeque<E> root;
      return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
        (index>=(head=root.head) && tail<head);
    }
    @SuppressWarnings("unchecked")
    @Override
    public E next()
    {
      final Object[] arr;
      int index;
      final E ret=(E)(arr=root.arr)[index=this.nextIndex];
      if(++index==arr.length)
      {
        index=0;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(Consumer<? super E> action)
    {
      final int tail,index,head;
      final RefArrDeque<E> root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        RefArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final Object[] arr;
        RefArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
        RefArrSeq.uncheckedForwardForEach(arr,0,tail,action);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void remove()
    {
      final RefArrDeque<E> root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final Object[] arr;
        int arrLength=(arr=root.arr).length-1;
        if((removeIndex=this.nextIndex-1)==-1)
        {
          //0 <= tail < head <= removeIndex == arr.length-1
          int before;
          if((before=arrLength-head)<=tail)
          {
            //cheaper to move elements forward
            if(before==0)
            {
              root.head=0;
              arr[head]=null;
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,head+1,before);
              arr[head]=null;
              root.head=head+1;
            }
          }
          else
          {
            arr[arrLength]=arr[0];
            //cheaper to move elements backward
            if(tail==0)
            {
              root.tail=arrLength;
            }
            else
            {
              root.tail=tail-1;
              ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
            }
            arr[tail]=null;
            //also move the nextIndex to the end of the array
            this.nextIndex=arrLength;
          }
        }
        else
        {
          int headDist,after;
          if((headDist=removeIndex-head)<0)
          {
            //0 <= removeIndex <= tail < head < arr.length
            if((headDist=arrLength-head)+removeIndex<(after=tail-removeIndex))
            {
              //cheaper to shift elements forward
              ArrCopy.semicheckedCopy(arr,0,arr,1,removeIndex);
              arr[0]=arr[arrLength];
              if(headDist==0)
              {
                arr[head]=null;
                root.head=0;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,head,arr,head+1,headDist);
                arr[head]=null;
                root.head=head+1;
              }
            }
            else
            {
              //cheaper to shift elements backwards
              if(after==0)
              {
                root.tail=arrLength;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
                root.tail=tail-1;
              }
              arr[tail]=null;
              //also move the nextIndex down
              this.nextIndex=removeIndex;
            }
          }
          else
          {
            //0 <= tail < head <= removeIndex < arr.length-1
            if(headDist<=(after=arrLength-removeIndex)+tail)
            {
              //cheaper to shift elements forward
              ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
              arr[head]=null;
              root.head=head+1;
            }
            else
            {
              //cheaper to shift elements down
              ArrCopy.uncheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
              arr[arrLength]=arr[0];
              if(tail==0)
              {
                root.tail=arrLength;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
                root.tail=tail-1;
              }
              arr[tail]=null;
              //also move the nextIndex down
              this.nextIndex=removeIndex;
            }
          }
        }
      }
      else
      {
        //non-fragmented
        //0 <= head <= removeIndex <= tail < arr.length
        final var arr=root.arr;
        int headDist,tailDist;
        if((headDist=(removeIndex=this.nextIndex-1)-head)<(tailDist=tail-removeIndex))
        {
          //cheaper to move elements forward
          ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
          arr[head]=null;
        }
        else
        {
          //cheaper to move elements backward
          this.nextIndex=removeIndex;
          ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tailDist);
          arr[tail]=null;
        }
      }
    }
  }
  @Override
  public boolean contains(final Object val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      {
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public int search(final Object val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      {
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public boolean remove(final Object val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      {
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final Object val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      {
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      {
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final byte val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final char val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final short val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final Boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Byte val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Character val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Short val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Integer val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Double val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      {
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final byte val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final char val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final short val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final Boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final Byte val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final Character val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final Short val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final Integer val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final Long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final Float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final Double val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public static class Checked<E> extends RefArrDeque<E>
  {
    transient int modCount;
    public Checked()
    {
      super();
    }
    public Checked(int capacity)
    {
      super(capacity);
    }
    public Checked(int head,int tail,Object[] arr)
    {
      super(head,tail,arr);
    }
    @Override
    public Object clone()
    {
      int tail;
      if((tail=this.tail+1)!=0)
      {
        final var arr=this.arr;
        final Object[] newArr;
        int head,size;
        if((size=tail-(head=this.head))<=0)
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new Object[size+=arr.length],0,head);
          ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
        }
        else
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new Object[size],0,size);
        }
        return new Checked<E>(0,size-1,newArr);
      }
      return new Checked<E>();
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public String toString()
    {
      final int tail;
      if((tail=this.tail+1)!=0)
      {
        int modCount=this.modCount;
        try
        {
          return super.toStringHelper(tail);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final int tail;
      if((tail=this.tail+1)!=0)
      {
        int modCount=this.modCount;
        try
        {
          return super.hashCodeHelper(tail);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
      return 1;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E element()
    {
      if(tail!=-1)
      {
        return (E)arr[head];
      }
      throw new NoSuchElementException();
    }
    @SuppressWarnings("unchecked")
    @Override
    public E getLast()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        return (E)arr[tail];
      }
      throw new NoSuchElementException();
    }
    @Override
    public void addLast(E val)
    {
      ++this.modCount;
      super.addLast(val);
    }
    @Override
    public void push(E val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public E pop()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        return super.uncheckedExtractFirst(tail);
      }
      throw new NoSuchElementException();
    }
    @Override
    public E removeLast()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        return super.uncheckedExtractLast(tail);
      }
      throw new NoSuchElementException();
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray(arrSize->
      {
        int modCount=this.modCount;
        try
        {
          return arrConstructor.apply(arrSize);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override
    public void clear()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        if(tail<(head=this.head))
        {
          Object[] arr;
          OmniArray.OfRef.nullifyRange(arr=this.arr,0,tail);
          OmniArray.OfRef.nullifyRange(arr,head,arr.length-1);
        }
        else
        {
          OmniArray.OfRef.nullifyRange(arr,head,tail);
        }
      }
    }
    @Override
    void uncheckedForEach(int tail,Consumer<? super E> action)
    {
      int modCount=this.modCount;
      try
      {
        super.uncheckedForEach(tail,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    public E pollLast()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        Object[] arr;
        @SuppressWarnings("unchecked")
        final var ret=(E)((arr=this.arr)[tail]);
        switch(Integer.signum(this.head-tail))
        {
        default:
          if(tail==0)
          {
            this.tail=arr.length-1;
            break;
          }
        case -1:
          this.tail=tail-1;
          break;
        case 0:
          this.tail=-1;
        }
        arr[tail]=null;
        return ret;
      }
      return null;
    }
    @Override
    public E poll()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        Object[] arr;
        @SuppressWarnings("unchecked")
        final var ret=(E)((arr=this.arr)[head=this.head]);
        switch(Integer.signum(head-tail))
        {
        default:
          if(head==arr.length-1)
          {
            this.head=0;
            break;
          }
        case -1:
          this.head=head+1;
          break;
        case 0:
          this.tail=-1;
        }
        arr[head]=null;
        return ret;
      }
      return null;
    }
    private static class CheckedAscendingItr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final Checked<E> root;
      transient int modCount;
      transient int nextIndex;
      transient int lastRet;
      CheckedAscendingItr(Checked<E> root)
      {
        this.root=root;
        this.modCount=root.modCount;
        this.lastRet=-1;
        this.nextIndex=root.head;
      }
      @Override
      public boolean hasNext()
      {
        final int tail,head,index;
        final RefArrDeque<E> root;
        return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
          (index>=(head=root.head) && tail<head);
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int tail,index;
        final var arr=root.arr;
        if((index=this.nextIndex)>(tail=root.tail))
        {
          int head;
          if(index<(head=root.head) || tail>head)
          {
            throw new NoSuchElementException();
          }
          else if(index==arr.length-1)
          {
            this.nextIndex=0;
          }
          else
          {
            this.nextIndex=index+1;
          }
        }
        else
        {
          this.nextIndex=index+1;
        }
        this.lastRet=index;
        return (E)arr[index];
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        final int tail,index,head;
        final Checked<E> root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            RefArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=tail-1;
          this.nextIndex=tail;
        }
        else if(index>=(head=root.head) && tail<=head)
        {
          int modCount=this.modCount;
          try
          {
            final Object[] arr;
            RefArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
            RefArrSeq.uncheckedForwardForEach(arr,0,tail,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=tail-1;
          this.nextIndex=tail; 
        }
      }
      @Override
      public void remove()
      {
        int removeIndex;
        if((removeIndex=this.lastRet)==-1)
        {
          throw new IllegalStateException();
        }
        int modCount;
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        int head,tail;
        if((tail=root.tail)<(head=root.head))
        {
          //fragmented
          final Object[] arr;
          int arrLength=(arr=root.arr).length-1;
          {
            int headDist,after;
            if((headDist=removeIndex-head)<0)
            {
              //0 <= removeIndex <= tail < head < arr.length
              if((headDist=arrLength-head)+removeIndex<(after=tail-removeIndex))
              {
                //cheaper to shift elements forward
                ArrCopy.semicheckedCopy(arr,0,arr,1,removeIndex);
                arr[0]=arr[arrLength];
                if(headDist==0)
                {
                  arr[head]=null;
                  root.head=0;
                }
                else
                {
                  ArrCopy.uncheckedCopy(arr,head,arr,head+1,headDist);
                  arr[head]=null;
                  root.head=head+1;
                }
              }
              else
              {
                //cheaper to shift elements backwards
                if(after==0)
                {
                  root.tail=arrLength;
                }
                else
                {
                  ArrCopy.uncheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
                  root.tail=tail-1;
                }
                arr[tail]=null;
                //also move the nextIndex down
                this.nextIndex=removeIndex;
              }
            }
            else
            {
              //0 <= tail < head <= removeIndex <= arr.length-1
              if(headDist<=(after=arrLength-removeIndex)+tail)
              {
                //cheaper to shift elements forward
                ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
                arr[head]=null;
                root.head=head+1;
              }
              else
              {
                //cheaper to shift elements down
                ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
                arr[arrLength]=arr[0];
                if(tail==0)
                {
                  root.tail=arrLength;
                }
                else
                {
                  ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
                  root.tail=tail-1;
                }
                arr[tail]=null;
                //also move the nextIndex down
                this.nextIndex=removeIndex;
              }
            }
          }
        }
        else
        {
          //non-fragmented
          //0 <= head <= removeIndex <= tail < arr.length
          final var arr=root.arr;
          int headDist,tailDist;
          if((headDist=removeIndex-head)<(tailDist=tail-removeIndex))
          {
            //cheaper to move elements forward
            ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
            arr[head]=null;
          }
          else
          {
            //cheaper to move elements backward
            this.nextIndex=removeIndex;
            ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tailDist);
            arr[tail]=null;
          }
        }
      }
    }
    private static class CheckedDescendingItr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final Checked<E> root;
      transient int modCount;
      transient int nextIndex;
      transient int lastRet;
      CheckedDescendingItr(Checked<E> root)
      {
        this.root=root;
        this.modCount=root.modCount;
        this.lastRet=-1;
        this.nextIndex=root.tail;
      }
       @Override
      public boolean hasNext()
      {
        final int tail,head,index;
        final RefArrDeque<E> root;
        return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int head,index;
        final var arr=root.arr;
        if((index=this.nextIndex)<(head=root.head))
        {
          int tail;
          if(index>(tail=root.tail) || tail>=head)
          {
            throw new NoSuchElementException();
          }
          else if(index==0)
          {
            this.nextIndex=arr.length-1;
          }
          else
          {
            this.nextIndex=index-1;
          }
        }
        else
        {
          this.nextIndex=index-1;
        }
        this.lastRet=index;
        return (E)arr[index];
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        final int tail,index,head;
        final Checked<E> root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            RefArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=head;
          this.nextIndex=head-1;
        }
        else if(index<=(tail=root.tail) && tail<head)
        {
          int modCount=this.modCount;
          try
          {
            Object[] arr;
            RefArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
            RefArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=head;
          this.nextIndex=head-1;
        }
      }
      @Override
      public void remove()
      {
        int removeIndex;
        if((removeIndex=this.lastRet)==-1)
        {
          throw new IllegalStateException();
        }
        int modCount;
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        int head,tail;
        if((tail=root.tail)<(head=root.head))
        {
          //fragmented
          final Object[] arr;
          int arrLength=(arr=root.arr).length-1;
          {
            int headDist,after;
            if((headDist=removeIndex-head)<0)
            {
              //0 <= removeIndex <= tail < head < arr.length
              if((headDist=arrLength-head)+removeIndex<(after=tail-removeIndex))
              {
                //cheaper to shift elements forward
                ArrCopy.semicheckedCopy(arr,0,arr,1,removeIndex);
                arr[0]=arr[arrLength];
                if(headDist==0)
                {
                  arr[head]=null;
                  root.head=0;
                }
                else
                {
                  ArrCopy.uncheckedCopy(arr,head,arr,head+1,headDist);
                  arr[head]=null;
                  root.head=head+1;
                }
                //also move the nextIndex up
                this.nextIndex=removeIndex;
              }
              else
              {
                //cheaper to shift elements backwards
                if(after==0)
                {
                  root.tail=arrLength;
                }
                else
                {
                  ArrCopy.uncheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
                  root.tail=tail-1;
                }
                arr[tail]=null;
              }
            }
            else
            {
              //0 <= tail < head <= removeIndex <= arr.length-1
              if(headDist<=(after=arrLength-removeIndex)+tail)
              {
                //cheaper to shift elements forward
                //also move the index up
                if(head==arrLength)
                {
                  root.head=0;
                }
                else
                {
                  ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
                  root.head=head+1;
                }
                arr[head]=null;
                this.nextIndex=removeIndex;
              }
              else
              {
                //cheaper to shift elements down
                ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
                arr[arrLength]=arr[0];
                if(tail==0)
                {
                  root.tail=arrLength;
                }
                else
                {
                  ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
                  root.tail=tail-1;
                }
                arr[tail]=null;
              }
            }
          }
        }
        else
        {
          //non-fragmented
          //0 <= head <= removeIndex <= tail < arr.length
          final var arr=root.arr;
          int headDist,tailDist;
          if((headDist=removeIndex-head)<(tailDist=tail-removeIndex))
          {
            //cheaper to move elements forward
            this.nextIndex=removeIndex;
            ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
            arr[head]=null;
          }
          else
          {
            //cheaper to move elements backward
            ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tailDist);
            arr[tail]=null;
          }
        }
      }
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new CheckedAscendingItr<E>(this);
    }
    @Override
    public OmniIterator.OfRef<E> descendingIterator()
    {
      return new CheckedDescendingItr<E>(this);
    }
    @Override
    public boolean contains(final Object val)
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          try
          {
            return super.uncheckedcontains(tail,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return super.uncheckedcontains(tail,Objects::isNull);
      }
      return false;
    }
    @Override
    public int search(final Object val)
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          try
          {
            return super.uncheckedsearch(tail,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return super.uncheckedsearch(tail,Objects::isNull);
      }
      return -1;
    }
    @Override
    public boolean remove(Object val)
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        if(val!=null)
        {
          return uncheckedremoveValNonNull(tail,val);
        }
        return super.uncheckedremoveVal(tail,Objects::isNull);
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(Object val)
    {
      final int tail;
      if((tail=this.tail)!=-1)
      {
        if(val!=null)
        {
          return uncheckedremoveLastOccurrenceNonNull(tail,val);
        }
        return super.uncheckedremoveLastOccurrence(tail,Objects::isNull);
      }
      return false;
    }
    private boolean fragmentedremoveLastOccurrenceNonNull(int head,int tail,Object val)
    {
      int modCount=this.modCount;
      try
      {
        final Object[] arr=this.arr;
        int removeIndex=tail,arrLength;
        for(;;--removeIndex)
        {
          if(val.equals(arr[removeIndex]))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            //0 <= removeIndex <= tail < head <= arr.length-1
            int before,after;
            if((before=(arrLength=arr.length-1)-head)+removeIndex<(after=tail-removeIndex))
            {
              ArrCopy.semicheckedCopy(arr,0,arr,1,removeIndex);
              arr[0]=arr[arrLength];
              //cheaper to move elements forward
              if(before==0)
              {
                this.head=0;
                arr[head]=null;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,head,arr,head+1,before);
                arr[head]=null;
                this.head=head+1;
              }
            }
            else
            {
              //cheaper to move elements backward
              if(tail==0)
              {
                this.tail=arrLength;
              }
              else
              {
                this.tail=tail-1;
                ArrCopy.semicheckedCopy(arr,1,arr,0,after);
              }
              arr[tail]=null;
            }
            return true;
          }
          if(removeIndex==0)
          {
            removeIndex=arrLength=arr.length-1;
            break;
          }
        }
        for(;;--removeIndex)
        {
          if(val.equals(arr[removeIndex]))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            //0 <= tail < head <= removeIndex <= arr.length-1
            int headDist,after;
            if((headDist=removeIndex-head)<=(after=arrLength-removeIndex)+tail)
            {
              //cheaper to shift elements forward
              //also move the removeIndex up
              if(head==arrLength)
              {
                this.head=0;
              }
              else
              {
                ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
                this.head=head+1;
              }
              arr[head]=null;
            }
            else
            {
              //cheaper to shift elements down
              ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
              arr[arrLength]=arr[0];
              if(tail==0)
              {
                this.tail=arrLength;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
                this.tail=tail-1;
              }
              arr[tail]=null;
            }
            return true;
          }
          if(removeIndex==head)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
        }
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    private boolean fragmentedremoveValNonNull(int head,int tail,Object val)
    {
      int modCount=this.modCount;
      try
      {
        final Object[] arr;
        int removeIndex=head;
        int arrLength;
        for(arrLength=(arr=this.arr).length-1;;++removeIndex)
        {
          if(val.equals(arr[removeIndex]))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            //0 <= tail < head <= removeIndex <= arr.length-1
            int headDist,after;
            if((headDist=removeIndex-head)<=(after=arrLength-removeIndex)+tail)
            {
              //cheaper to shift elements forward
              //also move the removeIndex up
              if(head==arrLength)
              {
                this.head=0;
              }
              else
              {
                ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
                this.head=head+1;
              }
              arr[head]=null;
            }
            else
            {
              //cheaper to shift elements down
              ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
              arr[arrLength]=arr[0];
              if(tail==0)
              {
                this.tail=arrLength;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
                this.tail=tail-1;
              }
              arr[tail]=null;
            }
            return true;
          }
          if(removeIndex==arrLength)
          {
            removeIndex=0;
            break;
          }
        }
        for(;;++removeIndex)
        {
          if(val.equals(arr[removeIndex]))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            //0 <= removeIndex <= tail < head <= arr.length-1
            int before,after;
            if((before=(arrLength)-head)+removeIndex<(after=tail-removeIndex))
            {
              ArrCopy.semicheckedCopy(arr,0,arr,1,removeIndex);
              arr[0]=arr[arrLength];
              //cheaper to move elements forward
              if(before==0)
              {
                this.head=0;
                arr[head]=null;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,head,arr,head+1,before);
                arr[head]=null;
                this.head=head+1;
              }
            }
            else
            {
              //cheaper to move elements backward
              if(tail==0)
              {
                this.tail=arrLength;
              }
              else
              {
                this.tail=tail-1;
                ArrCopy.semicheckedCopy(arr,1,arr,0,after);
              }
              arr[tail]=null;
            }
            return true;
          }
          if(removeIndex==tail)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
        }
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    private boolean uncheckedremoveLastOccurrenceNonNull(int tail,Object val)
    {
      final int head;
      if(tail<(head=this.head))
      {
        return fragmentedremoveLastOccurrenceNonNull(head,tail,val);
      }
      return nonfragmentedremoveLastOccurrenceNonNull(head,tail,val);
    }
    private boolean nonfragmentedremoveLastOccurrenceNonNull(int head,int tail,Object val)
    {
      int modCount=this.modCount;
      try
      {
        final var arr=this.arr;
        int index=tail;
        for(;;)
        {
          if(val.equals(arr[index]))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            int hDist,tDist;
            if((hDist=index-head)<=(tDist=tail-index))
            {
              if(hDist==0)
              {
                if(tDist==0)
                {
                  arr[index]=null;
                  this.tail=-1;
                  return true;
                }
                tDist=head+1;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,head,arr,tDist=head+1,hDist);
              }
              arr[head]=null;
              this.head=tDist;
            }
            else
            {
              ArrCopy.semicheckedCopy(arr,index+1,arr,index,tDist);
              arr[tail]=null;
              this.tail=tail-1;
            }
            return true;
          }
          if(index==head)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
          --index;
        }
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    private boolean uncheckedremoveValNonNull(int tail,Object val)
    {
      final int head;
      if(tail<(head=this.head))
      {
        return fragmentedremoveValNonNull(head,tail,val);
      }
      return nonfragmentedremoveValNonNull(head,tail,val);
    }
    private boolean nonfragmentedremoveValNonNull(int head,int tail,Object val)
    {
      int modCount=this.modCount;
      try
      {
        final var arr=this.arr;
        int index=head;
        for(;;)
        {
          if(val.equals(arr[index]))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            int hDist,tDist;
            if((hDist=index-head)<=(tDist=tail-index))
            {
              if(hDist==0)
              {
                if(tDist==0)
                {
                  arr[index]=null;
                  this.tail=-1;
                  return true;
                }
                tDist=head+1;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,head,arr,tDist=head+1,hDist);
              }
              arr[head]=null;
              this.head=tDist;
            }
            else
            {
              ArrCopy.semicheckedCopy(arr,index+1,arr,index,tDist);
              arr[tail]=null;
              this.tail=tail-1;
            }
            return true;
          }
          if(index==tail)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
          ++index;
        }
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @Override
    boolean fragmentedremoveVal(int head,int tail,Predicate<Object> pred)
    {
      final Object[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=head;
      for(;;++removeIndex)
      {
        if(pred.test(arr[removeIndex]))
        {
          ++this.modCount;
          //0 <= tail < head <= removeIndex <= arr.length-1
          int headDist,after;
          if((headDist=removeIndex-head)<=(after=arrLength-removeIndex)+tail)
          {
            //cheaper to shift elements forward
            //also move the removeIndex up
            if(head==arrLength)
            {
              this.head=0;
            }
            else
            {
              ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
              this.head=head+1;
            }
            arr[head]=null;
          }
          else
          {
            //cheaper to shift elements down
            ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
            arr[arrLength]=arr[0];
            if(tail==0)
            {
              this.tail=arrLength;
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
              this.tail=tail-1;
            }
            arr[tail]=null;
          }
          return true;
        }
        if(removeIndex==arrLength)
        {
          removeIndex=0;
          break;
        }
      }
      for(;;++removeIndex)
      {
        if(pred.test(arr[removeIndex]))
        {
          ++this.modCount;
          //0 <= removeIndex <= tail < head <= arr.length-1
          int before,after;
          if((before=(arrLength)-head)+removeIndex<(after=tail-removeIndex))
          {
            ArrCopy.semicheckedCopy(arr,0,arr,1,removeIndex);
            arr[0]=arr[arrLength];
            //cheaper to move elements forward
            if(before==0)
            {
              this.head=0;
              arr[head]=null;
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,head+1,before);
              arr[head]=null;
              this.head=head+1;
            }
          }
          else
          {
            //cheaper to move elements backward
            if(tail==0)
            {
              this.tail=arrLength;
            }
            else
            {
              this.tail=tail-1;
              ArrCopy.semicheckedCopy(arr,1,arr,0,after);
            }
            arr[tail]=null;
          }
          return true;
        }
        if(removeIndex==tail)
        {
          return false;
        }
      }
    }
    @Override
    boolean nonfragmentedremoveVal(int head,int tail,Predicate<Object> pred)
    {
      final var arr=this.arr;
      for(int removeIndex=head;;++removeIndex)
      {
        if(pred.test(arr[removeIndex]))
        {
          ++this.modCount;
          int hDist,tDist;
          if((hDist=removeIndex-head)<=(tDist=tail-removeIndex))
          {
            if(hDist==0)
            {
              if(tDist==0)
              {
                arr[removeIndex]=null;
                this.tail=-1;
                return true;
              }
              tDist=head+1;
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,tDist=head+1,hDist);
            }
            arr[head]=null;
            this.head=tDist;
          }
          else
          {
            ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tDist);
            arr[tail]=null;
            this.tail=tail-1;
          }
          return true;
        }
        if(removeIndex==tail)
        {
          return false;
        }
      }
    }
    @Override
    boolean fragmentedremoveLastOccurrence(int head,int tail,Predicate<Object> pred)
    {
      final Object[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=tail;
      for(;;--removeIndex)
      {
        if(pred.test(arr[removeIndex]))
        {
          ++this.modCount;
          //0 <= removeIndex <= tail < head <= arr.length-1
          int before,after;
          if((before=(arrLength)-head)+removeIndex<(after=tail-removeIndex))
          {
            ArrCopy.semicheckedCopy(arr,0,arr,1,removeIndex);
            arr[0]=arr[arrLength];
            //cheaper to move elements forward
            if(before==0)
            {
              this.head=0;
              arr[head]=null;
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,head+1,before);
              arr[head]=null;
              this.head=head+1;
            }
          }
          else
          {
            //cheaper to move elements backward
            if(tail==0)
            {
              this.tail=arrLength;
            }
            else
            {
              this.tail=tail-1;
              ArrCopy.semicheckedCopy(arr,1,arr,0,after);
            }
            arr[tail]=null;
          }
          return true;
        }
        if(removeIndex==0)
        {
          removeIndex=arrLength;
          break;
        }
      }
      for(;;++removeIndex)
      {
        if(pred.test(arr[removeIndex]))
        {
          ++this.modCount;
          //0 <= tail < head <= removeIndex <= arr.length-1
          int headDist,after;
          if((headDist=removeIndex-head)<=(after=arrLength-removeIndex)+tail)
          {
            //cheaper to shift elements forward
            //also move the removeIndex up
            if(head==arrLength)
            {
              this.head=0;
            }
            else
            {
              ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
              this.head=head+1;
            }
            arr[head]=null;
          }
          else
          {
            //cheaper to shift elements down
            ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
            arr[arrLength]=arr[0];
            if(tail==0)
            {
              this.tail=arrLength;
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
              this.tail=tail-1;
            }
            arr[tail]=null;
          }
          return true;
        }
        if(removeIndex==head)
        {
          return false;
        }
      }
    }
    @Override
    boolean nonfragmentedremoveLastOccurrence(int head,int tail,Predicate<Object> pred)
    {
      final var arr=this.arr;
      for(int removeIndex=tail;;--removeIndex)
      {
        if(pred.test(arr[removeIndex]))
        {
          ++this.modCount;
          int hDist,tDist;
          if((hDist=removeIndex-head)<=(tDist=tail-removeIndex))
          {
            if(hDist==0)
            {
              if(tDist==0)
              {
                arr[removeIndex]=null;
                this.tail=-1;
                return true;
              }
              tDist=head+1;
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,tDist=head+1,hDist);
            }
            arr[head]=null;
            this.head=tDist;
          }
          else
          {
            ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tDist);
            arr[tail]=null;
            this.tail=tail-1;
          }
          return true;
        }
        if(removeIndex==head)
        {
          return false;
        }
      }
    }
    @SuppressWarnings("unchecked")
    private void collapseheadNonFragmented(int modCount,Object[] arr,int head,int tail,Predicate<? super E> filter)
    {
      int dstOffset;
      for(;;)
      {
        if(--tail==head)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          dstOffset=head;
        }
        if(filter.test((E)arr[tail]))
        {
          dstOffset=RefArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,tail,head,filter);
          break;
        }
      }
      this.head=dstOffset+1;
      this.modCount=modCount+1;
      OmniArray.OfRef.nullifyRange(arr,head,dstOffset);
    }
    @SuppressWarnings("unchecked")
    private void collapsetailNonFragmented(int modCount,Object[] arr,int head,int tail,Predicate<? super E> filter)
    {
      int dstOffset;
      for(;;)
      {
        if(++head==tail)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          dstOffset=tail;
        }
        if(filter.test((E)arr[head]))
        {
          dstOffset=RefArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter);
          break;
        }
      }
      this.tail=dstOffset-1;
      this.modCount=modCount+1;
      OmniArray.OfRef.nullifyRange(arr,dstOffset,tail);
    }
    @SuppressWarnings("unchecked")
    private void collapseheadFragmentedHelper(int modCount,Object[] arr,int tail,Predicate<? super E> filter)
    {
      for(;;)
      {
        if(tail==0)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=0;
          return;
        }
        if(filter.test((E)arr[--tail]))
        {
          break;
        }
      }
      this.head=(tail=RefArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,tail,-1,filter))+1;
      OmniArray.OfRef.nullifyRange(arr,0,tail);
    }
    @SuppressWarnings("unchecked")
    private void collapsetailFragmentedHelper(int modCount,Object[] arr,int head,Predicate<? super E> filter)
    {
      int bound=arr.length;
      for(;;)
      {
        if(++head==bound)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.tail=bound-1;
          return;
        }
        if(filter.test((E)arr[head]))
        {
          break;
        }
      }
      this.tail=(head=RefArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,bound,filter))-1;
      OmniArray.OfRef.nullifyRange(arr,head,bound-1);
    }
    @SuppressWarnings("unchecked")
    private void collapseheadFragmented(int modCount,Object[] arr,int head,int tail,Predicate<? super E> filter)
    {
      int oldHead=head;
      for(int bound=arr.length-1;;)
      {
        if(head==bound)
        {
          collapseheadFragmentedHelper(modCount,arr,tail,filter);
          OmniArray.OfRef.nullifyRange(arr,oldHead,bound);
          break;
        }
        if(!filter.test((E)arr[++head]))
        {
          collapseheadFragmentedHelper(modCount,arr,head,tail,filter);
          OmniArray.OfRef.nullifyRange(arr,oldHead,head-1);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    @SuppressWarnings("unchecked")
    private void collapsetailFragmented(int modCount,Object[] arr,int head,int tail,Predicate<? super E> filter)
    {
      int oldTail=tail;
      for(;;)
      {
        if(tail==0)
        {
          collapsetailFragmentedHelper(modCount,arr,head,filter);
          OmniArray.OfRef.nullifyRange(arr,0,oldTail);
          break;
        }
        if(!filter.test((E)arr[--tail]))
        {
          collapsetailFragmentedHelper(modCount,arr,head,tail,filter);
          OmniArray.OfRef.nullifyRange(arr,tail+1,oldTail);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    @SuppressWarnings("unchecked")
    private void collapseheadFragmentedHelper(int modCount,Object[] arr,int head,int tail,Predicate<? super E> filter)
    {
      for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
      {
        if(filter.test((E)arr[headOffset]))
        {
          for(int tailOffset=0;tailOffset!=tail;++tailOffset)
          {
            if(filter.test((E)arr[tailOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              return;
            }
          }
          this.head=headOffset=RefArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          arr[headOffset]=arr[head];
          OmniArray.OfRef.nullifyRange(arr,head,headOffset-1);
          return;
        }
      }
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((E)arr[tailOffset]))
        {
          this.tail=tailOffset=RefArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter);
          arr[tailOffset]=arr[tail];
          OmniArray.OfRef.nullifyRange(arr,tailOffset+1,tail);
          return;
        }
      }
    }
    @SuppressWarnings("unchecked")
    private void collapsetailFragmentedHelper(int modCount,Object[] arr,int head,int tail,Predicate<? super E> filter)
    {
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((E)arr[tailOffset]))
        {
          for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
          {
            if(filter.test((E)arr[headOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              return;
            }
          }
          this.tail=tailOffset=RefArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter);
          arr[tailOffset]=arr[tail];
          OmniArray.OfRef.nullifyRange(arr,tailOffset+1,tail);
          return;
        }
      }
      for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
      {
        if(filter.test((E)arr[headOffset]))
        {
          this.head=headOffset=RefArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          arr[headOffset]=arr[head];
          OmniArray.OfRef.nullifyRange(arr,head,headOffset-1);
          return;
        }
      }
    }
    @SuppressWarnings("unchecked")
    private void collapseheadandtailFragmentedHelper(int modCount,Object[] arr,int head,int tail,int headOffset,int tailOffset,Predicate<? super E> filter)
    {
      int tailDstOffset=tailOffset;
      int headDstOffset;
      outer:for(;;)
      {
        if(++tailOffset==tail)
        {
          headDstOffset=RefArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          break;
        }
        final Object tailVal;
        if(!filter.test((E)(tailVal=arr[tailOffset])))
        {
          headDstOffset=headOffset;
          for(;;)
          {
            if(--headOffset==head)
            {
              int numTailSurvivors;
              if((numTailSurvivors=tail-++tailOffset)!=0)
              {
                final long[] survivors;
                numTailSurvivors=RefArrSeq.markSurvivors(arr,survivors=BitSetUtils.getBitSet(numTailSurvivors),tailOffset,tail,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[tailDstOffset++]=tailVal;
                if(numTailSurvivors!=0)
                {
                  tailDstOffset=RefArrSeq.pullSurvivorsDown(arr,survivors,tailDstOffset,tailOffset,numTailSurvivors);
                }
              }
              else
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[tailDstOffset++]=tailVal;
              }
              break outer;
            }
            final Object headVal;
            if(!filter.test((E)(headVal=arr[headOffset])))
            {
              int numHeadSurvivors,numTailSurvivors;
              if((numHeadSurvivors=(--headOffset)-head)+(numTailSurvivors=tail-++tailOffset)!=0)
              {
                final long[] survivors;
                numTailSurvivors=RefArrSeq.markSurvivors(arr,survivors=new long[((numTailSurvivors-1)>>>6)+((numHeadSurvivors-1)>>>6)+2],tailOffset,tail,filter);
                numHeadSurvivors=RefArrSeq.markSurvivorsReverse(arr,survivors,headOffset,head,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[headDstOffset--]=headVal;
                if(numHeadSurvivors!=0)
                {
                  headDstOffset=RefArrSeq.pullSurvivorsUp(arr,survivors,headDstOffset,headOffset,numHeadSurvivors);
                }
                arr[tailDstOffset++]=tailVal;
                if(numTailSurvivors!=0)
                {
                  tailDstOffset=RefArrSeq.pullSurvivorsDown(arr,survivors,tailDstOffset,tailOffset,numTailSurvivors);
                }
              }
              else
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[headDstOffset--]=headVal;
                arr[tailDstOffset++]=tailVal;
              }
              break outer;
            }
          }
        }
      }
      this.tail=tailDstOffset;
      this.head=headDstOffset;
      arr[headDstOffset]=arr[head];
      arr[tailDstOffset]=arr[tail];
      OmniArray.OfRef.nullifyRange(arr,head,headDstOffset-1);
      OmniArray.OfRef.nullifyRange(arr,tailDstOffset+1,tail);
    }
    @SuppressWarnings("unchecked")
    private boolean collapsebodyFragmented(int modCount,Object[] arr,int head,int tail,Predicate<? super E> filter)
    {
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((E)arr[tailOffset]))
        {
          for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
          {
            if(filter.test((E)arr[headOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              this.modCount=modCount+1;
              return true;
            }
          }
          arr[tailOffset=RefArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter)]=arr[tail];
          this.modCount=modCount+1;
          OmniArray.OfRef.nullifyRange(arr,tailOffset+1,tail);
          this.tail=tailOffset;
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @SuppressWarnings("unchecked")
    private void collapseheadandtailFragmented(int modCount,Object[] arr,int head,int tail,Predicate<? super E> filter)
    {
      int oldTail=tail;
      outer:for(;;)
      {
        if(tail==0)
        {
          if((tail=arr.length-1)==head)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            arr[head]=null;
            this.tail=-1;
          }
          else if(filter.test((E)arr[tail=arr.length-1]))
          {
            collapseheadandtailNonFragmented(modCount,arr,head,tail,filter);
          }
          else
          {
            collapseheadNonFragmented(modCount,arr,head,tail,filter);
            this.tail=tail;
          }
          OmniArray.OfRef.nullifyRange(arr,0,oldTail);
          return;
        }
        final Object tailVal;
        if(!filter.test((E)(tailVal=arr[--tail])))
        {
          int oldHead=head;
          inner: for(int bound=arr.length-1;;)
          {
            if(head==bound)
            {
              if(tail==0)
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
              }
              else
              {
                if(filter.test((E)arr[head=0]))
                {
                  collapseheadNonFragmented(modCount,arr,head,tail,filter);
                  OmniArray.OfRef.nullifyRange(arr,tail+1,oldTail);
                  this.tail=tail;
                  return;
                }
                else
                {
                  for(;;)
                  {
                    if(++head==tail)
                    {
                      CheckedCollection.checkModCount(modCount,this.modCount);
                      break;
                    }
                    if(filter.test((E)arr[head]))
                    {
                      arr[tail=RefArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter)]=tailVal;
                      break;
                    }
                  }
                }
              }
              this.head=0;
              this.tail=tail;
              OmniArray.OfRef.nullifyRange(arr,oldHead,bound);
              OmniArray.OfRef.nullifyRange(arr,tail+1,oldTail);
              break outer;
            }
            final Object headVal;
            if(!filter.test((E)(headVal=arr[++head])))
            {
              for(int tailOffset=0;tailOffset!=tail;++tailOffset)
              {
                if(filter.test((E)arr[tailOffset]))
                {
                  for(int headOffset=bound;headOffset!=head;--headOffset)
                  {
                    if(filter.test((E)arr[headOffset]))
                    {
                      collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
                      break outer;
                    }
                  }
                  arr[tail=RefArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter)]=tailVal;
                  break inner;
                }
              }
              for(int headOffset=bound;headOffset!=head;--headOffset)
              {
                if(filter.test((E)arr[headOffset]))
                {
                  arr[head=RefArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter)]=headVal;
                  break inner;
                }
              }
              CheckedCollection.checkModCount(modCount,this.modCount);
              break;
            }
          }
          this.head=head;
          this.tail=tail;
          OmniArray.OfRef.nullifyRange(arr,oldHead,head-1);
          OmniArray.OfRef.nullifyRange(arr,tail+1,oldTail);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    @SuppressWarnings("unchecked")
    private void collapseheadandtailNonFragmented(int modCount,Object[] arr,int head,int tail,Predicate<? super E> filter)
    {
      int oldHead=head;
      for(;;)
      {
        if(++head==tail)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.tail=-1;
          break;
        }
        if(!filter.test((E)arr[head]))
        {
          collapsetailNonFragmented(modCount,arr,head,tail,filter);
          this.head=head;
          break;
        }
      }
      OmniArray.OfRef.nullifyRange(arr,oldHead,head);
    }
    @SuppressWarnings("unchecked")
    private boolean collapsebodyNonFragmented(int modCount,Object[] arr,int head,int tail,Predicate<? super E> filter)
    {
      while(++head!=tail)
      {
        if(filter.test((E)arr[head]))
        {
          int dstOffset;
          this.tail=dstOffset=RefArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter);
          arr[dstOffset]=arr[tail];
          this.modCount=modCount+1;
          OmniArray.OfRef.nullifyRange(arr,dstOffset+1,tail);
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker
    {
      public ModCountChecker(int expectedModCount)
      {
        super(expectedModCount);
      }
      @Override protected int getActualModCount()
      {
        return modCount;
      }
    }
    @SuppressWarnings("unchecked")
    private boolean uncheckedRemoveIf1(int tail,Predicate<? super E> filter)
    {
      int modCount=this.modCount;
      try
      {
        Object[] arr;
        if(filter.test((E)(arr=this.arr)[tail]))
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          arr[tail]=null;
          this.modCount=modCount+1;
          this.tail=-1;
          return true;
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        return false;
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @SuppressWarnings("unchecked")
    private boolean uncheckedRemoveIfFragmented(int head,int tail,Predicate<? super E> filter)
    {
      int modCount=this.modCount;
      try
      {
        Object[] arr;
        if(filter.test((E)(arr=this.arr)[head]))
        {
          if(filter.test((E)arr[tail]))
          {
            collapseheadandtailFragmented(modCount,arr,head,tail,filter);
          }
          else
          {
            collapseheadFragmented(modCount,arr,head,tail,filter);
          }
          return true;
        }
        else if(filter.test((E)arr[tail]))
        {
          collapsetailFragmented(modCount,arr,head,tail,filter);
          return true;
        }
        return collapsebodyFragmented(modCount,arr,head,tail,filter);
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @SuppressWarnings("unchecked")
    boolean uncheckedRemoveIf(int tail,Predicate<? super E> filter)
    {
      int head;
      switch(Integer.signum(tail-(head=this.head))) 
      {
        case 0:
          return uncheckedRemoveIf1(tail,filter);
        case -1:
          return uncheckedRemoveIfFragmented(head,tail,filter);
        default:
          int modCount=this.modCount;
          try
          {
            Object[] arr;
            if(filter.test((E)(arr=this.arr)[head]))
            {
              if(filter.test((E)arr[tail]))
              {
                collapseheadandtailNonFragmented(modCount,arr,head,tail,filter);
              }
              else
              {
                collapseheadNonFragmented(modCount,arr,head,tail,filter);
              }
              return true;
            }
            else if(filter.test((E)arr[tail]))
            {
              collapsetailNonFragmented(modCount,arr,head,tail,filter);
              return true;
            }
            return collapsebodyNonFragmented(modCount,arr,head,tail,filter);
          }
          catch(final RuntimeException e)
          {
            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
          }
      }
    }
  }
  @SuppressWarnings("unchecked")
  private void collapseheadNonFragmented(Object[] arr,int head,int tail,
  Predicate<? super E> filter)
  {
    int oldhead=head;
    while(++head!=tail)
    {
      final Object v;
      if(!filter.test((E)(v=arr[head])))
      {
        if(head!=(head=pullUp(arr,tail,head,
        filter))){arr[head]=v;}
        break;
      }
    }
    this.head=head;
    OmniArray.OfRef.nullifyRange(arr,oldhead,head-1);
  }
  @SuppressWarnings("unchecked")
  private void collapsetailNonFragmented(Object[] arr,int head,int tail,
  Predicate<? super E> filter)
  {
    int oldtail=tail;
    while(--tail!=head)
    {
      final Object v;
      if(!filter.test((E)(v=arr[tail])))
      {
        if(tail!=(tail=pullDown(arr,head,tail,
        filter))){arr[tail]=v;}
        break;
      }
    }
    this.tail=tail;
    OmniArray.OfRef.nullifyRange(arr,tail+1,oldtail);
  }
  @SuppressWarnings("unchecked")
  private void collapseheadFragmented(Object[] arr,int head,int tail,Predicate<? super E> filter)
  {
    int oldTmp=head;
    int bound=arr.length-1;
    while(head!=bound)
    {
      if(!filter.test((E)arr[++head]))
      {
        for(int srcOffset=bound;srcOffset!=head;--srcOffset)
        {
          if(filter.test((E)arr[srcOffset]))
          {
            arr[srcOffset=RefArrSeq.pullSurvivorsUp(arr,srcOffset,head+1,filter)]=arr[head];
            this.head=srcOffset;
            OmniArray.OfRef.nullifyRange(arr,oldTmp,srcOffset-1);
            break;
          }
        }
        if((oldTmp=tail)!=(bound=pullDown(arr,-1,tail,filter)))
        {
          arr[bound]=arr[tail];
          this.tail=bound;
          OmniArray.OfRef.nullifyRange(arr,bound+1,oldTmp);
        }
        return;
      }
    }
    OmniArray.OfRef.nullifyRange(arr,oldTmp,head-1);
    if(-1!=(head=pullUp(arr,tail,-1,filter)))
    {
      OmniArray.OfRef.nullifyRange(arr,0,head);
    }
    this.head=head+1;
  }
  @SuppressWarnings("unchecked")
  private void collapsetailFragmented(Object[] arr,int head,int tail,Predicate<? super E> filter)
  {
    int oldTmp=tail;
    while(tail!=0)
    {
      if(!filter.test((E)arr[--tail]))
      {
        for(int srcOffset=0;srcOffset!=tail;++srcOffset)
        {
          if(filter.test((E)arr[srcOffset]))
          {
            arr[srcOffset=RefArrSeq.pullSurvivorsDown(arr,srcOffset,tail-1,filter)]=arr[tail];
            this.tail=srcOffset;
            OmniArray.OfRef.nullifyRange(arr,srcOffset+1,oldTmp);
            break;
          }
        }
        int tmp;
        if(head!=(tmp=pullUp(arr,arr.length,head,filter)))
        {
          arr[tmp]=arr[head];
          this.head=tmp;
          OmniArray.OfRef.nullifyRange(arr,head,tmp-1);
        }
        return;
      }
    }
    OmniArray.OfRef.nullifyRange(arr,tail+1,oldTmp);
    if(-1!=(tail=pullDown(arr,head,oldTmp=arr.length,filter)))
    {
      OmniArray.OfRef.nullifyRange(arr,tail,oldTmp-1);
    }
    this.tail=tail-1;
  }
  @SuppressWarnings("unchecked")
  private static <E> int pullDown(Object[] arr,int srcOffset,int srcBound,Predicate<? super E> filter)
  {
    while(++srcOffset!=srcBound)
    {
      if(filter.test((E)arr[srcOffset]))
      {
        return RefArrSeq.pullSurvivorsDown(arr,srcOffset,srcBound-1,filter);
      }
    }
    return srcOffset;
  }
  @SuppressWarnings("unchecked")
  private static <E> int pullUp(Object[] arr,int srcOffset,int srcBound,Predicate<? super E> filter)
  {
    while(--srcOffset!=srcBound)
    {
      if(filter.test((E)arr[srcOffset]))
      {
        return RefArrSeq.pullSurvivorsUp(arr,srcOffset,srcBound+1,filter);
      }
    }
    return srcOffset;
  }
  @SuppressWarnings("unchecked")
  private void collapseheadandtailFragmented(Object[] arr,int head,int tail,Predicate<? super E> filter)
  {
    int oldTail=tail;
    while(tail!=0)
    {
      if(!filter.test((E)arr[--tail]))
      {
        int bound=arr.length;
        int oldHead=head;
        while(++head!=bound)
        {
          if(!filter.test((E)arr[head]))
          {
            int tmp;
            if(head!=(tmp=pullUp(arr,bound,head,filter)))
            {
              arr[tmp]=arr[head];
            }
            this.head=tmp;
            OmniArray.OfRef.nullifyRange(arr,oldHead,tmp-1);
            if(tail!=(tmp=pullDown(arr,-1,tail,filter)))
            {
              arr[tmp]=arr[tail];
            }
            this.tail=tmp;
            OmniArray.OfRef.nullifyRange(arr,tmp+1,oldTail);
            return;
          }
        }
        this.tail=tail;
        OmniArray.OfRef.nullifyRange(arr,oldHead,bound-1);
        OmniArray.OfRef.nullifyRange(arr,tail+1,oldTail);
        if(-1!=(head=pullUp(arr,tail,-1,filter)))
        {
          OmniArray.OfRef.nullifyRange(arr,0,head);
        }
        this.head=head+1;
        return;
      }
    }
    tail=arr.length-1;
    OmniArray.OfRef.nullifyRange(arr,0,oldTail);
    oldTail=tail;
    for(;tail!=head;--tail)
    {
      if(!filter.test((E)arr[tail]))
      {
        int oldHead=head;
        while(++head!=tail)
        {
          if(!filter.test((E)arr[head]))
          {
            int tmp;
            if(tail!=(tmp=pullDown(arr,head,tail,filter)))
            {
              arr[tmp]=arr[tail];
              OmniArray.OfRef.nullifyRange(arr,tmp+1,oldTail);
            }
            break;
          }
        }
        OmniArray.OfRef.nullifyRange(arr,oldHead,head-1);
        this.head=head;
        this.tail=tail;
        return;
      }
    }
    OmniArray.OfRef.nullifyRange(arr,tail,oldTail);
    this.tail=-1;
  }
  @SuppressWarnings("unchecked")
  private boolean collapsebodyFragmented(Object[] arr,int head,int tail,Predicate<? super E> filter)
  {
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((E)arr[srcOffset]))
      {
        arr[srcOffset=RefArrSeq.pullSurvivorsDown(arr,srcOffset,tail-1,filter)]=arr[tail];
        this.tail=srcOffset;
        OmniArray.OfRef.nullifyRange(arr,srcOffset+1,tail);
        if(head!=(srcOffset=pullUp(arr,arr.length,head,filter)))
        {
          arr[srcOffset]=arr[head];
          this.head=srcOffset;
          OmniArray.OfRef.nullifyRange(arr,head,srcOffset-1);
        }
        return true;
      }
    }
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset)
    {
      if(filter.test((E)arr[srcOffset]))
      {
        arr[srcOffset=RefArrSeq.pullSurvivorsUp(arr,srcOffset,head+1,filter)]=arr[head];
        this.head=srcOffset;
        OmniArray.OfRef.nullifyRange(arr,head,srcOffset-1);
        return true;
      }
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  private void collapseheadandtailNonFragmented(Object[] arr,int head,int tail,Predicate<? super E> filter)
  {
    int oldHead=head;
    while(++head!=tail)
    {
      if(!filter.test((E)arr[head]))
      {
        this.head=head;
        OmniArray.OfRef.nullifyRange(arr,oldHead,head-1);
        this.tail=head=pullDown(arr,head,tail,filter)-1;
        OmniArray.OfRef.nullifyRange(arr,head+1,tail);
        return;
      }
    }
    OmniArray.OfRef.nullifyRange(arr,oldHead,tail);
    this.tail=-1;
  }
  @SuppressWarnings("unchecked")
  private boolean collapsebodyNonFragmented(Object[] arr,int head,int tail,Predicate<? super E> filter)
  {
    int oldHead=head;
    while(++head!=tail)
    {
      Object v;
      if(filter.test((E)(v=arr[head])))
      {
        arr[head=RefArrSeq.pullSurvivorsDown(arr,head,tail-1,filter)]=v;
        this.tail=head;
        OmniArray.OfRef.nullifyRange(arr,head+1,oldHead);
        return true;
      }
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  private boolean uncheckedRemoveIf1(int tail,Predicate<? super E> filter)
  {
    Object[] arr;
    if(filter.test((E)(arr=this.arr)[tail]))
    {
      arr[tail]=null;
        this.tail=-1;
        return true;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  private boolean uncheckedRemoveIfFragmented(int head,int tail,Predicate<? super E> filter)
  {
    Object[] arr;
    if(filter.test((E)(arr=this.arr)[head]))
    {
      if(filter.test((E)arr[tail]))
      {
        collapseheadandtailFragmented(arr,head,tail,filter);
      }
      else
      {
        collapseheadFragmented(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((E)arr[tail]))
    {
      collapsetailFragmented(arr,head,tail,filter);
      return true;
    }
    return collapsebodyFragmented(arr,head,tail,filter);
  }
  @SuppressWarnings("unchecked")
  boolean uncheckedRemoveIf(int tail,Predicate<? super E> filter)
  {
    int head;
    switch(Integer.signum(tail-(head=this.head)))
    {
      case 0:
        return uncheckedRemoveIf1(tail,filter);
      case -1:
        return uncheckedRemoveIfFragmented(head,tail,filter);
      default:
        Object[] arr;
        if(filter.test((E)(arr=this.arr)[head]))
        {
          if(filter.test((E)arr[tail]))
          {
            collapseheadandtailNonFragmented(arr,head,tail,filter);
          }
          else
          {
            collapseheadNonFragmented(arr,head,tail,filter);
          }
          return true;
        }
        else if(filter.test((E)arr[tail]))
        {
          collapsetailNonFragmented(arr,head,tail,filter);
          return true;
        }
        return collapsebodyNonFragmented(arr,head,tail,filter);
    }
  }
  private boolean uncheckedcontains(int tail,Predicate<Object> pred)
  {
    if(tail<head)
    {
      final Object[] arr;
      return RefArrSeq.uncheckedcontains(arr=this.arr,0,tail+1,pred)
      || RefArrSeq.uncheckedcontains(arr,head,arr.length,pred);
    }
    return RefArrSeq.uncheckedcontains(arr,head,tail+1,pred);
  }
  private int uncheckedsearch(int tail,Predicate<Object> pred)
  {
    final var arr=this.arr;
    int index=1;
    if(tail<head)
    {
      for(final int bound=arr.length;;++index)
      {
        if(pred.test(arr[head]))
        {
          return index;
        }
        if(++head==bound)
        {
          break;
        }
      }
      head=0;
    }
    for(;;++index,++head)
    {
      if(pred.test(arr[head]))
      {
        return index;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  boolean fragmentedremoveVal(int head,int tail,Predicate<Object> pred)
  {
    final Object[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=head;
    for(;;++removeIndex)
    {
      if(pred.test(arr[removeIndex]))
      {
        //0 <= tail < head <= removeIndex <= arr.length-1
        int headDist,after;
        if((headDist=removeIndex-head)<=(after=arrLength-removeIndex)+tail)
        {
          //cheaper to shift elements forward
          //also move the removeIndex up
          if(head==arrLength)
          {
            this.head=0;
          }
          else
          {
            ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
            this.head=head+1;
          }
          arr[head]=null;
        }
        else
        {
          //cheaper to shift elements down
          ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
          arr[arrLength]=arr[0];
          if(tail==0)
          {
            this.tail=arrLength;
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
            this.tail=tail-1;
          }
          arr[tail]=null;
        }
        return true;
      }
      if(removeIndex==arrLength)
      {
        removeIndex=0;
        break;
      }
    }
    for(;;++removeIndex)
    {
      if(pred.test(arr[removeIndex]))
      {
        //0 <= removeIndex <= tail < head <= arr.length-1
        int before,after;
        if((before=(arrLength)-head)+removeIndex<(after=tail-removeIndex))
        {
          ArrCopy.semicheckedCopy(arr,0,arr,1,removeIndex);
          arr[0]=arr[arrLength];
          //cheaper to move elements forward
          if(before==0)
          {
            this.head=0;
            arr[head]=null;
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,head,arr,head+1,before);
            arr[head]=null;
            this.head=head+1;
          }
        }
        else
        {
          //cheaper to move elements backward
          if(tail==0)
          {
            this.tail=arrLength;
          }
          else
          {
            this.tail=tail-1;
            ArrCopy.semicheckedCopy(arr,1,arr,0,after);
          }
          arr[tail]=null;
        }
        return true;
      }
      if(removeIndex==tail)
      {
        return false;
      }
    }
  }
  boolean nonfragmentedremoveVal(int head,int tail,Predicate<Object> pred)
  {
    final var arr=this.arr;
    for(int removeIndex=head;;++removeIndex)
    {
      if(pred.test(arr[removeIndex]))
      {
        int hDist,tDist;
        if((hDist=removeIndex-head)<=(tDist=tail-removeIndex))
        {
          if(hDist==0)
          {
            if(tDist==0)
            {
              arr[removeIndex]=null;
              this.tail=-1;
              return true;
            }
            tDist=head+1;
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,head,arr,tDist=head+1,hDist);
          }
          arr[head]=null;
          this.head=tDist;
        }
        else
        {
          ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tDist);
          arr[tail]=null;
          this.tail=tail-1;
        }
        return true;
      }
      if(removeIndex==tail)
      {
        return false;
      }
    }
  }
  private boolean uncheckedremoveVal(int tail,Predicate<Object> pred)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveVal(head,tail,pred);
    }
    return nonfragmentedremoveVal(head,tail,pred);
  }
  boolean fragmentedremoveLastOccurrence(int head,int tail,Predicate<Object> pred)
  {
    final Object[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=tail;
    for(;;--removeIndex)
    {
      if(pred.test(arr[removeIndex]))
      {
        //0 <= removeIndex <= tail < head <= arr.length-1
        int before,after;
        if((before=(arrLength)-head)+removeIndex<(after=tail-removeIndex))
        {
          ArrCopy.semicheckedCopy(arr,0,arr,1,removeIndex);
          arr[0]=arr[arrLength];
          //cheaper to move elements forward
          if(before==0)
          {
            this.head=0;
            arr[head]=null;
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,head,arr,head+1,before);
            arr[head]=null;
            this.head=head+1;
          }
        }
        else
        {
          //cheaper to move elements backward
          if(tail==0)
          {
            this.tail=arrLength;
          }
          else
          {
            this.tail=tail-1;
            ArrCopy.semicheckedCopy(arr,1,arr,0,after);
          }
          arr[tail]=null;
        }
        return true;
      }
      if(removeIndex==0)
      {
        removeIndex=arrLength;
        break;
      }
    }
    for(;;++removeIndex)
    {
      if(pred.test(arr[removeIndex]))
      {
        //0 <= tail < head <= removeIndex <= arr.length-1
        int headDist,after;
        if((headDist=removeIndex-head)<=(after=arrLength-removeIndex)+tail)
        {
          //cheaper to shift elements forward
          //also move the removeIndex up
          if(head==arrLength)
          {
            this.head=0;
          }
          else
          {
            ArrCopy.semicheckedCopy(arr,head,arr,head+1,headDist);
            this.head=head+1;
          }
          arr[head]=null;
        }
        else
        {
          //cheaper to shift elements down
          ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,after);
          arr[arrLength]=arr[0];
          if(tail==0)
          {
            this.tail=arrLength;
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,1,arr,0,tail);
            this.tail=tail-1;
          }
          arr[tail]=null;
        }
        return true;
      }
      if(removeIndex==head)
      {
        return false;
      }
    }
  }
  boolean nonfragmentedremoveLastOccurrence(int head,int tail,Predicate<Object> pred)
  {
    final var arr=this.arr;
    for(int removeIndex=tail;;--removeIndex)
    {
      if(pred.test(arr[removeIndex]))
      {
        int hDist,tDist;
        if((hDist=removeIndex-head)<=(tDist=tail-removeIndex))
        {
          if(hDist==0)
          {
            if(tDist==0)
            {
              arr[removeIndex]=null;
              this.tail=-1;
              return true;
            }
            tDist=head+1;
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,head,arr,tDist=head+1,hDist);
          }
          arr[head]=null;
          this.head=tDist;
        }
        else
        {
          ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tDist);
          arr[tail]=null;
          this.tail=tail-1;
        }
        return true;
      }
      if(removeIndex==head)
      {
        return false;
      }
    }
  }
  private boolean uncheckedremoveLastOccurrence(int tail,Predicate<Object> pred)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveLastOccurrence(head,tail,pred);
    }
    return nonfragmentedremoveLastOccurrence(head,tail,pred);
  }
}
