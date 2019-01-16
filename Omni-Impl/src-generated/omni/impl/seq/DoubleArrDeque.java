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
import omni.util.HashUtils;
import java.util.function.DoublePredicate;
import java.util.function.DoubleConsumer;
import omni.impl.AbstractDoubleItr;
import omni.util.TypeUtil;
public class DoubleArrDeque implements OmniDeque.OfDouble
{
  transient double[] arr;
  transient int head;
  transient int tail;
  public DoubleArrDeque()
  {
    this.tail=-1;
    this.arr=OmniArray.OfDouble.DEFAULT_ARR;
  }
  public DoubleArrDeque(int capacity)
  {
    this.tail=-1;
    switch(capacity)
    {
    default:
      this.arr=new double[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfDouble.DEFAULT_ARR;
    case 0:
    }
  }
  public DoubleArrDeque(int head,int tail,double[] arr)
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
      final double[] newArr;
      int head,size;
      if((size=tail-(head=this.head))<=0)
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new double[size+=arr.length],0,head);
        ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new double[size],0,size);
      }
      return new DoubleArrDeque(0,size-1,newArr);
    }
    return new DoubleArrDeque();
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
    final double[] arr;
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
    final double[] arr;
    int head;
    int hash=31+HashUtils.hashDouble((arr=this.arr)[head=this.head]);
    if(tail<=head)
    {
      for(int bound=arr.length;++head!=bound;hash=(hash*31)+HashUtils.hashDouble(arr[head])){}
      hash=(hash*31)+HashUtils.hashDouble(arr[head=0]);
    }
    for(;++head!=tail;hash=(hash*31)+HashUtils.hashDouble(arr[head])){}
    return hash;
  }
  @Override
  public double doubleElement()
  {
    return (double)arr[head];
  }
  @Override
  public double getLastDouble()
  {
    return (double)arr[tail];
  }
  @Override
  public void addLast(double val)
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
          final double[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new double[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
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
              ArrCopy.uncheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(tail)],0,tail);
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
  public void push(double val)
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
          final double[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new double[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
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
              ArrCopy.uncheckedCopy(arr,0,arr=new double[arrLength=OmniArray.growBy50Pct(tail)],arrLength-=tail,tail);
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
  public double popDouble()
  {
    return uncheckedExtractFirst(tail);
  }
  @Override
  public double removeLastDouble()
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
  void uncheckedForEach(int tail,DoubleConsumer action)
  {
    var arr=this.arr;
    int head;
    if(tail<(head=this.head))
    {
      for(int bound=arr.length;;)
      {
        action.accept((double)arr[head]);
        if(++head==bound)
        {
          break;
        }
      }
      head=0;
    }
    for(;;++head)
    {
      action.accept((double)arr[head]);
      if(head==tail)
      {
        return;
      }
    }
  }
  @Override
  public double pollLastDouble()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      double[] arr;
      final var ret=(double)((arr=this.arr)[tail]);
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
  public Double pollLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      double[] arr;
      final var ret=(Double)((arr=this.arr)[tail]);
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
  public double pollDouble()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      double[] arr;
      final var ret=(double)((arr=this.arr)[head=this.head]);
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
  public Double poll()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      double[] arr;
      final var ret=(Double)((arr=this.arr)[head=this.head]);
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
  public OmniIterator.OfDouble iterator()
  {
    return new AscendingItr(this);
  }
  @Override
  public OmniIterator.OfDouble descendingIterator()
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
  private boolean uncheckedremoveLastOccurrence(int tail,double val)
  {
    if(val==val)
    {
      return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
    }
    return uncheckedremoveLastOccurrenceNaN(tail);
  }
  private boolean uncheckedremoveVal(int tail,double val)
  {
    if(val==val)
    {
      return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
    }
    return uncheckedremoveValNaN(tail);
  }
  @Override
  public double peekLastDouble()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (double)(arr[tail]);
    }
    return Double.NaN;
  }
  @Override
  public Double peekLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (Double)(arr[tail]);
    }
    return null;
  }
  @Override
  public double peekDouble()
  {
    if(this.tail!=-1)
    {
      return (double)(arr[head]);
    }
    return Double.NaN;
  }
  @Override
  public Double peek()
  {
    if(this.tail!=-1)
    {
      return (Double)(arr[head]);
    }
    return null;
  }
  @Override
  public void forEach(DoubleConsumer action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Double> action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override
  public boolean add(final double val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerLast(final double val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerFirst(final double val)
  {
    push((val));
    return true;
  }
  @Override
  public boolean offer(final double val)
  {
    addLast((val));
    return true;
  }
  @Override
  public void addFirst(final Double val)
  {
    push((double)(val));
  }
  @Override
  public void push(final Double val)
  {
    push((double)(val));
  }
  @Override
  public boolean offer(final Double val)
  {
    addLast((double)(val));
    return true;
  }
  @Override
  public boolean offerLast(final Double val)
  {
    addLast((double)(val));
    return true;
  }
  @Override
  public boolean offerFirst(final Double val)
  {
    push((double)(val));
    return true;
  }
  @Override
  public void addLast(final Double val)
  {
    addLast((double)(val));
  }
  @Override
  public boolean add(final Double val)
  {
    addLast((double)(val));
    return true;
  }
  @Override
  public boolean add(final boolean val)
  {
    addLast(TypeUtil.castToDouble(val));
    return true;
  }
  @Override
  public boolean add(final int val)
  {
    addLast((double)(val));
    return true;
  }
  @Override
  public boolean add(final char val)
  {
    addLast((double)(val));
    return true;
  }
  @Override
  public boolean add(final short val)
  {
    addLast((double)(val));
    return true;
  }
  @Override
  public boolean add(final long val)
  {
    addLast((double)(val));
    return true;
  }
  @Override
  public boolean add(final float val)
  {
    addLast((double)(val));
    return true;
  }
  private void initialize(double val)
  {
    double[] arr;
    if((arr=this.arr)==OmniArray.OfDouble.DEFAULT_ARR)
    {
      this.arr=arr=new double[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=new double[1];
    }
    arr[0]=val;
    this.tail=0;
    this.head=0;
  }
  private double uncheckedExtractFirst(int tail)
  {
    double[] arr;
    int head;
    var ret=(double)(arr=this.arr)[head=this.head];
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
  private double uncheckedExtractLast(int tail)
  {
    double[] arr;
    var ret=(double)(arr=this.arr)[tail];
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
  public Double peekFirst()
  {
    return (Double)peek();
  }
  @Override
  public Double pollFirst()
  {
    return (Double)poll();
  }
  @Override
  public Double getFirst()
  {
    return (Double)doubleElement();
  }
  @Override
  public Double removeFirst()
  {
    return (Double)popDouble();
  }
  @Override
  public Double remove()
  {
    return (Double)popDouble();
  }
  @Override
  public Double element()
  {
    return (Double)doubleElement();
  }
  @Override
  public Double getLast()
  {
    return (Double)getLastDouble();
  }
  @Override
  public Double removeLast()
  {
    return (Double)removeLastDouble();
  }
  @Override
  public Double pop()
  {
    return (Double)popDouble();
  }
  @Override
  public boolean removeFirstOccurrence(Object val)
  {
    return remove(val);
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
  public boolean removeIf(DoublePredicate filter)
  {
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter);
  }
  @Override
  public boolean removeIf(Predicate<? super Double> filter)
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
  public Double[] toArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final Double[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new Double[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new Double[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
  @Override
  public boolean contains(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      if(val)
      {
        return uncheckedcontainsBits(tail,TypeUtil.DBL_TRUE_BITS);
      }
      return uncheckedcontains0(tail);
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      if(val!=0)
      {
        {
          return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return uncheckedcontains0(tail);
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
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return uncheckedcontains0(tail);
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
      if(val==val)
      {
        return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
      }
      return uncheckedcontainsNaN(tail);
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      return uncheckedcontains(tail,(val));
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
        if(val!=0)
        {
          return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontains0(tail);
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
        if(val!=0)
        {
          return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontains0(tail);
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
        if(val!=0)
        {
          return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontains0(tail);
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
      if(val)
      {
        return uncheckedsearchBits(tail,TypeUtil.DBL_TRUE_BITS);
      }
      return uncheckedsearch0(tail);
    }
    return -1;
  }
  @Override
  public int search(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      if(val!=0)
      {
        {
          return uncheckedsearchBits(tail,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return uncheckedsearch0(tail);
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
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return uncheckedsearchBits(tail,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return uncheckedsearch0(tail);
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
      if(val==val)
      {
        return uncheckedsearchBits(tail,Double.doubleToRawLongBits(val));
      }
      return uncheckedsearchNaN(tail);
    }
    return -1;
  }
  @Override
  public int search(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      return uncheckedsearch(tail,(val));
    }
    return -1;
  }
  private boolean uncheckedcontains(int tail,double val)
  {
    if(val==val)
    {
      return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
    }
    return uncheckedcontainsNaN(tail);
  }
  private int uncheckedsearch(int tail,double val)
  {
    if(val==val)
    {
      return uncheckedsearchBits(tail,Double.doubleToRawLongBits(val));
    }
    return uncheckedsearchNaN(tail);
  }
  private static class DescendingItr
    extends AbstractDoubleItr
    implements OmniIterator.OfDouble
  {
    transient final DoubleArrDeque root;
    transient int nextIndex;
    DescendingItr(DoubleArrDeque root)
    {
      this.root=root;
      this.nextIndex=root.tail;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final DoubleArrDeque root;
      return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
    }
    @Override
    public double nextDouble()
    {
      final double[] arr;
      int index;
      final double ret=(double)(arr=root.arr)[index=this.nextIndex];
      if(--index==-1)
      {
        index=arr.length-1;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(DoubleConsumer action)
    {
      final int tail,index,head;
      final DoubleArrDeque root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        DoubleArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        double[] arr;
        DoubleArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
        DoubleArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void forEachRemaining(Consumer<? super Double> action)
    {
      final int tail,index,head;
      final DoubleArrDeque root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        DoubleArrSeq.uncheckedReverseForEach(root.arr,head,index,action::accept);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        double[] arr;
        DoubleArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action::accept);
        DoubleArrSeq.uncheckedReverseForEach(arr,head,arr.length,action::accept);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void remove()
    {
      final DoubleArrDeque root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final double[] arr;
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
    extends AbstractDoubleItr
    implements OmniIterator.OfDouble
  {
    transient final DoubleArrDeque root;
    transient int nextIndex;
    AscendingItr(DoubleArrDeque root)
    {
      this.root=root;
      this.nextIndex=root.head;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final DoubleArrDeque root;
      return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
        (index>=(head=root.head) && tail<head);
    }
    @Override
    public double nextDouble()
    {
      final double[] arr;
      int index;
      final double ret=(double)(arr=root.arr)[index=this.nextIndex];
      if(++index==arr.length)
      {
        index=0;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(DoubleConsumer action)
    {
      final int tail,index,head;
      final DoubleArrDeque root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        DoubleArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final double[] arr;
        DoubleArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
        DoubleArrSeq.uncheckedForwardForEach(arr,0,tail,action);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void forEachRemaining(Consumer<? super Double> action)
    {
      final int tail,index,head;
      final DoubleArrDeque root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        DoubleArrSeq.uncheckedForwardForEach(root.arr,index,tail,action::accept);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final double[] arr;
        DoubleArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action::accept);
        DoubleArrSeq.uncheckedForwardForEach(arr,0,tail,action::accept);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void remove()
    {
      final DoubleArrDeque root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final double[] arr;
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
      if(val instanceof Double)
      {
        return uncheckedcontains(tail,(double)(val));
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
      if(val instanceof Double)
      {
        return uncheckedsearch(tail,(double)(val));
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
      if(val instanceof Double)
      {
        return uncheckedremoveVal(tail,(double)(val));
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
      if(val instanceof Double)
      {
        return uncheckedremoveLastOccurrence(tail,(double)(val));
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
      if(val)
      {
        return uncheckedremoveValBits(tail,TypeUtil.DBL_TRUE_BITS);
      }
      return uncheckedremoveVal0(tail);
    }
    return false;
  }
  @Override
  public boolean removeVal(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      if(val!=0)
      {
        {
          return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return uncheckedremoveVal0(tail);
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
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return uncheckedremoveVal0(tail);
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
      if(val==val)
      {
        return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
      }
      return uncheckedremoveValNaN(tail);
    }
    return false;
  }
  @Override
  public boolean removeVal(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      return uncheckedremoveVal(tail,(val));
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
        if(val!=0)
        {
          return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveVal0(tail);
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
        if(val!=0)
        {
          return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveVal0(tail);
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
        if(val!=0)
        {
          return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveVal0(tail);
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
      if(val)
      {
        return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.DBL_TRUE_BITS);
      }
      return uncheckedremoveLastOccurrence0(tail);
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      if(val!=0)
      {
        {
          return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return uncheckedremoveLastOccurrence0(tail);
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
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return uncheckedremoveLastOccurrence0(tail);
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
      if(val==val)
      {
        return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
      }
      return uncheckedremoveLastOccurrenceNaN(tail);
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      return uncheckedremoveLastOccurrence(tail,(val));
    }
    return false;
  }
  public static class Checked extends DoubleArrDeque
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
    public Checked(int head,int tail,double[] arr)
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
        final double[] newArr;
        int head,size;
        if((size=tail-(head=this.head))<=0)
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new double[size+=arr.length],0,head);
          ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
        }
        else
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new double[size],0,size);
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
    public double doubleElement()
    {
      if(tail!=-1)
      {
        return (double)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override
    public double getLastDouble()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        return (double)arr[tail];
      }
      throw new NoSuchElementException();
    }
    @Override
    public void addLast(double val)
    {
      ++this.modCount;
      super.addLast(val);
    }
    @Override
    public void push(double val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public double popDouble()
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
    public double removeLastDouble()
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
    void uncheckedForEach(int tail,DoubleConsumer action)
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
    public double pollLastDouble()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        double[] arr;
        final var ret=(double)((arr=this.arr)[tail]);
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
    public Double pollLast()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        double[] arr;
        final var ret=(Double)((arr=this.arr)[tail]);
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
    public double pollDouble()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        double[] arr;
        final var ret=(double)((arr=this.arr)[head=this.head]);
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
    public Double poll()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        double[] arr;
        final var ret=(Double)((arr=this.arr)[head=this.head]);
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
    private static class CheckedAscendingItr
      extends AbstractDoubleItr
      implements OmniIterator.OfDouble
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
        final DoubleArrDeque root;
        return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
          (index>=(head=root.head) && tail<head);
      }
      @Override
      public double nextDouble()
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
        return (double)arr[index];
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            DoubleArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
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
            final double[] arr;
            DoubleArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
            DoubleArrSeq.uncheckedForwardForEach(arr,0,tail,action);
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
      public void forEachRemaining(Consumer<? super Double> action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            DoubleArrSeq.uncheckedForwardForEach(root.arr,index,tail,action::accept);
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
            final double[] arr;
            DoubleArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action::accept);
            DoubleArrSeq.uncheckedForwardForEach(arr,0,tail,action::accept);
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
          final double[] arr;
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
      extends AbstractDoubleItr
      implements OmniIterator.OfDouble
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
        final DoubleArrDeque root;
        return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
      }
      @Override
      public double nextDouble()
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
        return (double)arr[index];
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            DoubleArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
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
            double[] arr;
            DoubleArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
            DoubleArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
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
      public void forEachRemaining(Consumer<? super Double> action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            DoubleArrSeq.uncheckedReverseForEach(root.arr,head,index,action::accept);
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
            double[] arr;
            DoubleArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action::accept);
            DoubleArrSeq.uncheckedReverseForEach(arr,head,arr.length,action::accept);
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
          final double[] arr;
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
    public OmniIterator.OfDouble iterator()
    {
      return new CheckedAscendingItr(this);
    }
    @Override
    public OmniIterator.OfDouble descendingIterator()
    {
      return new CheckedDescendingItr(this);
    }
    @Override
    boolean fragmentedremoveValBits(int head,int tail,long bits)
    {
      final double[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=head;
      for(;;++removeIndex)
      {
        if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
        if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
    boolean nonfragmentedremoveValBits(int head,int tail,long bits)
    {
      final var arr=this.arr;
      for(int removeIndex=head;;++removeIndex)
      {
        if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
    boolean fragmentedremoveVal0(int head,int tail)
    {
      final double[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=head;
      for(;;++removeIndex)
      {
        if(0==(arr[removeIndex]))
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
        if(0==(arr[removeIndex]))
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
    boolean nonfragmentedremoveVal0(int head,int tail)
    {
      final var arr=this.arr;
      for(int removeIndex=head;;++removeIndex)
      {
        if(0==(arr[removeIndex]))
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
    boolean fragmentedremoveValNaN(int head,int tail)
    {
      final double[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=head;
      for(;;++removeIndex)
      {
        if(Double.isNaN(arr[removeIndex]))
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
        if(Double.isNaN(arr[removeIndex]))
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
    boolean nonfragmentedremoveValNaN(int head,int tail)
    {
      final var arr=this.arr;
      for(int removeIndex=head;;++removeIndex)
      {
        if(Double.isNaN(arr[removeIndex]))
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
    boolean fragmentedremoveLastOccurrenceBits(int head,int tail,long bits)
    {
      final double[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=tail;
      for(;;--removeIndex)
      {
        if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
        if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
    boolean nonfragmentedremoveLastOccurrenceBits(int head,int tail,long bits)
    {
      final var arr=this.arr;
      for(int removeIndex=tail;;--removeIndex)
      {
        if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
    @Override
    boolean fragmentedremoveLastOccurrence0(int head,int tail)
    {
      final double[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=tail;
      for(;;--removeIndex)
      {
        if(0==(arr[removeIndex]))
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
        if(0==(arr[removeIndex]))
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
    boolean nonfragmentedremoveLastOccurrence0(int head,int tail)
    {
      final var arr=this.arr;
      for(int removeIndex=tail;;--removeIndex)
      {
        if(0==(arr[removeIndex]))
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
    @Override
    boolean fragmentedremoveLastOccurrenceNaN(int head,int tail)
    {
      final double[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=tail;
      for(;;--removeIndex)
      {
        if(Double.isNaN(arr[removeIndex]))
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
        if(Double.isNaN(arr[removeIndex]))
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
    boolean nonfragmentedremoveLastOccurrenceNaN(int head,int tail)
    {
      final var arr=this.arr;
      for(int removeIndex=tail;;--removeIndex)
      {
        if(Double.isNaN(arr[removeIndex]))
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
    private void collapseheadNonFragmented(int modCount,double[] arr,int head,int tail,DoublePredicate filter)
    {
      int dstOffset;
      for(;;)
      {
        if(--tail==head)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          dstOffset=head;
        }
        if(filter.test((double)arr[tail]))
        {
          dstOffset=DoubleArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,tail,head,filter);
          break;
        }
      }
      this.head=dstOffset+1;
      this.modCount=modCount+1;
    }
    private void collapsetailNonFragmented(int modCount,double[] arr,int head,int tail,DoublePredicate filter)
    {
      int dstOffset;
      for(;;)
      {
        if(++head==tail)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          dstOffset=tail;
        }
        if(filter.test((double)arr[head]))
        {
          dstOffset=DoubleArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter);
          break;
        }
      }
      this.tail=dstOffset-1;
      this.modCount=modCount+1;
    }
    private void collapseheadFragmentedHelper(int modCount,double[] arr,int tail,DoublePredicate filter)
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
        if(filter.test((double)arr[--tail]))
        {
          break;
        }
      }
      this.head=(tail=DoubleArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,tail,-1,filter))+1;
    }
    private void collapsetailFragmentedHelper(int modCount,double[] arr,int head,DoublePredicate filter)
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
        if(filter.test((double)arr[head]))
        {
          break;
        }
      }
      this.tail=(head=DoubleArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,bound,filter))-1;
    }
    private void collapseheadFragmented(int modCount,double[] arr,int head,int tail,DoublePredicate filter)
    {
      for(int bound=arr.length-1;;)
      {
        if(head==bound)
        {
          collapseheadFragmentedHelper(modCount,arr,tail,filter);
          break;
        }
        if(!filter.test((double)arr[++head]))
        {
          collapseheadFragmentedHelper(modCount,arr,head,tail,filter);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    private void collapsetailFragmented(int modCount,double[] arr,int head,int tail,DoublePredicate filter)
    {
      for(;;)
      {
        if(tail==0)
        {
          collapsetailFragmentedHelper(modCount,arr,head,filter);
          break;
        }
        if(!filter.test((double)arr[--tail]))
        {
          collapsetailFragmentedHelper(modCount,arr,head,tail,filter);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    private void collapseheadFragmentedHelper(int modCount,double[] arr,int head,int tail,DoublePredicate filter)
    {
      for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
      {
        if(filter.test((double)arr[headOffset]))
        {
          for(int tailOffset=0;tailOffset!=tail;++tailOffset)
          {
            if(filter.test((double)arr[tailOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              return;
            }
          }
          this.head=headOffset=DoubleArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          arr[headOffset]=arr[head];
          return;
        }
      }
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((double)arr[tailOffset]))
        {
          this.tail=tailOffset=DoubleArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter);
          arr[tailOffset]=arr[tail];
          return;
        }
      }
    }
    private void collapsetailFragmentedHelper(int modCount,double[] arr,int head,int tail,DoublePredicate filter)
    {
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((double)arr[tailOffset]))
        {
          for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
          {
            if(filter.test((double)arr[headOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              return;
            }
          }
          this.tail=tailOffset=DoubleArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter);
          arr[tailOffset]=arr[tail];
          return;
        }
      }
      for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
      {
        if(filter.test((double)arr[headOffset]))
        {
          this.head=headOffset=DoubleArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          arr[headOffset]=arr[head];
          return;
        }
      }
    }
    private void collapseheadandtailFragmentedHelper(int modCount,double[] arr,int head,int tail,int headOffset,int tailOffset,DoublePredicate filter)
    {
      int tailDstOffset=tailOffset;
      int headDstOffset;
      outer:for(;;)
      {
        if(++tailOffset==tail)
        {
          headDstOffset=DoubleArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          break;
        }
        final double tailVal;
        if(!filter.test((double)(tailVal=arr[tailOffset])))
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
                numTailSurvivors=DoubleArrSeq.markSurvivors(arr,survivors=BitSetUtils.getBitSet(numTailSurvivors),tailOffset,tail,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[tailDstOffset++]=tailVal;
                if(numTailSurvivors!=0)
                {
                  tailDstOffset=DoubleArrSeq.pullSurvivorsDown(arr,survivors,tailDstOffset,tailOffset,numTailSurvivors);
                }
              }
              else
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[tailDstOffset++]=tailVal;
              }
              break outer;
            }
            final double headVal;
            if(!filter.test((double)(headVal=arr[headOffset])))
            {
              int numHeadSurvivors,numTailSurvivors;
              if((numHeadSurvivors=(--headOffset)-head)+(numTailSurvivors=tail-++tailOffset)!=0)
              {
                final long[] survivors;
                numTailSurvivors=DoubleArrSeq.markSurvivors(arr,survivors=new long[((numTailSurvivors-1)>>>6)+((numHeadSurvivors-1)>>>6)+2],tailOffset,tail,filter);
                numHeadSurvivors=DoubleArrSeq.markSurvivorsReverse(arr,survivors,headOffset,head,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[headDstOffset--]=headVal;
                if(numHeadSurvivors!=0)
                {
                  headDstOffset=DoubleArrSeq.pullSurvivorsUp(arr,survivors,headDstOffset,headOffset,numHeadSurvivors);
                }
                arr[tailDstOffset++]=tailVal;
                if(numTailSurvivors!=0)
                {
                  tailDstOffset=DoubleArrSeq.pullSurvivorsDown(arr,survivors,tailDstOffset,tailOffset,numTailSurvivors);
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
    private boolean collapsebodyFragmented(int modCount,double[] arr,int head,int tail,DoublePredicate filter)
    {
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((double)arr[tailOffset]))
        {
          for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
          {
            if(filter.test((double)arr[headOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              this.modCount=modCount+1;
              return true;
            }
          }
          arr[tailOffset=DoubleArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter)]=arr[tail];
          this.modCount=modCount+1;
          this.tail=tailOffset;
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    private void collapseheadandtailFragmented(int modCount,double[] arr,int head,int tail,DoublePredicate filter)
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
          else if(filter.test((double)arr[tail=arr.length-1]))
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
        final double tailVal;
        if(!filter.test((double)(tailVal=arr[--tail])))
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
                if(filter.test((double)arr[head=0]))
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
                    if(filter.test((double)arr[head]))
                    {
                      arr[tail=DoubleArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter)]=tailVal;
                      break;
                    }
                  }
                }
              }
              this.head=0;
              this.tail=tail;
              break outer;
            }
            final double headVal;
            if(!filter.test((double)(headVal=arr[++head])))
            {
              for(int tailOffset=0;tailOffset!=tail;++tailOffset)
              {
                if(filter.test((double)arr[tailOffset]))
                {
                  for(int headOffset=bound;headOffset!=head;--headOffset)
                  {
                    if(filter.test((double)arr[headOffset]))
                    {
                      collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
                      break outer;
                    }
                  }
                  arr[tail=DoubleArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter)]=tailVal;
                  break inner;
                }
              }
              for(int headOffset=bound;headOffset!=head;--headOffset)
              {
                if(filter.test((double)arr[headOffset]))
                {
                  arr[head=DoubleArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter)]=headVal;
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
    private void collapseheadandtailNonFragmented(int modCount,double[] arr,int head,int tail,DoublePredicate filter)
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
        if(!filter.test((double)arr[head]))
        {
          collapsetailNonFragmented(modCount,arr,head,tail,filter);
          this.head=head;
          break;
        }
      }
    }
    private boolean collapsebodyNonFragmented(int modCount,double[] arr,int head,int tail,DoublePredicate filter)
    {
      while(++head!=tail)
      {
        if(filter.test((double)arr[head]))
        {
          int dstOffset;
          this.tail=dstOffset=DoubleArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter);
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
    private boolean uncheckedRemoveIf1(int tail,DoublePredicate filter)
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
    private boolean uncheckedRemoveIfFragmented(int head,int tail,DoublePredicate filter)
    {
      int modCount=this.modCount;
      try
      {
        double[] arr;
        if(filter.test((double)(arr=this.arr)[head]))
        {
          if(filter.test((double)arr[tail]))
          {
            collapseheadandtailFragmented(modCount,arr,head,tail,filter);
          }
          else
          {
            collapseheadFragmented(modCount,arr,head,tail,filter);
          }
          return true;
        }
        else if(filter.test((double)arr[tail]))
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
    boolean uncheckedRemoveIf(int tail,DoublePredicate filter)
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
            double[] arr;
            if(filter.test((double)(arr=this.arr)[head]))
            {
              if(filter.test((double)arr[tail]))
              {
                collapseheadandtailNonFragmented(modCount,arr,head,tail,filter);
              }
              else
              {
                collapseheadNonFragmented(modCount,arr,head,tail,filter);
              }
              return true;
            }
            else if(filter.test((double)arr[tail]))
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
  private void collapseheadNonFragmented(double[] arr,int head,int tail,
  DoublePredicate filter)
  {
    while(++head!=tail)
    {
      final double v;
      if(!filter.test((double)(v=arr[head])))
      {
        if(head!=(head=pullUp(arr,tail,head,
        filter))){arr[head]=v;}
        break;
      }
    }
    this.head=head;
  }
  private void collapsetailNonFragmented(double[] arr,int head,int tail,
  DoublePredicate filter)
  {
    while(--tail!=head)
    {
      final double v;
      if(!filter.test((double)(v=arr[tail])))
      {
        if(tail!=(tail=pullDown(arr,head,tail,
        filter))){arr[tail]=v;}
        break;
      }
    }
    this.tail=tail;
  }
  private void collapseheadFragmented(double[] arr,int head,int tail,DoublePredicate filter)
  {
    int bound=arr.length-1;
    while(head!=bound)
    {
      if(!filter.test((double)arr[++head]))
      {
        for(int srcOffset=bound;srcOffset!=head;--srcOffset)
        {
          if(filter.test((double)arr[srcOffset]))
          {
            arr[srcOffset=DoubleArrSeq.pullSurvivorsUp(arr,srcOffset,head+1,filter)]=arr[head];
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
  private void collapsetailFragmented(double[] arr,int head,int tail,DoublePredicate filter)
  {
    while(tail!=0)
    {
      if(!filter.test((double)arr[--tail]))
      {
        for(int srcOffset=0;srcOffset!=tail;++srcOffset)
        {
          if(filter.test((double)arr[srcOffset]))
          {
            arr[srcOffset=DoubleArrSeq.pullSurvivorsDown(arr,srcOffset,tail-1,filter)]=arr[tail];
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
  private static  int pullDown(double[] arr,int srcOffset,int srcBound,DoublePredicate filter)
  {
    while(++srcOffset!=srcBound)
    {
      if(filter.test((double)arr[srcOffset]))
      {
        return DoubleArrSeq.pullSurvivorsDown(arr,srcOffset,srcBound-1,filter);
      }
    }
    return srcOffset;
  }
  private static  int pullUp(double[] arr,int srcOffset,int srcBound,DoublePredicate filter)
  {
    while(--srcOffset!=srcBound)
    {
      if(filter.test((double)arr[srcOffset]))
      {
        return DoubleArrSeq.pullSurvivorsUp(arr,srcOffset,srcBound+1,filter);
      }
    }
    return srcOffset;
  }
  private void collapseheadandtailFragmented(double[] arr,int head,int tail,DoublePredicate filter)
  {
    while(tail!=0)
    {
      if(!filter.test((double)arr[--tail]))
      {
        int bound=arr.length;
        while(++head!=bound)
        {
          if(!filter.test((double)arr[head]))
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
      if(!filter.test((double)arr[tail]))
      {
        while(++head!=tail)
        {
          if(!filter.test((double)arr[head]))
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
  private boolean collapsebodyFragmented(double[] arr,int head,int tail,DoublePredicate filter)
  {
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((double)arr[srcOffset]))
      {
        arr[srcOffset=DoubleArrSeq.pullSurvivorsDown(arr,srcOffset,tail-1,filter)]=arr[tail];
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
      if(filter.test((double)arr[srcOffset]))
      {
        arr[srcOffset=DoubleArrSeq.pullSurvivorsUp(arr,srcOffset,head+1,filter)]=arr[head];
        this.head=srcOffset;
        return true;
      }
    }
    return false;
  }
  private void collapseheadandtailNonFragmented(double[] arr,int head,int tail,DoublePredicate filter)
  {
    while(++head!=tail)
    {
      if(!filter.test((double)arr[head]))
      {
        this.head=head;
        this.tail=head=pullDown(arr,head,tail,filter)-1;
        return;
      }
    }
    this.tail=-1;
  }
  private boolean collapsebodyNonFragmented(double[] arr,int head,int tail,DoublePredicate filter)
  {
    while(++head!=tail)
    {
      double v;
      if(filter.test((double)(v=arr[head])))
      {
        arr[head=DoubleArrSeq.pullSurvivorsDown(arr,head,tail-1,filter)]=v;
        this.tail=head;
        return true;
      }
    }
    return false;
  }
  private boolean uncheckedRemoveIf1(int tail,DoublePredicate filter)
  {
    if(filter.test(arr[tail]))
    {
        this.tail=-1;
        return true;
    }
    return false;
  }
  private boolean uncheckedRemoveIfFragmented(int head,int tail,DoublePredicate filter)
  {
    double[] arr;
    if(filter.test((double)(arr=this.arr)[head]))
    {
      if(filter.test((double)arr[tail]))
      {
        collapseheadandtailFragmented(arr,head,tail,filter);
      }
      else
      {
        collapseheadFragmented(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((double)arr[tail]))
    {
      collapsetailFragmented(arr,head,tail,filter);
      return true;
    }
    return collapsebodyFragmented(arr,head,tail,filter);
  }
  boolean uncheckedRemoveIf(int tail,DoublePredicate filter)
  {
    int head;
    switch(Integer.signum(tail-(head=this.head)))
    {
      case 0:
        return uncheckedRemoveIf1(tail,filter);
      case -1:
        return uncheckedRemoveIfFragmented(head,tail,filter);
      default:
        double[] arr;
        if(filter.test((double)(arr=this.arr)[head]))
        {
          if(filter.test((double)arr[tail]))
          {
            collapseheadandtailNonFragmented(arr,head,tail,filter);
          }
          else
          {
            collapseheadNonFragmented(arr,head,tail,filter);
          }
          return true;
        }
        else if(filter.test((double)arr[tail]))
        {
          collapsetailNonFragmented(arr,head,tail,filter);
          return true;
        }
        return collapsebodyNonFragmented(arr,head,tail,filter);
    }
  }
  private boolean uncheckedcontainsBits(int tail,long bits)
  {
    if(tail<head)
    {
      final double[] arr;
      return DoubleArrSeq.uncheckedcontainsBits(arr=this.arr,0,tail+1,bits)
      || DoubleArrSeq.uncheckedcontainsBits(arr,head,arr.length,bits);
    }
    return DoubleArrSeq.uncheckedcontainsBits(arr,head,tail+1,bits);
  }
  private int uncheckedsearchBits(int tail,long bits)
  {
    final var arr=this.arr;
    int index=1;
    if(tail<head)
    {
      for(final int bound=arr.length;;++index)
      {
        if(bits==Double.doubleToRawLongBits(arr[head]))
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
      if(bits==Double.doubleToRawLongBits(arr[head]))
      {
        return index;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  boolean fragmentedremoveValBits(int head,int tail,long bits)
  {
    final double[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=head;
    for(;;++removeIndex)
    {
      if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
      if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
  boolean nonfragmentedremoveValBits(int head,int tail,long bits)
  {
    final var arr=this.arr;
    for(int removeIndex=head;;++removeIndex)
    {
      if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
  private boolean uncheckedremoveValBits(int tail,long bits)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveValBits(head,tail,bits);
    }
    return nonfragmentedremoveValBits(head,tail,bits);
  }
  boolean fragmentedremoveLastOccurrenceBits(int head,int tail,long bits)
  {
    final double[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=tail;
    for(;;--removeIndex)
    {
      if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
      if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
  boolean nonfragmentedremoveLastOccurrenceBits(int head,int tail,long bits)
  {
    final var arr=this.arr;
    for(int removeIndex=tail;;--removeIndex)
    {
      if(bits==Double.doubleToRawLongBits(arr[removeIndex]))
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
  private boolean uncheckedremoveLastOccurrenceBits(int tail,long bits)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveLastOccurrenceBits(head,tail,bits);
    }
    return nonfragmentedremoveLastOccurrenceBits(head,tail,bits);
  }
  private boolean uncheckedcontains0(int tail)
  {
    if(tail<head)
    {
      final double[] arr;
      return DoubleArrSeq.uncheckedcontains0(arr=this.arr,0,tail+1)
      || DoubleArrSeq.uncheckedcontains0(arr,head,arr.length);
    }
    return DoubleArrSeq.uncheckedcontains0(arr,head,tail+1);
  }
  private int uncheckedsearch0(int tail)
  {
    final var arr=this.arr;
    int index=1;
    if(tail<head)
    {
      for(final int bound=arr.length;;++index)
      {
        if(0==(arr[head]))
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
      if(0==(arr[head]))
      {
        return index;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  boolean fragmentedremoveVal0(int head,int tail)
  {
    final double[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=head;
    for(;;++removeIndex)
    {
      if(0==(arr[removeIndex]))
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
      if(0==(arr[removeIndex]))
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
  boolean nonfragmentedremoveVal0(int head,int tail)
  {
    final var arr=this.arr;
    for(int removeIndex=head;;++removeIndex)
    {
      if(0==(arr[removeIndex]))
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
  private boolean uncheckedremoveVal0(int tail)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveVal0(head,tail);
    }
    return nonfragmentedremoveVal0(head,tail);
  }
  boolean fragmentedremoveLastOccurrence0(int head,int tail)
  {
    final double[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=tail;
    for(;;--removeIndex)
    {
      if(0==(arr[removeIndex]))
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
      if(0==(arr[removeIndex]))
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
  boolean nonfragmentedremoveLastOccurrence0(int head,int tail)
  {
    final var arr=this.arr;
    for(int removeIndex=tail;;--removeIndex)
    {
      if(0==(arr[removeIndex]))
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
  private boolean uncheckedremoveLastOccurrence0(int tail)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveLastOccurrence0(head,tail);
    }
    return nonfragmentedremoveLastOccurrence0(head,tail);
  }
  private boolean uncheckedcontainsNaN(int tail)
  {
    if(tail<head)
    {
      final double[] arr;
      return DoubleArrSeq.uncheckedcontainsNaN(arr=this.arr,0,tail+1)
      || DoubleArrSeq.uncheckedcontainsNaN(arr,head,arr.length);
    }
    return DoubleArrSeq.uncheckedcontainsNaN(arr,head,tail+1);
  }
  private int uncheckedsearchNaN(int tail)
  {
    final var arr=this.arr;
    int index=1;
    if(tail<head)
    {
      for(final int bound=arr.length;;++index)
      {
        if(Double.isNaN(arr[head]))
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
      if(Double.isNaN(arr[head]))
      {
        return index;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  boolean fragmentedremoveValNaN(int head,int tail)
  {
    final double[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=head;
    for(;;++removeIndex)
    {
      if(Double.isNaN(arr[removeIndex]))
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
      if(Double.isNaN(arr[removeIndex]))
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
  boolean nonfragmentedremoveValNaN(int head,int tail)
  {
    final var arr=this.arr;
    for(int removeIndex=head;;++removeIndex)
    {
      if(Double.isNaN(arr[removeIndex]))
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
  private boolean uncheckedremoveValNaN(int tail)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveValNaN(head,tail);
    }
    return nonfragmentedremoveValNaN(head,tail);
  }
  boolean fragmentedremoveLastOccurrenceNaN(int head,int tail)
  {
    final double[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=tail;
    for(;;--removeIndex)
    {
      if(Double.isNaN(arr[removeIndex]))
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
      if(Double.isNaN(arr[removeIndex]))
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
  boolean nonfragmentedremoveLastOccurrenceNaN(int head,int tail)
  {
    final var arr=this.arr;
    for(int removeIndex=tail;;--removeIndex)
    {
      if(Double.isNaN(arr[removeIndex]))
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
  private boolean uncheckedremoveLastOccurrenceNaN(int tail)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveLastOccurrenceNaN(head,tail);
    }
    return nonfragmentedremoveLastOccurrenceNaN(head,tail);
  }
}
