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
import omni.function.BooleanPredicate;
import omni.function.BooleanConsumer;
import omni.util.ToStringUtil;
import omni.impl.AbstractBooleanItr;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.util.RandomAccess;
public class BooleanArrDeq implements OmniDeque.OfBoolean,Externalizable,Cloneable,RandomAccess{
  private static final long serialVersionUID=1L;
  transient boolean[] arr;
  transient int head;
  transient int tail;
  public BooleanArrDeq(){
    super();
    this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
    this.tail=-1;
  }
  public BooleanArrDeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new boolean[initialCapacity];
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
    case 0:
    }
    this.tail=-1;
  }
  BooleanArrDeq(int head,boolean[] arr,int tail){
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
  @Override public void forEach(BooleanConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action);
    }
  }
  @Override public boolean removeIf(BooleanPredicate filter){
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
  void uncheckedForEach(final int tail,BooleanConsumer action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      OmniArray.OfBoolean.ascendingForEach(arr,head,arr.length-1,action);
      head=0;
    }
    OmniArray.OfBoolean.ascendingForEach(arr,head,tail,action);
  }
  @Override public boolean add(boolean val){
    addLast(val);
    return true;
  }
  @Override public void addFirst(boolean val){
    push(val);
  }
  @Override public boolean offerFirst(boolean val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(boolean val){
    addLast(val);
    return true;
  }
  @Override public boolean booleanElement(){
    return (boolean)arr[head];
  }
  @Override public boolean getLastBoolean(){
    return (boolean)arr[tail];
  }
  @Override public boolean offer(boolean val){
    addLast(val);
    return true;
  }
  @Override public boolean contains(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean contains(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(val){
          default:
            break returnFalse;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
          }
          return uncheckedcontains(tail,v);
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
        returnFalse:for(;;){
          final boolean v;
          if(val==0L){
            v=false;
          }else if(val==1L){
            v=true;
          }else{
            break returnFalse;
          }
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
        returnFalse:for(;;){
          final boolean v;
          switch(Float.floatToRawIntBits(val)){
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
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
        returnFalse:for(;;){
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
            v=false;
          }else if(bits==TypeUtil.DBL_TRUE_BITS){
            v=true;
          }else{
            break returnFalse;
          }
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
          final boolean b;
          if(val instanceof Boolean){
            b=(boolean)val;
          }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            switch(((Number)val).intValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else if(val instanceof Float){
            switch(Float.floatToRawIntBits((float)val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                b=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                b=true;
            }
          }else if(val instanceof Double){
            final long bits;
            if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
              b=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              b=true;
            }else{
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long v;
            if((v=(long)val)==0L){
              b=false;
            }else if(v==1L){
              b=true;
            }else{
             break returnFalse;
            }
          }else if(val instanceof Character){
            switch(((Character)val).charValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else{
            break returnFalse;
          }
          return uncheckedcontains(tail,b);
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(val){
          default:
            break returnFalse;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
          }
          return uncheckedremoveVal(tail,v);
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
        returnFalse:for(;;){
          final boolean v;
          if(val==0L){
            v=false;
          }else if(val==1L){
            v=true;
          }else{
            break returnFalse;
          }
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
        returnFalse:for(;;){
          final boolean v;
          switch(Float.floatToRawIntBits(val)){
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
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
        returnFalse:for(;;){
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
            v=false;
          }else if(bits==TypeUtil.DBL_TRUE_BITS){
            v=true;
          }else{
            break returnFalse;
          }
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
          final boolean b;
          if(val instanceof Boolean){
            b=(boolean)val;
          }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            switch(((Number)val).intValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else if(val instanceof Float){
            switch(Float.floatToRawIntBits((float)val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                b=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                b=true;
            }
          }else if(val instanceof Double){
            final long bits;
            if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
              b=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              b=true;
            }else{
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long v;
            if((v=(long)val)==0L){
              b=false;
            }else if(v==1L){
              b=true;
            }else{
             break returnFalse;
            }
          }else if(val instanceof Character){
            switch(((Character)val).charValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else{
            break returnFalse;
          }
          return uncheckedremoveVal(tail,b);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(val){
          default:
            break returnFalse;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
          }
          return uncheckedremoveLastOccurrence(tail,v);
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
        returnFalse:for(;;){
          final boolean v;
          if(val==0L){
            v=false;
          }else if(val==1L){
            v=true;
          }else{
            break returnFalse;
          }
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
        returnFalse:for(;;){
          final boolean v;
          switch(Float.floatToRawIntBits(val)){
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
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
        returnFalse:for(;;){
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
            v=false;
          }else if(bits==TypeUtil.DBL_TRUE_BITS){
            v=true;
          }else{
            break returnFalse;
          }
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
          final boolean b;
          if(val instanceof Boolean){
            b=(boolean)val;
          }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            switch(((Number)val).intValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else if(val instanceof Float){
            switch(Float.floatToRawIntBits((float)val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                b=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                b=true;
            }
          }else if(val instanceof Double){
            final long bits;
            if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
              b=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              b=true;
            }else{
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long v;
            if((v=(long)val)==0L){
              b=false;
            }else if(v==1L){
              b=true;
            }else{
             break returnFalse;
            }
          }else if(val instanceof Character){
            switch(((Character)val).charValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else{
            break returnFalse;
          }
          return uncheckedremoveLastOccurrence(tail,b);
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
        return uncheckedsearch(tail,(val));
      }
    }
    return -1;
  }
  @Override public int search(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(val){
          default:
            break returnFalse;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
          }
          return uncheckedsearch(tail,v);
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
        returnFalse:for(;;){
          final boolean v;
          if(val==0L){
            v=false;
          }else if(val==1L){
            v=true;
          }else{
            break returnFalse;
          }
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
        returnFalse:for(;;){
          final boolean v;
          switch(Float.floatToRawIntBits(val)){
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
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
        returnFalse:for(;;){
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
            v=false;
          }else if(bits==TypeUtil.DBL_TRUE_BITS){
            v=true;
          }else{
            break returnFalse;
          }
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
          final boolean b;
          if(val instanceof Boolean){
            b=(boolean)val;
          }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            switch(((Number)val).intValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else if(val instanceof Float){
            switch(Float.floatToRawIntBits((float)val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                b=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                b=true;
            }
          }else if(val instanceof Double){
            final long bits;
            if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
              b=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              b=true;
            }else{
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long v;
            if((v=(long)val)==0L){
              b=false;
            }else if(v==1L){
              b=true;
            }else{
             break returnFalse;
            }
          }else if(val instanceof Character){
            switch(((Character)val).charValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else{
            break returnFalse;
          }
          return uncheckedsearch(tail,b);
        }
      }
    }
    return -1;
  }
  @Override public boolean[] toBooleanArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final boolean[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new boolean[size+=arr.length],size-=tail,tail);
      }else{
        dst=new boolean[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override public Boolean[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final Boolean[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new Boolean[size+=arr.length],size-=tail,tail);
      }else{
        dst=new Boolean[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
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
  @Override public short[] toShortArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final short[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new short[size+=arr.length],size-=tail,tail);
      }else{
        dst=new short[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public byte[] toByteArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final byte[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new byte[size+=arr.length],size-=tail,tail);
      }else{
        dst=new byte[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public char[] toCharArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final char[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new char[size+=arr.length],size-=tail,tail);
      }else{
        dst=new char[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public boolean peekBoolean(){
    if(tail!=-1){
      return (boolean)(arr[head]);
    }
    return false;
  }
  @Override public boolean peekLastBoolean(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (boolean)(arr[tail]);
    }
    return false;
  }
  @Override public Boolean peek(){
    if(tail!=-1){
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
  @Override public double peekDouble(){
    if(tail!=-1){
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
  @Override public float peekFloat(){
    if(tail!=-1){
      return TypeUtil.castToFloat(arr[head]);
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
  @Override public long peekLong(){
    if(tail!=-1){
      return TypeUtil.castToLong(arr[head]);
    }
    return Long.MIN_VALUE;
  }
  @Override public long peekLastLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToLong(arr[tail]);
    }
    return Long.MIN_VALUE;
  }
  @Override public int peekInt(){
    if(tail!=-1){
      return (int)TypeUtil.castToByte(arr[head]);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int peekLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)TypeUtil.castToByte(arr[tail]);
    }
    return Integer.MIN_VALUE;
  }
  @Override public short peekShort(){
    if(tail!=-1){
      return (short)TypeUtil.castToByte(arr[head]);
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
  @Override public byte peekByte(){
    if(tail!=-1){
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
  @Override public char peekChar(){
    if(tail!=-1){
      return TypeUtil.castToChar(arr[head]);
    }
    return Character.MIN_VALUE;
  }
  @Override public char peekLastChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToChar(arr[tail]);
    }
    return Character.MIN_VALUE;
  }
  @Override public boolean pollBoolean(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      int head;
      var ret=(boolean)((arr=this.arr)[head=this.head]);
      if(head==tail){
        this.tail=-1;
        return ret;
      }else if(++head==arr.length){
        head=0;
      }
      this.head=head;
      return ret;
    }
    return false;
  }
  @Override public boolean pollLastBoolean(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      var ret=(boolean)((arr=this.arr)[tail]);
      if(this.head==tail){
        tail=-1;
      }else if(--tail==-1){
        tail=arr.length-1;
      }
      this.tail=tail;
      return ret;
    }
    return false;
  }
  @Override public Boolean poll(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      int head;
      var ret=(Boolean)((arr=this.arr)[head=this.head]);
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
  @Override public Boolean pollLast(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      var ret=(Boolean)((arr=this.arr)[tail]);
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
      final boolean[] arr;
      int head;
      var ret=TypeUtil.castToDouble((arr=this.arr)[head=this.head]);
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
      final boolean[] arr;
      var ret=TypeUtil.castToDouble((arr=this.arr)[tail]);
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
      final boolean[] arr;
      int head;
      var ret=TypeUtil.castToFloat((arr=this.arr)[head=this.head]);
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
      final boolean[] arr;
      var ret=TypeUtil.castToFloat((arr=this.arr)[tail]);
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
      final boolean[] arr;
      int head;
      var ret=TypeUtil.castToLong((arr=this.arr)[head=this.head]);
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
      final boolean[] arr;
      var ret=TypeUtil.castToLong((arr=this.arr)[tail]);
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
  @Override public int pollInt(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      int head;
      var ret=(int)TypeUtil.castToByte((arr=this.arr)[head=this.head]);
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
      final boolean[] arr;
      var ret=(int)TypeUtil.castToByte((arr=this.arr)[tail]);
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
  @Override public short pollShort(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      int head;
      var ret=(short)TypeUtil.castToByte((arr=this.arr)[head=this.head]);
      if(head==tail){
        this.tail=-1;
        return ret;
      }else if(++head==arr.length){
        head=0;
      }
      this.head=head;
      return ret;
    }
    return Short.MIN_VALUE;
  }
  @Override public short pollLastShort(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      var ret=(short)TypeUtil.castToByte((arr=this.arr)[tail]);
      if(this.head==tail){
        tail=-1;
      }else if(--tail==-1){
        tail=arr.length-1;
      }
      this.tail=tail;
      return ret;
    }
    return Short.MIN_VALUE;
  }
  @Override public byte pollByte(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      int head;
      var ret=TypeUtil.castToByte((arr=this.arr)[head=this.head]);
      if(head==tail){
        this.tail=-1;
        return ret;
      }else if(++head==arr.length){
        head=0;
      }
      this.head=head;
      return ret;
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte pollLastByte(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      var ret=TypeUtil.castToByte((arr=this.arr)[tail]);
      if(this.head==tail){
        tail=-1;
      }else if(--tail==-1){
        tail=arr.length-1;
      }
      this.tail=tail;
      return ret;
    }
    return Byte.MIN_VALUE;
  }
  @Override public char pollChar(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      int head;
      var ret=TypeUtil.castToChar((arr=this.arr)[head=this.head]);
      if(head==tail){
        this.tail=-1;
        return ret;
      }else if(++head==arr.length){
        head=0;
      }
      this.head=head;
      return ret;
    }
    return Character.MIN_VALUE;
  }
  @Override public char pollLastChar(){
    int tail;
    if((tail=this.tail)!=-1){
      final boolean[] arr;
      var ret=TypeUtil.castToChar((arr=this.arr)[tail]);
      if(this.head==tail){
        tail=-1;
      }else if(--tail==-1){
        tail=arr.length-1;
      }
      this.tail=tail;
      return ret;
    }
    return Character.MIN_VALUE;
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
  @Override public Boolean getFirst(){
    return booleanElement();
  }
  @Override public Boolean peekFirst(){
    return peekBoolean();
  }
  @Override public Boolean pollFirst(){
    return pollBoolean();
  }
  @Override public Boolean removeFirst(){
    return popBoolean();
  }
  @Override public Boolean remove(){
    return popBoolean();
  }
  @Override public boolean removeFirstOccurrence(Object val){
    return remove(val);
  }
  @Override public Boolean pop(){
    return popBoolean();
  }
  @Override public Boolean removeLast(){
    return removeLastBoolean();
  }
  @Override public void push(Boolean val){
    push((boolean)val);
  }
  @Override public boolean offer(Boolean val){
    addLast((boolean)val);
    return true;
  }
  @Override public Boolean element(){
    return booleanElement();
  }
  @Override public Boolean getLast(){
    return getLastBoolean();
  }
  @Override public void addFirst(Boolean val){
    push((boolean)val);
  }
  @Override public void addLast(Boolean val){
    addLast((boolean)val);
  }
  @Override public boolean add(Boolean val){
    addLast((boolean)val);
    return true;
  }
  @Override public boolean offerFirst(Boolean val){
    push((boolean)val);
    return true;
  }
  @Override public boolean offerLast(Boolean val){
    addLast((boolean)val);
    return true;
  }
  boolean uncheckedremoveVal(int tail
  ,boolean val
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(index=head,bound=arr.length-1;;++index){
          if(val==(arr[index])){
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
  ,boolean val
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
  ,boolean val
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfBoolean.uncheckedcontains (arr,0,tail,val) || OmniArray.OfBoolean.uncheckedcontains (arr,head,arr.length-1,val);
    }
    return OmniArray.OfBoolean.uncheckedcontains (arr,head,tail,val);
  }
  private int uncheckedsearch (int tail
  ,boolean val
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
  @Override public void forEach(Consumer<? super Boolean> action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override public boolean removeIf(Predicate<? super Boolean> filter){
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
  @Override public boolean popBoolean(){
    final boolean[] arr;
    int head;
    var ret=(boolean)((arr=this.arr)[head=this.head]);
    if(head==this.tail){
      this.tail=-1;
      return ret;
    }else if(++head==arr.length){
      head=0;
    }
    this.head=head;
    return ret;
  }
  @Override public boolean removeLastBoolean(){
    final boolean[] arr;
    int tail;
    var ret=(boolean)((arr=this.arr)[tail=this.tail]);
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
      final boolean[] dst;
      int size,head;
      BooleanArrDeq clone;
      if((size=(++tail)-(head=this.head))<=0){
        clone=new BooleanArrDeq(0,dst=new boolean[size+=arr.length],size-1);
        ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
      }else{
        clone=new BooleanArrDeq(0,dst=new boolean[size],size-1);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return clone;
    }
    return new BooleanArrDeq();
  }
  @Override public boolean equals(Object obj){
    //TODO
    return false;
  }
  boolean fragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
    //TODO
    return false;
  }
  boolean nonfragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
    //TODO
    return false;
  }
  @Override public OmniIterator.OfBoolean iterator(){
    //TODO
    return null;
  }
  @Override public OmniIterator.OfBoolean descendingIterator(){
    //TODO
    return null;
  }
  private String uncheckedToString(int tail){
    //TODO
    return null;
  }
  private int uncheckedHashCode(int tail){
    //TODO
    return -1;
  }
  @Override public void push(boolean val){
    boolean[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfBoolean.DEFAULT_ARR){
        this.head=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.tail=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
        arr[OmniArray.DEFAULT_ARR_SEQ_CAP-1]=val;
      }else{
        //TODO
      }
    }else{
      this.head=0;
      this.tail=0;
      this.arr=new boolean[]{val};
    }
  }
  @Override public void addLast(boolean val){
    //TODO
  }
  @Override public void readExternal(ObjectInput input) throws IOException
  {
    //TODO
  }
  @Override public void writeExternal(ObjectOutput output) throws IOException{
    //TODO
  }
  public static class Checked extends BooleanArrDeq{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(int initialCapacity){
      super(initialCapacity);
    }
    Checked(int head,boolean[] arr,int tail){
      super(head,arr,tail);
    }
    @Override boolean fragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
      //TODO
      return false;
    }
    @Override boolean nonfragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      return null;
    }
    @Override public boolean equals(Object obj){
      //TODO
      return false;
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final var arr=this.arr;
        final boolean[] dst;
        int size,head;
        Checked clone;
        if((size=(++tail)-(head=this.head))<=0){
          clone=new Checked(0,dst=new boolean[size+=arr.length],size-1);
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
        }else{
          clone=new Checked(0,dst=new boolean[size],size-1);
        }
        ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        return clone;
      }
      return new Checked();
    }
    @Override public boolean removeLastBoolean(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        var ret=(boolean)((arr=this.arr)[tail]);
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
    @Override public boolean popBoolean(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        int head;
        var ret=(boolean)((arr=this.arr)[head=this.head]);
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
    @Override public void push(boolean val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void addLast(boolean val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override void uncheckedForEach(final int tail,BooleanConsumer action){
      final int modCount=this.modCount;
      try{
        super.uncheckedForEach(tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public boolean booleanElement(){
      if(tail!=-1){
        return (boolean)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override public boolean getLastBoolean(){
      final int tail;
      if((tail=this.tail)!=-1){
        return (boolean)arr[tail];
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
    @Override public boolean pollBoolean(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        int head;
        var ret=(boolean)((arr=this.arr)[head=this.head]);
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      return false;
    }
    @Override public boolean pollLastBoolean(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        var ret=(boolean)((arr=this.arr)[tail]);
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      return false;
    }
    @Override public Boolean poll(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        int head;
        var ret=(Boolean)((arr=this.arr)[head=this.head]);
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
    @Override public Boolean pollLast(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        var ret=(Boolean)((arr=this.arr)[tail]);
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
        final boolean[] arr;
        int head;
        var ret=TypeUtil.castToDouble((arr=this.arr)[head=this.head]);
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
        final boolean[] arr;
        var ret=TypeUtil.castToDouble((arr=this.arr)[tail]);
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
        final boolean[] arr;
        int head;
        var ret=TypeUtil.castToFloat((arr=this.arr)[head=this.head]);
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
        final boolean[] arr;
        var ret=TypeUtil.castToFloat((arr=this.arr)[tail]);
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
        final boolean[] arr;
        int head;
        var ret=TypeUtil.castToLong((arr=this.arr)[head=this.head]);
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
        final boolean[] arr;
        var ret=TypeUtil.castToLong((arr=this.arr)[tail]);
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
    @Override public int pollInt(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        int head;
        var ret=(int)TypeUtil.castToByte((arr=this.arr)[head=this.head]);
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
        final boolean[] arr;
        var ret=(int)TypeUtil.castToByte((arr=this.arr)[tail]);
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
    @Override public short pollShort(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        int head;
        var ret=(short)TypeUtil.castToByte((arr=this.arr)[head=this.head]);
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        var ret=(short)TypeUtil.castToByte((arr=this.arr)[tail]);
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte pollByte(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        int head;
        var ret=TypeUtil.castToByte((arr=this.arr)[head=this.head]);
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        var ret=TypeUtil.castToByte((arr=this.arr)[tail]);
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        int head;
        var ret=TypeUtil.castToChar((arr=this.arr)[head=this.head]);
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final boolean[] arr;
        var ret=TypeUtil.castToChar((arr=this.arr)[tail]);
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override
    boolean uncheckedremoveVal(int tail
    ,boolean val
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
    ,boolean val
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
