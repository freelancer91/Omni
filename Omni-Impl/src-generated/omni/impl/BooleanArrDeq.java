package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
public class BooleanArrDeq extends BooleanUntetheredArrSeq<Boolean> implements OmniDeque.OfBoolean
{
  BooleanArrDeq(int head,boolean[] arr,int tail){
    super(head,arr,tail);
  }
  BooleanArrDeq(){
    super();
  }
  @Override public boolean offerFirst(boolean val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(boolean val){
    addLast(val);
    return true;
  }
  @Override public boolean offer(boolean val){
    addLast(val);
    return true;
  }
  @Override public void addFirst(boolean val){
    push(val);
  }
  @Override public boolean add(boolean val){
    addLast(val);
    return true;
  }
  @Override public boolean booleanElement(){
    return (boolean)arr[head];
  }
  @Override public boolean getLastBoolean(){
    return (boolean)arr[tail];
  }
  @Override public boolean peekBoolean(){
    if(this.tail!=-1){
      return (boolean)arr[head];
    }
    return false;
  }
  @Override public boolean peekLastBoolean(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (boolean)arr[tail];
    }
    return false;
  }
  @Override public boolean pollBoolean(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (boolean)uncheckedRemoveFirst(tail);
    }
    return false;
  }
  @Override public boolean pollLastBoolean(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (boolean)uncheckedRemoveLast(tail);
    }
    return false;
  }
  @Override public boolean[] toBooleanArray(){
    int tail;
    if((tail=this.tail)!=-1){
      boolean[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new boolean[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new boolean[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override public boolean removeLastBoolean(){
    return (boolean)uncheckedRemoveLast(this.tail);
  }
  @Override public boolean popBoolean(){
    return (boolean)uncheckedRemoveFirst(this.tail);
  }
  @Override public OmniIterator.OfBoolean iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public OmniIterator.OfBoolean descendingIterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public Object clone(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean removeIf(BooleanPredicate filter){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter);
  }
  @Override public boolean removeIf(Predicate<? super Boolean> filter){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter::test);
  }
  @Override public void forEach(BooleanConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action::accept);
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
        final boolean[] arr;
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
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=OmniArray.uncheckedArrResize(size+=arr.length,dst),0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public Boolean[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      Boolean[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new Boolean[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new Boolean[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override public Boolean element(){
    return this.booleanElement();
  }
  @Override public Boolean getLast(){
    return this.getLastBoolean();
  }
  @Override public Boolean getFirst(){
    return this.booleanElement();
  }
  @Override public Boolean removeFirst(){
    return uncheckedRemoveFirst(this.tail);
  }
  @Override public Boolean removeLast(){
    return uncheckedRemoveLast(this.tail);
  }
  @Override public Boolean remove(){
    return uncheckedRemoveFirst(this.tail);
  }
  @Override public boolean offerFirst(Boolean val){
    push((boolean)val);
    return true;
  }
  @Override public boolean offerLast(Boolean val){
    addLast((boolean)val);
    return true;
  }
  @Override public boolean add(Boolean val){
    addLast((boolean)val);
    return true;
  }
  @Override public void addLast(Boolean val){
    addLast((boolean)val);
  }
  @Override public boolean offer(Boolean val){
    addLast((boolean)val);
    return true;
  }
  @Override public void addFirst(Boolean val){
    push((boolean)val);
  }
  @Override public void push(Boolean val){
    push((boolean)val);
  }
  @Override public Boolean peek(){
    if(this.tail!=-1){
      return (Boolean)(arr[head]);
    }
    return null;
  }
  @Override public Boolean peekFirst(){
    if(this.tail!=-1){
      return (Boolean)(arr[head]);
    }
    return null;
  }
  @Override public Boolean peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Boolean)(arr[tail]);
    }
    return null;
  }
  @Override public Boolean poll(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Boolean)uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Boolean pollLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Boolean)uncheckedRemoveLast(tail);
    }
    return null;
  }
  @Override public Boolean pollFirst(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Boolean)uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Boolean pop(){
    return (Boolean)uncheckedRemoveFirst(this.tail);
  }
  @Override public double peekDouble(){
    if(this.tail!=-1){
      return TypeUtil.castToDouble(arr[head]);
    }
    return Double.NaN;
  }
  @Override public double peekLastDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToDouble(arr[tail]);
    }
    return Double.NaN;
  }
  @Override public double pollDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToDouble(uncheckedRemoveFirst(tail));
    }
    return Double.NaN;
  }
  @Override public double pollLastDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToDouble(uncheckedRemoveLast(tail));
    }
    return Double.NaN;
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
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new double[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float peekFloat(){
    if(this.tail!=-1){
      return TypeUtil.castToFloat(arr[head]);
    }
    return Float.NaN;
  }
  @Override public float pollLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToFloat(uncheckedRemoveLast(tail));
    }
    return Float.NaN;
  }
  @Override public float pollFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToFloat(uncheckedRemoveFirst(tail));
    }
    return Float.NaN;
  }
  @Override public float peekLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToFloat(arr[tail]);
    }
    return Float.NaN;
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
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new float[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long peekLastLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToLong(arr[tail]);
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToLong(uncheckedRemoveFirst(tail));
    }
    return Long.MIN_VALUE;
  }
  @Override public long peekLong(){
    if(this.tail!=-1){
      return TypeUtil.castToLong(arr[head]);
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollLastLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToLong(uncheckedRemoveLast(tail));
    }
    return Long.MIN_VALUE;
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
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new long[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int peekLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)TypeUtil.castToByte(arr[tail]);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)TypeUtil.castToByte(uncheckedRemoveFirst(tail));
    }
    return Integer.MIN_VALUE;
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
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new int[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public int peekInt(){
    if(this.tail!=-1){
      return (int)TypeUtil.castToByte(arr[head]);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)TypeUtil.castToByte(uncheckedRemoveLast(tail));
    }
    return Integer.MIN_VALUE;
  }
  @Override public short peekShort(){
    if(this.tail!=-1){
      return (short)TypeUtil.castToByte(arr[head]);
    }
    return Short.MIN_VALUE;
  }
  @Override public short pollShort(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (short)TypeUtil.castToByte(uncheckedRemoveFirst(tail));
    }
    return Short.MIN_VALUE;
  }
  @Override public short peekLastShort(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (short)TypeUtil.castToByte(arr[tail]);
    }
    return Short.MIN_VALUE;
  }
  @Override public short pollLastShort(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (short)TypeUtil.castToByte(uncheckedRemoveLast(tail));
    }
    return Short.MIN_VALUE;
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
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new short[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public char peekLastChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToChar(arr[tail]);
    }
    return Character.MIN_VALUE;
  }
  @Override public char pollLastChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToChar(uncheckedRemoveLast(tail));
    }
    return Character.MIN_VALUE;
  }
  @Override public char peekChar(){
    if(this.tail!=-1){
      return TypeUtil.castToChar(arr[head]);
    }
    return Character.MIN_VALUE;
  }
  @Override public char pollChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToChar(uncheckedRemoveFirst(tail));
    }
    return Character.MIN_VALUE;
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
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new char[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public byte pollByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToByte(uncheckedRemoveFirst(tail));
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte peekByte(){
    if(this.tail!=-1){
      return TypeUtil.castToByte(arr[head]);
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte peekLastByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToByte(arr[tail]);
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte pollLastByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToByte(uncheckedRemoveLast(tail));
    }
    return Byte.MIN_VALUE;
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
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new byte[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public void addLast(boolean val){
    var arr=this.arr;
    int tail;
    if((tail=this.tail)!=-1){
      int head;
      if((head=this.head)<=tail){
        if(++tail==arr.length && head==0){
          ArrCopy.uncheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(tail)],0,tail);
          this.arr=arr;
        }
      }else if(++tail==head){
        this.head=0;
        final var tmp=new boolean[OmniArray.growBy50Pct(tail=arr.length)];
        final int copyLength;
        ArrCopy.uncheckedCopy(arr,head,tmp,0,copyLength=tail-head);
        ArrCopy.uncheckedCopy(arr,0,tmp,copyLength,head);
        this.arr=arr=tmp;
      }
      arr[tail]=val;
      this.tail=tail;
    }else{
      if(arr==null){
        this.arr=new boolean[]{val};
      }else{
        if(arr==OmniArray.OfBoolean.DEFAULT_ARR){
          this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }
        arr[0]=val;
      }
      this.head=0;
      this.tail=0;
    }
  }
  @Override public void push(boolean val){
    var arr=this.arr;
    int tail;
    if((tail=this.tail)!=-1){
      int head;
      if((head=this.head)<=tail){
        if(head==0 && tail==arr.length-1){
          final var tmp=new boolean[head=OmniArray.growBy50Pct(++tail)];
          this.tail=head-1;
          ArrCopy.uncheckedCopy(arr,0,tmp,head-=tail,tail);
          this.arr=arr=tmp;
        }
        --head;
      }else if(--head==tail){
        int arrLength;
        final var tmp=new boolean[head=OmniArray.growBy50Pct(arrLength=arr.length)];
        this.tail=head-1;
        ArrCopy.uncheckedCopy(arr,0,tmp,head-=(++tail),tail);
        ArrCopy.uncheckedCopy(arr,tail,tmp,head-=(arrLength-=tail),arrLength);
        this.arr=arr=tmp;
        --head;
      }
      arr[head]=val;
      this.head=head;
    }else{
      if(arr==null){
        this.arr=new boolean[]{val};
        this.head=0;
        this.tail=0;
      }else if(arr==OmniArray.OfBoolean.DEFAULT_ARR){
        this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
        arr[OmniArray.DEFAULT_ARR_SEQ_CAP-1]=val;
        this.head=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.tail=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
      }else{
        arr[tail=arr.length-1]=val;
        this.tail=tail;
        this.head=tail;
      }
    }
  }
  boolean uncheckedRemoveIf(int tail,BooleanPredicate action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  void uncheckedForEach(int tail,BooleanConsumer action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      for(int bound=arr.length;;){
        action.accept((boolean)arr[head]);
        if(++head==bound){
          head=0;
          break;
        }
      }
    }
    for(;;){
      action.accept((boolean)arr[head]);
      if(head==tail){
        break;
      }
      ++head;
    }
  }
  @Override public boolean contains(boolean queryParam){
    final int tail;
    if((tail=this.tail)!=-1){
      return uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean contains(int val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(val){
        default:
          return false;
        case 0:
          queryParam=false;
          break;
        case 1:
          queryParam=true;
      }
      return uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean contains(long val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val==0L){
        queryParam=false;
      }else if(val==1L){  
        queryParam=true;
      }else{
        return false;
      }
      return uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean contains(float val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          queryParam=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          queryParam=true;
      }
      return uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean contains(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
        queryParam=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        queryParam=true;
      }else{
        return false;
      }
      return uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val instanceof Boolean){
        queryParam=(boolean)val;
      }else if(val instanceof Integer || val instanceof Short || val instanceof Byte){
        switch(((Number)val).intValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)==0){
          queryParam=false;
        }else if(l==1){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Float){
        switch(Float.floatToRawIntBits((float)val)){
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            queryParam=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            queryParam=true;
        }
      }else if(val instanceof Double){
        long bits;
        if((bits=Double.doubleToRawLongBits((double)val))==0 || bits==Long.MIN_VALUE){
          queryParam=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Character){
        switch(((Character)val).charValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else{
        return false;
      }
      return uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public int search(boolean queryParam){
    final int tail;
    if((tail=this.tail)!=-1){
      return uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(val){
        default:
          return -1;
        case 0:
          queryParam=false;
          break;
        case 1:
          queryParam=true;
      }
      return uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val==0L){
        queryParam=false;
      }else if(val==1L){  
        queryParam=true;
      }else{
        return -1;
      }
      return uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(Float.floatToRawIntBits(val)){
        default:
          return -1;
        case 0:
        case Integer.MIN_VALUE:
          queryParam=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          queryParam=true;
      }
      return uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
        queryParam=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        queryParam=true;
      }else{
        return -1;
      }
      return uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val instanceof Boolean){
        queryParam=(boolean)val;
      }else if(val instanceof Integer || val instanceof Short || val instanceof Byte){
        switch(((Number)val).intValue()){
          default:
            return -1;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)==0){
          queryParam=false;
        }else if(l==1){
          queryParam=true;
        }else{
          return -1;
        }
      }else if(val instanceof Float){
        switch(Float.floatToRawIntBits((float)val)){
          default:
            return -1;
          case 0:
          case Integer.MIN_VALUE:
            queryParam=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            queryParam=true;
        }
      }else if(val instanceof Double){
        long bits;
        if((bits=Double.doubleToRawLongBits((double)val))==0 || bits==Long.MIN_VALUE){
          queryParam=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          queryParam=true;
        }else{
          return -1;
        }
      }else if(val instanceof Character){
        switch(((Character)val).charValue()){
          default:
            return -1;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else{
        return -1;
      }
      return uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public boolean removeVal(boolean queryParam){
    final int tail;
    if((tail=this.tail)!=-1){
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveFirstMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveFirstMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeVal(int val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(val){
        default:
          return false;
        case 0:
          queryParam=false;
          break;
        case 1:
          queryParam=true;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveFirstMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveFirstMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeVal(long val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val==0L){
        queryParam=false;
      }else if(val==1L){  
        queryParam=true;
      }else{
        return false;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveFirstMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveFirstMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeVal(float val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          queryParam=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          queryParam=true;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveFirstMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveFirstMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeVal(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
        queryParam=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        queryParam=true;
      }else{
        return false;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveFirstMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveFirstMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val instanceof Boolean){
        queryParam=(boolean)val;
      }else if(val instanceof Integer || val instanceof Short || val instanceof Byte){
        switch(((Number)val).intValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)==0){
          queryParam=false;
        }else if(l==1){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Float){
        switch(Float.floatToRawIntBits((float)val)){
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            queryParam=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            queryParam=true;
        }
      }else if(val instanceof Double){
        long bits;
        if((bits=Double.doubleToRawLongBits((double)val))==0 || bits==Long.MIN_VALUE){
          queryParam=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Character){
        switch(((Character)val).charValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else{
        return false;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveFirstMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveFirstMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeFirstOccurrence(boolean queryParam){
    final int tail;
    if((tail=this.tail)!=-1){
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveFirstMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveFirstMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeFirstOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val instanceof Boolean){
        queryParam=(boolean)val;
      }else if(val instanceof Integer || val instanceof Short || val instanceof Byte){
        switch(((Number)val).intValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)==0){
          queryParam=false;
        }else if(l==1){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Float){
        switch(Float.floatToRawIntBits((float)val)){
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            queryParam=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            queryParam=true;
        }
      }else if(val instanceof Double){
        long bits;
        if((bits=Double.doubleToRawLongBits((double)val))==0 || bits==Long.MIN_VALUE){
          queryParam=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Character){
        switch(((Character)val).charValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else{
        return false;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveFirstMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveFirstMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(boolean queryParam){
    final int tail;
    if((tail=this.tail)!=-1){
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveLastMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveLastMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(val){
        default:
          return false;
        case 0:
          queryParam=false;
          break;
        case 1:
          queryParam=true;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveLastMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveLastMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val==0L){
        queryParam=false;
      }else if(val==1L){  
        queryParam=true;
      }else{
        return false;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveLastMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveLastMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          queryParam=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          queryParam=true;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveLastMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveLastMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
        queryParam=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        queryParam=true;
      }else{
        return false;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveLastMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveLastMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val instanceof Boolean){
        queryParam=(boolean)val;
      }else if(val instanceof Integer || val instanceof Short || val instanceof Byte){
        switch(((Number)val).intValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)==0){
          queryParam=false;
        }else if(l==1){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Float){
        switch(Float.floatToRawIntBits((float)val)){
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            queryParam=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            queryParam=true;
        }
      }else if(val instanceof Double){
        long bits;
        if((bits=Double.doubleToRawLongBits((double)val))==0 || bits==Long.MIN_VALUE){
          queryParam=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Character){
        switch(((Character)val).charValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else{
        return false;
      }
      final int head;
      if((head=this.head)<=tail){
        return nonfragmentedRemoveLastMatch(head,tail,queryParam);
      }else{
        return fragmentedRemoveLastMatch(head,tail,queryParam);
      }
    }
    return false;
  }
  boolean uncheckedRemoveLast(int tail){
    final boolean[] arr;
    final var ret=(arr=this.arr)[tail];
    switch(Integer.signum(tail-this.head))
    {
      case 0:
        this.tail=-1;
        return ret;
      case -1:
        //fragmented
        if(--tail==-1){
          tail=arr.length-1;
        }
        break;
      default:
        --tail;
    }
    this.tail=tail;
    return ret;
  }
  boolean uncheckedRemoveFirst(int tail){
    int head;
    final boolean[] arr;
    final var ret=(arr=this.arr)[head=this.head];
    switch(Integer.signum(tail-head))
    {
      case 0:
        this.tail=-1;
        return ret;
      case -1:
        //fragmented
        if(++head==arr.length){
          head=0;
        }
        break;
      default:
        ++head;
    }
    this.head=head;
    return ret;
  }
}
