package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
public class CharArrDeq extends CharUntetheredArrSeq<Character> implements OmniDeque.OfChar
{
  CharArrDeq(int head,char[] arr,int tail){
    super(head,arr,tail);
  }
  CharArrDeq(){
    super();
  }
  @Override public boolean add(char val){
    addLast(val);
    return true;
  }
  @Override public boolean offer(char val){
    addLast(val);
    return true;
  }
  @Override public boolean offerFirst(char val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(char val){
    addLast(val);
    return true;
  }
  @Override public boolean add(boolean val){
    addLast((char)TypeUtil.castToChar(val));
    return true;
  }
  @Override public boolean add(Character val){
    addLast((char)val);
    return true;
  }
  @Override public void addFirst(Character val){
    push((char)val);
  }
  @Override public void addLast(Character val){
    addLast((char)val);
  }
  @Override public boolean offer(Character val){
    addLast((char)val);
    return true;
  }
  @Override public boolean offerFirst(Character val){
    push((char)val);
    return true;
  }
  @Override public boolean offerLast(Character val){
    addLast((char)val);
    return true;
  }
  @Override public void push(Character val){
    push((char)val);
  }
  @Override public char charElement(){
    return (char)arr[head];
  }
  @Override public char getLastChar(){
    return (char)arr[tail];
  }
  @Override public Character element(){
    return (char)arr[head];
  }
  @Override public Character getFirst(){
    return (char)arr[head];
  }
  @Override public Character getLast(){
    return (char)arr[tail];
  }
  @Override public char peekChar(){
    if(this.tail!=-1){
      return (char)arr[head];
    }
    return Character.MIN_VALUE;
  }
  @Override public char peekLastChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (char)arr[tail];
    }
    return Character.MIN_VALUE;
  }
  @Override public Character peek(){
    if(this.tail!=-1){
      return (Character)(arr[head]);
    }
    return null;
  }
  @Override public Character peekFirst(){
    if(this.tail!=-1){
      return (Character)(arr[head]);
    }
    return null;
  }
  @Override public Character peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Character)(arr[tail]);
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
  @Override public int peekInt(){
    if(this.tail!=-1){
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
  @Override public char pollChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (char)super.uncheckedRemoveFirst(tail);
    }
    return Character.MIN_VALUE;
  }
  @Override public char pollLastChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (char)super.uncheckedRemoveLast(tail);
    }
    return Character.MIN_VALUE;
  }
  @Override public Character poll(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Character)super.uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Character pollFirst(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Character)super.uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Character pollLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Character)super.uncheckedRemoveLast(tail);
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
  @Override public int pollInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)(super.uncheckedRemoveFirst(tail));
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)(super.uncheckedRemoveLast(tail));
    }
    return Integer.MIN_VALUE;
  }
  @Override public char popChar(){
    return (char)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public char removeLastChar(){
    return (char)super.uncheckedRemoveLast(this.tail);
  }
  @Override public Character pop(){
    return (Character)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Character remove(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Character removeFirst(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Character removeLast(){
    return super.uncheckedRemoveLast(this.tail);
  }
  @Override public char[] toCharArray(){
    int tail;
    if((tail=this.tail)!=-1){
      char[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new char[size],0,size);
      }else{
        final char[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new char[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public Character[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      Character[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new Character[size],0,size);
      }else{
        final char[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new Character[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
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
        final char[] arr;
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
        final char[] arr;
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
        final char[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new long[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
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
        final char[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new int[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.castToChar(val));
  }
  @Override public boolean contains(byte val){
    final int tail;
    return val>=0 && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);
  }
  @Override public boolean contains(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);
  }
  @Override public boolean contains(short val){
    final int tail;
    return val>=0 && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);
  }
  @Override public boolean contains(int val){
    final int tail;
    return val==(char)val && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val); 
  }
  @Override public boolean contains(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedContainsMatch(tail,v);
  }
  @Override public boolean contains(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedContainsMatch(tail,v);
  }
  @Override public boolean contains(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedContainsMatch(tail,v);
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Character){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(char)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(char)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(char)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(char)d)){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToChar((boolean)val);
      }else{
        return false;
      } 
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;  
  }
  @Override public int search(boolean val){
    final int tail;
    if((tail=this.tail)!=-1){ return super.uncheckedSearch(tail,TypeUtil.castToChar(val));
    }
    return -1;
  }
  @Override public int search(char val){
    final int tail;
    if((tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(short val){
    final int tail;
    if(val>=0 && (tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
    if(val==(char)val && (tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(char)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(char)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(char)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Character){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(char)queryParam){
          return -1;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(char)l)){
          return -1;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(char)f)){
          return -1;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(char)d)){
          return -1;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToChar((boolean)val);
      }else{
        return -1;
      } 
      return super.uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public boolean removeVal(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.castToChar(val));
  }
  @Override public boolean removeVal(byte val){
    final int tail;
    return val>=0 && (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);
  }
  @Override public boolean removeVal(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);
  }
  @Override public boolean removeVal(short val){
    final int tail;
    return val>=0 && (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);
  }
  @Override public boolean removeVal(int val){
    final int tail;
    return val==(char)val && (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val); 
  }
  @Override public boolean removeVal(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveFirstMatch(tail,v);
  }
  @Override public boolean removeVal(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveFirstMatch(tail,v);
  }
  @Override public boolean removeVal(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveFirstMatch(tail,v);
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Character){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(char)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(char)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(char)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(char)d)){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToChar((boolean)val);
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
      if(val instanceof Character){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(char)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(char)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(char)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(char)d)){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToChar((boolean)val);
      }else{
        return false;
      } 
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;  
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.castToChar(val));
  }
  @Override public boolean removeLastOccurrence(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(short val){
    final int tail;
    return val>=0 && (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    return val==(char)val && (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val); 
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Character){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(char)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(char)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(char)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(char)d)){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToChar((boolean)val);
      }else{
        return false;
      } 
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;  
  }
  @Override public OmniIterator.OfChar iterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-(tail=this.head))<=0){
        size+=arr.length;
      }
      return new AscendingUntetheredArrSeqItr<Character>(this,tail,size);
    }
    return new AscendingUntetheredArrSeqItr<Character>(this,-1,0);
  }
  @Override public OmniIterator.OfChar descendingIterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-this.head)<=0){
        size+=arr.length;
      }
      return new DescendingUntetheredArrSeqItr<Character>(this,tail,size);
    }
    return new DescendingUntetheredArrSeqItr<Character>(this,-1,0);
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      char[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new char[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final char[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new char[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new CharArrDeq(head,copy,tail);
    }
    return new CharArrDeq();
  }
  @Override public boolean removeIf(CharPredicate filter){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveIf(tail,filter);
  }
  @Override public boolean removeIf(Predicate<? super Character> filter){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveIf(tail,filter::test);
  }
  @Override public void forEach(CharConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      super.ascendingForEach(tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Character> action){
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
        final char[] arr;
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
        final char[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=OmniArray.uncheckedArrResize(size+=arr.length,dst),0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
}
