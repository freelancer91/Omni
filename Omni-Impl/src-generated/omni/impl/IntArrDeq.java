package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
public class IntArrDeq extends IntUntetheredArrSeq<Integer> implements OmniDeque.OfInt
{
  IntArrDeq(int head,int[] arr,int tail){
    super(head,arr,tail);
  }
  IntArrDeq(){
    super();
  }
  @Override public boolean add(int val){
    addLast(val);
    return true;
  }
  @Override public boolean offer(int val){
    addLast(val);
    return true;
  }
  @Override public boolean offerFirst(int val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(int val){
    addLast(val);
    return true;
  }
  @Override public boolean add(boolean val){
    addLast((int)(int)TypeUtil.castToByte(val));
    return true;
  }
  @Override public boolean add(byte val){
    addLast((int)(val));
    return true;
  }
  @Override public boolean add(char val){
    addLast((int)(val));
    return true;
  }
  @Override public boolean add(Integer val){
    addLast((int)val);
    return true;
  }
  @Override public void addFirst(Integer val){
    push((int)val);
  }
  @Override public void addLast(Integer val){
    addLast((int)val);
  }
  @Override public boolean offer(Integer val){
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
  @Override public void push(Integer val){
    push((int)val);
  }
  @Override public int intElement(){
    return (int)arr[head];
  }
  @Override public int getLastInt(){
    return (int)arr[tail];
  }
  @Override public Integer element(){
    return (int)arr[head];
  }
  @Override public Integer getFirst(){
    return (int)arr[head];
  }
  @Override public Integer getLast(){
    return (int)arr[tail];
  }
  @Override public int peekInt(){
    if(this.tail!=-1){
      return (int)arr[head];
    }
    return Integer.MIN_VALUE;
  }
  @Override public int peekLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)arr[tail];
    }
    return Integer.MIN_VALUE;
  }
  @Override public Integer peek(){
    if(this.tail!=-1){
      return (Integer)(arr[head]);
    }
    return null;
  }
  @Override public Integer peekFirst(){
    if(this.tail!=-1){
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
  @Override public long peekLong(){
    if(this.tail!=-1){
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
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)super.uncheckedRemoveFirst(tail);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)super.uncheckedRemoveLast(tail);
    }
    return Integer.MIN_VALUE;
  }
  @Override public Integer poll(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Integer)super.uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Integer pollFirst(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Integer)super.uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Integer pollLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Integer)super.uncheckedRemoveLast(tail);
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
  @Override public long pollLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (long)(super.uncheckedRemoveFirst(tail));
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollLastLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (long)(super.uncheckedRemoveLast(tail));
    }
    return Long.MIN_VALUE;
  }
  @Override public int popInt(){
    return (int)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public int removeLastInt(){
    return (int)super.uncheckedRemoveLast(this.tail);
  }
  @Override public Integer pop(){
    return (Integer)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Integer remove(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Integer removeFirst(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Integer removeLast(){
    return super.uncheckedRemoveLast(this.tail);
  }
  @Override public int[] toIntArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new int[size],0,size);
      }else{
        final int[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new int[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public Integer[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      Integer[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new Integer[size],0,size);
      }else{
        final int[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new Integer[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_BOXED_ARR;
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
        final int[] arr;
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
        final int[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new float[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
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
        final int[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new long[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,(int)TypeUtil.castToByte(val));  
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
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedContainsMatch(tail,v); 
  }
  @Override public boolean contains(float val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (double)val==(v=(int)val)){
      return super.uncheckedContainsMatch(tail,v);
    }
    return false;
  }
  @Override public boolean contains(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedContainsMatch(tail,v); 
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(int)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(int)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(int)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
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
    ){return super.uncheckedSearch(tail,(int)TypeUtil.castToByte(val));  
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
    ){final int v;if((v=(int)val)==val){return super.uncheckedSearch(tail,v);}
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
      if((tail=this.tail)!=-1
    ){final int v;if((double)val==(v=(int)val)){return super.uncheckedSearch(tail,v);}
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
      if((tail=this.tail)!=-1
    ){final int v;if((v=(int)val)==val){return super.uncheckedSearch(tail,v);}
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
      if((tail=this.tail)!=-1
    ){
      final int queryParam;
      if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(int)l)){
          return -1;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(int)f)){
          return -1;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(int)d)){
          return -1;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
      }else{
        return -1;
      }
      return super.uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public boolean removeVal(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,(int)TypeUtil.castToByte(val));  
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
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedRemoveFirstMatch(tail,v); 
  }
  @Override public boolean removeVal(float val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (double)val==(v=(int)val)){
      return super.uncheckedRemoveFirstMatch(tail,v);
    }
    return false;
  }
  @Override public boolean removeVal(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedRemoveFirstMatch(tail,v); 
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(int)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(int)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(int)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
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
      final int queryParam;
      if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(int)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(int)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(int)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,(int)TypeUtil.castToByte(val));  
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);    
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedRemoveLastMatch(tail,v); 
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (double)val==(v=(int)val)){
      return super.uncheckedRemoveLastMatch(tail,v);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedRemoveLastMatch(tail,v); 
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(int)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(int)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(int)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public OmniIterator.OfInt iterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-(tail=this.head))<=0){
        size+=arr.length;
      }
      return new AscendingItr(this,tail,size);
    }
    return new AscendingItr(this,-1,0);
  }
  @Override public OmniIterator.OfInt descendingIterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-this.head)<=0){
        size+=arr.length;
      }
      return new DescendingItr(this,tail,size);
    }
    return new DescendingItr(this,-1,0);
  }
  private static interface DefaultItr extends OmniIterator.OfInt{
    @Override public default Integer next(){
      return nextInt();
    }
  }
  private static class AscendingItr extends AscendingUntetheredArrSeqItr<Integer> implements DefaultItr{
    private AscendingItr(IntUntetheredArrSeq<Integer> root,int index,int numLeft){
      super(root,index,numLeft);
    }
    @Override public Object clone(){
      return new AscendingItr(root,index,numLeft);
    }
    @Override public void forEachRemaining(Consumer<? super Integer> action){
      if(numLeft!=0){
        super.uncheckedForEachRemaining(action::accept);
      }
    }
  }
  private static class DescendingItr extends DescendingUntetheredArrSeqItr<Integer> implements DefaultItr{
    private DescendingItr(IntUntetheredArrSeq<Integer> root,int index,int numLeft){
      super(root,index,numLeft);
    }
    @Override public Object clone(){
      return new DescendingItr(root,index,numLeft);
    }
    @Override public void forEachRemaining(Consumer<? super Integer> action){
      if(numLeft!=0){
        super.uncheckedForEachRemaining(action::accept);
      }
    }
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      int[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new int[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final int[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new int[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new IntArrDeq(head,copy,tail);
    }
    return new IntArrDeq();
  }
  @Override public boolean removeIf(IntPredicate filter){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveIf(tail,filter);
  }
  @Override public boolean removeIf(Predicate<? super Integer> filter){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveIf(tail,filter::test);
  }
  @Override public void forEach(IntConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      super.ascendingForEach(tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Integer> action){
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
        final int[] arr;
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
        final int[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=OmniArray.uncheckedArrResize(size+=arr.length,dst),0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
}
