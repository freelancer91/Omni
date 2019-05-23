package omni.impl.seq;
import omni.util.HashUtil;
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
import java.util.function.DoublePredicate;
import java.util.function.DoubleConsumer;
import omni.impl.AbstractDoubleItr;
import java.util.ConcurrentModificationException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.util.RandomAccess;
public class DoubleArrDeq implements OmniDeque.OfDouble,Externalizable,Cloneable,RandomAccess{
  private static final long serialVersionUID=1L;
  transient double[] arr;
  transient int head;
  transient int tail;
  public DoubleArrDeq(){
    super();
    this.arr=OmniArray.OfDouble.DEFAULT_ARR;
    this.tail=-1;
  }
  public DoubleArrDeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new double[initialCapacity];
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfDouble.DEFAULT_ARR;
    case 0:
    }
    this.tail=-1;
  }
  DoubleArrDeq(int head,double[] arr,int tail){
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
  @Override public void forEach(DoubleConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action);
    }
  }
  @Override public boolean removeIf(DoublePredicate filter){
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
  void uncheckedForEach(final int tail,DoubleConsumer action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      OmniArray.OfDouble.ascendingForEach(arr,head,arr.length-1,action);
      head=0;
    }
    OmniArray.OfDouble.ascendingForEach(arr,head,tail,action);
  }
  @Override public boolean add(double val){
    addLast(val);
    return true;
  }
  @Override public void addFirst(double val){
    push(val);
  }
  @Override public boolean offerFirst(double val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(double val){
    addLast(val);
    return true;
  }
  @Override public double doubleElement(){
    return (double)arr[head];
  }
  @Override public double getLastDouble(){
    return (double)arr[tail];
  }
  @Override public boolean offer(double val){
    addLast(val);
    return true;
  }
  @Override public boolean contains(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val){
          return uncheckedcontainsBits(tail,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedcontains0(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          {
            return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
          }
        }else{
          return uncheckedcontains0(tail);
        }
      }
    }
    return false;
  }
  @Override public boolean contains(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          if(TypeUtil.checkCastToDouble(val)){
            return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
          }
        }else{
          return uncheckedcontains0(tail);
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
        if(val==val){
          return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontainsNaN(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val==val){
          return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontainsNaN(tail);
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
          if(val instanceof Double){
            final double d;
            if((d=(double)val)==d){
               return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(d));
            }
            return uncheckedcontainsNaN(tail);
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)==f){
               return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(f));
            }
            return uncheckedcontainsNaN(tail);
          }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
            final int i;
            if((i=((Number)val).intValue())!=0){
              return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(i));
            }
            return uncheckedcontains0(tail);
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=0){
              if(!TypeUtil.checkCastToDouble(l)){
                break returnFalse;
              }
              return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(l));
            }
            return uncheckedcontains0(tail);
          }else if(val instanceof Character){
            final int i;
            if((i=(char)val)!=0){
              return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(i));
            }
            return uncheckedcontains0(tail);
          }else if(val instanceof Boolean){
            if((boolean)val){
              return uncheckedcontainsBits(tail,TypeUtil.DBL_TRUE_BITS);
            }
            return uncheckedcontains0(tail);
          }else{
            break returnFalse;
          }
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
        if(val!=0){
          return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontains0(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(char val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontains0(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          return uncheckedcontainsBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontains0(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val){
          return uncheckedremoveValBits(tail,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedremoveVal0(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          {
            return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
          }
        }else{
          return uncheckedremoveVal0(tail);
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          if(TypeUtil.checkCastToDouble(val)){
            return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
          }
        }else{
          return uncheckedremoveVal0(tail);
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
        if(val==val){
          return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveValNaN(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val==val){
          return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveValNaN(tail);
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
          if(val instanceof Double){
            final double d;
            if((d=(double)val)==d){
               return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(d));
            }
            return uncheckedremoveValNaN(tail);
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)==f){
               return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(f));
            }
            return uncheckedremoveValNaN(tail);
          }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
            final int i;
            if((i=((Number)val).intValue())!=0){
              return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(i));
            }
            return uncheckedremoveVal0(tail);
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=0){
              if(!TypeUtil.checkCastToDouble(l)){
                break returnFalse;
              }
              return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(l));
            }
            return uncheckedremoveVal0(tail);
          }else if(val instanceof Character){
            final int i;
            if((i=(char)val)!=0){
              return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(i));
            }
            return uncheckedremoveVal0(tail);
          }else if(val instanceof Boolean){
            if((boolean)val){
              return uncheckedremoveValBits(tail,TypeUtil.DBL_TRUE_BITS);
            }
            return uncheckedremoveVal0(tail);
          }else{
            break returnFalse;
          }
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
        if(val!=0){
          return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveVal0(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(char val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveVal0(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          return uncheckedremoveValBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveVal0(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val){
          return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedremoveLastOccurrence0(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          {
            return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
          }
        }else{
          return uncheckedremoveLastOccurrence0(tail);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          if(TypeUtil.checkCastToDouble(val)){
            return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
          }
        }else{
          return uncheckedremoveLastOccurrence0(tail);
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
        if(val==val){
          return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveLastOccurrenceNaN(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val==val){
          return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveLastOccurrenceNaN(tail);
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
          if(val instanceof Double){
            final double d;
            if((d=(double)val)==d){
               return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(d));
            }
            return uncheckedremoveLastOccurrenceNaN(tail);
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)==f){
               return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(f));
            }
            return uncheckedremoveLastOccurrenceNaN(tail);
          }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
            final int i;
            if((i=((Number)val).intValue())!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(i));
            }
            return uncheckedremoveLastOccurrence0(tail);
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=0){
              if(!TypeUtil.checkCastToDouble(l)){
                break returnFalse;
              }
              return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(l));
            }
            return uncheckedremoveLastOccurrence0(tail);
          }else if(val instanceof Character){
            final int i;
            if((i=(char)val)!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(i));
            }
            return uncheckedremoveLastOccurrence0(tail);
          }else if(val instanceof Boolean){
            if((boolean)val){
              return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.DBL_TRUE_BITS);
            }
            return uncheckedremoveLastOccurrence0(tail);
          }else{
            break returnFalse;
          }
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
        if(val){
          return uncheckedsearchBits(tail,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedsearch0(tail);
      }
    }
    return -1;
  }
  @Override public int search(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          {
            return uncheckedsearchBits(tail,Double.doubleToRawLongBits(val));
          }
        }else{
          return uncheckedsearch0(tail);
        }
      }
    }
    return -1;
  }
  @Override public int search(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          if(TypeUtil.checkCastToDouble(val)){
            return uncheckedsearchBits(tail,Double.doubleToRawLongBits(val));
          }
        }else{
          return uncheckedsearch0(tail);
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
        if(val==val){
          return uncheckedsearchBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedsearchNaN(tail);
      }
    }
    return -1;
  }
  @Override public int search(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val==val){
          return uncheckedsearchBits(tail,Double.doubleToRawLongBits(val));
        }
        return uncheckedsearchNaN(tail);
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
          if(val instanceof Double){
            final double d;
            if((d=(double)val)==d){
               return uncheckedsearchBits(tail,Double.doubleToRawLongBits(d));
            }
            return uncheckedsearchNaN(tail);
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)==f){
               return uncheckedsearchBits(tail,Double.doubleToRawLongBits(f));
            }
            return uncheckedsearchNaN(tail);
          }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
            final int i;
            if((i=((Number)val).intValue())!=0){
              return uncheckedsearchBits(tail,Double.doubleToRawLongBits(i));
            }
            return uncheckedsearch0(tail);
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=0){
              if(!TypeUtil.checkCastToDouble(l)){
                break returnFalse;
              }
              return uncheckedsearchBits(tail,Double.doubleToRawLongBits(l));
            }
            return uncheckedsearch0(tail);
          }else if(val instanceof Character){
            final int i;
            if((i=(char)val)!=0){
              return uncheckedsearchBits(tail,Double.doubleToRawLongBits(i));
            }
            return uncheckedsearch0(tail);
          }else if(val instanceof Boolean){
            if((boolean)val){
              return uncheckedsearchBits(tail,TypeUtil.DBL_TRUE_BITS);
            }
            return uncheckedsearch0(tail);
          }else{
            break returnFalse;
          }
        }
      }
    }
    return -1;
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
  @Override public Double[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final Double[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new Double[size+=arr.length],size-=tail,tail);
      }else{
        dst=new Double[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
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
  @Override public Double peek(){
    if(tail!=-1){
      return (Double)(arr[head]);
    }
    return null;
  }
  @Override public Double peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Double)(arr[tail]);
    }
    return null;
  }
  @Override public double pollDouble(){
    int tail;
    if((tail=this.tail)!=-1){
      final double[] arr;
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
      final double[] arr;
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
  @Override public Double poll(){
    int tail;
    if((tail=this.tail)!=-1){
      final double[] arr;
      int head;
      var ret=(Double)((arr=this.arr)[head=this.head]);
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
  @Override public Double pollLast(){
    int tail;
    if((tail=this.tail)!=-1){
      final double[] arr;
      var ret=(Double)((arr=this.arr)[tail]);
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
    addLast((double)TypeUtil.castToDouble(val));
    return true;
  }
  @Override public boolean add(int val){
    addLast((double)val);
    return true;
  }
  @Override public boolean add(char val){
    addLast((double)val);
    return true;
  }
  @Override public boolean add(short val){
    addLast((double)val);
    return true;
  }
  @Override public boolean add(long val){
    addLast((double)val);
    return true;
  }
  @Override public boolean add(float val){
    addLast((double)val);
    return true;
  }
  @Override public Double getFirst(){
    return doubleElement();
  }
  @Override public Double peekFirst(){
    return peek();
  }
  @Override public Double pollFirst(){
    return poll();
  }
  @Override public Double removeFirst(){
    return popDouble();
  }
  @Override public Double remove(){
    return popDouble();
  }
  @Override public boolean removeFirstOccurrence(Object val){
    return remove(val);
  }
  @Override public Double pop(){
    return popDouble();
  }
  @Override public Double removeLast(){
    return removeLastDouble();
  }
  @Override public void push(Double val){
    push((double)val);
  }
  @Override public boolean offer(Double val){
    addLast((double)val);
    return true;
  }
  @Override public Double element(){
    return doubleElement();
  }
  @Override public Double getLast(){
    return getLastDouble();
  }
  @Override public void addFirst(Double val){
    push((double)val);
  }
  @Override public void addLast(Double val){
    addLast((double)val);
  }
  @Override public boolean add(Double val){
    addLast((double)val);
    return true;
  }
  @Override public boolean offerFirst(Double val){
    push((double)val);
    return true;
  }
  @Override public boolean offerLast(Double val){
    addLast((double)val);
    return true;
  }
  boolean uncheckedremoveValBits(int tail
  ,long bits
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(index=head,bound=arr.length-1;;++index){
          if(bits==Double.doubleToRawLongBits(arr[index])){
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
              if(bits==Double.doubleToRawLongBits(arr[index])){
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
          if(bits==Double.doubleToRawLongBits(arr[index])){
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
  boolean uncheckedremoveVal0(int tail
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(index=head,bound=arr.length-1;;++index){
          if(0==(arr[index])){
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
              if(0==(arr[index])){
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
          if(0==(arr[index])){
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
  boolean uncheckedremoveValNaN(int tail
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(index=head,bound=arr.length-1;;++index){
          if(Double.isNaN(arr[index])){
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
              if(Double.isNaN(arr[index])){
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
          if(Double.isNaN(arr[index])){
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
  boolean uncheckedremoveLastOccurrenceBits(int tail
  ,long bits
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(bound=arr.length-1,index=tail;;--index){
          if(bits==Double.doubleToRawLongBits(arr[index])){
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
              if(bits==Double.doubleToRawLongBits(arr[index])){
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
          if(bits==Double.doubleToRawLongBits(arr[index])){
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
  boolean uncheckedremoveLastOccurrence0(int tail
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(bound=arr.length-1,index=tail;;--index){
          if(0==(arr[index])){
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
              if(0==(arr[index])){
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
          if(0==(arr[index])){
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
  boolean uncheckedremoveLastOccurrenceNaN(int tail
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(bound=arr.length-1,index=tail;;--index){
          if(Double.isNaN(arr[index])){
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
              if(Double.isNaN(arr[index])){
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
          if(Double.isNaN(arr[index])){
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
  private boolean uncheckedcontainsBits(int tail
  ,long bits
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfDouble.uncheckedcontainsBits(arr,0,tail,bits) || OmniArray.OfDouble.uncheckedcontainsBits(arr,head,arr.length-1,bits);
    }
    return OmniArray.OfDouble.uncheckedcontainsBits(arr,head,tail,bits);
  }
  private boolean uncheckedcontains0(int tail
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfDouble.uncheckedcontains0(arr,0,tail ) || OmniArray.OfDouble.uncheckedcontains0(arr,head,arr.length-1 );
    }
    return OmniArray.OfDouble.uncheckedcontains0(arr,head,tail );
  }
  private boolean uncheckedcontainsNaN(int tail
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfDouble.uncheckedcontainsNaN(arr,0,tail ) || OmniArray.OfDouble.uncheckedcontainsNaN(arr,head,arr.length-1 );
    }
    return OmniArray.OfDouble.uncheckedcontainsNaN(arr,head,tail );
  }
  private int uncheckedsearchBits(int tail
  ,long bits
  ){
    final var arr=this.arr;
    int head,prefix;
    if(tail<(head=this.head)){
      int bound;
      for(prefix=head,bound=arr.length;;)
      {
        if(bits==Double.doubleToRawLongBits(arr[prefix]))
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
      if(bits==Double.doubleToRawLongBits(arr[head]))
      {
        return prefix+head+1;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  private int uncheckedsearch0(int tail
  ){
    final var arr=this.arr;
    int head,prefix;
    if(tail<(head=this.head)){
      int bound;
      for(prefix=head,bound=arr.length;;)
      {
        if(0==(arr[prefix]))
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
      if(0==(arr[head]))
      {
        return prefix+head+1;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  private int uncheckedsearchNaN(int tail
  ){
    final var arr=this.arr;
    int head,prefix;
    if(tail<(head=this.head)){
      int bound;
      for(prefix=head,bound=arr.length;;)
      {
        if(Double.isNaN(arr[prefix]))
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
      if(Double.isNaN(arr[head]))
      {
        return prefix+head+1;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  @Override public void forEach(Consumer<? super Double> action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override public boolean removeIf(Predicate<? super Double> filter){
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
  @Override public double popDouble(){
    final double[] arr;
    int head;
    var ret=(double)((arr=this.arr)[head=this.head]);
    if(head==this.tail){
      this.tail=-1;
      return ret;
    }else if(++head==arr.length){
      head=0;
    }
    this.head=head;
    return ret;
  }
  @Override public double removeLastDouble(){
    final double[] arr;
    int tail;
    var ret=(double)((arr=this.arr)[tail=this.tail]);
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
      final double[] dst;
      int size,head;
      DoubleArrDeq clone;
      if((size=(++tail)-(head=this.head))<=0){
        clone=new DoubleArrDeq(0,dst=new double[size+=arr.length],size-1);
        ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
      }else{
        clone=new DoubleArrDeq(0,dst=new double[size],size-1);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return clone;
    }
    return new DoubleArrDeq();
  }
  private String uncheckedToString(int tail){
    final var arr=this.arr;
    final var builder=new StringBuilder("[");
    int head;
    if(tail<(head=this.head)){
      for(int bound=arr.length;;){
        builder.append(arr[head]).append(',').append(' ');
        if(++head==bound){
          head=0;
          break;
        }
      }
    }
    for(;;builder.append(',').append(' '),++head){
      builder.append(arr[head]);
      if(head==tail){
        return builder.append(']').toString();
      }
    }
  }
  private int uncheckedHashCode(int tail){
    final double[] arr;
    int head;
    int hash=31+HashUtil.hashDouble((arr=this.arr)[head=this.head]);
    if(tail<head){
      for(final int bound=arr.length;;){  
        if(++head==bound){
          hash=hash*31+HashUtil.hashDouble(arr[head=0]);
          break;
        }
        hash=(hash*31)+HashUtil.hashDouble(arr[head]);
      }
    }
    for(;head!=tail;hash=(hash*31)+HashUtil.hashDouble(arr[++head])){}
    return hash;
  }
  @Override public void push(double val){
    double[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfDouble.DEFAULT_ARR){
        this.head=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.tail=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.arr=arr=new double[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
            final double[] newArr;
            int newCap,size;
            this.tail=(newCap=OmniArray.growBy50Pct(head+(size=arr.length)))-1;
            ArrCopy.uncheckedCopy(arr,0,newArr=new double[newCap],newCap-=(++tail),tail);
            ArrCopy.uncheckedCopy(arr,head+1,newArr,head=newCap-(size-=tail),size);
            this.arr=arr=newArr;
            --head;
          }else if(head==-1 && tail==(head=arr.length-1)){
            int newCap;
            this.tail=(newCap=OmniArray.growBy50Pct(++tail))-1;
            ArrCopy.uncheckedCopy(arr,0,arr=new double[newCap],head=newCap-tail,tail);
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
  private void initFromNullArr(double val){
    this.head=0;
    this.tail=0;
    this.arr=new double[]{val};
  }
  @Override public void addLast(double val){
    double[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfDouble.DEFAULT_ARR){
        this.head=0;
        this.tail=0;
        this.arr=arr=new double[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
            final double[] newArr;
            (newArr=new double[OmniArray.growBy50Pct(tail=arr.length)])[tail]=val;
            this.tail=tail;
            ArrCopy.uncheckedCopy(arr,head,newArr,0,tail-=head);
            ArrCopy.uncheckedCopy(arr,0,newArr,tail,head);
            this.arr=newArr;
          }else{
            if(tail==arr.length){
              if(head==0){
                ArrCopy.uncheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(tail)],0,tail);
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
    extends AbstractDoubleItr
  {
    transient int cursor;
    AbstractDeqItr(int cursor){
      this.cursor=cursor;
    }
    @Override public boolean hasNext(){
      return this.cursor!=-1;
    }
    abstract void uncheckedForEachRemaining(int cursor,DoubleConsumer action);
    @Override public void forEachRemaining(DoubleConsumer action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Double> action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action::accept);
      }
    }
  }
  private static int pullUp(double[] arr,int head,int headDist){
    ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
    return head;
  }
  private static int fragmentedPullUp(double[] arr,int head,int headDist){
    if(headDist==0){
      return 0;
    }else{
      ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
      return head;
    }
  }
  private static int fragmentedPullDown(double[] arr,int arrBound,int tail){
    if(tail==0){
      return arrBound;
    }
    ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
    return tail-1;
  }
  private static class AscendingItr extends AbstractDeqItr
  {
    transient final DoubleArrDeq root;
    private AscendingItr(DoubleArrDeq root){
      super(root.tail!=-1?root.head:-1);
      this.root=root;
    }
    private AscendingItr(DoubleArrDeq root,int cursor){
      super(cursor);
      this.root=root;
    }
    @Override public double nextDouble(){
      final double[] arr;
      int cursor;
      final DoubleArrDeq root;
      final var ret=(double)(arr=(root=this.root).arr)[cursor=this.cursor];
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
      final DoubleArrDeq root;
      final double[] arr;
      if((tail=(root=this.root).tail)<(headDist=(arrBound=(arr=root.arr).length-1)-(head=root.head))){
        arr[arrBound]=arr[0];
        root.tail=fragmentedPullDown(arr,arrBound,tail);
        this.cursor=arrBound;
      }else{
        root.head=fragmentedPullUp(arr,head,headDist);
      }
    }
    private void fragmentedAscendingRemove(int head,int lastRet,int tail,DoubleArrDeq root){
      double[] arr;
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
    private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,DoubleArrDeq root){
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
          final DoubleArrDeq root;
          if((tail=(root=this.root).tail)<(head=root.head)){
            fragmentedAscendingRemove(head,cursor-1,tail,root);
          }else{
            nonfragmentedAscendingRemove(head,cursor-1,tail,root);
          }
      }
    }
    @Override void uncheckedForEachRemaining(int cursor,DoubleConsumer action){
      final DoubleArrDeq root;
      int tail;
      final var arr=(root=this.root).arr;
      if(cursor>(tail=root.tail)){
        OmniArray.OfDouble.ascendingForEach(arr,cursor,arr.length-1,action);
        cursor=0;
      }
      OmniArray.OfDouble.ascendingForEach(arr,cursor,tail,action);
      this.cursor=-1;
    }
  }
  private static class DescendingItr extends AscendingItr{
    private DescendingItr(DoubleArrDeq root){
      super(root,root.tail);
    }
    @Override void uncheckedForEachRemaining(int cursor,DoubleConsumer action){
      final DoubleArrDeq root;
      final int head;
      final var arr=(root=this.root).arr;
      if(cursor<(head=root.head)){
        OmniArray.OfDouble.descendingForEach(arr,0,cursor,action);
        cursor=arr.length-1;
      }
      OmniArray.OfDouble.descendingForEach(arr,head,cursor,action);
      this.cursor=-1;
    }
    @Override public double nextDouble(){
      int cursor;
      final DoubleArrDeq root;
      final var arr=(root=this.root).arr;
      this.cursor=(cursor=this.cursor)==root.head?-1:cursor==0?arr.length-1:cursor-1;
      return (double)arr[cursor];
    }
    private void fragmentedDescendingRemove(int head,int cursor,int tail,DoubleArrDeq root){
      double[] arr;
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
    private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,DoubleArrDeq root){
      int tailDist,headDist;
      if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
        ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
        root.tail=tail-1;
      }else{
        double[] arr;
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
        DoubleArrDeq root;
        int head,tail;
        if((tail=(root=this.root).tail)<(head=root.head)){
          fragmentedDescendingRemove(head,cursor,tail,root);
        }else{
          nonfragmentedDescendingRemove(head,cursor+1,tail,root);
        }
      }
    }
  } 
  @Override public OmniIterator.OfDouble iterator(){
    return new AscendingItr(this);
  }
  @Override public OmniIterator.OfDouble descendingIterator(){
    return new DescendingItr(this);
  }
  boolean fragmentedRemoveIf(int head,int tail,DoublePredicate filter){
    double[] arr;
    if(filter.test((double)(arr=this.arr)[head]))
    {
      if(filter.test((double)arr[tail]))
      {
        fragmentedCollapseHeadAndTail(arr,head,tail,filter);
      }
      else
      {
        fragmentedCollapsehead(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((double)arr[tail]))
    {
      fragmentedCollapsetail(arr,head,tail,filter);
      return true;
    }
    return fragmentedCollapseBody(arr,head,tail,filter);
  }
  private static  int pullDown(double[] arr,int dstOffset,int srcBound,DoublePredicate filter){
    for(int srcOffset=dstOffset+1;srcOffset!=srcBound;++srcOffset)
    {
      final double v;
      if(!filter.test((double)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    return dstOffset;
  }
  private static  int pullUp(double[] arr,int dstOffset,int srcBound,DoublePredicate filter){
    for(int srcOffset=dstOffset-1;srcOffset!=srcBound;--srcOffset)
    {
      final double v;
      if(!filter.test((double)(v=arr[srcOffset])))
      {
        arr[dstOffset--]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    return dstOffset;
  }
  private void fragmentedCollapseBodyHelper(double[] arr,int head,int tail,DoublePredicate filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((double)arr[srcOffset]))
      {
        tail=pullDown(arr,srcOffset,tail,filter);
        break;
      }
    }
    this.tail=tail;
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset)
    {
      if(filter.test((double)arr[srcOffset]))
      {
        head=pullUp(arr,srcOffset,head,filter);
        break;
      }
    }
    this.head=head;
  }
  private void collapseBodyHelper(double[] ar,int head,int tail,DoublePredicate filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((double)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((double)arr[midPoint])){
            tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        this.tail=tail;
        return;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((double)arr[midPoint])){
        tail=pullDown(arr,midPoint,tail,filter);
        break;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  private void fragmentedCollapseHeadAndTail(double[] arr,int head,int tail,DoublePredicate filter){
    outer:for(;;){
      do{
        if(tail==0){
          for(tail=arr.length-1;tail!=head;--tail){
            if(!filter.test((double)arr[tail])){
              break outer;
            }
          }  
          this.tail=-1;
          return;
        }
      }while(filter.test((double)arr[--tail]));
      for(int bound=arr.length;++head!=bound;){
        if(!filter.test((double)arr[head])){
          fragmentedCollapseBodyHelper(arr,head,tail,filter);
          return;
        }
      }
      head=-1;
      break;
    }
    while(++head!=tail){
      if(!filter.test((double)arr[head])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  private boolean fragmentedCollapseBody(double[] arr,int head,int tail,DoublePredicate filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset){
      if(filter.test((double)arr[srcOffset])){
        this.tail=pullDown(arr,srcOffset,tail,filter);
        for(srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
          if(filter.test((double)arr[srcOffset])){
            this.head=pullUp(arr,srcOffset,head,filter);
            break;
          }
        }
        return true;
      }
    }
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
      if(filter.test((double)arr[srcOffset]))
      {
        this.head=pullUp(arr,srcOffset,head,filter);
        return true;
      }
    }
    return false;
  }
  private void fragmentedCollapsehead(double[] arr,int head,int tail,DoublePredicate filter){
    for(int bound=arr.length;;)
    {
      if(++head==bound){
        for(head=0;;++head)
        {
          if(head==tail){
            this.head=head;
            return;
          }else if(!filter.test((double)arr[head])){
            collapseBodyHelper(arr,head,tail,filter);
            return;
          }
        }
      }
      if(!filter.test((double)arr[head]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  private void collapsehead(double[] arr,int head,int tail,DoublePredicate filter){
    do{
      if(++head==tail){
        this.head=head;
        return;
      }
    }while(filter.test((double)arr[head]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  private void fragmentedCollapsetail(double[] arr,int head,int tail,DoublePredicate filter){
    for(;;)
    {
      if(tail==0){
        for(tail=arr.length-1;;--tail)
        {
          if(tail==head){
            this.tail=head;
            return;
          }else if(!filter.test((double)arr[tail])){
            collapseBodyHelper(arr,head,tail,filter);
            return;
          }
        }
      }
      if(!filter.test((double)arr[--tail]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  private void collapsetail(double[] arr,int head,int tail,DoublePredicate filter){
    do{
      if(--tail==head){
        this.tail=tail;
        return;
      }
    }while(filter.test((double)arr[tail]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  private void collapseHeadAndTail(double[] arr,int head,int tail,DoublePredicate filter){
    do{
      if(++head==tail){
        this.tail=-1;
        return;
      }
    }while(filter.test((double)arr[head]));
    while(--tail!=head){
      if(!filter.test((double)arr[tail])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
    this.head=head;
    this.tail=head;
  }
  private boolean collapseBody(double[] arr,int head,int tail,DoublePredicate filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((double)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((double)arr[midPoint])){
            this.tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        return true;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((double)arr[midPoint])){
        this.tail=pullDown(arr,midPoint,tail,filter);
        return true;
      }
    }
    return false;
  }
  boolean nonfragmentedRemoveIf(int head,int tail,DoublePredicate filter){
    final double[] arr;
    if(filter.test((double)(arr=this.arr)[head])){
      if(head==tail){
        this.tail=-1;
      }else{
        if(filter.test((double)arr[tail])){
          collapseHeadAndTail(arr,head,tail,filter);
        }else{
          collapsehead(arr,head,tail,filter);
        }
      }
      return true;
    }else if(head!=tail){
      if(filter.test((double)arr[tail])){
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
      double[] arr;
      OmniArray.OfDouble.readArray(arr=new double[size],0,--size,input);
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
        double[] arr;
        output.writeInt(size+(size=(arr=this.arr).length));
        OmniArray.OfDouble.writeArray(arr,head,size-1,output);
        OmniArray.OfDouble.writeArray(arr,0,tail-1,output);
      }else{
        output.writeInt(size);
        OmniArray.OfDouble.writeArray(arr,head,tail-1,output);
      }
    }else{
      output.writeInt(0);
    }
  }
  @Override public boolean equals(Object obj){
      //TODO
      return false;
  }
  public static class Checked extends DoubleArrDeq{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(int initialCapacity){
      super(initialCapacity);
    }
    Checked(int head,double[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public boolean equals(Object obj){
      //TODO
      return false;
    }
    private void collapseheadHelper(double[] arr,int tail,DoublePredicate filter,int modCount){
      if(tail==0){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.head=0;
        this.tail=0;
      }else{
        for(int head=0;;)
        {
          if(!filter.test((double)arr[head])){
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
    private void fragmentedCollapseheadHelper(double[] arr,int tail,DoublePredicate filter,int modCount){
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
          }else if(!filter.test((double)arr[head])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }
        }
      }
    }
    private void fragmentedCollapsehead(double[] arr,int head,int tail,DoublePredicate filter,int modCount){
      int newhead;
      int bound;
      for(newhead=head+1,bound=arr.length-1;;++newhead){
        if(newhead>bound){
          fragmentedCollapseheadHelper(arr,tail,filter,modCount);
          break;
        }
        if(!filter.test((double)arr[newhead])){
          fragmentedCollapseBodyHelper(arr,newhead,tail,filter,modCount);
          break;
        }
      }
    }
    private void collapsehead(double[] arr,int head,int tail,DoublePredicate filter,int modCount){
      int srcOffset;
      for(srcOffset=head;++srcOffset!=tail;){
        if(!filter.test((double)arr[srcOffset])){
          collapseBodyHelper(arr,srcOffset,tail,filter,modCount);
          this.modCount=modCount+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.head=srcOffset;
    }
    private void collapsetailHelper(double[] arr,int head,DoublePredicate filter,int modCount){
      int tail;
      if((tail=arr.length-1)==head){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.tail=tail;
        this.head=head;
      }else{
        for(;;)
        {
          if(!filter.test((double)arr[tail])){
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
    private void fragmentedCollapsetailHelper(double[] arr,int head,DoublePredicate filter,int modCount){
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
          }else if(!filter.test((double)arr[tail])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }
        }
      }
    }
    private void fragmentedCollapsetail(double[] arr,int head,int tail,DoublePredicate filter,int modCount){
      int newtail;
      for(newtail=tail-1;;--newtail){
        if(newtail==-1){
          fragmentedCollapsetailHelper(arr,head,filter,modCount);
          break;
        }
        if(!filter.test((double)arr[newtail])){
          fragmentedCollapseBodyHelper(arr,head,newtail,filter,modCount);
          break;
        }
      }
    }
    private void collapsetail(double[] arr,int head,int tail,DoublePredicate filter,int modCount){
      int srcOffset;
      for(srcOffset=tail;--srcOffset!=head;){
        if(!filter.test((double)arr[srcOffset])){
          collapseBodyHelper(arr,head,srcOffset,filter,modCount);
          this.modCount=modCount+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.tail=srcOffset;
    }
    private void noElementsLeftToMarkFragmentedBefore(double[] arr,int head,int gapBegin){
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
    private void noElementsLeftToMarkFragmentedGap(double[] arr,int head,int gapBegin,int gapEnd){
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
    private void noElementsLeftToMarkFragmentedAfter(double[] arr,int head,int gapBegin,int gapEnd){
      assert gapBegin>=0;
      assert head>gapEnd;
      assert head<arr.length;
      assert gapEnd>gapBegin;
      arr[gapBegin]=arr[gapEnd];
      this.tail=++gapBegin;
      arr[gapBegin]=arr[gapEnd+1];
    }
    private void noElementsLeftToMark(double[] arr,int head,int gapBegin,int gapEnd){
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
      final double[] arr;
      (arr=this.arr)[gapBegin]=arr[gapEnd];
      arr[++gapBegin]=arr[tail];
      this.head=head;
      this.tail=gapBegin;
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
      abstract void arrSeqPullDown(double[] arr,int srcOffset,int dstOffset,int dstBound);
      abstract void pullSurvivorsDown(double[] arr,int dstOffset,int survivorIndex,int dstBound);
      abstract void pullSurvivorsUp(double[] arr,int srcOffset,int dstOffset,int survivorIndex,int dstBound);
      private int collapseTailHelper(double[] arr,int gapEnd,int tail){
        int biggestRunBegin=this.biggestRunBegin;
        int survivorsBeforeOrAfter;
        int newHead;
        if((survivorsBeforeOrAfter=this.survivorsBeforeBiggestRun)==0){
          newHead=biggestRunBegin-1;
        }else{
          pullSurvivorsUp(arr,biggestRunBegin-1,(newHead=biggestRunBegin),newHead-gapEnd-2,(newHead-=(survivorsBeforeOrAfter)));
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
      private void collapse(int head,int gapBegin,int gapEnd,int tail){
        assert head>=0;
        assert gapBegin>head;
        assert gapEnd>gapBegin;
        assert tail>gapEnd;
        assert tail<deq.arr.length;
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
      BigCollapseData(Checked deq,int srcOffset,int numLeft,DoublePredicate filter){
        super(deq,numLeft);
        assert srcOffset>=0;
        assert numLeft>64;
        var survivorSet=new long[((numLeft-1)>>6)+1];
        numLeft+=srcOffset;
        final var arr=deq.arr;
        for(int wordOffset=0,survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;;){
          long word=0L,marker=1L;
          do{
            if(filter.test((double)arr[srcOffset])){
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
      @Override void arrSeqPullDown(double[] arr,int srcOffset,int dstOffset,int dstBound){
        assert dstOffset>=0;
        assert srcOffset>dstOffset;
        assert dstOffset<dstBound;
        assert dstBound<arr.length;
        DoubleArrSeq.pullSurvivorsDown(arr,srcOffset,dstOffset,dstBound,survivorSet);
      }
      @Override void pullSurvivorsDown(double[] arr,int dstOffset,int survivorIndex,int dstBound){
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
      @Override void pullSurvivorsUp(double[] arr,int srcOffset,int dstOffset,int survivorIndex,int dstBound){
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
    }
    private static class SmallCollapseData extends CollapseData{
      @Override void pullSurvivorsUp(double[] arr,int srcOffset,int dstOffset,int survivorIndex,int dstBound){
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
      @Override void arrSeqPullDown(double[] arr,int srcOffset,int dstOffset,int dstBound){
        assert dstOffset>=0;
        assert srcOffset>dstOffset;
        assert dstOffset<dstBound;
        assert dstBound<arr.length;
        assert dstBound-dstOffset<64;
        DoubleArrSeq.pullSurvivorsDown(arr,srcOffset,dstOffset,dstBound,survivorWord);
      }
      @Override void pullSurvivorsDown(double[] arr,int dstOffset,int survivorIndex,int dstBound){
        assert dstOffset>=0;
        assert dstOffset<dstBound;
        assert dstBound<arr.length;
        DoubleArrSeq.pullSurvivorsDown(arr,dstOffset+1,dstOffset,dstBound,this.survivorWord>>>survivorIndex);
      }
      final long survivorWord;
      SmallCollapseData(Checked deq,int srcOffset,int numLeft,DoublePredicate filter){
        super(deq,numLeft);
        assert srcOffset>=0;
        assert numLeft<=64;
        numLeft+=srcOffset;
        final var arr=deq.arr;
        int survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;
        for(long word=0L,marker=1L;;marker<<=1){
          if(filter.test((double)arr[srcOffset])){
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
    private void collapseBodyHelper(double[] arr,int head,int tail,DoublePredicate filter,int modCount){
      for(int gapBegin=head+1;gapBegin!=tail;++gapBegin){
        if(filter.test((double)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=tail;++gapEnd){
            if(!filter.test((double)arr[gapEnd])){
              int numLeft,srcOffset;
              if((numLeft=tail-(srcOffset=gapEnd+1))==0){
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMark(arr,head,gapBegin,gapEnd);
              }else{
                CollapseData collapseData=numLeft>64?new BigCollapseData(this,srcOffset,numLeft,filter):new SmallCollapseData(this,srcOffset,numLeft,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                collapseData.collapse(head,gapBegin,gapEnd,tail);
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
    private boolean collapseBody(double[] arr,int head,int tail,DoublePredicate filter,int modCount){
      for(int gapBegin=head+1;gapBegin!=tail;++gapBegin){
        if(filter.test((double)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=tail;++gapEnd){
            if(!filter.test((double)arr[gapEnd])){
              int numLeft,srcOffset;
              if((numLeft=tail-(srcOffset=gapEnd+1))==0){
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMark(arr,head,gapBegin,gapEnd);
              }else{
                CollapseData collapseData=numLeft>64?new BigCollapseData(this,srcOffset,numLeft,filter):new SmallCollapseData(this,srcOffset,numLeft,filter);
                CheckedCollection.checkModCount(modCount,this.modCount);
                collapseData.collapse(head,gapBegin,gapEnd,tail);
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
    private void fragmentedCollapseBodyHelper(double[] arr,int head,int tail,DoublePredicate filter,int modCount){
      for(int gapBegin=head+1,arrBound=arr.length;gapBegin!=arrBound;++gapBegin){
        if(filter.test((double)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=arrBound;++gapEnd){
            if(!filter.test((double)arr[gapEnd])){
              int numLeft,srcOffset;
              if((numLeft=tail+arrBound-(srcOffset=gapEnd+1))==0){
                assert tail==0;
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMarkFragmentedBefore(arr,head,gapBegin);
              }else{
                CollapseData collapseData;
                if(srcOffset+numLeft==arrBound)
                {
                  assert tail==0;
                  if(numLeft>64)
                  {
                    //TODO
                    throw new UnsupportedOperationException();
                  }
                  else
                  {
                    throw new UnsupportedOperationException();
                  }
                }
                else
                {
                  if(numLeft>64)
                  {
                    //TODO
                    throw new UnsupportedOperationException();
                  }
                  else
                  {
                    //TODO
                    throw new UnsupportedOperationException();
                  }
                }
              }
              return;
            }
          }
          for(int gapEnd=0;gapEnd!=tail;++gapEnd){
            if(!filter.test((double)arr[gapEnd])){
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
        if(filter.test((double)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=tail;++gapEnd){
            if(!filter.test((double)arr[gapEnd])){
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
    private boolean fragmentedCollapseBody(double[] arr,int head,int tail,DoublePredicate filter,int modCount){
      for(int gapBegin=head+1,arrBound=arr.length;gapBegin!=arrBound;++gapBegin){
        if(filter.test((double)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=arrBound;++gapEnd){
            if(!filter.test((double)arr[gapEnd])){
              int numLeft,srcOffset;
              if((numLeft=tail+arrBound-(srcOffset=gapEnd+1))==0){
                assert tail==0;
                CheckedCollection.checkModCount(modCount,this.modCount);
                noElementsLeftToMarkFragmentedBefore(arr,head,gapBegin);
              }else{
                CollapseData collapseData;
                if(srcOffset+numLeft==arrBound)
                {
                  assert tail==0;
                  if(numLeft>64)
                  {
                    //TODO
                    throw new UnsupportedOperationException();
                  }
                  else
                  {
                    throw new UnsupportedOperationException();
                  }
                }
                else
                {
                  if(numLeft>64)
                  {
                    //TODO
                    throw new UnsupportedOperationException();
                  }
                  else
                  {
                    //TODO
                    throw new UnsupportedOperationException();
                  }
                }
              }
              this.modCount=modCount+1;
              return true;
            }
          }
          for(int gapEnd=0;gapEnd!=tail;++gapEnd){
            if(!filter.test((double)arr[gapEnd])){
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
        if(filter.test((double)arr[gapBegin])){
          for(int gapEnd=gapBegin+1;gapEnd!=tail;++gapEnd){
            if(!filter.test((double)arr[gapEnd])){
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
    private void fragmentedCollapseHeadAndTail(double[] arr,int head,int tail,DoublePredicate filter,int modCount){
      int newTail=tail-1,newHead=head+1,bound=arr.length-1;
      for(;;--newTail){
        if(newTail==-1){
          for(;;++newHead){
            if(newHead>bound){
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.tail=-1;
              break;
            }else if(!filter.test((double)arr[newHead])){
              collapsetailHelper(arr,newHead,filter,modCount);
              break;
            }
          }
          break;
        }else if(!filter.test((double)arr[newTail])){
          for(;;++newHead){
            if(newHead>bound){
              collapseheadHelper(arr,newTail,filter,modCount);
              break;
            }else if(!filter.test((double)arr[newHead])){
              fragmentedCollapseBodyHelper(arr,newHead,newTail,filter,modCount);
              break;
            }
          }
          break;
        }
      }
    }
    private void collapseHeadAndTail(double[] arr,int head,int tail,DoublePredicate filter,int modCount){
      for(int headOffset=head+1;headOffset!=tail;++headOffset){
        if(!filter.test((double)arr[headOffset])){
          for(int tailOffset=tail-1;tailOffset!=headOffset;--tailOffset){
            if(!filter.test((double)arr[tailOffset])){
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
    @Override boolean fragmentedRemoveIf(int head,int tail,DoublePredicate filter){
      int modCount=this.modCount;
      try{
        final double[] arr;
        if(filter.test((double)(arr=this.arr)[head])){
          if(filter.test((double)arr[tail])){
            fragmentedCollapseHeadAndTail(arr,head,tail,filter,modCount);
          }else{
            fragmentedCollapsehead(arr,head,tail,filter,modCount);
          }
          this.modCount=modCount+1;
          return true;
        }else{
          if(filter.test((double)arr[tail])){
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
    @Override boolean nonfragmentedRemoveIf(int head,int tail,DoublePredicate filter){
      final int modCount=this.modCount;
      try{
        final double[] arr;
        if(filter.test((double)(arr=this.arr)[head])){
          if(head==tail){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.tail=-1;
          }else if(filter.test((double)arr[tail])){
            collapseHeadAndTail(arr,head,tail,filter,modCount);
          }else{
            collapsehead(arr,head,tail,filter,modCount);
          }
          this.modCount=modCount+1;
          return true;
        }else if(head!=tail){
          if(filter.test((double)arr[tail])){
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
    @Override public OmniIterator.OfDouble iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniIterator.OfDouble descendingIterator(){
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
      @Override public double nextDouble(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final double[] arr;
          final var ret=(double)(arr=root.arr)[cursor];
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
      private void fragmentedAscendingRemove(int head,int lastRet,int tail,DoubleArrDeq root){
        double[] arr;
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
      private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,DoubleArrDeq root){
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
      @Override void uncheckedForEachRemaining(int cursor,DoubleConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int tail=(root=this.root).tail;
        try{
          final var arr=root.arr;
          if(cursor>tail){
            OmniArray.OfDouble.ascendingForEach(arr,cursor,arr.length-1,action);
            cursor=0;
          }
          OmniArray.OfDouble.ascendingForEach(arr,cursor,tail,action);
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
      @Override public double nextDouble(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final double[] arr;
          final var ret=(double)(arr=root.arr)[cursor];
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
      private void fragmentedDescendingRemove(int head,int lastRet,int tail,DoubleArrDeq root){
        double[] arr;
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
      private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,DoubleArrDeq root){
        int tailDist,headDist;
        if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
          ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
          root.tail=tail-1;
        }else{
          if(headDist==0){
            root.head=head+1;
          }else{
            double[] arr;
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
      @Override void uncheckedForEachRemaining(int cursor,DoubleConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int head=(root=this.root).head;
        try{
          final var arr=root.arr;
          if(cursor<head){
            OmniArray.OfDouble.descendingForEach(arr,0,cursor,action);
            cursor=arr.length-1;
          }
          OmniArray.OfDouble.descendingForEach(arr,head,cursor,action);
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
        final double[] dst;
        int size,head;
        Checked clone;
        if((size=(++tail)-(head=this.head))<=0){
          clone=new Checked(0,dst=new double[size+=arr.length],size-1);
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
        }else{
          clone=new Checked(0,dst=new double[size],size-1);
        }
        ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        return clone;
      }
      return new Checked();
    }
    @Override public double removeLastDouble(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final double[] arr;
        var ret=(double)((arr=this.arr)[tail]);
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
    @Override public double popDouble(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final double[] arr;
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
    @Override public void push(double val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void addLast(double val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override void uncheckedForEach(final int tail,DoubleConsumer action){
      final int modCount=this.modCount;
      try{
        super.uncheckedForEach(tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public double doubleElement(){
      if(tail!=-1){
        return (double)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override public double getLastDouble(){
      final int tail;
      if((tail=this.tail)!=-1){
        return (double)arr[tail];
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
    @Override public double pollDouble(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final double[] arr;
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
        final double[] arr;
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
    @Override public Double poll(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final double[] arr;
        int head;
        var ret=(Double)((arr=this.arr)[head=this.head]);
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
    @Override public Double pollLast(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final double[] arr;
        var ret=(Double)((arr=this.arr)[tail]);
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
    @Override
    boolean uncheckedremoveValBits(int tail
    ,long bits
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(index=head,bound=arr.length-1;;++index){
            if(bits==Double.doubleToRawLongBits(arr[index])){
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
                if(bits==Double.doubleToRawLongBits(arr[index])){
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
            if(bits==Double.doubleToRawLongBits(arr[index])){
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
    boolean uncheckedremoveVal0(int tail
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(index=head,bound=arr.length-1;;++index){
            if(0==(arr[index])){
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
                if(0==(arr[index])){
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
            if(0==(arr[index])){
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
    boolean uncheckedremoveValNaN(int tail
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(index=head,bound=arr.length-1;;++index){
            if(Double.isNaN(arr[index])){
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
                if(Double.isNaN(arr[index])){
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
            if(Double.isNaN(arr[index])){
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
    boolean uncheckedremoveLastOccurrenceBits(int tail
    ,long bits
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(bound=arr.length-1,index=tail;;--index){
            if(bits==Double.doubleToRawLongBits(arr[index])){
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
                if(bits==Double.doubleToRawLongBits(arr[index])){
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
            if(bits==Double.doubleToRawLongBits(arr[index])){
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
    @Override
    boolean uncheckedremoveLastOccurrence0(int tail
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(bound=arr.length-1,index=tail;;--index){
            if(0==(arr[index])){
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
                if(0==(arr[index])){
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
            if(0==(arr[index])){
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
    @Override
    boolean uncheckedremoveLastOccurrenceNaN(int tail
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(bound=arr.length-1,index=tail;;--index){
            if(Double.isNaN(arr[index])){
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
                if(Double.isNaN(arr[index])){
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
            if(Double.isNaN(arr[index])){
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
