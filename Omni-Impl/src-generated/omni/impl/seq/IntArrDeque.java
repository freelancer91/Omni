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
import java.util.function.IntPredicate;
import java.util.function.IntConsumer;
import omni.impl.AbstractIntItr;
import omni.util.TypeUtil;
public class IntArrDeque implements OmniDeque.OfInt
{
  transient int[] arr;
  transient int head;
  transient int tail;
  public IntArrDeque()
  {
    this.tail=-1;
    this.arr=OmniArray.OfInt.DEFAULT_ARR;
  }
  public IntArrDeque(int capacity)
  {
    this.tail=-1;
    switch(capacity)
    {
    default:
      this.arr=new int[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfInt.DEFAULT_ARR;
    case 0:
    }
  }
  public IntArrDeque(int head,int tail,int[] arr)
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
      final int[] newArr;
      int head,size;
      if((size=tail-(head=this.head))<=0)
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new int[size+=arr.length],0,head);
        ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new int[size],0,size);
      }
      return new IntArrDeque(0,size-1,newArr);
    }
    return new IntArrDeque();
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
    final int[] arr;
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
    final int[] arr;
    int head;
    int hash=31+((arr=this.arr)[head=this.head]);
    if(tail<=head)
    {
      for(int bound=arr.length;++head!=bound;hash=(hash*31)+(arr[head])){}
      hash=(hash*31)+(arr[head=0]);
    }
    for(;++head!=tail;hash=(hash*31)+(arr[head])){}
    return hash;
  }
  @Override
  public int intElement()
  {
    return (int)arr[head];
  }
  @Override
  public int getLastInt()
  {
    return (int)arr[tail];
  }
  @Override
  public void addLast(int val)
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
          final int[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new int[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
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
              ArrCopy.uncheckedCopy(arr,0,arr=new int[OmniArray.growBy50Pct(tail)],0,tail);
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
  public void push(int val)
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
          final int[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new int[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
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
              ArrCopy.uncheckedCopy(arr,0,arr=new int[arrLength=OmniArray.growBy50Pct(tail)],arrLength-=tail,tail);
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
  public int popInt()
  {
    return uncheckedExtractFirst(tail);
  }
  @Override
  public int removeLastInt()
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
  void uncheckedForEach(int tail,IntConsumer action)
  {
    var arr=this.arr;
    int head;
    if(tail<(head=this.head))
    {
      for(int bound=arr.length;;)
      {
        action.accept((int)arr[head]);
        if(++head==bound)
        {
          break;
        }
      }
      head=0;
    }
    for(;;++head)
    {
      action.accept((int)arr[head]);
      if(head==tail)
      {
        return;
      }
    }
  }
  @Override
  public int pollLastInt()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int[] arr;
      final var ret=(int)((arr=this.arr)[tail]);
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
    return Integer.MIN_VALUE;
  }
  @Override
  public Integer pollLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int[] arr;
      final var ret=(Integer)((arr=this.arr)[tail]);
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
      int[] arr;
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
      int[] arr;
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
  public long pollLastLong()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int[] arr;
      final var ret=(long)(long)((arr=this.arr)[tail]);
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
  public int pollInt()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      int[] arr;
      final var ret=(int)((arr=this.arr)[head=this.head]);
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
    return Integer.MIN_VALUE;
  }
  @Override
  public Integer poll()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      int[] arr;
      final var ret=(Integer)((arr=this.arr)[head=this.head]);
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
      int[] arr;
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
      int[] arr;
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
  public long pollLong()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      int[] arr;
      final var ret=(long)(long)((arr=this.arr)[head=this.head]);
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
  public OmniIterator.OfInt iterator()
  {
    return new AscendingItr(this);
  }
  @Override
  public OmniIterator.OfInt descendingIterator()
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
  public int peekLastInt()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (int)(arr[tail]);
    }
    return Integer.MIN_VALUE;
  }
  @Override
  public Integer peekLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (Integer)(arr[tail]);
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
  public long peekLastLong()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (long)(long)(arr[tail]);
    }
    return Long.MIN_VALUE;
  }
  @Override
  public int peekInt()
  {
    if(this.tail!=-1)
    {
      return (int)(arr[head]);
    }
    return Integer.MIN_VALUE;
  }
  @Override
  public Integer peek()
  {
    if(this.tail!=-1)
    {
      return (Integer)(arr[head]);
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
  public long peekLong()
  {
    if(this.tail!=-1)
    {
      return (long)(long)(arr[head]);
    }
    return Long.MIN_VALUE;
  }
  @Override
  public void forEach(IntConsumer action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Integer> action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override
  public boolean add(final int val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerLast(final int val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerFirst(final int val)
  {
    push((val));
    return true;
  }
  @Override
  public boolean offer(final int val)
  {
    addLast((val));
    return true;
  }
  @Override
  public void addFirst(final Integer val)
  {
    push((int)(val));
  }
  @Override
  public void push(final Integer val)
  {
    push((int)(val));
  }
  @Override
  public boolean offer(final Integer val)
  {
    addLast((int)(val));
    return true;
  }
  @Override
  public boolean offerLast(final Integer val)
  {
    addLast((int)(val));
    return true;
  }
  @Override
  public boolean offerFirst(final Integer val)
  {
    push((int)(val));
    return true;
  }
  @Override
  public void addLast(final Integer val)
  {
    addLast((int)(val));
  }
  @Override
  public boolean add(final Integer val)
  {
    addLast((int)(val));
    return true;
  }
  @Override
  public boolean add(final boolean val)
  {
    addLast((int)TypeUtil.castToByte(val));
    return true;
  }
  private void initialize(int val)
  {
    int[] arr;
    if((arr=this.arr)==OmniArray.OfInt.DEFAULT_ARR)
    {
      this.arr=arr=new int[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=new int[1];
    }
    arr[0]=val;
    this.tail=0;
    this.head=0;
  }
  private int uncheckedExtractFirst(int tail)
  {
    int[] arr;
    int head;
    var ret=(int)(arr=this.arr)[head=this.head];
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
  private int uncheckedExtractLast(int tail)
  {
    int[] arr;
    var ret=(int)(arr=this.arr)[tail];
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
  public Integer peekFirst()
  {
    return (Integer)peek();
  }
  @Override
  public Integer pollFirst()
  {
    return (Integer)poll();
  }
  @Override
  public Integer getFirst()
  {
    return (Integer)intElement();
  }
  @Override
  public Integer removeFirst()
  {
    return (Integer)popInt();
  }
  @Override
  public Integer remove()
  {
    return (Integer)popInt();
  }
  @Override
  public Integer element()
  {
    return (Integer)intElement();
  }
  @Override
  public Integer getLast()
  {
    return (Integer)getLastInt();
  }
  @Override
  public Integer removeLast()
  {
    return (Integer)removeLastInt();
  }
  @Override
  public Integer pop()
  {
    return (Integer)popInt();
  }
  @Override
  public boolean removeFirstOccurrence(Object val)
  {
    return remove(val);
  }
  @Override
  public int[] toIntArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final int[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new int[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new int[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override
  public boolean removeIf(IntPredicate filter)
  {
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter);
  }
  @Override
  public boolean removeIf(Predicate<? super Integer> filter)
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
  public Integer[] toArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final Integer[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new Integer[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new Integer[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfInt.DEFAULT_BOXED_ARR;
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
  public boolean contains(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,(int)TypeUtil.castToByte(val));
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
      final int v;
      if(val==(v=(int)val))
      {
        return uncheckedcontains(tail,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final int v;
      if((double)val==(double)(v=(int)val))
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
      final int v;
      if(val==(v=(int)val))
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
      return uncheckedsearch(tail,(int)TypeUtil.castToByte(val));
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
      final int v;
      if(val==(v=(int)val))
      {
        return uncheckedsearch(tail,v);
      }
    }
    return -1;
  }
  @Override
  public int search(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final int v;
      if((double)val==(double)(v=(int)val))
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
      final int v;
      if(val==(v=(int)val))
      {
        return uncheckedsearch(tail,v);
      }
    }
    return -1;
  }
  private static class DescendingItr
    extends AbstractIntItr
    implements OmniIterator.OfInt
  {
    transient final IntArrDeque root;
    transient int nextIndex;
    DescendingItr(IntArrDeque root)
    {
      this.root=root;
      this.nextIndex=root.tail;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final IntArrDeque root;
      return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
    }
    @Override
    public int nextInt()
    {
      final int[] arr;
      int index;
      final int ret=(int)(arr=root.arr)[index=this.nextIndex];
      if(--index==-1)
      {
        index=arr.length-1;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(IntConsumer action)
    {
      final int tail,index,head;
      final IntArrDeque root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        IntArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        int[] arr;
        IntArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
        IntArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void forEachRemaining(Consumer<? super Integer> action)
    {
      final int tail,index,head;
      final IntArrDeque root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        IntArrSeq.uncheckedReverseForEach(root.arr,head,index,action::accept);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        int[] arr;
        IntArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action::accept);
        IntArrSeq.uncheckedReverseForEach(arr,head,arr.length,action::accept);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void remove()
    {
      final IntArrDeque root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final int[] arr;
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
    extends AbstractIntItr
    implements OmniIterator.OfInt
  {
    transient final IntArrDeque root;
    transient int nextIndex;
    AscendingItr(IntArrDeque root)
    {
      this.root=root;
      this.nextIndex=root.head;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final IntArrDeque root;
      return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
        (index>=(head=root.head) && tail<head);
    }
    @Override
    public int nextInt()
    {
      final int[] arr;
      int index;
      final int ret=(int)(arr=root.arr)[index=this.nextIndex];
      if(++index==arr.length)
      {
        index=0;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(IntConsumer action)
    {
      final int tail,index,head;
      final IntArrDeque root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        IntArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final int[] arr;
        IntArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
        IntArrSeq.uncheckedForwardForEach(arr,0,tail,action);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void forEachRemaining(Consumer<? super Integer> action)
    {
      final int tail,index,head;
      final IntArrDeque root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        IntArrSeq.uncheckedForwardForEach(root.arr,index,tail,action::accept);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final int[] arr;
        IntArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action::accept);
        IntArrSeq.uncheckedForwardForEach(arr,0,tail,action::accept);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void remove()
    {
      final IntArrDeque root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final int[] arr;
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
      if(val instanceof Integer)
      {
        return uncheckedcontains(tail,(int)(val));
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
      if(val instanceof Integer)
      {
        return uncheckedsearch(tail,(int)(val));
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
      if(val instanceof Integer)
      {
        return uncheckedremoveVal(tail,(int)(val));
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
      if(val instanceof Integer)
      {
        return uncheckedremoveLastOccurrence(tail,(int)(val));
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
      return uncheckedremoveVal(tail,(int)TypeUtil.castToByte(val));
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
      final int v;
      if(val==(v=(int)val))
      {
        return uncheckedremoveVal(tail,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final int v;
      if((double)val==(double)(v=(int)val))
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
      final int v;
      if(val==(v=(int)val))
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
      return uncheckedremoveLastOccurrence(tail,(int)TypeUtil.castToByte(val));
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
      final int v;
      if(val==(v=(int)val))
      {
        return uncheckedremoveLastOccurrence(tail,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final float val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final int v;
      if((double)val==(double)(v=(int)val))
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
      final int v;
      if(val==(v=(int)val))
      {
        return uncheckedremoveLastOccurrence(tail,v);
      }
    }
    return false;
  }
  public static class Checked extends IntArrDeque
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
    public Checked(int head,int tail,int[] arr)
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
        final int[] newArr;
        int head,size;
        if((size=tail-(head=this.head))<=0)
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new int[size+=arr.length],0,head);
          ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
        }
        else
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new int[size],0,size);
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
    public int intElement()
    {
      if(tail!=-1)
      {
        return (int)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override
    public int getLastInt()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        return (int)arr[tail];
      }
      throw new NoSuchElementException();
    }
    @Override
    public void addLast(int val)
    {
      ++this.modCount;
      super.addLast(val);
    }
    @Override
    public void push(int val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public int popInt()
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
    public int removeLastInt()
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
    void uncheckedForEach(int tail,IntConsumer action)
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
    public int pollLastInt()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int[] arr;
        final var ret=(int)((arr=this.arr)[tail]);
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
      return Integer.MIN_VALUE;
    }
    @Override
    public Integer pollLast()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int[] arr;
        final var ret=(Integer)((arr=this.arr)[tail]);
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
        int[] arr;
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
        int[] arr;
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
    public long pollLastLong()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int[] arr;
        final var ret=(long)(long)((arr=this.arr)[tail]);
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
    public int pollInt()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        int[] arr;
        final var ret=(int)((arr=this.arr)[head=this.head]);
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
      return Integer.MIN_VALUE;
    }
    @Override
    public Integer poll()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        int[] arr;
        final var ret=(Integer)((arr=this.arr)[head=this.head]);
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
        int[] arr;
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
        int[] arr;
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
    public long pollLong()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        int[] arr;
        final var ret=(long)(long)((arr=this.arr)[head=this.head]);
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
    private static class CheckedAscendingItr
      extends AbstractIntItr
      implements OmniIterator.OfInt
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
        final IntArrDeque root;
        return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
          (index>=(head=root.head) && tail<head);
      }
      @Override
      public int nextInt()
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
        return (int)arr[index];
      }
      @Override
      public void forEachRemaining(IntConsumer action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            IntArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
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
            final int[] arr;
            IntArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
            IntArrSeq.uncheckedForwardForEach(arr,0,tail,action);
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
      public void forEachRemaining(Consumer<? super Integer> action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            IntArrSeq.uncheckedForwardForEach(root.arr,index,tail,action::accept);
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
            final int[] arr;
            IntArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action::accept);
            IntArrSeq.uncheckedForwardForEach(arr,0,tail,action::accept);
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
          final int[] arr;
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
      extends AbstractIntItr
      implements OmniIterator.OfInt
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
        final IntArrDeque root;
        return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
      }
      @Override
      public int nextInt()
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
        return (int)arr[index];
      }
      @Override
      public void forEachRemaining(IntConsumer action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            IntArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
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
            int[] arr;
            IntArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
            IntArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
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
      public void forEachRemaining(Consumer<? super Integer> action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            IntArrSeq.uncheckedReverseForEach(root.arr,head,index,action::accept);
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
            int[] arr;
            IntArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action::accept);
            IntArrSeq.uncheckedReverseForEach(arr,head,arr.length,action::accept);
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
          final int[] arr;
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
    public OmniIterator.OfInt iterator()
    {
      return new CheckedAscendingItr(this);
    }
    @Override
    public OmniIterator.OfInt descendingIterator()
    {
      return new CheckedDescendingItr(this);
    }
    @Override
    boolean fragmentedremoveVal(int head,int tail,int val)
    {
      final int[] arr;
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
    boolean nonfragmentedremoveVal(int head,int tail,int val)
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
    boolean fragmentedremoveLastOccurrence(int head,int tail,int val)
    {
      final int[] arr;
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
    boolean nonfragmentedremoveLastOccurrence(int head,int tail,int val)
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
    private void collapseheadNonFragmented(int modCount,int[] arr,int head,int tail,IntPredicate filter)
    {
      int dstOffset;
      for(;;)
      {
        if(--tail==head)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          dstOffset=head;
        }
        if(filter.test((int)arr[tail]))
        {
          dstOffset=IntArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,tail,head,filter);
          break;
        }
      }
      this.head=dstOffset+1;
      this.modCount=modCount+1;
    }
    private void collapsetailNonFragmented(int modCount,int[] arr,int head,int tail,IntPredicate filter)
    {
      int dstOffset;
      for(;;)
      {
        if(++head==tail)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          dstOffset=tail;
        }
        if(filter.test((int)arr[head]))
        {
          dstOffset=IntArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter);
          break;
        }
      }
      this.tail=dstOffset-1;
      this.modCount=modCount+1;
    }
    private void collapseheadFragmentedHelper(int modCount,int[] arr,int tail,IntPredicate filter)
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
        if(filter.test((int)arr[--tail]))
        {
          break;
        }
      }
      this.head=(tail=IntArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,tail,-1,filter))+1;
    }
    private void collapsetailFragmentedHelper(int modCount,int[] arr,int head,IntPredicate filter)
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
        if(filter.test((int)arr[head]))
        {
          break;
        }
      }
      this.tail=(head=IntArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,bound,filter))-1;
    }
    private void collapseheadFragmented(int modCount,int[] arr,int head,int tail,IntPredicate filter)
    {
      for(int bound=arr.length-1;;)
      {
        if(head==bound)
        {
          collapseheadFragmentedHelper(modCount,arr,tail,filter);
          break;
        }
        if(!filter.test((int)arr[++head]))
        {
          collapseheadFragmentedHelper(modCount,arr,head,tail,filter);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    private void collapsetailFragmented(int modCount,int[] arr,int head,int tail,IntPredicate filter)
    {
      for(;;)
      {
        if(tail==0)
        {
          collapsetailFragmentedHelper(modCount,arr,head,filter);
          break;
        }
        if(!filter.test((int)arr[--tail]))
        {
          collapsetailFragmentedHelper(modCount,arr,head,tail,filter);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    private void collapseheadFragmentedHelper(int modCount,int[] arr,int head,int tail,IntPredicate filter)
    {
      for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
      {
        if(filter.test((int)arr[headOffset]))
        {
          for(int tailOffset=0;tailOffset!=tail;++tailOffset)
          {
            if(filter.test((int)arr[tailOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              return;
            }
          }
          this.head=headOffset=IntArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          arr[headOffset]=arr[head];
          return;
        }
      }
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((int)arr[tailOffset]))
        {
          this.tail=tailOffset=IntArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter);
          arr[tailOffset]=arr[tail];
          return;
        }
      }
    }
    private void collapsetailFragmentedHelper(int modCount,int[] arr,int head,int tail,IntPredicate filter)
    {
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((int)arr[tailOffset]))
        {
          for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
          {
            if(filter.test((int)arr[headOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              return;
            }
          }
          this.tail=tailOffset=IntArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter);
          arr[tailOffset]=arr[tail];
          return;
        }
      }
      for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
      {
        if(filter.test((int)arr[headOffset]))
        {
          this.head=headOffset=IntArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          arr[headOffset]=arr[head];
          return;
        }
      }
    }
    private void collapseheadandtailFragmentedHelper(int modCount,int[] arr,int head,int tail,int headOffset,int tailOffset,IntPredicate filter)
    {
      int tailDstOffset=tailOffset;
      int headDstOffset;
      outer:for(;;)
      {
        if(++tailOffset==tail)
        {
          headDstOffset=IntArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          break;
        }
        final int tailVal;
        if(!filter.test((int)(tailVal=arr[tailOffset])))
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
                numTailSurvivors=IntArrSeq.markSurvivors(arr,survivors=BitSetUtils.getBitSet(numTailSurvivors),tailOffset,tail,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[tailDstOffset++]=tailVal;
                if(numTailSurvivors!=0)
                {
                  tailDstOffset=IntArrSeq.pullSurvivorsDown(arr,survivors,tailDstOffset,tailOffset,numTailSurvivors);
                }
              }
              else
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[tailDstOffset++]=tailVal;
              }
              break outer;
            }
            final int headVal;
            if(!filter.test((int)(headVal=arr[headOffset])))
            {
              int numHeadSurvivors,numTailSurvivors;
              if((numHeadSurvivors=(--headOffset)-head)+(numTailSurvivors=tail-++tailOffset)!=0)
              {
                final long[] survivors;
                numTailSurvivors=IntArrSeq.markSurvivors(arr,survivors=new long[((numTailSurvivors-1)>>>6)+((numHeadSurvivors-1)>>>6)+2],tailOffset,tail,filter);
                numHeadSurvivors=IntArrSeq.markSurvivorsReverse(arr,survivors,headOffset,head,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[headDstOffset--]=headVal;
                if(numHeadSurvivors!=0)
                {
                  headDstOffset=IntArrSeq.pullSurvivorsUp(arr,survivors,headDstOffset,headOffset,numHeadSurvivors);
                }
                arr[tailDstOffset++]=tailVal;
                if(numTailSurvivors!=0)
                {
                  tailDstOffset=IntArrSeq.pullSurvivorsDown(arr,survivors,tailDstOffset,tailOffset,numTailSurvivors);
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
    private boolean collapsebodyFragmented(int modCount,int[] arr,int head,int tail,IntPredicate filter)
    {
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((int)arr[tailOffset]))
        {
          for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
          {
            if(filter.test((int)arr[headOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              this.modCount=modCount+1;
              return true;
            }
          }
          arr[tailOffset=IntArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter)]=arr[tail];
          this.modCount=modCount+1;
          this.tail=tailOffset;
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    private void collapseheadandtailFragmented(int modCount,int[] arr,int head,int tail,IntPredicate filter)
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
          else if(filter.test((int)arr[tail=arr.length-1]))
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
        final int tailVal;
        if(!filter.test((int)(tailVal=arr[--tail])))
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
                if(filter.test((int)arr[head=0]))
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
                    if(filter.test((int)arr[head]))
                    {
                      arr[tail=IntArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter)]=tailVal;
                      break;
                    }
                  }
                }
              }
              this.head=0;
              this.tail=tail;
              break outer;
            }
            final int headVal;
            if(!filter.test((int)(headVal=arr[++head])))
            {
              for(int tailOffset=0;tailOffset!=tail;++tailOffset)
              {
                if(filter.test((int)arr[tailOffset]))
                {
                  for(int headOffset=bound;headOffset!=head;--headOffset)
                  {
                    if(filter.test((int)arr[headOffset]))
                    {
                      collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
                      break outer;
                    }
                  }
                  arr[tail=IntArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter)]=tailVal;
                  break inner;
                }
              }
              for(int headOffset=bound;headOffset!=head;--headOffset)
              {
                if(filter.test((int)arr[headOffset]))
                {
                  arr[head=IntArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter)]=headVal;
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
    private void collapseheadandtailNonFragmented(int modCount,int[] arr,int head,int tail,IntPredicate filter)
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
        if(!filter.test((int)arr[head]))
        {
          collapsetailNonFragmented(modCount,arr,head,tail,filter);
          this.head=head;
          break;
        }
      }
    }
    private boolean collapsebodyNonFragmented(int modCount,int[] arr,int head,int tail,IntPredicate filter)
    {
      while(++head!=tail)
      {
        if(filter.test((int)arr[head]))
        {
          int dstOffset;
          this.tail=dstOffset=IntArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter);
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
    private boolean uncheckedRemoveIf1(int tail,IntPredicate filter)
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
    private boolean uncheckedRemoveIfFragmented(int head,int tail,IntPredicate filter)
    {
      int modCount=this.modCount;
      try
      {
        int[] arr;
        if(filter.test((int)(arr=this.arr)[head]))
        {
          if(filter.test((int)arr[tail]))
          {
            collapseheadandtailFragmented(modCount,arr,head,tail,filter);
          }
          else
          {
            collapseheadFragmented(modCount,arr,head,tail,filter);
          }
          return true;
        }
        else if(filter.test((int)arr[tail]))
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
    boolean uncheckedRemoveIf(int tail,IntPredicate filter)
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
            int[] arr;
            if(filter.test((int)(arr=this.arr)[head]))
            {
              if(filter.test((int)arr[tail]))
              {
                collapseheadandtailNonFragmented(modCount,arr,head,tail,filter);
              }
              else
              {
                collapseheadNonFragmented(modCount,arr,head,tail,filter);
              }
              return true;
            }
            else if(filter.test((int)arr[tail]))
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
  private void collapseheadNonFragmented(int[] arr,int head,int tail,
  IntPredicate filter)
  {
    while(++head!=tail)
    {
      final int v;
      if(!filter.test((int)(v=arr[head])))
      {
        if(head!=(head=pullUp(arr,tail,head,
        filter))){arr[head]=v;}
        break;
      }
    }
    this.head=head;
  }
  private void collapsetailNonFragmented(int[] arr,int head,int tail,
  IntPredicate filter)
  {
    while(--tail!=head)
    {
      final int v;
      if(!filter.test((int)(v=arr[tail])))
      {
        if(tail!=(tail=pullDown(arr,head,tail,
        filter))){arr[tail]=v;}
        break;
      }
    }
    this.tail=tail;
  }
  private void collapseheadFragmented(int[] arr,int head,int tail,IntPredicate filter)
  {
    int bound=arr.length-1;
    while(head!=bound)
    {
      if(!filter.test((int)arr[++head]))
      {
        for(int srcOffset=bound;srcOffset!=head;--srcOffset)
        {
          if(filter.test((int)arr[srcOffset]))
          {
            arr[srcOffset=IntArrSeq.pullSurvivorsUp(arr,srcOffset,head+1,filter)]=arr[head];
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
  private void collapsetailFragmented(int[] arr,int head,int tail,IntPredicate filter)
  {
    while(tail!=0)
    {
      if(!filter.test((int)arr[--tail]))
      {
        for(int srcOffset=0;srcOffset!=tail;++srcOffset)
        {
          if(filter.test((int)arr[srcOffset]))
          {
            arr[srcOffset=IntArrSeq.pullSurvivorsDown(arr,srcOffset,tail-1,filter)]=arr[tail];
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
  private static  int pullDown(int[] arr,int srcOffset,int srcBound,IntPredicate filter)
  {
    while(++srcOffset!=srcBound)
    {
      if(filter.test((int)arr[srcOffset]))
      {
        return IntArrSeq.pullSurvivorsDown(arr,srcOffset,srcBound-1,filter);
      }
    }
    return srcOffset;
  }
  private static  int pullUp(int[] arr,int srcOffset,int srcBound,IntPredicate filter)
  {
    while(--srcOffset!=srcBound)
    {
      if(filter.test((int)arr[srcOffset]))
      {
        return IntArrSeq.pullSurvivorsUp(arr,srcOffset,srcBound+1,filter);
      }
    }
    return srcOffset;
  }
  private void collapseheadandtailFragmented(int[] arr,int head,int tail,IntPredicate filter)
  {
    while(tail!=0)
    {
      if(!filter.test((int)arr[--tail]))
      {
        int bound=arr.length;
        while(++head!=bound)
        {
          if(!filter.test((int)arr[head]))
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
      if(!filter.test((int)arr[tail]))
      {
        while(++head!=tail)
        {
          if(!filter.test((int)arr[head]))
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
  private boolean collapsebodyFragmented(int[] arr,int head,int tail,IntPredicate filter)
  {
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((int)arr[srcOffset]))
      {
        arr[srcOffset=IntArrSeq.pullSurvivorsDown(arr,srcOffset,tail-1,filter)]=arr[tail];
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
      if(filter.test((int)arr[srcOffset]))
      {
        arr[srcOffset=IntArrSeq.pullSurvivorsUp(arr,srcOffset,head+1,filter)]=arr[head];
        this.head=srcOffset;
        return true;
      }
    }
    return false;
  }
  private void collapseheadandtailNonFragmented(int[] arr,int head,int tail,IntPredicate filter)
  {
    while(++head!=tail)
    {
      if(!filter.test((int)arr[head]))
      {
        this.head=head;
        this.tail=head=pullDown(arr,head,tail,filter)-1;
        return;
      }
    }
    this.tail=-1;
  }
  private boolean collapsebodyNonFragmented(int[] arr,int head,int tail,IntPredicate filter)
  {
    while(++head!=tail)
    {
      int v;
      if(filter.test((int)(v=arr[head])))
      {
        arr[head=IntArrSeq.pullSurvivorsDown(arr,head,tail-1,filter)]=v;
        this.tail=head;
        return true;
      }
    }
    return false;
  }
  private boolean uncheckedRemoveIf1(int tail,IntPredicate filter)
  {
    if(filter.test(arr[tail]))
    {
        this.tail=-1;
        return true;
    }
    return false;
  }
  private boolean uncheckedRemoveIfFragmented(int head,int tail,IntPredicate filter)
  {
    int[] arr;
    if(filter.test((int)(arr=this.arr)[head]))
    {
      if(filter.test((int)arr[tail]))
      {
        collapseheadandtailFragmented(arr,head,tail,filter);
      }
      else
      {
        collapseheadFragmented(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((int)arr[tail]))
    {
      collapsetailFragmented(arr,head,tail,filter);
      return true;
    }
    return collapsebodyFragmented(arr,head,tail,filter);
  }
  boolean uncheckedRemoveIf(int tail,IntPredicate filter)
  {
    int head;
    switch(Integer.signum(tail-(head=this.head)))
    {
      case 0:
        return uncheckedRemoveIf1(tail,filter);
      case -1:
        return uncheckedRemoveIfFragmented(head,tail,filter);
      default:
        int[] arr;
        if(filter.test((int)(arr=this.arr)[head]))
        {
          if(filter.test((int)arr[tail]))
          {
            collapseheadandtailNonFragmented(arr,head,tail,filter);
          }
          else
          {
            collapseheadNonFragmented(arr,head,tail,filter);
          }
          return true;
        }
        else if(filter.test((int)arr[tail]))
        {
          collapsetailNonFragmented(arr,head,tail,filter);
          return true;
        }
        return collapsebodyNonFragmented(arr,head,tail,filter);
    }
  }
  private boolean uncheckedcontains(int tail,int val)
  {
    if(tail<head)
    {
      final int[] arr;
      return IntArrSeq.uncheckedcontains(arr=this.arr,0,tail+1,val)
      || IntArrSeq.uncheckedcontains(arr,head,arr.length,val);
    }
    return IntArrSeq.uncheckedcontains(arr,head,tail+1,val);
  }
  private int uncheckedsearch(int tail,int val)
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
  boolean fragmentedremoveVal(int head,int tail,int val)
  {
    final int[] arr;
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
  boolean nonfragmentedremoveVal(int head,int tail,int val)
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
  private boolean uncheckedremoveVal(int tail,int val)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveVal(head,tail,val);
    }
    return nonfragmentedremoveVal(head,tail,val);
  }
  boolean fragmentedremoveLastOccurrence(int head,int tail,int val)
  {
    final int[] arr;
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
  boolean nonfragmentedremoveLastOccurrence(int head,int tail,int val)
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
  private boolean uncheckedremoveLastOccurrence(int tail,int val)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveLastOccurrence(head,tail,val);
    }
    return nonfragmentedremoveLastOccurrence(head,tail,val);
  }
}
