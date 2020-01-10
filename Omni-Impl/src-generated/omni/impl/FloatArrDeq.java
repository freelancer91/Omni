package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
public class FloatArrDeq extends FloatUntetheredArrSeq<Float> implements OmniDeque.OfFloat
{
  FloatArrDeq(int head,float[] arr,int tail){
    super(head,arr,tail);
  }
  FloatArrDeq(){
    super();
  }
  @Override public boolean add(float val){
    addLast(val);
    return true;
  }
  @Override public boolean offer(float val){
    addLast(val);
    return true;
  }
  @Override public boolean offerFirst(float val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(float val){
    addLast(val);
    return true;
  }
  @Override public boolean add(boolean val){
    addLast((float)TypeUtil.castToFloat(val));
    return true;
  }
  @Override public boolean add(short val){
    addLast((float)(val));
    return true;
  }
  @Override public boolean add(char val){
    addLast((float)(val));
    return true;
  }
  @Override public boolean add(int val){
    addLast((float)(val));
    return true;
  }
  @Override public boolean add(long val){
    addLast((float)(val));
    return true;
  }
  @Override public boolean add(Float val){
    addLast((float)val);
    return true;
  }
  @Override public void addFirst(Float val){
    push((float)val);
  }
  @Override public void addLast(Float val){
    addLast((float)val);
  }
  @Override public boolean offer(Float val){
    addLast((float)val);
    return true;
  }
  @Override public boolean offerFirst(Float val){
    push((float)val);
    return true;
  }
  @Override public boolean offerLast(Float val){
    addLast((float)val);
    return true;
  }
  @Override public void push(Float val){
    push((float)val);
  }
  @Override public float floatElement(){
    return (float)arr[head];
  }
  @Override public float getLastFloat(){
    return (float)arr[tail];
  }
  @Override public Float element(){
    return (float)arr[head];
  }
  @Override public Float getFirst(){
    return (float)arr[head];
  }
  @Override public Float getLast(){
    return (float)arr[tail];
  }
  @Override public float peekFloat(){
    if(this.tail!=-1){
      return (float)arr[head];
    }
    return Float.NaN;
  }
  @Override public float peekLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (float)arr[tail];
    }
    return Float.NaN;
  }
  @Override public Float peek(){
    if(this.tail!=-1){
      return (Float)(arr[head]);
    }
    return null;
  }
  @Override public Float peekFirst(){
    if(this.tail!=-1){
      return (Float)(arr[head]);
    }
    return null;
  }
  @Override public Float peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Float)(arr[tail]);
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
  @Override public float pollFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (float)super.uncheckedRemoveFirst(tail);
    }
    return Float.NaN;
  }
  @Override public float pollLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (float)super.uncheckedRemoveLast(tail);
    }
    return Float.NaN;
  }
  @Override public Float poll(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Float)super.uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Float pollFirst(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Float)super.uncheckedRemoveFirst(tail);
    }
    return null;
  }
  @Override public Float pollLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Float)super.uncheckedRemoveLast(tail);
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
  @Override public float popFloat(){
    return (float)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public float removeLastFloat(){
    return (float)super.uncheckedRemoveLast(this.tail);
  }
  @Override public Float pop(){
    return (Float)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Float remove(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Float removeFirst(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Float removeLast(){
    return super.uncheckedRemoveLast(this.tail);
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
        final float[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new float[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public Float[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      Float[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new Float[size],0,size);
      }else{
        final float[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new Float[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
        final float[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new double[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean contains(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean contains(short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean contains(int val){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(val) && super.uncheckedContainsMatch(tail,TypeUtil.floatEquals(val));   
  }
  @Override public boolean contains(long val){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(val) && super.uncheckedContainsMatch(tail,TypeUtil.floatEquals(val));   
  }
  @Override public boolean contains(float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean contains(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final FloatPredicate queryParam;
      final float f;
      if((f=(float)val)==val){
        queryParam=TypeUtil.floatEqualsBits(Float.floatToRawIntBits(f));
      }else if(f!=f){
        queryParam=Float::isNaN;
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final FloatPredicate queryParam;
      if(val instanceof Float){
        queryParam=TypeUtil.floatEquals((float)val);
      }else if(val instanceof Integer){
        final int i;
        if(!TypeUtil.checkCastToFloat(i=(int)val)){
          return false;
        }
        queryParam=TypeUtil.floatEquals(i);
      }else if(val instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToFloat(l=(long)val)){
          return false;
        }
        queryParam=TypeUtil.floatEquals(l);
      }else if(val instanceof Double){
        final double d;
        final float f;
        if((d=(double)val)!=(f=(float)d)){
          if(f==f){
            return false;
          }
          queryParam=Float::isNaN;
        }else{
          queryParam=TypeUtil.floatEqualsBits(Float.floatToRawIntBits(f));
        }
      }else if(val instanceof Byte || val instanceof Short){
        queryParam=TypeUtil.floatEquals(((Number)val).shortValue());
      }else if(val instanceof Character){
        queryParam=TypeUtil.floatEquals((char)val);
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.floatEquals((boolean)val);
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
    ){return super.uncheckedSearch(tail,TypeUtil.floatEquals(val));    
    }
    return -1;
  }
  @Override public int search(char val){
    final int tail;
    if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,TypeUtil.floatEquals(val));    
    }
    return -1;
  }
  @Override public int search(short val){
    final int tail;
    if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,TypeUtil.floatEquals(val));    
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
    if((tail=this.tail)!=-1
    &&TypeUtil.checkCastToFloat(val)){ return super.uncheckedSearch(tail,TypeUtil.floatEquals(val));   
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
    if((tail=this.tail)!=-1
    &&TypeUtil.checkCastToFloat(val)){ return super.uncheckedSearch(tail,TypeUtil.floatEquals(val));   
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
    if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,TypeUtil.floatEquals(val));    
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
    if((tail=this.tail)!=-1
    ){
      final FloatPredicate queryParam;
      final float f;
      if((f=(float)val)==val){
        queryParam=TypeUtil.floatEqualsBits(Float.floatToRawIntBits(f));
      }else if(f!=f){
        queryParam=Float::isNaN;
      }else{
        return -1;
      }
      return super.uncheckedSearch(tail,queryParam); 
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
    if((tail=this.tail)!=-1
    ){
      final FloatPredicate queryParam;
      if(val instanceof Float){
        queryParam=TypeUtil.floatEquals((float)val);
      }else if(val instanceof Integer){
        final int i;
        if(!TypeUtil.checkCastToFloat(i=(int)val)){
          return -1;
        }
        queryParam=TypeUtil.floatEquals(i);
      }else if(val instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToFloat(l=(long)val)){
          return -1;
        }
        queryParam=TypeUtil.floatEquals(l);
      }else if(val instanceof Double){
        final double d;
        final float f;
        if((d=(double)val)!=(f=(float)d)){
          if(f==f){
            return -1;
          }
          queryParam=Float::isNaN;
        }else{
          queryParam=TypeUtil.floatEqualsBits(Float.floatToRawIntBits(f));
        }
      }else if(val instanceof Byte || val instanceof Short){
        queryParam=TypeUtil.floatEquals(((Number)val).shortValue());
      }else if(val instanceof Character){
        queryParam=TypeUtil.floatEquals((char)val);
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.floatEquals((boolean)val);
      }else{
        return -1;
      }
      return super.uncheckedSearch(tail,queryParam); 
    }
    return -1;
  }
  @Override public boolean removeVal(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean removeVal(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean removeVal(short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean removeVal(int val){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(val) && super.uncheckedRemoveFirstMatch(tail,TypeUtil.floatEquals(val));   
  }
  @Override public boolean removeVal(long val){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(val) && super.uncheckedRemoveFirstMatch(tail,TypeUtil.floatEquals(val));   
  }
  @Override public boolean removeVal(float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean removeVal(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final FloatPredicate queryParam;
      final float f;
      if((f=(float)val)==val){
        queryParam=TypeUtil.floatEqualsBits(Float.floatToRawIntBits(f));
      }else if(f!=f){
        queryParam=Float::isNaN;
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final FloatPredicate queryParam;
      if(val instanceof Float){
        queryParam=TypeUtil.floatEquals((float)val);
      }else if(val instanceof Integer){
        final int i;
        if(!TypeUtil.checkCastToFloat(i=(int)val)){
          return false;
        }
        queryParam=TypeUtil.floatEquals(i);
      }else if(val instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToFloat(l=(long)val)){
          return false;
        }
        queryParam=TypeUtil.floatEquals(l);
      }else if(val instanceof Double){
        final double d;
        final float f;
        if((d=(double)val)!=(f=(float)d)){
          if(f==f){
            return false;
          }
          queryParam=Float::isNaN;
        }else{
          queryParam=TypeUtil.floatEqualsBits(Float.floatToRawIntBits(f));
        }
      }else if(val instanceof Byte || val instanceof Short){
        queryParam=TypeUtil.floatEquals(((Number)val).shortValue());
      }else if(val instanceof Character){
        queryParam=TypeUtil.floatEquals((char)val);
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.floatEquals((boolean)val);
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
      final FloatPredicate queryParam;
      if(val instanceof Float){
        queryParam=TypeUtil.floatEquals((float)val);
      }else if(val instanceof Integer){
        final int i;
        if(!TypeUtil.checkCastToFloat(i=(int)val)){
          return false;
        }
        queryParam=TypeUtil.floatEquals(i);
      }else if(val instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToFloat(l=(long)val)){
          return false;
        }
        queryParam=TypeUtil.floatEquals(l);
      }else if(val instanceof Double){
        final double d;
        final float f;
        if((d=(double)val)!=(f=(float)d)){
          if(f==f){
            return false;
          }
          queryParam=Float::isNaN;
        }else{
          queryParam=TypeUtil.floatEqualsBits(Float.floatToRawIntBits(f));
        }
      }else if(val instanceof Byte || val instanceof Short){
        queryParam=TypeUtil.floatEquals(((Number)val).shortValue());
      }else if(val instanceof Character){
        queryParam=TypeUtil.floatEquals((char)val);
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.floatEquals((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean removeLastOccurrence(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean removeLastOccurrence(short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(val) && super.uncheckedRemoveLastMatch(tail,TypeUtil.floatEquals(val));   
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(val) && super.uncheckedRemoveLastMatch(tail,TypeUtil.floatEquals(val));   
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.floatEquals(val));    
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final FloatPredicate queryParam;
      final float f;
      if((f=(float)val)==val){
        queryParam=TypeUtil.floatEqualsBits(Float.floatToRawIntBits(f));
      }else if(f!=f){
        queryParam=Float::isNaN;
      }else{
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final FloatPredicate queryParam;
      if(val instanceof Float){
        queryParam=TypeUtil.floatEquals((float)val);
      }else if(val instanceof Integer){
        final int i;
        if(!TypeUtil.checkCastToFloat(i=(int)val)){
          return false;
        }
        queryParam=TypeUtil.floatEquals(i);
      }else if(val instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToFloat(l=(long)val)){
          return false;
        }
        queryParam=TypeUtil.floatEquals(l);
      }else if(val instanceof Double){
        final double d;
        final float f;
        if((d=(double)val)!=(f=(float)d)){
          if(f==f){
            return false;
          }
          queryParam=Float::isNaN;
        }else{
          queryParam=TypeUtil.floatEqualsBits(Float.floatToRawIntBits(f));
        }
      }else if(val instanceof Byte || val instanceof Short){
        queryParam=TypeUtil.floatEquals(((Number)val).shortValue());
      }else if(val instanceof Character){
        queryParam=TypeUtil.floatEquals((char)val);
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.floatEquals((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public OmniIterator.OfFloat iterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-(tail=this.head))<=0){
        size+=arr.length;
      }
      return new AscendingUntetheredArrSeqItr<Float>(this,tail,size);
    }
    return new AscendingUntetheredArrSeqItr<Float>(this,-1,0);
  }
  @Override public OmniIterator.OfFloat descendingIterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-this.head)<=0){
        size+=arr.length;
      }
      return new DescendingUntetheredArrSeqItr<Float>(this,tail,size);
    }
    return new DescendingUntetheredArrSeqItr<Float>(this,-1,0);
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      float[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new float[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final float[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new float[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new FloatArrDeq(head,copy,tail);
    }
    return new FloatArrDeq();
  }
  @Override public boolean removeIf(FloatPredicate filter){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveIf(tail,filter);
  }
  @Override public boolean removeIf(Predicate<? super Float> filter){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveIf(tail,filter::test);
  }
  @Override public void forEach(FloatConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      super.ascendingForEach(tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
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
        final float[] arr;
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
        final float[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=OmniArray.uncheckedArrResize(size+=arr.length,dst),0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
}
