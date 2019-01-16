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
import omni.function.BooleanPredicate;
import omni.function.BooleanConsumer;
import omni.impl.AbstractBooleanItr;
import omni.util.TypeUtil;
public class BooleanArrDeque implements OmniDeque.OfBoolean
{
  transient boolean[] arr;
  transient int head;
  transient int tail;
  public BooleanArrDeque()
  {
    this.tail=-1;
    this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
  }
  public BooleanArrDeque(int capacity)
  {
    this.tail=-1;
    switch(capacity)
    {
    default:
      this.arr=new boolean[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
    case 0:
    }
  }
  public BooleanArrDeque(int head,int tail,boolean[] arr)
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
      final boolean[] newArr;
      int head,size;
      if((size=tail-(head=this.head))<=0)
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new boolean[size+=arr.length],0,head);
        ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new boolean[size],0,size);
      }
      return new BooleanArrDeque(0,size-1,newArr);
    }
    return new BooleanArrDeque();
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
    final boolean[] arr;
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
    final boolean[] arr;
    int head;
    int hash=31+Boolean.hashCode((arr=this.arr)[head=this.head]);
    if(tail<=head)
    {
      for(int bound=arr.length;++head!=bound;hash=(hash*31)+Boolean.hashCode(arr[head])){}
      hash=(hash*31)+Boolean.hashCode(arr[head=0]);
    }
    for(;++head!=tail;hash=(hash*31)+Boolean.hashCode(arr[head])){}
    return hash;
  }
  @Override
  public boolean booleanElement()
  {
    return (boolean)arr[head];
  }
  @Override
  public boolean getLastBoolean()
  {
    return (boolean)arr[tail];
  }
  @Override
  public void addLast(boolean val)
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
          final boolean[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new boolean[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
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
              ArrCopy.uncheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(tail)],0,tail);
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
  public void push(boolean val)
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
          final boolean[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new boolean[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
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
              ArrCopy.uncheckedCopy(arr,0,arr=new boolean[arrLength=OmniArray.growBy50Pct(tail)],arrLength-=tail,tail);
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
  public boolean popBoolean()
  {
    return uncheckedExtractFirst(tail);
  }
  @Override
  public boolean removeLastBoolean()
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
  void uncheckedForEach(int tail,BooleanConsumer action)
  {
    var arr=this.arr;
    int head;
    if(tail<(head=this.head))
    {
      for(int bound=arr.length;;)
      {
        action.accept((boolean)arr[head]);
        if(++head==bound)
        {
          break;
        }
      }
      head=0;
    }
    for(;;++head)
    {
      action.accept((boolean)arr[head]);
      if(head==tail)
      {
        return;
      }
    }
  }
  @Override
  public boolean pollLastBoolean()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      boolean[] arr;
      final var ret=(boolean)((arr=this.arr)[tail]);
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
    return false;
  }
  @Override
  public Boolean pollLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      boolean[] arr;
      final var ret=(Boolean)((arr=this.arr)[tail]);
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
      boolean[] arr;
      final var ret=(double)TypeUtil.castToDouble((arr=this.arr)[tail]);
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
      boolean[] arr;
      final var ret=(float)TypeUtil.castToFloat((arr=this.arr)[tail]);
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
      boolean[] arr;
      final var ret=(long)TypeUtil.castToLong((arr=this.arr)[tail]);
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
  public int pollLastInt()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      boolean[] arr;
      final var ret=(int)(int)TypeUtil.castToByte((arr=this.arr)[tail]);
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
  public short pollLastShort()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      boolean[] arr;
      final var ret=(short)(short)TypeUtil.castToByte((arr=this.arr)[tail]);
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
    return Short.MIN_VALUE;
  }
  @Override
  public byte pollLastByte()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      boolean[] arr;
      final var ret=(byte)TypeUtil.castToByte((arr=this.arr)[tail]);
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
    return Byte.MIN_VALUE;
  }
  @Override
  public char pollLastChar()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      boolean[] arr;
      final var ret=(char)TypeUtil.castToChar((arr=this.arr)[tail]);
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
    return Character.MIN_VALUE;
  }
  @Override
  public boolean pollBoolean()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      boolean[] arr;
      final var ret=(boolean)((arr=this.arr)[head=this.head]);
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
    return false;
  }
  @Override
  public Boolean poll()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      boolean[] arr;
      final var ret=(Boolean)((arr=this.arr)[head=this.head]);
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
      boolean[] arr;
      final var ret=(double)TypeUtil.castToDouble((arr=this.arr)[head=this.head]);
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
      boolean[] arr;
      final var ret=(float)TypeUtil.castToFloat((arr=this.arr)[head=this.head]);
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
      boolean[] arr;
      final var ret=(long)TypeUtil.castToLong((arr=this.arr)[head=this.head]);
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
  public int pollInt()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      boolean[] arr;
      final var ret=(int)(int)TypeUtil.castToByte((arr=this.arr)[head=this.head]);
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
  public short pollShort()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      boolean[] arr;
      final var ret=(short)(short)TypeUtil.castToByte((arr=this.arr)[head=this.head]);
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
    return Short.MIN_VALUE;
  }
  @Override
  public byte pollByte()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      boolean[] arr;
      final var ret=(byte)TypeUtil.castToByte((arr=this.arr)[head=this.head]);
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
    return Byte.MIN_VALUE;
  }
  @Override
  public char pollChar()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      boolean[] arr;
      final var ret=(char)TypeUtil.castToChar((arr=this.arr)[head=this.head]);
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
    return Character.MIN_VALUE;
  }
  @Override
  public OmniIterator.OfBoolean iterator()
  {
    return new AscendingItr(this);
  }
  @Override
  public OmniIterator.OfBoolean descendingIterator()
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
  public boolean peekLastBoolean()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (boolean)(arr[tail]);
    }
    return false;
  }
  @Override
  public Boolean peekLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (Boolean)(arr[tail]);
    }
    return null;
  }
  @Override
  public double peekLastDouble()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (double)TypeUtil.castToDouble(arr[tail]);
    }
    return Double.NaN;
  }
  @Override
  public float peekLastFloat()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (float)TypeUtil.castToFloat(arr[tail]);
    }
    return Float.NaN;
  }
  @Override
  public long peekLastLong()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (long)TypeUtil.castToLong(arr[tail]);
    }
    return Long.MIN_VALUE;
  }
  @Override
  public int peekLastInt()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (int)(int)TypeUtil.castToByte(arr[tail]);
    }
    return Integer.MIN_VALUE;
  }
  @Override
  public short peekLastShort()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (short)(short)TypeUtil.castToByte(arr[tail]);
    }
    return Short.MIN_VALUE;
  }
  @Override
  public byte peekLastByte()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (byte)TypeUtil.castToByte(arr[tail]);
    }
    return Byte.MIN_VALUE;
  }
  @Override
  public char peekLastChar()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (char)TypeUtil.castToChar(arr[tail]);
    }
    return Character.MIN_VALUE;
  }
  @Override
  public boolean peekBoolean()
  {
    if(this.tail!=-1)
    {
      return (boolean)(arr[head]);
    }
    return false;
  }
  @Override
  public Boolean peek()
  {
    if(this.tail!=-1)
    {
      return (Boolean)(arr[head]);
    }
    return null;
  }
  @Override
  public double peekDouble()
  {
    if(this.tail!=-1)
    {
      return (double)TypeUtil.castToDouble(arr[head]);
    }
    return Double.NaN;
  }
  @Override
  public float peekFloat()
  {
    if(this.tail!=-1)
    {
      return (float)TypeUtil.castToFloat(arr[head]);
    }
    return Float.NaN;
  }
  @Override
  public long peekLong()
  {
    if(this.tail!=-1)
    {
      return (long)TypeUtil.castToLong(arr[head]);
    }
    return Long.MIN_VALUE;
  }
  @Override
  public int peekInt()
  {
    if(this.tail!=-1)
    {
      return (int)(int)TypeUtil.castToByte(arr[head]);
    }
    return Integer.MIN_VALUE;
  }
  @Override
  public short peekShort()
  {
    if(this.tail!=-1)
    {
      return (short)(short)TypeUtil.castToByte(arr[head]);
    }
    return Short.MIN_VALUE;
  }
  @Override
  public byte peekByte()
  {
    if(this.tail!=-1)
    {
      return (byte)TypeUtil.castToByte(arr[head]);
    }
    return Byte.MIN_VALUE;
  }
  @Override
  public char peekChar()
  {
    if(this.tail!=-1)
    {
      return (char)TypeUtil.castToChar(arr[head]);
    }
    return Character.MIN_VALUE;
  }
  @Override
  public void forEach(BooleanConsumer action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Boolean> action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override
  public boolean add(final boolean val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerLast(final boolean val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerFirst(final boolean val)
  {
    push((val));
    return true;
  }
  @Override
  public boolean offer(final boolean val)
  {
    addLast((val));
    return true;
  }
  @Override
  public void addFirst(final Boolean val)
  {
    push((boolean)(val));
  }
  @Override
  public void push(final Boolean val)
  {
    push((boolean)(val));
  }
  @Override
  public boolean offer(final Boolean val)
  {
    addLast((boolean)(val));
    return true;
  }
  @Override
  public boolean offerLast(final Boolean val)
  {
    addLast((boolean)(val));
    return true;
  }
  @Override
  public boolean offerFirst(final Boolean val)
  {
    push((boolean)(val));
    return true;
  }
  @Override
  public void addLast(final Boolean val)
  {
    addLast((boolean)(val));
  }
  @Override
  public boolean add(final Boolean val)
  {
    addLast((boolean)(val));
    return true;
  }
  private void initialize(boolean val)
  {
    boolean[] arr;
    if((arr=this.arr)==OmniArray.OfBoolean.DEFAULT_ARR)
    {
      this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=new boolean[1];
    }
    arr[0]=val;
    this.tail=0;
    this.head=0;
  }
  private boolean uncheckedExtractFirst(int tail)
  {
    boolean[] arr;
    int head;
    var ret=(boolean)(arr=this.arr)[head=this.head];
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
  private boolean uncheckedExtractLast(int tail)
  {
    boolean[] arr;
    var ret=(boolean)(arr=this.arr)[tail];
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
  public Boolean peekFirst()
  {
    return (Boolean)peek();
  }
  @Override
  public Boolean pollFirst()
  {
    return (Boolean)poll();
  }
  @Override
  public Boolean getFirst()
  {
    return (Boolean)booleanElement();
  }
  @Override
  public Boolean removeFirst()
  {
    return (Boolean)popBoolean();
  }
  @Override
  public Boolean remove()
  {
    return (Boolean)popBoolean();
  }
  @Override
  public Boolean element()
  {
    return (Boolean)booleanElement();
  }
  @Override
  public Boolean getLast()
  {
    return (Boolean)getLastBoolean();
  }
  @Override
  public Boolean removeLast()
  {
    return (Boolean)removeLastBoolean();
  }
  @Override
  public Boolean pop()
  {
    return (Boolean)popBoolean();
  }
  @Override
  public boolean removeFirstOccurrence(Object val)
  {
    return remove(val);
  }
  @Override
  public boolean[] toBooleanArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final boolean[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new boolean[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new boolean[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override
  public boolean removeIf(BooleanPredicate filter)
  {
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter);
  }
  @Override
  public boolean removeIf(Predicate<? super Boolean> filter)
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
  public Boolean[] toArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final Boolean[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new Boolean[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new Boolean[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
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
  public short[] toShortArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final short[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new short[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new short[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override
  public byte[] toByteArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final byte[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new byte[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new byte[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override
  public char[] toCharArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final char[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new char[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new char[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override
  public boolean contains(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      return uncheckedcontains(tail,(val));
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final boolean v;
      switch(val)
      {
        default:
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedcontains(tail,v);
    }
    return false;
  }
  @Override
  public boolean contains(final long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return false;
      }
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
      final boolean v;
      switch(Float.floatToRawIntBits(val))
      {
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
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return uncheckedcontains(tail,v);
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
      return uncheckedsearch(tail,(val));
    }
    return -1;
  }
  @Override
  public int search(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final boolean v;
      switch(val)
      {
        default:
          return -1;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedsearch(tail,v);
    }
    return -1;
  }
  @Override
  public int search(final long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return -1;
      }
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
      final boolean v;
      switch(Float.floatToRawIntBits(val))
      {
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
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return -1;
      }
      {
        return uncheckedsearch(tail,v);
      }
    }
    return -1;
  }
  private static class DescendingItr
    extends AbstractBooleanItr
    implements OmniIterator.OfBoolean
  {
    transient final BooleanArrDeque root;
    transient int nextIndex;
    DescendingItr(BooleanArrDeque root)
    {
      this.root=root;
      this.nextIndex=root.tail;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final BooleanArrDeque root;
      return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
    }
    @Override
    public boolean nextBoolean()
    {
      final boolean[] arr;
      int index;
      final boolean ret=(boolean)(arr=root.arr)[index=this.nextIndex];
      if(--index==-1)
      {
        index=arr.length-1;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(BooleanConsumer action)
    {
      final int tail,index,head;
      final BooleanArrDeque root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        BooleanArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        boolean[] arr;
        BooleanArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
        BooleanArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void forEachRemaining(Consumer<? super Boolean> action)
    {
      final int tail,index,head;
      final BooleanArrDeque root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        BooleanArrSeq.uncheckedReverseForEach(root.arr,head,index,action::accept);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        boolean[] arr;
        BooleanArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action::accept);
        BooleanArrSeq.uncheckedReverseForEach(arr,head,arr.length,action::accept);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void remove()
    {
      final BooleanArrDeque root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final boolean[] arr;
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
    extends AbstractBooleanItr
    implements OmniIterator.OfBoolean
  {
    transient final BooleanArrDeque root;
    transient int nextIndex;
    AscendingItr(BooleanArrDeque root)
    {
      this.root=root;
      this.nextIndex=root.head;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final BooleanArrDeque root;
      return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
        (index>=(head=root.head) && tail<head);
    }
    @Override
    public boolean nextBoolean()
    {
      final boolean[] arr;
      int index;
      final boolean ret=(boolean)(arr=root.arr)[index=this.nextIndex];
      if(++index==arr.length)
      {
        index=0;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(BooleanConsumer action)
    {
      final int tail,index,head;
      final BooleanArrDeque root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        BooleanArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final boolean[] arr;
        BooleanArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
        BooleanArrSeq.uncheckedForwardForEach(arr,0,tail,action);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void forEachRemaining(Consumer<? super Boolean> action)
    {
      final int tail,index,head;
      final BooleanArrDeque root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        BooleanArrSeq.uncheckedForwardForEach(root.arr,index,tail,action::accept);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final boolean[] arr;
        BooleanArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action::accept);
        BooleanArrSeq.uncheckedForwardForEach(arr,0,tail,action::accept);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void remove()
    {
      final BooleanArrDeque root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final boolean[] arr;
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
      if(val instanceof Boolean)
      {
        return uncheckedcontains(tail,(boolean)(val));
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
      if(val instanceof Boolean)
      {
        return uncheckedsearch(tail,(boolean)(val));
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
      if(val instanceof Boolean)
      {
        return uncheckedremoveVal(tail,(boolean)(val));
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
      if(val instanceof Boolean)
      {
        return uncheckedremoveLastOccurrence(tail,(boolean)(val));
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
      return uncheckedremoveVal(tail,(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final boolean v;
      switch(val)
      {
        default:
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedremoveVal(tail,v);
    }
    return false;
  }
  @Override
  public boolean removeVal(final long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return false;
      }
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
      final boolean v;
      switch(Float.floatToRawIntBits(val))
      {
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
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return uncheckedremoveVal(tail,v);
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
      return uncheckedremoveLastOccurrence(tail,(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final int val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final boolean v;
      switch(val)
      {
        default:
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedremoveLastOccurrence(tail,v);
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final long val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return false;
      }
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
      final boolean v;
      switch(Float.floatToRawIntBits(val))
      {
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
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return uncheckedremoveLastOccurrence(tail,v);
      }
    }
    return false;
  }
  public static class Checked extends BooleanArrDeque
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
    public Checked(int head,int tail,boolean[] arr)
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
        final boolean[] newArr;
        int head,size;
        if((size=tail-(head=this.head))<=0)
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new boolean[size+=arr.length],0,head);
          ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
        }
        else
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new boolean[size],0,size);
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
    public boolean booleanElement()
    {
      if(tail!=-1)
      {
        return (boolean)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override
    public boolean getLastBoolean()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        return (boolean)arr[tail];
      }
      throw new NoSuchElementException();
    }
    @Override
    public void addLast(boolean val)
    {
      ++this.modCount;
      super.addLast(val);
    }
    @Override
    public void push(boolean val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public boolean popBoolean()
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
    public boolean removeLastBoolean()
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
    void uncheckedForEach(int tail,BooleanConsumer action)
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
    public boolean pollLastBoolean()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        boolean[] arr;
        final var ret=(boolean)((arr=this.arr)[tail]);
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
      return false;
    }
    @Override
    public Boolean pollLast()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        boolean[] arr;
        final var ret=(Boolean)((arr=this.arr)[tail]);
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
        boolean[] arr;
        final var ret=(double)TypeUtil.castToDouble((arr=this.arr)[tail]);
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
        boolean[] arr;
        final var ret=(float)TypeUtil.castToFloat((arr=this.arr)[tail]);
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
        boolean[] arr;
        final var ret=(long)TypeUtil.castToLong((arr=this.arr)[tail]);
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
    public int pollLastInt()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        boolean[] arr;
        final var ret=(int)(int)TypeUtil.castToByte((arr=this.arr)[tail]);
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
    public short pollLastShort()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        boolean[] arr;
        final var ret=(short)(short)TypeUtil.castToByte((arr=this.arr)[tail]);
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
      return Short.MIN_VALUE;
    }
    @Override
    public byte pollLastByte()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        boolean[] arr;
        final var ret=(byte)TypeUtil.castToByte((arr=this.arr)[tail]);
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
      return Byte.MIN_VALUE;
    }
    @Override
    public char pollLastChar()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        boolean[] arr;
        final var ret=(char)TypeUtil.castToChar((arr=this.arr)[tail]);
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
      return Character.MIN_VALUE;
    }
    @Override
    public boolean pollBoolean()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        boolean[] arr;
        final var ret=(boolean)((arr=this.arr)[head=this.head]);
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
      return false;
    }
    @Override
    public Boolean poll()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        boolean[] arr;
        final var ret=(Boolean)((arr=this.arr)[head=this.head]);
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
        boolean[] arr;
        final var ret=(double)TypeUtil.castToDouble((arr=this.arr)[head=this.head]);
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
        boolean[] arr;
        final var ret=(float)TypeUtil.castToFloat((arr=this.arr)[head=this.head]);
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
        boolean[] arr;
        final var ret=(long)TypeUtil.castToLong((arr=this.arr)[head=this.head]);
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
    public int pollInt()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        boolean[] arr;
        final var ret=(int)(int)TypeUtil.castToByte((arr=this.arr)[head=this.head]);
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
    public short pollShort()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        boolean[] arr;
        final var ret=(short)(short)TypeUtil.castToByte((arr=this.arr)[head=this.head]);
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
      return Short.MIN_VALUE;
    }
    @Override
    public byte pollByte()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        boolean[] arr;
        final var ret=(byte)TypeUtil.castToByte((arr=this.arr)[head=this.head]);
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
      return Byte.MIN_VALUE;
    }
    @Override
    public char pollChar()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        boolean[] arr;
        final var ret=(char)TypeUtil.castToChar((arr=this.arr)[head=this.head]);
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
      return Character.MIN_VALUE;
    }
    private static class CheckedAscendingItr
      extends AbstractBooleanItr
      implements OmniIterator.OfBoolean
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
        final BooleanArrDeque root;
        return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
          (index>=(head=root.head) && tail<head);
      }
      @Override
      public boolean nextBoolean()
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
        return (boolean)arr[index];
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            BooleanArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
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
            final boolean[] arr;
            BooleanArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
            BooleanArrSeq.uncheckedForwardForEach(arr,0,tail,action);
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
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            BooleanArrSeq.uncheckedForwardForEach(root.arr,index,tail,action::accept);
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
            final boolean[] arr;
            BooleanArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action::accept);
            BooleanArrSeq.uncheckedForwardForEach(arr,0,tail,action::accept);
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
          final boolean[] arr;
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
      extends AbstractBooleanItr
      implements OmniIterator.OfBoolean
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
        final BooleanArrDeque root;
        return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
      }
      @Override
      public boolean nextBoolean()
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
        return (boolean)arr[index];
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            BooleanArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
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
            boolean[] arr;
            BooleanArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
            BooleanArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
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
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            BooleanArrSeq.uncheckedReverseForEach(root.arr,head,index,action::accept);
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
            boolean[] arr;
            BooleanArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action::accept);
            BooleanArrSeq.uncheckedReverseForEach(arr,head,arr.length,action::accept);
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
          final boolean[] arr;
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
    public OmniIterator.OfBoolean iterator()
    {
      return new CheckedAscendingItr(this);
    }
    @Override
    public OmniIterator.OfBoolean descendingIterator()
    {
      return new CheckedDescendingItr(this);
    }
    @Override
    boolean fragmentedremoveVal(int head,int tail,boolean val)
    {
      final boolean[] arr;
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
    boolean nonfragmentedremoveVal(int head,int tail,boolean val)
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
    boolean fragmentedremoveLastOccurrence(int head,int tail,boolean val)
    {
      final boolean[] arr;
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
    boolean nonfragmentedremoveLastOccurrence(int head,int tail,boolean val)
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
    private void collapseheadandtailFragmented(int modCount,boolean[] arr,int head,int tail,BooleanPredicate filter,boolean removeThis)
    {
      while(tail!=0)
      {
        if(arr[--tail]^removeThis)
        {
          if(filter.test(removeThis=!removeThis))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            this.tail=-1;
            return;
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.tail=tail;
          int bound;
          if((bound=arr.length)-1==(head=pullUp(arr,bound,head,removeThis)))
          {
            head=pullUp(arr,tail,-1,removeThis);
          }
          this.head=head+1;
          return;
        }
      }
      for(tail=arr.length-1;tail!=head;--tail)
      {
        if(arr[tail]^removeThis)
        {
          if(filter.test(removeThis=!removeThis))
          {
            break;
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.tail=tail;
          this.head=pullUp(arr,tail,head,removeThis)+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.tail=-1;
    }
    private boolean collapsebodyFragmented(int modCount,boolean[] arr,int head,int tail,BooleanPredicate filter,boolean retainThis)
    {
      for(int srcOffset=0;srcOffset!=tail;++srcOffset)
      {
        if(arr[srcOffset]^retainThis)
        {
          if(!filter.test(!retainThis))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          arr[tail=BooleanArrSeq.pullSurvivorsDown(arr,srcOffset,tail,retainThis)]=retainThis;
          this.tail=tail;
          if(head!=(head=pullUp(arr,arr.length,head,retainThis)))
          {
            arr[head]=retainThis;
            this.head=head;
          }
          return true;
        }
      }
      for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset)
      {
        if(arr[srcOffset]^retainThis)
        {
          if(!filter.test(!retainThis))
          {
            break;
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          arr[head=BooleanArrSeq.pullSurvivorsUp(arr,srcOffset,head,retainThis)]=retainThis;
          this.head=head;
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    private void collapseheadandtailNonFragmented(int modCount,boolean[] arr,int head,int tail,BooleanPredicate filter,boolean removeThis)
    {
      while(++head!=tail)
      {
        if(arr[head]^removeThis)
        {
          if(filter.test(removeThis=!removeThis))
          {
            break;
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=head;
          while(--tail!=head)
          {
            if(arr[tail]==removeThis)
            {
              if(tail!=(tail=pullDown(arr,head,tail,removeThis)))
              {
                arr[tail]=removeThis;
              }
              break;
            }
          }
          this.tail=tail;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.tail=-1;
      return;
    }
    private boolean collapsebodyNonFragmented(int modCount,boolean[] arr,int head,int tail,BooleanPredicate filter, boolean retainThis)
    {
      while(++head!=tail)
      {
        if(arr[head]^retainThis)
        {
          if(!filter.test(!retainThis))
          {
            break;
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          arr[tail=BooleanArrSeq.pullSurvivorsDown(arr,head,tail,retainThis)]=retainThis;
          this.tail=tail;
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    private boolean uncheckedRemoveIf1(int tail,BooleanPredicate filter)
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
    private boolean uncheckedRemoveIfFragmented(int head,int tail,BooleanPredicate filter)
    {
      int modCount=this.modCount;
      try
      {
        boolean[] arr;
        boolean v;
        if(filter.test(v=(arr=this.arr)[head]))
        {
          if(arr[tail]==v)
          {
            collapseheadandtailFragmented(modCount,arr,head,tail,filter,v);
          }
          else if(filter.test(v=!v))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            this.tail=-1;
          }
          else
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            super.collapseheadFragmented(arr,head,tail,v);
          }
          return true;
        }
        else if(arr[tail]==v)
        {
          return collapsebodyFragmented(modCount,arr,head,tail,filter,v);
        }
        else if(filter.test(!v))
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          super.collapsetailFragmented(arr,head,tail,v);
          return true;
        }
        return false;
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    boolean uncheckedRemoveIf(int tail,BooleanPredicate filter)
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
            boolean[] arr;
            boolean v;
            if(filter.test(v=(arr=this.arr)[head]))
            {
              if(arr[tail]==v)
              {
                collapseheadandtailNonFragmented(modCount,arr,head,tail,filter,v);
              }
              else if(filter.test(v=!v))
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.modCount=modCount+1;
                this.tail=-1;
              }
              else
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.modCount=modCount+1;
                super.collapseheadNonFragmented(arr,head,tail,v);
              }
              return true;
            }
            else if(arr[tail]==v)
            {
              return collapsebodyNonFragmented(modCount,arr,head,tail,filter,v);
            }
            else if(filter.test(!v))
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
              super.collapsetailNonFragmented(arr,head,tail,v);
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
    }
  }
  private void collapseheadNonFragmented(boolean[] arr,int head,int tail,
  boolean retainThis)
  {
    while(++head!=tail)
    {
      if(arr[head]==retainThis)
      {
        if(head!=(head=pullUp(arr,tail,head,
        retainThis))){arr[head]=retainThis;}
        break;
      }
    }
    this.head=head;
  }
  private void collapsetailNonFragmented(boolean[] arr,int head,int tail,
  boolean retainThis)
  {
    while(--tail!=head)
    {
      if(arr[tail]==retainThis)
      {
        if(tail!=(tail=pullDown(arr,head,tail,
        retainThis))){arr[tail]=retainThis;}
        break;
      }
    }
    this.tail=tail;
  }
  private boolean collapsebodyNonFragmented(boolean[] arr,int head,int tail,BooleanPredicate filter,boolean retainThis)
  {
    while(++head!=tail)
    {
      if(arr[head]^retainThis)
      {
        if(!filter.test(!retainThis))
        {
          break;
        }
        arr[head=BooleanArrSeq.pullSurvivorsDown(arr,head,tail,retainThis)]=retainThis;
        this.tail=head;
        return true;
      }
    }
    return false;
  }
  private static int pullDown(boolean[] arr,int srcOffset,int srcBound,boolean retainThis)
  {
    while(++srcOffset!=srcBound)
    {
      if(arr[srcOffset]^retainThis)
      {
        return BooleanArrSeq.pullSurvivorsDown(arr,srcOffset,srcBound,retainThis);
      }
    }
    return srcOffset;
  }
  private static int pullUp(boolean[] arr,int srcOffset,int srcBound,boolean retainThis)
  {
    while(--srcOffset!=srcBound)
    {
      if(arr[srcOffset]^retainThis)
      {
        return BooleanArrSeq.pullSurvivorsUp(arr,srcOffset,srcBound,retainThis);
      }
    }
    return srcOffset;
  }
  private void collapseheadandtailNonFragmented(boolean[] arr,int head,int tail,BooleanPredicate filter,boolean removeThis)
  {
    while(++head!=tail)
    {
      if(arr[head]^removeThis)
      {
        if(filter.test(removeThis=!removeThis))
        {
          this.tail=-1;
        }
        else
        {
          this.head=head;
          this.tail=pullDown(arr,head,tail,removeThis)-1;
        }
        return;
      }
    }
    this.tail=-1;
  }
  private boolean collapsebodyFragmented(boolean[] arr,int head,int tail,BooleanPredicate filter,boolean retainThis)
  {
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(arr[srcOffset]^retainThis)
      {
        if(!filter.test(!retainThis))
        {
          return false;
        }
        arr[tail=BooleanArrSeq.pullSurvivorsDown(arr,srcOffset,tail,retainThis)]=retainThis;
        this.tail=tail;
        if(head!=(head=pullUp(arr,arr.length,head,retainThis)))
        {
          arr[head]=retainThis;
          this.head=head;
        }
        return true;
      }
    }
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset)
    {
      if(arr[srcOffset]^retainThis)
      {
        if(!filter.test(!retainThis))
        {
          break;
        }
        arr[head=BooleanArrSeq.pullSurvivorsUp(arr,srcOffset,head,retainThis)]=retainThis;
        this.head=head;
        return true;
      }
    }
    return false;
  }
  private void collapseheadandtailFragmented(boolean[] arr,int head,int tail,BooleanPredicate filter,boolean removeThis)
  {
    while(tail!=0)
    {
      if(arr[--tail]^removeThis)
      {
        if(filter.test(removeThis=!removeThis))
        {
          this.tail=-1;
          return;
        }
        int bound=arr.length;
        while(++head!=bound)
        {
          if(arr[head]==removeThis)
          {
            if(head!=(head=pullUp(arr,bound,head,removeThis)))
            {
              arr[head]=removeThis;
            }
            this.head=head;
            if(tail!=(tail=pullDown(arr,-1,tail,removeThis)))
            {
              arr[tail]=removeThis;
            }
            this.tail=tail;
            return;
          }
        }
        this.tail=tail;
        this.head=pullUp(arr,tail,-1,removeThis)+1;
        return;
      }
    }
    for(tail=arr.length-1;tail!=head;--tail)
    {
      if(arr[tail]^removeThis)
      {
        if(filter.test(removeThis=!removeThis))
        {
          break;
        }
        while(++head!=tail)
        {
          if(arr[head]==removeThis)
          {
            if(tail!=(tail=pullDown(arr,head,tail,removeThis)))
            {
              arr[tail]=removeThis;
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
  private void collapsetailFragmented(boolean[] arr,int head,int tail,boolean retainThis)
  {
    while(tail!=0)
    {
      if(arr[--tail]==retainThis)
      {
        for(int srcOffset=0;srcOffset!=tail;++srcOffset)
        {
          if(arr[srcOffset]^retainThis)
          {
            arr[tail=BooleanArrSeq.pullSurvivorsDown(arr,srcOffset,tail,retainThis)]=retainThis;
            break;
          }
        }
        if(head!=(head=pullUp(arr,arr.length,head,retainThis)))
        {
          arr[head]=retainThis;
          this.head=head;
        }
        this.tail=tail;
        return;
      }
    }
    this.tail=pullDown(arr,head,arr.length,retainThis)-1;
  }
  private void collapseheadFragmented(boolean[] arr,int head,int tail,boolean retainThis)
  {
    int bound=arr.length-1;
    while(head!=bound)
    {
      if(arr[++head]==retainThis)
      {
        for(int srcOffset=bound;srcOffset!=head;--srcOffset)
        {
          if(arr[srcOffset]^retainThis)
          {
            arr[head=BooleanArrSeq.pullSurvivorsUp(arr,srcOffset,head,retainThis)]=retainThis;
            break;
          }
        }
        if(tail!=(tail=pullDown(arr,-1,tail,retainThis)))
        {
          arr[tail]=retainThis;
          this.tail=tail;
        }
        this.head=head;
        return;
      }
    }
    this.head=pullUp(arr,tail,-1,retainThis)+1;
  }
  private boolean uncheckedRemoveIf1(int tail,BooleanPredicate filter)
  {
    if(filter.test(arr[tail]))
    {
        this.tail=-1;
        return true;
    }
    return false;
  }
  private boolean uncheckedRemoveIfFragmented(int head,int tail,BooleanPredicate filter)
  {
    boolean[] arr;
    boolean v;
    if(filter.test(v=(arr=this.arr)[head]))
    {
      if(arr[tail]==v)
      {
        collapseheadandtailFragmented(arr,head,tail,filter,v);
      }
      else if(filter.test(v=!v))
      {
        this.tail=-1;
      }
      else
      {
        collapseheadFragmented(arr,head,tail,v);
      }
      return true;
    }
    else if(arr[tail]==v)
    {
      return collapsebodyFragmented(arr,head,tail,filter,v);
    }
    else if(filter.test(!v))
    {
      collapsetailFragmented(arr,head,tail,v);
      return true;
    }
    return false;
  }
  boolean uncheckedRemoveIf(int tail,BooleanPredicate filter)
  {
    int head;
    switch(Integer.signum(tail-(head=this.head)))
    {
      case 0:
        return uncheckedRemoveIf1(tail,filter);
      case -1:
        return uncheckedRemoveIfFragmented(head,tail,filter);
      default:
        boolean[] arr;
        boolean v;
        if(filter.test(v=(arr=this.arr)[head]))
        {
          if(arr[tail]==v)
          {
            collapseheadandtailNonFragmented(arr,head,tail,filter,v);
          }
          else if(filter.test(v=!v))
          {
            this.tail=-1;
          }
          else
          {
            collapseheadNonFragmented(arr,head,tail,v);
          }
          return true;
        }
        else if(arr[tail]==v)
        {
          return collapsebodyNonFragmented(arr,head,tail,filter,v);
        }
        else if(filter.test(!v))
        {
          collapsetailNonFragmented(arr,head,tail,v);
          return true;
        }
        return false;
    }
  }
  private boolean uncheckedcontains(int tail,boolean val)
  {
    if(tail<head)
    {
      final boolean[] arr;
      return BooleanArrSeq.uncheckedcontains(arr=this.arr,0,tail+1,val)
      || BooleanArrSeq.uncheckedcontains(arr,head,arr.length,val);
    }
    return BooleanArrSeq.uncheckedcontains(arr,head,tail+1,val);
  }
  private int uncheckedsearch(int tail,boolean val)
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
  boolean fragmentedremoveVal(int head,int tail,boolean val)
  {
    final boolean[] arr;
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
  boolean nonfragmentedremoveVal(int head,int tail,boolean val)
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
  private boolean uncheckedremoveVal(int tail,boolean val)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveVal(head,tail,val);
    }
    return nonfragmentedremoveVal(head,tail,val);
  }
  boolean fragmentedremoveLastOccurrence(int head,int tail,boolean val)
  {
    final boolean[] arr;
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
  boolean nonfragmentedremoveLastOccurrence(int head,int tail,boolean val)
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
  private boolean uncheckedremoveLastOccurrence(int tail,boolean val)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveLastOccurrence(head,tail,val);
    }
    return nonfragmentedremoveLastOccurrence(head,tail,val);
  }
}
