package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
public class ByteArrDeq extends ByteUntetheredArrSeq<Byte> implements OmniDeque.OfByte
{
  ByteArrDeq(int head,byte[] arr,int tail){
    super(head,arr,tail);
  }
  ByteArrDeq(){
    super();
  }
  @Override public boolean add(byte val){
    addLast(val);
    return true;
  }
  @Override public boolean offer(byte val){
    addLast(val);
    return true;
  }
  @Override public boolean offerFirst(byte val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(byte val){
    addLast(val);
    return true;
  }
  @Override public boolean add(boolean val){
    addLast((byte)TypeUtil.castToByte(val));
    return true;
  }
  @Override public boolean add(Byte val){
    addLast((byte)val);
    return true;
  }
  @Override public void addFirst(Byte val){
    push((byte)val);
  }
  @Override public void addLast(Byte val){
    addLast((byte)val);
  }
  @Override public boolean offer(Byte val){
    addLast((byte)val);
    return true;
  }
  @Override public boolean offerFirst(Byte val){
    push((byte)val);
    return true;
  }
  @Override public boolean offerLast(Byte val){
    addLast((byte)val);
    return true;
  }
  @Override public void push(Byte val){
    push((byte)val);
  }
  @Override public byte byteElement(){
    return (byte)arr[head];
  }
  @Override public byte getLastByte(){
    return (byte)arr[tail];
  }
  @Override public Byte element(){
    return (byte)arr[head];
  }
  @Override public Byte getFirst(){
    return (byte)arr[head];
  }
  @Override public Byte getLast(){
    return (byte)arr[tail];
  }
  @Override public byte peekByte(){
    if(this.tail!=-1){
      return (byte)arr[head];
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte peekLastByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (byte)arr[tail];
    }
    return Byte.MIN_VALUE;
  }
  @Override public Byte peek(){
    if(this.tail!=-1){
      return (Byte)(arr[head]);
    }
    return null;
  }
  @Override public Byte peekFirst(){
    if(this.tail!=-1){
      return (Byte)(arr[head]);
    }
    return null;
  }
  @Override public Byte peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Byte)(arr[tail]);
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
  @Override public short peekShort(){
    if(this.tail!=-1){
      return (short)(arr[head]);
    }
    return Short.MIN_VALUE;
  }
  @Override public short peekLastShort(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (short)(arr[tail]);
    }
    return Short.MIN_VALUE;
  }
  @Override public byte pollByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (byte)super.uncheckedRemoveFirst(tail);
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte pollLastByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (byte)super.uncheckedRemoveLast(tail);
    }
    return Byte.MIN_VALUE;
  }
  @Override public Byte poll(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Byte)super.uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Byte pollFirst(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Byte)super.uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Byte pollLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Byte)super.uncheckedRemoveLast(tail);
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
  @Override public short pollShort(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (short)(super.uncheckedRemoveFirst(tail));
    }
    return Short.MIN_VALUE;
  }
  @Override public short pollLastShort(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (short)(super.uncheckedRemoveLast(tail));
    }
    return Short.MIN_VALUE;
  }
  @Override public byte popByte(){
    return (byte)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public byte removeLastByte(){
    return (byte)super.uncheckedRemoveLast(this.tail);
  }
  @Override public Byte pop(){
    return (Byte)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Byte remove(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Byte removeFirst(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Byte removeLast(){
    return super.uncheckedRemoveLast(this.tail);
  }
  @Override public byte[] toByteArray(){
    int tail;
    if((tail=this.tail)!=-1){
      byte[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new byte[size],0,size);
      }else{
        final byte[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new byte[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public Byte[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      Byte[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new Byte[size],0,size);
      }else{
        final byte[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new Byte[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
        final byte[] arr;
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
        final byte[] arr;
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
        final byte[] arr;
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
        final byte[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new int[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    int tail;
    if((tail=this.tail)!=-1){
      short[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new short[size],0,size);
      }else{
        final byte[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new short[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.castToByte(val));
  }
  @Override public boolean contains(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);
  }
  @Override public boolean contains(char val){
    final int tail;
    return val<=Byte.MAX_VALUE && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);
  }
  @Override public boolean contains(int val){
    final int tail;
    return val==(byte)val && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);
  }
  @Override public boolean contains(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(byte)val)==val && super.uncheckedContainsMatch(tail,v);
  }
  @Override public boolean contains(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(byte)val)==val && super.uncheckedContainsMatch(tail,v);
  }
  @Override public boolean contains(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(byte)val)==val && super.uncheckedContainsMatch(tail,v);
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Byte){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(byte)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(byte)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(byte)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(byte)d)){
          return false;
        }
      }else if(val instanceof Character){
        if((queryParam=(char)val)>Byte.MAX_VALUE){
          return false;
        }
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
    if((tail=this.tail)!=-1){ return super.uncheckedSearch(tail,TypeUtil.castToByte(val));
    }
    return -1;
  }
  @Override public int search(byte val){
    final int tail;
    if((tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(char val){
    final int tail;
    if(val<=Byte.MAX_VALUE && (tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
    if(val==(byte)val && (tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(byte)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(byte)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(byte)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Byte){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(byte)queryParam){
          return -1;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(byte)l)){
          return -1;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(byte)f)){
          return -1;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(byte)d)){
          return -1;
        }
      }else if(val instanceof Character){
        if((queryParam=(char)val)>Byte.MAX_VALUE){
          return -1;
        }
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
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.castToByte(val));
  }
  @Override public boolean removeVal(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);
  }
  @Override public boolean removeVal(char val){
    final int tail;
    return val<=Byte.MAX_VALUE && (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);
  }
  @Override public boolean removeVal(int val){
    final int tail;
    return val==(byte)val && (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);
  }
  @Override public boolean removeVal(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(byte)val)==val && super.uncheckedRemoveFirstMatch(tail,v);
  }
  @Override public boolean removeVal(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(byte)val)==val && super.uncheckedRemoveFirstMatch(tail,v);
  }
  @Override public boolean removeVal(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(byte)val)==val && super.uncheckedRemoveFirstMatch(tail,v);
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Byte){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(byte)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(byte)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(byte)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(byte)d)){
          return false;
        }
      }else if(val instanceof Character){
        if((queryParam=(char)val)>Byte.MAX_VALUE){
          return false;
        }
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
      if(val instanceof Byte){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(byte)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(byte)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(byte)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(byte)d)){
          return false;
        }
      }else if(val instanceof Character){
        if((queryParam=(char)val)>Byte.MAX_VALUE){
          return false;
        }
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
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.castToByte(val));
  }
  @Override public boolean removeLastOccurrence(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(char val){
    final int tail;
    return val<=Byte.MAX_VALUE && (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    return val==(byte)val && (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(byte)val)==val && super.uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(byte)val)==val && super.uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(byte)val)==val && super.uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Byte){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(byte)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(byte)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(byte)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(byte)d)){
          return false;
        }
      }else if(val instanceof Character){
        if((queryParam=(char)val)>Byte.MAX_VALUE){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
      }else{
        return false;
      } 
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;
  }
  @Override public OmniIterator.OfByte iterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-(tail=this.head))<=0){
        size+=arr.length;
      }
      return new AscendingUntetheredArrSeqItr<Byte>(this,tail,size);
    }
    return new AscendingUntetheredArrSeqItr<Byte>(this,-1,0);
  }
  @Override public OmniIterator.OfByte descendingIterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-this.head)<=0){
        size+=arr.length;
      }
      return new DescendingUntetheredArrSeqItr<Byte>(this,tail,size);
    }
    return new DescendingUntetheredArrSeqItr<Byte>(this,-1,0);
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      byte[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new byte[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final byte[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new byte[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new ByteArrDeq(head,copy,tail);
    }
    return new ByteArrDeq();
  }
  @Override public boolean removeIf(BytePredicate filter){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveIf(tail,filter);
  }
  @Override public boolean removeIf(Predicate<? super Byte> filter){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveIf(tail,filter::test);
  }
  @Override public void forEach(ByteConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      super.ascendingForEach(tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Byte> action){
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
        final byte[] arr;
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
        final byte[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=OmniArray.uncheckedArrResize(size+=arr.length,dst),0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
}
