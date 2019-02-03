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
import omni.util.BitSetUtil;
import omni.util.HashUtil;
import omni.function.FloatPredicate;
import omni.function.FloatConsumer;
import omni.impl.AbstractFloatItr;
import omni.util.TypeUtil;
public class FloatArrDeque implements OmniDeque.OfFloat
{
  transient float[] arr;
  transient int head;
  transient int tail;
  public FloatArrDeque()
  {
    this.tail=-1;
    this.arr=OmniArray.OfFloat.DEFAULT_ARR;
  }
  public FloatArrDeque(int capacity)
  {
    this.tail=-1;
    switch(capacity)
    {
    default:
      this.arr=new float[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfFloat.DEFAULT_ARR;
    case 0:
    }
  }
  public FloatArrDeque(int head,int tail,float[] arr)
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
      final float[] newArr;
      int head,size;
      if((size=tail-(head=this.head))<=0)
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new float[size+=arr.length],0,head);
        ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,head,newArr=new float[size],0,size);
      }
      return new FloatArrDeque(0,size-1,newArr);
    }
    return new FloatArrDeque();
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
    final float[] arr;
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
    final float[] arr;
    int head;
    int hash=31+HashUtil.hashFloat((arr=this.arr)[head=this.head]);
    if(tail<=head)
    {
      for(int bound=arr.length;++head!=bound;hash=(hash*31)+HashUtil.hashFloat(arr[head])){}
      hash=(hash*31)+HashUtil.hashFloat(arr[head=0]);
    }
    for(;++head!=tail;hash=(hash*31)+HashUtil.hashFloat(arr[head])){}
    return hash;
  }
  @Override
  public float floatElement()
  {
    return (float)arr[head];
  }
  @Override
  public float getLastFloat()
  {
    return (float)arr[tail];
  }
  @Override
  public void addLast(float val)
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
          final float[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new float[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
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
              ArrCopy.uncheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(tail)],0,tail);
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
  public void push(float val)
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
          final float[] newArr;
          ArrCopy.uncheckedCopy(arr,0,newArr=new float[newArrSize=OmniArray.growBy50Pct(oldArrSize=arr.length)],0,tail);
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
              ArrCopy.uncheckedCopy(arr,0,arr=new float[arrLength=OmniArray.growBy50Pct(tail)],arrLength-=tail,tail);
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
  public float popFloat()
  {
    return uncheckedExtractFirst(tail);
  }
  @Override
  public float removeLastFloat()
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
  void uncheckedForEach(int tail,FloatConsumer action)
  {
    var arr=this.arr;
    int head;
    if(tail<(head=this.head))
    {
      for(int bound=arr.length;;)
      {
        action.accept((float)arr[head]);
        if(++head==bound)
        {
          break;
        }
      }
      head=0;
    }
    for(;;++head)
    {
      action.accept((float)arr[head]);
      if(head==tail)
      {
        return;
      }
    }
  }
  @Override
  public float pollLastFloat()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      float[] arr;
      final var ret=(float)((arr=this.arr)[tail]);
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
  public Float pollLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      float[] arr;
      final var ret=(Float)((arr=this.arr)[tail]);
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
      float[] arr;
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
  public float pollFloat()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      float[] arr;
      final var ret=(float)((arr=this.arr)[head=this.head]);
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
  public Float poll()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      int head;
      float[] arr;
      final var ret=(Float)((arr=this.arr)[head=this.head]);
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
      float[] arr;
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
  public OmniIterator.OfFloat iterator()
  {
    return new AscendingItr(this);
  }
  @Override
  public OmniIterator.OfFloat descendingIterator()
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
  private boolean uncheckedremoveLastOccurrence(int tail,float val)
  {
    if(val==val)
    {
      return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
    }
    return uncheckedremoveLastOccurrenceNaN(tail);
  }
  private boolean uncheckedremoveVal(int tail,float val)
  {
    if(val==val)
    {
      return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
    }
    return uncheckedremoveValNaN(tail);
  }
  @Override
  public float peekLastFloat()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (float)(arr[tail]);
    }
    return Float.NaN;
  }
  @Override
  public Float peekLast()
  {
    int tail;
    if((tail=this.tail)!=-1)
    {
      return (Float)(arr[tail]);
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
  public float peekFloat()
  {
    if(this.tail!=-1)
    {
      return (float)(arr[head]);
    }
    return Float.NaN;
  }
  @Override
  public Float peek()
  {
    if(this.tail!=-1)
    {
      return (Float)(arr[head]);
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
  public void forEach(FloatConsumer action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Float> action)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override
  public boolean add(final float val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerLast(final float val)
  {
    addLast((val));
    return true;
  }
  @Override
  public boolean offerFirst(final float val)
  {
    push((val));
    return true;
  }
  @Override
  public boolean offer(final float val)
  {
    addLast((val));
    return true;
  }
  @Override
  public void addFirst(final Float val)
  {
    push((float)(val));
  }
  @Override
  public void push(final Float val)
  {
    push((float)(val));
  }
  @Override
  public boolean offer(final Float val)
  {
    addLast((float)(val));
    return true;
  }
  @Override
  public boolean offerLast(final Float val)
  {
    addLast((float)(val));
    return true;
  }
  @Override
  public boolean offerFirst(final Float val)
  {
    push((float)(val));
    return true;
  }
  @Override
  public void addLast(final Float val)
  {
    addLast((float)(val));
  }
  @Override
  public boolean add(final Float val)
  {
    addLast((float)(val));
    return true;
  }
  @Override
  public boolean add(final boolean val)
  {
    addLast(TypeUtil.castToFloat(val));
    return true;
  }
  @Override
  public boolean add(final int val)
  {
    addLast((float)(val));
    return true;
  }
  @Override
  public boolean add(final char val)
  {
    addLast((float)(val));
    return true;
  }
  @Override
  public boolean add(final short val)
  {
    addLast((float)(val));
    return true;
  }
  @Override
  public boolean add(final long val)
  {
    addLast((float)(val));
    return true;
  }
  private void initialize(float val)
  {
    float[] arr;
    if((arr=this.arr)==OmniArray.OfFloat.DEFAULT_ARR)
    {
      this.arr=arr=new float[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=new float[1];
    }
    arr[0]=val;
    this.tail=0;
    this.head=0;
  }
  private float uncheckedExtractFirst(int tail)
  {
    float[] arr;
    int head;
    var ret=(float)(arr=this.arr)[head=this.head];
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
  private float uncheckedExtractLast(int tail)
  {
    float[] arr;
    var ret=(float)(arr=this.arr)[tail];
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
  public Float peekFirst()
  {
    return (Float)peek();
  }
  @Override
  public Float pollFirst()
  {
    return (Float)poll();
  }
  @Override
  public Float getFirst()
  {
    return (Float)floatElement();
  }
  @Override
  public Float removeFirst()
  {
    return (Float)popFloat();
  }
  @Override
  public Float remove()
  {
    return (Float)popFloat();
  }
  @Override
  public Float element()
  {
    return (Float)floatElement();
  }
  @Override
  public Float getLast()
  {
    return (Float)getLastFloat();
  }
  @Override
  public Float removeLast()
  {
    return (Float)removeLastFloat();
  }
  @Override
  public Float pop()
  {
    return (Float)popFloat();
  }
  @Override
  public boolean removeFirstOccurrence(Object val)
  {
    return remove(val);
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
  public boolean removeIf(FloatPredicate filter)
  {
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter);
  }
  @Override
  public boolean removeIf(Predicate<? super Float> filter)
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
  public Float[] toArray()
  {
    int tail;
    if((tail=this.tail+1)!=0)
    {
      final var srcArr=this.arr;
      final Float[] ret;
      int size;
      if((size=tail-head)<1)
      {
        ArrCopy.uncheckedCopy(srcArr,0,ret=new Float[size+=srcArr.length],size-=tail,tail);
        ArrCopy.uncheckedCopy(srcArr,head,ret,0,size);
      }
      else
      {
        ArrCopy.uncheckedCopy(srcArr,head,ret=new Float[size],0,size);
      }
      return ret;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  public boolean contains(final boolean val)
  {
    final int tail;
    if((tail=this.tail)!=-1)
    {
      if(val)
      {
        return uncheckedcontainsBits(tail,TypeUtil.FLT_TRUE_BITS);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
      return uncheckedcontains(tail,(val));
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return uncheckedcontainsBits(tail,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return uncheckedcontainsNaN(tail);
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
        if(val!=0)
        {
          return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
        return uncheckedsearchBits(tail,TypeUtil.FLT_TRUE_BITS);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return uncheckedsearchBits(tail,Float.floatToRawIntBits(val));
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return uncheckedsearchBits(tail,Float.floatToRawIntBits(val));
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
      return uncheckedsearch(tail,(val));
    }
    return -1;
  }
  @Override
  public int search(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return uncheckedsearchBits(tail,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return uncheckedsearchNaN(tail);
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
        if(val!=0)
        {
          return uncheckedsearchBits(tail,Float.floatToRawIntBits(val));
        }
        return uncheckedsearch0(tail);
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
        if(val!=0)
        {
          return uncheckedsearchBits(tail,Float.floatToRawIntBits(val));
        }
        return uncheckedsearch0(tail);
      }
    }
    return -1;
  }
  private boolean uncheckedcontains(int tail,float val)
  {
    if(val==val)
    {
      return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
    }
    return uncheckedcontainsNaN(tail);
  }
  private int uncheckedsearch(int tail,float val)
  {
    if(val==val)
    {
      return uncheckedsearchBits(tail,Float.floatToRawIntBits(val));
    }
    return uncheckedsearchNaN(tail);
  }
  private static class DescendingItr
    extends AbstractFloatItr
    implements OmniIterator.OfFloat
  {
    transient final FloatArrDeque root;
    transient int nextIndex;
    DescendingItr(FloatArrDeque root)
    {
      this.root=root;
      this.nextIndex=root.tail;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final FloatArrDeque root;
      return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
    }
    @Override
    public float nextFloat()
    {
      final float[] arr;
      int index;
      final float ret=(float)(arr=root.arr)[index=this.nextIndex];
      if(--index==-1)
      {
        index=arr.length-1;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(FloatConsumer action)
    {
      final int tail,index,head;
      final FloatArrDeque root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        FloatArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        float[] arr;
        FloatArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
        FloatArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void forEachRemaining(Consumer<? super Float> action)
    {
      final int tail,index,head;
      final FloatArrDeque root;
      if((index=this.nextIndex+1)>(head=(root=this.root).head))
      {
        FloatArrSeq.uncheckedReverseForEach(root.arr,head,index,action::accept);
        this.nextIndex=head-1;
      }
      else if(index<=(tail=root.tail) && tail<head)
      {
        float[] arr;
        FloatArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action::accept);
        FloatArrSeq.uncheckedReverseForEach(arr,head,arr.length,action::accept);
        this.nextIndex=head-1;
      }
    }
    @Override
    public void remove()
    {
      final FloatArrDeque root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final float[] arr;
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
    extends AbstractFloatItr
    implements OmniIterator.OfFloat
  {
    transient final FloatArrDeque root;
    transient int nextIndex;
    AscendingItr(FloatArrDeque root)
    {
      this.root=root;
      this.nextIndex=root.head;
    }
    @Override
    public boolean hasNext()
    {
      final int tail,head,index;
      final FloatArrDeque root;
      return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
        (index>=(head=root.head) && tail<head);
    }
    @Override
    public float nextFloat()
    {
      final float[] arr;
      int index;
      final float ret=(float)(arr=root.arr)[index=this.nextIndex];
      if(++index==arr.length)
      {
        index=0;
      }
      this.nextIndex=index;
      return ret;
    }
    @Override
    public void forEachRemaining(FloatConsumer action)
    {
      final int tail,index,head;
      final FloatArrDeque root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        FloatArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final float[] arr;
        FloatArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
        FloatArrSeq.uncheckedForwardForEach(arr,0,tail,action);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void forEachRemaining(Consumer<? super Float> action)
    {
      final int tail,index,head;
      final FloatArrDeque root;
      if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
      {
        FloatArrSeq.uncheckedForwardForEach(root.arr,index,tail,action::accept);
        this.nextIndex=tail;
      }
      else if(index>=(head=root.head) && tail<=head)
      {
        final float[] arr;
        FloatArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action::accept);
        FloatArrSeq.uncheckedForwardForEach(arr,0,tail,action::accept);
        this.nextIndex=tail; 
      }
    }
    @Override
    public void remove()
    {
      final FloatArrDeque root;
      int head,tail,removeIndex;
      if((tail=(root=this.root).tail)<(head=root.head))
      {
        //fragmented
        final float[] arr;
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
      if(val instanceof Float)
      {
        return uncheckedcontains(tail,(float)(val));
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
      if(val instanceof Float)
      {
        return uncheckedsearch(tail,(float)(val));
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
      if(val instanceof Float)
      {
        return uncheckedremoveVal(tail,(float)(val));
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
      if(val instanceof Float)
      {
        return uncheckedremoveLastOccurrence(tail,(float)(val));
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
        return uncheckedremoveValBits(tail,TypeUtil.FLT_TRUE_BITS);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
      return uncheckedremoveVal(tail,(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return uncheckedremoveValBits(tail,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return uncheckedremoveValNaN(tail);
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
        if(val!=0)
        {
          return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
        return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.FLT_TRUE_BITS);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
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
      return uncheckedremoveLastOccurrence(tail,(val));
    }
    return false;
  }
  @Override
  public boolean removeLastOccurrence(final double val)
  {
    final int tail;
    if((tail=this.tail)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return uncheckedremoveLastOccurrenceNaN(tail);
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
        if(val!=0)
        {
          return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveLastOccurrence0(tail);
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
        if(val!=0)
        {
          return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveLastOccurrence0(tail);
      }
    }
    return false;
  }
  public static class Checked extends FloatArrDeque
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
    public Checked(int head,int tail,float[] arr)
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
        final float[] newArr;
        int head,size;
        if((size=tail-(head=this.head))<=0)
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new float[size+=arr.length],0,head);
          ArrCopy.uncheckedCopy(arr,0,newArr,head,tail);
        }
        else
        {
          ArrCopy.uncheckedCopy(arr,head,newArr=new float[size],0,size);
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
    public float floatElement()
    {
      if(tail!=-1)
      {
        return (float)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override
    public float getLastFloat()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        return (float)arr[tail];
      }
      throw new NoSuchElementException();
    }
    @Override
    public void addLast(float val)
    {
      ++this.modCount;
      super.addLast(val);
    }
    @Override
    public void push(float val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public float popFloat()
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
    public float removeLastFloat()
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
    void uncheckedForEach(int tail,FloatConsumer action)
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
    public float pollLastFloat()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        float[] arr;
        final var ret=(float)((arr=this.arr)[tail]);
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
    public Float pollLast()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        float[] arr;
        final var ret=(Float)((arr=this.arr)[tail]);
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
        float[] arr;
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
    public float pollFloat()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        float[] arr;
        final var ret=(float)((arr=this.arr)[head=this.head]);
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
    public Float poll()
    {
      int tail;
      if((tail=this.tail)!=-1)
      {
        ++this.modCount;
        int head;
        float[] arr;
        final var ret=(Float)((arr=this.arr)[head=this.head]);
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
        float[] arr;
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
    private static class CheckedAscendingItr
      extends AbstractFloatItr
      implements OmniIterator.OfFloat
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
        final FloatArrDeque root;
        return (index=this.nextIndex)<=(tail=(root=this.root).tail) ||
          (index>=(head=root.head) && tail<head);
      }
      @Override
      public float nextFloat()
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
        return (float)arr[index];
      }
      @Override
      public void forEachRemaining(FloatConsumer action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            FloatArrSeq.uncheckedForwardForEach(root.arr,index,tail,action);
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
            final float[] arr;
            FloatArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action);
            FloatArrSeq.uncheckedForwardForEach(arr,0,tail,action);
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
      public void forEachRemaining(Consumer<? super Float> action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex)<(tail=(root=this.root).tail+1))
        {
          int modCount=this.modCount;
          try
          {
            FloatArrSeq.uncheckedForwardForEach(root.arr,index,tail,action::accept);
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
            final float[] arr;
            FloatArrSeq.uncheckedForwardForEach(arr=root.arr,index,arr.length,action::accept);
            FloatArrSeq.uncheckedForwardForEach(arr,0,tail,action::accept);
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
          final float[] arr;
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
      extends AbstractFloatItr
      implements OmniIterator.OfFloat
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
        final FloatArrDeque root;
        return (index=this.nextIndex)>=(head=(root=this.root).head) || (index<=(tail=root.tail)&&tail<head);
      }
      @Override
      public float nextFloat()
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
        return (float)arr[index];
      }
      @Override
      public void forEachRemaining(FloatConsumer action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            FloatArrSeq.uncheckedReverseForEach(root.arr,head,index,action);
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
            float[] arr;
            FloatArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action);
            FloatArrSeq.uncheckedReverseForEach(arr,head,arr.length,action);
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
      public void forEachRemaining(Consumer<? super Float> action)
      {
        final int tail,index,head;
        final Checked root;
        if((index=this.nextIndex+1)>(head=(root=this.root).head))
        {
          int modCount=this.modCount;
          try
          {
            FloatArrSeq.uncheckedReverseForEach(root.arr,head,index,action::accept);
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
            float[] arr;
            FloatArrSeq.uncheckedReverseForEach(arr=root.arr,0,index,action::accept);
            FloatArrSeq.uncheckedReverseForEach(arr,head,arr.length,action::accept);
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
          final float[] arr;
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
    public OmniIterator.OfFloat iterator()
    {
      return new CheckedAscendingItr(this);
    }
    @Override
    public OmniIterator.OfFloat descendingIterator()
    {
      return new CheckedDescendingItr(this);
    }
    @Override
    boolean fragmentedremoveValBits(int head,int tail,int bits)
    {
      final float[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=head;
      for(;;++removeIndex)
      {
        if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
        if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
    boolean nonfragmentedremoveValBits(int head,int tail,int bits)
    {
      final var arr=this.arr;
      for(int removeIndex=head;;++removeIndex)
      {
        if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
      final float[] arr;
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
      final float[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=head;
      for(;;++removeIndex)
      {
        if(Float.isNaN(arr[removeIndex]))
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
        if(Float.isNaN(arr[removeIndex]))
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
        if(Float.isNaN(arr[removeIndex]))
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
    boolean fragmentedremoveLastOccurrenceBits(int head,int tail,int bits)
    {
      final float[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=tail;
      for(;;--removeIndex)
      {
        if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
        if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
    boolean nonfragmentedremoveLastOccurrenceBits(int head,int tail,int bits)
    {
      final var arr=this.arr;
      for(int removeIndex=tail;;--removeIndex)
      {
        if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
      final float[] arr;
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
      final float[] arr;
      int arrLength=(arr=this.arr).length-1;
      int removeIndex=tail;
      for(;;--removeIndex)
      {
        if(Float.isNaN(arr[removeIndex]))
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
        if(Float.isNaN(arr[removeIndex]))
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
        if(Float.isNaN(arr[removeIndex]))
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
    private void collapseheadNonFragmented(int modCount,float[] arr,int head,int tail,FloatPredicate filter)
    {
      int dstOffset;
      for(;;)
      {
        if(--tail==head)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          dstOffset=head;
        }
        if(filter.test((float)arr[tail]))
        {
          dstOffset=FloatArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,tail,head,filter);
          break;
        }
      }
      this.head=dstOffset+1;
      this.modCount=modCount+1;
    }
    private void collapsetailNonFragmented(int modCount,float[] arr,int head,int tail,FloatPredicate filter)
    {
      int dstOffset;
      for(;;)
      {
        if(++head==tail)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          dstOffset=tail;
        }
        if(filter.test((float)arr[head]))
        {
          dstOffset=FloatArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter);
          break;
        }
      }
      this.tail=dstOffset-1;
      this.modCount=modCount+1;
    }
    private void collapseheadFragmentedHelper(int modCount,float[] arr,int tail,FloatPredicate filter)
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
        if(filter.test((float)arr[--tail]))
        {
          break;
        }
      }
      this.head=(tail=FloatArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,tail,-1,filter))+1;
    }
    private void collapsetailFragmentedHelper(int modCount,float[] arr,int head,FloatPredicate filter)
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
        if(filter.test((float)arr[head]))
        {
          break;
        }
      }
      this.tail=(head=FloatArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,bound,filter))-1;
    }
    private void collapseheadFragmented(int modCount,float[] arr,int head,int tail,FloatPredicate filter)
    {
      for(int bound=arr.length-1;;)
      {
        if(head==bound)
        {
          collapseheadFragmentedHelper(modCount,arr,tail,filter);
          break;
        }
        if(!filter.test((float)arr[++head]))
        {
          collapseheadFragmentedHelper(modCount,arr,head,tail,filter);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    private void collapsetailFragmented(int modCount,float[] arr,int head,int tail,FloatPredicate filter)
    {
      for(;;)
      {
        if(tail==0)
        {
          collapsetailFragmentedHelper(modCount,arr,head,filter);
          break;
        }
        if(!filter.test((float)arr[--tail]))
        {
          collapsetailFragmentedHelper(modCount,arr,head,tail,filter);
          break;
        }
      }
      this.modCount=modCount+1;
    }
    private void collapseheadFragmentedHelper(int modCount,float[] arr,int head,int tail,FloatPredicate filter)
    {
      for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
      {
        if(filter.test((float)arr[headOffset]))
        {
          for(int tailOffset=0;tailOffset!=tail;++tailOffset)
          {
            if(filter.test((float)arr[tailOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              return;
            }
          }
          this.head=headOffset=FloatArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          arr[headOffset]=arr[head];
          return;
        }
      }
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((float)arr[tailOffset]))
        {
          this.tail=tailOffset=FloatArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter);
          arr[tailOffset]=arr[tail];
          return;
        }
      }
    }
    private void collapsetailFragmentedHelper(int modCount,float[] arr,int head,int tail,FloatPredicate filter)
    {
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((float)arr[tailOffset]))
        {
          for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
          {
            if(filter.test((float)arr[headOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              return;
            }
          }
          this.tail=tailOffset=FloatArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter);
          arr[tailOffset]=arr[tail];
          return;
        }
      }
      for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
      {
        if(filter.test((float)arr[headOffset]))
        {
          this.head=headOffset=FloatArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          arr[headOffset]=arr[head];
          return;
        }
      }
    }
    private void collapseheadandtailFragmentedHelper(int modCount,float[] arr,int head,int tail,int headOffset,int tailOffset,FloatPredicate filter)
    {
      int tailDstOffset=tailOffset;
      int headDstOffset;
      outer:for(;;)
      {
        if(++tailOffset==tail)
        {
          headDstOffset=FloatArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter);
          break;
        }
        final float tailVal;
        if(!filter.test((float)(tailVal=arr[tailOffset])))
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
                numTailSurvivors=FloatArrSeq.markSurvivors(arr,survivors=BitSetUtil.getBitSet(numTailSurvivors),tailOffset,tail,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[tailDstOffset++]=tailVal;
                if(numTailSurvivors!=0)
                {
                  tailDstOffset=FloatArrSeq.pullSurvivorsDown(arr,survivors,tailDstOffset,tailOffset,numTailSurvivors);
                }
              }
              else
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[tailDstOffset++]=tailVal;
              }
              break outer;
            }
            final float headVal;
            if(!filter.test((float)(headVal=arr[headOffset])))
            {
              int numHeadSurvivors,numTailSurvivors;
              if((numHeadSurvivors=(--headOffset)-head)+(numTailSurvivors=tail-++tailOffset)!=0)
              {
                final long[] survivors;
                numTailSurvivors=FloatArrSeq.markSurvivors(arr,survivors=new long[((numTailSurvivors-1)>>>6)+((numHeadSurvivors-1)>>>6)+2],tailOffset,tail,filter);
                numHeadSurvivors=FloatArrSeq.markSurvivorsReverse(arr,survivors,headOffset,head,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                arr[headDstOffset--]=headVal;
                if(numHeadSurvivors!=0)
                {
                  headDstOffset=FloatArrSeq.pullSurvivorsUp(arr,survivors,headDstOffset,headOffset,numHeadSurvivors);
                }
                arr[tailDstOffset++]=tailVal;
                if(numTailSurvivors!=0)
                {
                  tailDstOffset=FloatArrSeq.pullSurvivorsDown(arr,survivors,tailDstOffset,tailOffset,numTailSurvivors);
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
    private boolean collapsebodyFragmented(int modCount,float[] arr,int head,int tail,FloatPredicate filter)
    {
      for(int tailOffset=0;tailOffset!=tail;++tailOffset)
      {
        if(filter.test((float)arr[tailOffset]))
        {
          for(int headOffset=arr.length-1;headOffset!=head;--headOffset)
          {
            if(filter.test((float)arr[headOffset]))
            {
              collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
              this.modCount=modCount+1;
              return true;
            }
          }
          arr[tailOffset=FloatArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter)]=arr[tail];
          this.modCount=modCount+1;
          this.tail=tailOffset;
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    private void collapseheadandtailFragmented(int modCount,float[] arr,int head,int tail,FloatPredicate filter)
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
          else if(filter.test((float)arr[tail=arr.length-1]))
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
        final float tailVal;
        if(!filter.test((float)(tailVal=arr[--tail])))
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
                if(filter.test((float)arr[head=0]))
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
                    if(filter.test((float)arr[head]))
                    {
                      arr[tail=FloatArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter)]=tailVal;
                      break;
                    }
                  }
                }
              }
              this.head=0;
              this.tail=tail;
              break outer;
            }
            final float headVal;
            if(!filter.test((float)(headVal=arr[++head])))
            {
              for(int tailOffset=0;tailOffset!=tail;++tailOffset)
              {
                if(filter.test((float)arr[tailOffset]))
                {
                  for(int headOffset=bound;headOffset!=head;--headOffset)
                  {
                    if(filter.test((float)arr[headOffset]))
                    {
                      collapseheadandtailFragmentedHelper(modCount,arr,head,tail,headOffset,tailOffset,filter);
                      break outer;
                    }
                  }
                  arr[tail=FloatArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,tailOffset,tail,filter)]=tailVal;
                  break inner;
                }
              }
              for(int headOffset=bound;headOffset!=head;--headOffset)
              {
                if(filter.test((float)arr[headOffset]))
                {
                  arr[head=FloatArrSeq.markSurvivorsAndPullUp(new ModCountChecker(modCount),arr,headOffset,head,filter)]=headVal;
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
    private void collapseheadandtailNonFragmented(int modCount,float[] arr,int head,int tail,FloatPredicate filter)
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
        if(!filter.test((float)arr[head]))
        {
          collapsetailNonFragmented(modCount,arr,head,tail,filter);
          this.head=head;
          break;
        }
      }
    }
    private boolean collapsebodyNonFragmented(int modCount,float[] arr,int head,int tail,FloatPredicate filter)
    {
      while(++head!=tail)
      {
        if(filter.test((float)arr[head]))
        {
          int dstOffset;
          this.tail=dstOffset=FloatArrSeq.markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,head,tail,filter);
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
    private boolean uncheckedRemoveIf1(int tail,FloatPredicate filter)
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
    private boolean uncheckedRemoveIfFragmented(int head,int tail,FloatPredicate filter)
    {
      int modCount=this.modCount;
      try
      {
        float[] arr;
        if(filter.test((float)(arr=this.arr)[head]))
        {
          if(filter.test((float)arr[tail]))
          {
            collapseheadandtailFragmented(modCount,arr,head,tail,filter);
          }
          else
          {
            collapseheadFragmented(modCount,arr,head,tail,filter);
          }
          return true;
        }
        else if(filter.test((float)arr[tail]))
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
    boolean uncheckedRemoveIf(int tail,FloatPredicate filter)
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
            float[] arr;
            if(filter.test((float)(arr=this.arr)[head]))
            {
              if(filter.test((float)arr[tail]))
              {
                collapseheadandtailNonFragmented(modCount,arr,head,tail,filter);
              }
              else
              {
                collapseheadNonFragmented(modCount,arr,head,tail,filter);
              }
              return true;
            }
            else if(filter.test((float)arr[tail]))
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
  private void collapseheadNonFragmented(float[] arr,int head,int tail,
  FloatPredicate filter)
  {
    while(++head!=tail)
    {
      final float v;
      if(!filter.test((float)(v=arr[head])))
      {
        if(head!=(head=pullUp(arr,tail,head,
        filter))){arr[head]=v;}
        break;
      }
    }
    this.head=head;
  }
  private void collapsetailNonFragmented(float[] arr,int head,int tail,
  FloatPredicate filter)
  {
    while(--tail!=head)
    {
      final float v;
      if(!filter.test((float)(v=arr[tail])))
      {
        if(tail!=(tail=pullDown(arr,head,tail,
        filter))){arr[tail]=v;}
        break;
      }
    }
    this.tail=tail;
  }
  private void collapseheadFragmented(float[] arr,int head,int tail,FloatPredicate filter)
  {
    int bound=arr.length-1;
    while(head!=bound)
    {
      if(!filter.test((float)arr[++head]))
      {
        for(int srcOffset=bound;srcOffset!=head;--srcOffset)
        {
          if(filter.test((float)arr[srcOffset]))
          {
            arr[srcOffset=FloatArrSeq.pullSurvivorsUp(arr,srcOffset,head+1,filter)]=arr[head];
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
  private void collapsetailFragmented(float[] arr,int head,int tail,FloatPredicate filter)
  {
    while(tail!=0)
    {
      if(!filter.test((float)arr[--tail]))
      {
        for(int srcOffset=0;srcOffset!=tail;++srcOffset)
        {
          if(filter.test((float)arr[srcOffset]))
          {
            arr[srcOffset=FloatArrSeq.pullSurvivorsDown(arr,srcOffset,tail-1,filter)]=arr[tail];
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
  private static  int pullDown(float[] arr,int srcOffset,int srcBound,FloatPredicate filter)
  {
    while(++srcOffset!=srcBound)
    {
      if(filter.test((float)arr[srcOffset]))
      {
        return FloatArrSeq.pullSurvivorsDown(arr,srcOffset,srcBound-1,filter);
      }
    }
    return srcOffset;
  }
  private static  int pullUp(float[] arr,int srcOffset,int srcBound,FloatPredicate filter)
  {
    while(--srcOffset!=srcBound)
    {
      if(filter.test((float)arr[srcOffset]))
      {
        return FloatArrSeq.pullSurvivorsUp(arr,srcOffset,srcBound+1,filter);
      }
    }
    return srcOffset;
  }
  private void collapseheadandtailFragmented(float[] arr,int head,int tail,FloatPredicate filter)
  {
    while(tail!=0)
    {
      if(!filter.test((float)arr[--tail]))
      {
        int bound=arr.length;
        while(++head!=bound)
        {
          if(!filter.test((float)arr[head]))
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
      if(!filter.test((float)arr[tail]))
      {
        while(++head!=tail)
        {
          if(!filter.test((float)arr[head]))
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
  private boolean collapsebodyFragmented(float[] arr,int head,int tail,FloatPredicate filter)
  {
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((float)arr[srcOffset]))
      {
        arr[srcOffset=FloatArrSeq.pullSurvivorsDown(arr,srcOffset,tail-1,filter)]=arr[tail];
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
      if(filter.test((float)arr[srcOffset]))
      {
        arr[srcOffset=FloatArrSeq.pullSurvivorsUp(arr,srcOffset,head+1,filter)]=arr[head];
        this.head=srcOffset;
        return true;
      }
    }
    return false;
  }
  private void collapseheadandtailNonFragmented(float[] arr,int head,int tail,FloatPredicate filter)
  {
    while(++head!=tail)
    {
      if(!filter.test((float)arr[head]))
      {
        this.head=head;
        this.tail=head=pullDown(arr,head,tail,filter)-1;
        return;
      }
    }
    this.tail=-1;
  }
  private boolean collapsebodyNonFragmented(float[] arr,int head,int tail,FloatPredicate filter)
  {
    while(++head!=tail)
    {
      float v;
      if(filter.test((float)(v=arr[head])))
      {
        arr[head=FloatArrSeq.pullSurvivorsDown(arr,head,tail-1,filter)]=v;
        this.tail=head;
        return true;
      }
    }
    return false;
  }
  private boolean uncheckedRemoveIf1(int tail,FloatPredicate filter)
  {
    if(filter.test(arr[tail]))
    {
        this.tail=-1;
        return true;
    }
    return false;
  }
  private boolean uncheckedRemoveIfFragmented(int head,int tail,FloatPredicate filter)
  {
    float[] arr;
    if(filter.test((float)(arr=this.arr)[head]))
    {
      if(filter.test((float)arr[tail]))
      {
        collapseheadandtailFragmented(arr,head,tail,filter);
      }
      else
      {
        collapseheadFragmented(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((float)arr[tail]))
    {
      collapsetailFragmented(arr,head,tail,filter);
      return true;
    }
    return collapsebodyFragmented(arr,head,tail,filter);
  }
  boolean uncheckedRemoveIf(int tail,FloatPredicate filter)
  {
    int head;
    switch(Integer.signum(tail-(head=this.head)))
    {
      case 0:
        return uncheckedRemoveIf1(tail,filter);
      case -1:
        return uncheckedRemoveIfFragmented(head,tail,filter);
      default:
        float[] arr;
        if(filter.test((float)(arr=this.arr)[head]))
        {
          if(filter.test((float)arr[tail]))
          {
            collapseheadandtailNonFragmented(arr,head,tail,filter);
          }
          else
          {
            collapseheadNonFragmented(arr,head,tail,filter);
          }
          return true;
        }
        else if(filter.test((float)arr[tail]))
        {
          collapsetailNonFragmented(arr,head,tail,filter);
          return true;
        }
        return collapsebodyNonFragmented(arr,head,tail,filter);
    }
  }
  private boolean uncheckedcontainsBits(int tail,int bits)
  {
    if(tail<head)
    {
      final float[] arr;
      return FloatArrSeq.uncheckedcontainsBits(arr=this.arr,0,tail+1,bits)
      || FloatArrSeq.uncheckedcontainsBits(arr,head,arr.length,bits);
    }
    return FloatArrSeq.uncheckedcontainsBits(arr,head,tail+1,bits);
  }
  private int uncheckedsearchBits(int tail,int bits)
  {
    final var arr=this.arr;
    int index=1;
    if(tail<head)
    {
      for(final int bound=arr.length;;++index)
      {
        if(bits==Float.floatToRawIntBits(arr[head]))
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
      if(bits==Float.floatToRawIntBits(arr[head]))
      {
        return index;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  boolean fragmentedremoveValBits(int head,int tail,int bits)
  {
    final float[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=head;
    for(;;++removeIndex)
    {
      if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
      if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
  boolean nonfragmentedremoveValBits(int head,int tail,int bits)
  {
    final var arr=this.arr;
    for(int removeIndex=head;;++removeIndex)
    {
      if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
  private boolean uncheckedremoveValBits(int tail,int bits)
  {
    final int head;
    if(tail<(head=this.head))
    {
      return fragmentedremoveValBits(head,tail,bits);
    }
    return nonfragmentedremoveValBits(head,tail,bits);
  }
  boolean fragmentedremoveLastOccurrenceBits(int head,int tail,int bits)
  {
    final float[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=tail;
    for(;;--removeIndex)
    {
      if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
      if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
  boolean nonfragmentedremoveLastOccurrenceBits(int head,int tail,int bits)
  {
    final var arr=this.arr;
    for(int removeIndex=tail;;--removeIndex)
    {
      if(bits==Float.floatToRawIntBits(arr[removeIndex]))
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
  private boolean uncheckedremoveLastOccurrenceBits(int tail,int bits)
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
      final float[] arr;
      return FloatArrSeq.uncheckedcontains0(arr=this.arr,0,tail+1)
      || FloatArrSeq.uncheckedcontains0(arr,head,arr.length);
    }
    return FloatArrSeq.uncheckedcontains0(arr,head,tail+1);
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
    final float[] arr;
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
    final float[] arr;
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
      final float[] arr;
      return FloatArrSeq.uncheckedcontainsNaN(arr=this.arr,0,tail+1)
      || FloatArrSeq.uncheckedcontainsNaN(arr,head,arr.length);
    }
    return FloatArrSeq.uncheckedcontainsNaN(arr,head,tail+1);
  }
  private int uncheckedsearchNaN(int tail)
  {
    final var arr=this.arr;
    int index=1;
    if(tail<head)
    {
      for(final int bound=arr.length;;++index)
      {
        if(Float.isNaN(arr[head]))
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
      if(Float.isNaN(arr[head]))
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
    final float[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=head;
    for(;;++removeIndex)
    {
      if(Float.isNaN(arr[removeIndex]))
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
      if(Float.isNaN(arr[removeIndex]))
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
      if(Float.isNaN(arr[removeIndex]))
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
    final float[] arr;
    int arrLength=(arr=this.arr).length-1;
    int removeIndex=tail;
    for(;;--removeIndex)
    {
      if(Float.isNaN(arr[removeIndex]))
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
      if(Float.isNaN(arr[removeIndex]))
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
      if(Float.isNaN(arr[removeIndex]))
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
