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
import omni.function.BytePredicate;
import omni.function.ByteConsumer;
import omni.util.ToStringUtil;
import omni.impl.AbstractByteItr;
import java.util.ConcurrentModificationException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.util.RandomAccess;
public class ByteArrDeq implements OmniDeque.OfByte,Externalizable,Cloneable,RandomAccess{
  private static final long serialVersionUID=1L;
  transient byte[] arr;
  transient int head;
  transient int tail;
  public ByteArrDeq(){
    super();
    this.arr=OmniArray.OfByte.DEFAULT_ARR;
    this.tail=-1;
  }
  public ByteArrDeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new byte[initialCapacity];
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfByte.DEFAULT_ARR;
    case 0:
    }
    this.tail=-1;
  }
  ByteArrDeq(int head,byte[] arr,int tail){
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
  @Override public void forEach(ByteConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action);
    }
  }
  @Override public boolean removeIf(BytePredicate filter){
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
  void uncheckedForEach(final int tail,ByteConsumer action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      OmniArray.OfByte.ascendingForEach(arr,head,arr.length-1,action);
      head=0;
    }
    OmniArray.OfByte.ascendingForEach(arr,head,tail,action);
  }
  @Override public boolean add(byte val){
    addLast(val);
    return true;
  }
  @Override public void addFirst(byte val){
    push(val);
  }
  @Override public boolean offerFirst(byte val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(byte val){
    addLast(val);
    return true;
  }
  @Override public byte byteElement(){
    return (byte)arr[head];
  }
  @Override public byte getLastByte(){
    return (byte)arr[tail];
  }
  @Override public boolean offer(byte val){
    addLast(val);
    return true;
  }
  @Override public boolean contains(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,TypeUtil.castToByte(val));
      }
    }
    return false;
  }
  @Override public boolean contains(int val)
  {
    if(val==(byte)val)
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean contains(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final byte v;
        if((v=(byte)val)==val){
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
        final byte v;
        if(val==(v=(byte)val))
        {
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
        final byte v;
        if(val==(v=(byte)val))
        {
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
          final int i;
          if(val instanceof Byte){
            i=(byte)val;
          }else if(val instanceof Integer||val instanceof Short){
            if((i=((Number)val).intValue())!=(byte)i){
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(i=(byte)l)){
              break returnFalse;
            }
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)!=(i=(byte)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(i=(byte)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            if((i=(char)val)>Byte.MAX_VALUE){
              break returnFalse;
            }
          }else if(val instanceof Boolean){
            i=TypeUtil.castToByte((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedcontains(tail,i);
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
        return uncheckedcontains(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean contains(char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,TypeUtil.castToByte(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(int val)
  {
    if(val==(byte)val)
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final byte v;
        if((v=(byte)val)==val){
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
        final byte v;
        if(val==(v=(byte)val))
        {
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
        final byte v;
        if(val==(v=(byte)val))
        {
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
          final int i;
          if(val instanceof Byte){
            i=(byte)val;
          }else if(val instanceof Integer||val instanceof Short){
            if((i=((Number)val).intValue())!=(byte)i){
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(i=(byte)l)){
              break returnFalse;
            }
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)!=(i=(byte)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(i=(byte)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            if((i=(char)val)>Byte.MAX_VALUE){
              break returnFalse;
            }
          }else if(val instanceof Boolean){
            i=TypeUtil.castToByte((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedremoveVal(tail,i);
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
        return uncheckedremoveVal(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,TypeUtil.castToByte(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(int val)
  {
    if(val==(byte)val)
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final byte v;
        if((v=(byte)val)==val){
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
        final byte v;
        if(val==(v=(byte)val))
        {
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
        final byte v;
        if(val==(v=(byte)val))
        {
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
          final int i;
          if(val instanceof Byte){
            i=(byte)val;
          }else if(val instanceof Integer||val instanceof Short){
            if((i=((Number)val).intValue())!=(byte)i){
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(i=(byte)l)){
              break returnFalse;
            }
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)!=(i=(byte)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(i=(byte)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            if((i=(char)val)>Byte.MAX_VALUE){
              break returnFalse;
            }
          }else if(val instanceof Boolean){
            i=TypeUtil.castToByte((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedremoveLastOccurrence(tail,i);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,(val));
      }
    }
    return false;
  }
  @Override public int search(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,TypeUtil.castToByte(val));
      }
    }
    return -1;
  }
  @Override public int search(int val)
  {
    if(val==(byte)val)
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,(val));
      }
    }
    return -1;
  }
  @Override public int search(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        final byte v;
        if((v=(byte)val)==val){
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
        final byte v;
        if(val==(v=(byte)val))
        {
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
        final byte v;
        if(val==(v=(byte)val))
        {
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
          final int i;
          if(val instanceof Byte){
            i=(byte)val;
          }else if(val instanceof Integer||val instanceof Short){
            if((i=((Number)val).intValue())!=(byte)i){
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(i=(byte)l)){
              break returnFalse;
            }
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)!=(i=(byte)f)){
              break returnFalse;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(i=(byte)d)){
              break returnFalse;
            }
          }else if(val instanceof Character){
            if((i=(char)val)>Byte.MAX_VALUE){
              break returnFalse;
            }
          }else if(val instanceof Boolean){
            i=TypeUtil.castToByte((boolean)val);
          }else{
            break returnFalse;
          }
          return uncheckedsearch(tail,i);
        }
      }
    }
    return -1;
  }
  @Override public int search(byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,(val));
      }
    }
    return -1;
  }
  @Override public int search(char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,(val));
      }
    }
    return -1;
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
  @Override public Byte[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final Byte[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new Byte[size+=arr.length],size-=tail,tail);
      }else{
        dst=new Byte[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
  @Override public byte peekByte(){
    if(tail!=-1){
      return (byte)(arr[head]);
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte peekLastByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (byte)(arr[tail]);
    }
    return Byte.MIN_VALUE;
  }
  @Override public Byte peek(){
    if(tail!=-1){
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
  @Override public long peekLong(){
    if(tail!=-1){
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
    if(tail!=-1){
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
    if(tail!=-1){
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
    int tail;
    if((tail=this.tail)!=-1){
      final byte[] arr;
      int head;
      var ret=(byte)((arr=this.arr)[head=this.head]);
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
      final byte[] arr;
      var ret=(byte)((arr=this.arr)[tail]);
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
  @Override public Byte poll(){
    int tail;
    if((tail=this.tail)!=-1){
      final byte[] arr;
      int head;
      var ret=(Byte)((arr=this.arr)[head=this.head]);
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
  @Override public Byte pollLast(){
    int tail;
    if((tail=this.tail)!=-1){
      final byte[] arr;
      var ret=(Byte)((arr=this.arr)[tail]);
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
      final byte[] arr;
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
      final byte[] arr;
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
  @Override public float pollFloat(){
    int tail;
    if((tail=this.tail)!=-1){
      final byte[] arr;
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
      final byte[] arr;
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
  @Override public long pollLong(){
    int tail;
    if((tail=this.tail)!=-1){
      final byte[] arr;
      int head;
      var ret=(long)((arr=this.arr)[head=this.head]);
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
      final byte[] arr;
      var ret=(long)((arr=this.arr)[tail]);
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
      final byte[] arr;
      int head;
      var ret=(int)((arr=this.arr)[head=this.head]);
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
      final byte[] arr;
      var ret=(int)((arr=this.arr)[tail]);
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
      final byte[] arr;
      int head;
      var ret=(short)((arr=this.arr)[head=this.head]);
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
      final byte[] arr;
      var ret=(short)((arr=this.arr)[tail]);
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
    addLast((byte)TypeUtil.castToByte(val));
    return true;
  }
  @Override public Byte getFirst(){
    return byteElement();
  }
  @Override public Byte peekFirst(){
    return peek();
  }
  @Override public Byte pollFirst(){
    return poll();
  }
  @Override public Byte removeFirst(){
    return popByte();
  }
  @Override public Byte remove(){
    return popByte();
  }
  @Override public boolean removeFirstOccurrence(Object val){
    return remove(val);
  }
  @Override public Byte pop(){
    return popByte();
  }
  @Override public Byte removeLast(){
    return removeLastByte();
  }
  @Override public void push(Byte val){
    push((byte)val);
  }
  @Override public boolean offer(Byte val){
    addLast((byte)val);
    return true;
  }
  @Override public Byte element(){
    return byteElement();
  }
  @Override public Byte getLast(){
    return getLastByte();
  }
  @Override public void addFirst(Byte val){
    push((byte)val);
  }
  @Override public void addLast(Byte val){
    addLast((byte)val);
  }
  @Override public boolean add(Byte val){
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
  boolean uncheckedremoveVal(int tail
  ,int val
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(index=head,bound=arr.length-1;;++index){
          if(val==(arr[index])){
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
            break;
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
  ,int val
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
  ,int val
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfByte.uncheckedcontains (arr,0,tail,val) || OmniArray.OfByte.uncheckedcontains (arr,head,arr.length-1,val);
    }
    return OmniArray.OfByte.uncheckedcontains (arr,head,tail,val);
  }
  private int uncheckedsearch (int tail
  ,int val
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
          break;
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
  @Override public void forEach(Consumer<? super Byte> action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override public boolean removeIf(Predicate<? super Byte> filter){
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
  @Override public byte popByte(){
    final byte[] arr;
    int head;
    var ret=(byte)((arr=this.arr)[head=this.head]);
    if(head==this.tail){
      this.tail=-1;
      return ret;
    }else if(++head==arr.length){
      head=0;
    }
    this.head=head;
    return ret;
  }
  @Override public byte removeLastByte(){
    final byte[] arr;
    int tail;
    var ret=(byte)((arr=this.arr)[tail=this.tail]);
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
      final byte[] dst;
      int size,head;
      ByteArrDeq clone;
      if((size=(++tail)-(head=this.head))<=0){
        clone=new ByteArrDeq(0,dst=new byte[size+=arr.length],size-1);
        ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
      }else{
        clone=new ByteArrDeq(0,dst=new byte[size],size-1);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return clone;
    }
    return new ByteArrDeq();
  }
  private String uncheckedToString(int tail){
    final var arr=this.arr;
    final byte[] buffer;
    int size,head,bufferOffset=1;
    if((size=(++tail)-(head=this.head))<=0){
      int bound;
      if((size+=(bound=arr.length))<=(OmniArray.MAX_ARR_SIZE/6)){(buffer=new byte[size*6])
        [0]=(byte)'[';
        for(;;++bufferOffset){
          buffer[bufferOffset=ToStringUtil.getStringShort(arr[head],buffer,bufferOffset)]=(byte)',';
          buffer[++bufferOffset]=(byte)' ';
          if(++head==bound){
            for(head=0;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' '){
              bufferOffset=ToStringUtil.getStringShort(arr[head],buffer,++bufferOffset);
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
          builder.uncheckedAppendShort(arr[head]);
          builder.uncheckedAppendCommaAndSpace();
          if(++head==bound){
            for(head=0;;builder.uncheckedAppendCommaAndSpace()){
              builder.uncheckedAppendShort(arr[head]);
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
      if(size<=(OmniArray.MAX_ARR_SIZE/6)){(buffer=new byte[size*6])
        [size=OmniArray.OfByte.ascendingToString(arr,head,tail-1,buffer,1)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        OmniArray.OfByte.ascendingToString(arr,head,tail-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
  }
  private int uncheckedHashCode(int tail){
    final byte[] arr;
    int head;
    int hash=31+((arr=this.arr)[head=this.head]);
    if(tail<head){
      for(final int bound=arr.length;;){  
        if(++head==bound){
          hash=hash*31+(arr[head=0]);
          break;
        }
        hash=(hash*31)+(arr[head]);
      }
    }
    for(;head!=tail;hash=(hash*31)+(arr[++head])){}
    return hash;
  }
  @Override public void push(byte val){
    byte[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfByte.DEFAULT_ARR){
        this.head=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.tail=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.arr=arr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
            final byte[] newArr;
            int newCap,size;
            this.tail=(newCap=OmniArray.growBy50Pct(head+(size=arr.length)))-1;
            ArrCopy.uncheckedCopy(arr,0,newArr=new byte[newCap],newCap-=(++tail),tail);
            ArrCopy.uncheckedCopy(arr,head+1,newArr,head=newCap-(size-=tail),size);
            this.arr=arr=newArr;
            --head;
          }else if(head==-1 && tail==(head=arr.length-1)){
            int newCap;
            this.tail=(newCap=OmniArray.growBy50Pct(++tail))-1;
            ArrCopy.uncheckedCopy(arr,0,arr=new byte[newCap],head=newCap-tail,tail);
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
  private void initFromNullArr(byte val){
    this.head=0;
    this.tail=0;
    this.arr=new byte[]{val};
  }
  @Override public void addLast(byte val){
    byte[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfByte.DEFAULT_ARR){
        this.head=0;
        this.tail=0;
        this.arr=arr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
            final byte[] newArr;
            (newArr=new byte[OmniArray.growBy50Pct(tail=arr.length)])[tail]=val;
            this.tail=tail;
            ArrCopy.uncheckedCopy(arr,head,newArr,0,tail-=head);
            ArrCopy.uncheckedCopy(arr,0,newArr,tail,head);
            this.arr=newArr;
          }else{
            if(tail==arr.length){
              if(head==0){
                ArrCopy.uncheckedCopy(arr,0,arr=new byte[OmniArray.growBy50Pct(tail)],0,tail);
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
    extends AbstractByteItr
  {
    transient int cursor;
    AbstractDeqItr(int cursor){
      this.cursor=cursor;
    }
    @Override public boolean hasNext(){
      return this.cursor!=-1;
    }
    abstract void uncheckedForEachRemaining(int cursor,ByteConsumer action);
    @Override public void forEachRemaining(ByteConsumer action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action::accept);
      }
    }
    private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,ByteArrDeq root){
      int tailDist,headDist;
      if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
        ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
        root.tail=tail-1;
      }else{
        byte[] arr;
        ArrCopy.uncheckedCopy(arr=root.arr,head,arr,++head,headDist);
        root.head=head;
        this.cursor=lastRet;
      }
    }
  }
  private static int pullUp(byte[] arr,int head,int headDist){
    ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
    return head;
  }
  private static int fragmentedPullUp(byte[] arr,int head,int headDist){
    if(headDist==0){
      return 0;
    }else{
      ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
      return head;
    }
  }
  private static int fragmentedPullDown(byte[] arr,int arrBound,int tail){
    if(tail==0){
      return arrBound;
    }
    ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
    return tail-1;
  }
  private static class AscendingItr extends AbstractDeqItr
  {
    transient final ByteArrDeq root;
    private AscendingItr(ByteArrDeq root){
      super(root.tail!=-1?root.head:-1);
      this.root=root;
    }
    private AscendingItr(ByteArrDeq root,int cursor){
      super(cursor);
      this.root=root;
    }
    @Override public byte nextByte(){
      final byte[] arr;
      int cursor;
      final ByteArrDeq root;
      final var ret=(byte)(arr=(root=this.root).arr)[cursor=this.cursor];
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
      final ByteArrDeq root;
      final byte[] arr;
      if((tail=(root=this.root).tail)<(headDist=(arrBound=(arr=root.arr).length-1)-(head=root.head))){
        arr[arrBound]=arr[0];
        root.tail=fragmentedPullDown(arr,arrBound,tail);
        this.cursor=arrBound;
      }else{
        root.head=fragmentedPullUp(arr,head,headDist);
      }
    }
    private void fragmentedAscendingRemove(int head,int lastRet,int tail,ByteArrDeq root){
      byte[] arr;
      int headDist,tailDist,arrBound=(arr=root.arr).length-1;
      if((headDist=lastRet-head)>=0){
        //index to remove is in head run
        if(headDist<=(tailDist=arrBound-lastRet)+tail){
          ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
          root.head=head;
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
    private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,ByteArrDeq root){
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
          final ByteArrDeq root;
          if((tail=(root=this.root).tail)<(head=root.head)){
            fragmentedAscendingRemove(head,cursor-1,tail,root);
          }else{
            nonfragmentedAscendingRemove(head,cursor-1,tail,root);
          }
      }
    }
    @Override void uncheckedForEachRemaining(int cursor,ByteConsumer action){
      final ByteArrDeq root;
      int tail;
      final var arr=(root=this.root).arr;
      if(cursor>(tail=root.tail)){
        OmniArray.OfByte.ascendingForEach(arr,cursor,arr.length-1,action);
        cursor=0;
      }
      OmniArray.OfByte.ascendingForEach(arr,cursor,tail,action);
      this.cursor=-1;
    }
  }
  private static class DescendingItr extends AscendingItr{
    private DescendingItr(ByteArrDeq root){
      super(root,root.tail);
    }
    @Override void uncheckedForEachRemaining(int cursor,ByteConsumer action){
      final ByteArrDeq root;
      final int head;
      final var arr=(root=this.root).arr;
      if(cursor<(head=root.head)){
        OmniArray.OfByte.descendingForEach(arr,0,cursor,action);
        cursor=arr.length-1;
      }
      OmniArray.OfByte.descendingForEach(arr,head,cursor,action);
      this.cursor=-1;
    }
    @Override public byte nextByte(){
      int cursor;
      final ByteArrDeq root;
      final var arr=(root=this.root).arr;
      this.cursor=(cursor=this.cursor)==root.head?-1:cursor==0?arr.length-1:cursor-1;
      return (byte)arr[cursor];
    }
    private void fragmentedDescendingRemove(int head,int cursor,int tail,ByteArrDeq root){
      byte[] arr;
      int arrBound;
      if((arrBound=(arr=root.arr).length-1)==cursor){
        if(tail<=(cursor=arrBound-head)){
          ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
          root.tail=tail-1;
        }else{
          arr[0]=arr[arrBound];
          root.head=pullUp(arr,head,cursor);
          this.cursor=0;
        }
      }else{
        int headDist,tailDist;
        if((headDist=(++cursor)-head)>=0){
          if(headDist<=(tailDist=arrBound-cursor)+tail){
            root.head=fragmentedPullUp(arr,head,headDist);
            this.cursor=cursor;
          }else{
            ArrCopy.semicheckedSelfCopy(arr,cursor,cursor+1,tailDist);
            arr[arrBound]=arr[0];
            root.tail=fragmentedPullDown(arr,arrBound,tail);
          }
        }else{
          if((tailDist=tail-cursor)<=(headDist=arrBound-head)+cursor){
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
    @Override public void remove(){
      int cursor;
      if((cursor=this.cursor)==-1){
        root.eraseHead();
      }else{
        ByteArrDeq root;
        int head,tail;
        if((tail=(root=this.root).tail)<(head=root.head)){
          fragmentedDescendingRemove(head,cursor,tail,root);
        }else{
          ((AbstractDeqItr)this).nonfragmentedDescendingRemove(head,cursor+1,tail,root);
        }
      }
    }
  } 
  @Override public OmniIterator.OfByte iterator(){
    return new AscendingItr(this);
  }
  @Override public OmniIterator.OfByte descendingIterator(){
    return new DescendingItr(this);
  }
  boolean fragmentedRemoveIf(int head,int tail,BytePredicate filter){
    byte[] arr;
    if(filter.test((byte)(arr=this.arr)[head]))
    {
      if(filter.test((byte)arr[tail]))
      {
        fragmentedCollapseHeadAndTail(arr,head,tail,filter);
      }
      else
      {
        fragmentedCollapsehead(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((byte)arr[tail]))
    {
      fragmentedCollapsetail(arr,head,tail,filter);
      return true;
    }
    return fragmentedCollapseBody(arr,head,tail,filter);
  }
  private static  int pullDown(byte[] arr,int dstOffset,int srcBound,BytePredicate filter){
    for(int srcOffset=dstOffset+1;srcOffset!=srcBound;++srcOffset)
    {
      final byte v;
      if(!filter.test((byte)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    return dstOffset;
  }
  private static  int pullUp(byte[] arr,int dstOffset,int srcBound,BytePredicate filter){
    for(int srcOffset=dstOffset-1;srcOffset!=srcBound;--srcOffset)
    {
      final byte v;
      if(!filter.test((byte)(v=arr[srcOffset])))
      {
        arr[dstOffset--]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    return dstOffset;
  }
  private void fragmentedCollapseBodyHelper(byte[] arr,int head,int tail,BytePredicate filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((byte)arr[srcOffset]))
      {
        tail=pullDown(arr,srcOffset,tail,filter);
        break;
      }
    }
    this.tail=tail;
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset)
    {
      if(filter.test((byte)arr[srcOffset]))
      {
        head=pullUp(arr,srcOffset,head,filter);
        break;
      }
    }
    this.head=head;
  }
  private void collapseBodyHelper(byte[] ar,int head,int tail,BytePredicate filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((byte)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((byte)arr[midPoint])){
            tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        this.tail=tail;
        return;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((byte)arr[midPoint])){
        tail=pullDown(arr,midPoint,tail,filter);
        break;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  private void fragmentedCollapseHeadAndTail(byte[] arr,int head,int tail,BytePredicate filter){
    outer:for(;;){
      do{
        if(tail==0){
          for(tail=arr.length-1;tail!=head;--tail){
            if(!filter.test((byte)arr[tail])){
              break outer;
            }
          }  
          this.tail=-1;
          return;
        }
      }while(filter.test((byte)arr[--tail]));
      for(int bound=arr.length;++head!=bound;){
        if(!filter.test((byte)arr[head])){
          fragmentedCollapseBodyHelper(arr,head,tail,filter);
          return;
        }
      }
      head=-1;
      break;
    }
    while(++head!=tail){
      if(!filter.test((byte)arr[head])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  private boolean fragmentedCollapseBody(byte[] arr,int head,int tail,BytePredicate filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset){
      if(filter.test((byte)arr[srcOffset])){
        this.tail=pullDown(arr,srcOffset,tail,filter);
        for(srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
          if(filter.test((byte)arr[srcOffset])){
            this.head=pullUp(arr,srcOffset,head,filter);
            break;
          }
        }
        return true;
      }
    }
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
      if(filter.test((byte)arr[srcOffset]))
      {
        this.head=pullUp(arr,srcOffset,head,filter);
        return true;
      }
    }
    return false;
  }
  private void fragmentedCollapsehead(byte[] arr,int head,int tail,BytePredicate filter){
    for(int bound=arr.length;;)
    {
      if(++head==bound){
        for(head=0;head!=tail;++head)
        {
          if(!filter.test((byte)arr[head]))
          {
            collapseBodyHelper(arr,head,tail,filter);
            break;
          }
        }
        return;
      }
      if(!filter.test((byte)arr[head]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  private void collapsehead(byte[] arr,int head,int tail,BytePredicate filter){
    do{
      if(++head==tail){
        this.head=head;
        return;
      }
    }while(filter.test((byte)arr[head]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  private void fragmentedCollapsetail(byte[] arr,int head,int tail,BytePredicate filter){
    for(;;)
    {
      if(tail==0){
        for(tail=arr.length-1;tail!=head;--tail)
        {
          if(!filter.test((byte)arr[tail]))
          {
            collapseBodyHelper(arr,head,tail,filter);
            break;
          }
        }
        return;
      }
      if(!filter.test((byte)arr[--tail]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  private void collapsetail(byte[] arr,int head,int tail,BytePredicate filter){
    do{
      if(--tail==head){
        this.tail=tail;
        return;
      }
    }while(filter.test((byte)arr[tail]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  private void collapseHeadAndTail(byte[] arr,int head,int tail,BytePredicate filter){
    do{
      if(++head==tail){
        this.tail=-1;
        return;
      }
    }while(filter.test((byte)arr[head]));
    while(--tail!=head){
      if(!filter.test((byte)arr[tail])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
    this.head=head;
    this.tail=head;
  }
  private boolean collapseBody(byte[] arr,int head,int tail,BytePredicate filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((byte)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((byte)arr[midPoint])){
            this.tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        return true;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((byte)arr[midPoint])){
        this.tail=pullDown(arr,midPoint,tail,filter);
        return true;
      }
    }
    return false;
  }
  boolean nonfragmentedRemoveIf(int head,int tail,BytePredicate filter){
    final byte[] arr;
    if(filter.test((byte)(arr=this.arr)[head])){
      if(head==tail){
        this.tail=-1;
      }else{
        if(filter.test((byte)arr[tail])){
          collapseHeadAndTail(arr,head,tail,filter);
        }else{
          collapsehead(arr,head,tail,filter);
        }
      }
      return true;
    }else if(head!=tail){
      if(filter.test((byte)arr[tail])){
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
      byte[] arr;
      input.readFully(arr=new byte[size]);
      this.tail=size-1;
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
        byte[] arr;
        output.writeInt(size+(size=(arr=this.arr).length));
        output.write(arr,head,size-head);
        output.write(arr,0,tail);
      }else{
        output.writeInt(size);
        output.write(arr,head,size);
      }
    }else{
      output.writeInt(0);
    }
  }
  @Override public boolean equals(Object obj){
      //TODO
      return false;
  }
  public static class Checked extends ByteArrDeq{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(int initialCapacity){
      super(initialCapacity);
    }
    Checked(int head,byte[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public boolean equals(Object obj){
      //TODO
      return false;
    }
    //TODO CollapseEnd<true>(head,tail,++)
    private void fragmentedCollapsehead(byte[] arr,int head,int tail,BytePredicate filter,int modCount){
      //TODO
    }
    private void collapsehead(byte[] arr,int head,int tail,BytePredicate filter,int modCount){
      for(int srcOffset=head;++srcOffset!=tail;)
      {
        if(!filter.test((byte)arr[srcOffset]))
        {
          collapseBodyHelper(arr,srcOffset,tail,filter,modCount);
          this.modCount=modCount+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.head=head;
    }
    //TODO CollapseEnd<true>(tail,head,--)
    private void fragmentedCollapsetail(byte[] arr,int head,int tail,BytePredicate filter,int modCount){
      //TODO
    }
    private void collapsetail(byte[] arr,int head,int tail,BytePredicate filter,int modCount){
      for(int srcOffset=tail;--srcOffset!=head;)
      {
        if(!filter.test((byte)arr[srcOffset]))
        {
          collapseBodyHelper(arr,head,srcOffset,filter,modCount);
          this.modCount=modCount+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.tail=head;
    }
    private void collapseBodyHelper(byte[] arr,int head,int tail,BytePredicate filter,int modCount){
      //TODO
    }
    private void collapseHeadAndTail(byte[] arr,int head,int tail,BytePredicate filter,int modCount){
      for(int headOffset=head+1;headOffset!=tail;++headOffset)
      {
        if(!filter.test((byte)arr[headOffset]))
        {
          for(int tailOffset=tail-1;tailOffset!=headOffset;--tailOffset)
          {
            if(!filter.test((byte)arr[tailOffset]))
            {
              collapseBodyHelper(arr,headOffset,tailOffset,filter,modCount);
              this.modCount=modCount+1;
              return;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=headOffset;
          this.tail=headOffset;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      this.tail=-1;
    }
    private boolean collapseBody(byte[] arr,int head,int tail,BytePredicate filter,int modCount){
      //TODO
      return false;
    }
    @Override boolean fragmentedRemoveIf(int head,int tail,BytePredicate filter){
      //TODO
      return false;
    }
    @Override boolean nonfragmentedRemoveIf(int head,int tail,BytePredicate filter){
      final int modCount=this.modCount;
      try{
        final byte[] arr;
        if(filter.test((byte)(arr=this.arr)[head])){
          if(head==tail){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            this.tail=-1;
          }else if(filter.test((byte)arr[tail])){
            collapseHeadAndTail(arr,head,tail,filter,modCount);
          }else{
            collapsehead(arr,head,tail,filter,modCount);
          }
          return true;
        }else if(head!=tail){
          if(filter.test((byte)arr[tail])){
            collapsetail(arr,head,tail,filter,modCount);
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
    @Override public OmniIterator.OfByte iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniIterator.OfByte descendingIterator(){
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
      @Override public byte nextByte(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final byte[] arr;
          final var ret=(byte)(arr=root.arr)[cursor];
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
      private void fragmentedAscendingRemove(int head,int lastRet,int tail,ByteArrDeq root){
        byte[] arr;
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
      private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,ByteArrDeq root){
        int headDist,tailDist;
        if((headDist=lastRet-head)<=(tailDist=tail-lastRet)){
          root.head=pullUp(root.arr,head,headDist);
        }else{
          ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
          root.tail=tail-1;
          if(this.cursor!=-1){
            this.cursor=lastRet;
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
      @Override void uncheckedForEachRemaining(int cursor,ByteConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int tail=(root=this.root).tail;
        try{
          final var arr=root.arr;
          if(cursor>tail){
            OmniArray.OfByte.ascendingForEach(arr,cursor,arr.length-1,action);
            cursor=0;
          }
          OmniArray.OfByte.ascendingForEach(arr,cursor,tail,action);
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
      @Override public byte nextByte(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final byte[] arr;
          final var ret=(byte)(arr=root.arr)[cursor];
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
      private void fragmentedDescendingRemove(int head,int lastRet,int tail,ByteArrDeq root){
        byte[] arr;
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
              ((AbstractDeqItr)this).nonfragmentedDescendingRemove(head,lastRet,tail,root);
          }
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override void uncheckedForEachRemaining(int cursor,ByteConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int head=(root=this.root).head;
        try{
          final var arr=root.arr;
          if(cursor<head){
            OmniArray.OfByte.descendingForEach(arr,0,cursor,action);
            cursor=arr.length-1;
          }
          OmniArray.OfByte.descendingForEach(arr,head,cursor,action);
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
        final byte[] dst;
        int size,head;
        Checked clone;
        if((size=(++tail)-(head=this.head))<=0){
          clone=new Checked(0,dst=new byte[size+=arr.length],size-1);
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
        }else{
          clone=new Checked(0,dst=new byte[size],size-1);
        }
        ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        return clone;
      }
      return new Checked();
    }
    @Override public byte removeLastByte(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final byte[] arr;
        var ret=(byte)((arr=this.arr)[tail]);
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
    @Override public byte popByte(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final byte[] arr;
        int head;
        var ret=(byte)((arr=this.arr)[head=this.head]);
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
    @Override public void push(byte val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void addLast(byte val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override void uncheckedForEach(final int tail,ByteConsumer action){
      final int modCount=this.modCount;
      try{
        super.uncheckedForEach(tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public byte byteElement(){
      if(tail!=-1){
        return (byte)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override public byte getLastByte(){
      final int tail;
      if((tail=this.tail)!=-1){
        return (byte)arr[tail];
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
    @Override public byte pollByte(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final byte[] arr;
        int head;
        var ret=(byte)((arr=this.arr)[head=this.head]);
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
        final byte[] arr;
        var ret=(byte)((arr=this.arr)[tail]);
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
    @Override public Byte poll(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final byte[] arr;
        int head;
        var ret=(Byte)((arr=this.arr)[head=this.head]);
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
    @Override public Byte pollLast(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final byte[] arr;
        var ret=(Byte)((arr=this.arr)[tail]);
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
        final byte[] arr;
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
        final byte[] arr;
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
    @Override public float pollFloat(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final byte[] arr;
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
        final byte[] arr;
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
    @Override public long pollLong(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final byte[] arr;
        int head;
        var ret=(long)((arr=this.arr)[head=this.head]);
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
        final byte[] arr;
        var ret=(long)((arr=this.arr)[tail]);
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
        final byte[] arr;
        int head;
        var ret=(int)((arr=this.arr)[head=this.head]);
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
        final byte[] arr;
        var ret=(int)((arr=this.arr)[tail]);
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
        final byte[] arr;
        int head;
        var ret=(short)((arr=this.arr)[head=this.head]);
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
        final byte[] arr;
        var ret=(short)((arr=this.arr)[tail]);
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
    @Override
    boolean uncheckedremoveVal(int tail
    ,int val
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
              break;
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
    ,int val
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
