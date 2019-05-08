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
import java.util.ConcurrentModificationException;
import omni.function.FloatPredicate;
import omni.function.FloatConsumer;
import omni.util.ToStringUtil;
import omni.impl.AbstractFloatItr;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
public class FloatArrDeq implements OmniDeque.OfFloat{
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
  boolean fragmentedRemoveIf(int head,int tail,FloatPredicate filter){
    //TODO
    return false;
  }
  boolean nonfragmentedRemoveIf(int head,int tail,FloatPredicate filter){
    //TODO
    return false;
  }
  @Override public OmniIterator.OfFloat iterator(){
    //TODO
    return null;
  }
  @Override public OmniIterator.OfFloat descendingIterator(){
    //TODO
    return null;
  }
  @Override public Object clone(){
    //TODO
    return null;
  }
  @Override public String toString(){
    //TODO
    return null;
  }
  @Override public int hashCode(){
    //TODO
    return 0;
  }
  @Override public boolean equals(Object obj){
    //TODO
    return false;
  }
  @Override public void push(float val){
    //TODO
  }
  @Override public float popFloat(){
    //TODO
    return Float.NaN;
  }
  @Override public float removeLastFloat(){
    //TODO
    return Float.NaN;
  }
  @Override public boolean add(float val){
    addLast(val);
    return true;
  }
  @Override public void addLast(float val){
    //TODO
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
  public static class Checked extends FloatArrDeq{
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
    @Override public float popFloat(){
      //TODO
      return Float.NaN;
    }
    @Override public float removeLastFloat(){
      //TODO
      return Float.NaN;
    }
    @Override void uncheckedForEach(final int tail,FloatConsumer action){
      final int modCount=this.modCount;
      try{
        super.uncheckedForEach(tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override boolean fragmentedRemoveIf(int head,int tail,FloatPredicate filter){
      //TODO
      return false;
    }
    @Override boolean nonfragmentedRemoveIf(int head,int tail,FloatPredicate filter){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfFloat iterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfFloat descendingIterator(){
      //TODO
      return null;
    }
    @Override public Object clone(){
      //TODO
      return null;
    }
    @Override public boolean equals(Object obj){
      //TODO
      return false;
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
