package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.IntFunction;
public class ByteArrDeq extends ByteUntetheredArrSeq<Byte> implements OmniDeque.OfByte
{
  ByteArrDeq(int head,byte[] arr,int tail){
    super(head,arr,tail);
  }
  ByteArrDeq(){
    super();
  }
  @Override public boolean offerFirst(byte val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(byte val){
    addLast(val);
    return true;
  }
  @Override public boolean offer(byte val){
    addLast(val);
    return true;
  }
  @Override public void addFirst(byte val){
    push(val);
  }
  @Override public boolean add(byte val){
    addLast(val);
    return true;
  }
  @Override public byte byteElement(){
    return (byte)arr[head];
  }
  @Override public byte getLastByte(){
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
  @Override public byte pollByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (byte)uncheckedRemoveFirst(tail);
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte pollLastByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (byte)uncheckedRemoveLast(tail);
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
        final byte[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new byte[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public byte removeLastByte(){
    return (byte)uncheckedRemoveLast(this.tail);
  }
  @Override public byte popByte(){
    return (byte)uncheckedRemoveFirst(this.tail);
  }
  @Override public OmniIterator.OfByte iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public OmniIterator.OfByte descendingIterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public Object clone(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean removeIf(BytePredicate filter){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter);
  }
  @Override public void forEach(ByteConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action);
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
  @Override public void addLast(byte val){
    var arr=this.arr;
    int tail;
    if((tail=this.tail)!=-1){
      int head;
      if((head=this.head)<=tail){
        if(++tail==arr.length && head==0){
          ArrCopy.uncheckedCopy(arr,0,arr=new byte[OmniArray.growBy50Pct(tail)],0,tail);
          this.arr=arr;
        }
      }else if(++tail==head){
        this.head=0;
        final var tmp=new byte[OmniArray.growBy50Pct(tail=arr.length)];
        final int copyLength;
        ArrCopy.uncheckedCopy(arr,head,tmp,0,copyLength=tail-head);
        ArrCopy.uncheckedCopy(arr,0,tmp,copyLength,head);
        this.arr=arr=tmp;
      }
      arr[tail]=val;
      this.tail=tail;
    }else{
      if(arr==null){
        this.arr=new byte[]{val};
      }else{
        if(arr==OmniArray.OfByte.DEFAULT_ARR){
          this.arr=arr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }
        arr[0]=val;
      }
      this.head=0;
      this.tail=0;
    }
  }
  @Override public void push(byte val){
    var arr=this.arr;
    int tail;
    if((tail=this.tail)!=-1){
      int head;
      if((head=this.head)<=tail){
        if(head==0 && tail==arr.length-1){
          final var tmp=new byte[head=OmniArray.growBy50Pct(++tail)];
          this.tail=head-1;
          ArrCopy.uncheckedCopy(arr,0,tmp,head-=tail,tail);
          this.arr=arr=tmp;
        }
        --head;
      }else if(--head==tail){
        int arrLength;
        final var tmp=new byte[head=OmniArray.growBy50Pct(arrLength=arr.length)];
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
        this.arr=new byte[]{val};
        this.head=0;
        this.tail=0;
      }else if(arr==OmniArray.OfByte.DEFAULT_ARR){
        this.arr=arr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
  boolean uncheckedRemoveIf(int tail,BytePredicate action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  void uncheckedForEach(int tail,ByteConsumer action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      for(int bound=arr.length;;){
        action.accept((byte)arr[head]);
        if(++head==bound){
          head=0;
          break;
        }
      }
    }
    for(;;){
      action.accept((byte)arr[head]);
      if(head==tail){
        break;
      }
      ++head;
    }
  }
  byte uncheckedRemoveLast(int tail){
    final byte[] arr;
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
  byte uncheckedRemoveFirst(int tail){
    int head;
    final byte[] arr;
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
