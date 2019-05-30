package omni.impl.seq;
import omni.util.OmniArray;
import omni.api.OmniDeque;
import omni.util.ArrCopy;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import java.util.function.IntFunction;
import omni.util.TypeUtil;
import java.util.function.LongPredicate;
import java.util.function.LongConsumer;
import omni.util.ToStringUtil;
import omni.impl.AbstractLongItr;
import java.util.ConcurrentModificationException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.util.RandomAccess;
public class LongArrDeq implements OmniDeque.OfLong,Externalizable,Cloneable,RandomAccess{
  private static final long serialVersionUID=1L;
  transient long[] arr;
  transient int head;
  transient int tail;
  public LongArrDeq(){
    super();
    this.arr=OmniArray.OfLong.DEFAULT_ARR;
    this.tail=-1;
  }
  public LongArrDeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new long[initialCapacity];
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfLong.DEFAULT_ARR;
    case 0:
    }
    this.tail=-1;
  }
  LongArrDeq(int head,long[] arr,int tail){
    super();
    this.arr=arr;
    this.head=head;
    this.tail=tail;
  }
  @Override public int size(){
    int tail;
    if((tail=this.tail+1)!=0){
      if((tail-=this.head)<=0){
        tail+=arr.length;
      }
    }
    return tail;
  }
  @Override public boolean isEmpty(){
    return this.tail==-1;
  }
  @Override public void forEach(LongConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action);
    }
  }
  @Override public boolean removeIf(LongPredicate filter){
    final int tail;
    if((tail=this.tail)!=-1){
      final int head;
      if(tail<(head=this.head)){
        return fragmentedRemoveIf(head,tail,filter);
      }
      return nonfragmentedRemoveIf(head,tail,filter);
    }
    return false;
  }
  @Override public void clear(){
    this.tail=-1;
  }
  void uncheckedForEach(final int tail,LongConsumer action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      OmniArray.OfLong.ascendingForEach(arr,head,arr.length-1,action);
      head=0;
    }
    OmniArray.OfLong.ascendingForEach(arr,head,tail,action);
  }
  @Override public boolean add(long val){
    addLast(val);
    return true;
  }
  @Override public void addFirst(long val){
    push(val);
  }
  @Override public boolean offerFirst(long val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(long val){
    addLast(val);
    return true;
  }
  @Override public long longElement(){
    return (long)arr[head];
  }
  @Override public long getLastLong(){
    return (long)arr[tail];
  }
  @Override public boolean offer(long val){
    addLast(val);
    return true;
  }
  @Override public boolean contains(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,TypeUtil.castToLong(val));
      }
    }
    return false;
  }
  @Override public boolean contains(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean contains(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean contains(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedcontains(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean contains(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedcontains(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean contains(Object val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        //todo: a pattern-matching switch statement would be great here
        returnFalse:for(;;){
          final long l;
          if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
            l=((Number)val).longValue();
          }else if(val instanceof Float){
            final float f;
            if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            l=(char)val;
          }else if(val instanceof Boolean){
            l=TypeUtil.castToLong((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedcontains(tail,l);
        }
      }
    }
    return false;
  }
  @Override public boolean contains(byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean contains(char val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,TypeUtil.castToLong(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedremoveVal(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedremoveVal(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean remove(Object val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        //todo: a pattern-matching switch statement would be great here
        returnFalse:for(;;){
          final long l;
          if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
            l=((Number)val).longValue();
          }else if(val instanceof Float){
            final float f;
            if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            l=(char)val;
          }else if(val instanceof Boolean){
            l=TypeUtil.castToLong((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedremoveVal(tail,l);
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(char val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,TypeUtil.castToLong(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedremoveLastOccurrence(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedremoveLastOccurrence(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Object val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        //todo: a pattern-matching switch statement would be great here
        returnFalse:for(;;){
          final long l;
          if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
            l=((Number)val).longValue();
          }else if(val instanceof Float){
            final float f;
            if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            l=(char)val;
          }else if(val instanceof Boolean){
            l=TypeUtil.castToLong((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedremoveLastOccurrence(tail,l);
        }
      }
    }
    return false;
  }
  @Override public int search(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,TypeUtil.castToLong(val));
      }
    }
    return -1;
  }
  @Override public int search(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,(val));
      }
    }
    return -1;
  }
  @Override public int search(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,(val));
      }
    }
    return -1;
  }
  @Override public int search(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedsearch(tail,v);
        }
      }
    }
    return -1;
  }
  @Override public int search(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedsearch(tail,v);
        }
      }
    }
    return -1;
  }
  @Override public int search(Object val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        //todo: a pattern-matching switch statement would be great here
        returnFalse:for(;;){
          final long l;
          if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
            l=((Number)val).longValue();
          }else if(val instanceof Float){
            final float f;
            if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            l=(char)val;
          }else if(val instanceof Boolean){
            l=TypeUtil.castToLong((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedsearch(tail,l);
        }
      }
    }
    return -1;
  }
  @Override public long[] toLongArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final long[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new long[size+=arr.length],size-=tail,tail);
      }else{
        dst=new long[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public Long[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final Long[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new Long[size+=arr.length],size-=tail,tail);
      }else{
        dst=new Long[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final double[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new double[size+=arr.length],size-=tail,tail);
      }else{
        dst=new double[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final float[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new float[size+=arr.length],size-=tail,tail);
      }else{
        dst=new float[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long peekLong(){
    if(tail!=-1){
      return (long)(arr[head]);
    }
    return Long.MIN_VALUE;
  }
  @Override public long peekLastLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (long)(arr[tail]);
    }
    return Long.MIN_VALUE;
  }
  @Override public Long peek(){
    if(tail!=-1){
      return (Long)(arr[head]);
    }
    return null;
  }
  @Override public Long peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Long)(arr[tail]);
    }
    return null;
  }
  @Override public double peekDouble(){
    if(tail!=-1){
      return (double)(arr[head]);
    }
    return Double.NaN;
  }
  @Override public double peekLastDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (double)(arr[tail]);
    }
    return Double.NaN;
  }
  @Override public float peekFloat(){
    if(tail!=-1){
      return (float)(arr[head]);
    }
    return Float.NaN;
  }
  @Override public float peekLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (float)(arr[tail]);
    }
    return Float.NaN;
  }
  @Override public long pollLong(){
    int tail;
    if((tail=this.tail)!=-1){
      final long[] arr;
      int head;
      var ret=(long)((arr=this.arr)[head=this.head]);
      if(head==tail){
        this.tail=-1;
        return ret;
      }else if(++head==arr.length){
        head=0;
      }
      this.head=head;
      return ret;
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollLastLong(){
    int tail;
    if((tail=this.tail)!=-1){
      final long[] arr;
      var ret=(long)((arr=this.arr)[tail]);
      if(this.head==tail){
        tail=-1;
      }else if(--tail==-1){
        tail=arr.length-1;
      }
      this.tail=tail;
      return ret;
    }
    return Long.MIN_VALUE;
  }
  @Override public Long poll(){
    int tail;
    if((tail=this.tail)!=-1){
      final long[] arr;
      int head;
      var ret=(Long)((arr=this.arr)[head=this.head]);
      if(head==tail){
        this.tail=-1;
        return ret;
      }else if(++head==arr.length){
        head=0;
      }
      this.head=head;
      return ret;
    }
    return null;
  }
  @Override public Long pollLast(){
    int tail;
    if((tail=this.tail)!=-1){
      final long[] arr;
      var ret=(Long)((arr=this.arr)[tail]);
      if(this.head==tail){
        tail=-1;
      }else if(--tail==-1){
        tail=arr.length-1;
      }
      this.tail=tail;
      return ret;
    }
    return null;
  }
  @Override public double pollDouble(){
    int tail;
    if((tail=this.tail)!=-1){
      final long[] arr;
      int head;
      var ret=(double)((arr=this.arr)[head=this.head]);
      if(head==tail){
        this.tail=-1;
        return ret;
      }else if(++head==arr.length){
        head=0;
      }
      this.head=head;
      return ret;
    }
    return Double.NaN;
  }
  @Override public double pollLastDouble(){
    int tail;
    if((tail=this.tail)!=-1){
      final long[] arr;
      var ret=(double)((arr=this.arr)[tail]);
      if(this.head==tail){
        tail=-1;
      }else if(--tail==-1){
        tail=arr.length-1;
      }
      this.tail=tail;
      return ret;
    }
    return Double.NaN;
  }
  @Override public float pollFloat(){
    int tail;
    if((tail=this.tail)!=-1){
      final long[] arr;
      int head;
      var ret=(float)((arr=this.arr)[head=this.head]);
      if(head==tail){
        this.tail=-1;
        return ret;
      }else if(++head==arr.length){
        head=0;
      }
      this.head=head;
      return ret;
    }
    return Float.NaN;
  }
  @Override public float pollLastFloat(){
    int tail;
    if((tail=this.tail)!=-1){
      final long[] arr;
      var ret=(float)((arr=this.arr)[tail]);
      if(this.head==tail){
        tail=-1;
      }else if(--tail==-1){
        tail=arr.length-1;
      }
      this.tail=tail;
      return ret;
    }
    return Float.NaN;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final T[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=arrConstructor.apply(size+=arr.length),size-=tail,tail);
      }else{
        dst=arrConstructor.apply(size);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return arrConstructor.apply(0);
  }
  @Override public <T> T[] toArray(T[] dst){
    int tail;
    if((tail=this.tail)!=-1){
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=OmniArray.uncheckedArrResize(size+=arr.length,dst),size-=tail,tail);
      }else{
        dst=OmniArray.uncheckedArrResize(size,dst);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public boolean add(boolean val){
    addLast((long)TypeUtil.castToLong(val));
    return true;
  }
  @Override public boolean add(int val){
    addLast((long)val);
    return true;
  }
  @Override public Long getFirst(){
    return longElement();
  }
  @Override public Long peekFirst(){
    return peek();
  }
  @Override public Long pollFirst(){
    return poll();
  }
  @Override public Long removeFirst(){
    return popLong();
  }
  @Override public Long remove(){
    return popLong();
  }
  @Override public boolean removeFirstOccurrence(Object val){
    return remove(val);
  }
  @Override public Long pop(){
    return popLong();
  }
  @Override public Long removeLast(){
    return removeLastLong();
  }
  @Override public void push(Long val){
    push((long)val);
  }
  @Override public boolean offer(Long val){
    addLast((long)val);
    return true;
  }
  @Override public Long element(){
    return longElement();
  }
  @Override public Long getLast(){
    return getLastLong();
  }
  @Override public void addFirst(Long val){
    push((long)val);
  }
  @Override public void addLast(Long val){
    addLast((long)val);
  }
  @Override public boolean add(Long val){
    addLast((long)val);
    return true;
  }
  @Override public boolean offerFirst(Long val){
    push((long)val);
    return true;
  }
  @Override public boolean offerLast(Long val){
    addLast((long)val);
    return true;
  }
  boolean uncheckedremoveVal(int tail
  ,long val
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(index=head,bound=arr.length-1;;++index){
          if(val==(arr[index])){
            int headDist,tailDist;
            if((headDist=index-head)<((tailDist=bound-index)+tail)){
              ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
              this.head=(head>bound)?0:head;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
              arr[bound]=arr[0];
              ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
              this.tail=(--tail==-1)?bound:tail;
            }
            return true;
          }else if(index==bound){
            for(index=0;;++index){
              if(val==(arr[index])){
                int headDist,tailDist;
                if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                  ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                  arr[0]=arr[bound];
                  ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                  this.head=(head>bound)?0:head;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  this.tail=(--tail==-1)?bound:tail;
                }
                return true;
              }else if(index==tail){
                break;
              }
            }
            break;
          }
        }
      }else{
        for(index=head;;++index){
          if(val==(arr[index])){
            int headDist,tailDist;
            if((tailDist=tail-index)<=(headDist=index-head)){
              if(headDist==0){
                this.tail=-1;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                this.tail=tail-1;
              }
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
              this.head=head;
            }
            return true;
          }else if(index==tail){
            break;
          }
        }
      }
    }
    return false;
  }
  boolean uncheckedremoveLastOccurrence(int tail
  ,long val
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(bound=arr.length-1,index=tail;;--index){
          if(val==(arr[index])){
            int headDist,tailDist;
            if((headDist=bound-head)+index+1<(tailDist=tail-index)){
              ArrCopy.semicheckedCopy(arr,0,arr,1,index);
              arr[0]=arr[bound];
              ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
              this.head=(head>bound)?0:head;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
              this.tail=(--tail==-1)?bound:tail;
            }
            return true;
          }else if(index==0){
            for(index=bound;;--index){
              if(val==(arr[index])){
                int headDist,tailDist;
                if((headDist=index-head)<((tailDist=bound-index)+tail)){
                  ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                  this.head=(head>bound)?0:head;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  arr[bound]=arr[0];
                  ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                  this.tail=(--tail==-1)?bound:tail;
                }
                return true;
              }else if(index==tail){
                break;
              }
            }
            break;
          }
        }
      }else{
        for(index=tail;;--index){
          if(val==(arr[index])){
            int headDist,tailDist;
            if((tailDist=tail-index)<=(headDist=index-head)){
              if(headDist==0){
                this.tail=-1;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                this.tail=tail-1;
              }
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
              this.head=head;
            }
            return true;
          }else if(index==head){
            break;
          }
        }
      }
    }
    return false;
  }
  private boolean uncheckedcontains (int tail
  ,long val
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfLong.uncheckedcontains (arr,0,tail,val) || OmniArray.OfLong.uncheckedcontains (arr,head,arr.length-1,val);
    }
    return OmniArray.OfLong.uncheckedcontains (arr,head,tail,val);
  }
  private int uncheckedsearch (int tail
  ,long val
  ){
    final var arr=this.arr;
    int head,prefix;
    if(tail<(head=this.head)){
      int bound;
      for(prefix=head,bound=arr.length;;)
      {
        if(val==(arr[prefix]))
        {
          return prefix-head+1;
        }
        if(++prefix==bound){
          prefix=bound-head;
          head=0;
          break;
        }
      }
    }else{
      prefix=-head;
    }
    for(;;++head){
      if(val==(arr[head]))
      {
        return prefix+head+1;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  @Override public void forEach(Consumer<? super Long> action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override public boolean removeIf(Predicate<? super Long> filter){
    final int tail;
    if((tail=this.tail)!=-1){
      final int head;
      if(tail<(head=this.head)){
        return fragmentedRemoveIf(head,tail,filter::test);
      }
      return nonfragmentedRemoveIf(head,tail,filter::test);
    }
    return false;
  }
  @Override public String toString(){
    final int tail;
    if((tail=this.tail)!=-1){
      return uncheckedToString(tail);
    }
    return "[]";
  }
  @Override public int hashCode(){
    final int tail;
    if((tail=this.tail)!=-1){
      return uncheckedHashCode(tail);
    }
    return 1;
  }
  @Override public long popLong(){
    final long[] arr;
    int head;
    var ret=(long)((arr=this.arr)[head=this.head]);
    if(head==this.tail){
      this.tail=-1;
      return ret;
    }else if(++head==arr.length){
      head=0;
    }
    this.head=head;
    return ret;
  }
  @Override public long removeLastLong(){
    final long[] arr;
    int tail;
    var ret=(long)((arr=this.arr)[tail=this.tail]);
    if(this.head==tail){
      tail=-1;
    }else if(--tail==-1){
      tail=arr.length-1;
    }
    this.tail=tail;
    return ret;
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final long[] dst;
      int size,head;
      LongArrDeq clone;
      if((size=(++tail)-(head=this.head))<=0){
        clone=new LongArrDeq(0,dst=new long[size+=arr.length],size-1);
        ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
      }else{
        clone=new LongArrDeq(0,dst=new long[size],size-1);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return clone;
    }
    return new LongArrDeq();
  }
  private String uncheckedToString(int tail){
    final var arr=this.arr;
    final byte[] buffer;
    int size,head,bufferOffset=1;
    if((size=(++tail)-(head=this.head))<=0){
      int bound;
      if((size+=(bound=arr.length))<=(OmniArray.MAX_ARR_SIZE/22)){(buffer=new byte[size*22])
        [0]=(byte)'[';
        for(;;++bufferOffset){
          buffer[bufferOffset=ToStringUtil.getStringLong(arr[head],buffer,bufferOffset)]=(byte)',';
          buffer[++bufferOffset]=(byte)' ';
          if(++head==bound){
            for(head=0;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' '){
              bufferOffset=ToStringUtil.getStringLong(arr[head],buffer,++bufferOffset);
              if(++head==tail){
                buffer[bufferOffset]=(byte)']';
                return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
              }
            }
          }
        }
      }else{
        final ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]);
        for(;;){
          builder.uncheckedAppendLong(arr[head]);
          builder.uncheckedAppendCommaAndSpace();
          if(++head==bound){
            for(head=0;;builder.uncheckedAppendCommaAndSpace()){
              builder.uncheckedAppendLong(arr[head]);
              if(++head==tail){
                builder.uncheckedAppendChar((byte)']');
                (buffer=builder.buffer)[0]=(byte)'[';
                return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
              }
            }
          }
        }
      }
    }else{
      if(size<=(OmniArray.MAX_ARR_SIZE/22)){(buffer=new byte[size*22])
        [size=OmniArray.OfLong.ascendingToString(arr,head,tail-1,buffer,1)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        OmniArray.OfLong.ascendingToString(arr,head,tail-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
  }
  private int uncheckedHashCode(int tail){
    final long[] arr;
    int head;
    int hash=31+Long.hashCode((arr=this.arr)[head=this.head]);
    if(tail<head){
      for(final int bound=arr.length;;){  
        if(++head==bound){
          hash=hash*31+Long.hashCode(arr[head=0]);
          break;
        }
        hash=(hash*31)+Long.hashCode(arr[head]);
      }
    }
    for(;head!=tail;hash=(hash*31)+Long.hashCode(arr[++head])){}
    return hash;
  }
  @Override public void push(long val){
    long[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfLong.DEFAULT_ARR){
        this.head=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.tail=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.arr=arr=new long[OmniArray.DEFAULT_ARR_SEQ_CAP];
        arr[OmniArray.DEFAULT_ARR_SEQ_CAP-1]=val;
      }else{
        int tail;
        if((tail=this.tail)==-1){
          arr[tail=arr.length-1]=val;
          this.tail=tail;
          this.head=tail;
        }else{
          int head;
          if((head=this.head-1)==tail){
            final long[] newArr;
            int newCap,size;
            this.tail=(newCap=OmniArray.growBy50Pct(head+(size=arr.length)))-1;
            ArrCopy.uncheckedCopy(arr,0,newArr=new long[newCap],newCap-=(++tail),tail);
            ArrCopy.uncheckedCopy(arr,head+1,newArr,head=newCap-(size-=tail),size);
            this.arr=arr=newArr;
            --head;
          }else if(head==-1 && tail==(head=arr.length-1)){
            int newCap;
            this.tail=(newCap=OmniArray.growBy50Pct(++tail))-1;
            ArrCopy.uncheckedCopy(arr,0,arr=new long[newCap],head=newCap-tail,tail);
            this.arr=arr;
            --head;
          }
          arr[head]=val;
          this.head=head;
        }
      }
    }else{
      initFromNullArr(val);
    }
  }
  private void initFromNullArr(long val){
    this.head=0;
    this.tail=0;
    this.arr=new long[]{val};
  }
  @Override public void addLast(long val){
    long[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfLong.DEFAULT_ARR){
        this.head=0;
        this.tail=0;
        this.arr=arr=new long[OmniArray.DEFAULT_ARR_SEQ_CAP];
        arr[0]=val;
      }else{
        int tail;
        if((tail=this.tail)==-1){
          arr[0]=val;
          this.tail=0;
          this.head=0;
        }else{
          int head;
          if(++tail==(head=this.head)){
            this.head=0;
            final long[] newArr;
            (newArr=new long[OmniArray.growBy50Pct(tail=arr.length)])[tail]=val;
            this.tail=tail;
            ArrCopy.uncheckedCopy(arr,head,newArr,0,tail-=head);
            ArrCopy.uncheckedCopy(arr,0,newArr,tail,head);
            this.arr=newArr;
          }else{
            if(tail==arr.length){
              if(head==0){
                ArrCopy.uncheckedCopy(arr,0,arr=new long[OmniArray.growBy50Pct(tail)],0,tail);
                this.arr=arr;
              }else{
                tail=0;
              }
            }
            arr[tail]=val;
            this.tail=tail;
          }
        }
      }
    }else{
      initFromNullArr(val);
    }
  }
  private void eraseHead(){
    int head;
    switch(Integer.signum(this.tail-(head=this.head))){
      case -1:
        this.head=head==arr.length-1?0:head+1;
        return;
      case 0:
        this.tail=-1;
        break;
      default:
        this.head=head+1;
    }
  }
  private void eraseTail(){
    int tail;
    switch(Integer.signum((tail=this.tail)-this.head)){
      case -1:
        this.tail=tail==0?arr.length-1:tail-1;
        return;
      case 0:
        this.tail=-1;
        break;
      default:
        this.tail=tail-1;
    }
  }
  private static abstract class AbstractDeqItr
    extends AbstractLongItr
  {
    transient int cursor;
    AbstractDeqItr(int cursor){
      this.cursor=cursor;
    }
    @Override public boolean hasNext(){
      return this.cursor!=-1;
    }
    abstract void uncheckedForEachRemaining(int cursor,LongConsumer action);
    @Override public void forEachRemaining(LongConsumer action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Long> action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action::accept);
      }
    }
  }
  private static int pullUp(long[] arr,int head,int headDist){
    ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
    return head;
  }
  private static int fragmentedPullUp(long[] arr,int head,int headDist){
    if(headDist==0){
      return 0;
    }else{
      ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
      return head;
    }
  }
  private static int fragmentedPullDown(long[] arr,int arrBound,int tail){
    if(tail==0){
      return arrBound;
    }
    ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
    return tail-1;
  }
  private static class AscendingItr extends AbstractDeqItr
  {
    transient final LongArrDeq root;
    private AscendingItr(LongArrDeq root){
      super(root.tail!=-1?root.head:-1);
      this.root=root;
    }
    private AscendingItr(LongArrDeq root,int cursor){
      super(cursor);
      this.root=root;
    }
    @Override public long nextLong(){
      final long[] arr;
      int cursor;
      final LongArrDeq root;
      final var ret=(long)(arr=(root=this.root).arr)[cursor=this.cursor];
      if(cursor==root.tail){
        cursor=-1;
      }else if(++cursor==arr.length){
        cursor=0;
      }
      this.cursor=cursor;
      return ret;
    }
    private void eraseAtSplit(){
      final int head,tail,headDist,arrBound;
      final LongArrDeq root;
      final long[] arr;
      if((tail=(root=this.root).tail)<(headDist=(arrBound=(arr=root.arr).length-1)-(head=root.head))){
        arr[arrBound]=arr[0];
        root.tail=fragmentedPullDown(arr,arrBound,tail);
        this.cursor=arrBound;
      }else{
        root.head=fragmentedPullUp(arr,head,headDist);
      }
    }
    private void fragmentedAscendingRemove(int head,int lastRet,int tail,LongArrDeq root){
      long[] arr;
      int headDist,tailDist,arrBound=(arr=root.arr).length-1;
      if((headDist=lastRet-head)>=0){
        //index to remove is in head run
        if(headDist<=(tailDist=arrBound-lastRet)+tail+1){
          root.head=pullUp(arr,head,headDist);
        }else{
          ArrCopy.semicheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
          arr[arrBound]=arr[0];
          root.tail=fragmentedPullDown(arr,arrBound,tail);
          this.cursor=lastRet;
        }
      }else{
        if((tailDist=tail-lastRet)<=(headDist=arrBound-head)+lastRet+1){
          ArrCopy.uncheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
          root.tail=tail-1;
          this.cursor=lastRet;
        }else{
          ArrCopy.semicheckedCopy(arr,0,arr,1,lastRet);
          arr[0]=arr[arrBound];
          root.head=fragmentedPullUp(arr,head,headDist);
        }
      }
    }
    private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,LongArrDeq root){
      int headDist,tailDist;
      if((headDist=lastRet-head)<=(tailDist=tail-lastRet)){
        root.head=pullUp(root.arr,head,headDist);
      }else{
        ArrCopy.uncheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
        root.tail=tail-1;
        this.cursor=lastRet;
      }
    }
    @Override public void remove(){
      final int cursor;
      switch(cursor=this.cursor){
        case -1:
          root.eraseTail();
          break;
        case 0:
          eraseAtSplit();
          break;
        default:
          final int head,tail;
          final LongArrDeq root;
          if((tail=(root=this.root).tail)<(head=root.head)){
            fragmentedAscendingRemove(head,cursor-1,tail,root);
          }else{
            nonfragmentedAscendingRemove(head,cursor-1,tail,root);
          }
      }
    }
    @Override void uncheckedForEachRemaining(int cursor,LongConsumer action){
      final LongArrDeq root;
      int tail;
      final var arr=(root=this.root).arr;
      if(cursor>(tail=root.tail)){
        OmniArray.OfLong.ascendingForEach(arr,cursor,arr.length-1,action);
        cursor=0;
      }
      OmniArray.OfLong.ascendingForEach(arr,cursor,tail,action);
      this.cursor=-1;
    }
  }
  private static class DescendingItr extends AscendingItr{
    private DescendingItr(LongArrDeq root){
      super(root,root.tail);
    }
    @Override void uncheckedForEachRemaining(int cursor,LongConsumer action){
      final LongArrDeq root;
      final int head;
      final var arr=(root=this.root).arr;
      if(cursor<(head=root.head)){
        OmniArray.OfLong.descendingForEach(arr,0,cursor,action);
        cursor=arr.length-1;
      }
      OmniArray.OfLong.descendingForEach(arr,head,cursor,action);
      this.cursor=-1;
    }
    @Override public long nextLong(){
      int cursor;
      final LongArrDeq root;
      final var arr=(root=this.root).arr;
      this.cursor=(cursor=this.cursor)==root.head?-1:cursor==0?arr.length-1:cursor-1;
      return (long)arr[cursor];
    }
    private void fragmentedDescendingRemove(int head,int cursor,int tail,LongArrDeq root){
      long[] arr;
      int arrBound;
      if((arrBound=(arr=root.arr).length-1)==cursor){
        //remove index 0
        if(tail<=(cursor=arrBound-head)){
          root.tail=fragmentedPullDown(arr,arrBound,tail);
        }else{
          arr[0]=arr[arrBound];
          root.head=fragmentedPullUp(arr,head,cursor);
          this.cursor=0;
        }
      }else{
        int headDist,tailDist;
        if((headDist=(++cursor)-head)>0){
          //removing from head run
          if(headDist<=(tailDist=arrBound-cursor)+tail+1){
            ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
            root.head=head;
            this.cursor=cursor;
          }else{
            ArrCopy.semicheckedSelfCopy(arr,cursor,cursor+1,tailDist);
            arr[arrBound]=arr[0];
            root.tail=fragmentedPullDown(arr,arrBound,tail);
          }
        }else{
          //removing from tail run
          if((tailDist=tail-cursor)<=(headDist=arrBound-head)+cursor+1){
            ArrCopy.semicheckedSelfCopy(arr,cursor,cursor+1,tailDist);
            root.tail=tail-1;
          }else{
            ArrCopy.uncheckedCopy(arr,0,arr,1,cursor);
            arr[0]=arr[arrBound];
            root.head=fragmentedPullUp(arr,head,headDist);
            this.cursor=cursor;
          }
        }
      }
    }
    private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,LongArrDeq root){
      int tailDist,headDist;
      if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
        ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
        root.tail=tail-1;
      }else{
        long[] arr;
        ArrCopy.uncheckedCopy(arr=root.arr,head,arr,++head,headDist);
        root.head=head;
        this.cursor=lastRet;
      }
    }
    @Override public void remove(){
      int cursor;
      if((cursor=this.cursor)==-1){
        root.eraseHead();
      }else{
        LongArrDeq root;
        int head,tail;
        if((tail=(root=this.root).tail)<(head=root.head)){
          fragmentedDescendingRemove(head,cursor,tail,root);
        }else{
          nonfragmentedDescendingRemove(head,cursor+1,tail,root);
        }
      }
    }
  } 
  @Override public OmniIterator.OfLong iterator(){
    return new AscendingItr(this);
  }
  @Override public OmniIterator.OfLong descendingIterator(){
    return new DescendingItr(this);
  }
  boolean fragmentedRemoveIf(int head,int tail,LongPredicate filter){
    long[] arr;
    if(filter.test((long)(arr=this.arr)[head]))
    {
      if(filter.test((long)arr[tail]))
      {
        fragmentedCollapseHeadAndTail(arr,head,tail,filter);
      }
      else
      {
        fragmentedCollapsehead(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((long)arr[tail]))
    {
      fragmentedCollapsetail(arr,head,tail,filter);
      return true;
    }
    return fragmentedCollapseBody(arr,head,tail,filter);
  }
  private static  int pullDown(long[] arr,int dstOffset,int srcBound,LongPredicate filter){
    for(int srcOffset=dstOffset+1;srcOffset!=srcBound;++srcOffset)
    {
      final long v;
      if(!filter.test((long)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    return dstOffset;
  }
  private static  int pullUp(long[] arr,int dstOffset,int srcBound,LongPredicate filter){
    for(int srcOffset=dstOffset-1;srcOffset!=srcBound;--srcOffset)
    {
      final long v;
      if(!filter.test((long)(v=arr[srcOffset])))
      {
        arr[dstOffset--]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    return dstOffset;
  }
  private void fragmentedCollapseBodyHelper(long[] arr,int head,int tail,LongPredicate filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((long)arr[srcOffset]))
      {
        tail=pullDown(arr,srcOffset,tail,filter);
        break;
      }
    }
    this.tail=tail;
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset)
    {
      if(filter.test((long)arr[srcOffset]))
      {
        head=pullUp(arr,srcOffset,head,filter);
        break;
      }
    }
    this.head=head;
  }
  private void collapseBodyHelper(long[] ar,int head,int tail,LongPredicate filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((long)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((long)arr[midPoint])){
            tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        this.tail=tail;
        return;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((long)arr[midPoint])){
        tail=pullDown(arr,midPoint,tail,filter);
        break;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  private void fragmentedCollapseHeadAndTail(long[] arr,int head,int tail,LongPredicate filter){
    outer:for(;;){
      do{
        if(tail==0){
          for(tail=arr.length-1;tail!=head;--tail){
            if(!filter.test((long)arr[tail])){
              break outer;
            }
          }  
          this.tail=-1;
          return;
        }
      }while(filter.test((long)arr[--tail]));
      for(int bound=arr.length;++head!=bound;){
        if(!filter.test((long)arr[head])){
          fragmentedCollapseBodyHelper(arr,head,tail,filter);
          return;
        }
      }
      head=-1;
      break;
    }
    while(++head!=tail){
      if(!filter.test((long)arr[head])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  private boolean fragmentedCollapseBody(long[] arr,int head,int tail,LongPredicate filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset){
      if(filter.test((long)arr[srcOffset])){
        this.tail=pullDown(arr,srcOffset,tail,filter);
        for(srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
          if(filter.test((long)arr[srcOffset])){
            this.head=pullUp(arr,srcOffset,head,filter);
            break;
          }
        }
        return true;
      }
    }
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
      if(filter.test((long)arr[srcOffset]))
      {
        this.head=pullUp(arr,srcOffset,head,filter);
        return true;
      }
    }
    return false;
  }
  private void fragmentedCollapsehead(long[] arr,int head,int tail,LongPredicate filter){
    for(int bound=arr.length;;)
    {
      if(++head==bound){
        for(head=0;;++head)
        {
          if(head==tail){
            this.head=head;
            return;
          }else if(!filter.test((long)arr[head])){
            collapseBodyHelper(arr,head,tail,filter);
            return;
          }
        }
      }
      if(!filter.test((long)arr[head]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  private void collapsehead(long[] arr,int head,int tail,LongPredicate filter){
    do{
      if(++head==tail){
        this.head=head;
        return;
      }
    }while(filter.test((long)arr[head]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  private void fragmentedCollapsetail(long[] arr,int head,int tail,LongPredicate filter){
    for(;;)
    {
      if(tail==0){
        for(tail=arr.length-1;;--tail)
        {
          if(tail==head){
            this.tail=head;
            return;
          }else if(!filter.test((long)arr[tail])){
            collapseBodyHelper(arr,head,tail,filter);
            return;
          }
        }
      }
      if(!filter.test((long)arr[--tail]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  private void collapsetail(long[] arr,int head,int tail,LongPredicate filter){
    do{
      if(--tail==head){
        this.tail=tail;
        return;
      }
    }while(filter.test((long)arr[tail]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  private void collapseHeadAndTail(long[] arr,int head,int tail,LongPredicate filter){
    do{
      if(++head==tail){
        this.tail=-1;
        return;
      }
    }while(filter.test((long)arr[head]));
    while(--tail!=head){
      if(!filter.test((long)arr[tail])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
    this.head=head;
    this.tail=head;
  }
  private boolean collapseBody(long[] arr,int head,int tail,LongPredicate filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((long)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((long)arr[midPoint])){
            this.tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        return true;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((long)arr[midPoint])){
        this.tail=pullDown(arr,midPoint,tail,filter);
        return true;
      }
    }
    return false;
  }
  boolean nonfragmentedRemoveIf(int head,int tail,LongPredicate filter){
    final long[] arr;
    if(filter.test((long)(arr=this.arr)[head])){
      if(head==tail){
        this.tail=-1;
      }else{
        if(filter.test((long)arr[tail])){
          collapseHeadAndTail(arr,head,tail,filter);
        }else{
          collapsehead(arr,head,tail,filter);
        }
      }
      return true;
    }else if(head!=tail){
      if(filter.test((long)arr[tail])){
        collapsetail(arr,head,tail,filter);
        return true;
      }
      return collapseBody(arr,head,tail,filter);
    }
    return false;
  }
  @Override public void readExternal(ObjectInput input) throws IOException
  {
    int size;
    if((size=input.readInt())!=0){
      long[] arr;
      OmniArray.OfLong.readArray(arr=new long[size],0,--size,input);
      this.tail=size;
      this.head=0;
      this.arr=arr;
    }else{
      this.tail=-1;
    }
  }
  @Override public void writeExternal(ObjectOutput output) throws IOException{
    int tail;
    if((tail=this.tail)!=-1){
      int head,size;
      if((size=(++tail)-(head=this.head))<=0){
        long[] arr;
        output.writeInt(size+(size=(arr=this.arr).length));
        OmniArray.OfLong.writeArray(arr,head,size-1,output);
        OmniArray.OfLong.writeArray(arr,0,tail-1,output);
      }else{
        output.writeInt(size);
        OmniArray.OfLong.writeArray(arr,head,tail-1,output);
      }
    }else{
      output.writeInt(0);
    }
  }
  @Override public boolean equals(Object obj){
      //TODO
      return false;
  }
  public static class Checked extends LongArrDeq{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(int initialCapacity){
      super(initialCapacity);
    }
    Checked(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public boolean equals(Object obj){
      //TODO
      return false;
    }
    private void collapseheadHelper(long[] arr,int tail,LongPredicate filter,int modCount){
      if(tail==0){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.head=0;
        this.tail=0;
      }else{
        for(int head=0;;)
        {
          if(!filter.test((long)arr[head])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }else if(++head==tail){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.tail=tail;
            this.head=head;
            break;
          }
        }
      }
    }
    private void fragmentedCollapseheadHelper(long[] arr,int tail,LongPredicate filter,int modCount){
      if(tail==0){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.head=0;
      }else{
        int head;
        for(head=0;;++head)
        {
          if(head==tail){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.head=head;
            break;
          }else if(!filter.test((long)arr[head])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }
        }
      }
    }
    private void fragmentedCollapsehead(long[] arr,int head,int tail,LongPredicate filter,int modCount){
      int newhead;
      int bound;
      for(newhead=head+1,bound=arr.length-1;;++newhead){
        if(newhead>bound){
          fragmentedCollapseheadHelper(arr,tail,filter,modCount);
          break;
        }
        if(!filter.test((long)arr[newhead])){
          fragmentedCollapseBodyHelper(arr,newhead,tail,filter,modCount);
          break;
        }
      }
    }
    private void collapsehead(long[] arr,int head,int tail,LongPredicate filter,int modCount){
      int srcOffset;
      for(srcOffset=head;++srcOffset!=tail;){
        if(!filter.test((long)arr[srcOffset])){
          collapseBodyHelper(arr,srcOffset,tail,filter,modCount);
          this.modCount=modCount+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.head=srcOffset;
    }
    private void collapsetailHelper(long[] arr,int head,LongPredicate filter,int modCount){
      int tail;
      if((tail=arr.length-1)==head){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.tail=tail;
        this.head=head;
      }else{
        for(;;)
        {
          if(!filter.test((long)arr[tail])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }else if(--tail==head){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.tail=tail;
            this.head=head;
            break;
          }
        }
      }
    }
    private void fragmentedCollapsetailHelper(long[] arr,int head,LongPredicate filter,int modCount){
      int tail;
      if((tail=arr.length-1)==head){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.tail=head;
      }else{
        for(;;--tail)
        {
          if(tail==head){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.tail=tail;
            break;
          }else if(!filter.test((long)arr[tail])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }
        }
      }
    }
    private void fragmentedCollapsetail(long[] arr,int head,int tail,LongPredicate filter,int modCount){
      int newtail;
      for(newtail=tail-1;;--newtail){
        if(newtail==-1){
          fragmentedCollapsetailHelper(arr,head,filter,modCount);
          break;
        }
        if(!filter.test((long)arr[newtail])){
          fragmentedCollapseBodyHelper(arr,head,newtail,filter,modCount);
          break;
        }
      }
    }
    private void collapsetail(long[] arr,int head,int tail,LongPredicate filter,int modCount){
      int srcOffset;
      for(srcOffset=tail;--srcOffset!=head;){
        if(!filter.test((long)arr[srcOffset])){
          collapseBodyHelper(arr,head,srcOffset,filter,modCount);
          this.modCount=modCount+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.tail=srcOffset;
    }
    private static class BigCollapseData extends CollapseData{
      final long[] survivorSet;
      BigCollapseData(long[] arr,int srcOffset,int numLeft,LongPredicate filter,int arrBound){
        assert srcOffset>0;
        assert numLeft>64;
        assert srcOffset+numLeft>=arrBound;
        assert srcOffset<=arrBound;
        var survivorSet=new long[((numLeft-1)>>6)+1];
        numLeft+=(srcOffset-arrBound);
        int wordOffset=-1,survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;
        long word=0L,marker=1L;
        outer: for(;;)
        {
          if(srcOffset!=arrBound)
          {
            for(;;){
              if(filter.test((long)arr[srcOffset])){
                currentRunLength=0;
              }else{
                word|=marker;
                if(currentRunLength==0){
                  currentRunBegin=srcOffset;
                }
                if(currentRunLength==biggestRunLength){
                  survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
                  survivorsAfterBiggestRun=0;
                  biggestRunBegin=currentRunBegin;
                  biggestRunLength=++currentRunLength;
                }else{
                  ++currentRunLength;
                  ++survivorsAfterBiggestRun;
                }
              }
              if(++srcOffset==arrBound)
              {
                if(numLeft==0)
                {
                  survivorSet[++wordOffset]=word;
                  break outer;
                }
                if((marker<<=1)==0L)
                {
                  survivorSet[++wordOffset]=word;
                  word=0L;
                  marker=1L;
                }
                break;
              }
              if((marker<<=1)==0L){
                survivorSet[++wordOffset]=word;
                word=0L;
                marker=1L;
              }
            }
          }
          for(srcOffset=0;;)
          {
            if(filter.test((long)arr[srcOffset])){
              currentRunLength=0;
            }else{
              word|=marker;
              if(currentRunLength==0){
                currentRunBegin=srcOffset;
              }
              if(currentRunLength==biggestRunLength){
                survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
                survivorsAfterBiggestRun=0;
                biggestRunBegin=currentRunBegin;
                biggestRunLength=++currentRunLength;
              }else{
                ++currentRunLength;
                ++survivorsAfterBiggestRun;
              }
            }
            if(++srcOffset==numLeft)
            {
              survivorSet[++wordOffset]=word;
              break outer;
            }
            if((marker<<=1)==0L){
              survivorSet[++wordOffset]=word;
              word=0L;
              marker=1L;
            }
          }
        }
        this.biggestRunBegin=biggestRunBegin;
        this.biggestRunLength=biggestRunLength;
        this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
        this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
        this.survivorSet=survivorSet;
      }
      BigCollapseData(long[] arr,int srcOffset,int numLeft,LongPredicate filter){
        assert srcOffset>=0;
        assert numLeft>64;
        var survivorSet=new long[((numLeft-1)>>6)+1];
        numLeft+=srcOffset;
        for(int wordOffset=-1,survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;;){
          long word=0L,marker=1L;
          do{
            if(filter.test((long)arr[srcOffset])){
              currentRunLength=0;
            }else{
              word|=marker;
              if(currentRunLength==0){
                currentRunBegin=srcOffset;
              }
              if(currentRunLength==biggestRunLength){
                survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
                survivorsAfterBiggestRun=0;
                biggestRunBegin=currentRunBegin;
                biggestRunLength=++currentRunLength;
              }else{
                ++currentRunLength;
                ++survivorsAfterBiggestRun;
              }
            }
            if(++srcOffset==numLeft){
              survivorSet[++wordOffset]=word;
              this.biggestRunBegin=biggestRunBegin;
              this.biggestRunLength=biggestRunLength;
              this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
              this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
              this.survivorSet=survivorSet;
              return;
            }
          }while((marker<<=1)!=0L);
          survivorSet[++wordOffset]=word;
        }
      }
      @Override void fragmentedCollapseBiggestRunInHead(int head,LongArrDeq deq,int tail){
        int overflow;
        int biggestRunEnd;
        int arrLength;
        int biggestRunBegin;
        long[] arr;
        int survivorsBeforeAndAfter=this.survivorsAfterBiggestRun;
        if((overflow=(biggestRunEnd=(biggestRunBegin=this.biggestRunBegin)+this.biggestRunLength)-(arrLength=(arr=deq.arr).length))>=0){
          if(survivorsBeforeAndAfter!=0){
            nonfragmentedPullSurvivorsDown(arr,overflow,biggestRunEnd-head,overflow+=survivorsBeforeAndAfter);
          }
          arr[overflow]=arr[tail];
        }else{
          int newTail;
          switch(Integer.signum(overflow=(newTail=biggestRunEnd+survivorsBeforeAndAfter)-arrLength)){
            case -1:
              if(survivorsBeforeAndAfter!=0){
                if(tail==0){
                  nonfragmentedPullSurvivorsDown(arr,biggestRunEnd,biggestRunEnd-head,newTail);
                }else{
                  fragmentedPullSurvivorsDownToNonFragmented(arr,biggestRunEnd,biggestRunEnd-head,newTail);
                }
              }
              arr[newTail]=arr[tail];
              overflow=newTail;
              break;
            case 0:
              fragmentedPullSurvivorsDownToNonFragmented(arr,biggestRunEnd,biggestRunEnd-head,newTail);             
              arr[0]=arr[tail];
              break;
            default:
              fragmentedPullSurvivorsDown(arr,biggestRunEnd,biggestRunEnd-head,overflow);
              arr[overflow]=arr[tail];
          }
        }
        deq.tail=overflow;
        if((survivorsBeforeAndAfter=this.survivorsBeforeBiggestRun)!=0){
          nonfragmentedPullSurvivorsUp(arr,biggestRunBegin,(biggestRunBegin-head)-3,biggestRunBegin-=survivorsBeforeAndAfter);
        }
        arr[--biggestRunBegin]=arr[head];
        deq.head=biggestRunBegin;
      }
      @Override void fragmentedCollapseBiggestRunInTail(int head,LongArrDeq deq,int tail){
        //TODO
        throw new UnsupportedOperationException();
      }
      private void fragmentedPullSurvivorsDownToNonFragmented(long[] arr,int dstOffset,int numToSkip,int dstBound){
        int wordOffset;
        long[] survivorSet;
        long word=(survivorSet=this.survivorSet)[wordOffset=numToSkip>>6]>>>numToSkip;
        int s=dstOffset+1;
        int srcOffset=s+(((-numToSkip)-1)&63)+1;
        int arrLength=arr.length;
        int numToRetain;
        int srcOverflow;
        for(;;)
        {
          if((numToSkip=Long.numberOfTrailingZeros(word))==64)
          {
            s=srcOffset;
            srcOffset+=64;
            word=survivorSet[++wordOffset];
            continue;
          }
          numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip));
          if((srcOverflow=(s+=numToSkip)-arrLength)>=0)
          {
            ArrCopy.uncheckedCopy(arr,srcOverflow,arr,dstOffset,numToRetain);
            srcOverflow+=numToRetain;
            break;
          }
          int srcBound;
          switch(Integer.signum(srcOverflow=(srcBound=s+numToRetain)-arrLength))
          {
            default:
              //no overflow detected yet
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,s,numToRetain);
              if((dstOffset+=numToRetain)==dstBound)
              {
                return;
              }
              s=srcBound;
              word>>>=numToRetain;
              continue;
            case 1:
              //the source bound overflowed, so wrap around
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,s,srcBound=numToRetain-srcOverflow);
              ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+srcBound,srcOverflow);
              break;
            case 0:
              //the source bound goes right up to arrLength, so the next skip will overflow
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,s,numToRetain);
          }
          break;
        }
        if((dstOffset+=numToRetain)!=dstBound)
        {
          for(srcOffset-=arrLength,word>>>=numToRetain;;)
          {
            if((numToSkip=Long.numberOfTrailingZeros(word))==64)
            {
              srcOverflow=srcOffset;
              srcOffset+=64;
              word=survivorSet[++wordOffset];
              continue;
            }
            ArrCopy.uncheckedCopy(arr,srcOverflow+=numToSkip,arr,dstOffset,numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip)));
            if((dstOffset+=numToRetain)==dstBound)
            {
              return;
            }
            srcOverflow+=numToRetain;
            word>>>=numToRetain;
          }
        }
      }
      private void nonfragmentedPullSurvivorsUp(long[] arr,int dstOffset,int numToSkip,int dstBound){
        int wordOffset;
        long[] survivorSet;
        long word=(survivorSet=this.survivorSet)[wordOffset=numToSkip>>6]<<(-(numToSkip+1));
        for(int s,srcOffset=(s=dstOffset-1)-(((numToSkip)&63)+1);;)
        {
          while((numToSkip=Long.numberOfLeadingZeros(word))!=64)
          {
            ArrCopy.uncheckedCopy(arr,s-=(numToSkip+(numToSkip=Long.numberOfLeadingZeros(~(word<<=numToSkip)))),arr,dstOffset-=numToSkip,numToSkip);
            if(dstOffset==dstBound)
            {
              return;
            }
            //else if(numToSkip==64)
            //{
            //  break;
            //}
            word<<=numToSkip;
          }
          word=survivorSet[--wordOffset];
          s=srcOffset;
          srcOffset-=64;
        }
      }
      private void fragmentedPullSurvivorsDown(long[] arr,int dstOffset,int numToSkip,int dstBound){
        //TODO
        throw new UnsupportedOperationException();
      }
      private void nonfragmentedPullSurvivorsDown(long[] arr,int dstOffset,int numToSkip,int dstBound){
        int wordOffset;
        long[] survivorSet;
        long word=(survivorSet=this.survivorSet)[wordOffset=numToSkip>>6]>>>numToSkip;
        for(int s=dstOffset+1,srcOffset=s+(((-numToSkip)-1)&63)+1;;)
        {
          while((numToSkip=Long.numberOfTrailingZeros(word))!=64)
          {
            ArrCopy.uncheckedSelfCopy(arr,dstOffset,s+=numToSkip,numToSkip=Long.numberOfTrailingZeros(~(word>>>=numToSkip)));
            if((dstOffset+=numToSkip)==dstBound)
            {
              return;
            }
            else if(numToSkip==64)
            {
              break;
            }
            s+=numToSkip;
            word>>>=numToSkip;
          }
          word=survivorSet[++wordOffset];
          s=srcOffset;
          srcOffset+=64;
        }
      }
      @Override void nonfragmentedCollapse(int head,LongArrDeq deq,int tail){
        final var arr=deq.arr;
        int biggestRunBegin;
        int biggestRunEnd=(biggestRunBegin=this.biggestRunBegin)+this.biggestRunLength;
        int survivorsBeforeAndAfter;
        if((survivorsBeforeAndAfter=this.survivorsAfterBiggestRun)!=0){
          nonfragmentedPullSurvivorsDown(arr,biggestRunEnd,biggestRunEnd-head,biggestRunEnd+=survivorsBeforeAndAfter);
        }
        arr[biggestRunEnd]=arr[tail];
        deq.tail=biggestRunEnd;
        if((survivorsBeforeAndAfter=this.survivorsBeforeBiggestRun)!=0){
          nonfragmentedPullSurvivorsUp(arr,biggestRunBegin,(biggestRunBegin-head)-3,biggestRunBegin-=survivorsBeforeAndAfter);
        }
        arr[--biggestRunBegin]=arr[head];
        deq.head=biggestRunBegin;
      }
    }
    private static class SmallCollapseData extends CollapseData{
      private static void fragmentedPullSurvivorsDownHelper(long[] arr,int dstOffset,int srcOffset,long word){
        for(int arrLength=arr.length;;){
          //check to see if a dst bound overflow occurred
          int dstOverflow,numToRetain,dstBound;
          switch(Integer.signum(dstOverflow=(dstBound=dstOffset+(numToRetain=Long.numberOfTrailingZeros(~(word))))-arrLength)){
            default:
              //no dst bound overflow detected yet
              ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numToRetain);
              dstOffset=dstBound;
              srcOffset+=(numToRetain+(dstBound=Long.numberOfTrailingZeros(word>>>=numToRetain)));
              word>>>=dstBound;
              continue;
            case 0:
              //the dst bound goes right up to the array bound. The next copy will wrap
              ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numToRetain);
              dstBound=Long.numberOfTrailingZeros(word>>>=numToRetain);
              break;
            case 1:
              //dst bound overflow detected
              ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,dstOffset=numToRetain-dstOverflow);
              ArrCopy.uncheckedSelfCopy(arr,0,srcOffset+dstOffset,dstOverflow);
              if((dstBound=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
                return;
              }
          }
          finalizeNonfragmentedPullDown(arr,dstOverflow,srcOffset+numToRetain+dstBound,word>>>dstBound);
          return;
        }
      }
      private static void fragmentedPullSurvivorsUpHelper(long[] arr,int dstOffset,int srcOffset,long word)
      {
        for(;;)
        {
          //check to see if dst bound overflow occurred
          int lead0s,dstBound;
          switch(Integer.signum(dstBound=dstOffset-(lead0s=Long.numberOfLeadingZeros(~word))))
          {
            default:
              //no dst bound overflow detected yet
               ArrCopy.uncheckedCopy(arr,srcOffset-=lead0s,arr,dstOffset=dstBound,lead0s);
               srcOffset-=(lead0s=Long.numberOfLeadingZeros(word<<=lead0s));
               word<<=lead0s;
               continue;
            case 0:
              //the dst bound goes right down to zero. The next copy will wrap
              ArrCopy.uncheckedCopy(arr,srcOffset-=lead0s,arr,0,lead0s);
              lead0s=Long.numberOfLeadingZeros(word<<=lead0s);
              dstOffset=arr.length;
              break;
            case -1:
              //dst bound overflow detected
              ArrCopy.uncheckedCopy(arr,srcOffset-dstOffset,arr,0,dstOffset);
              ArrCopy.uncheckedCopy(arr,srcOffset-=lead0s,arr,dstOffset=arr.length+dstBound,-dstBound);
              if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
              {
                return;
              }
          }
          finalizeNonfragmentedPullUp(arr,dstOffset,srcOffset-lead0s,word<<lead0s);
          return;
        }
      }
      private static void fragmentedPullSurvivorsDown(long[] arr,int dstOffset,long word)
      {
        int numToSkip;
        for(int arrLength=arr.length,srcOffset=dstOffset+1+(numToSkip=Long.numberOfTrailingZeros(word));;)
        {
          int srcOverflow;
          if((srcOverflow=srcOffset-arrLength)>=0)
          {
            //the source offset overflowed on a skip
            fragmentedPullSurvivorsDownHelper(arr,dstOffset,srcOverflow,word>>>numToSkip);
            return;
          }
          int srcBound,numToRetain;
          switch(Integer.signum(srcOverflow=(srcBound=srcOffset+(numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip))))-arrLength))
          {
            default:
              //no overflow detected yet
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
              dstOffset+=numToRetain;
              srcOffset=srcBound+(numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain));
              continue;
            case 1:
              //source bound overflowed, check to see if dst overflowed
              int dstOverflow;
              switch(Integer.signum(dstOverflow=(srcBound=dstOffset+numToRetain)-arrLength))
              {
                default:
                  //no dst overflow detected
                  ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,dstOverflow=numToRetain-srcOverflow);
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+dstOverflow,srcOverflow);
                  fragmentedPullSurvivorsDownHelper(arr,srcBound,srcOverflow+(numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain)),word>>>numToSkip);
                  return;
                case 1:
                  //dst overflow detected
                  ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,srcBound=numToRetain-srcOverflow);
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+srcBound,srcBound=srcOverflow-dstOverflow);
                  ArrCopy.uncheckedSelfCopy(arr,0,srcBound,dstOverflow);
                  if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64)
                  {
                    return;
                  }
                  break;
                case 0:
                  //dst bound goes right up to the end, next copy will overflow
                  ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,srcBound=numToRetain-srcOverflow);
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+srcBound,srcOverflow);
                  numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain);
              }
              finalizeNonfragmentedPullDown(arr,dstOverflow,srcOverflow+numToSkip,word>>>numToSkip);
              return;
            case 0:
              //the source bound goes right up to arrLength, so the next skip will overflow
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
              fragmentedPullSurvivorsDownHelper(arr,dstOffset+numToRetain,numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain),word>>>numToSkip);
              return;
          }
        }
      }
      private static void fragmentedPullSurvivorsUp(long[] arr,int dstOffset,long word)
      {
        int lead0s;
        for(int srcOffset=dstOffset-1-(lead0s=Long.numberOfLeadingZeros(word));;)
        {
          if(srcOffset<=0)
          {
            //the source offset overflowed on a skip
            fragmentedPullSurvivorsUpHelper(arr,dstOffset,srcOffset+arr.length,word<<lead0s);
            return;
          }
          int srcBound;
          switch(Integer.signum(srcBound=srcOffset-(lead0s=Long.numberOfLeadingZeros(~(word<<=lead0s)))))
          {
            default:
              //no overflow detected yet
              ArrCopy.uncheckedCopy(arr,srcBound,arr,dstOffset-=lead0s,lead0s);
              srcOffset=srcBound-(lead0s=Long.numberOfLeadingZeros(word<<=lead0s));
              continue;
            case -1:
              //the source bound overflowed, check to see if dst overflowed
              int dstBound;
              switch(Integer.signum(dstBound=dstOffset-lead0s))
              {
                default:
                  //no dst overflow detected
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-srcOffset,srcOffset);
                  ArrCopy.uncheckedCopy(arr,srcOffset=arr.length+srcBound,arr,dstBound,-srcBound);
                  fragmentedPullSurvivorsUpHelper(arr,dstBound,srcOffset-(lead0s=Long.numberOfLeadingZeros(word<<=lead0s)),word<<lead0s);
                  return;
                case -1:
                  //dst overflow detected
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-srcOffset,srcOffset);
                  ArrCopy.uncheckedCopy(arr,srcOffset=(dstOffset=arr.length)-(srcBound=dstBound-srcBound),arr,0,srcBound);
                  ArrCopy.uncheckedCopy(arr,srcOffset+=dstBound,arr,dstOffset+=dstBound,-dstBound);
                  if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
                  {
                    return;
                  }
                  break;
                case 0:
                  //the dst bound goes right to zero, so the next copy will wrap
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-srcOffset,srcOffset);
                  ArrCopy.uncheckedCopy(arr,srcOffset=(dstOffset=arr.length)+srcBound,arr,0,-srcBound);
                  lead0s=Long.numberOfLeadingZeros(word<<=lead0s);
                  break;
              }
              finalizeNonfragmentedPullUp(arr,dstOffset,srcOffset-=lead0s,word<<lead0s);
              return;
            case 0:
              //the source bound goes right to zero, so the next skip will overflow
              ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-=lead0s,lead0s);
              fragmentedPullSurvivorsUpHelper(arr,dstOffset,arr.length-(lead0s=Long.numberOfLeadingZeros(word<<=lead0s)),word<<lead0s);
              return;
          }
        }
      }
      private static void fragmentedPullSurvivorsDownToNonFragmented(long[] arr,int dstOffset,long word){
        int numToRetain,srcOverflow,numToSkip;
        for(int arrLength=arr.length,srcOffset=dstOffset+1+(numToSkip=Long.numberOfTrailingZeros(word));;)
        {
          numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip));
          if((srcOverflow=srcOffset-arrLength)>=0)
          {
            //the source offset overflowed on a skip
            ArrCopy.uncheckedCopy(arr,srcOverflow,arr,dstOffset,numToRetain);
            srcOverflow+=numToRetain;
            break;
          }
          int srcBound;
          switch(Integer.signum(srcOverflow=(srcBound=srcOffset+numToRetain)-arrLength))
          {
            default:
              //no overflow detected yet
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
              if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64)
              {
                return;
              }
              dstOffset+=numToRetain;
              srcOffset=srcBound+numToSkip;
              continue;
            case 1:
              //the source bound overflowed, so wrap around
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,srcBound=numToRetain-srcOverflow);
              ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+srcBound,srcOverflow);
              break;
            case 0:
              //the source bound goes right up to arrLength, so the next skip will overflow
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
          }
          break;
        }
        if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))!=64)
        {
          finalizeFragmentedPullDown(arr,dstOffset+numToRetain,srcOverflow+numToSkip,word>>>numToSkip);
        }
      }
      private static void fragmentedPullSurvivorsUpToNonFragmented(long[] arr,int dstOffset,long word)
      {
        int lead0s,srcOffset;
        for(srcOffset=dstOffset-1-(lead0s=Long.numberOfLeadingZeros(word));;)
        {
          lead0s=Long.numberOfLeadingZeros(~(word<<=lead0s));
          if(srcOffset<=0)
          {
            //the source offset overflowed on a kip
            ArrCopy.uncheckedCopy(arr,srcOffset+=(arr.length-lead0s),arr,dstOffset-=lead0s,lead0s);
            break;
          }
          int srcBound;
          switch(Integer.signum(srcBound=srcOffset-lead0s))
          {
            default:
              //no overflow detected yet
              ArrCopy.uncheckedCopy(arr,srcBound,arr,dstOffset-=lead0s,lead0s);
              if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
              {
                return;
              }
              srcOffset=srcBound-lead0s;
              continue;
            case -1:
              //the source bound overflowed, so wrap around
              ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-srcOffset,srcOffset);
              ArrCopy.uncheckedCopy(arr,srcOffset=arr.length+srcBound,arr,dstOffset-=lead0s,-srcBound);
              break;
            case 0:
              //the source bound goes right to zero, so the next skip will overflow
              ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-=lead0s,lead0s);
              srcOffset=arr.length;
          }
          break;
        }
        if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))!=64)
        {
          finalizeFragmentedPullUp(arr,dstOffset,srcOffset-lead0s,word<<lead0s);
        }
      }
      private static void finalizeNonfragmentedPullUp(long[] arr,int dstOffset,int srcOffset,long word){
        for(;;){
          int lead0s;
          ArrCopy.uncheckedCopy(arr,srcOffset-=(lead0s=Long.numberOfLeadingZeros(~word)),arr,dstOffset-=lead0s,lead0s);
          if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
          {
            return;
          }
          srcOffset-=lead0s;
          word<<=lead0s;
        }
      }
      private static void finalizeNonfragmentedPullDown(long[] arr,int dstOffset,int srcOffset,long word){
        for(;;){
          int numToRetain;
          ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain=Long.numberOfTrailingZeros(~word));
          int numToSkip;
          if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
            return;
          }
          srcOffset+=(numToSkip+numToRetain);
          dstOffset+=numToRetain;
          word>>>=numToSkip;
        }
      }
      private static void finalizeFragmentedPullDown(long[] arr,int dstOffset,int srcOffset,long word){
        for(;;){
          int numToRetain;
          ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numToRetain=Long.numberOfTrailingZeros(~word));
          int numToSkip;
          if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
            return;
          }
          srcOffset+=(numToSkip+numToRetain);
          dstOffset+=numToRetain;
          word>>>=numToSkip;
        }
      }
      private static void finalizeFragmentedPullUp(long[] arr,int dstOffset,int srcOffset,long word)
      {
        for(;;)
        {
          int lead0s;
          ArrCopy.uncheckedCopy(arr,srcOffset-=(lead0s=Long.numberOfLeadingZeros(~word)),arr,dstOffset-=lead0s,lead0s);
          if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
          {
            return;
          }
          srcOffset-=lead0s;
          word<<=lead0s;
        }
      }
      private static void nonfragmentedPullSurvivorsDown(long[] arr,int dstOffset,long word){
        int numToSkip;
        for(int srcOffset=dstOffset+1+(numToSkip=Long.numberOfTrailingZeros(word));;)
        {
          int numToRetain;
          ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip)));
          if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
            return;
          }
          dstOffset+=numToRetain;
          srcOffset+=(numToSkip+numToRetain);
        }
      }
      private static void nonfragmentedPullSurvivorsUp(long[] arr,int dstOffset,long word)
      {
        int lead0s;
        if((lead0s=Long.numberOfLeadingZeros(word))!=64)
        {
          for(int srcOffset=dstOffset-1-lead0s;;)
          {
            ArrCopy.uncheckedCopy(arr,srcOffset-=(lead0s=Long.numberOfLeadingZeros(~(word<<=lead0s))),arr,dstOffset-=lead0s,lead0s);
            if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
            {
              return;
            }
            srcOffset-=lead0s;
          }
        }
      }
      final long survivorWord;
      SmallCollapseData(long[] arr,int head,int tail,LongPredicate filter){
        assert head>=0;
        assert tail+1-head<=64;
        int survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;
        for(long word=0L,marker=1L;;++head,marker<<=1){
          if(filter.test((long)arr[head])){
            currentRunLength=0;
          }else{
            word|=marker;
            if(currentRunLength==0){
              currentRunBegin=head;
            }
            if(currentRunLength==biggestRunLength){
              survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
              survivorsAfterBiggestRun=0;
              biggestRunBegin=currentRunBegin;
              biggestRunLength=++currentRunLength;
            }else{
              ++currentRunLength;
              ++survivorsAfterBiggestRun;
            }
          }
          if(head==tail){
            this.survivorWord=word;
            this.biggestRunBegin=biggestRunBegin;
            this.biggestRunLength=biggestRunLength;
            this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
            this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
            return;
          }
        }
      }
      SmallCollapseData(long[] arr,int head,int tail,LongPredicate filter,int arrBound){
        assert tail<head;
        assert tail>=0;
        assert head<=arrBound;
        assert tail-head+arrBound<=64;
        int survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;
        for(long word=0L,marker=1L;;++head,marker<<=1){
          if(head==arrBound){
            for(head=0;;++head,marker<<=1){
              if(head==tail){
                this.survivorWord=word;
                this.biggestRunBegin=biggestRunBegin;
                this.biggestRunLength=biggestRunLength;
                this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
                this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
                return;
              }
              if(filter.test((long)arr[head])){
                currentRunLength=0;
              }else{
                word|=marker;
                if(currentRunLength==0){
                  currentRunBegin=head;
                }
                if(currentRunLength==biggestRunLength){
                  survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
                  survivorsAfterBiggestRun=0;
                  biggestRunBegin=currentRunBegin;
                  biggestRunLength=++currentRunLength;
                }else{
                  ++currentRunLength;
                  ++survivorsAfterBiggestRun;
                }
              }
            }
          }
          if(filter.test((long)arr[head])){
            currentRunLength=0;
          }else{
            word|=marker;
            if(currentRunLength==0){
              currentRunBegin=head;
            }
            if(currentRunLength==biggestRunLength){
              survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
              survivorsAfterBiggestRun=0;
              biggestRunBegin=currentRunBegin;
              biggestRunLength=++currentRunLength;
            }else{
              ++currentRunLength;
              ++survivorsAfterBiggestRun;
            }
          }
        }
      }
      @Override void fragmentedCollapseBiggestRunInTail(int head,LongArrDeq deq,int tail){
        int biggestRunBegin;
        int biggestRunEnd=(biggestRunBegin=this.biggestRunBegin)+this.biggestRunLength;
        long[] arr;
        int arrLength;
        int overflow=(arrLength=(arr=deq.arr).length)-head;
        int survivorsBeforeAndAfter;
        if((survivorsBeforeAndAfter=this.survivorsAfterBiggestRun)!=0)
        {
          nonfragmentedPullSurvivorsDown(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd+overflow));
        }
        arr[biggestRunEnd+=survivorsBeforeAndAfter]=arr[tail];
        deq.tail=biggestRunEnd;
        switch(Integer.signum(biggestRunEnd=biggestRunBegin-(survivorsBeforeAndAfter=this.survivorsBeforeBiggestRun)))
        {
          case 1:
            //no overflow detected
            if(survivorsBeforeAndAfter!=0)
            {
              if(overflow==1)
              {
                nonfragmentedPullSurvivorsUp(arr,biggestRunBegin,this.survivorWord<<(1-biggestRunBegin));
              }
              else
              {
                fragmentedPullSurvivorsUpToNonFragmented(arr,biggestRunBegin,this.survivorWord<<(2-(biggestRunBegin+overflow)));
              }
            }
            arr[--biggestRunEnd]=arr[head];
            break;
          case 0:
            if(survivorsBeforeAndAfter!=0)
            {
              fragmentedPullSurvivorsUpToNonFragmented(arr,biggestRunBegin,this.survivorWord<<(2-(biggestRunBegin+overflow)));
            }
            arr[biggestRunEnd=arrLength-1]=arr[head];
            break;
          default:
            if(biggestRunBegin==0)
            {
              nonfragmentedPullSurvivorsUp(arr,arrLength,this.survivorWord<<(2-overflow));
            }
            else
            {
              fragmentedPullSurvivorsUp(arr,biggestRunBegin,this.survivorWord<<(2-(biggestRunBegin+overflow)));
            }
            arr[biggestRunEnd+=(arrLength-1)]=arr[head];
        }
        deq.head=biggestRunEnd;
      }
      @Override void fragmentedCollapseBiggestRunInHead(int head,final LongArrDeq deq,int tail){
        int overflow;
        int biggestRunEnd;
        int arrLength;
        int biggestRunBegin;
        long[] arr;
        int survivorsBeforeAndAfter=this.survivorsAfterBiggestRun;
        if((overflow=(biggestRunEnd=(biggestRunBegin=this.biggestRunBegin)+this.biggestRunLength)-(arrLength=(arr=deq.arr).length))>=0){
          if(survivorsBeforeAndAfter!=0){
            nonfragmentedPullSurvivorsDown(arr,overflow,this.survivorWord>>>(biggestRunEnd-head));
          }
          arr[overflow+=survivorsBeforeAndAfter]=arr[tail];
        }else{
          int newTail;
          switch(Integer.signum(overflow=(newTail=biggestRunEnd+survivorsBeforeAndAfter)-arrLength)){
            case -1:
              if(survivorsBeforeAndAfter!=0){
                if(tail==0){
                  nonfragmentedPullSurvivorsDown(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd-head));
                }else{
                  fragmentedPullSurvivorsDownToNonFragmented(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd-head));
                }
              }
              arr[newTail]=arr[tail];
              overflow=newTail;
              break;
            case 0:
              fragmentedPullSurvivorsDownToNonFragmented(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd-head));             
              arr[0]=arr[tail];
              break;
            default:
              fragmentedPullSurvivorsDown(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd-head));
              arr[overflow]=arr[tail];
          }
        }
        deq.tail=overflow;
        if((survivorsBeforeAndAfter=this.survivorsBeforeBiggestRun)!=0){
          nonfragmentedPullSurvivorsUp(arr,biggestRunBegin,this.survivorWord<<(2-(biggestRunBegin-head)));
        }
        arr[biggestRunBegin-=(survivorsBeforeAndAfter+1)]=arr[head];
        deq.head=biggestRunBegin;
      }
      @Override void nonfragmentedCollapse(int head,LongArrDeq deq,int tail)
      {
        final var arr=deq.arr;
        int biggestRunBegin;
        int biggestRunEnd=(biggestRunBegin=this.biggestRunBegin)+this.biggestRunLength;
        int survivorsBeforeAndAfter;
        if((survivorsBeforeAndAfter=this.survivorsAfterBiggestRun)!=0){
          nonfragmentedPullSurvivorsDown(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd-head));
        }
        arr[biggestRunEnd+=survivorsBeforeAndAfter]=arr[tail];
        deq.tail=biggestRunEnd;
        if((survivorsBeforeAndAfter=this.survivorsBeforeBiggestRun)!=0){
          nonfragmentedPullSurvivorsUp(arr,biggestRunBegin,this.survivorWord<<(2-(biggestRunBegin-head)));
        }
        arr[biggestRunBegin-=(survivorsBeforeAndAfter+1)]=arr[head];
        deq.head=biggestRunBegin;
      }
    }
    private static abstract class CollapseData{
      int survivorsBeforeBiggestRun;
      int survivorsAfterBiggestRun;
      int biggestRunBegin;
      int biggestRunLength;
      abstract void fragmentedCollapseBiggestRunInHead(int head,LongArrDeq deq,int tail);
      abstract void fragmentedCollapseBiggestRunInTail(int head,LongArrDeq deq,int tail);
      abstract void nonfragmentedCollapse(int head,LongArrDeq deq,int tail);
      private int getNumSurvivors(){
        return this.survivorsBeforeBiggestRun+this.biggestRunLength+this.survivorsAfterBiggestRun;
      }
    }
    private void collapseBodyHelper(long[] arr,int head,int tail,LongPredicate filter,int modCount){
      int numLeft,srcOffset;
      if((numLeft=tail-(srcOffset=head+1))!=0){
        CollapseData collapseData;
        if(numLeft>64){
          collapseData=new BigCollapseData(arr,srcOffset,numLeft,filter);
        }else{
          collapseData=new SmallCollapseData(arr,srcOffset,tail-1,filter);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        int numSurvivors;
        if((numSurvivors=collapseData.getNumSurvivors())!=numLeft){
          if(numSurvivors==0){
            nonfragmentedCollapseAll(head,tail);
          }else{
            collapseData.nonfragmentedCollapse(head,this,tail);
          }
          return;
        }
      }else{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.head=head;
      this.tail=tail;
    }
    private void fragmentedCollapseAll(int head,int tail){
      this.tail=tail;
      final var arr=this.arr;
      if(tail==0){
        arr[tail=arr.length-1]=arr[head];
        this.head=tail;
      }else{
        arr[--tail]=arr[head];
        this.head=tail;
      }
    }
    private void nonfragmentedCollapseAll(int head,int tail){
      this.head=head;
      this.tail=++head;
      long[] arr;
      (arr=this.arr)[head]=arr[tail];
    }
    private void fragmentedCollapseBodyHelper(long[] arr,int head,int tail,LongPredicate filter,int modCount){
      int numLeft,arrLength,srcOffset;
      if((numLeft=tail-(srcOffset=head+1)+(arrLength=arr.length))!=0){
        CollapseData collapseData;
        if(numLeft>64){
          collapseData=new BigCollapseData(arr,srcOffset,numLeft,filter,arrLength);
        }else{
          collapseData=new SmallCollapseData(arr,srcOffset,tail,filter,arrLength);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        int numSurvivors;
        if((numSurvivors=collapseData.getNumSurvivors())!=numLeft){
          if(numSurvivors==0){
            fragmentedCollapseAll(head,tail);
          }else{
            if(collapseData.biggestRunBegin>head){
              collapseData.fragmentedCollapseBiggestRunInHead(head,this,tail);
            }else{
              collapseData.fragmentedCollapseBiggestRunInTail(head,this,tail);
            }
          }
          return;
        }
      }else{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.head=head;
      this.tail=tail;
    }
    private boolean collapseBody(long[] arr,int head,int tail,LongPredicate filter,int modCount){
      int numLeft,srcOffset;
      if((numLeft=tail-(srcOffset=head+1))!=0){
        CollapseData collapseData;
        if(numLeft>64){
          collapseData=new BigCollapseData(arr,srcOffset,numLeft,filter);
        }else{
          collapseData=new SmallCollapseData(arr,srcOffset,tail-1,filter);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        int numSurvivors;
        if((numSurvivors=collapseData.getNumSurvivors())!=numLeft){
          if(numSurvivors==0){
            nonfragmentedCollapseAll(head,tail);
          }else{
            collapseData.nonfragmentedCollapse(head,this,tail);
          }
          this.modCount=modCount+1;
          return true;
        }
      }else{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      return false;
    }
    private boolean fragmentedCollapseBody(long[] arr,int head,int tail,LongPredicate filter,int modCount){
      int numLeft,arrLength,srcOffset;
      if((numLeft=tail-(srcOffset=head+1)+(arrLength=arr.length))!=0){
        CollapseData collapseData;
        if(numLeft>64){
          collapseData=new BigCollapseData(arr,srcOffset,numLeft,filter,arrLength);
        }else{
          collapseData=new SmallCollapseData(arr,srcOffset,tail,filter,arrLength);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        int numSurvivors;
        if((numSurvivors=collapseData.getNumSurvivors())!=numLeft){
          if(numSurvivors==0){
            fragmentedCollapseAll(head,tail);
          }else{
            if(collapseData.biggestRunBegin>head){
              collapseData.fragmentedCollapseBiggestRunInHead(head,this,tail);
            }else{
              collapseData.fragmentedCollapseBiggestRunInTail(head,this,tail);
            }
          }
          this.modCount=modCount+1;
          return true;
        }
      }else{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      return false;
    }
    private void fragmentedCollapseHeadAndTail(long[] arr,int head,int tail,LongPredicate filter,int modCount){
      int newTail=tail-1,newHead=head+1,bound=arr.length-1;
      for(;;--newTail){
        if(newTail==-1){
          for(;;++newHead){
            if(newHead>bound){
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.tail=-1;
              break;
            }else if(!filter.test((long)arr[newHead])){
              collapsetailHelper(arr,newHead,filter,modCount);
              break;
            }
          }
          break;
        }else if(!filter.test((long)arr[newTail])){
          for(;;++newHead){
            if(newHead>bound){
              collapseheadHelper(arr,newTail,filter,modCount);
              break;
            }else if(!filter.test((long)arr[newHead])){
              fragmentedCollapseBodyHelper(arr,newHead,newTail,filter,modCount);
              break;
            }
          }
          break;
        }
      }
    }
    private void collapseHeadAndTail(long[] arr,int head,int tail,LongPredicate filter,int modCount){
      for(int headOffset=head+1;headOffset!=tail;++headOffset){
        if(!filter.test((long)arr[headOffset])){
          for(int tailOffset=tail-1;tailOffset!=headOffset;--tailOffset){
            if(!filter.test((long)arr[tailOffset])){
              collapseBodyHelper(arr,headOffset,tailOffset,filter,modCount);
              return;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.head=headOffset;
          this.tail=headOffset;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.tail=-1;
    }
    @Override boolean fragmentedRemoveIf(int head,int tail,LongPredicate filter){
      int modCount=this.modCount;
      try{
        final long[] arr;
        if(filter.test((long)(arr=this.arr)[head])){
          if(filter.test((long)arr[tail])){
            fragmentedCollapseHeadAndTail(arr,head,tail,filter,modCount);
          }else{
            fragmentedCollapsehead(arr,head,tail,filter,modCount);
          }
          this.modCount=modCount+1;
          return true;
        }else{
          if(filter.test((long)arr[tail])){
            fragmentedCollapsetail(arr,head,tail,filter,modCount);
            this.modCount=modCount+1;
            return true;
          }
          return fragmentedCollapseBody(arr,head,tail,filter,modCount);
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @Override boolean nonfragmentedRemoveIf(int head,int tail,LongPredicate filter){
      final int modCount=this.modCount;
      try{
        final long[] arr;
        if(filter.test((long)(arr=this.arr)[head])){
          if(head==tail){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.tail=-1;
          }else if(filter.test((long)arr[tail])){
            collapseHeadAndTail(arr,head,tail,filter,modCount);
          }else{
            collapsehead(arr,head,tail,filter,modCount);
          }
          this.modCount=modCount+1;
          return true;
        }else if(head!=tail){
          if(filter.test((long)arr[tail])){
            collapsetail(arr,head,tail,filter,modCount);
            this.modCount=modCount+1;
            return true;
          }
          return collapseBody(arr,head,tail,filter,modCount);
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override public OmniIterator.OfLong iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniIterator.OfLong descendingIterator(){
      return new DescendingItr(this);
    }
    private static class AscendingItr extends AbstractDeqItr{
      transient int modCount;
      transient int lastRet;
      transient final Checked root;
      private AscendingItr(Checked root){
        super(root.tail==-1?-1:root.head);
        this.root=root;
        this.modCount=root.modCount;
        this.lastRet=-1;
      }
      private AscendingItr(Checked root,int cursor){
        super(cursor);
        this.root=root;
        this.modCount=root.modCount;
        this.lastRet=-1;
      }
      @Override public long nextLong(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final long[] arr;
          final var ret=(long)(arr=root.arr)[cursor];
          this.lastRet=cursor;
          if(cursor==root.tail){
            cursor=-1;
          }else if(++cursor==arr.length){
            cursor=0;
          }
          this.cursor=cursor;
          return ret;
        }
        throw new NoSuchElementException();
      }
      private void fragmentedAscendingRemove(int head,int lastRet,int tail,LongArrDeq root){
        long[] arr;
        int headDist,tailDist,arrBound=(arr=root.arr).length-1;
        if((headDist=lastRet-head)>=0){
          //index to remove is in head run
          if(headDist<=(tailDist=arrBound-lastRet)+tail+1){
            if(headDist==0){
              if(tailDist==0){
                root.head=0;
              }
              else{
                root.head=head+1;
              }
            }else{
              ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
              root.head=head;
            }
          }else{
            ArrCopy.semicheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
            arr[arrBound]=arr[0];
            root.tail=fragmentedPullDown(arr,arrBound,tail);
            this.cursor=lastRet;
          }
        }else{
          if((tailDist=tail-lastRet)<=(headDist=arrBound-head)+lastRet+1){
            if(tailDist==0){
              if(lastRet==0){
                root.tail=arrBound;
              }else{
                root.tail=tail-1;
              }
            }else{
              ArrCopy.uncheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
              root.tail=tail-1;
              this.cursor=lastRet;
            }
          }else{
            ArrCopy.semicheckedCopy(arr,0,arr,1,lastRet);
            arr[0]=arr[arrBound];
            root.head=fragmentedPullUp(arr,head,headDist);
          }
        }
      }
      private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,LongArrDeq root){
        int headDist,tailDist;
        if((headDist=lastRet-head)<=(tailDist=tail-lastRet)){
          root.head=pullUp(root.arr,head,headDist);
        }else{
          if(tailDist!=0)
          {
            ArrCopy.uncheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
            this.cursor=lastRet;
          }
          root.tail=tail-1;
        }
      }
      @Override public void remove(){
        int lastRet;
        if((lastRet=this.lastRet)!=-1){
          int modCount;
          final Checked root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          final int head,tail;
          switch(Integer.signum((tail=root.tail)-(head=root.head))){
            case -1:
              fragmentedAscendingRemove(head,lastRet,tail,root);
              break;
            case 0:
              root.tail=-1;
              break;
            default:
              nonfragmentedAscendingRemove(head,lastRet,tail,root);
          }
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override void uncheckedForEachRemaining(int cursor,LongConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int tail=(root=this.root).tail;
        try{
          final var arr=root.arr;
          if(cursor>tail){
            OmniArray.OfLong.ascendingForEach(arr,cursor,arr.length-1,action);
            cursor=0;
          }
          OmniArray.OfLong.ascendingForEach(arr,cursor,tail,action);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        this.lastRet=tail;
        this.cursor=-1;
      }
    }
    private static class DescendingItr extends AscendingItr{
      private DescendingItr(Checked root){
        super(root,root.tail);
      }
      @Override public long nextLong(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final long[] arr;
          final var ret=(long)(arr=root.arr)[cursor];
          this.lastRet=cursor;
          if(cursor==root.head){
            cursor=-1;
          }else if(--cursor==-1){
            cursor=arr.length-1;
          }
          this.cursor=cursor;
          return ret;
        }
        throw new NoSuchElementException();
      }
      private void fragmentedDescendingRemove(int head,int lastRet,int tail,LongArrDeq root){
        long[] arr;
        int headDist,tailDist,arrBound=(arr=root.arr).length-1;
        if((headDist=lastRet-head)>=0){
          if(headDist<=(tailDist=arrBound-lastRet)+tail+1){
            if(headDist==0){
              if(lastRet==arrBound){
                root.head=0;
              }else{
                root.head=head+1;
              }
            }else{
              ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
              root.head=head;
              this.cursor=lastRet;
            }
          }else{
            ArrCopy.semicheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
            arr[arrBound]=arr[0];
            root.tail=fragmentedPullDown(arr,arrBound,tail);
          }
        }else{
          if((tailDist=tail-lastRet)<=(headDist=arrBound-head)+lastRet+1){
            if(tailDist==0){
              if(lastRet==0){
                root.tail=arrBound;
              }else{
                root.tail=tail-1;
              }
            }else{
              ArrCopy.uncheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
              root.tail=tail-1;
            }
          }else{
            ArrCopy.semicheckedCopy(arr,0,arr,1,lastRet);
            arr[0]=arr[arrBound];
            root.head=fragmentedPullUp(arr,head,headDist);
            this.cursor=lastRet;
          }
        }
      }
      private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,LongArrDeq root){
        int tailDist,headDist;
        if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
          ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
          root.tail=tail-1;
        }else{
          if(headDist==0){
            root.head=head+1;
          }else{
            long[] arr;
            ArrCopy.uncheckedCopy(arr=root.arr,head,arr,++head,headDist);
            this.cursor=lastRet;
            root.head=head;
          }
        }
      }
      @Override public void remove(){
        int lastRet;
        if((lastRet=this.lastRet)!=-1){
          int modCount;
          final Checked root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          final int head,tail;
          switch(Integer.signum((tail=root.tail)-(head=root.head))){
            case -1:
              fragmentedDescendingRemove(head,lastRet,tail,root);
              break;
            case 0:
              root.tail=-1;
              break;
            default:
              nonfragmentedDescendingRemove(head,lastRet,tail,root);
          }
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override void uncheckedForEachRemaining(int cursor,LongConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int head=(root=this.root).head;
        try{
          final var arr=root.arr;
          if(cursor<head){
            OmniArray.OfLong.descendingForEach(arr,0,cursor,action);
            cursor=arr.length-1;
          }
          OmniArray.OfLong.descendingForEach(arr,head,cursor,action);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        this.lastRet=head;
        this.cursor=-1;
      }
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final var arr=this.arr;
        final long[] dst;
        int size,head;
        Checked clone;
        if((size=(++tail)-(head=this.head))<=0){
          clone=new Checked(0,dst=new long[size+=arr.length],size-1);
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
        }else{
          clone=new Checked(0,dst=new long[size],size-1);
        }
        ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        return clone;
      }
      return new Checked();
    }
    @Override public long removeLastLong(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] arr;
        var ret=(long)((arr=this.arr)[tail]);
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public long popLong(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] arr;
        int head;
        var ret=(long)((arr=this.arr)[head=this.head]);
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public void writeExternal(ObjectOutput output) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(output);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void clear(){
      if(this.tail!=-1){
        ++this.modCount;
        this.tail=-1;
      }
    }
    @Override public void push(long val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void addLast(long val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override void uncheckedForEach(final int tail,LongConsumer action){
      final int modCount=this.modCount;
      try{
        super.uncheckedForEach(tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public long longElement(){
      if(tail!=-1){
        return (long)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override public long getLastLong(){
      final int tail;
      if((tail=this.tail)!=-1){
        return (long)arr[tail];
      }
      throw new NoSuchElementException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public long pollLong(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] arr;
        int head;
        var ret=(long)((arr=this.arr)[head=this.head]);
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] arr;
        var ret=(long)((arr=this.arr)[tail]);
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public Long poll(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] arr;
        int head;
        var ret=(Long)((arr=this.arr)[head=this.head]);
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      return null;
    }
    @Override public Long pollLast(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] arr;
        var ret=(Long)((arr=this.arr)[tail]);
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] arr;
        int head;
        var ret=(double)((arr=this.arr)[head=this.head]);
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] arr;
        var ret=(double)((arr=this.arr)[tail]);
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] arr;
        int head;
        var ret=(float)((arr=this.arr)[head=this.head]);
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] arr;
        var ret=(float)((arr=this.arr)[tail]);
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    boolean uncheckedremoveVal(int tail
    ,long val
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(index=head,bound=arr.length-1;;++index){
            if(val==(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((headDist=index-head)<((tailDist=bound-index)+tail)){
                ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                this.head=(head>bound)?0:head;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                arr[bound]=arr[0];
                ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                this.tail=(--tail==-1)?bound:tail;
              }
              return true;
            }else if(index==bound){
              for(index=0;;++index){
                if(val==(arr[index])){
                  this.modCount=modCount+1;
                  int headDist,tailDist;
                  if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                    ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                    arr[0]=arr[bound];
                    ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                    this.head=(head>bound)?0:head;
                  }else{
                    ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                    this.tail=(--tail==-1)?bound:tail;
                  }
                  return true;
                }else if(index==tail){
                  break;
                }
              }
              break;
            }
          }
        }else{
          for(index=head;;++index){
            if(val==(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((tailDist=tail-index)<=(headDist=index-head)){
                if(headDist==0){
                  this.tail=-1;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  this.tail=tail-1;
                }
              }else{
                ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                this.head=head;
              }
              return true;
            }else if(index==tail){
              break;
            }
          }
        }
      }
      return false;
    }
    @Override
    boolean uncheckedremoveLastOccurrence(int tail
    ,long val
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(bound=arr.length-1,index=tail;;--index){
            if(val==(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                arr[0]=arr[bound];
                ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                this.head=(head>bound)?0:head;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                this.tail=(--tail==-1)?bound:tail;
              }
              return true;
            }else if(index==0){
              for(index=bound;;--index){
                if(val==(arr[index])){
                  this.modCount=modCount+1;
                  int headDist,tailDist;
                  if((headDist=index-head)<((tailDist=bound-index)+tail)){
                    ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                    this.head=(head>bound)?0:head;
                  }else{
                    ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                    arr[bound]=arr[0];
                    ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                    this.tail=(--tail==-1)?bound:tail;
                  }
                  return true;
                }else if(index==tail){
                  break;
                }
              }
              break;
            }
          }
        }else{
          for(index=tail;;--index){
            if(val==(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((tailDist=tail-index)<=(headDist=index-head)){
                if(headDist==0){
                  this.tail=-1;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  this.tail=tail-1;
                }
              }else{
                ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
                this.head=head;
              }
              return true;
            }else if(index==head){
              break;
            }
          }
        }
      }
      return false;
    }
  }
}
