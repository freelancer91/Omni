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
import java.util.function.LongPredicate;
import java.util.function.LongConsumer;
import omni.impl.AbstractLongItr;
import omni.util.TypeUtil;
public class LongArrDeque implements OmniDeque.OfLong
{
  transient long[] arr;
  transient int head;
  transient int tail;
  public LongArrDeque()
  {
    this.tail=-1;
    this.arr=OmniArray.OfLong.DEFAULT_ARR;
  }
  public LongArrDeque(int capacity)
  {
    this.tail=-1;
    switch(capacity)
    {
    default:
      this.arr=new long[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfLong.DEFAULT_ARR;
    case 0:
    }
  }
  public LongArrDeque(int head,int tail,long[] arr)
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
      final long[] newArr;
      int head,size;
      if((size=tail-(head=this.head))<=0)
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new long[size+=arr.length],0,head);
        ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new long[size],0,size);
      }
      return new LongArrDeque(0,size-1,newArr);
    }
    return new LongArrDeque();
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
    final long[] arr;
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
    final long[] arr;
    int head;
    int hash=31+Long.hashCode((arr=this.arr)[head=this.head]);
    if(tail<=head)
    {
      for(int bound=arr.length;++head!=bound;hash=(hash*31)+Long.hashCode(arr[head])){}
      hash=(hash*31)+Long.hashCode(arr[head=0]);
    }
    for(;++head!=tail;hash=(hash*31)+Long.hashCode(arr[head])){}
    return hash;
  }
  @Override
  public long longElement()
  {
    return (long)arr[head];
  }
  @Override
  public long getLastLong()
  {
    return (long)arr[tail];
  }
  @Override
  public void addLast(long val)
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
          final long[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new long[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
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
              ArrCopy.uncheckedCopy(arr,0,arr=new long[OmniArray.growBy50Pct(tail)],0,tail);
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
  public void push(long val)
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
          final long[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new long[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
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
              ArrCopy.uncheckedCopy(arr,0,arr=new long[arrLength=OmniArray.growBy50Pct(tail)],arrLength-=tail,tail);
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
  public long popLong()
  {
    return uncheckedExtractFirst(tail);
  }
  @Override
  public long removeLastLong()
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
    this.tail=-1;
  }
  void uncheckedForEach(int tail,LongConsumer action)
  {
    var arr=this.arr;
    int head;
    if(tail<(head=this.head))
    {
      for(int bound=arr.length;;)
      {
        action.accept((long)arr[head]);
        if(++head==bound)
        {
          break;
        }
      }
      head=0;
    }
    for(;;++head)
    {
      action.accept((long)arr[head]);
      if(head==tail)
      {
        return;
      }
    }
  }
  @Override
  public long pollLastLong()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      long[] arr;
      final var ret=(long)((arr=this.arr)[tail]);
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
      return ret;
    }
    return Long.MIN_VALUE;
  }
  @Override
  public Long pollLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      long[] arr;
      final var ret=(Long)((arr=this.arr)[tail]);
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
      return ret;
    }
    return null;
  }
  @Override
  public double pollLastDouble()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      long[] arr;
      final var ret=(double)(double)((arr=this.arr)[tail]);
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
      return ret;
    }
    return Double.NaN;
  }
  @Override
  public float pollLastFloat()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      long[] arr;
      final var ret=(float)(float)((arr=this.arr)[tail]);
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
      return ret;
    }
    return Float.NaN;
  }
  @Override
  public long pollLong()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      long[] arr;
      final var ret=(long)((arr=this.arr)[head=this.head]);
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
      return ret;
    }
    return Long.MIN_VALUE;
  }
  @Override
  public Long poll()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      long[] arr;
      final var ret=(Long)((arr=this.arr)[head=this.head]);
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
      return ret;
    }
    return null;
  }
  @Override
  public double pollDouble()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      long[] arr;
      final var ret=(double)(double)((arr=this.arr)[head=this.head]);
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
      return ret;
    }
    return Double.NaN;
  }
  @Override
  public float pollFloat()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      long[] arr;
      final var ret=(float)(float)((arr=this.arr)[head=this.head]);
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
      return ret;
    }
    return Float.NaN;
  }
  @Override
  public OmniIterator.OfLong iterator()
  {
    return new AscendingItr(this);
  }
  @Override
  public OmniIterator.OfLong descendingIterator()
  {
    return new DescendingItr(this);
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
  @Override
  public long peekLastLong()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (long)(arr[tail]);
    }
    return Long.MIN_VALUE;
  }
  @Override
  public Long peekLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (Long)(arr[tail]);
    }
    return null;
  }
  @Override
  public double peekLastDouble()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (double)(double)(arr[tail]);
    }
    return Double.NaN;
  }
  @Override
  public float peekLastFloat()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (float)(float)(arr[tail]);
    }
    return Float.NaN;
  }
  @Override
  public long peekLong()
  {
    if(this.tail!=-1)
    {
      return (long)(arr[head]);
    }
    return Long.MIN_VALUE;
  }
  @Override
  public Long peek()
  {
    if(this.tail!=-1)
    {
      return (Long)(arr[head]);
    }
    return null;
  }
  @Override
  public double peekDouble()
  {
    if(this.tail!=-1)
    {
      return (double)(double)(arr[head]);
    }
    return Double.NaN;
  }
  @Override
  public float peekFloat()
  {
    if(this.tail!=-1)
    {
      return (float)(float)(arr[head]);
    }
    return Float.NaN;
  }
  @Override
  public void forEach(LongConsumer action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Long> action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override
  public boolean add(final long val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerLast(final long val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerFirst(final long val)
  {
    push((val));
    return true;
  }
  @Override
  public boolean offer(final long val)
  {
    addLast((val));
    return true;
  }
  @Override
  public void addFirst(final Long val)
  {
    push((long)(val));
  }
  @Override
  public void push(final Long val)
  {
    push((long)(val));
  }
  @Override
  public boolean offer(final Long val)
  {
    addLast((long)(val));
    return true;
  }
  @Override
  public boolean offerLast(final Long val)
  {
    addLast((long)(val));
    return true;
  }
  @Override
  public boolean offerFirst(final Long val)
  {
    push((long)(val));
    return true;
  }
  @Override
  public void addLast(final Long val)
  {
    addLast((long)(val));
  }
  @Override
  public boolean add(final Long val)
  {
    addLast((long)(val));
    return true;
  }
  @Override
  public boolean add(final boolean val)
  {
    addLast(TypeUtil.castToLong(val));
    return true;
  }
  @Override
  public boolean add(final int val)
  {
    addLast((long)(val));
    return true;
  }
  private void initialize(long val)
  {
    long[] arr;
    if((arr=this.arr)==OmniArray.OfLong.DEFAULT_ARR)
    {
      this.arr=arr=new long[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=new long[1];
    }
    arr[0]=val;
    this.tail=0;
    this.head=0;
  }
  private long uncheckedExtractFirst(int tail)
  {
    long[] arr;
    int head;
    var ret=(long)(arr=this.arr)[head=this.head];
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
  private long uncheckedExtractLast(int tail)
  {
    long[] arr;
    var ret=(long)(arr=this.arr)[tail];
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
  public Long peekFirst()
  {
    return (Long)peek();
  }
  @Override
  public Long pollFirst()
  {
    return (Long)poll();
  }
  @Override
  public Long getFirst()
  {
    return (Long)longElement();
  }
  @Override
  public Long removeFirst()
  {
    return (Long)popLong();
  }
  @Override
  public Long remove()
  {
    return (Long)popLong();
  }
  @Override
  public Long element()
  {
    return (Long)longElement();
  }
  @Override
  public Long getLast()
  {
    return (Long)getLastLong();
  }
  @Override
  public Long removeLast()
  {
    return (Long)removeLastLong();
  }
  @Override
  public Long pop()
  {
    return (Long)popLong();
  }
  @Override
  public boolean removeFirstOccurrence(Object val)
  {
    return remove(val);
  }
  @Override
  public long[] toLongArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final long[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new long[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new long[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override
  public boolean removeIf(LongPredicate filter)
  {
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter);
  }
  @Override
  public boolean removeIf(Predicate<? super Long> filter)
  {
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter::test);
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
  public Long[] toArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final Long[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new Long[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new Long[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfLong.DEFAULT_BOXED_ARR;
  }
  @Override
  public double[] toDoubleArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final double[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new double[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new double[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override
  public float[] toFloatArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final float[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new float[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new float[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override
  public boolean contains(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,TypeUtil.castToLong(val));
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
        return uncheckedcontains(tail,(val));
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
      return uncheckedcontains(tail,(val));
    }
    return false;
  }
  @Override
  public boolean contains(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final long v;
      if(TypeUtil.floatEquals(val,v=(long)val))
      {
        return uncheckedcontains(tail,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      final long v;
      if(val==(v=(long)val))
      {
        return uncheckedcontains(tail,v);
      }
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
        return uncheckedcontains(tail,(val));
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
        return uncheckedcontains(tail,(val));
      }
    }
    return false;
  }
  @Override
  public int search(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedsearch(tail,TypeUtil.castToLong(val));
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
        return uncheckedsearch(tail,(val));
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
      return uncheckedsearch(tail,(val));
    }
    return -1;
  }
  @Override
  public int search(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final long v;
      if(TypeUtil.floatEquals(val,v=(long)val))
      {
        return uncheckedsearch(tail,v);
      }
    }
    return -1;
  }
  @Override
  public int search(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      final long v;
      if(val==(v=(long)val))
      {
        return uncheckedsearch(tail,v);
      }
    }
    return -1;
  }
  private static class DescendingItr
    extends AbstractLongItr
    implements OmniIterator.OfLong
  {
    transient final LongArrDeque root;
    transient int nextIndex;
    DescendingItr(LongArrDeque root)
    {
      this.root=root;
      this.nextIndex=root.tail;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final LongArrDeque root;
      return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
    }
    @Override
    public long nextLong()
    {
      final long[] arr;
      int index;
      final long ret=(long)(arr=root.arr)[index=this.nextIndex];
      if(--index==-1)
      {
        index=arr.length-1;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(LongConsumer action)
    {
      final int tail,index,head;
      final LongArrDeque root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        LongArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        long[] arr;
        LongArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
        LongArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void forEachRemaining(Consumer<? super Long> action)
    {
      final int tail,index,head;
      final LongArrDeque root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        LongArrSeq.uncheckedReverseForEach(root.arr,head,index,action::accept);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        long[] arr;
        LongArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action::accept);
        LongArrSeq.uncheckedReverseForEach(arr,head,arr.length,action::accept);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void remove()
    {
      final LongArrDeque root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final long[] arr;
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
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,++head,before);
              root.head=head;
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
                root.head=0;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
                root.head=head;
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
                ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                root.head=head;
              }
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
        }
        else
        {
          //cheaper to move elements backward
          ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tailDist);
        }
      }
    }
  }
  private static class AscendingItr
    extends AbstractLongItr
    implements OmniIterator.OfLong
  {
    transient final LongArrDeque root;
    transient int nextIndex;
    AscendingItr(LongArrDeque root)
    {
      this.root=root;
      this.nextIndex=root.head;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final LongArrDeque root;
      return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
        (index>=(head=root.head) && tail<head);
    }
    @Override
    public long nextLong()
    {
      final long[] arr;
      int index;
      final long ret=(long)(arr=root.arr)[index=this.nextIndex];
      if(++index==arr.length)
      {
        index=0;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(LongConsumer action)
    {
      final int tail,index,head;
      final LongArrDeque root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        LongArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final long[] arr;
        LongArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
        LongArrSeq.uncheckedForwardForEach(arr,0,tail,action);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void forEachRemaining(Consumer<? super Long> action)
    {
      final int tail,index,head;
      final LongArrDeque root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        LongArrSeq.uncheckedForwardForEach(root.arr,index,tail,action::accept);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final long[] arr;
        LongArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action::accept);
        LongArrSeq.uncheckedForwardForEach(arr,0,tail,action::accept);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void remove()
    {
      final LongArrDeque root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final long[] arr;
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
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,++head,before);
              root.head=head;
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
                root.head=0;
              }
              else
              {
                ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
                root.head=head;
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
              ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
              root.head=head;
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
        }
        else
        {
          //cheaper to move elements backward
          this.nextIndex=removeIndex;
          ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tailDist);
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
      if(val instanceof Long)
      {
        return uncheckedcontains(tail,(long)(val));
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
      if(val instanceof Long)
      {
        return uncheckedsearch(tail,(long)(val));
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
      if(val instanceof Long)
      {
        return uncheckedremoveVal(tail,(long)(val));
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
      if(val instanceof Long)
      {
        return uncheckedremoveLastOccurrence(tail,(long)(val));
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
      return uncheckedremoveVal(tail,TypeUtil.castToLong(val));
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
        return uncheckedremoveVal(tail,(val));
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
      return uncheckedremoveVal(tail,(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final long v;
      if(TypeUtil.floatEquals(val,v=(long)val))
      {
        return uncheckedremoveVal(tail,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      final long v;
      if(val==(v=(long)val))
      {
        return uncheckedremoveVal(tail,v);
      }
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
        return uncheckedremoveVal(tail,(val));
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
        return uncheckedremoveVal(tail,(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedremoveLastOccurrence(tail,TypeUtil.castToLong(val));
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
        return uncheckedremoveLastOccurrence(tail,(val));
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
      return uncheckedremoveLastOccurrence(tail,(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final long v;
      if(TypeUtil.floatEquals(val,v=(long)val))
      {
        return uncheckedremoveLastOccurrence(tail,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      final long v;
      if(val==(v=(long)val))
      {
        return uncheckedremoveLastOccurrence(tail,v);
      }
    }
    return false;
  }
  public static class Checked extends LongArrDeque
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
    public Checked(int head,int tail,long[] arr)
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
        final long[] newArr;
        int head,size;
        if((size=tail-(head=this.head))<=0)
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new long[size+=arr.length],0,head);
          ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
        }
        else
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new long[size],0,size);
        }
        return new Checked(0,size-1,newArr);
      }
      return new Checked();
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public long longElement()
    {
      if(tail!=-1)
      {
        return (long)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override
    public long getLastLong()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        return (long)arr[tail];
      }
      throw new NoSuchElementException();
    }
    @Override
    public void addLast(long val)
    {
      ++this.modCount;
      super.addLast(val);
    }
    @Override
    public void push(long val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public long popLong()
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
    public long removeLastLong()
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
      if(tail!=-1)
      {
        ++this.modCount;
        this.tail=-1;
      }
    }
    @Override
    void uncheckedForEach(int tail,LongConsumer action)
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
    public long pollLastLong()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        long[] arr;
        final var ret=(long)((arr=this.arr)[tail]);
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
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public Long pollLast()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        long[] arr;
        final var ret=(Long)((arr=this.arr)[tail]);
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
        return ret;
      }
      return null;
    }
    @Override
    public double pollLastDouble()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        long[] arr;
        final var ret=(double)(double)((arr=this.arr)[tail]);
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
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public float pollLastFloat()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        long[] arr;
        final var ret=(float)(float)((arr=this.arr)[tail]);
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
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public long pollLong()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        long[] arr;
        final var ret=(long)((arr=this.arr)[head=this.head]);
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
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public Long poll()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        long[] arr;
        final var ret=(Long)((arr=this.arr)[head=this.head]);
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
        return ret;
      }
      return null;
    }
    @Override
    public double pollDouble()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        long[] arr;
        final var ret=(double)(double)((arr=this.arr)[head=this.head]);
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
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public float pollFloat()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        long[] arr;
        final var ret=(float)(float)((arr=this.arr)[head=this.head]);
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
        return ret;
      }
      return Float.NaN;
    }
    private static class CheckedAscendingItr
      extends AbstractLongItr
      implements OmniIterator.OfLong
    {
      transient final Checked root;
      transient int modCount;
      transient int nextIndex;
      transient int lastRet;
      CheckedAscendingItr(Checked root)
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
        final LongArrDeque root;
        return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
          (index>=(head=root.head) && tail<head);
      }
      @Override
      public long nextLong()
      {
        final Checked root;
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
        return (long)arr[index];
      }
      @Override
      public void forEachRemaining(LongConsumer action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            LongArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
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
            final long[] arr;
            LongArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
            LongArrSeq.uncheckedForwardForEach(arr,0,tail,action);
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
      public void forEachRemaining(Consumer<? super Long> action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            LongArrSeq.uncheckedForwardForEach(root.arr,index,tail,action::accept);
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
            final long[] arr;
            LongArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action::accept);
            LongArrSeq.uncheckedForwardForEach(arr,0,tail,action::accept);
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
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        int head,tail;
        if((tail=root.tail)<(head=root.head))
        {
          //fragmented
          final long[] arr;
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
                  root.head=0;
                }
                else
                {
                  ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
                  root.head=head;
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
                ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                root.head=head;
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
          }
          else
          {
            //cheaper to move elements backward
            this.nextIndex=removeIndex;
            ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tailDist);
          }
        }
      }
    }
    private static class CheckedDescendingItr
      extends AbstractLongItr
      implements OmniIterator.OfLong
    {
      transient final Checked root;
      transient int modCount;
      transient int nextIndex;
      transient int lastRet;
      CheckedDescendingItr(Checked root)
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
        final LongArrDeque root;
        return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
      }
      @Override
      public long nextLong()
      {
        final Checked root;
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
        return (long)arr[index];
      }
      @Override
      public void forEachRemaining(LongConsumer action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            LongArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
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
            long[] arr;
            LongArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
            LongArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
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
      public void forEachRemaining(Consumer<? super Long> action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            LongArrSeq.uncheckedReverseForEach(root.arr,head,index,action::accept);
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
            long[] arr;
            LongArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action::accept);
            LongArrSeq.uncheckedReverseForEach(arr,head,arr.length,action::accept);
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
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        int head,tail;
        if((tail=root.tail)<(head=root.head))
        {
          //fragmented
          final long[] arr;
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
                  root.head=0;
                }
                else
                {
                  ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
                  root.head=head;
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
                  ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                  root.head=head;
                }
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
          }
          else
          {
            //cheaper to move elements backward
            ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tailDist);
          }
        }
      }
    }
    @Override
    public OmniIterator.OfLong iterator()
    {
      return new CheckedAscendingItr(this);
    }
    @Override
    public OmniIterator.OfLong descendingIterator()
    {
      return new CheckedDescendingItr(this);
    }
    @Override
    boolean fragmentedremoveVal(int head,int tail,long val)
    {
      final long[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=head;
      for(;;++removeIndex)
      {
        if(val==(arr[removeIndex]))
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
              ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
              this.head=head;
            }
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
        if(val==(arr[removeIndex]))
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
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,++head,before);
              this.head=head;
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
    boolean nonfragmentedremoveVal(int head,int tail,long val)
    {
      final var arr=this.arr;
      for(int removeIndex=head;;++removeIndex)
      {
        if(val==(arr[removeIndex]))
        {
          ++this.modCount;
          int hDist,tDist;
          if((hDist=removeIndex-head)<=(tDist=tail-removeIndex))
          {
            if(hDist==0)
            {
              if(tDist==0)
              {
                this.tail=-1;
                return true;
              }
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,++head,hDist);
            }
            this.head=head;
          }
          else
          {
            ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tDist);
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
    boolean fragmentedremoveLastOccurrence(int head,int tail,long val)
    {
      final long[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=tail;
      for(;;--removeIndex)
      {
        if(val==(arr[removeIndex]))
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
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,++head,before);
              this.head=head;
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
        if(val==(arr[removeIndex]))
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
              ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
              this.head=head;
            }
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
    boolean nonfragmentedremoveLastOccurrence(int head,int tail,long val)
    {
      final var arr=this.arr;
      for(int removeIndex=tail;;--removeIndex)
      {
        if(val==(arr[removeIndex]))
        {
          ++this.modCount;
          int hDist,tDist;
          if((hDist=removeIndex-head)<=(tDist=tail-removeIndex))
          {
            if(hDist==0)
            {
              if(tDist==0)
              {
                this.tail=-1;
                return true;
              }
            }
            else
            {
              ArrCopy.uncheckedCopy(arr,head,arr,++head,hDist);
            }
            this.head=head;
          }
          else
          {
            ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tDist);
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
    private void collapseheadNonFragmented(int modCount,long[] arr,int head,int tail,LongPredicate filter)
    {
      int dstOffset;
      for(;;)
      {
        if(--tail==head)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          dstOffset=head;
        }
        if(filter.test((long)arr[tail]))
        {
          dstOffset=LongArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,tail,head,filter);
          break;
        }
      }
      this.head=dstOffset+1;
      this.modCount=modCount+1;
    }
    private void collapsetailNonFragmented(int modCount,long[] arr,int head,int tail,LongPredicate filter)
    {
      int dstOffset;
      for(;;)
      {
        if(++head==tail)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          dstOffset=tail;
        }
        if(filter.test((long)arr[head]))
        {
          dstOffset=LongArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter);
          break;
        }
      }
      this.tail=dstOffset-1;
      this.modCount=modCount+1;
    }
    private void collapseheadFragmentedHelper(int modCount,long[] arr,int tail,LongPredicate filter)
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
        if(filter.test((long)arr[--tail]))
        {
          break;
        }
      }
      this.head=(tail=LongArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,tail,-1,filter))+1;
    }
    private void collapsetailFragmentedHelper(int modCount,long[] arr,int head,LongPredicate filter)
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
        if(filter.test((long)arr[head]))
        {
          break;
        }
      }
      this.tail=(head=LongArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,bound,filter))-1;
    }
    private void collapseheadFragmented(int modCount,long[] arr,int head,int tail,LongPredicate filter)
    {
      for(int bound=arr.length-1;;)
      {
        if(head==bound)
        {
          collapseheadFragmentedHelper(modCount,arr,tail,filter);
          break;
        }
        if(!filter.test((long)arr[++head]))
        {
          collapseheadFragmentedHelper(modCount,arr,head,tail,filter);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    private void collapsetailFragmented(int modCount,long[] arr,int head,int tail,LongPredicate filter)
    {
      for(;;)
      {
        if(tail==0)
        {
          collapsetailFragmentedHelper(modCount,arr,head,filter);
          break;
        }
        if(!filter.test((long)arr[--tail]))
        {
          collapsetailFragmentedHelper(modCount,arr,head,tail,filter);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    private void collapseheadFragmentedHelper(int modCount,long[] arr,int head,int tail,LongPredicate filter)
    {
      for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
      {
        if(filter.test((long)arr[headOffset]))
        {
          for(int tailOffset=0;tailOffset!=tail;++tailOffset)
          {
            if(filter.test((long)arr[tailOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              return;
            }
          }
          this.head=headOffset=LongArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          arr[headOffset]=arr[head];
          return;
        }
      }
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((long)arr[tailOffset]))
        {
          this.tail=tailOffset=LongArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter);
          arr[tailOffset]=arr[tail];
          return;
        }
      }
    }
    private void collapsetailFragmentedHelper(int modCount,long[] arr,int head,int tail,LongPredicate filter)
    {
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((long)arr[tailOffset]))
        {
          for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
          {
            if(filter.test((long)arr[headOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              return;
            }
          }
          this.tail=tailOffset=LongArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter);
          arr[tailOffset]=arr[tail];
          return;
        }
      }
      for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
      {
        if(filter.test((long)arr[headOffset]))
        {
          this.head=headOffset=LongArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          arr[headOffset]=arr[head];
          return;
        }
      }
    }
    private void collapseheadandtailFragmentedHelper(int modCount,long[] arr,int head,int tail,int headOffset,int tailOffset,LongPredicate filter)
    {
      int tailDstOffset=tailOffset;
      int headDstOffset;
      outer:for(;;)
      {
        if(++tailOffset==tail)
        {
          headDstOffset=LongArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          break;
        }
        final long tailVal;
        if(!filter.test((long)(tailVal=arr[tailOffset])))
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
                numTailSurvivors=LongArrSeq.markSurvivors(arr,survivors=BitSetUtils.getBitSet(numTailSurvivors),tailOffset,tail,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[tailDstOffset++]=tailVal;
                if(numTailSurvivors!=0)
                {
                  tailDstOffset=LongArrSeq.pullSurvivorsDown(arr,survivors,tailDstOffset,tailOffset,numTailSurvivors);
                }
              }
              else
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[tailDstOffset++]=tailVal;
              }
              break outer;
            }
            final long headVal;
            if(!filter.test((long)(headVal=arr[headOffset])))
            {
              int numHeadSurvivors,numTailSurvivors;
              if((numHeadSurvivors=(--headOffset)-head)+(numTailSurvivors=tail-++tailOffset)!=0)
              {
                final long[] survivors;
                numTailSurvivors=LongArrSeq.markSurvivors(arr,survivors=new long[((numTailSurvivors-1)>>>6)+((numHeadSurvivors-1)>>>6)+2],tailOffset,tail,filter);
                numHeadSurvivors=LongArrSeq.markSurvivorsReverse(arr,survivors,headOffset,head,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[headDstOffset--]=headVal;
                if(numHeadSurvivors!=0)
                {
                  headDstOffset=LongArrSeq.pullSurvivorsUp(arr,survivors,headDstOffset,headOffset,numHeadSurvivors);
                }
                arr[tailDstOffset++]=tailVal;
                if(numTailSurvivors!=0)
                {
                  tailDstOffset=LongArrSeq.pullSurvivorsDown(arr,survivors,tailDstOffset,tailOffset,numTailSurvivors);
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
    }
    private boolean collapsebodyFragmented(int modCount,long[] arr,int head,int tail,LongPredicate filter)
    {
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((long)arr[tailOffset]))
        {
          for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
          {
            if(filter.test((long)arr[headOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              this.modCount=modCount+1;
              return true;
            }
          }
          arr[tailOffset=LongArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter)]=arr[tail];
          this.modCount=modCount+1;
          this.tail=tailOffset;
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    private void collapseheadandtailFragmented(int modCount,long[] arr,int head,int tail,LongPredicate filter)
    {
      outer:for(;;)
      {
        if(tail==0)
        {
          if((tail=arr.length-1)==head)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            this.tail=-1;
          }
          else if(filter.test((long)arr[tail=arr.length-1]))
          {
            collapseheadandtailNonFragmented(modCount,arr,head,tail,filter);
          }
          else
          {
            collapseheadNonFragmented(modCount,arr,head,tail,filter);
            this.tail=tail;
          }
          return;
        }
        final long tailVal;
        if(!filter.test((long)(tailVal=arr[--tail])))
        {
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
                if(filter.test((long)arr[head=0]))
                {
                  collapseheadNonFragmented(modCount,arr,head,tail,filter);
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
                    if(filter.test((long)arr[head]))
                    {
                      arr[tail=LongArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter)]=tailVal;
                      break;
                    }
                  }
                }
              }
              this.head=0;
              this.tail=tail;
              break outer;
            }
            final long headVal;
            if(!filter.test((long)(headVal=arr[++head])))
            {
              for(int tailOffset=0;tailOffset!=tail;++tailOffset)
              {
                if(filter.test((long)arr[tailOffset]))
                {
                  for(int headOffset=bound;headOffset!=head;--headOffset)
                  {
                    if(filter.test((long)arr[headOffset]))
                    {
                      collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
                      break outer;
                    }
                  }
                  arr[tail=LongArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter)]=tailVal;
                  break inner;
                }
              }
              for(int headOffset=bound;headOffset!=head;--headOffset)
              {
                if(filter.test((long)arr[headOffset]))
                {
                  arr[head=LongArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter)]=headVal;
                  break inner;
                }
              }
              CheckedCollection.checkModCount(modCount,this.modCount);
              break;
            }
          }
          this.head=head;
          this.tail=tail;
          break;
        }
      }
      this.modCount=modCount+1;
    }
    private void collapseheadandtailNonFragmented(int modCount,long[] arr,int head,int tail,LongPredicate filter)
    {
      for(;;)
      {
        if(++head==tail)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.tail=-1;
          break;
        }
        if(!filter.test((long)arr[head]))
        {
          collapsetailNonFragmented(modCount,arr,head,tail,filter);
          this.head=head;
          break;
        }
      }
    }
    private boolean collapsebodyNonFragmented(int modCount,long[] arr,int head,int tail,LongPredicate filter)
    {
      while(++head!=tail)
      {
        if(filter.test((long)arr[head]))
        {
          int dstOffset;
          this.tail=dstOffset=LongArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter);
          arr[dstOffset]=arr[tail];
          this.modCount=modCount+1;
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
    private boolean uncheckedRemoveIf1(int tail,LongPredicate filter)
    {
      int modCount=this.modCount;
      try
      {
        if(filter.test(arr[tail]))
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
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
    private boolean uncheckedRemoveIfFragmented(int head,int tail,LongPredicate filter)
    {
      int modCount=this.modCount;
      try
      {
        long[] arr;
        if(filter.test((long)(arr=this.arr)[head]))
        {
          if(filter.test((long)arr[tail]))
          {
            collapseheadandtailFragmented(modCount,arr,head,tail,filter);
          }
          else
          {
            collapseheadFragmented(modCount,arr,head,tail,filter);
          }
          return true;
        }
        else if(filter.test((long)arr[tail]))
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
    boolean uncheckedRemoveIf(int tail,LongPredicate filter)
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
            long[] arr;
            if(filter.test((long)(arr=this.arr)[head]))
            {
              if(filter.test((long)arr[tail]))
              {
                collapseheadandtailNonFragmented(modCount,arr,head,tail,filter);
              }
              else
              {
                collapseheadNonFragmented(modCount,arr,head,tail,filter);
              }
              return true;
            }
            else if(filter.test((long)arr[tail]))
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
  private void collapseheadNonFragmented(long[] arr,int head,int tail,
  LongPredicate filter)
  {
    while(++head!=tail)
    {
      final long v;
      if(!filter.test((long)(v=arr[head])))
      {
        if(head!=(head=pullUp(arr,tail,head,
        filter))){arr[head]=v;}
        break;
      }
    }
    this.head=head;
  }
  private void collapsetailNonFragmented(long[] arr,int head,int tail,
  LongPredicate filter)
  {
    while(--tail!=head)
    {
      final long v;
      if(!filter.test((long)(v=arr[tail])))
      {
        if(tail!=(tail=pullDown(arr,head,tail,
        filter))){arr[tail]=v;}
        break;
      }
    }
    this.tail=tail;
  }
  private void collapseheadFragmented(long[] arr,int head,int tail,LongPredicate filter)
  {
    int bound=arr.length-1;
    while(head!=bound)
    {
      if(!filter.test((long)arr[++head]))
      {
        for(int srcOffset=bound;srcOffset!=head;--srcOffset)
        {
          if(filter.test((long)arr[srcOffset]))
          {
            arr[srcOffset=LongArrSeq.pullSurvivorsUp(arr,srcOffset,head+1,filter)]=arr[head];
            this.head=srcOffset;
            break;
          }
        }
        if(tail!=(bound=pullDown(arr,-1,tail,filter)))
        {
          arr[bound]=arr[tail];
          this.tail=bound;
        }
        return;
      }
    }
    this.head=pullUp(arr,tail,-1,filter)+1;
  }
  private void collapsetailFragmented(long[] arr,int head,int tail,LongPredicate filter)
  {
    while(tail!=0)
    {
      if(!filter.test((long)arr[--tail]))
      {
        for(int srcOffset=0;srcOffset!=tail;++srcOffset)
        {
          if(filter.test((long)arr[srcOffset]))
          {
            arr[srcOffset=LongArrSeq.pullSurvivorsDown(arr,srcOffset,tail-1,filter)]=arr[tail];
            this.tail=srcOffset;
            break;
          }
        }
        int tmp;
        if(head!=(tmp=pullUp(arr,arr.length,head,filter)))
        {
          arr[tmp]=arr[head];
          this.head=tmp;
        }
        return;
      }
    }
    this.tail=pullDown(arr,head,arr.length,filter)-1;
  }
  private static  int pullDown(long[] arr,int srcOffset,int srcBound,LongPredicate filter)
  {
    while(++srcOffset!=srcBound)
    {
      if(filter.test((long)arr[srcOffset]))
      {
        return LongArrSeq.pullSurvivorsDown(arr,srcOffset,srcBound-1,filter);
      }
    }
    return srcOffset;
  }
  private static  int pullUp(long[] arr,int srcOffset,int srcBound,LongPredicate filter)
  {
    while(--srcOffset!=srcBound)
    {
      if(filter.test((long)arr[srcOffset]))
      {
        return LongArrSeq.pullSurvivorsUp(arr,srcOffset,srcBound+1,filter);
      }
    }
    return srcOffset;
  }
  private void collapseheadandtailFragmented(long[] arr,int head,int tail,LongPredicate filter)
  {
    while(tail!=0)
    {
      if(!filter.test((long)arr[--tail]))
      {
        int bound=arr.length;
        while(++head!=bound)
        {
          if(!filter.test((long)arr[head]))
          {
            int tmp;
            if(head!=(tmp=pullUp(arr,bound,head,filter)))
            {
              arr[tmp]=arr[head];
            }
            this.head=tmp;
            if(tail!=(tmp=pullDown(arr,-1,tail,filter)))
            {
              arr[tmp]=arr[tail];
            }
            this.tail=tmp;
            return;
          }
        }
        this.tail=tail;
        this.head=pullUp(arr,tail,-1,filter)+1;
        return;
      }
    }
    tail=arr.length-1;
    for(;tail!=head;--tail)
    {
      if(!filter.test((long)arr[tail]))
      {
        while(++head!=tail)
        {
          if(!filter.test((long)arr[head]))
          {
            int tmp;
            if(tail!=(tmp=pullDown(arr,head,tail,filter)))
            {
              arr[tmp]=arr[tail];
            }
            break;
          }
        }
        this.head=head;
        this.tail=tail;
        return;
      }
    }
    this.tail=-1;
  }
  private boolean collapsebodyFragmented(long[] arr,int head,int tail,LongPredicate filter)
  {
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((long)arr[srcOffset]))
      {
        arr[srcOffset=LongArrSeq.pullSurvivorsDown(arr,srcOffset,tail-1,filter)]=arr[tail];
        this.tail=srcOffset;
        if(head!=(srcOffset=pullUp(arr,arr.length,head,filter)))
        {
          arr[srcOffset]=arr[head];
          this.head=srcOffset;
        }
        return true;
      }
    }
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset)
    {
      if(filter.test((long)arr[srcOffset]))
      {
        arr[srcOffset=LongArrSeq.pullSurvivorsUp(arr,srcOffset,head+1,filter)]=arr[head];
        this.head=srcOffset;
        return true;
      }
    }
    return false;
  }
  private void collapseheadandtailNonFragmented(long[] arr,int head,int tail,LongPredicate filter)
  {
    while(++head!=tail)
    {
      if(!filter.test((long)arr[head]))
      {
        this.head=head;
        this.tail=head=pullDown(arr,head,tail,filter)-1;
        return;
      }
    }
    this.tail=-1;
  }
  private boolean collapsebodyNonFragmented(long[] arr,int head,int tail,LongPredicate filter)
  {
    while(++head!=tail)
    {
      long v;
      if(filter.test((long)(v=arr[head])))
      {
        arr[head=LongArrSeq.pullSurvivorsDown(arr,head,tail-1,filter)]=v;
        this.tail=head;
        return true;
      }
    }
    return false;
  }
  private boolean uncheckedRemoveIf1(int tail,LongPredicate filter)
  {
    if(filter.test(arr[tail]))
    {
        this.tail=-1;
        return true;
    }
    return false;
  }
  private boolean uncheckedRemoveIfFragmented(int head,int tail,LongPredicate filter)
  {
    long[] arr;
    if(filter.test((long)(arr=this.arr)[head]))
    {
      if(filter.test((long)arr[tail]))
      {
        collapseheadandtailFragmented(arr,head,tail,filter);
      }
      else
      {
        collapseheadFragmented(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((long)arr[tail]))
    {
      collapsetailFragmented(arr,head,tail,filter);
      return true;
    }
    return collapsebodyFragmented(arr,head,tail,filter);
  }
  boolean uncheckedRemoveIf(int tail,LongPredicate filter)
  {
    int head;
    switch(Integer.signum(tail-(head=this.head)))
    {
      case 0:
        return uncheckedRemoveIf1(tail,filter);
      case -1:
        return uncheckedRemoveIfFragmented(head,tail,filter);
      default:
        long[] arr;
        if(filter.test((long)(arr=this.arr)[head]))
        {
          if(filter.test((long)arr[tail]))
          {
            collapseheadandtailNonFragmented(arr,head,tail,filter);
          }
          else
          {
            collapseheadNonFragmented(arr,head,tail,filter);
          }
          return true;
        }
        else if(filter.test((long)arr[tail]))
        {
          collapsetailNonFragmented(arr,head,tail,filter);
          return true;
        }
        return collapsebodyNonFragmented(arr,head,tail,filter);
    }
  }
  private boolean uncheckedcontains(int tail,long val)
  {
    if(tail<head)
    {
      final long[] arr;
      return LongArrSeq.uncheckedcontains(arr=this.arr,0,tail+1,val)
      || LongArrSeq.uncheckedcontains(arr,head,arr.length,val);
    }
    return LongArrSeq.uncheckedcontains(arr,head,tail+1,val);
  }
  private int uncheckedsearch(int tail,long val)
  {
    final var arr=this.arr;
    int index=1;
    if(tail<head)
    {
      for(final int bound=arr.length;;++index)
      {
        if(val==(arr[head]))
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
      if(val==(arr[head]))
      {
        return index;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  boolean fragmentedremoveVal(int head,int tail,long val)
  {
    final long[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=head;
    for(;;++removeIndex)
    {
      if(val==(arr[removeIndex]))
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
            ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
            this.head=head;
          }
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
      if(val==(arr[removeIndex]))
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
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,head,arr,++head,before);
            this.head=head;
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
        }
        return true;
      }
      if(removeIndex==tail)
      {
        return false;
      }
    }
  }
  boolean nonfragmentedremoveVal(int head,int tail,long val)
  {
    final var arr=this.arr;
    for(int removeIndex=head;;++removeIndex)
    {
      if(val==(arr[removeIndex]))
      {
        int hDist,tDist;
        if((hDist=removeIndex-head)<=(tDist=tail-removeIndex))
        {
          if(hDist==0)
          {
            if(tDist==0)
            {
              this.tail=-1;
              return true;
            }
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,head,arr,++head,hDist);
          }
          this.head=head;
        }
        else
        {
          ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tDist);
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
  private boolean uncheckedremoveVal(int tail,long val)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveVal(head,tail,val);
    }
    return nonfragmentedremoveVal(head,tail,val);
  }
  boolean fragmentedremoveLastOccurrence(int head,int tail,long val)
  {
    final long[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=tail;
    for(;;--removeIndex)
    {
      if(val==(arr[removeIndex]))
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
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,head,arr,++head,before);
            this.head=head;
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
      if(val==(arr[removeIndex]))
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
            ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
            this.head=head;
          }
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
        }
        return true;
      }
      if(removeIndex==head)
      {
        return false;
      }
    }
  }
  boolean nonfragmentedremoveLastOccurrence(int head,int tail,long val)
  {
    final var arr=this.arr;
    for(int removeIndex=tail;;--removeIndex)
    {
      if(val==(arr[removeIndex]))
      {
        int hDist,tDist;
        if((hDist=removeIndex-head)<=(tDist=tail-removeIndex))
        {
          if(hDist==0)
          {
            if(tDist==0)
            {
              this.tail=-1;
              return true;
            }
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,head,arr,++head,hDist);
          }
          this.head=head;
        }
        else
        {
          ArrCopy.semicheckedCopy(arr,removeIndex+1,arr,removeIndex,tDist);
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
  private boolean uncheckedremoveLastOccurrence(int tail,long val)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveLastOccurrence(head,tail,val);
    }
    return nonfragmentedremoveLastOccurrence(head,tail,val);
  }
}
