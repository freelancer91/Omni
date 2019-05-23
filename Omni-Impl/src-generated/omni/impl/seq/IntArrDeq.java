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
import java.util.function.IntPredicate;
import java.util.function.IntConsumer;
import omni.util.ToStringUtil;
import omni.impl.AbstractIntItr;
import java.util.ConcurrentModificationException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.util.RandomAccess;
public class IntArrDeq implements OmniDeque.OfInt,Externalizable,Cloneable,RandomAccess{
  private static final long serialVersionUID=1L;
  transient int[] arr;
  transient int head;
  transient int tail;
  public IntArrDeq(){
    super();
    this.arr=OmniArray.OfInt.DEFAULT_ARR;
    this.tail=-1;
  }
  public IntArrDeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new int[initialCapacity];
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfInt.DEFAULT_ARR;
    case 0:
    }
    this.tail=-1;
  }
  IntArrDeq(int head,int[] arr,int tail){
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
  @Override public void forEach(IntConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action);
    }
  }
  @Override public boolean removeIf(IntPredicate filter){
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
  void uncheckedForEach(final int tail,IntConsumer action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      OmniArray.OfInt.ascendingForEach(arr,head,arr.length-1,action);
      head=0;
    }
    OmniArray.OfInt.ascendingForEach(arr,head,tail,action);
  }
  @Override public boolean add(int val){
    addLast(val);
    return true;
  }
  @Override public void addFirst(int val){
    push(val);
  }
  @Override public boolean offerFirst(int val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(int val){
    addLast(val);
    return true;
  }
  @Override public int intElement(){
    return (int)arr[head];
  }
  @Override public int getLastInt(){
    return (int)arr[tail];
  }
  @Override public boolean offer(int val){
    addLast(val);
    return true;
  }
  @Override public boolean contains(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,(int)TypeUtil.castToByte(val));
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
        final int v;
        if((v=(int)val)==val){
          return uncheckedcontains(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean contains(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final int v;
        if((double)val==(double)(v=(int)val))
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
        final int v;
        if(val==(v=(int)val))
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
          final int i;
          if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            i=((Number)val).intValue();
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(i=(int)l)){
              break returnFalse;
            }
          }else if(val instanceof Float){
            final float f;
            if((double)(f=(float)val)!=(double)(i=(int)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(i=(int)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            i=(char)val;
          }else if(val instanceof Boolean){
            i=TypeUtil.castToByte((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedcontains(tail,i);
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
        return uncheckedremoveVal(tail,(int)TypeUtil.castToByte(val));
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
        final int v;
        if((v=(int)val)==val){
          return uncheckedremoveVal(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final int v;
        if((double)val==(double)(v=(int)val))
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
        final int v;
        if(val==(v=(int)val))
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
          final int i;
          if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            i=((Number)val).intValue();
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(i=(int)l)){
              break returnFalse;
            }
          }else if(val instanceof Float){
            final float f;
            if((double)(f=(float)val)!=(double)(i=(int)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(i=(int)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            i=(char)val;
          }else if(val instanceof Boolean){
            i=TypeUtil.castToByte((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedremoveVal(tail,i);
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
        return uncheckedremoveLastOccurrence(tail,(int)TypeUtil.castToByte(val));
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
        final int v;
        if((v=(int)val)==val){
          return uncheckedremoveLastOccurrence(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final int v;
        if((double)val==(double)(v=(int)val))
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
        final int v;
        if(val==(v=(int)val))
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
          final int i;
          if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            i=((Number)val).intValue();
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(i=(int)l)){
              break returnFalse;
            }
          }else if(val instanceof Float){
            final float f;
            if((double)(f=(float)val)!=(double)(i=(int)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(i=(int)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            i=(char)val;
          }else if(val instanceof Boolean){
            i=TypeUtil.castToByte((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedremoveLastOccurrence(tail,i);
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
        return uncheckedsearch(tail,(int)TypeUtil.castToByte(val));
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
        final int v;
        if((v=(int)val)==val){
          return uncheckedsearch(tail,v);
        }
      }
    }
    return -1;
  }
  @Override public int search(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final int v;
        if((double)val==(double)(v=(int)val))
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
        final int v;
        if(val==(v=(int)val))
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
          final int i;
          if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            i=((Number)val).intValue();
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(i=(int)l)){
              break returnFalse;
            }
          }else if(val instanceof Float){
            final float f;
            if((double)(f=(float)val)!=(double)(i=(int)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(i=(int)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            i=(char)val;
          }else if(val instanceof Boolean){
            i=TypeUtil.castToByte((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedsearch(tail,i);
        }
      }
    }
    return -1;
  }
  @Override public int[] toIntArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final int[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new int[size+=arr.length],size-=tail,tail);
      }else{
        dst=new int[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public Integer[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final Integer[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new Integer[size+=arr.length],size-=tail,tail);
      }else{
        dst=new Integer[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_BOXED_ARR;
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
  @Override public int peekInt(){
    if(tail!=-1){
      return (int)(arr[head]);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int peekLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)(arr[tail]);
    }
    return Integer.MIN_VALUE;
  }
  @Override public Integer peek(){
    if(tail!=-1){
      return (Integer)(arr[head]);
    }
    return null;
  }
  @Override public Integer peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Integer)(arr[tail]);
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
  @Override public int pollInt(){
    int tail;
    if((tail=this.tail)!=-1){
      final int[] arr;
      int head;
      var ret=(int)((arr=this.arr)[head=this.head]);
      if(head==tail){
        this.tail=-1;
        return ret;
      }else if(++head==arr.length){
        head=0;
      }
      this.head=head;
      return ret;
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollLastInt(){
    int tail;
    if((tail=this.tail)!=-1){
      final int[] arr;
      var ret=(int)((arr=this.arr)[tail]);
      if(this.head==tail){
        tail=-1;
      }else if(--tail==-1){
        tail=arr.length-1;
      }
      this.tail=tail;
      return ret;
    }
    return Integer.MIN_VALUE;
  }
  @Override public Integer poll(){
    int tail;
    if((tail=this.tail)!=-1){
      final int[] arr;
      int head;
      var ret=(Integer)((arr=this.arr)[head=this.head]);
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
  @Override public Integer pollLast(){
    int tail;
    if((tail=this.tail)!=-1){
      final int[] arr;
      var ret=(Integer)((arr=this.arr)[tail]);
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
      final int[] arr;
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
      final int[] arr;
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
      final int[] arr;
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
      final int[] arr;
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
  @Override public long pollLong(){
    int tail;
    if((tail=this.tail)!=-1){
      final int[] arr;
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
      final int[] arr;
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
    addLast((int)(int)TypeUtil.castToByte(val));
    return true;
  }
  @Override public Integer getFirst(){
    return intElement();
  }
  @Override public Integer peekFirst(){
    return peek();
  }
  @Override public Integer pollFirst(){
    return poll();
  }
  @Override public Integer removeFirst(){
    return popInt();
  }
  @Override public Integer remove(){
    return popInt();
  }
  @Override public boolean removeFirstOccurrence(Object val){
    return remove(val);
  }
  @Override public Integer pop(){
    return popInt();
  }
  @Override public Integer removeLast(){
    return removeLastInt();
  }
  @Override public void push(Integer val){
    push((int)val);
  }
  @Override public boolean offer(Integer val){
    addLast((int)val);
    return true;
  }
  @Override public Integer element(){
    return intElement();
  }
  @Override public Integer getLast(){
    return getLastInt();
  }
  @Override public void addFirst(Integer val){
    push((int)val);
  }
  @Override public void addLast(Integer val){
    addLast((int)val);
  }
  @Override public boolean add(Integer val){
    addLast((int)val);
    return true;
  }
  @Override public boolean offerFirst(Integer val){
    push((int)val);
    return true;
  }
  @Override public boolean offerLast(Integer val){
    addLast((int)val);
    return true;
  }
  boolean uncheckedremoveVal(int tail
  ,int val
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
  ,int val
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
  ,int val
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfInt.uncheckedcontains (arr,0,tail,val) || OmniArray.OfInt.uncheckedcontains (arr,head,arr.length-1,val);
    }
    return OmniArray.OfInt.uncheckedcontains (arr,head,tail,val);
  }
  private int uncheckedsearch (int tail
  ,int val
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
  @Override public void forEach(Consumer<? super Integer> action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override public boolean removeIf(Predicate<? super Integer> filter){
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
  @Override public int popInt(){
    final int[] arr;
    int head;
    var ret=(int)((arr=this.arr)[head=this.head]);
    if(head==this.tail){
      this.tail=-1;
      return ret;
    }else if(++head==arr.length){
      head=0;
    }
    this.head=head;
    return ret;
  }
  @Override public int removeLastInt(){
    final int[] arr;
    int tail;
    var ret=(int)((arr=this.arr)[tail=this.tail]);
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
      final int[] dst;
      int size,head;
      IntArrDeq clone;
      if((size=(++tail)-(head=this.head))<=0){
        clone=new IntArrDeq(0,dst=new int[size+=arr.length],size-1);
        ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
      }else{
        clone=new IntArrDeq(0,dst=new int[size],size-1);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return clone;
    }
    return new IntArrDeq();
  }
  private String uncheckedToString(int tail){
    final var arr=this.arr;
    final byte[] buffer;
    int size,head,bufferOffset=1;
    if((size=(++tail)-(head=this.head))<=0){
      int bound;
      if((size+=(bound=arr.length))<=(OmniArray.MAX_ARR_SIZE/13)){(buffer=new byte[size*13])
        [0]=(byte)'[';
        for(;;++bufferOffset){
          buffer[bufferOffset=ToStringUtil.getStringInt(arr[head],buffer,bufferOffset)]=(byte)',';
          buffer[++bufferOffset]=(byte)' ';
          if(++head==bound){
            for(head=0;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' '){
              bufferOffset=ToStringUtil.getStringInt(arr[head],buffer,++bufferOffset);
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
          builder.uncheckedAppendInt(arr[head]);
          builder.uncheckedAppendCommaAndSpace();
          if(++head==bound){
            for(head=0;;builder.uncheckedAppendCommaAndSpace()){
              builder.uncheckedAppendInt(arr[head]);
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
      if(size<=(OmniArray.MAX_ARR_SIZE/13)){(buffer=new byte[size*13])
        [size=OmniArray.OfInt.ascendingToString(arr,head,tail-1,buffer,1)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        OmniArray.OfInt.ascendingToString(arr,head,tail-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
  }
  private int uncheckedHashCode(int tail){
    final int[] arr;
    int head;
    int hash=31+((arr=this.arr)[head=this.head]);
    if(tail<head){
      for(final int bound=arr.length;;){  
        if(++head==bound){
          hash=hash*31+(arr[head=0]);
          break;
        }
        hash=(hash*31)+(arr[head]);
      }
    }
    for(;head!=tail;hash=(hash*31)+(arr[++head])){}
    return hash;
  }
  @Override public void push(int val){
    int[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfInt.DEFAULT_ARR){
        this.head=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.tail=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.arr=arr=new int[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
            final int[] newArr;
            int newCap,size;
            this.tail=(newCap=OmniArray.growBy50Pct(head+(size=arr.length)))-1;
            ArrCopy.uncheckedCopy(arr,0,newArr=new int[newCap],newCap-=(++tail),tail);
            ArrCopy.uncheckedCopy(arr,head+1,newArr,head=newCap-(size-=tail),size);
            this.arr=arr=newArr;
            --head;
          }else if(head==-1 && tail==(head=arr.length-1)){
            int newCap;
            this.tail=(newCap=OmniArray.growBy50Pct(++tail))-1;
            ArrCopy.uncheckedCopy(arr,0,arr=new int[newCap],head=newCap-tail,tail);
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
  private void initFromNullArr(int val){
    this.head=0;
    this.tail=0;
    this.arr=new int[]{val};
  }
  @Override public void addLast(int val){
    int[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfInt.DEFAULT_ARR){
        this.head=0;
        this.tail=0;
        this.arr=arr=new int[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
            final int[] newArr;
            (newArr=new int[OmniArray.growBy50Pct(tail=arr.length)])[tail]=val;
            this.tail=tail;
            ArrCopy.uncheckedCopy(arr,head,newArr,0,tail-=head);
            ArrCopy.uncheckedCopy(arr,0,newArr,tail,head);
            this.arr=newArr;
          }else{
            if(tail==arr.length){
              if(head==0){
                ArrCopy.uncheckedCopy(arr,0,arr=new int[OmniArray.growBy50Pct(tail)],0,tail);
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
    extends AbstractIntItr
  {
    transient int cursor;
    AbstractDeqItr(int cursor){
      this.cursor=cursor;
    }
    @Override public boolean hasNext(){
      return this.cursor!=-1;
    }
    abstract void uncheckedForEachRemaining(int cursor,IntConsumer action);
    @Override public void forEachRemaining(IntConsumer action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Integer> action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action::accept);
      }
    }
  }
  private static int pullUp(int[] arr,int head,int headDist){
    ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
    return head;
  }
  private static int fragmentedPullUp(int[] arr,int head,int headDist){
    if(headDist==0){
      return 0;
    }else{
      ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
      return head;
    }
  }
  private static int fragmentedPullDown(int[] arr,int arrBound,int tail){
    if(tail==0){
      return arrBound;
    }
    ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
    return tail-1;
  }
  private static class AscendingItr extends AbstractDeqItr
  {
    transient final IntArrDeq root;
    private AscendingItr(IntArrDeq root){
      super(root.tail!=-1?root.head:-1);
      this.root=root;
    }
    private AscendingItr(IntArrDeq root,int cursor){
      super(cursor);
      this.root=root;
    }
    @Override public int nextInt(){
      final int[] arr;
      int cursor;
      final IntArrDeq root;
      final var ret=(int)(arr=(root=this.root).arr)[cursor=this.cursor];
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
      final IntArrDeq root;
      final int[] arr;
      if((tail=(root=this.root).tail)<(headDist=(arrBound=(arr=root.arr).length-1)-(head=root.head))){
        arr[arrBound]=arr[0];
        root.tail=fragmentedPullDown(arr,arrBound,tail);
        this.cursor=arrBound;
      }else{
        root.head=fragmentedPullUp(arr,head,headDist);
      }
    }
    private void fragmentedAscendingRemove(int head,int lastRet,int tail,IntArrDeq root){
      int[] arr;
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
    private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,IntArrDeq root){
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
          final IntArrDeq root;
          if((tail=(root=this.root).tail)<(head=root.head)){
            fragmentedAscendingRemove(head,cursor-1,tail,root);
          }else{
            nonfragmentedAscendingRemove(head,cursor-1,tail,root);
          }
      }
    }
    @Override void uncheckedForEachRemaining(int cursor,IntConsumer action){
      final IntArrDeq root;
      int tail;
      final var arr=(root=this.root).arr;
      if(cursor>(tail=root.tail)){
        OmniArray.OfInt.ascendingForEach(arr,cursor,arr.length-1,action);
        cursor=0;
      }
      OmniArray.OfInt.ascendingForEach(arr,cursor,tail,action);
      this.cursor=-1;
    }
  }
  private static class DescendingItr extends AscendingItr{
    private DescendingItr(IntArrDeq root){
      super(root,root.tail);
    }
    @Override void uncheckedForEachRemaining(int cursor,IntConsumer action){
      final IntArrDeq root;
      final int head;
      final var arr=(root=this.root).arr;
      if(cursor<(head=root.head)){
        OmniArray.OfInt.descendingForEach(arr,0,cursor,action);
        cursor=arr.length-1;
      }
      OmniArray.OfInt.descendingForEach(arr,head,cursor,action);
      this.cursor=-1;
    }
    @Override public int nextInt(){
      int cursor;
      final IntArrDeq root;
      final var arr=(root=this.root).arr;
      this.cursor=(cursor=this.cursor)==root.head?-1:cursor==0?arr.length-1:cursor-1;
      return (int)arr[cursor];
    }
    private void fragmentedDescendingRemove(int head,int cursor,int tail,IntArrDeq root){
      int[] arr;
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
    private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,IntArrDeq root){
      int tailDist,headDist;
      if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
        ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
        root.tail=tail-1;
      }else{
        int[] arr;
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
        IntArrDeq root;
        int head,tail;
        if((tail=(root=this.root).tail)<(head=root.head)){
          fragmentedDescendingRemove(head,cursor,tail,root);
        }else{
          nonfragmentedDescendingRemove(head,cursor+1,tail,root);
        }
      }
    }
  } 
  @Override public OmniIterator.OfInt iterator(){
    return new AscendingItr(this);
  }
  @Override public OmniIterator.OfInt descendingIterator(){
    return new DescendingItr(this);
  }
  boolean fragmentedRemoveIf(int head,int tail,IntPredicate filter){
    int[] arr;
    if(filter.test((int)(arr=this.arr)[head]))
    {
      if(filter.test((int)arr[tail]))
      {
        fragmentedCollapseHeadAndTail(arr,head,tail,filter);
      }
      else
      {
        fragmentedCollapsehead(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((int)arr[tail]))
    {
      fragmentedCollapsetail(arr,head,tail,filter);
      return true;
    }
    return fragmentedCollapseBody(arr,head,tail,filter);
  }
  private static  int pullDown(int[] arr,int dstOffset,int srcBound,IntPredicate filter){
    for(int srcOffset=dstOffset+1;srcOffset!=srcBound;++srcOffset)
    {
      final int v;
      if(!filter.test((int)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    return dstOffset;
  }
  private static  int pullUp(int[] arr,int dstOffset,int srcBound,IntPredicate filter){
    for(int srcOffset=dstOffset-1;srcOffset!=srcBound;--srcOffset)
    {
      final int v;
      if(!filter.test((int)(v=arr[srcOffset])))
      {
        arr[dstOffset--]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    return dstOffset;
  }
  private void fragmentedCollapseBodyHelper(int[] arr,int head,int tail,IntPredicate filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((int)arr[srcOffset]))
      {
        tail=pullDown(arr,srcOffset,tail,filter);
        break;
      }
    }
    this.tail=tail;
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset)
    {
      if(filter.test((int)arr[srcOffset]))
      {
        head=pullUp(arr,srcOffset,head,filter);
        break;
      }
    }
    this.head=head;
  }
  private void collapseBodyHelper(int[] ar,int head,int tail,IntPredicate filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((int)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((int)arr[midPoint])){
            tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        this.tail=tail;
        return;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((int)arr[midPoint])){
        tail=pullDown(arr,midPoint,tail,filter);
        break;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  private void fragmentedCollapseHeadAndTail(int[] arr,int head,int tail,IntPredicate filter){
    outer:for(;;){
      do{
        if(tail==0){
          for(tail=arr.length-1;tail!=head;--tail){
            if(!filter.test((int)arr[tail])){
              break outer;
            }
          }  
          this.tail=-1;
          return;
        }
      }while(filter.test((int)arr[--tail]));
      for(int bound=arr.length;++head!=bound;){
        if(!filter.test((int)arr[head])){
          fragmentedCollapseBodyHelper(arr,head,tail,filter);
          return;
        }
      }
      head=-1;
      break;
    }
    while(++head!=tail){
      if(!filter.test((int)arr[head])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  private boolean fragmentedCollapseBody(int[] arr,int head,int tail,IntPredicate filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset){
      if(filter.test((int)arr[srcOffset])){
        this.tail=pullDown(arr,srcOffset,tail,filter);
        for(srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
          if(filter.test((int)arr[srcOffset])){
            this.head=pullUp(arr,srcOffset,head,filter);
            break;
          }
        }
        return true;
      }
    }
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
      if(filter.test((int)arr[srcOffset]))
      {
        this.head=pullUp(arr,srcOffset,head,filter);
        return true;
      }
    }
    return false;
  }
  private void fragmentedCollapsehead(int[] arr,int head,int tail,IntPredicate filter){
    for(int bound=arr.length;;)
    {
      if(++head==bound){
        for(head=0;;++head)
        {
          if(head==tail){
            this.head=head;
            return;
          }else if(!filter.test((int)arr[head])){
            collapseBodyHelper(arr,head,tail,filter);
            return;
          }
        }
      }
      if(!filter.test((int)arr[head]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  private void collapsehead(int[] arr,int head,int tail,IntPredicate filter){
    do{
      if(++head==tail){
        this.head=head;
        return;
      }
    }while(filter.test((int)arr[head]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  private void fragmentedCollapsetail(int[] arr,int head,int tail,IntPredicate filter){
    for(;;)
    {
      if(tail==0){
        for(tail=arr.length-1;;--tail)
        {
          if(tail==head){
            this.tail=head;
            return;
          }else if(!filter.test((int)arr[tail])){
            collapseBodyHelper(arr,head,tail,filter);
            return;
          }
        }
      }
      if(!filter.test((int)arr[--tail]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  private void collapsetail(int[] arr,int head,int tail,IntPredicate filter){
    do{
      if(--tail==head){
        this.tail=tail;
        return;
      }
    }while(filter.test((int)arr[tail]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  private void collapseHeadAndTail(int[] arr,int head,int tail,IntPredicate filter){
    do{
      if(++head==tail){
        this.tail=-1;
        return;
      }
    }while(filter.test((int)arr[head]));
    while(--tail!=head){
      if(!filter.test((int)arr[tail])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
    this.head=head;
    this.tail=head;
  }
  private boolean collapseBody(int[] arr,int head,int tail,IntPredicate filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((int)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((int)arr[midPoint])){
            this.tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        return true;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((int)arr[midPoint])){
        this.tail=pullDown(arr,midPoint,tail,filter);
        return true;
      }
    }
    return false;
  }
  boolean nonfragmentedRemoveIf(int head,int tail,IntPredicate filter){
    final int[] arr;
    if(filter.test((int)(arr=this.arr)[head])){
      if(head==tail){
        this.tail=-1;
      }else{
        if(filter.test((int)arr[tail])){
          collapseHeadAndTail(arr,head,tail,filter);
        }else{
          collapsehead(arr,head,tail,filter);
        }
      }
      return true;
    }else if(head!=tail){
      if(filter.test((int)arr[tail])){
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
      int[] arr;
      OmniArray.OfInt.readArray(arr=new int[size],0,--size,input);
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
        int[] arr;
        output.writeInt(size+(size=(arr=this.arr).length));
        OmniArray.OfInt.writeArray(arr,head,size-1,output);
        OmniArray.OfInt.writeArray(arr,0,tail-1,output);
      }else{
        output.writeInt(size);
        OmniArray.OfInt.writeArray(arr,head,tail-1,output);
      }
    }else{
      output.writeInt(0);
    }
  }
  @Override public boolean equals(Object obj){
      //TODO
      return false;
  }
  public static class Checked extends IntArrDeq{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(int initialCapacity){
      super(initialCapacity);
    }
    Checked(int head,int[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public boolean equals(Object obj){
      //TODO
      return false;
    }
    private void collapseheadHelper(int[] arr,int tail,IntPredicate filter,int modCount){
      if(tail==0){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.head=0;
        this.tail=0;
      }else{
        for(int head=0;;)
        {
          if(!filter.test((int)arr[head])){
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
    private void fragmentedCollapseheadHelper(int[] arr,int tail,IntPredicate filter,int modCount){
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
          }else if(!filter.test((int)arr[head])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }
        }
      }
    }
    private void fragmentedCollapsehead(int[] arr,int head,int tail,IntPredicate filter,int modCount){
      int newhead;
      int bound;
      for(newhead=head+1,bound=arr.length-1;;++newhead){
        if(newhead>bound){
          fragmentedCollapseheadHelper(arr,tail,filter,modCount);
          break;
        }
        if(!filter.test((int)arr[newhead])){
          fragmentedCollapseBodyHelper(arr,newhead,tail,filter,modCount);
          break;
        }
      }
    }
    private void collapsehead(int[] arr,int head,int tail,IntPredicate filter,int modCount){
      int srcOffset;
      for(srcOffset=head;++srcOffset!=tail;){
        if(!filter.test((int)arr[srcOffset])){
          collapseBodyHelper(arr,srcOffset,tail,filter,modCount);
          this.modCount=modCount+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.head=srcOffset;
    }
    private void collapsetailHelper(int[] arr,int head,IntPredicate filter,int modCount){
      int tail;
      if((tail=arr.length-1)==head){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.tail=tail;
        this.head=head;
      }else{
        for(;;)
        {
          if(!filter.test((int)arr[tail])){
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
    private void fragmentedCollapsetailHelper(int[] arr,int head,IntPredicate filter,int modCount){
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
          }else if(!filter.test((int)arr[tail])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }
        }
      }
    }
    private void fragmentedCollapsetail(int[] arr,int head,int tail,IntPredicate filter,int modCount){
      int newtail;
      for(newtail=tail-1;;--newtail){
        if(newtail==-1){
          fragmentedCollapsetailHelper(arr,head,filter,modCount);
          break;
        }
        if(!filter.test((int)arr[newtail])){
          fragmentedCollapseBodyHelper(arr,head,newtail,filter,modCount);
          break;
        }
      }
    }
    private void collapsetail(int[] arr,int head,int tail,IntPredicate filter,int modCount){
      int srcOffset;
      for(srcOffset=tail;--srcOffset!=head;){
        if(!filter.test((int)arr[srcOffset])){
          collapseBodyHelper(arr,head,srcOffset,filter,modCount);
          this.modCount=modCount+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.tail=srcOffset;
    }
    private void noElementsLeftToMarkFragmentedBefore(int[] arr,int head,int gapBegin){
      assert gapBegin<arr.length-1;
      assert head<gapBegin;
      assert 0<head;
      if((gapBegin-head)==1)
      {
        arr[gapBegin=arr.length-2]=arr[head];
        this.head=gapBegin;
        this.tail=0;
      }
      else
      {
        this.head=head;
        arr[gapBegin]=arr[head=arr.length-1];
        arr[++gapBegin]=arr[0];
        this.tail=gapBegin;
      }
    }
    private void noElementsLeftToMarkFragmentedGap(int[] arr,int head,int gapBegin,int gapEnd){
      assert gapBegin<arr.length;
      assert head<gapBegin;
      assert gapEnd>=0;
      assert gapEnd<head;
      this.head=head;
      arr[gapBegin]=arr[gapEnd];
      if(++gapBegin==arr.length){
        arr[0]=arr[++gapEnd];
        this.tail=0;
      }else{
        this.tail=gapBegin;
        arr[gapBegin]=arr[gapEnd+1];
      }
    }
    private void noElementsLeftToMarkFragmentedAfter(int[] arr,int head,int gapBegin,int gapEnd){
      assert gapBegin>=0;
      assert head>gapEnd;
      assert head<arr.length;
      assert gapEnd>gapBegin;
      arr[gapBegin]=arr[gapEnd];
      this.tail=++gapBegin;
      arr[gapBegin]=arr[gapEnd+1];
    }
    private void noElementsLeftToMark(int[] arr,int head,int gapBegin,int gapEnd){
      //there were no elements left to mark, so finalize the collapse
      if((gapBegin-head)==1){
        this.tail=gapEnd+1;
        arr[--gapEnd]=arr[head];
        this.head=gapEnd;
      }else{
        this.head=head;
        arr[gapBegin]=arr[gapEnd];
        arr[++gapBegin]=arr[gapEnd+1];
        this.tail=gapBegin;
      }
    }
    private void nonfragmentedCollapseAllSurvived(int head,int gapBegin,int gapEnd,int numLeft,int tail){
      final var arr=this.arr;
      int beforeLength;
      if((numLeft+=2)<(beforeLength=gapBegin-head)){
        ArrCopy.uncheckedSelfCopy(arr,gapBegin,gapEnd,numLeft);
        this.tail=gapBegin+numLeft-1;
        this.head=head;
      }else{
        ArrCopy.uncheckedCopy(arr,head,arr,gapEnd-=beforeLength,beforeLength);
        this.tail=tail;
        this.head=gapEnd;
      }
    }
    private void nonfragmentedCollapseNoSurvivors(int head,int gapBegin,int gapEnd,int tail){
      final int[] arr;
      (arr=this.arr)[gapBegin]=arr[gapEnd];
      arr[++gapBegin]=arr[tail];
      this.head=head;
      this.tail=gapBegin;
    }
    private void fragmentedCollapseTailIsZeroNoSurvivors(int head,int gapBegin,int gapEnd){
      this.head=head;
      final int[] arr;
      (arr=this.arr)[gapBegin]=arr[gapEnd];
      arr[++gapBegin]=arr[0];
      this.tail=gapBegin;
    }
    private void fragmentedCollapseTailIsZeroAllSurvived(int head,int gapBegin,int gapEnd){
      final int[] arr;
      int headLength,tailLength;
      if((headLength=gapBegin-head)<=(tailLength=((arr=this.arr).length)-gapEnd)+1){
        ArrCopy.uncheckedCopy(arr,head,arr,gapEnd-=headLength,headLength);
        this.head=gapEnd;
        this.tail=0;
      }else{
        this.head=head;
        ArrCopy.uncheckedSelfCopy(arr,gapBegin,gapEnd,tailLength);
        arr[gapBegin+=tailLength]=arr[0];
        this.tail=gapBegin;
      }
    }
    private void fragmentedCollapseMarkRangeSpansSplitNoSurvivors(int head,int gapBegin,int gapEnd,int tail){
      this.head=head;
      int[] arr;
      (arr=this.arr)[gapBegin]=arr[gapEnd];
      arr[++gapBegin]=arr[tail];
      this.tail=gapBegin;
    }
    private void fragmentedCollapseMarkRangeSpansSplitAllSurvived(int head,int gapBegin,int gapEnd,int tail){
      int[] arr;
      int headLength,arrLength,tailLength;
      if((headLength=gapBegin-head)<=(tailLength=(arrLength=(arr=this.arr).length)-gapEnd)+(++tail))
      {
        this.tail=tail-1;
        ArrCopy.uncheckedCopy(arr,head,arr,gapEnd-=headLength,headLength);
        this.head=gapEnd;
      }
      else
      {
        this.head=head;
        ArrCopy.uncheckedSelfCopy(arr,gapBegin,gapEnd,tailLength);
        if((headLength=tail-(tailLength=arrLength-(gapBegin+=tailLength)))<=0)
        {
          ArrCopy.uncheckedCopy(arr,0,arr,gapBegin,tail);
          this.tail=gapBegin+tail-1;
        }else{
          ArrCopy.uncheckedCopy(arr,0,arr,gapBegin,tailLength);
          ArrCopy.uncheckedSelfCopy(arr,0,tailLength,headLength);
          this.tail=headLength-1;
        }
      }
    }
    private static abstract class CollapseData{
      final Checked deq;
      final int numLeft;
      int survivorsBeforeBiggestRun;
      int survivorsAfterBiggestRun;
      int biggestRunBegin;
      int biggestRunLength;
      CollapseData(Checked deq,int numLeft){
        this.deq=deq;
        this.numLeft=numLeft;
      }
      abstract void arrSeqPullDown(int[] arr,int srcOffset,int dstOffset,int dstBound);
      abstract void pullSurvivorsDown(int[] arr,int dstOffset,int survivorIndex,int dstBound);
      abstract void pullSurvivorsUp(int[] arr,int srcOffset,int dstOffset,int survivorIndex,int dstBound);
      abstract void pullDownMarkRangeSpansSplit(int[] arr,int srcOffset,int dstOffset,int dstBound);
      abstract void pullDownMarkRangeSpansSplitDstBoundGoesBoundSplit(int[] arr,int srcOffset,int dstOffset,int dstBound);
      private int collapseTailHelper(int[] arr,int gapEnd,int tail){
        int biggestRunBegin=this.biggestRunBegin;
        int survivorsBeforeOrAfter;
        int newHead;
        if((survivorsBeforeOrAfter=this.survivorsBeforeBiggestRun)==0){
          newHead=biggestRunBegin-1;
        }else{
          pullSurvivorsUp(arr,biggestRunBegin-1,newHead=biggestRunBegin,newHead-gapEnd-2,newHead-=survivorsBeforeOrAfter);
          --newHead;
        }
        arr[newHead]=arr[gapEnd];
        biggestRunBegin+=this.biggestRunLength;
        if((survivorsBeforeOrAfter=this.survivorsAfterBiggestRun)!=0){
          pullSurvivorsDown(arr,biggestRunBegin,biggestRunBegin-gapEnd,biggestRunBegin+=survivorsBeforeOrAfter);
        }
        if(biggestRunBegin!=tail){
          arr[biggestRunBegin]=arr[tail];
        }
        deq.tail=biggestRunBegin;
        return newHead;
      }
      private void fragmentedCollapseMarkRangeSpansSplit(int head,int gapBegin,int gapEnd,int tail){
        assert tail<head;
        assert tail>0;
        assert gapBegin>head;
        assert gapEnd>gapBegin;
        //0 < tail < head < gapBegin < gapEnd < deq.arr.length-1
        int numSurvivors,biggestRunLength;
        if((numSurvivors=
           (this.survivorsBeforeBiggestRun)
          +(this.survivorsAfterBiggestRun)
          +(biggestRunLength=this.biggestRunLength))==0){
          deq.fragmentedCollapseMarkRangeSpansSplitNoSurvivors(head,gapBegin,gapEnd,tail);
        }else{
          int numLeft;
          if((numLeft=this.numLeft)==numSurvivors){
            deq.fragmentedCollapseMarkRangeSpansSplitAllSurvived(head,gapBegin,gapEnd,tail);
          }else{
            final Checked deq;
            final var arr=(deq=this.deq).arr;
            if(biggestRunLength>(numLeft=gapBegin-head)){
               //TODO
               throw new UnsupportedOperationException();
            }else{
               deq.head=head;
               arr[gapBegin]=arr[gapEnd++];
               switch(Integer.signum(head=(numSurvivors+=(++gapBegin))-arr.length)){
                 case -1:
                   pullDownMarkRangeSpansSplit(arr,gapEnd,gapBegin,numSurvivors);
                   arr[numSurvivors]=arr[tail];
                   deq.tail=numSurvivors;
                   break;
                 case 0:
                   pullDownMarkRangeSpansSplit(arr,gapEnd,gapBegin,numSurvivors);
                   arr[0]=arr[tail];
                   deq.tail=0;
                   break;
                 default:
                   pullDownMarkRangeSpansSplitDstBoundGoesBoundSplit(arr,gapEnd,gapBegin,head);
                   arr[head]=arr[tail];
                   deq.tail=head;
               }
            }
          }
        }
      }
      private void fragmentedCollapseTailIs0(int head,int gapBegin,int gapEnd){
        assert head<gapBegin;
        assert gapBegin<gapEnd;
        //0 == tail < head < gapBegin < gapEnd < srcBound == arr.length
        int numSurvivors,biggestRunLength,survivorsBefore,survivorsAfter;
        if((numSurvivors=
           (survivorsBefore=this.survivorsBeforeBiggestRun)
          +(survivorsAfter=this.survivorsAfterBiggestRun)
          +(biggestRunLength=this.biggestRunLength))==0){
          deq.fragmentedCollapseTailIsZeroNoSurvivors(head,gapBegin,gapEnd);
        }else{
          int numLeft;
          if((numLeft=this.numLeft)==numSurvivors){
            deq.fragmentedCollapseTailIsZeroAllSurvived(head,gapBegin,gapEnd);
          }else{
            final Checked deq;
            final var arr=(deq=this.deq).arr;
            if(biggestRunLength>(numLeft=gapBegin-head)){
              int biggestRunBegin=this.biggestRunBegin;
              if(survivorsBefore==0){
                gapBegin=biggestRunBegin-1;
              }else{
                pullSurvivorsUp(arr,biggestRunBegin-1,gapBegin=biggestRunBegin,gapBegin-gapEnd-2,gapBegin-=survivorsBefore);
                --gapBegin;
              }
              arr[gapBegin]=arr[gapEnd];
              biggestRunBegin+=biggestRunLength;
              if(survivorsAfter!=0){
                pullSurvivorsDown(arr,biggestRunBegin,biggestRunBegin-gapEnd,biggestRunBegin+=survivorsAfter);
              }
              if(biggestRunBegin==arr.length){
                deq.tail=0;
              }else{
                arr[biggestRunBegin]=arr[0];
                deq.tail=biggestRunBegin;
              }
              ArrCopy.uncheckedCopy(arr,head,arr,gapBegin-=numLeft,numLeft);
              deq.head=gapBegin;
            }else{
              arr[gapBegin]=arr[gapEnd];
              arrSeqPullDown(arr,++gapEnd,++gapBegin,gapBegin+=numSurvivors);
              arr[gapBegin]=arr[0];
              deq.head=head;
              deq.tail=gapBegin;
            }
          }
        }
      }
      private void nonfragmentedCollapse(int head,int gapBegin,int gapEnd,int tail){
        assert head>=0;
        assert gapBegin>head;
        assert gapEnd>gapBegin;
        assert tail>gapEnd;
        //0 <= head < gapBegin < gapEnd < tail < arr.length
        int numSurvivors,biggestRunLength;
        if((numSurvivors=
           (this.survivorsBeforeBiggestRun)
          +(this.survivorsAfterBiggestRun)
          +(biggestRunLength=this.biggestRunLength))==0){
          deq.nonfragmentedCollapseNoSurvivors(head,gapBegin,gapEnd,tail);
        }else{
          int numLeft;
          if((numLeft=this.numLeft)==numSurvivors){
            deq.nonfragmentedCollapseAllSurvived(head,gapBegin,gapEnd,numLeft,tail);
          }else{
            final Checked deq;
            final var arr=(deq=this.deq).arr;
            if(biggestRunLength>(numLeft=gapBegin-head)){
              ArrCopy.uncheckedCopy(arr,head,arr,numSurvivors=collapseTailHelper(arr,gapEnd,tail)-numLeft,numLeft);
              deq.head=numSurvivors;
            }else{
              arr[gapBegin]=arr[gapEnd];
              arrSeqPullDown(arr,++gapEnd,++gapBegin,gapBegin+=numSurvivors);
              arr[gapBegin]=arr[tail];
              deq.head=head;
              deq.tail=gapBegin;
            }
          }
        }
      }
    }
    private static class BigCollapseData extends CollapseData{
      final long[] survivorSet;
      BigCollapseData(Checked deq,int srcOffset,int numLeft,IntPredicate filter,int arrBound){
        super(deq,numLeft);
        assert srcOffset>0;
        assert numLeft>1;
        assert numLeft>64;
        assert srcOffset+numLeft>arrBound;
        assert srcOffset<arrBound;
        var survivorSet=new long[((numLeft-1)>>6)+1];
        numLeft+=(srcOffset-arrBound);
        final var arr=deq.arr;
        for(int wordOffset=0,survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;;){
          long word=0L,marker=1L;
          do{
            if(filter.test((int)arr[srcOffset])){
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
            if(++srcOffset==arrBound){
              for(srcOffset=0,currentRunLength=0;;survivorSet[wordOffset++]=word,word=0L,marker=1L){
                while((marker<<=1)!=0L){
                  if(filter.test((int)arr[srcOffset])){
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
                    survivorSet[wordOffset]=word;
                    this.biggestRunBegin=biggestRunBegin;
                    this.biggestRunLength=biggestRunLength;
                    this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
                    this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
                    this.survivorSet=survivorSet;
                    return;
                  }
                }
              }
            }
          }while((marker<<=1)!=0L);
          survivorSet[wordOffset++]=word;
        }
      }
      BigCollapseData(Checked deq,int srcOffset,int numLeft,IntPredicate filter){
        super(deq,numLeft);
        assert srcOffset>=0;
        assert numLeft>64;
        var survivorSet=new long[((numLeft-1)>>6)+1];
        numLeft+=srcOffset;
        final var arr=deq.arr;
        for(int wordOffset=0,survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;;){
          long word=0L,marker=1L;
          do{
            if(filter.test((int)arr[srcOffset])){
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
              survivorSet[wordOffset]=word;
              this.biggestRunBegin=biggestRunBegin;
              this.biggestRunLength=biggestRunLength;
              this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
              this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
              this.survivorSet=survivorSet;
              return;
            }
          }while((marker<<=1)!=0L);
          survivorSet[wordOffset++]=word;
        }
      }
      @Override void arrSeqPullDown(int[] arr,int srcOffset,int dstOffset,int dstBound){
        assert dstOffset>=0;
        assert srcOffset>dstOffset;
        assert dstOffset<dstBound;
        assert dstBound<arr.length;
        IntArrSeq.pullSurvivorsDown(arr,srcOffset,dstOffset,dstBound,survivorSet);
      }
      @Override void pullSurvivorsDown(int[] arr,int dstOffset,int survivorIndex,int dstBound){
        assert dstOffset>=0;
        assert dstOffset<dstBound;
        assert dstBound<arr.length;
        int wordOffset;
        long[] survivorSet;
        long word=(survivorSet=this.survivorSet)[wordOffset=survivorIndex>>6]>>>survivorIndex;
        for(int s=dstOffset+1,srcOffset=s+(64-(survivorIndex&0b111111));;word=survivorSet[++wordOffset],s=srcOffset,srcOffset+=64){
          for(;;s+=survivorIndex,word>>>=survivorIndex){
            if((survivorIndex=Long.numberOfTrailingZeros(word))==64){
              break;
            }
            ArrCopy.uncheckedSelfCopy(arr,dstOffset,s+=survivorIndex,survivorIndex=Long.numberOfTrailingZeros(~(word>>>=survivorIndex)));
            dstOffset+=survivorIndex;
            if(survivorIndex==64){
              break;
            }else if(dstOffset>=dstBound){
              return;
            }
          }
        }
      }
      @Override void pullSurvivorsUp(int[] arr,int srcOffset,int dstOffset,int survivorIndex,int dstBound){
        assert srcOffset<dstOffset;
        assert srcOffset>=0;
        assert dstOffset<arr.length;
        assert survivorIndex>=0;
        assert dstOffset>dstBound;
        int wordOffset;
        long[] survivorSet;
        long word=(survivorSet=this.survivorSet)[wordOffset=(survivorIndex-1)>>6]<<(64-(survivorIndex&0b111111));
        int s;
        for(srcOffset=(s=srcOffset)-(((survivorIndex-1)&0b111111)+1);;word=survivorSet[--wordOffset],s=srcOffset,srcOffset-=64){
          for(;;word<<=survivorIndex){
            if((survivorIndex=Long.numberOfLeadingZeros(word))==64){
              break;
            }
            ArrCopy.uncheckedCopy(arr,s-=(survivorIndex+(survivorIndex=Long.numberOfLeadingZeros(~(word<<=survivorIndex)))),arr,dstOffset-=survivorIndex,survivorIndex);
            if(survivorIndex==64){
              break;
            }else if(dstOffset<=dstBound){
              return;
            }
          }
        }
      }
      @Override void pullDownMarkRangeSpansSplit(int[] arr,int srcOffset,int dstOffset,int dstBound){
        throw new UnsupportedOperationException();
      }
      @Override void pullDownMarkRangeSpansSplitDstBoundGoesBoundSplit(int[] arr,int srcOffset,int dstOffset,int dstBound){
        throw new UnsupportedOperationException();
      }
    }
    private static class SmallCollapseData extends CollapseData{
      @Override void pullDownMarkRangeSpansSplit(int[] arr,int srcOffset,int dstOffset,int dstBound){
        long word;
        int numToRetain,numToSkip=Long.numberOfTrailingZeros(word=this.survivorWord);
        for(int arrLength=arr.length;;)
        {
          int overflow;
          if((overflow=(srcOffset+=numToSkip)-arrLength)>=0)
          {
            srcOffset=overflow;
            break;
          }
          int srcBound;
          if((overflow=(srcBound=srcOffset+(numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip))))-arrLength)>0){
            ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,arrLength=numToRetain-overflow);
            ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+arrLength,overflow);
            if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
              return;
            }
            srcOffset=overflow+numToSkip;
            dstOffset+=numToRetain;
            break;
          }
          ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
          if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
            return;
          }
          srcOffset=srcBound;
          dstOffset+=numToRetain;
        }
        for(;;){
          ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip)));
          if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
            return;
          }
          dstOffset+=numToRetain;
          srcOffset+=(numToSkip+numToRetain);
        }
      }
      @Override void pullDownMarkRangeSpansSplitDstBoundGoesBoundSplit(int[] arr,int srcOffset,int dstOffset,int dstBound){
        //TODO refactor this function to make it smaller
        long word;
        int numToRetain,numToSkip=Long.numberOfTrailingZeros(word=this.survivorWord);
        outerLoop:for(int arrLength=arr.length;;){
          innerLoop:for(;;){
            int srcOverflow;
            if((srcOverflow=(srcOffset+=numToSkip)-arrLength)>=0){
              int dstOverflow;
              switch(Integer.signum(dstOverflow=(dstBound=dstOffset+(numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip))))-arrLength)){
                case 0:
                  ArrCopy.uncheckedCopy(arr,srcOverflow,arr,dstOffset,numToRetain);
                  dstOffset=0;
                  srcOffset=srcOverflow+numToRetain+(numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain));
                  break outerLoop;
                default:
                  ArrCopy.uncheckedCopy(arr,srcOverflow,arr,dstOffset,arrLength=numToRetain-dstOverflow);
                  ArrCopy.uncheckedSelfCopy(arr,0,srcOverflow+=arrLength,dstOverflow);
                  if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
                    return;
                  }
                  srcOffset=srcOverflow+dstOverflow+numToSkip;
                  dstOffset=dstOverflow;
                  break outerLoop;
                case -1:
                  ArrCopy.uncheckedCopy(arr,srcOverflow,arr,dstOffset,numToRetain);
                  dstOffset=dstBound;
                  srcOffset=srcOverflow+numToRetain+(numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain));
              }
              break;
            }
            int srcBound;
            switch(Integer.signum(srcOverflow=(srcBound=srcOffset+(numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip))))-arrLength)){
              case 0:
                //the source goes right up to the end of the array. wrap around
                ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
                dstOffset+=numToRetain;
                srcOffset=numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain);
                break innerLoop;
              default:
                //the source overflows
                int dstOverflow;
                switch(Integer.signum(dstOverflow=(dstBound=dstOffset+numToRetain)-arrLength)){
                  case -1:
                    //there is source overflow, but no destination overflow
                    ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,dstOverflow=numToRetain-srcOverflow);
                    ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+dstOverflow,srcOverflow);
                    dstOffset=dstBound;
                    srcOffset=srcOverflow+(numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain));
                    break innerLoop;
                  case 0:
                    //the source overflows and the destination goes right up to the end of the array
                    ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,arrLength=numToRetain-srcOverflow);
                    ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+arrLength,srcOverflow);
                    dstOffset=0;
                    srcOffset=srcOverflow+(numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain));
                    break outerLoop;
                  default:
                    //the source AND the destination overflow
                    ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,arrLength=numToRetain-srcOverflow);
                    ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+arrLength,srcOverflow-=dstOverflow);
                    ArrCopy.uncheckedSelfCopy(arr,0,srcOverflow,dstOverflow);
                    if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
                      return;
                    }
                    dstOffset=dstOverflow;
                    srcOffset=srcOverflow+dstOverflow+numToSkip;
                    break outerLoop;
                }
              case -1:
                //no overflow of either the source or the destination
                ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
                srcOffset=srcBound;
                dstOffset+=numToRetain;
                numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain);
            }
          }
          for(;;){
            int dstOverflow;
            switch(Integer.signum(dstOverflow=(dstBound=dstOffset+(numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip))))-arrLength)){
              case 0:
                ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numToRetain);
                dstOffset=0;
                srcOffset+=(numToRetain+(numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain)));
                break outerLoop;
              default:
                ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,arrLength=numToRetain-dstOverflow);
                ArrCopy.uncheckedSelfCopy(arr,0,srcOffset+arrLength,dstOverflow);
                if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
                  return;
                }
                dstOffset=dstOverflow;
                srcOffset+=(numToRetain+numToSkip);
                break outerLoop;
              case -1:
                //still no destination overflow
                ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numToRetain);
                dstOffset+=numToRetain;
                srcOffset+=(numToRetain+(numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain)));
            }
          }
        }
        for(;;){
          ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip)));
          if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
            return;
          }
          srcOffset+=(numToRetain+numToSkip);
          dstOffset+=numToRetain;
        }
      }
      @Override void pullSurvivorsUp(int[] arr,int srcOffset,int dstOffset,int survivorIndex,int dstBound){
        assert srcOffset<dstOffset;
        assert srcOffset>=0;
        assert dstOffset<arr.length;
        assert survivorIndex>=0;
        assert dstOffset>dstBound;
        assert dstOffset-dstBound<64;
        long word=this.survivorWord<<(64-(0b111111&survivorIndex));
        for(;;){
          int numToRetain;
          ArrCopy.uncheckedCopy(arr,srcOffset-=(survivorIndex=Long.numberOfLeadingZeros(word))+(numToRetain=Long.numberOfLeadingZeros(~(word<<=survivorIndex))),arr,dstOffset-=numToRetain,numToRetain);
          if(dstOffset<=dstBound){
            return;
          }
          word<<=numToRetain;
        }
      }
      @Override void arrSeqPullDown(int[] arr,int srcOffset,int dstOffset,int dstBound){
        assert dstOffset>=0;
        assert srcOffset>dstOffset;
        assert dstOffset<dstBound;
        assert dstBound<arr.length;
        assert dstBound-dstOffset<64;
        IntArrSeq.pullSurvivorsDown(arr,srcOffset,dstOffset,survivorWord);
      }
      @Override void pullSurvivorsDown(int[] arr,int dstOffset,int survivorIndex,int dstBound){
        assert dstOffset>=0;
        assert dstOffset<dstBound;
        assert dstBound<arr.length;
        IntArrSeq.pullSurvivorsDown(arr,dstOffset+1,dstOffset,this.survivorWord>>>survivorIndex);
      }
      final long survivorWord;
      SmallCollapseData(Checked deq,int srcOffset,int numLeft,IntPredicate filter){
        super(deq,numLeft);
        assert srcOffset>=0;
        assert numLeft<=64;
        numLeft+=srcOffset;
        final var arr=deq.arr;
        int survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;
        for(long word=0L,marker=1L;;marker<<=1){
          if(filter.test((int)arr[srcOffset])){
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
            this.survivorWord=word;
            this.biggestRunBegin=biggestRunBegin;
            this.biggestRunLength=biggestRunLength;
            this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
            this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
            return;
          }
        }
      }
      SmallCollapseData(Checked deq,int srcOffset,int numLeft,IntPredicate filter,int arrBound){
        super(deq,numLeft);
        assert srcOffset>0;
        assert numLeft>1;
        assert numLeft<=64;
        assert srcOffset+numLeft>arrBound;
        assert srcOffset<arrBound;
        final var arr=deq.arr;
        numLeft+=(srcOffset-arrBound);
        int survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;
        for(long word=0L,marker=1L;;marker<<=1){
          if(filter.test((int)arr[srcOffset])){
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
          if(++srcOffset==arrBound){
            for(srcOffset=0,currentRunLength=0;;){
              marker<<=1;
              if(filter.test((int)arr[srcOffset])){
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
                this.survivorWord=word;
                this.biggestRunBegin=biggestRunBegin;
                this.biggestRunLength=biggestRunLength;
                this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
                this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
                return;
              }
            }
          }
        }
      }
    }
    private void collapseBodyHelper(int[] arr,int head,int tail,IntPredicate filter,int modCount){
      for(int gapBegin=head+1;gapBegin!=tail;++gapBegin){
        if(filter.test((int)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=tail;++gapEnd){
            if(!filter.test((int)arr[gapEnd])){
              int numLeft,srcOffset;
              if((numLeft=tail-(srcOffset=gapEnd+1))==0){
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMark(arr,head,gapBegin,gapEnd);
              }else{
                var collapseData=getNonfragmentedCollapseData(srcOffset,numLeft,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                collapseData.nonfragmentedCollapse(head,gapBegin,gapEnd,tail);
              }
              return;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.head=head;
          arr[gapBegin]=arr[tail];
          this.tail=gapBegin;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.head=head;
      this.tail=tail;
    }
    private boolean collapseBody(int[] arr,int head,int tail,IntPredicate filter,int modCount){
      for(int gapBegin=head+1;gapBegin!=tail;++gapBegin){
        if(filter.test((int)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=tail;++gapEnd){
            if(!filter.test((int)arr[gapEnd])){
              int numLeft,srcOffset;
              if((numLeft=tail-(srcOffset=gapEnd+1))==0){
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMark(arr,head,gapBegin,gapEnd);
              }else{
                var collapseData=getNonfragmentedCollapseData(srcOffset,numLeft,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                collapseData.nonfragmentedCollapse(head,gapBegin,gapEnd,tail);
              }
              this.modCount=modCount+1;
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          arr[gapBegin]=arr[tail];
          this.tail=gapBegin;
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    private void fragmentedCollapseBodyHelper(int[] arr,int head,int tail,IntPredicate filter,int modCount){
      for(int gapBegin=head+1,arrBound=arr.length;gapBegin!=arrBound;++gapBegin){
        if(filter.test((int)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=arrBound;++gapEnd){
            if(!filter.test((int)arr[gapEnd])){
              int numLeft,srcOffset;
              if((numLeft=tail+arrBound-(srcOffset=gapEnd+1))==0){
                assert tail==0;
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMarkFragmentedBefore(arr,head,gapBegin);
              }else{
                if(srcOffset+numLeft>arrBound){
                  if(srcOffset==arrBound){
                    var collapseData=getNonfragmentedCollapseData(0,numLeft,filter);
                    CheckedCollection.checkModCount(modCount,this.modCount);
                    //TODO
                    throw new UnsupportedOperationException();
                  }else{
                    //0 < tail < head < gapBegin < gapEnd < arrBound
                    var collapseData=getFragmentedCollapseData(srcOffset,numLeft,filter,arrBound);
                    CheckedCollection.checkModCount(modCount,this.modCount);
                    collapseData.fragmentedCollapseMarkRangeSpansSplit(head,gapBegin,gapEnd,tail);
                  }
                }else{
                  assert tail==0;
                  assert srcOffset+numLeft==arrBound;
                  var collapseData=getNonfragmentedCollapseData(srcOffset,numLeft,filter);
                  CheckedCollection.checkModCount(modCount,this.modCount);
                  collapseData.fragmentedCollapseTailIs0(head,gapBegin,gapEnd);
                }
              }
              return;
            }
          }
          for(int gapEnd=0;gapEnd!=tail;++gapEnd){
            if(!filter.test((int)arr[gapEnd])){
              int numLeft;
              if((numLeft=tail-(arrBound=gapEnd+1))==0){
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMarkFragmentedGap(arr,head,gapBegin,gapEnd);
              }else{
                //TODO
                if(numLeft>64){
                  throw new UnsupportedOperationException();
                }else{
                  throw new UnsupportedOperationException();
                }
              }
              return;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.head=head;
          arr[gapBegin]=arr[tail];
          this.tail=gapBegin;
          return;
        }
      }
      for(int gapBegin=0;gapBegin!=tail;++gapBegin){
        if(filter.test((int)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=tail;++gapEnd){
            if(!filter.test((int)arr[gapEnd])){
              int numLeft,srcOffset;
              if((numLeft=tail-(srcOffset=gapEnd+1))==0){
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.head=head;
                noElementsLeftToMarkFragmentedAfter(arr,head,gapBegin,gapEnd);
              }else{
                //TODO
                if(numLeft>64){
                  throw new UnsupportedOperationException();
                }else{
                  throw new UnsupportedOperationException();
                }
              }
              return;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.head=head;
          arr[gapBegin]=arr[tail];
          this.tail=gapBegin;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.head=head;
      this.tail=tail;
    }
    private CollapseData getNonfragmentedCollapseData(int srcOffset,int numLeft,IntPredicate filter){
      return numLeft>64?new BigCollapseData(this,srcOffset,numLeft,filter):new SmallCollapseData(this,srcOffset,numLeft,filter);
    }
    private CollapseData getFragmentedCollapseData(int srcOffset,int numLeft,IntPredicate filter,int arrBound){
      return numLeft>64?new BigCollapseData(this,srcOffset,numLeft,filter,arrBound):new SmallCollapseData(this,srcOffset,numLeft,filter,arrBound);
    }
    private boolean fragmentedCollapseBody(int[] arr,int head,int tail,IntPredicate filter,int modCount){
      for(int gapBegin=head+1,arrBound=arr.length;gapBegin!=arrBound;++gapBegin){
        if(filter.test((int)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=arrBound;++gapEnd){
            if(!filter.test((int)arr[gapEnd])){
              int numLeft,srcOffset;
              if((numLeft=tail+arrBound-(srcOffset=gapEnd+1))==0){
                assert tail==0;
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMarkFragmentedBefore(arr,head,gapBegin);
              }else{
                if(srcOffset+numLeft>arrBound){
                  if(srcOffset==arrBound){
                    var collapseData=getNonfragmentedCollapseData(0,numLeft,filter);
                    CheckedCollection.checkModCount(modCount,this.modCount);
                    //TODO
                    throw new UnsupportedOperationException();
                  }else{
                    //0 < tail < head < gapBegin < gapEnd < arrBound
                    var collapseData=getFragmentedCollapseData(srcOffset,numLeft,filter,arrBound);
                    CheckedCollection.checkModCount(modCount,this.modCount);
                    collapseData.fragmentedCollapseMarkRangeSpansSplit(head,gapBegin,gapEnd,tail);
                  }
                }else{
                  assert tail==0;
                  assert srcOffset+numLeft==arrBound;
                  var collapseData=getNonfragmentedCollapseData(srcOffset,numLeft,filter);
                  CheckedCollection.checkModCount(modCount,this.modCount);
                  collapseData.fragmentedCollapseTailIs0(head,gapBegin,gapEnd);
                }
              }
              this.modCount=modCount+1;
              return true;
            }
          }
          for(int gapEnd=0;gapEnd!=tail;++gapEnd){
            if(!filter.test((int)arr[gapEnd])){
              int numLeft;
              if((numLeft=tail-(arrBound=gapEnd+1))==0){
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMarkFragmentedGap(arr,head,gapBegin,gapEnd);
              }else{
                //TODO
                if(numLeft>64){
                  throw new UnsupportedOperationException();
                }else{
                  throw new UnsupportedOperationException();
                }
              }
              this.modCount=modCount+1;
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          arr[gapBegin]=arr[tail];
          this.tail=gapBegin;
          this.modCount=modCount+1;
          return true;
        }
      }
      for(int gapBegin=0;gapBegin!=tail;++gapBegin){
        if(filter.test((int)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=tail;++gapEnd){
            if(!filter.test((int)arr[gapEnd])){
              int numLeft,srcOffset;
              if((numLeft=tail-(srcOffset=gapEnd+1))==0){
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMarkFragmentedAfter(arr,head,gapBegin,gapEnd);
              }else{
                //TODO
                if(numLeft>64){
                  throw new UnsupportedOperationException();
                }else{
                  throw new UnsupportedOperationException();
                }
              }
              this.modCount=modCount+1;
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.tail=gapBegin;
          arr[gapBegin]=arr[tail];
          this.modCount=modCount+1;
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    private void fragmentedCollapseHeadAndTail(int[] arr,int head,int tail,IntPredicate filter,int modCount){
      int newTail=tail-1,newHead=head+1,bound=arr.length-1;
      for(;;--newTail){
        if(newTail==-1){
          for(;;++newHead){
            if(newHead>bound){
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.tail=-1;
              break;
            }else if(!filter.test((int)arr[newHead])){
              collapsetailHelper(arr,newHead,filter,modCount);
              break;
            }
          }
          break;
        }else if(!filter.test((int)arr[newTail])){
          for(;;++newHead){
            if(newHead>bound){
              collapseheadHelper(arr,newTail,filter,modCount);
              break;
            }else if(!filter.test((int)arr[newHead])){
              fragmentedCollapseBodyHelper(arr,newHead,newTail,filter,modCount);
              break;
            }
          }
          break;
        }
      }
    }
    private void collapseHeadAndTail(int[] arr,int head,int tail,IntPredicate filter,int modCount){
      for(int headOffset=head+1;headOffset!=tail;++headOffset){
        if(!filter.test((int)arr[headOffset])){
          for(int tailOffset=tail-1;tailOffset!=headOffset;--tailOffset){
            if(!filter.test((int)arr[tailOffset])){
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
    @Override boolean fragmentedRemoveIf(int head,int tail,IntPredicate filter){
      int modCount=this.modCount;
      try{
        final int[] arr;
        if(filter.test((int)(arr=this.arr)[head])){
          if(filter.test((int)arr[tail])){
            fragmentedCollapseHeadAndTail(arr,head,tail,filter,modCount);
          }else{
            fragmentedCollapsehead(arr,head,tail,filter,modCount);
          }
          this.modCount=modCount+1;
          return true;
        }else{
          if(filter.test((int)arr[tail])){
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
    @Override boolean nonfragmentedRemoveIf(int head,int tail,IntPredicate filter){
      final int modCount=this.modCount;
      try{
        final int[] arr;
        if(filter.test((int)(arr=this.arr)[head])){
          if(head==tail){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.tail=-1;
          }else if(filter.test((int)arr[tail])){
            collapseHeadAndTail(arr,head,tail,filter,modCount);
          }else{
            collapsehead(arr,head,tail,filter,modCount);
          }
          this.modCount=modCount+1;
          return true;
        }else if(head!=tail){
          if(filter.test((int)arr[tail])){
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
    @Override public OmniIterator.OfInt iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniIterator.OfInt descendingIterator(){
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
      @Override public int nextInt(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final int[] arr;
          final var ret=(int)(arr=root.arr)[cursor];
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
      private void fragmentedAscendingRemove(int head,int lastRet,int tail,IntArrDeq root){
        int[] arr;
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
      private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,IntArrDeq root){
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
      @Override void uncheckedForEachRemaining(int cursor,IntConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int tail=(root=this.root).tail;
        try{
          final var arr=root.arr;
          if(cursor>tail){
            OmniArray.OfInt.ascendingForEach(arr,cursor,arr.length-1,action);
            cursor=0;
          }
          OmniArray.OfInt.ascendingForEach(arr,cursor,tail,action);
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
      @Override public int nextInt(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final int[] arr;
          final var ret=(int)(arr=root.arr)[cursor];
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
      private void fragmentedDescendingRemove(int head,int lastRet,int tail,IntArrDeq root){
        int[] arr;
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
      private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,IntArrDeq root){
        int tailDist,headDist;
        if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
          ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
          root.tail=tail-1;
        }else{
          if(headDist==0){
            root.head=head+1;
          }else{
            int[] arr;
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
      @Override void uncheckedForEachRemaining(int cursor,IntConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int head=(root=this.root).head;
        try{
          final var arr=root.arr;
          if(cursor<head){
            OmniArray.OfInt.descendingForEach(arr,0,cursor,action);
            cursor=arr.length-1;
          }
          OmniArray.OfInt.descendingForEach(arr,head,cursor,action);
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
        final int[] dst;
        int size,head;
        Checked clone;
        if((size=(++tail)-(head=this.head))<=0){
          clone=new Checked(0,dst=new int[size+=arr.length],size-1);
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
        }else{
          clone=new Checked(0,dst=new int[size],size-1);
        }
        ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        return clone;
      }
      return new Checked();
    }
    @Override public int removeLastInt(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final int[] arr;
        var ret=(int)((arr=this.arr)[tail]);
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
    @Override public int popInt(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final int[] arr;
        int head;
        var ret=(int)((arr=this.arr)[head=this.head]);
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
    @Override public void push(int val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void addLast(int val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override void uncheckedForEach(final int tail,IntConsumer action){
      final int modCount=this.modCount;
      try{
        super.uncheckedForEach(tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public int intElement(){
      if(tail!=-1){
        return (int)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override public int getLastInt(){
      final int tail;
      if((tail=this.tail)!=-1){
        return (int)arr[tail];
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
    @Override public int pollInt(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final int[] arr;
        int head;
        var ret=(int)((arr=this.arr)[head=this.head]);
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final int[] arr;
        var ret=(int)((arr=this.arr)[tail]);
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public Integer poll(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final int[] arr;
        int head;
        var ret=(Integer)((arr=this.arr)[head=this.head]);
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
    @Override public Integer pollLast(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final int[] arr;
        var ret=(Integer)((arr=this.arr)[tail]);
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
        final int[] arr;
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
        final int[] arr;
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
        final int[] arr;
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
        final int[] arr;
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
    @Override public long pollLong(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final int[] arr;
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
        final int[] arr;
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
    @Override
    boolean uncheckedremoveVal(int tail
    ,int val
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
    ,int val
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
