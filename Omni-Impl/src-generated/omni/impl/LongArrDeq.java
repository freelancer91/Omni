package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
public class LongArrDeq extends LongUntetheredArrSeq<Long> implements OmniDeque.OfLong
{
  LongArrDeq(int head,long[] arr,int tail){
    super(head,arr,tail);
  }
  LongArrDeq(){
    super();
  }
  @Override public boolean add(long val){
    addLast(val);
    return true;
  }
  @Override public boolean offer(long val){
    addLast(val);
    return true;
  }
  @Override public boolean offerFirst(long val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(long val){
    addLast(val);
    return true;
  }
  @Override public boolean add(boolean val){
    addLast((long)TypeUtil.castToLong(val));
    return true;
  }
  @Override public boolean add(byte val){
    addLast((long)(val));
    return true;
  }
  @Override public boolean add(char val){
    addLast((long)(val));
    return true;
  }
  @Override public boolean add(int val){
    addLast((long)(val));
    return true;
  }
  @Override public boolean add(Long val){
    addLast((long)val);
    return true;
  }
  @Override public void addFirst(Long val){
    push((long)val);
  }
  @Override public void addLast(Long val){
    addLast((long)val);
  }
  @Override public boolean offer(Long val){
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
  @Override public void push(Long val){
    push((long)val);
  }
  @Override public long longElement(){
    return (long)arr[head];
  }
  @Override public long getLastLong(){
    return (long)arr[tail];
  }
  @Override public Long element(){
    return (long)arr[head];
  }
  @Override public Long getFirst(){
    return (long)arr[head];
  }
  @Override public Long getLast(){
    return (long)arr[tail];
  }
  @Override public long peekLong(){
    if(this.tail!=-1){
      return (long)arr[head];
    }
    return Long.MIN_VALUE;
  }
  @Override public long peekLastLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (long)arr[tail];
    }
    return Long.MIN_VALUE;
  }
  @Override public Long peek(){
    if(this.tail!=-1){
      return (Long)(arr[head]);
    }
    return null;
  }
  @Override public Long peekFirst(){
    if(this.tail!=-1){
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
    if(this.tail!=-1){
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
    if(this.tail!=-1){
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
    final int tail;
    if((tail=this.tail)!=-1){
      return (long)super.uncheckedRemoveFirst(tail);
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollLastLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (long)super.uncheckedRemoveLast(tail);
    }
    return Long.MIN_VALUE;
  }
  @Override public Long poll(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Long)super.uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Long pollFirst(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Long)super.uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Long pollLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Long)super.uncheckedRemoveLast(tail);
    }
    return null;
  }
  @Override public double pollDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (double)(super.uncheckedRemoveFirst(tail));
    }
    return Double.NaN;
  }
  @Override public double pollLastDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (double)(super.uncheckedRemoveLast(tail));
    }
    return Double.NaN;
  }
  @Override public float pollFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (float)(super.uncheckedRemoveFirst(tail));
    }
    return Float.NaN;
  }
  @Override public float pollLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (float)(super.uncheckedRemoveLast(tail));
    }
    return Float.NaN;
  }
  @Override public long popLong(){
    return (long)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public long removeLastLong(){
    return (long)super.uncheckedRemoveLast(this.tail);
  }
  @Override public Long pop(){
    return (Long)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Long remove(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Long removeFirst(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Long removeLast(){
    return super.uncheckedRemoveLast(this.tail);
  }
  @Override public long[] toLongArray(){
    int tail;
    if((tail=this.tail)!=-1){
      long[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new long[size],0,size);
      }else{
        final long[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new long[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public Long[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      Long[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new Long[size],0,size);
      }else{
        final long[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new Long[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int tail;
    if((tail=this.tail)!=-1){
      double[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new double[size],0,size);
      }else{
        final long[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new double[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int tail;
    if((tail=this.tail)!=-1){
      float[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new float[size],0,size);
      }else{
        final long[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new float[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.castToLong(val));    
  }
  @Override public boolean contains(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);      
  }
  @Override public boolean contains(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);      
  }
  @Override public boolean contains(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);      
  }
  @Override public boolean contains(long val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);      
  }
  @Override public boolean contains(float val){
    final int tail;
    final long v;
    return (tail=this.tail)!=-1 && TypeUtil.floatEquals(val,v=(long)val) && super.uncheckedContainsMatch(tail,v);
  }
  @Override public boolean contains(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final long queryParam;
      if(!TypeUtil.doubleEquals(val,queryParam=(long)val)){
        return false;
      }
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final long queryParam;
      if(val instanceof Long || val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).longValue();
      }else if(val instanceof Float){
        final float f;
        if(!TypeUtil.floatEquals(f=(float)val,queryParam=(long)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if(!TypeUtil.doubleEquals(d=(double)val,queryParam=(long)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToLong((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public int search(boolean val){
    final int tail;
      if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,TypeUtil.castToLong(val));    
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
      if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,val);      
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
      if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,val);      
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
      if((tail=this.tail)!=-1
    ){final long v;if(TypeUtil.floatEquals(val,v=(long)val)){return super.uncheckedSearch(tail,v);}
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
      if((tail=this.tail)!=-1
    ){final long v;if(TypeUtil.doubleEquals(val,v=(long)val)){return super.uncheckedSearch(tail,v);}
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
      if((tail=this.tail)!=-1
    ){
      final long queryParam;
      if(val instanceof Long || val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).longValue();
      }else if(val instanceof Float){
        final float f;
        if(!TypeUtil.floatEquals(f=(float)val,queryParam=(long)f)){
          return -1;
        }
      }else if(val instanceof Double){
        final double d;
        if(!TypeUtil.doubleEquals(d=(double)val,queryParam=(long)d)){
          return -1;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToLong((boolean)val);
      }else{
        return -1;
      }
      return super.uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public boolean removeVal(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.castToLong(val));    
  }
  @Override public boolean removeVal(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);      
  }
  @Override public boolean removeVal(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);      
  }
  @Override public boolean removeVal(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);      
  }
  @Override public boolean removeVal(long val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);      
  }
  @Override public boolean removeVal(float val){
    final int tail;
    final long v;
    return (tail=this.tail)!=-1 && TypeUtil.floatEquals(val,v=(long)val) && super.uncheckedRemoveFirstMatch(tail,v);
  }
  @Override public boolean removeVal(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final long queryParam;
      if(!TypeUtil.doubleEquals(val,queryParam=(long)val)){
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final long queryParam;
      if(val instanceof Long || val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).longValue();
      }else if(val instanceof Float){
        final float f;
        if(!TypeUtil.floatEquals(f=(float)val,queryParam=(long)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if(!TypeUtil.doubleEquals(d=(double)val,queryParam=(long)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToLong((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean removeFirstOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final long queryParam;
      if(val instanceof Long || val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).longValue();
      }else if(val instanceof Float){
        final float f;
        if(!TypeUtil.floatEquals(f=(float)val,queryParam=(long)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if(!TypeUtil.doubleEquals(d=(double)val,queryParam=(long)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToLong((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.castToLong(val));    
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);      
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);      
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    final long v;
    return (tail=this.tail)!=-1 && TypeUtil.floatEquals(val,v=(long)val) && super.uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final long queryParam;
      if(!TypeUtil.doubleEquals(val,queryParam=(long)val)){
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final long queryParam;
      if(val instanceof Long || val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).longValue();
      }else if(val instanceof Float){
        final float f;
        if(!TypeUtil.floatEquals(f=(float)val,queryParam=(long)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if(!TypeUtil.doubleEquals(d=(double)val,queryParam=(long)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToLong((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public OmniIterator.OfLong iterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-(tail=this.head))<=0){
        size+=arr.length;
      }
      return new AscendingUntetheredArrSeqItr<Long>(this,tail,size);
    }
    return new AscendingUntetheredArrSeqItr<Long>(this,-1,0);
  }
  @Override public OmniIterator.OfLong descendingIterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-this.head)<=0){
        size+=arr.length;
      }
      return new DescendingUntetheredArrSeqItr<Long>(this,tail,size);
    }
    return new DescendingUntetheredArrSeqItr<Long>(this,-1,0);
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      long[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new long[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final long[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new long[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new LongArrDeq(head,copy,tail);
    }
    return new LongArrDeq();
  }
  @Override public boolean removeIf(LongPredicate filter){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveIf(tail,filter);
  }
  @Override public boolean removeIf(Predicate<? super Long> filter){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveIf(tail,filter::test);
  }
  @Override public void forEach(LongConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      super.ascendingForEach(tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Long> action){
    final int tail;
    if((tail=this.tail)!=-1){
      super.ascendingForEach(tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    int tail;
    if((tail=this.tail)!=-1){
      final T[] dst;
      final int head;
      int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=arrConstructor.apply(size),0,size);
      }else{
        final long[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=arrConstructor.apply(size+=arr.length),0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return arrConstructor.apply(0);
  }
  @Override public <T> T[] toArray(T[] dst){
    int tail;
    if((tail=this.tail)!=-1){
      final int head;
      int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
      }else{
        final long[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=OmniArray.uncheckedArrResize(size+=arr.length,dst),0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
}
