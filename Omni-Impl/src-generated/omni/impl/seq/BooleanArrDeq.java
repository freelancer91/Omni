package omni.impl.seq;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import java.util.function.IntFunction;
import omni.util.TypeUtil;
import omni.function.BooleanPredicate;
import omni.function.BooleanConsumer;
import omni.util.ToStringUtil;
import java.util.ConcurrentModificationException;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
public class BooleanArrDeq extends AbstractBooleanArrDeq{
  private static final long serialVersionUID=1L;
  transient boolean[] arr;
  public BooleanArrDeq(){
    super();
    this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
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
  }
  BooleanArrDeq(int head,boolean[] arr,int tail){
    super(head,tail);
    this.arr=arr;
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
  void uncheckedForEach(final int tail,BooleanConsumer action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      OmniArray.OfBoolean.ascendingForEach(arr,head,arr.length-1,action);
      head=0;
    }
    OmniArray.OfBoolean.ascendingForEach(arr,head,tail,action);
  }
  @Override public boolean booleanElement(){
    return (boolean)arr[head];
  }
  @Override public boolean getLastBoolean(){
    return (boolean)arr[tail];
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
            if(++head>bound){
              this.head=0;
            }else{
              arr[index]=!val;
              this.head=head;
            }
            return true;
          }else if(index==bound){
            for(index=0;;++index){
              if(val==(arr[index])){
                arr[index]=!val;
                this.head=(++head>bound)?0:head;
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
            if(head==tail){
              this.tail=-1;
            }else{
              arr[index]=!val;
              this.head=head+1;
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
            if(--tail==-1){
              this.tail=bound;
            }else{
              arr[index]=!val;
              this.tail=tail;
            }
            return true;
          }else if(index==0){
            for(index=bound;;--index){
              if(val==(arr[index])){
                arr[index]=!val;
                this.tail=(--tail==-1)?bound:tail;
                return true;
              }else if(index==head){
                break;
              }
            }
            break;
          }
        }
      }else{
        for(index=tail;;--index){
          if(val==(arr[index])){
            if(head==tail){
               this.tail=-1;
            }else{
               arr[index]=!val;
               this.tail=tail-1;
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
  boolean uncheckedcontains (int tail
  ,boolean val
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfBoolean.uncheckedcontains (arr,0,tail,val) || OmniArray.OfBoolean.uncheckedcontains (arr,head,arr.length-1,val);
    }
    return OmniArray.OfBoolean.uncheckedcontains (arr,head,tail,val);
  }
  int uncheckedsearch (int tail
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
  String uncheckedToString(int tail){
    final var arr=this.arr;
    final byte[] buffer;
    int size,head,bufferOffset=1;
    if((size=(++tail)-(head=this.head))<=0){
      int bound;
      if((size+=(bound=arr.length))<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
        [0]=(byte)'[';
        for(;;++bufferOffset){
          buffer[bufferOffset=ToStringUtil.getStringBoolean(arr[head],buffer,bufferOffset)]=(byte)',';
          buffer[++bufferOffset]=(byte)' ';
          if(++head==bound){
            for(head=0;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' '){
              bufferOffset=ToStringUtil.getStringBoolean(arr[head],buffer,++bufferOffset);
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
          builder.uncheckedAppendBoolean(arr[head]);
          builder.uncheckedAppendCommaAndSpace();
          if(++head==bound){
            for(head=0;;builder.uncheckedAppendCommaAndSpace()){
              builder.uncheckedAppendBoolean(arr[head]);
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
      if(size<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
        [size=OmniArray.OfBoolean.ascendingToString(arr,head,tail-1,buffer,1)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        OmniArray.OfBoolean.ascendingToString(arr,head,tail-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
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
        int tail;
        if((tail=this.tail)==-1){
          arr[tail=arr.length-1]=val;
          this.tail=tail;
          this.head=tail;
        }else{
          int head;
          if((head=this.head-1)==tail){
            final boolean[] newArr;
            int newCap,size;
            this.tail=(newCap=OmniArray.growBy50Pct(head+(size=arr.length)))-1;
            ArrCopy.uncheckedCopy(arr,0,newArr=new boolean[newCap],newCap-=(++tail),tail);
            ArrCopy.uncheckedCopy(arr,head+1,newArr,head=newCap-(size-=tail),size);
            this.arr=arr=newArr;
            --head;
          }else if(head==-1 && tail==(head=arr.length-1)){
            int newCap;
            this.tail=(newCap=OmniArray.growBy50Pct(++tail))-1;
            ArrCopy.uncheckedCopy(arr,0,arr=new boolean[newCap],head=newCap-tail,tail);
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
  private void initFromNullArr(boolean val){
    this.head=0;
    this.tail=0;
    this.arr=new boolean[]{val};
  }
  @Override public void addLast(boolean val){
    boolean[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfBoolean.DEFAULT_ARR){
        this.head=0;
        this.tail=0;
        this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
            final boolean[] newArr;
            (newArr=new boolean[OmniArray.growBy50Pct(tail=arr.length)])[tail]=val;
            this.tail=tail;
            ArrCopy.uncheckedCopy(arr,head,newArr,0,tail-=head);
            ArrCopy.uncheckedCopy(arr,0,newArr,tail,head);
            this.arr=newArr;
          }else{
            if(tail==arr.length){
              if(head==0){
                ArrCopy.uncheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(tail)],0,tail);
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
  private static int pullUp(boolean[] arr,int head,int headDist){
    ArrCopy.semicheckedCopy(arr,head,arr,++head,headDist);
    return head;
  }
  private static int fragmentedPullUp(boolean[] arr,int head,int headDist){
    if(headDist==0){
      return 0;
    }else{
      ArrCopy.uncheckedCopy(arr,head,arr,++head,headDist);
      return head;
    }
  }
  private static int fragmentedPullDown(boolean[] arr,int arrBound,int tail){
    if(tail==0){
      return arrBound;
    }
    ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
    return tail-1;
  }
  private static class AscendingItr extends AbstractDeqItr
  {
    transient final BooleanArrDeq root;
    private AscendingItr(AscendingItr itr){
      super(itr);
      this.root=itr.root;
    }
    private AscendingItr(BooleanArrDeq root){
      super(root.tail!=-1?root.head:-1);
      this.root=root;
    }
    private AscendingItr(BooleanArrDeq root,int cursor){
      super(cursor);
      this.root=root;
    }
    @Override public Object clone(){
      return new AscendingItr(this);
    }
    @Override public boolean nextBoolean(){
      final boolean[] arr;
      int cursor;
      final BooleanArrDeq root;
      final var ret=(boolean)(arr=(root=this.root).arr)[cursor=this.cursor];
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
      final BooleanArrDeq root;
      final boolean[] arr;
      if((tail=(root=this.root).tail)<(headDist=(arrBound=(arr=root.arr).length-1)-(head=root.head))){
        arr[arrBound]=arr[0];
        root.tail=fragmentedPullDown(arr,arrBound,tail);
        this.cursor=arrBound;
      }else{
        root.head=fragmentedPullUp(arr,head,headDist);
      }
    }
    private void fragmentedAscendingRemove(int head,int lastRet,int tail,BooleanArrDeq root){
      boolean[] arr;
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
    private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,BooleanArrDeq root){
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
          final BooleanArrDeq root;
          if((tail=(root=this.root).tail)<(head=root.head)){
            fragmentedAscendingRemove(head,cursor-1,tail,root);
          }else{
            nonfragmentedAscendingRemove(head,cursor-1,tail,root);
          }
      }
    }
    @Override void uncheckedForEachRemaining(int cursor,BooleanConsumer action){
      final BooleanArrDeq root;
      int tail;
      final var arr=(root=this.root).arr;
      if(cursor>(tail=root.tail)){
        OmniArray.OfBoolean.ascendingForEach(arr,cursor,arr.length-1,action);
        cursor=0;
      }
      OmniArray.OfBoolean.ascendingForEach(arr,cursor,tail,action);
      this.cursor=-1;
    }
  }
  private static class DescendingItr extends AscendingItr{
    private DescendingItr(DescendingItr itr){
      super(itr);
    }
    private DescendingItr(BooleanArrDeq root){
      super(root,root.tail);
    }
    @Override public Object clone(){
      return new DescendingItr(this);
    }
    @Override void uncheckedForEachRemaining(int cursor,BooleanConsumer action){
      final BooleanArrDeq root;
      final int head;
      final var arr=(root=this.root).arr;
      if(cursor<(head=root.head)){
        OmniArray.OfBoolean.descendingForEach(arr,0,cursor,action);
        cursor=arr.length-1;
      }
      OmniArray.OfBoolean.descendingForEach(arr,head,cursor,action);
      this.cursor=-1;
    }
    @Override public boolean nextBoolean(){
      int cursor;
      final BooleanArrDeq root;
      final var arr=(root=this.root).arr;
      this.cursor=(cursor=this.cursor)==root.head?-1:cursor==0?arr.length-1:cursor-1;
      return (boolean)arr[cursor];
    }
    private void fragmentedDescendingRemove(int head,int cursor,int tail,BooleanArrDeq root){
      boolean[] arr;
      int arrBound;
      if((arrBound=(arr=root.arr).length-1)==cursor){
        //remove index 0
        if(tail<=(cursor=arrBound-head)+1){
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
    private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,BooleanArrDeq root){
      int tailDist,headDist;
      if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
        ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
        root.tail=tail-1;
      }else{
        boolean[] arr;
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
        BooleanArrDeq root;
        int head,tail;
        if((tail=(root=this.root).tail)<(head=root.head)){
          fragmentedDescendingRemove(head,cursor,tail,root);
        }else{
          nonfragmentedDescendingRemove(head,cursor+1,tail,root);
        }
      }
    }
  } 
  @Override public OmniIterator.OfBoolean iterator(){
    return new AscendingItr(this);
  }
  @Override public OmniIterator.OfBoolean descendingIterator(){
    return new DescendingItr(this);
  }
  boolean fragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
    final var arr=this.arr;
    int trueCount=0;
    int size;
    for(int i=tail;;--i){
      if(arr[i])
      {
        ++trueCount;
      }
      if(i==0)
      {
        for(i=(size=arr.length)-1;;--i)
        {
          if(arr[i])
          {
            ++trueCount;
          }
          if(i==head)
          {
            break;
          }
        }
        size+=(tail+1-head);
        break;
      }
    }
    if(trueCount==size)
    {
      if(filter.test(true))
      {
        this.tail=-1;
        return true;
      }
    }else if(trueCount==0)
    {
      if(filter.test(false))
      {
        this.tail=-1;
        return true;
      }
    }else{
      if(filter.test(true)){
        if(filter.test(false)){
          this.tail=-1;
        }else{
          setRange(arr,size-=(trueCount+1),false);
          this.head=0;
          this.tail=size;         
        }
        return true;
      }else if(filter.test(false)){
        setRange(arr,--trueCount,true);
        this.head=0;
        this.tail=trueCount;
        return true;
      }
    }
    return false;
  }
  private static void setRange(boolean[] arr,int tail,boolean val){
    for(;;--tail){
      arr[tail]=val;
      if(tail==0){
        return;
      }
    }
  }
  boolean nonfragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
    final var arr=this.arr;
    int trueCount=0;
    for(int i=head;;++i){
      if(arr[i]){
        ++trueCount;
      }
      if(i==tail){
        break;
      }
    }
    int size;
    if(trueCount==(size=tail-head+1)){
      if(filter.test(true)){
        this.tail=-1;
        return true;
      }
    }else if(trueCount==0){
      if(filter.test(false)){
        this.tail=-1;
        return true;
      }
    }else{
      if(filter.test(true)){
        if(filter.test(false)){
          this.tail=-1;
        }else{
          setRange(arr,size-=(trueCount+1),false);
          this.head=0;
          this.tail=size;
        }
        return true;
      }else if(filter.test(false)){
        setRange(arr,--trueCount,true);
        this.head=0;
        this.tail=trueCount;
        return true;
      }
    }
    return false;
  }
  @Override public void readExternal(ObjectInput input) throws IOException
  {
    int size;
    if((size=input.readInt())!=0){
      boolean[] arr;
      OmniArray.OfBoolean.readArray(arr=new boolean[size],0,--size,input);
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
        boolean[] arr;
        output.writeInt(size+(size=(arr=this.arr).length));
        for(int word=TypeUtil.castToByte(arr[head]),marker=1;;){
          if(++head==size){
             head=0;
             do{
               if((marker<<=1)==(1<<8)){
                 output.writeByte(word);
                 word=0;
                 marker=1;
               }
               if(arr[head]){
                 word|=marker;
               }
             }while(++head!=tail);
             output.writeByte(word);
             return;
          }
          if((marker<<=1)==(1<<8)){
            output.writeByte(word);
            word=0;
            marker=1;
          }
          if(arr[head]){
            word|=marker;
          }
        }
      }else{
        output.writeInt(size);
        OmniArray.OfBoolean.writeArray(arr,head,tail-1,output);
      }
    }else{
      output.writeInt(0);
    }
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
      final boolean[] arr;
      int trueCount=0,bound=(arr=this.arr).length;
      for(int i=head;;){
        if(arr[i]){
          ++trueCount;
        }
        if(++i==bound){
          for(i=0;;++i){
            if(arr[i]){
              ++trueCount;
            }
            if(i==tail){
              break;
            }
          }
          break;
        }
      }
      int modCount=this.modCount;
      try{
        if(trueCount!=0){
          if(trueCount!=(bound+=(tail-head+1))){
            head=(filter.test(true)?0b01:0b00)|(filter.test(false)?0b10:0b00);
            CheckedCollection.checkModCount(modCount,this.modCount);
            switch(head){
              case 0b00:
                return false;
              case 0b01:
                setRange(arr,bound-=(trueCount+1),false);
                this.tail=bound;
                this.head=0;
                break;
              case 0b10:
                setRange(arr,--trueCount,true);
                this.tail=trueCount;
                this.head=0;
                break;
              default:
                this.tail=-1;
            }
          }else{
            boolean removeTrue=filter.test(true);
            CheckedCollection.checkModCount(modCount,this.modCount);
            if(!removeTrue){
              return false;
            }
            this.tail=-1;
          }
        }else{
          boolean removeFalse=filter.test(false);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if(!removeFalse){
             return false; 
          }
          this.tail=-1;
        }
        this.modCount=modCount+1;
        return true;
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @Override boolean nonfragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
      final var arr=this.arr;
      int trueCount=0;
      for(int i=head;;++i){
        if(arr[i]){
          ++trueCount;
        }
        if(i==tail){
          break;
        }
      }
      final int modCount=this.modCount;
      try{
        if(trueCount!=0){
          if(trueCount!=(tail-=(head-1))){
            head=(filter.test(true)?0b01:0b00)|(filter.test(false)?0b10:0b00);
            CheckedCollection.checkModCount(modCount,this.modCount);
            switch(head){
              case 0b00:
                return false;
              case 0b01:
                setRange(arr,tail-=(trueCount+1),false);
                this.tail=tail;
                this.head=0;
                break;
              case 0b10:
                setRange(arr,--trueCount,true);
                this.tail=trueCount;
                this.head=0;
                break;
              default:
                this.tail=-1;
            }
          }else{
            boolean removeTrue=filter.test(true);
            CheckedCollection.checkModCount(modCount,this.modCount);
            if(!removeTrue){
               return false; 
            }
            this.tail=-1;
          }
        }else{
          boolean removeFalse=filter.test(false);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if(!removeFalse){
             return false; 
          }
          this.tail=-1;
        }
        this.modCount=modCount+1;
        return true;
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      return new DescendingItr(this);
    }
    private static class AscendingItr extends AbstractDeqItr{
      transient int modCount;
      transient int lastRet;
      transient final Checked root;
      private AscendingItr(AscendingItr itr){
        super(itr);
        this.modCount=itr.modCount;
        this.lastRet=itr.lastRet;
        this.root=itr.root;
      }
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
      @Override public Object clone(){
        return new AscendingItr(this);
      }
      @Override public boolean nextBoolean(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final boolean[] arr;
          final var ret=(boolean)(arr=root.arr)[cursor];
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
      private void fragmentedAscendingRemove(int head,int lastRet,int tail,BooleanArrDeq root){
        boolean[] arr;
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
      private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,BooleanArrDeq root){
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
      @Override void uncheckedForEachRemaining(final int expectedCursor,BooleanConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int tail=(root=this.root).tail;
        try{
          final var arr=root.arr;
          int cursor;
          if((cursor=expectedCursor)>tail){
            OmniArray.OfBoolean.ascendingForEach(arr,cursor,arr.length-1,action);
            cursor=0;
          }
          OmniArray.OfBoolean.ascendingForEach(arr,cursor,tail,action);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount,expectedCursor,this.cursor);
        }
        this.lastRet=tail;
        this.cursor=-1;
      }
    }
    private static class DescendingItr extends AscendingItr{
      private DescendingItr(DescendingItr itr){
        super(itr);
      }
      private DescendingItr(Checked root){
        super(root,root.tail);
      }
      @Override public Object clone(){
        return new DescendingItr(this);
      }
      @Override public boolean nextBoolean(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final boolean[] arr;
          final var ret=(boolean)(arr=root.arr)[cursor];
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
      private void fragmentedDescendingRemove(int head,int lastRet,int tail,BooleanArrDeq root){
        boolean[] arr;
        int headDist,tailDist,arrBound=(arr=root.arr).length-1;
        if((headDist=lastRet-head)>=0){
          if(headDist<=(tailDist=arrBound-lastRet)+tail+1){
            if(headDist==0){
              if(tailDist==0){
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
      private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,BooleanArrDeq root){
        int tailDist,headDist;
        if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
          ArrCopy.semicheckedSelfCopy(root.arr,lastRet,lastRet+1,tailDist);
          root.tail=tail-1;
        }else{
          if(headDist==0){
            root.head=head+1;
          }else{
            boolean[] arr;
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
      @Override void uncheckedForEachRemaining(final int expectedCursor,BooleanConsumer action){
        int modCount=this.modCount;
        final Checked root;
        int head=(root=this.root).head;
        try{
          final var arr=root.arr;
          int cursor;
          if((cursor=expectedCursor)<head){
            OmniArray.OfBoolean.descendingForEach(arr,0,cursor,action);
            cursor=arr.length-1;
          }
          OmniArray.OfBoolean.descendingForEach(arr,head,cursor,action);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount,expectedCursor,this.cursor);
        }
        this.lastRet=head;
        this.cursor=-1;
      }
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
              if(++head>bound){
                this.head=0;
              }else{
                arr[index]=!val;
                this.head=head;
              }
              return true;
            }else if(index==bound){
              for(index=0;;++index){
                if(val==(arr[index])){
                  this.modCount=modCount+1;
                  arr[index]=!val;
                  this.head=(++head>bound)?0:head;
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
              if(head==tail){
                this.tail=-1;
              }else{
                arr[index]=!val;
                this.head=head+1;
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
              if(--tail==-1){
                this.tail=bound;
              }else{
                arr[index]=!val;
                this.tail=tail;
              }
              return true;
            }else if(index==0){
              for(index=bound;;--index){
                if(val==(arr[index])){
                  this.modCount=modCount+1;
                  arr[index]=!val;
                  this.tail=(--tail==-1)?bound:tail;
                  return true;
                }else if(index==head){
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
              if(head==tail){
                 this.tail=-1;
              }else{
                 arr[index]=!val;
                 this.tail=tail-1;
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
