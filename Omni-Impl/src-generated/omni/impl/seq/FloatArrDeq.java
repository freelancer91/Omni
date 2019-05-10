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
import omni.function.FloatPredicate;
import omni.function.FloatConsumer;
import omni.util.ToStringUtil;
import omni.impl.AbstractFloatItr;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.util.RandomAccess;
public class FloatArrDeq implements OmniDeque.OfFloat,Externalizable,Cloneable,RandomAccess{
  private static final long serialVersionUID=1L;
  transient float[] arr;
  transient int head;
  transient int tail;
  public FloatArrDeq(){
    super();
    this.arr=OmniArray.OfFloat.DEFAULT_ARR;
    this.tail=-1;
  }
  public FloatArrDeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new float[initialCapacity];
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfFloat.DEFAULT_ARR;
    case 0:
    }
    this.tail=-1;
  }
  FloatArrDeq(int head,float[] arr,int tail){
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
    return this.tail!=-1;
  }
  @Override public void forEach(FloatConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action);
    }
  }
  @Override public boolean removeIf(FloatPredicate filter){
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
  void uncheckedForEach(final int tail,FloatConsumer action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      OmniArray.OfFloat.ascendingForEach(arr,head,arr.length-1,action);
      head=0;
    }
    OmniArray.OfFloat.ascendingForEach(arr,head,tail,action);
  }
  @Override public boolean add(float val){
    addLast(val);
    return true;
  }
  @Override public void addFirst(float val){
    push(val);
  }
  @Override public boolean offerFirst(float val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(float val){
    addLast(val);
    return true;
  }
  @Override public float floatElement(){
    return (float)arr[head];
  }
  @Override public float getLastFloat(){
    return (float)arr[tail];
  }
  @Override public boolean offer(float val){
    addLast(val);
    return true;
  }
  @Override public boolean contains(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val){
          return uncheckedcontainsBits(tail,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val)){
            return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
        final float v;
        if(val==(v=(float)val)){
          return uncheckedcontainsBits(tail,Float.floatToRawIntBits(v));
        }else if(v!=v){
          return uncheckedcontainsNaN(tail);
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
          if(val instanceof Float){
            final float f;
            if((f=(float)val)==f){
               return uncheckedcontainsBits(tail,Float.floatToRawIntBits(f));
            }
            return uncheckedcontainsNaN(tail);
          }else if(val instanceof Double){
            final double d;
            final float f;
            if((d=(double)val)==(f=(float)d)){
              return uncheckedcontainsBits(tail,Float.floatToRawIntBits(f));
            }else if(f!=f){
              return uncheckedcontainsNaN(tail);
            }else{
              break returnFalse;
            }
          }else if(val instanceof Integer){
            final int i;
            if((i=(int)val)!=0){
              if(!TypeUtil.checkCastToFloat(i)){
                break returnFalse;
              }
              return uncheckedcontainsBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedcontains0(tail);
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=0){
              if(!TypeUtil.checkCastToFloat(l)){
                break returnFalse;
              }
              return uncheckedcontainsBits(tail,Float.floatToRawIntBits(l));
            }
            return uncheckedcontains0(tail);
          }else if(val instanceof Short||val instanceof Byte){
            final int i;
            if((i=((Number)val).shortValue())!=0){
              return uncheckedcontainsBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedcontains0(tail);
          }else if(val instanceof Character){
            final int i;
            if((i=(char)val)!=0){
              return uncheckedcontainsBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedcontains0(tail);
          }else if(val instanceof Boolean){
            if((boolean)val){
              return uncheckedcontainsBits(tail,TypeUtil.FLT_TRUE_BITS);
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
          return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedcontainsBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedremoveValBits(tail,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val)){
            return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
        final float v;
        if(val==(v=(float)val)){
          return uncheckedremoveValBits(tail,Float.floatToRawIntBits(v));
        }else if(v!=v){
          return uncheckedremoveValNaN(tail);
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
          if(val instanceof Float){
            final float f;
            if((f=(float)val)==f){
               return uncheckedremoveValBits(tail,Float.floatToRawIntBits(f));
            }
            return uncheckedremoveValNaN(tail);
          }else if(val instanceof Double){
            final double d;
            final float f;
            if((d=(double)val)==(f=(float)d)){
              return uncheckedremoveValBits(tail,Float.floatToRawIntBits(f));
            }else if(f!=f){
              return uncheckedremoveValNaN(tail);
            }else{
              break returnFalse;
            }
          }else if(val instanceof Integer){
            final int i;
            if((i=(int)val)!=0){
              if(!TypeUtil.checkCastToFloat(i)){
                break returnFalse;
              }
              return uncheckedremoveValBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedremoveVal0(tail);
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=0){
              if(!TypeUtil.checkCastToFloat(l)){
                break returnFalse;
              }
              return uncheckedremoveValBits(tail,Float.floatToRawIntBits(l));
            }
            return uncheckedremoveVal0(tail);
          }else if(val instanceof Short||val instanceof Byte){
            final int i;
            if((i=((Number)val).shortValue())!=0){
              return uncheckedremoveValBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedremoveVal0(tail);
          }else if(val instanceof Character){
            final int i;
            if((i=(char)val)!=0){
              return uncheckedremoveValBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedremoveVal0(tail);
          }else if(val instanceof Boolean){
            if((boolean)val){
              return uncheckedremoveValBits(tail,TypeUtil.FLT_TRUE_BITS);
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
          return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedremoveValBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val)){
            return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
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
        final float v;
        if(val==(v=(float)val)){
          return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(v));
        }else if(v!=v){
          return uncheckedremoveLastOccurrenceNaN(tail);
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
          if(val instanceof Float){
            final float f;
            if((f=(float)val)==f){
               return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(f));
            }
            return uncheckedremoveLastOccurrenceNaN(tail);
          }else if(val instanceof Double){
            final double d;
            final float f;
            if((d=(double)val)==(f=(float)d)){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(f));
            }else if(f!=f){
              return uncheckedremoveLastOccurrenceNaN(tail);
            }else{
              break returnFalse;
            }
          }else if(val instanceof Integer){
            final int i;
            if((i=(int)val)!=0){
              if(!TypeUtil.checkCastToFloat(i)){
                break returnFalse;
              }
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedremoveLastOccurrence0(tail);
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=0){
              if(!TypeUtil.checkCastToFloat(l)){
                break returnFalse;
              }
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(l));
            }
            return uncheckedremoveLastOccurrence0(tail);
          }else if(val instanceof Short||val instanceof Byte){
            final int i;
            if((i=((Number)val).shortValue())!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedremoveLastOccurrence0(tail);
          }else if(val instanceof Character){
            final int i;
            if((i=(char)val)!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedremoveLastOccurrence0(tail);
          }else if(val instanceof Boolean){
            if((boolean)val){
              return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.FLT_TRUE_BITS);
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
  @Override public boolean removeLastOccurrence(char val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveLastOccurrence0(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveLastOccurrence0(tail);
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
          return uncheckedsearchBits(tail,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedsearchBits(tail,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val)){
            return uncheckedsearchBits(tail,Float.floatToRawIntBits(val));
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
          return uncheckedsearchBits(tail,Float.floatToRawIntBits(val));
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
        final float v;
        if(val==(v=(float)val)){
          return uncheckedsearchBits(tail,Float.floatToRawIntBits(v));
        }else if(v!=v){
          return uncheckedsearchNaN(tail);
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
          if(val instanceof Float){
            final float f;
            if((f=(float)val)==f){
               return uncheckedsearchBits(tail,Float.floatToRawIntBits(f));
            }
            return uncheckedsearchNaN(tail);
          }else if(val instanceof Double){
            final double d;
            final float f;
            if((d=(double)val)==(f=(float)d)){
              return uncheckedsearchBits(tail,Float.floatToRawIntBits(f));
            }else if(f!=f){
              return uncheckedsearchNaN(tail);
            }else{
              break returnFalse;
            }
          }else if(val instanceof Integer){
            final int i;
            if((i=(int)val)!=0){
              if(!TypeUtil.checkCastToFloat(i)){
                break returnFalse;
              }
              return uncheckedsearchBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedsearch0(tail);
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=0){
              if(!TypeUtil.checkCastToFloat(l)){
                break returnFalse;
              }
              return uncheckedsearchBits(tail,Float.floatToRawIntBits(l));
            }
            return uncheckedsearch0(tail);
          }else if(val instanceof Short||val instanceof Byte){
            final int i;
            if((i=((Number)val).shortValue())!=0){
              return uncheckedsearchBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedsearch0(tail);
          }else if(val instanceof Character){
            final int i;
            if((i=(char)val)!=0){
              return uncheckedsearchBits(tail,Float.floatToRawIntBits(i));
            }
            return uncheckedsearch0(tail);
          }else if(val instanceof Boolean){
            if((boolean)val){
              return uncheckedsearchBits(tail,TypeUtil.FLT_TRUE_BITS);
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
  @Override public int search(char val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          return uncheckedsearchBits(tail,Float.floatToRawIntBits(val));
        }
        return uncheckedsearch0(tail);
      }
    }
    return -1;
  }
  @Override public int search(short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=0){
          return uncheckedsearchBits(tail,Float.floatToRawIntBits(val));
        }
        return uncheckedsearch0(tail);
      }
    }
    return -1;
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
  @Override public Float[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final Float[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new Float[size+=arr.length],size-=tail,tail);
      }else{
        dst=new Float[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  @Override public Float peek(){
    if(tail!=-1){
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
  @Override public float pollFloat(){
    int tail;
    if((tail=this.tail)!=-1){
      final float[] arr;
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
      final float[] arr;
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
  @Override public Float poll(){
    int tail;
    if((tail=this.tail)!=-1){
      final float[] arr;
      int head;
      var ret=(Float)((arr=this.arr)[head=this.head]);
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
  @Override public Float pollLast(){
    int tail;
    if((tail=this.tail)!=-1){
      final float[] arr;
      var ret=(Float)((arr=this.arr)[tail]);
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
      final float[] arr;
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
      final float[] arr;
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
    addLast((float)TypeUtil.castToFloat(val));
    return true;
  }
  @Override public boolean add(int val){
    addLast((float)val);
    return true;
  }
  @Override public boolean add(char val){
    addLast((float)val);
    return true;
  }
  @Override public boolean add(short val){
    addLast((float)val);
    return true;
  }
  @Override public boolean add(long val){
    addLast((float)val);
    return true;
  }
  @Override public Float getFirst(){
    return floatElement();
  }
  @Override public Float peekFirst(){
    return peekFloat();
  }
  @Override public Float pollFirst(){
    return pollFloat();
  }
  @Override public Float removeFirst(){
    return popFloat();
  }
  @Override public Float remove(){
    return popFloat();
  }
  @Override public boolean removeFirstOccurrence(Object val){
    return remove(val);
  }
  @Override public Float pop(){
    return popFloat();
  }
  @Override public Float removeLast(){
    return removeLastFloat();
  }
  @Override public void push(Float val){
    push((float)val);
  }
  @Override public boolean offer(Float val){
    addLast((float)val);
    return true;
  }
  @Override public Float element(){
    return floatElement();
  }
  @Override public Float getLast(){
    return getLastFloat();
  }
  @Override public void addFirst(Float val){
    push((float)val);
  }
  @Override public void addLast(Float val){
    addLast((float)val);
  }
  @Override public boolean add(Float val){
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
  boolean uncheckedremoveValBits(int tail
  ,int bits
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(index=head,bound=arr.length-1;;++index){
          if(bits==Float.floatToRawIntBits(arr[index])){
            int headDist,tailDist;
            if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
              if(bits==Float.floatToRawIntBits(arr[index])){
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
          }
        }
      }else{
        for(index=head;;++index){
          if(bits==Float.floatToRawIntBits(arr[index])){
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
            if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
          if(Float.isNaN(arr[index])){
            int headDist,tailDist;
            if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
              if(Float.isNaN(arr[index])){
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
          }
        }
      }else{
        for(index=head;;++index){
          if(Float.isNaN(arr[index])){
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
  ,int bits
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(bound=arr.length-1,index=tail;;--index){
          if(bits==Float.floatToRawIntBits(arr[index])){
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
              if(bits==Float.floatToRawIntBits(arr[index])){
                int headDist,tailDist;
                if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
          }
        }
      }else{
        for(index=tail;;--index){
          if(bits==Float.floatToRawIntBits(arr[index])){
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
                if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
          if(Float.isNaN(arr[index])){
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
              if(Float.isNaN(arr[index])){
                int headDist,tailDist;
                if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
          }
        }
      }else{
        for(index=tail;;--index){
          if(Float.isNaN(arr[index])){
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
  ,int bits
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfFloat.uncheckedcontainsBits(arr,0,tail,bits) || OmniArray.OfFloat.uncheckedcontainsBits(arr,head,arr.length-1,bits);
    }
    return OmniArray.OfFloat.uncheckedcontainsBits(arr,head,tail,bits);
  }
  private boolean uncheckedcontains0(int tail
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfFloat.uncheckedcontains0(arr,0,tail ) || OmniArray.OfFloat.uncheckedcontains0(arr,head,arr.length-1 );
    }
    return OmniArray.OfFloat.uncheckedcontains0(arr,head,tail );
  }
  private boolean uncheckedcontainsNaN(int tail
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfFloat.uncheckedcontainsNaN(arr,0,tail ) || OmniArray.OfFloat.uncheckedcontainsNaN(arr,head,arr.length-1 );
    }
    return OmniArray.OfFloat.uncheckedcontainsNaN(arr,head,tail );
  }
  private int uncheckedsearchBits(int tail
  ,int bits
  ){
    final var arr=this.arr;
    int head,prefix;
    if(tail<(head=this.head)){
      int bound;
      for(prefix=head,bound=arr.length;;)
      {
        if(bits==Float.floatToRawIntBits(arr[prefix]))
        {
          return prefix-head+1;
        }
        if(++prefix==bound){
          prefix=bound-head;
          head=0;
        }
      }
    }else{
      prefix=-head;
    }
    for(;;++head){
      if(bits==Float.floatToRawIntBits(arr[head]))
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
        if(Float.isNaN(arr[prefix]))
        {
          return prefix-head+1;
        }
        if(++prefix==bound){
          prefix=bound-head;
          head=0;
        }
      }
    }else{
      prefix=-head;
    }
    for(;;++head){
      if(Float.isNaN(arr[head]))
      {
        return prefix+head+1;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override public boolean removeIf(Predicate<? super Float> filter){
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
  @Override public float popFloat(){
    final float[] arr;
    int head;
    var ret=(float)((arr=this.arr)[head=this.head]);
    if(head==this.tail){
      this.tail=-1;
      return ret;
    }else if(++head==arr.length){
      head=0;
    }
    this.head=head;
    return ret;
  }
  @Override public float removeLastFloat(){
    final float[] arr;
    int tail;
    var ret=(float)((arr=this.arr)[tail=this.tail]);
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
      final float[] dst;
      int size,head;
      FloatArrDeq clone;
      if((size=(++tail)-(head=this.head))<=0){
        clone=new FloatArrDeq(0,dst=new float[size+=arr.length],size-1);
        ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
      }else{
        clone=new FloatArrDeq(0,dst=new float[size],size-1);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return clone;
    }
    return new FloatArrDeq();
  }
  private String uncheckedToString(int tail){
    final var arr=this.arr;
    final byte[] buffer;
    int size,head,bufferOffset=1;
    if((size=(++tail)-(head=this.head))<=0){
      int bound;
      if((size+=(bound=arr.length))<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
        [0]=(byte)'[';
        for(;;++bufferOffset){
          buffer[bufferOffset=ToStringUtil.getStringFloat(arr[head],buffer,bufferOffset)]=(byte)',';
          buffer[++bufferOffset]=(byte)' ';
          if(++head==bound){
            for(head=0;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
              bufferOffset=ToStringUtil.getStringFloat(arr[head],buffer,++bufferOffset);;
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
          builder.uncheckedAppendFloat(arr[head]);
          builder.uncheckedAppendCommaAndSpace();
          if(++head==bound){
            for(head=0;;builder.uncheckedAppendCommaAndSpace()){
              builder.uncheckedAppendFloat(arr[head]);
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
      if(size<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
        [size=OmniArray.OfFloat.ascendingToString(arr,head,tail-1,buffer,1)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        OmniArray.OfFloat.ascendingToString(arr,head,tail-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
  }
  private int uncheckedHashCode(int tail){
    final float[] arr;
    int head;
    int hash=31+HashUtil.hashFloat((arr=this.arr)[head=this.head]);
    if(tail<head){
      for(final int bound=arr.length;;){  
        if(++head==bound){
          hash=hash*31+HashUtil.hashFloat(arr[head=0]);
          break;
        }
        hash=(hash*31)+HashUtil.hashFloat(arr[head]);
      }
    }
    for(;head!=tail;hash=(hash*31)+HashUtil.hashFloat(arr[head]),++head){}
    return hash;
  }
  @Override public void push(float val){
    float[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfFloat.DEFAULT_ARR){
        this.head=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.tail=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.arr=arr=new float[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
            final float[] newArr;
            int newCap,size;
            this.tail=(newCap=OmniArray.growBy50Pct(head+(size=arr.length)))-1;
            ArrCopy.uncheckedCopy(arr,0,newArr=new float[newCap],newCap-=(++tail),tail);
            ArrCopy.uncheckedCopy(arr,head,newArr,head=newCap-(size-=tail),size);
            this.arr=newArr;
            --head;
          }else if(head==-1 && tail==(head=arr.length-1)){
            int newCap;
            this.tail=(newCap=OmniArray.growBy50Pct(++tail))-1;
            ArrCopy.uncheckedCopy(arr,0,arr=new float[newCap],head=newCap-tail,tail);
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
  private void initFromNullArr(float val){
    this.head=0;
    this.tail=0;
    this.arr=new float[]{val};
  }
  @Override public void addLast(float val){
    float[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfFloat.DEFAULT_ARR){
        this.head=0;
        this.tail=0;
        this.arr=arr=new float[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
            final float[] newArr;
            (newArr=new float[OmniArray.growBy50Pct(tail=arr.length)])[tail]=val;
            this.tail=tail;
            ArrCopy.uncheckedCopy(arr,head,newArr,0,tail-=head);
            ArrCopy.uncheckedCopy(arr,0,newArr,tail,head);
            this.arr=newArr;
          }else{
            if(tail==arr.length){
              if(head==0){
                ArrCopy.uncheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(tail)],0,tail);
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
    extends AbstractFloatItr
  {
    transient int cursor;
    AbstractDeqItr(int cursor){
      this.cursor=cursor;
    }
    @Override public boolean hasNext(){
      return this.cursor!=-1;
    }
    abstract void uncheckedForEachRemaining(int cursor,FloatConsumer action);
    @Override public void forEachRemaining(FloatConsumer action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Float> action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action::accept);
      }
    }
  }
  private static int pullUp(float[] arr,int head,int headDist){
    ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
    return head;
  }
  private static int fragmentedPullUp(float[] arr,int head,int headDist){
    if(headDist==0){
      return 0;
    }else{
      ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
      return head;
    }
  }
  private static int fragmentedPullDown(float[] arr,int arrBound,int tail){
    if(tail==0){
      return arrBound;
    }
    ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
    return tail-1;
  }
  private static class AscendingItr extends AbstractDeqItr
  {
    transient final FloatArrDeq root;
    private AscendingItr(FloatArrDeq root){
      super(root.tail!=-1?root.head:-1);
      this.root=root;
    }
    private AscendingItr(FloatArrDeq root,int cursor){
      super(cursor);
      this.root=root;
    }
    @Override public float nextFloat(){
      final float[] arr;
      int cursor;
      final FloatArrDeq root;
      final var ret=(float)(arr=(root=this.root).arr)[cursor=this.cursor];
      if(cursor==root.tail){
        cursor=-1;
      }else if(++cursor==arr.length){
        cursor=0;
      }
      this.cursor=cursor;
      return ret;
    }
    private void nonfragmentedRemove(int head,int cursor,int tail,FloatArrDeq root){
      int headDist,tailDist;
      if((headDist=(--cursor)-head)<=(tailDist=tail-cursor)){
        root.head=pullUp(root.arr,head,headDist);
      }else{
        ArrCopy.uncheckedSelfCopy(root.arr,cursor,cursor+1,tailDist);
        root.tail=tail-1;
        this.cursor=cursor;
      }
    }
    private void fragmentedRemove(int head,int cursor,int tail,FloatArrDeq root){
      float[] arr;
      int headDist,tailDist,arrBound=(arr=root.arr).length;
      if((headDist=(--cursor)-head)>=0){
        if(headDist<=(tailDist=arrBound-cursor)+tail){
          root.head=pullUp(arr,head,headDist);
        }else{
          ArrCopy.semicheckedSelfCopy(arr,cursor,cursor+1,tailDist);
          arr[arrBound]=arr[0];
          root.tail=fragmentedPullDown(arr,arrBound,tail);
          this.cursor=cursor;
        }
      }else{
        if((tailDist=tail-cursor)<=(headDist=arrBound-head)+cursor){
          ArrCopy.uncheckedSelfCopy(arr,cursor,cursor+1,tailDist);
          root.tail=tail-1;
          this.cursor=cursor;
        }else{
          ArrCopy.semicheckedCopy(arr,0,arr,1,cursor);
          arr[0]=arr[arrBound];
          root.head=fragmentedPullUp(arr,head,headDist);
        }
      }
    }
    private void eraseAtSplit(){
      final int head,tail,headDist,arrBound;
      final FloatArrDeq root;
      final float[] arr;
      if((tail=(root=this.root).tail)<(headDist=(arrBound=(arr=root.arr).length-1)-(head=root.head))){
        arr[arrBound]=arr[0];
        root.tail=fragmentedPullDown(arr,arrBound,tail);
        this.cursor=arrBound;
      }else{
        root.head=fragmentedPullUp(arr,head,headDist);
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
          final FloatArrDeq root;
          if((tail=(root=this.root).tail)<(head=root.head)){
            fragmentedRemove(head,cursor,tail,root);
          }else{
            nonfragmentedRemove(head,cursor,tail,root);
          }
      }
    }
    @Override void uncheckedForEachRemaining(int cursor,FloatConsumer action){
      final FloatArrDeq root;
      int tail;
      final var arr=(root=this.root).arr;
      if(cursor>(tail=root.tail)){
        OmniArray.OfFloat.ascendingForEach(arr,cursor,arr.length-1,action);
        cursor=0;
      }
      OmniArray.OfFloat.ascendingForEach(arr,cursor,tail,action);
      this.cursor=-1;
    }
  }
  private static class DescendingItr extends AscendingItr
  {
    private DescendingItr(FloatArrDeq root,int cursor){
      super(root,cursor);
    }
    @Override void uncheckedForEachRemaining(int cursor,FloatConsumer action){
      final FloatArrDeq root;
      final int head;
      final var arr=(root=this.root).arr;
      if(--cursor<(head=root.head)){
         if(cursor>=0){
           OmniArray.OfFloat.descendingForEach(arr,cursor,0,action);
         }
         cursor=arr.length-1;
      }
      OmniArray.OfFloat.descendingForEach(arr,head,cursor,action);
      this.cursor=-1;
    }
    @Override public float nextFloat(){
      int cursor;
      final FloatArrDeq root;
      final var arr=(root=this.root).arr;
      if((cursor=this.cursor-1)==-1){
        cursor=arr.length-1;
      }
      this.cursor=(cursor==root.head)?-1:cursor;
      return (float)arr[cursor];
    }
    private void eraseAtSplit(){
      final FloatArrDeq root;
      final int head,tail,headDist,arrBound;
      final float[] arr;
      if((tail=(root=this.root).tail)<=(headDist=(arrBound=(arr=root.arr).length-1)-(head=root.head))){
        root.tail=fragmentedPullDown(arr,arrBound,tail);
      }else{
        root.head=fragmentedPullUp(arr,head,headDist);
        this.cursor=0;
      }
    }
    private void fragmentedRemove(int head,int lastRet,int tail,FloatArrDeq root){
      float[] arr;
      int headDist,tailDist,arrBound=(arr=root.arr).length;
      if((headDist=lastRet-head)>=0){
        if(headDist<=(tailDist=arrBound-lastRet)+tail){
          root.head=pullUp(arr,head,headDist);
          this.cursor=lastRet;
        }else{
          ArrCopy.semicheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
          arr[arrBound]=arr[0];
          root.tail=fragmentedPullDown(arr,arrBound,tail);
        }
      }else{
        if((tailDist=tail-lastRet)<=(headDist=arrBound-head)+lastRet){
          ArrCopy.uncheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
          root.tail=tail-1;
        }else{
          ArrCopy.semicheckedCopy(arr,0,arr,1,lastRet);
          arr[0]=arr[arrBound];
          root.head=fragmentedPullUp(arr,head,headDist);
          this.cursor=lastRet;
        }
      }
    }
    private void nonfragmentedRemove(int head,int lastRet,int tail,FloatArrDeq root){
      int headDist,tailDist;
      if((headDist=lastRet-head)<=(tailDist=tail-lastRet)){
        root.head=pullUp(root.arr,head,headDist);
        this.cursor=lastRet;
      }else{
        ArrCopy.uncheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
        root.tail=tail-1;
      }
    }
    @Override public void remove(){
      final int cursor;
      switch(cursor=this.cursor){
        case -1:
          root.eraseHead();
          break;
        case 0:
          eraseAtSplit();
          break;
        default:
          final int head,tail;
          final FloatArrDeq root;
          if((tail=(root=this.root).tail)<(head=root.head)){
            fragmentedRemove(head,cursor,tail,root);
          }else{
            nonfragmentedRemove(head,cursor,tail,root);
          }
      }
    }
  }
  @Override public OmniIterator.OfFloat iterator(){
    return new AscendingItr(this);
  }
  @Override public OmniIterator.OfFloat descendingIterator(){
    int tail;
    return new DescendingItr(this,(tail=this.tail)==-1?-1:tail==arr.length-1?0:tail+1);
  }
  boolean fragmentedRemoveIf(int head,int tail,FloatPredicate filter){
    float[] arr;
    if(filter.test((float)(arr=this.arr)[head]))
    {
      if(filter.test((float)arr[tail]))
      {
        fragmentedCollapseHeadAndTail(arr,head,tail,filter);
      }
      else
      {
        fragmentedCollapsehead(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((float)arr[tail]))
    {
      fragmentedCollapsetail(arr,head,tail,filter);
      return true;
    }
    return fragmentedCollapseBody(arr,head,tail,filter);
  }
  @Override public boolean equals(Object obj){
      //TODO
      return false;
  }
  private static  int pullDown(float[] arr,int dstOffset,int srcBound,FloatPredicate filter){
    for(int srcOffset=dstOffset+1;srcOffset!=srcBound;++srcOffset)
    {
      final float v;
      if(!filter.test((float)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    return dstOffset;
  }
  private static  int pullUp(float[] arr,int dstOffset,int srcBound,FloatPredicate filter){
    for(int srcOffset=dstOffset-1;srcOffset!=srcBound;--srcOffset)
    {
      final float v;
      if(!filter.test((float)(v=arr[srcOffset])))
      {
        arr[dstOffset--]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    return dstOffset;
  }
  private void fragmentedCollapseBodyHelper(float[] arr,int head,int tail,FloatPredicate filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((float)arr[srcOffset]))
      {
        tail=pullDown(arr,srcOffset,tail,filter);
        break;
      }
    }
    this.tail=tail;
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset)
    {
      if(filter.test((float)arr[srcOffset]))
      {
        head=pullUp(arr,srcOffset,head,filter);
        break;
      }
    }
    this.head=head;
  }
  private void collapseBodyHelper(float[] ar,int head,int tail,FloatPredicate filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((float)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((float)arr[midPoint])){
            tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        this.tail=tail;
        return;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((float)arr[midPoint])){
        tail=pullDown(arr,midPoint,tail,filter);
        break;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  private void fragmentedCollapseHeadAndTail(float[] arr,int head,int tail,FloatPredicate filter){
    outer:for(;;){
      do{
        if(tail==0){
          for(tail=arr.length-1;tail!=head;--tail){
            if(!filter.test((float)arr[tail])){
              break outer;
            }
          }  
          this.tail=-1;
          return;
        }
      }while(filter.test((float)arr[--tail]));
      for(int bound=arr.length;++head!=bound;){
        if(!filter.test((float)arr[head])){
          fragmentedCollapseBodyHelper(arr,head,tail,filter);
          return;
        }
      }
      head=-1;
      break;
    }
    while(++head!=tail){
      if(!filter.test((float)arr[head])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  private boolean fragmentedCollapseBody(float[] arr,int head,int tail,FloatPredicate filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset){
      if(filter.test((float)arr[srcOffset])){
        this.tail=pullDown(arr,srcOffset,tail,filter);
        for(srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
          if(filter.test((float)arr[srcOffset])){
            this.head=pullUp(arr,srcOffset,head,filter);
            break;
          }
        }
        return true;
      }
    }
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
      if(filter.test((float)arr[srcOffset]))
      {
        this.head=pullUp(arr,srcOffset,head,filter);
        return true;
      }
    }
    return false;
  }
  private void fragmentedCollapsehead(float[] arr,int head,int tail,FloatPredicate filter){
    for(int bound=arr.length;;)
    {
      if(++head==bound){
        for(head=0;head!=tail;++head)
        {
          if(!filter.test((float)arr[head]))
          {
            collapseBodyHelper(arr,head,tail,filter);
            break;
          }
        }
        return;
      }
      if(!filter.test((float)arr[head]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  private void collapsehead(float[] arr,int head,int tail,FloatPredicate filter){
    do{
      if(++head==tail){
        this.head=head;
        return;
      }
    }while(filter.test((float)arr[head]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  private void fragmentedCollapsetail(float[] arr,int head,int tail,FloatPredicate filter){
    for(;;)
    {
      if(tail==0){
        for(tail=arr.length-1;tail!=head;--tail)
        {
          if(!filter.test((float)arr[tail]))
          {
            collapseBodyHelper(arr,head,tail,filter);
            break;
          }
        }
        return;
      }
      if(!filter.test((float)arr[--tail]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  private void collapsetail(float[] arr,int head,int tail,FloatPredicate filter){
    do{
      if(--tail==head){
        this.tail=tail;
        return;
      }
    }while(filter.test((float)arr[tail]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  private void collapseHeadAndTail(float[] arr,int head,int tail,FloatPredicate filter){
    do{
      if(++head==tail){
        this.tail=-1;
        return;
      }
    }while(filter.test((float)arr[head]));
    while(--tail!=head){
      if(!filter.test((float)arr[tail])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
    this.head=head;
    this.tail=head;
  }
  private boolean collapseBody(float[] arr,int head,int tail,FloatPredicate filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((float)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((float)arr[midPoint])){
            this.tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        return true;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((float)arr[midPoint])){
        this.tail=pullDown(arr,midPoint,tail,filter);
        return true;
      }
    }
    return false;
  }
  boolean nonfragmentedRemoveIf(int head,int tail,FloatPredicate filter){
    float[] arr;
    if(filter.test((float)(arr=this.arr)[head])){
      if(head==tail){
        this.tail=-1;
      }else{
        if(filter.test((float)arr[tail])){
          collapseHeadAndTail(arr,head,tail,filter);
        }else{
          collapsehead(arr,head,tail,filter);
        }
      }
      return true;
    }else if(head!=tail){
      if(filter.test((float)arr[tail])){
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
      float[] arr;
      OmniArray.OfFloat.readArray(arr=new float[size],0,--size,input);
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
        float[] arr;
        output.writeInt(size+(size=(arr=this.arr).length));
        OmniArray.OfFloat.writeArray(arr,head,size-1,output);
        OmniArray.OfFloat.writeArray(arr,0,tail-1,output);
      }else{
        output.writeInt(size);
        OmniArray.OfFloat.writeArray(arr,head,tail-1,output);
      }
    }else{
      output.writeInt(0);
    }
  }
  public static class Checked extends FloatArrDeq{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(int initialCapacity){
      super(initialCapacity);
    }
    Checked(int head,float[] arr,int tail){
      super(head,arr,tail);
    }
    @Override boolean fragmentedRemoveIf(int head,int tail,FloatPredicate filter){
      //TODO
      return false;
    }
    @Override boolean nonfragmentedRemoveIf(int head,int tail,FloatPredicate filter){
      //TODO
      return false;
    }
    @Override public boolean equals(Object obj){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniIterator.OfFloat descendingIterator(){
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
      @Override public float nextFloat(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final float[] arr;
          final var ret=(float)(arr=root.arr)[cursor];
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
      void fragmentedRemove(int head,int lastRet,int tail,Checked root){
        float[] arr;
        int headDist,tailDist,arrBound=(arr=root.arr).length;
        if((headDist=lastRet-head)>=0){
          if(headDist<=(tailDist=arrBound-lastRet)+tail){
            root.head=pullUp(arr,head,headDist);
          }else{
            ArrCopy.semicheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
            arr[arrBound]=arr[0];
            root.tail=fragmentedPullDown(arr,arrBound,tail);
            this.cursor=lastRet;
          }
        }else{
          if((tailDist=tail-lastRet)<=(headDist=arrBound-head)+lastRet){
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
      void nonfragmentedRemove(int head,int lastRet,int tail,Checked root){
        final int headDist,tailDist;
        if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
          ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
          root.tail=tail-1;
          this.cursor=lastRet;
        }else{
          root.head=pullUp(root.arr,head,headDist);
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
              fragmentedRemove(head,lastRet,tail,root);
              break;
            case 0:
              root.tail=-1;
              break;
            default:
              nonfragmentedRemove(head,lastRet,tail,root);
          }
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override void uncheckedForEachRemaining(int cursor,FloatConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int tail=(root=this.root).tail;
        try{
          final var arr=root.arr;
          if(cursor>tail){
            OmniArray.OfFloat.ascendingForEach(arr,cursor,arr.length-1,action);
            cursor=0;
          }
          OmniArray.OfFloat.ascendingForEach(arr,cursor,tail,action);
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
      @Override public float nextFloat(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final float[] arr;
          final var ret=(float)(arr=root.arr)[cursor];
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
      @Override void fragmentedRemove(int head,int lastRet,int tail,Checked root){
        float[] arr;
        int headDist,tailDist,arrBound=(arr=root.arr).length;
        if((headDist=lastRet-head)>=0){
          if(headDist<=(tailDist=arrBound-lastRet)+tail){
            root.head=pullUp(arr,head,headDist);
            this.cursor=lastRet;
          }else{
            ArrCopy.semicheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
            arr[arrBound]=arr[0];
            root.tail=fragmentedPullDown(arr,arrBound,tail);
          }
        }else{
          if((tailDist=tail-lastRet)<=(headDist=arrBound-head)+lastRet){
            ArrCopy.uncheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
            root.tail=tail-1;
          }else{
            ArrCopy.semicheckedCopy(arr,0,arr,1,lastRet);
            arr[0]=arr[arrBound];
            root.head=fragmentedPullUp(arr,head,headDist);
            this.cursor=lastRet;
          }
        }
      }
      @Override void nonfragmentedRemove(int head,int lastRet,int tail,Checked root){
        final int headDist,tailDist;
        if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
          ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
          root.tail=tail-1;
        }else{
          root.head=pullUp(root.arr,head,headDist);
          this.cursor=lastRet;
        }
      }
      @Override void uncheckedForEachRemaining(int cursor,FloatConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int head=(root=this.root).head;
        try{
          final var arr=root.arr;
          if(cursor<head){
            OmniArray.OfFloat.descendingForEach(arr,0,cursor,action);
            cursor=arr.length-1;
          }
          OmniArray.OfFloat.descendingForEach(arr,head,cursor,action);
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
        final float[] dst;
        int size,head;
        Checked clone;
        if((size=(++tail)-(head=this.head))<=0){
          clone=new Checked(0,dst=new float[size+=arr.length],size-1);
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
        }else{
          clone=new Checked(0,dst=new float[size],size-1);
        }
        ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        return clone;
      }
      return new Checked();
    }
    @Override public float removeLastFloat(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final float[] arr;
        var ret=(float)((arr=this.arr)[tail]);
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
    @Override public float popFloat(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final float[] arr;
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
    @Override public void push(float val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void addLast(float val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override void uncheckedForEach(final int tail,FloatConsumer action){
      final int modCount=this.modCount;
      try{
        super.uncheckedForEach(tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public float floatElement(){
      if(tail!=-1){
        return (float)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override public float getLastFloat(){
      final int tail;
      if((tail=this.tail)!=-1){
        return (float)arr[tail];
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
    @Override public float pollFloat(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final float[] arr;
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
        final float[] arr;
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
    @Override public Float poll(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final float[] arr;
        int head;
        var ret=(Float)((arr=this.arr)[head=this.head]);
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
    @Override public Float pollLast(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final float[] arr;
        var ret=(Float)((arr=this.arr)[tail]);
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
        final float[] arr;
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
        final float[] arr;
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
    @Override
    boolean uncheckedremoveValBits(int tail
    ,int bits
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(index=head,bound=arr.length-1;;++index){
            if(bits==Float.floatToRawIntBits(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
                if(bits==Float.floatToRawIntBits(arr[index])){
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
            }
          }
        }else{
          for(index=head;;++index){
            if(bits==Float.floatToRawIntBits(arr[index])){
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
              if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
            if(Float.isNaN(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
                if(Float.isNaN(arr[index])){
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
            }
          }
        }else{
          for(index=head;;++index){
            if(Float.isNaN(arr[index])){
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
    ,int bits
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(bound=arr.length-1,index=tail;;--index){
            if(bits==Float.floatToRawIntBits(arr[index])){
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
                if(bits==Float.floatToRawIntBits(arr[index])){
                  this.modCount=modCount+1;
                  int headDist,tailDist;
                  if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
            }
          }
        }else{
          for(index=tail;;--index){
            if(bits==Float.floatToRawIntBits(arr[index])){
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
                  if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
            if(Float.isNaN(arr[index])){
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
                if(Float.isNaN(arr[index])){
                  this.modCount=modCount+1;
                  int headDist,tailDist;
                  if((headDist=index-head)<(tailDist=(bound-index)+tail)){
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
            }
          }
        }else{
          for(index=tail;;--index){
            if(Float.isNaN(arr[index])){
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
